<script setup>
import {onMounted, reactive, watch} from "vue";

const props = defineProps({
  list: {
    type: Array,
    default: []
  },
  deduplication: {
    type: Boolean,
    default: false
  },
  placeholder: {
    type: String,
    default: ""
  }
})

const data = reactive({
  list: [],
  value: ''
})
const emit = defineEmits(['input','tagClose']);
onMounted(() => {
  data.list = props.list;
  emit('input', data.list)
})

const addTag = () => {
  data.value = data.value.trim();
  if (data.value !== '' && data.value !== ' '){
    data.list.push(data.value)
    if (props.list) {
      let array = data.list;
      let set = new Set(array)
      data.list = Array.from(set)
    }
    data.value = ''
    emit('input', data.list);
  }
}
watch(props.list, () => {
  data.list = props.list;
}, {
  deep: true
})
const handCloseTag = (tag) => {
  emit('tagClose', tag)
  data.list.splice(data.list.indexOf(tag), 1)
}
</script>

<template>
  <template v-if="data.list.length > 0">
    <div class="tag-input mt-1.5">
      <a-tag color="blue" closable @close="handCloseTag(tag)" v-for="(tag, index) in data.list" :key="index">{{ tag }}</a-tag>
      <a-tooltip title="按下回车添加玩家到列表" placement="bottom">
        <a-input class="mt-2" type="text" :placeholder="placeholder" v-model:value="data.value" @pressEnter="addTag"/>
      </a-tooltip>
    </div>
  </template>
  <template v-else>
    <div class="tag-input mt--1.5">
      <a-tag color="blue" closable @close="handCloseTag(tag)" v-for="(tag, index) in data.list" :key="index">{{ tag }}</a-tag>
      <a-tooltip title="按下回车添加玩家到列表" placement="bottom">
        <a-input class="mt-2" type="text" :placeholder="placeholder" v-model:value="data.value" @pressEnter="addTag"/>
      </a-tooltip>
    </div>
  </template>
</template>

<style scoped lang="scss">

</style>