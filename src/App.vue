<script setup>
import {useJobStore} from "@/store/modules/JobListStore";
import {onMounted, reactive, ref} from "vue";
import {setupSplit} from "@/utils/common.js";
import {message} from "ant-design-vue";
import {useRouter} from "vue-router";
import axios from "axios";
import {useCurtailStore} from "@/store/modules/CurtailListStore.js";
import {useLevelStore} from "@/store/modules/LevelListStore.js";

let currentYear = new Date().getFullYear();
const router = useRouter();
const data = reactive({
  activeKey: '1',
  jobs: [],
  levels: [],
  curtails: [],
  menus: [
    {
      path: '/add/job',
      title: '创建职业',
      name: '创建一份职业'
    },
    {
      path: '/add/level',
      title: '创建等级',
      name: '为职业创建一份等级'
    },
    {
      path: '/add/curtail',
      title: '创建限制',
      name: '为特定的职业的特定等级创建一个限制'
    }
  ],
})
const asidePanel = ref(null);
const resizeDivider = ref(null);
const jobStore = useJobStore();
const levelStore = useLevelStore();
const curtailStore = useCurtailStore();
const fileCodes = document.getElementById("fileCodes");
const sqlCodes = document.getElementById("sqlCodes");
onMounted(() => {
  // jobStore.addJob("1", "ceshi", "Ceshi", ['lingqi'])
  // jobStore.addJob("2", "ceshi1", "Ceshi1", [])
  // levelStore.addLevel("1", "yiji", "YiJi", 10, 2, [])
  // levelStore.addLevel("2", "erji", "Erji", 10, -1, [])
  // curtailStore.addCurtail("1", "ceshiYiji", "1", "1", [], [], [], [], [], [], [], [])
  // curtailStore.addCurtail("2", "ceshiErji", "1", "2", [], [], [], [], [], [], [], [])
  data.jobs = jobStore.getJobs
  data.levels = levelStore.getLevels
  data.curtails = curtailStore.getCurtails
  setupSplit(asidePanel, resizeDivider)
})
const deleteJob = (jobInfo) => {
  jobStore.removeJobById(jobInfo.id);
  data.jobs = jobStore.getJobs;
  message.success(`职业${jobInfo}删除成功!`)
}
const changeView = (e) => {
  let el = e.target.parentNode;
  router.push(el.dataset.href)
}

let showFileCodes = ref(false)
let showSqlCodes = ref(false)
let jobCodes = [];
let levelCodes = [];
let curtailsCodes = [];
let sqlJobCodes = '';
let sqlLevelCodes = '';
let sqlCurtailsCodes = '';
let codeTabIndex = ref('1')
const contextMenuClick = (e) => {
  let key = e.target.parentNode.dataset.index
  jobCodes = [];
  levelCodes = [];
  curtailsCodes = [];
  sqlJobCodes = '';
  sqlLevelCodes = '';
  sqlCurtailsCodes = '';
  console.log(key, key === '2')
  if (key === '1') {
    exportZip();
  } else if (key === '2') {
    exportSql();
  }
}

const arrToJsonArr = (objArr) => {
  let arr = [];
  for (let i = 0; i < objArr.length.length; i++) {
    arr.push(`${objArr[i].name}:${objArr[i].exp}`)
  }
  return JSON.stringify(arr);
}

const buffArrToJsonArr = (objArr) => {
  let arr = [];
  for (let i = 0; i < objArr.length.length; i++) {
    arr.push(`${objArr[i].name}:${objArr[i].value}`)
  }
  return JSON.stringify(arr);
}

