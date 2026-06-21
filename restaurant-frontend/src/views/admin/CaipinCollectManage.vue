<template>
  <div>
    <h2>菜品收藏管理</h2>
    <el-table :data="list" stripe border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="userId" label="用户ID" />
      <el-table-column prop="caipinId" label="菜品ID" />
      <el-table-column prop="addtime" label="收藏时间" />
      <el-table-column label="操作" width="100">
        <template #default="{ row }"><el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button></template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="prev, pager, next" :total="total" :page-size="10" v-model:current-page="page" @current-change="loadData" style="text-align:center;margin-top:20px" />
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { caipinCollectApi } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'
const list = ref([]); const total = ref(0); const page = ref(1)
const loadData = async () => { const res = await caipinCollectApi.list({ page: page.value, pageSize: 10 }); list.value = res.data.list; total.value = res.data.total }
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除？'); await caipinCollectApi.delete(id); ElMessage.success('已删除'); loadData() }
onMounted(loadData)
</script>
