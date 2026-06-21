<template>
  <div>
    <h2>员工管理</h2>
    <el-form :inline="true" style="margin-bottom:15px">
      <el-form-item><el-input v-model="search" placeholder="搜索用户名" clearable @clear="loadData" /></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
    </el-form>
    <el-table :data="list" stripe border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="phone" label="手机" />
      <el-table-column prop="addtime" label="创建时间" width="180" />
      <el-table-column label="操作" width="120">
        <template #default="{ row }"><el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button></template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="prev, pager, next" :total="total" :page-size="10" v-model:current-page="page" @current-change="loadData" style="text-align:center;margin-top:20px" />
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { employeeApi } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'
const list = ref([]); const total = ref(0); const page = ref(1); const search = ref('')
const loadData = async () => { const res = await employeeApi.list({ page: page.value, pageSize: 10, username: search.value }); list.value = res.data.list; total.value = res.data.total }
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除？'); await employeeApi.delete(id); ElMessage.success('已删除'); loadData() }
onMounted(loadData)
</script>