const exportZip = () => {
  let jobs = data.jobs;
  for (let i = 0; i < jobs.length; i++) {
    let job = jobs[i];
    let players = JSON.stringify(job.players);
    let code = `
job:
  players: ${players}
  name: ${job.name}
  id: ${job.id}
  title: ${job.title}
    `
    jobCodes.push({
      code: code,
      name: job.name + ".yml"
    })
  }
  let levels = data.levels;
  for (let i = 0; i < levels.length; i++) {
    let level = levels[i];
    let players = JSON.stringify(level.players);
    let code = `
level:
  nextId: ${level.nextId}
  players: ${players}
  maxExp: ${level.maxExp}
  name: ${level.name}
  id: ${level.id}
  title: ${level.title}
    `
    levelCodes.push({
      code: code,
      name: level.name + ".yml"
    })
  }
  let curtails = data.curtails;
  console.log(curtails)
  for (let i = 0; i < curtails.length; i++) {
    let curtail = curtails[i];
    let tools = arrToJsonArr(curtail.tools);
    let foods = arrToJsonArr(curtail.foods);
    let places = arrToJsonArr(curtail.places);
    let destructs = arrToJsonArr(curtail.destructs);
    let recipes = arrToJsonArr(curtail.recipes);
    let interacts = arrToJsonArr(curtail.interacts);
    let attacks = arrToJsonArr(curtail.attacks);
    let buffs = buffArrToJsonArr(curtail.buffs);
    let code = `
curtail:
  id: ${curtail.id}
  name: ${curtail.name}
  jobId: ${curtail.jobId}
  levelId: ${curtail.levelId}
  tools: ${tools}
  foods: ${foods}
  places: ${places}
  destructs: ${destructs}
  recipes: ${recipes}
  interacts: ${interacts}
  attacks: ${attacks}
  buffs: ${buffs}
    `
    curtailsCodes.push({
      code: code,
      name: curtail.name + ".yml"
    })
  }
  showFileCodes.value = true;
}
// INSERT INTO jobscraft.jobs
// (id, name, title, players)
// VALUES(1743158137893466112, 'ceshi', 'ceshi', '["bmlingqi"]');
const exportSql = () => {

  let jobs = data.jobs;
  for (let i = 0; i < jobs.length; i++) {
    let job = jobs[i];
    let players = JSON.stringify(job.players);
    let code = `
INSERT INTO jobs (id, name, title, players) VALUES (${job.id}, '${job.name}', '${job.title}', '${players}');
    `
    // jobCodes.join("\n")
    // jobCodes.join(code)
    jobCodes.push({
      code: code,
      name: job.name + ".sql"
    })
  }
  let levels = data.levels;
  for (let i = 0; i < levels.length; i++) {
    let level = levels[i];
    // let players = JSON.stringify(level.players);
    let code = `
INSERT INTO levels (id, name, title, players, maxExp, nextId) VALUES(${level.id}, '${level.name}', '${level.title}', '[]', ${level.maxExp}, ${level.nextId});
    `
    // levelCodes = levelCodes + "\n" + code
    levelCodes.join({
      code: code,
      name: level.name + ".sql"
    })
  }
  let curtails = data.curtails;
  for (let i = 0; i < curtails.length; i++) {
    let curtail = curtails[i];
    let tools = arrToJsonArr(curtail.tools);
    let foods = arrToJsonArr(curtail.foods);
    let places = arrToJsonArr(curtail.places);
    let destructs = arrToJsonArr(curtail.destructs);
    let recipes = arrToJsonArr(curtail.recipes);
    let interacts = arrToJsonArr(curtail.interacts);
    let attacks = arrToJsonArr(curtail.attacks);
    let buffs = buffArrToJsonArr(curtail.buffs);
    let code = `
INSERT INTO curtails (id, name, jobId, levelId, tools, foods, places, destructs, recipes, interacts, attacks, buffs) VALUES(${curtail.id}, '${curtail.name}', ${curtail.jobId}, ${curtail.levelId}, '${tools}', '${foods}', '${places}', '${destructs}', '${recipes}', '${interacts}', '${attacks}', '${buffs}');
    `
    // INSERT INTO curtails (id, name, jobId, levelId, tools, foods, places, destructs, recipes, interacts, attacks, buffs) VALUES(${level.id}, '${level.id}', ${level.id}, ${level.id}, '["minecraft:iron_axe:1","minecraft:iron_hoe:1"]', '["minecraft:apple:1"]', '["minecraft:stone:1"]', '["minecraft:grass_block:1"]', '["minecraft:oak_planks:1"]', '["minecraft:spruce_door:1"]', '["minecraft:zombie:1"]', '["minecraft:iron_axe:[''物理伤害: 50'']"]');
    curtailsCodes.push({
      code: code,
      name: curtail.name + ".sql"
    })
  }
  showSqlCodes.value = true;
}
</script>

