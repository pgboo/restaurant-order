<template>
  <el-card v-if="data">
    <h1 style="text-align:center">{{ data.title }}</h1>
    <p style="text-align:center;color:#999;margin:10px 0">{{ data.addtime }}</p>
    <div v-html="data.content" style="line-height:2"></div>
  </el-card>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { gonggaoApi } from '../../api'
const route = useRoute()
const data = ref(null)
onMounted(async () => {
  const res = await gonggaoApi.getById(route.params.id)
  data.value = res.data
})
</script>
