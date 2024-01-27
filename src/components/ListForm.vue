<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";

defineProps({
  id: {
    type: String,
    required: true
  },
  value: {
    type: String,
  },
  exp: {
    type: Number,
  },
  placeholder: {
    type: String,
    default: ''
  },
  isBuff: {
    type: Boolean,
    default: false
  }
})
const emit = defineEmits(['update:id', 'update:exp'])
let result = ref([]);
onMounted(async () => {
  let response = await axios.get("/ids.json")
  let responseData = response.data;
  let keys = Object.keys(responseData);
  let ids = [];
  for (let key of keys) {
    let id = {};
    id.name = responseData[key];
    id.title = key;
    ids.push(id);
  }
  result.value = ids;
})
const findInfoById = (id) => {
  let info = {};
  for (let i = 0; i < result.value.length; i++) {
    if (result.value[i].name === id) {
      info.name = result.value[i].name;
      info.title = result.value[i].title;
      break;
    }
  }
  return info;
}
const findInfoByName = (name) => {
  let info = {};
  for (let i = 0; i < result.value.length; i++) {
    if (result.value[i].title === name) {
      info.name = result.value[i].name;
      info.title = result.value[i].title;
      break;
    }
  }
  return info;
}
const changeId = (value) => {
  let info = findInfoByName(value)
  emit('update:id', info.name)
}
const changeExp = (value) => {
  emit('update:exp', value)
}
</script>

<template>
  <a-form :label-col="{
    style: {
      width: '70px'
    }
  }">
    <template v-if="isBuff">
      <a-form-item label="属性名">
        <a-input :default-value="name" @change="changeExp" placeholder="属性名称" style="width: 100%"/>
      </a-form-item>
      <a-form-item label="属性值">
        <a-input :default-value="value" @change="changeExp" placeholder="属性值" style="width: 100%"/>
      </a-form-item>
    </template>
    <template v-else>
      <a-form-item label="原版目标">
        <a-select placeholder="请选择目标" show-search @select="changeId" :placeholder="placeholder">
          <a-select-option v-for="item in result" :key="item.name" :value="item.title">{{
              item.title
            }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="经验值">
        <a-input-number :default-value="exp" @change="changeExp" placeholder="单次触发获得经验值" style="width: 100%"/>
      </a-form-item>
    </template>
  </a-form>
</template>

<style lang="scss">

</style>