<template>
  <a-config-provider>
    <a-layout class="root-layout">
      <a-layout-header class="header-layout">
        <div class="header-layout-inner">
          <div class="logo-box flex justify-between items-center">
            JobsCraft generate tool
          </div>
        </div>
      </a-layout-header>
      <a-layout-content class="main-layout">
        <div class="main-layout-inner flex flex-row">
          <div class="job-list" ref="asidePanel">
            <a-dropdown :trigger="['contextmenu']">
              <a-tabs class="m-1" v-model:active-key="data.activeKey">
              <a-tab-pane key="1" tab="首页">
                <a-list :data-source="data.menus">
                  <template #renderItem="{ item }">
                    <a-list-item>
                      <a-list-item-meta :description="item.name">
                        <template #title>
                          <span :data-href="item.path" @click="changeView" class="cursor-pointer"><b>{{ item.title }}</b></span>
                        </template>
                      </a-list-item-meta>
                    </a-list-item>
                  </template>
                </a-list>
              </a-tab-pane>
              <a-tab-pane key="2" tab="职业">
                <a-list :data-source="data.jobs" v-if="data.jobs.length > 0">
                  <template #renderItem="{ item }">
                    <a-list-item>
                      <template #actions>
                        <a-popconfirm title="真的要删除这个职业吗?" ok-text="确定" cancel-text="取消" @confirm="deleteJob(item)">
                          <a-button type="link" danger>删除</a-button>
                        </a-popconfirm>
                      </template>
                      <a-skeleton :title="false" :loading="!!item.loading">
                        <a-list-item-meta :description="item.title">
                          <template #title>
                            <span><b>{{ item.name }}</b></span>
                          </template>
                        </a-list-item-meta>
                      </a-skeleton>
                    </a-list-item>
                  </template>
                </a-list>
                <AEmpty v-else description="暂无创建职业"/>
              </a-tab-pane>
              <a-tab-pane key="3" tab="等级">
                <a-list :data-source="data.levels" v-if="data.levels.length > 0">
                  <template #renderItem="{ item }">
                    <a-list-item>
                      <template #actions>
                        <a-popconfirm title="真的要删除这个等级吗?" ok-text="确定" cancel-text="取消" @confirm="deleteLevel(item)">
                          <a-button type="link" danger>删除</a-button>
                        </a-popconfirm>
                      </template>
                      <a-skeleton :title="false" :loading="!!item.loading">
                        <a-list-item-meta :description="item.title">
                          <template #title>
                            <span><b>{{ item.name }}</b></span>
                          </template>
                        </a-list-item-meta>
                      </a-skeleton>
                    </a-list-item>
                  </template>
                </a-list>
                <AEmpty v-else description="暂无创建等级"/>
              </a-tab-pane>
              <a-tab-pane key="4" tab="限制">
                <a-list :data-source="data.curtails" v-if="data.curtails.length > 0">
                  <template #renderItem="{ item }">
                    <a-list-item>
                      <template #actions>
                        <a-popconfirm title="真的要删除这个限制吗?" ok-text="确定" cancel-text="取消" @confirm="deleteCurtail(item)">
                          <a-button type="link" danger>删除</a-button>
                        </a-popconfirm>
                      </template>
                      <a-skeleton :title="false" :loading="!!item.loading">
                        <a-list-item-meta :description="item.name">
                          <template #title>
                            <span><b>{{ item.name }}</b></span>
                          </template>
                        </a-list-item-meta>
                      </a-skeleton>
                    </a-list-item>
                  </template>
                </a-list>
                <AEmpty v-else description="暂无创建限制"/>
              </a-tab-pane>
            </a-tabs>
              <template #overlay>
                <a-menu>
                  <a-menu-sub-menu title="导出" @click="contextMenuClick">
                    <a-menu-item data-index="1">导出为压缩包文件</a-menu-item>
                    <a-menu-item data-index="2">导出为sql文件</a-menu-item>
                  </a-menu-sub-menu>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
          <div class="job-divider" ref="resizeDivider"/>
          <div class="job-create-form flex-1 flex flex-col">
            <div class="job-create-form-inner flex-1 m-4">
              <router-view></router-view>
            </div>
            <div class="footer-layout copyright-box flex justify-center items-center text-center">
              Copyright @copy; 2023-{{ currentYear }}&nbsp;<a target="_blank"
                                                              href="https://github.com/zeeyeh">LingQi</a>&nbsp;All
              rights reversed.
            </div>
          </div>
        </div>
      </a-layout-content>
    </a-layout>
    <template v-if="showFileCodes">
      <div id="fileCodes">
        <a-modal v-model:open="showFileCodes" title="文件保存类型配置集" ok-text="确定" cancel-text="取消">
          <a-tabs v-model:activeKey="codeTabIndex">
            <a-tab-pane key="1" tab="职业">
              <a-collapse>
                <a-collapse-panel v-for="(code, index) in jobCodes" :key="index" :header="code.name">
                  <highlightjs language="json" :autodetect="false" :code="code.code"></highlightjs>
                </a-collapse-panel>
              </a-collapse>
            </a-tab-pane>
            <a-tab-pane key="2" tab="等级">
              <a-collapse>
                <a-collapse-panel v-for="(code, index) in levelCodes" :key="index" :header="code.name">
                  <highlightjs language="json" :autodetect="false" :code="code.code"></highlightjs>
                </a-collapse-panel>
              </a-collapse>
            </a-tab-pane>
            <a-tab-pane key="3" tab="限制">
              <a-collapse>
                <a-collapse-panel v-for="(code, index) in curtailsCodes" :key="index" :header="code.name">
                  <highlightjs language="json" :autodetect="false" :code="code.code"></highlightjs>
                </a-collapse-panel>
              </a-collapse>
            </a-tab-pane>
          </a-tabs>
        </a-modal>
      </div>
    </template>
    <template v-if="showSqlCodes">
      <div id="sqlCodes">
        <a-modal v-model:open="showSqlCodes" title="数据库保存类型配置集" ok-text="确定" cancel-text="取消">
          <a-tabs v-model:activeKey="codeTabIndex">
            <a-tab-pane key="1" tab="职业">
              <a-collapse>
                <a-collapse-panel v-for="(code, index) in jobCodes" :key="index" :header="code.name">
                  <highlightjs language="json" :autodetect="false" :code="code.code"></highlightjs>
                </a-collapse-panel>
