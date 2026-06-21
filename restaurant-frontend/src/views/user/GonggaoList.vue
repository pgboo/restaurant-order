<template>
  <div>
    <h2>公告信息</h2>
    <el-card v-for="item in list" :key="item.id" style="margin-bottom:15px;cursor:pointer" @click="goDetail(item.id)" shadow="hover">
      <h3>{{ item.title }}</h3>
      <p style="color:#999">{{ item.addtime }}</p>
    </el-card>
    <el-pagination background layout="prev, pager, next" :total="total" :page-size="10" v-model:current-page="page" @current-change="loadData" style="text-align:center;margin-top:20px" />
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { gonggaoApi } from '../../api'
const router = useRouter()
const list = ref([])
const total = ref(0)
const page = ref(1)
const goDetail = (id) => router.push('/gonggao/' + id)
const loadData = async () => {
  const res = await gonggaoApi.list({ page: page.value, pageSize: 10 })
  list.value = res.data.list
  total.value = res.data.total
}
onMounted(loadData)
</script>
