<template>
  <div>
    <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:20px">
      <h2>在线论坛</h2>
      <el-button type="primary" @click="showDialog = true">发帖</el-button>
    </div>
    <el-card v-for="item in list" :key="item.id" style="margin-bottom:10px;cursor:pointer" @click="goDetail(item.id)" shadow="hover">
      <h3>{{ item.title }}</h3>
      <p style="color:#999;font-size:12px">{{ item.addtime }}</p>
    </el-card>
    <el-pagination background layout="prev, pager, next" :total="total" :page-size="10" v-model:current-page="page" @current-change="loadData" style="text-align:center;margin-top:20px" />
    <el-dialog v-model="showDialog" title="发帖" width="500px">
      <el-form :model="postForm">
        <el-form-item label="标题"><el-input v-model="postForm.title" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="postForm.content" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="showDialog = false">取消</el-button><el-button type="primary" @click="handlePost">发布</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { forumApi } from '../../api'
import { ElMessage } from 'element-plus'
const router = useRouter()
const list = ref([])
const total = ref(0)
const page = ref(1)
const showDialog = ref(false)
const postForm = ref({ title: '', content: '' })
const goDetail = (id) => router.push('/forum/' + id)
const loadData = async () => {
  const res = await forumApi.list({ page: page.value, pageSize: 10 })
  list.value = res.data.list
  total.value = res.data.total
}
const handlePost = async () => {
  if (!localStorage.getItem('token')) { ElMessage.warning('请先登录'); return }
  await forumApi.add(postForm.value)
  ElMessage.success('发布成功')
  showDialog.value = false
  postForm.value = { title: '', content: '' }
  loadData()
}
onMounted(loadData)
</script>
