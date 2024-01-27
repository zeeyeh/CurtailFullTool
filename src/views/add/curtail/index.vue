<script setup>
import {onMounted, reactive, ref, toRefs} from "vue";
import ListForm from "@/components/ListForm.vue";
import {useJobStore} from "@/store/modules/JobListStore.js";
import {message} from "ant-design-vue";
import SnowflakeId from "snowflake-id";
import {useLevelStore} from "@/store/modules/LevelListStore.js";

const snowflake = new SnowflakeId()

const data = reactive({
  id: snowflake.generate(),
  name: '',
  jobId: -1,
  levelId: -1,
  tools: [],
  foods: [],
  places: [],
  destructs: [],
  recipes: [],
  interacts: [],
  attacks: [],
  buffs: [],
  ids: []
})
const moduleStatus = reactive({
  tool: false,
  food: false,
  place: false,
  destruct: false,
  recipe: false,
  interact: false,
  attack: false,
  buff: false
})
const jobStore = useJobStore()
const levelStore = useLevelStore();
const basicInfo = reactive({
  jobs: [],
  levels: []
})
// let jobs = ref('');
// let levels = ref('');
onMounted(() => {
  basicInfo.jobs = jobStore.getJobs;
  basicInfo.levels = levelStore.getLevels;
  console.log(basicInfo.jobs.value)
  console.log(typeof basicInfo.jobs.value)
})
const addCurtail = () => {
  jobStore.addJob(...toRefs(data))
  message.success("限制添加成功")
  resetForm();
}
const resetForm = (showTip = false) => {
  data.id = snowflake.generate()
  data.name = ''
  data.jobId = -1
  data.levelId = -1
  data.tools = []
  data.foods = []
  data.places = []
  data.destructs = []
  data.recipes = []
  data.interacts = []
  data.attacks = []
  data.buffs = []
  data.ids = []
  if (showTip) {
    message.success("数据清空成功")
  }
}
const listForm = reactive({
  name: '',
  exp: 0,
})
const resetListForm = () => {
  listForm.name = '';
  listForm.exp = 0;
}
const buffForm = reactive({
  name: '',
  value: null
})
const contains = (key, list) => {
  for (let item of list) {
    if (item.name === key) {
      return true;
    }
  }
  return false;
}
const addTool = () => {
  if (contains(listForm.name, data.tools)) {
    return;
  }
  data.tools.push({
    name: listForm.name,
    exp: listForm.exp
  })
  resetListForm()
}
const addFood = () => {
  if (contains(listForm.name, data.foods)) {
    return;
  }
  data.foods.push({
    name: listForm.name,
    exp: listForm.exp
  })
  resetListForm()
}
const addPlace = () => {
  if (contains(listForm.name, data.places)) {
    return;
  }
  data.places.push({
    name: listForm.name,
    exp: listForm.exp
  })
  resetListForm()
}
const addDestruct = () => {
  if (contains(listForm.name, data.destructs)) {
    return;
  }
  data.destructs.push({
    name: listForm.name,
    exp: listForm.exp
  })
  resetListForm()
}
const addRecipe = () => {
  if (contains(listForm.name, data.recipes)) {
    return;
  }
  data.recipes.push({
    name: listForm.name,
    exp: listForm.exp
  })
  resetListForm()
}
const addInteract = () => {
  if (contains(listForm.name, data.interacts)) {
    return;
  }
  data.interacts.push({
    name: listForm.name,
    exp: listForm.exp
  })
  resetListForm()
}
const addAttack = () => {
  if (contains(listForm.name, data.attacks)) {
    return;
  }
  data.attacks.push({
    name: listForm.name,
    exp: listForm.exp
  })
  resetListForm()
}
const addBuff = () => {
  if (contains(listForm.name, data.buffs)) {
    return;
  }
  data.buffs.push({
    name: listForm.name,
    value: listForm.value
  })
  resetListForm()
}
</script>

