<template>
  <div>
    <h2>供应商管理</h2>
    <el-button type="primary" @click="handleAdd" style="margin-bottom:15px">新增</el-button>
    <el-table :data="list" stripe border>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="contact" label="联系人" width="100" />
      <el-table-column prop="phone" label="电话" width="130" />
      <el-table-column prop="address" label="地址" />
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
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="联系人"><el-input v-model="form.contact" /></el-form-item>
        <el-form-item label="电话"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="form.address" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { gongyingshangApi } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'
const list = ref([]); const total = ref(0); const page = ref(1)
const dialogVisible = ref(false); const isEdit = ref(false); const form = ref({})
const loadData = async () => { const res = await gongyingshangApi.list({ page: page.value, pageSize: 10 }); list.value = res.data.list; total.value = res.data.total }
const handleAdd = () => { form.value = {}; isEdit.value = false; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; isEdit.value = true; dialogVisible.value = true }
const handleSave = async () => {
  if (isEdit.value) await gongyingshangApi.update(form.value); else await gongyingshangApi.add(form.value)
  ElMessage.success('操作成功'); dialogVisible.value = false; loadData()
}
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除？'); await gongyingshangApi.delete(id); ElMessage.success('已删除'); loadData() }
onMounted(loadData)
</script>