<!--                <a-collapse-panel header="Sql">-->
<!--                  <highlightjs language="json" :autodetect="false" :code="jobCodes"></highlightjs>-->
<!--                </a-collapse-panel>-->
              </a-collapse>
            </a-tab-pane>
            <a-tab-pane key="2" tab="等级">
              <a-collapse>
                <a-collapse-panel v-for="(code, index) in levelCodes" :key="index" :header="code.name">
                  <highlightjs language="json" :autodetect="false" :code="code.code"></highlightjs>
                </a-collapse-panel>
              </a-collapse>
            </a-tab-pane>
            <a-tab-pane key="3" tab="限制">
              <a-collapse>
                <a-collapse-panel v-for="(code, index) in curtailsCodes" :key="index" :header="code.name">
                  <highlightjs language="json" :autodetect="false" :code="code.code"></highlightjs>
                </a-collapse-panel>
              </a-collapse>
            </a-tab-pane>
          </a-tabs>
        </a-modal>
      </div>
    </template>
  </a-config-provider>
</template>

<style lang="scss">
@import "assets/base";

.root-layout {
  height: 100%;

  .header-layout {
    height: $header-height;
    max-height: $header-height;
    background-color: rgb(255, 255, 255);
    box-shadow: 3px 3px 3px 0 rgba(0, 0, 0, 0.05);

    .header-layout-inner {
      height: 100%;
      font-size: 20px;
      font-weight: bold;
      color: $theme-color;
      .logo-box {
        height: $header-height;
        max-height: $header-height;
      }
    }
  }

  .main-layout {
    .main-layout-inner {
      height: 100%;

      .job-list {
        min-width: 220px;
        width: 220px;
        background: $background-color;
      }

      .job-divider {
        cursor: col-resize;
        height: 100%;
        background: $border-color;
        box-shadow: $box-shadow;
        width: 2px;
      }
    }
  }

  .footer-layout {
    height: 50px;
    min-height: 50px;
    max-height: 50px;
  }
}
</style>
