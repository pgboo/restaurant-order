<template>
  <div>
    <h2>菜品评价管理</h2>
    <el-table :data="list" stripe border>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="userId" label="用户ID" width="80" />
      <el-table-column prop="caipinId" label="菜品ID" width="80" />
      <el-table-column prop="content" label="评价内容" />
      <el-table-column prop="reply" label="回复" />
      <el-table-column prop="addtime" label="时间" width="180" />
      <el-table-column label="操作" width="160">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleReply(row)">回复</el-button>
          <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="prev, pager, next" :total="total" :page-size="10" v-model:current-page="page" @current-change="loadData" style="text-align:center;margin-top:20px" />
    <el-dialog v-model="replyDialog" title="回复评价" width="400px">
      <el-input v-model="replyContent" type="textarea" :rows="3" placeholder="输入回复内容" />
      <template #footer><el-button @click="replyDialog = false">取消</el-button><el-button type="primary" @click="submitReply">提交</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { caipinCommentApi } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'
const list = ref([]); const total = ref(0); const page = ref(1)
const replyDialog = ref(false); const replyContent = ref(''); const replyId = ref(null)
const loadData = async () => { const res = await caipinCommentApi.list({ page: page.value, pageSize: 10 }); list.value = res.data.list; total.value = res.data.total }
const handleReply = (row) => { replyId.value = row.id; replyContent.value = row.reply || ''; replyDialog.value = true }
const submitReply = async () => { await caipinCommentApi.reply(replyId.value, replyContent.value); ElMessage.success('回复成功'); replyDialog.value = false; loadData() }
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除？'); await caipinCommentApi.delete(id); ElMessage.success('已删除'); loadData() }
onMounted(loadData)
</script>
