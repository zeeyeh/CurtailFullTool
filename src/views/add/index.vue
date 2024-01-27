<script setup>
import {useRouter} from "vue-router";
import {onMounted} from "vue";

const router = useRouter();
onMounted(() => router.replace("/add/job"))
</script>

<template>
<!--  <RouterView v-slot="{ Component, route }">-->
<!--    <component :is="Component" :key="route.path"/>-->
<!--  </RouterView>-->
  <router-view/>
</template>

<style scoped lang="scss">

</style>