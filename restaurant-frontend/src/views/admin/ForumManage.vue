<template>
  <div>
    <h2>论坛管理</h2>
    <el-button type="primary" @click="handleAdd" style="margin-bottom:15px">新增帖子</el-button>
    <el-table :data="list" stripe border>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="userId" label="用户ID" width="80" />
      <el-table-column prop="addtime" label="时间" width="180" />
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="viewReplies(row)">查看回复</el-button>
          <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="prev, pager, next" :total="total" :page-size="10" v-model:current-page="page" @current-change="loadData" style="text-align:center;margin-top:20px" />
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑' : '新增'" width="500px">
      <el-form :model="form" label-width="60px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
    <el-dialog v-model="replyDialog" title="帖子回复" width="600px">
      <div v-for="r in replies" :key="r.id" style="border-bottom:1px solid #eee;padding:10px 0">
        <p>{{ r.content }}</p>
        <p style="color:#999;font-size:12px">{{ r.addtime }}</p>
      </div>
      <div v-if="!replies.length" style="text-align:center;color:#999;padding:20px">暂无回复</div>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { forumApi } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'
const list = ref([]); const total = ref(0); const page = ref(1)
const dialogVisible = ref(false); const isEdit = ref(false); const form = ref({})
const replyDialog = ref(false); const replies = ref([])
const loadData = async () => { const res = await forumApi.list({ page: page.value, pageSize: 10 }); list.value = res.data.list; total.value = res.data.total }
const handleAdd = () => { form.value = {}; isEdit.value = false; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; isEdit.value = true; dialogVisible.value = true }
const handleSave = async () => {
  if (isEdit.value) await forumApi.update(form.value); else await forumApi.add(form.value)
  ElMessage.success('操作成功'); dialogVisible.value = false; loadData()
}
const viewReplies = async (row) => { const res = await forumApi.getReplies(row.id); replies.value = res.data; replyDialog.value = true }
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除？'); await forumApi.delete(id); ElMessage.success('已删除'); loadData() }
onMounted(loadData)
</script>
