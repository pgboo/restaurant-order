<template>
  <div>
    <h2>联系我们</h2>
    <el-card v-for="item in list" :key="item.id" style="margin-bottom:15px">
      <h2>{{ item.name }}</h2>
      <p style="margin-top:10px">地址：{{ item.address }}</p>
      <p>电话：{{ item.phone }}</p>
      <p>邮箱：{{ item.email }}</p>
      <p style="margin-top:10px">{{ item.description }}</p>
    </el-card>
    <el-empty v-if="!list.length" description="暂无数据" />
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { companyApi } from '../../api'
const list = ref([])
onMounted(async () => {
  const res = await companyApi.list({ page: 1, pageSize: 100 })
  list.value = res.data.list
})
</script>
