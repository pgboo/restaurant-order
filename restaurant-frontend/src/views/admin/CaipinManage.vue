<template>
  <div>
    <h2>菜品管理</h2>
    <el-button type="primary" @click="handleAdd" style="margin-bottom:15px">新增</el-button>
    <el-form :inline="true" style="margin-bottom:15px">
      <el-form-item><el-input v-model="search" placeholder="搜索菜品名称" clearable @clear="loadData" /></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
    </el-form>
    <el-table :data="list" stripe border>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="typeId" label="类型ID" width="80" />
      <el-table-column prop="price" label="价格" width="80" />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }"><el-tag :type="row.status === '上架' ? 'success' : 'info'">{{ row.status }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="addtime" label="创建时间" width="180" />
      <el-table-column label="操作" width="240">
        <template #default="{ row }">
          <el-button :type="row.status === '上架' ? 'warning' : 'success'" size="small" @click="toggleStatus(row)">{{ row.status === '上架' ? '下架' : '上架' }}</el-button>
          <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="prev, pager, next" :total="total" :page-size="10" v-model:current-page="page" @current-change="loadData" style="text-align:center;margin-top:20px" />
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑' : '新增'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="类型"><el-select v-model="form.typeId"><el-option v-for="t in types" :key="t.id" :label="t.name" :value="t.id" /></el-select></el-form-item>
        <el-form-item label="价格"><el-input-number v-model="form.price" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="图片"><el-input v-model="form.image" placeholder="图片URL" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="状态"><el-select v-model="form.status"><el-option label="上架" value="上架" /><el-option label="下架" value="下架" /></el-select></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { caipinApi, caipinTypeApi } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'
const list = ref([]); const total = ref(0); const page = ref(1); const search = ref('')
const types = ref([]); const dialogVisible = ref(false); const isEdit = ref(false); const form = ref({})
const loadData = async () => { const res = await caipinApi.list({ page: page.value, pageSize: 10, name: search.value }); list.value = res.data.list; total.value = res.data.total }
const loadTypes = async () => { const res = await caipinTypeApi.all(); types.value = res.data }
const handleAdd = () => { form.value = { status: '下架' }; isEdit.value = false; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; isEdit.value = true; dialogVisible.value = true }
const handleSave = async () => {
  if (isEdit.value) await caipinApi.update(form.value); else await caipinApi.add(form.value)
  ElMessage.success('操作成功'); dialogVisible.value = false; loadData()
}
const toggleStatus = async (row) => {
  const newStatus = row.status === '上架' ? '下架' : '上架'
  await caipinApi.updateStatus(row.id, newStatus); ElMessage.success('操作成功'); loadData()
}
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除？'); await caipinApi.delete(id); ElMessage.success('已删除'); loadData() }
onMounted(() => { loadData(); loadTypes() })
</script>
