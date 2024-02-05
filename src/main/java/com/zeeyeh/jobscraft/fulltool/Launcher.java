package com.zeeyeh.jobscraft.fulltool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.liufree.xmindparser.XmindParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Launcher {

    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("h", "help", false, "display help information");
        options.addOption("i", "input", true, "Input file (must be a file with the suffix \". xmind\")");
        options.addOption("o", "output", true, "output file");
        options.addOption("r", "remove", false, "Force file generation (delete if the given output file already exists)");
        options.addOption("a", "append", false, "Content addition (if the given output file already exists, attach the content to its end)");
        CommandLine commandLine = new DefaultParser().parse(options, args);
        if (commandLine.hasOption("h")) {
            showHelp(options);
            System.exit(0);
        }
        String inputFilePath = commandLine.getOptionValue("i");
        if (inputFilePath == null) {
            System.out.println("Program exception! Unable to find parameters: -i");
            System.exit(1);
        }
        String outputFileName = commandLine.getOptionValue("o");
        if (outputFileName == null) {
            System.out.println("Program exception! Unable to find parameters: -o");
            System.exit(2);
        }
        File inputFile = new File(inputFilePath);
        String path = inputFile.getAbsolutePath();
        File outputFile = new File(System.getProperty("user.dir"), outputFileName);
        if (commandLine.hasOption("r")) {
            if (outputFile.exists()) {
                if (!outputFile.delete()) {
                    System.err.println("Program exception! cannot delete file: " + outputFile.getAbsolutePath());
                    System.exit(3);
                }
                if (!outputFile.createNewFile()) {
                    System.err.println("Program exception! could not create file: " + outputFile.getAbsolutePath());
                    System.exit(4);
                }
            }
        }
        if (commandLine.hasOption("a")) {
            if (outputFile.exists()) {
                FileUtil.appendLines(Collections.singleton(""), outputFile, StandardCharsets.UTF_8);
                FileUtil.appendLines(Collections.singleton(""), outputFile, StandardCharsets.UTF_8);
            }
        }
        String parsedJson = XmindParser.parseJson(path);
        JSONObject jsonObject = JSONObject.parseObject(parsedJson);
        JSONArray jobClasses = jsonObject.getJSONObject("rootTopic").getJSONObject("children").getJSONArray("attached");
        List<Job> jobs = new ArrayList<>();
        List<Level> levels = new ArrayList<>();
        List<Curtail> curtails = new ArrayList<>();
        for (Object jobClass : jobClasses) {
            JSONObject jobClassObject = (JSONObject) jobClass;
            // 职业总分类标题
//            String title = jobClassObject.getString("title");
            JSONArray jobObjects = jobClassObject.getJSONObject("children").getJSONArray("attached");
            for (Object jobObject : jobObjects) {
                JSONObject job = (JSONObject) jobObject;
                // 职业名称
                String jobTitle = job.getString("title");
                long jobId = IdUtil.getSnowflakeNextId();
                jobs.add(new Job(jobId, Collections.emptyList(), PinyinUtil.getPinyin(jobTitle).replace(" ", ""), jobTitle));
//                System.out.println(" " + jobTitle);
                JSONArray jobLevelObjects = job.getJSONObject("children").getJSONArray("attached");
                for (Object jobLevelObject : jobLevelObjects) {
                    JSONObject jobLevel = (JSONObject) jobLevelObject;
                    // 职业等级名称
                    String jobLevelTitle = jobLevel.getString("title");
                    long levelId = IdUtil.getSnowflakeNextId();
                    Level level = new Level(levelId, PinyinUtil.getPinyin(jobTitle + jobLevelTitle).replace(" ", ""), jobTitle + jobLevelTitle, 0, 0, Collections.emptyList());
//                    System.out.println("   " + jobLevelTitle);
                    JSONArray jobCurtailObjects = jobLevel.getJSONObject("children").getJSONArray("attached");
                    for (Object jobCurtailObject : jobCurtailObjects) {
                        JSONObject jobCurtail = (JSONObject) jobCurtailObject;
                        // 职业限制类型标题
                        String curtailTitle = jobCurtail.getString("title");
//                        System.out.println("    " + curtailTitle);
                        long curtailId = IdUtil.getSnowflakeNextId();
                        Curtail curtail = new Curtail(curtailId, PinyinUtil.getPinyin(jobTitle + jobLevelTitle + curtailTitle).replace(" ", ""), jobId, levelId, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
                        if (curtailTitle.equalsIgnoreCase("使用物品")) {
                            if (jobCurtail.containsKey("children")) {
                                JSONArray curtailItemObjects = jobCurtail.getJSONObject("children").getJSONArray("attached");
                                List<Curtail.IdExp> tools = new ArrayList<>();
                                for (Object curtailItemObject : curtailItemObjects) {
                                    JSONObject curtailItem = (JSONObject) curtailItemObject;
                                    // 限制物品名称
                                    String itemTitle = curtailItem.getString("title");
                                    StringBuilder itemIdChain = new StringBuilder(itemTitle);
                                    Curtail.IdExp idExp = new Curtail.IdExp();
                                    if (curtailItem.containsKey("children")) {
                                        JSONObject item = (JSONObject) curtailItem.getJSONObject("children").getJSONArray("attached").get(0);
                                        // 限制物品Id名称
                                        String itemIdTitle = item.getString("title");
                                        itemIdChain.append(":").append(itemIdTitle);
                                        idExp.setId(itemIdTitle);
                                        if (item.containsKey("children")) {
                                            JSONObject itemIdExp = (JSONObject) item.getJSONObject("children").getJSONArray("attached").get(0);
                                            String itemExpTitle = itemIdExp.getString("title");
                                            itemIdChain.append(":").append(itemExpTitle);
                                            idExp.setExp(Long.parseLong(itemExpTitle));
                                        } else {
                                            itemIdChain.append(":").append("0");
                                            // 限制物品默认经验点
                                            idExp.setExp(0);
                                        }
                                    }
                                    tools.add(idExp);
//                                    System.out.println("     " + itemIdChain);
                                }
                                curtail.setTools(tools);
                            }
                        } else if (curtailTitle.equalsIgnoreCase("食物[通用]")) {
                            if (jobCurtail.containsKey("children")) {
                                JSONArray curtailItemObjects = jobCurtail.getJSONObject("children").getJSONArray("attached");
                                List<Curtail.IdExp> foods = new ArrayList<>();
                                for (Object curtailItemObject : curtailItemObjects) {
                                    JSONObject curtailItem = (JSONObject) curtailItemObject;
//                                        String itemTitle = curtailItem.getString("title");
//                                        StringBuilder itemIdChain = new StringBuilder(itemTitle);
                                    Curtail.IdExp idExp = new Curtail.IdExp();
                                    if (curtailItem.containsKey("children")) {
                                        JSONObject item = (JSONObject) curtailItem.getJSONObject("children").getJSONArray("attached").get(0);
                                        // 限制物品Id名称
                                        String itemIdTitle = item.getString("title");
                                        JSONArray foodConfigs = JSONArray.parseArray(itemIdTitle);
                                        for (Object foodConfig : foodConfigs) {
                                            String foodConfigString = String.valueOf(foodConfig);
                                            String id = foodConfigString.substring(0, foodConfigString.lastIndexOf(":"));
                                            idExp.setId(id);
                                            String exp = foodConfigString.substring(foodConfigString.lastIndexOf(":") + 1);
                                            idExp.setExp(Long.parseLong(exp));
                                            foods.add(idExp);
                                        }
                                    }

                                    foods.add(idExp);
//                                        System.out.println("     " + itemIdChain);
                                }
                                curtail.setFoods(foods);
                            }
                        } else if (curtailTitle.equalsIgnoreCase("可放置方块")) {
                            if (jobCurtail.containsKey("children")) {
                                JSONArray curtailItemObjects = jobCurtail.getJSONObject("children").getJSONArray("attached");
                                List<Curtail.IdExp> places = new ArrayList<>();
                                for (Object curtailItemObject : curtailItemObjects) {
                                    JSONObject curtailItem = (JSONObject) curtailItemObject;
                                    // 限制物品名称
                                    String itemTitle = curtailItem.getString("title");
                                    StringBuilder itemIdChain = new StringBuilder(itemTitle);
                                    Curtail.IdExp idExp = new Curtail.IdExp();
                                    if (curtailItem.containsKey("children")) {
                                        JSONObject item = (JSONObject) curtailItem.getJSONObject("children").getJSONArray("attached").get(0);
                                        // 限制物品Id名称
                                        String itemIdTitle = item.getString("title");
                                        itemIdChain.append(":").append(itemIdTitle);
                                        idExp.setId(itemIdTitle);
                                        if (item.containsKey("children")) {
                                            JSONObject itemIdExp = (JSONObject) item.getJSONObject("children").getJSONArray("attached").get(0);
                                            String itemExpTitle = itemIdExp.getString("title");
                                            itemIdChain.append(":").append(itemExpTitle);
                                            idExp.setExp(Long.parseLong(itemExpTitle));
                                        } else {
                                            itemIdChain.append(":").append("0");
                                            // 限制物品默认经验点
                                            idExp.setExp(0);
                                        }
                                    }
                                    places.add(idExp);
//                                            System.out.println("     " + itemIdChain);
                                }
                                curtail.setPlaces(places);
                            } else if (curtailTitle.equalsIgnoreCase("可破坏方块")) {
                                if (jobCurtail.containsKey("children")) {
                                    JSONArray curtailItemObjects = jobCurtail.getJSONObject("children").getJSONArray("attached");
                                    List<Curtail.IdExp> destructs = new ArrayList<>();
                                    for (Object curtailItemObject : curtailItemObjects) {
                                        JSONObject curtailItem = (JSONObject) curtailItemObject;
                                        // 限制物品名称
                                        String itemTitle = curtailItem.getString("title");
                                        StringBuilder itemIdChain = new StringBuilder(itemTitle);
                                        Curtail.IdExp idExp = new Curtail.IdExp();
                                        if (curtailItem.containsKey("children")) {
                                            JSONObject item = (JSONObject) curtailItem.getJSONObject("children").getJSONArray("attached").get(0);
                                            // 限制物品Id名称
                                            String itemIdTitle = item.getString("title");
                                            itemIdChain.append(":").append(itemIdTitle);
                                            idExp.setId(itemIdTitle);
                                            if (item.containsKey("children")) {
                                                JSONObject itemIdExp = (JSONObject) item.getJSONObject("children").getJSONArray("attached").get(0);
                                                String itemExpTitle = itemIdExp.getString("title");
                                                itemIdChain.append(":").append(itemExpTitle);
                                                idExp.setExp(Long.parseLong(itemExpTitle));
                                            } else {
                                                itemIdChain.append(":").append("0");
                                                // 限制物品默认经验点
                                                idExp.setExp(0);
                                            }
                                        }
                                        destructs.add(idExp);
//                                                System.out.println("     " + itemIdChain);
                                    }
                                    curtail.setDestructs(destructs);
                                }
                            } else if (curtailTitle.equalsIgnoreCase("合成")) {
                                if (jobCurtail.containsKey("children")) {
                                    JSONArray curtailItemObjects = jobCurtail.getJSONObject("children").getJSONArray("attached");
                                    List<Curtail.IdExp> recipes = new ArrayList<>();
                                    for (Object curtailItemObject : curtailItemObjects) {
                                        JSONObject curtailItem = (JSONObject) curtailItemObject;
                                        // 限制物品名称
                                        String itemTitle = curtailItem.getString("title");
                                        StringBuilder itemIdChain = new StringBuilder(itemTitle);
                                        Curtail.IdExp idExp = new Curtail.IdExp();
                                        if (curtailItem.containsKey("children")) {
                                            JSONObject item = (JSONObject) curtailItem.getJSONObject("children").getJSONArray("attached").get(0);
                                            // 限制物品Id名称
                                            String itemIdTitle = item.getString("title");
                                            itemIdChain.append(":").append(itemIdTitle);
                                            idExp.setId(itemIdTitle);
                                            if (item.containsKey("children")) {
                                                JSONObject itemIdExp = (JSONObject) item.getJSONObject("children").getJSONArray("attached").get(0);
                                                String itemExpTitle = itemIdExp.getString("title");
                                                itemIdChain.append(":").append(itemExpTitle);
                                                idExp.setExp(Long.parseLong(itemExpTitle));
                                            } else {
                                                itemIdChain.append(":").append("0");
                                                // 限制物品默认经验点
                                                idExp.setExp(0);
                                            }
                                        }
                                        recipes.add(idExp);
//                                                    System.out.println("     " + itemIdChain);
                                    }
                                    curtail.setRecipes(recipes);
                                }
                            } else if (curtailTitle.equalsIgnoreCase("交互")) {
                                if (jobCurtail.containsKey("children")) {
                                    JSONArray curtailItemObjects = jobCurtail.getJSONObject("children").getJSONArray("attached");
                                    List<Curtail.IdExp> interacts = new ArrayList<>();
                                    for (Object curtailItemObject : curtailItemObjects) {
                                        JSONObject curtailItem = (JSONObject) curtailItemObject;
                                        // 限制物品名称
                                        String itemTitle = curtailItem.getString("title");
                                        StringBuilder itemIdChain = new StringBuilder(itemTitle);
                                        Curtail.IdExp idExp = new Curtail.IdExp();
                                        if (curtailItem.containsKey("children")) {
                                            JSONObject item = (JSONObject) curtailItem.getJSONObject("children").getJSONArray("attached").get(0);
                                            // 限制物品Id名称
                                            String itemIdTitle = item.getString("title");
                                            itemIdChain.append(":").append(itemIdTitle);
                                            idExp.setId(itemIdTitle);
                                            if (item.containsKey("children")) {
                                                JSONObject itemIdExp = (JSONObject) item.getJSONObject("children").getJSONArray("attached").get(0);
                                                String itemExpTitle = itemIdExp.getString("title");
                                                itemIdChain.append(":").append(itemExpTitle);
                                                idExp.setExp(Long.parseLong(itemExpTitle));
                                            } else {
                                                itemIdChain.append(":").append("0");
                                                // 限制物品默认经验点
                                                idExp.setExp(0);
                                            }
                                        }
                                        interacts.add(idExp);
//                                                        System.out.println("     " + itemIdChain);
                                    }
                                    curtail.setInteracts(interacts);
                                }
                            } else if (curtailTitle.equalsIgnoreCase("攻击")) {
                                if (jobCurtail.containsKey("children")) {
                                    JSONArray curtailItemObjects = jobCurtail.getJSONObject("children").getJSONArray("attached");
                                    List<Curtail.IdExp> attacks = new ArrayList<>();
                                    for (Object curtailItemObject : curtailItemObjects) {
                                        JSONObject curtailItem = (JSONObject) curtailItemObject;
                                        // 限制物品名称
                                        String itemTitle = curtailItem.getString("title");
                                        StringBuilder itemIdChain = new StringBuilder(itemTitle);
                                        Curtail.IdExp idExp = new Curtail.IdExp();
                                        if (curtailItem.containsKey("children")) {
                                            JSONObject item = (JSONObject) curtailItem.getJSONObject("children").getJSONArray("attached").get(0);
                                            // 限制物品Id名称
                                            String itemIdTitle = item.getString("title");
                                            itemIdChain.append(":").append(itemIdTitle);
                                            idExp.setId(itemIdTitle);
                                            if (item.containsKey("children")) {
                                                JSONObject itemIdExp = (JSONObject) item.getJSONObject("children").getJSONArray("attached").get(0);
                                                String itemExpTitle = itemIdExp.getString("title");
                                                itemIdChain.append(":").append(itemExpTitle);
                                                idExp.setExp(Long.parseLong(itemExpTitle));
                                            } else {
                                                itemIdChain.append(":").append("0");
                                                // 限制物品默认经验点
                                                idExp.setExp(0);
                                            }
                                        }
                                        attacks.add(idExp);
//                                                            System.out.println("     " + itemIdChain);
                                    }
                                    curtail.setAttacks(attacks);
                                }
                            } else if (curtailTitle.equalsIgnoreCase("增益")) {
                                if (jobCurtail.containsKey("children")) {
                                    JSONArray curtailItemObjects = jobCurtail.getJSONObject("children").getJSONArray("attached");
                                    List<Curtail.IdExp> buffs = new ArrayList<>();
                                    for (Object curtailItemObject : curtailItemObjects) {
                                        JSONObject curtailItem = (JSONObject) curtailItemObject;
                                        // 限制物品名称
                                        String itemTitle = curtailItem.getString("title");
                                        StringBuilder itemIdChain = new StringBuilder(itemTitle);
                                        Curtail.IdExp idExp = new Curtail.IdExp();
                                        if (curtailItem.containsKey("children")) {
                                            JSONObject item = (JSONObject) curtailItem.getJSONObject("children").getJSONArray("attached").get(0);
                                            // 限制物品Id名称
                                            String itemIdTitle = item.getString("title");
                                            itemIdChain.append(":").append(itemIdTitle);
                                            idExp.setId(itemIdTitle);
                                            if (item.containsKey("children")) {
                                                JSONObject itemIdExp = (JSONObject) item.getJSONObject("children").getJSONArray("attached").get(0);
                                                String itemExpTitle = itemIdExp.getString("title");
                                                itemIdChain.append(":").append(itemExpTitle);
                                                idExp.setExp(Long.parseLong(itemExpTitle));
                                            } else {
                                                itemIdChain.append(":").append("0");
                                                // 限制物品默认经验点
                                                idExp.setExp(0);
                                            }
                                        }
                                        buffs.add(idExp);
//                                                            System.out.println("     " + itemIdChain);
                                    }
                                    curtail.setBuffs(buffs);
                                }
                            } else if (curtailTitle.equalsIgnoreCase("最大经验点")) {
                                if (jobCurtail.containsKey("children")) {
                                    JSONObject maxExpObject = (JSONObject) jobCurtail.getJSONObject("children").getJSONArray("attached").get(0);
                                    level.setMaxExp(Integer.parseInt(maxExpObject.getString("title")));
                                }
                            }
                            curtails.add(curtail);
                        }
                    }
                    levels.add(level);
                }
            }
        }
        System.out.println(1);
        List<String> lines = new ArrayList<>();

        lines.add("");
        lines.add("-- Automatically create data tables");
        lines.add("");
        insertTableSql(lines);
        lines.add("");
        lines.add("-- Inject all occupational data");
        lines.add("");
        // 生成所有职业相关insert的sql语句
        for (Job job : jobs) {
            String playersString = JSONArray.toJSONString(job.getPlayers());
            String line = "INSERT INTO jobs (id, name, title, players) VALUES(" + job.getId() + ", '" + job.getName() + "', '" + job.getTitle() + "', '" + playersString + "');";
            lines.add(line);
        }
        lines.add("");
        lines.add("-- Inject all occupational level data");
        lines.add("");
        // 生成所有职业等级相关insert的sql语句
        int i = 0;
        for (Level level : levels) {
            i++;
            JSONArray objects = new JSONArray();
            for (Level.LevelPlayer player : level.getPlayers()) {
                objects.add(player.getName() + ":" + player.getJobId() + ":" + player.getExp());
            }
            String playersString = objects.toJSONString();
            long nextId = getLevelNextId(levels, i);
            String line = "INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(" +
                    level.getId() + ", '" +
                    level.getName() + "', '" +
                    level.getTitle() + "', '" +
                    playersString + "', " +
                    level.getMaxExp() + ", " +
                    nextId + ");";
            lines.add(line);
        }
        lines.add("");
        lines.add("-- Inject all occupational restriction data");
        lines.add("");
        // 生成所有职业限制相关insert的sql语句
        int c = 0;
        for (Curtail curtail : curtails) {
            String tools = parseListCurtail(Curtail.Type.TOOL, c, curtails);
            String foods = parseListCurtail(Curtail.Type.FOOD, c, curtails);
            String places = parseListCurtail(Curtail.Type.PLACE, c, curtails);
            String destructs = parseListCurtail(Curtail.Type.DESTRUCT, c, curtails);
            String recipes = parseListCurtail(Curtail.Type.RECIPE, c, curtails);
            String interacts = parseListCurtail(Curtail.Type.INTERACT, c, curtails);
            String attacks = parseListCurtail(Curtail.Type.ATTACK, c, curtails);
            String buffs = parseListCurtail(Curtail.Type.BUFF, c, curtails);
            String line = "INSERT INTO curtails (id, name, jobId, levelId, tools, foods, places, destructs, recipes, interacts, attacks, buffs) VALUES(" +
                    curtail.getId() + ", '" + curtail.getTitle() + "', " + curtail.getJobId() + ", " + curtail.getLevelId() + ", '" +
                    tools + "', '" + foods + "', '" + places + "', '" + destructs + "', '" + recipes + "', '" + interacts + "', '" +
                    attacks + "', '" + buffs + "');";
            lines.add(line);
            c++;
        }
        if (commandLine.hasOption("a")) {
            if (outputFile.exists()) {
                FileUtil.appendLines(lines, outputFile, StandardCharsets.UTF_8);
            }
        } else {
            FileUtil.writeLines(lines, outputFile, StandardCharsets.UTF_8);
        }
    }

    public static void showHelp(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        helpFormatter.printHelp(printWriter, HelpFormatter.DEFAULT_WIDTH, "jsftool -h", null, options, HelpFormatter.DEFAULT_LEFT_PAD, HelpFormatter.DEFAULT_DESC_PAD, null);
        printWriter.flush();
        String help = outputStream.toString(StandardCharsets.UTF_8);
        printWriter.close();
        System.out.println(help);
    }

    public static void insertTableSql(List<String> lines) {
        String curtail =
                """
                        CREATE TABLE IF NOT EXISTS `curtails` (
                          `id` bigint NOT NULL,
                          `name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                          `jobId` bigint DEFAULT NULL,
                          `levelId` bigint DEFAULT NULL,
                          `tools` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
                          `foods` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
                          `places` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
                          `destructs` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
                          `recipes` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
                          `interacts` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
                          `attacks` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
                          `buffs` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
                          PRIMARY KEY (`id`) USING BTREE
                        ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;""";
        String job =
                """
                        CREATE TABLE IF NOT EXISTS `jobs` (
                          `id` bigint NOT NULL,
                          `name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                          `title` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                          `players` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                          PRIMARY KEY (`id`) USING BTREE
                        ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;""";
        String level =
                """
                        CREATE TABLE IF NOT EXISTS `levels` (
                          `id` bigint NOT NULL,
                          `name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                          `title` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                          `players` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
                          `maxExp` bigint DEFAULT NULL,
                          `nextId` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
                        ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;""";
        lines.add(job);
        lines.add(level);
        lines.add(curtail);
    }

    public static long getLevelNextId(List<Level> levels, int index) {
        if (levels.size() <= index) {
            return -1;
        }
        return levels.get(index).getId();
    }

    public static String parseListCurtail(Curtail.Type type, int index, List<Curtail> curtails) {
        JSONArray objects = new JSONArray();
        for (int i = index; i >= 0; i--) {
            Curtail curtail = curtails.get(i);
            switch (type) {
                case TOOL:
                    for (Curtail.IdExp idExp : curtail.getTools()) {
                        objects.add(idExp.getId() + ":" + idExp.getExp());
                    }
                    break;
                case FOOD:
                    for (Curtail.IdExp idExp : curtail.getFoods()) {
                        objects.add(idExp.getId() + ":" + idExp.getExp());
                    }
                    break;
                case PLACE:
                    for (Curtail.IdExp idExp : curtail.getPlaces()) {
                        objects.add(idExp.getId() + ":" + idExp.getExp());
                    }
                    break;
                case DESTRUCT:
                    for (Curtail.IdExp idExp : curtail.getDestructs()) {
                        objects.add(idExp.getId() + ":" + idExp.getExp());
                    }
                    break;
                case RECIPE:
                    for (Curtail.IdExp idExp : curtail.getRecipes()) {
                        objects.add(idExp.getId() + ":" + idExp.getExp());
                    }
                    break;
                case INTERACT:
                    for (Curtail.IdExp idExp : curtail.getInteracts()) {
                        objects.add(idExp.getId() + ":" + idExp.getExp());
                    }
                    break;
                case ATTACK:
                    for (Curtail.IdExp idExp : curtail.getAttacks()) {
                        objects.add(idExp.getId() + ":" + idExp.getExp());
                    }
                    break;
                case BUFF:
                    for (Curtail.IdExp idExp : curtail.getBuffs()) {
                        objects.add(idExp.getId() + ":" + idExp.getExp());
                    }
                    break;
            }
        }
        return objects.toJSONString();
    }
}