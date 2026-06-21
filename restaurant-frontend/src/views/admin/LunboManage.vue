<template>
  <div>
    <h2>轮播图管理</h2>
    <el-button type="primary" @click="handleAdd" style="margin-bottom:15px">新增</el-button>
    <el-table :data="list" stripe border>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="image" label="图片" width="200">
        <template #default="{ row }"><img :src="row.image || 'https://via.placeholder.com/150x80'" style="width:150px;height:80px;object-fit:cover" /></template>
      </el-table-column>
      <el-table-column prop="url" label="链接" />
      <el-table-column prop="addtime" label="创建时间" width="180" />
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="prev, pager, next" :total="total" :page-size="10" v-model:current-page="page" @current-change="loadData" style="text-align:center;margin-top:20px" />
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑' : '新增'" width="500px">
      <el-form :model="form" label-width="60px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="图片"><el-input v-model="form.image" placeholder="图片URL" /></el-form-item>
        <el-form-item label="链接"><el-input v-model="form.url" placeholder="跳转链接" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { lunboApi } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'
const list = ref([]); const total = ref(0); const page = ref(1)
const dialogVisible = ref(false); const isEdit = ref(false); const form = ref({})
const loadData = async () => { const res = await lunboApi.list({ page: page.value, pageSize: 10 }); list.value = res.data.list; total.value = res.data.total }
const handleAdd = () => { form.value = {}; isEdit.value = false; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; isEdit.value = true; dialogVisible.value = true }
const handleSave = async () => {
  if (isEdit.value) await lunboApi.update(form.value); else await lunboApi.add(form.value)
  ElMessage.success('操作成功'); dialogVisible.value = false; loadData()
}
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除？'); await lunboApi.delete(id); ElMessage.success('已删除'); loadData() }
onMounted(loadData)
</script>
