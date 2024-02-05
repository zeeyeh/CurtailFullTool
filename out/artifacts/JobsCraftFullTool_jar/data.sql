
-- Automatically create data tables

CREATE TABLE IF NOT EXISTS `jobs` (
  `id` bigint NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `title` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `players` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
CREATE TABLE IF NOT EXISTS `levels` (
  `id` bigint NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `players` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `maxExp` bigint DEFAULT NULL,
  `nextId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- Inject all occupational data

INSERT INTO jobs (id, name, title, players) VALUES(1754458639760441344, 'qubianfu', '鑺卞尃', '[]');
INSERT INTO jobs (id, name, title, players) VALUES(1754458641085841411, 'lianㄥfu', '鏈ㄥ尃', '[]');
INSERT INTO jobs (id, name, title, players) VALUES(1754458641123590149, 'meiqi湪chen�', '浼愭湪宸�', '[]');
INSERT INTO jobs (id, name, title, players) VALUES(1754458641144561666, 'yukuafa', '鐭垮伐', '[]');
INSERT INTO jobs (id, name, title, players) VALUES(1754458641173921793, 'lizan煶chen�', '娌欑煶鍖�', '[]');
INSERT INTO jobs (id, name, title, players) VALUES(1754458641199087627, 'congcanggan', '鍐滄皯', '[]');
INSERT INTO jobs (id, name, title, players) VALUES(1754458641220059138, 'jianфgan', '鐗ф皯', '[]');

-- Inject all occupational level data

INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641010343936, 'qubianfuying﹀mian', '鑺卞尃瀛﹀緬', '[]', 0, 1754458641052286978);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641052286978, 'qubianfuroujihai', '鑺卞尃鍒濈骇', '[]', 0, 1754458641060675587);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641060675587, 'qubianfutanxiyin', '鑺卞尃鐔熺粌', '[]', 0, 1754458641064869893);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641064869893, 'qubianfujuanhai', '鑺卞尃涓骇', '[]', 0, 1754458641073258496);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641073258496, 'qubianfubijuanfan', '鑺卞尃璧勬繁', '[]', 0, 1754458641077452801);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641077452801, 'qubianfuzhazhenhai', '鑺卞尃楂樼骇', '[]', 0, 1754458641085841412);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641085841412, 'lianㄥfuying﹀mian', '鏈ㄥ尃瀛﹀緬', '[]', 0, 1754458641094230018);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641094230018, 'lianㄥfuroujihai', '鏈ㄥ尃鍒濈骇', '[]', 0, 1754458641102618625);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641102618625, 'lianㄥfutanxiyin', '鏈ㄥ尃鐔熺粌', '[]', 0, 1754458641106812933);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641106812933, 'lianㄥfujuanhai', '鏈ㄥ尃涓骇', '[]', 0, 1754458641115201540);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641115201540, 'lianㄥfubijuanfan', '鏈ㄥ尃璧勬繁', '[]', 0, 1754458641119395847);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641119395847, 'lianㄥfuzhazhenhai', '鏈ㄥ尃楂樼骇', '[]', 0, 1754458641123590150);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641123590150, 'meiqi湪chen�ying﹀mian', '浼愭湪宸�瀛﹀緬', '[]', 0, 1754458641127784451);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641127784451, 'meiqi湪chen�roujihai', '浼愭湪宸�鍒濈骇', '[]', 0, 1754458641127784461);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641127784461, 'meiqi湪chen�tanxiyin', '浼愭湪宸�鐔熺粌', '[]', 0, 1754458641136173056);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641136173056, 'meiqi湪chen�juanhai', '浼愭湪宸�涓骇', '[]', 0, 1754458641136173066);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641136173066, 'meiqi湪chen�bijuanfan', '浼愭湪宸�璧勬繁', '[]', 0, 1754458641140367367);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641140367367, 'meiqi湪chen�zhazhenhai', '浼愭湪宸�楂樼骇', '[]', 0, 1754458641144561667);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641144561667, 'yukuafaying﹀mian', '鐭垮伐瀛﹀緬', '[]', 0, 1754458641144561677);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641144561677, 'yukuafaroujihai', '鐭垮伐鍒濈骇', '[]', 0, 1754458641148755975);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641148755975, 'yukuafatanxiyin', '鐭垮伐鐔熺粌', '[]', 0, 1754458641152950279);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641152950279, 'yukuafajuanhai', '鐭垮伐涓骇', '[]', 0, 1754458641157144584);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641157144584, 'yukuafabijuanfan', '鐭垮伐璧勬繁', '[]', 0, 1754458641161338888);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641161338888, 'yukuafazhazhenhai', '鐭垮伐楂樼骇', '[]', 0, 1754458641173921794);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641173921794, 'lizan煶chen�ying﹀mian', '娌欑煶鍖�瀛﹀緬', '[]', 0, 1754458641178116104);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641178116104, 'lizan煶chen�roujihai', '娌欑煶鍖�鍒濈骇', '[]', 0, 1754458641182310406);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641182310406, 'lizan煶chen�tanxiyin', '娌欑煶鍖�鐔熺粌', '[]', 0, 1754458641190699008);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641190699008, 'lizan煶chen�juanhai', '娌欑煶鍖�涓骇', '[]', 0, 1754458641194893312);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641194893312, 'lizan煶chen�bijuanfan', '娌欑煶鍖�璧勬繁', '[]', 0, 1754458641199087618);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641199087618, 'lizan煶chen�zhazhenhai', '娌欑煶鍖�楂樼骇', '[]', 0, 1754458641199087628);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641199087628, 'congcangganying﹀mian', '鍐滄皯瀛﹀緬', '[]', 0, 1754458641203281926);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641203281926, 'congcangganroujihai', '鍐滄皯鍒濈骇', '[]', 0, 1754458641207476230);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641207476230, 'congcanggantanxiyin', '鍐滄皯鐔熺粌', '[]', 0, 1754458641211670530);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641211670530, 'congcangganjuanhai', '鍐滄皯涓骇', '[]', 0, 1754458641215864832);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641215864832, 'congcangganbijuanfan', '鍐滄皯璧勬繁', '[]', 0, 1754458641215864841);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641215864841, 'congcangganzhazhenhai', '鍐滄皯楂樼骇', '[]', 0, 1754458641220059139);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641220059139, 'jianфganying﹀mian', '鐗ф皯瀛﹀緬', '[]', 0, 1754458641224253440);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641224253440, 'jianфganroujihai', '鐗ф皯鍒濈骇', '[]', 0, 1754458641224253449);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641224253449, 'jianфgantanxiyin', '鐗ф皯鐔熺粌', '[]', 0, 1754458641228447745);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641228447745, 'jianфganjuanhai', '鐗ф皯涓骇', '[]', 0, 1754458641228447754);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641228447754, 'jianфganbijuanfan', '鐗ф皯璧勬繁', '[]', 0, 1754458641232642052);
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(1754458641232642052, 'jianфganzhazhenhai', '鐗ф皯楂樼骇', '[]', 0, -1);

-- Inject all occupational restriction data