<template>
  <a-card title="添加限制">
    <a-form class="add-job-form" :label-col="{
    style: {
      width: '150px'
    }
  }">
      <a-form-item label="限制唯一Id" name="id" class="add-job-form-item">
        <a-input-number v-model:value="data.id" placeholder="请输入限制唯一Id" class="add-job-form-item-id"/>
      </a-form-item>
      <a-form-item label="限制名称" name="name" class="add-job-form-item">
        <a-input v-model:value="data.name" placeholder="请输入限制名称" class="add-job-form-item-id"/>
      </a-form-item>
      <a-form-item label="目标职业" name="jobId" class="add-job-form-item">
        <a-select placeholder="请选择目标职业">
          <a-select-option v-for="(item, index) in basicInfo.jobs" :key="index" :value="item.name">{{ item.title }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="目标等级" name="levelId" class="add-job-form-item">
        <a-select placeholder="请选择目标等级">
          <a-select-option v-for="(item, index) in basicInfo.jobs" :key="index" :value="item.name">{{ item.title }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="可使用的工具" name="tools" class="add-job-form-item">
        <a-row :gutter="10">
          <a-col :span="20">
            <a-select placeholder="请添加工具">
              <a-select-option v-for="(item, index) in data.tools" :key="index" :value="item.name">{{
                  item.name
                }}:{{ item.exp }}
              </a-select-option>
            </a-select>
          </a-col>
          <a-col :span="4">
            <a-button @click="moduleStatus.tool = true">添加工具</a-button>
            <a-modal v-model:open="moduleStatus.tool" title="添加可使用的工具" @ok="addTool" ok-text="确定"
                     cancel-text="取消">
              <ListForm v-model:id="listForm.name" v-model:exp="listForm.exp"/>
            </a-modal>
          </a-col>
        </a-row>
      </a-form-item>
      <a-form-item label="可食用的食物" name="foods" class="add-job-form-item">
        <a-row :gutter="10">
          <a-col :span="20">
            <a-select placeholder="请添加食物">
              <a-select-option v-for="(item, index) in data.foods" :key="index" :value="item.name">{{
                  item.name
                }}:{{ item.exp }}
              </a-select-option>
            </a-select>
          </a-col>
          <a-col :span="4">
            <a-button @click="moduleStatus.food = true">添加食物</a-button>
            <a-modal v-model:open="moduleStatus.food" title="添加可食用的食物" @ok="addFood" ok-text="确定"
                     cancel-text="取消">
              <ListForm v-model:id="listForm.name" v-model:exp="listForm.exp"/>
            </a-modal>
          </a-col>
        </a-row>
      </a-form-item>
      <a-form-item label="可放置的方块" name="places" class="add-job-form-item">
        <a-row :gutter="10">
          <a-col :span="20">
            <a-select placeholder="请添加方块">
              <a-select-option v-for="(item, index) in data.places" :key="index" :value="item.name">{{
                  item.name
                }}:{{ item.exp }}
              </a-select-option>
            </a-select>
          </a-col>
          <a-col :span="4">
            <a-button @click="moduleStatus.place = true">添加方块</a-button>
            <a-modal v-model:open="moduleStatus.place" title="添加可放置的方块" @ok="addPlace" ok-text="确定"
                     cancel-text="取消">
              <ListForm v-model:id="listForm.name" v-model:exp="listForm.exp"/>
            </a-modal>
          </a-col>
        </a-row>
      </a-form-item>
      <a-form-item label="可破坏的方块" name="destructs" class="add-job-form-item">
        <a-row :gutter="10">
          <a-col :span="20">
            <a-select placeholder="请添加方块">
              <a-select-option v-for="(item, index) in data.destructs" :key="index" :value="item.name">{{
                  item.name
                }}:{{ item.exp }}
              </a-select-option>
            </a-select>
          </a-col>
          <a-col :span="4">
            <a-button @click="moduleStatus.destruct = true">添加方块</a-button>
            <a-modal v-model:open="moduleStatus.destruct" title="添加可破坏的方块" @ok="addDestruct" ok-text="确定"
                     cancel-text="取消">
              <ListForm v-model:id="listForm.name" v-model:exp="listForm.exp"/>
            </a-modal>
          </a-col>
        </a-row>
      </a-form-item>
      <a-form-item label="可合成的配方" name="recipes" class="add-job-form-item">
        <a-row :gutter="10">
          <a-col :span="20">
            <a-select placeholder="请添加配方">
              <a-select-option v-for="(item, index) in data.recipes" :key="index" :value="item.name">{{
                  item.name
                }}:{{ item.exp }}
              </a-select-option>
            </a-select>
          </a-col>
          <a-col :span="4">
            <a-button @click="moduleStatus.recipe = true">添加配方</a-button>
            <a-modal v-model:open="moduleStatus.recipe" title="添加可合成的配方" @ok="addRecipe" ok-text="确定"
                     cancel-text="取消">
              <ListForm v-model:id="listForm.name" v-model:exp="listForm.exp"/>
            </a-modal>
          </a-col>
        </a-row>
      </a-form-item>
      <a-form-item label="可交互的实体" name="interacts" class="add-job-form-item">
        <a-row :gutter="10">
          <a-col :span="20">
            <a-select placeholder="请添加实体">
              <a-select-option v-for="(item, index) in data.interacts" :key="index" :value="item.name">{{
                  item.name
                }}:{{ item.exp }}
              </a-select-option>
            </a-select>
          </a-col>
          <a-col :span="4">
            <a-button @click="moduleStatus.interact = true">添加实体</a-button>
            <a-modal v-model:open="moduleStatus.interact" title="添加可交互的实体" @ok="addInteract" ok-text="确定"
                     cancel-text="取消">
              <ListForm v-model:id="listForm.name" v-model:exp="listForm.exp"/>
            </a-modal>
          </a-col>
        </a-row>
      </a-form-item>
      <a-form-item label="可攻击的实体" name="attacks" class="add-job-form-item">
        <a-row :gutter="10">
          <a-col :span="20">
            <a-select placeholder="请添加实体">
              <a-select-option v-for="(item, index) in data.attacks" :key="index" :value="item.name">{{
                  item.name
                }}:{{ item.exp }}
              </a-select-option>
            </a-select>
          </a-col>
          <a-col :span="4">
            <a-button @click="moduleStatus.attack = true">添加实体</a-button>
            <a-modal v-model:open="moduleStatus.attack" title="添加可攻击的实体" @ok="addAttack" ok-text="确定"
                     cancel-text="取消">
              <ListForm v-model:id="listForm.name" v-model:exp="listForm.exp"/>
            </a-modal>
          </a-col>
        </a-row>
      </a-form-item>
      <a-form-item label="手持工具可获得的增益" name="buffs" class="add-job-form-item">
        <a-row :gutter="10">
          <a-col :span="20">
            <a-select placeholder="请添加增益">
              <a-select-option v-for="(item, index) in data.buffs" :key="index" :value="item.name">{{
                  item.name
                }}:{{ item.value }}
              </a-select-option>
            </a-select>
          </a-col>
          <a-col :span="4">
            <a-button @click="moduleStatus.buff = true">添加增益</a-button>
            <a-modal v-model:open="moduleStatus.buff" title="添加获得的增益" @ok="addBuff" ok-text="确定"
                     cancel-text="取消">
              <ListForm v-model:id="buffForm.name" isBuff v-model:value="buffForm.value"/>
            </a-modal>
          </a-col>
        </a-row>
      </a-form-item>
      <a-form-item class="add-job-form-item float-right">
        <a-tooltip title="重置所有输入" placement="bottom">
          <a-popconfirm ok-text="确定" cancel-text="取消" title="确定要清空所有输入吗？" @confirm="resetForm(true)">
            <a-button class="mr-2">取消</a-button>
          </a-popconfirm>
        </a-tooltip>
        <a-tooltip title="添加限制" placement="bottom">
          <a-button type="primary" @click="addCurtail">确定</a-button>
        </a-tooltip>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<style lang="scss">
.add-job-form {
  width: 100%;

  .add-job-form-item-id {
    width: 100%;
  }
}
</style>