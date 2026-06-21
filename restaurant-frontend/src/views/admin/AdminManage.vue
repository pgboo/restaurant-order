<template>
  <div>
    <h2>管理员管理</h2>
    <el-button type="primary" @click="handleAdd" style="margin-bottom:15px">新增</el-button>
    <el-form :inline="true" style="margin-bottom:15px">
      <el-form-item><el-input v-model="search" placeholder="搜索用户名" clearable @clear="loadData" /></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
    </el-form>
    <el-table :data="list" stripe border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="addtime" label="创建时间" width="180" />
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="prev, pager, next" :total="total" :page-size="10" v-model:current-page="page" @current-change="loadData" style="text-align:center;margin-top:20px" />
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑' : '新增'" width="400px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" :placeholder="isEdit ? '不修改留空' : '请输入密码'" show-password /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { adminApi } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'
const list = ref([]); const total = ref(0); const page = ref(1); const search = ref('')
const dialogVisible = ref(false); const isEdit = ref(false); const form = ref({})
const loadData = async () => { const res = await adminApi.list({ page: page.value, pageSize: 10, username: search.value }); list.value = res.data.list; total.value = res.data.total }
const handleAdd = () => { form.value = {}; isEdit.value = false; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; form.value.password = ''; isEdit.value = true; dialogVisible.value = true }
const handleSave = async () => {
  if (isEdit.value) await adminApi.update(form.value); else await adminApi.add(form.value)
  ElMessage.success('操作成功'); dialogVisible.value = false; loadData()
}
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除？'); await adminApi.delete(id); ElMessage.success('已删除'); loadData() }
onMounted(loadData)
</script>