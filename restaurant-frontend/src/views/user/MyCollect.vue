<template>
  <div>
    <h2>我的收藏</h2>
    <el-table :data="list" stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="caipinId" label="菜品ID" width="100" />
      <el-table-column prop="addtime" label="收藏时间" />
      <el-table-column label="操作" width="100">
        <template #default="{ row }"><el-button type="danger" size="small" @click="handleDelete(row.id)">取消收藏</el-button></template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="prev, pager, next" :total="total" :page-size="10" v-model:current-page="page" @current-change="loadData" style="text-align:center;margin-top:20px" />
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { caipinCollectApi } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'
const list = ref([])
const total = ref(0)
const page = ref(1)
const loadData = async () => {
  const res = await caipinCollectApi.list({ page: page.value, pageSize: 10 })
  list.value = res.data.list
  total.value = res.data.total
}
const handleDelete = async (id) => {
  await ElMessageBox.confirm('确定取消收藏？')
  await caipinCollectApi.delete(id)
  ElMessage.success('已取消')
  loadData()
}
onMounted(loadData)
</script>
