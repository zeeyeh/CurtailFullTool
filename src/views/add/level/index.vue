<script setup>
import {reactive, toRefs} from "vue";
import TagInput from "@/components/TagInput.vue";
import {message} from "ant-design-vue";
import SnowflakeId from "snowflake-id";
import {useLevelStore} from "@/store/modules/LevelListStore.js";

const snowflake = new SnowflakeId()

const data = reactive({
  id: snowflake.generate(),
  name: '',
  title: '',
  maxExp: 0,
  nextId: -1,
  players: []
})
const levelStore = useLevelStore()

const addLevel = () => {
  levelStore.addLevel(...toRefs(data))
  message.success("等级添加成功")
  resetForm();
}

const resetForm = (showTip = false) => {
  data.id = snowflake.generate();
  data.name = '';
  data.title = '';
  data.maxExp = 0;
  data.nextId = -1;
  data.players = [];
  if (showTip) {
    message.success("数据清空成功")
  }
}
</script>

<template>
  <a-card title="添加等级">
    <a-form class="add-job-form" :label-col="{
    style: {
      width: '90px'
    }
  }">
      <a-form-item label="等级唯一Id" name="id" class="add-job-form-item">
        <a-input-number v-model:value="data.id" placeholder="请输入等级唯一Id" class="add-job-form-item-id"/>
      </a-form-item>
      <a-form-item label="等级名称" name="name" class="add-job-form-item">
        <a-input v-model:value="data.name" placeholder="请输入等级名称" class="add-job-form-item-id"/>
      </a-form-item>
      <a-form-item label="等级标题" name="title" class="add-job-form-item">
        <a-input v-model:value="data.title" placeholder="请输入等级标题" class="add-job-form-item-id"/>
      </a-form-item>
      <a-form-item label="最大经验值" name="maxExp" class="add-job-form-item">
        <a-input-number v-model:value="data.maxExp" placeholder="请输入等级标题" class="add-job-form-item-id"/>
      </a-form-item>
      <a-form-item label="下一阶段Id" name="nextId" class="add-job-form-item">
        <a-input-number v-model:value="data.nextId" placeholder="请输入下一阶段Id" class="add-job-form-item-id"/>
      </a-form-item>
      <a-form-item label="拥有玩家" name="players" class="add-job-form-item">
        <TagInput :list="data.players" placeholder="请输入玩家名称"/>
      </a-form-item>
      <a-form-item class="add-job-form-item float-right">
        <a-tooltip title="重置所有输入" placement="bottom">
          <a-popconfirm ok-text="确定" cancel-text="取消" title="确定要清空所有输入吗？" @confirm="resetForm(true)">
            <a-button class="mr-2">取消</a-button>
          </a-popconfirm>
        </a-tooltip>
        <a-tooltip title="添加等级" placement="bottom">
          <a-button type="primary" @click="addLevel">确定</a-button>
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