<template>
  <div>
    <h2>酒店介绍</h2>
    <el-card v-for="item in list" :key="item.id" style="margin-bottom:15px">
      <h3>{{ item.title }}</h3>
      <div v-html="item.content" style="line-height:2;margin-top:10px"></div>
    </el-card>
    <el-empty v-if="!list.length" description="暂无数据" />
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { danpageApi } from '../../api'
const list = ref([])
onMounted(async () => {
  const res = await danpageApi.list({ page: 1, pageSize: 100 })
  list.value = res.data.list
})
</script>
