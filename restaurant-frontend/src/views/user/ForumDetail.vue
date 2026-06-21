<template>
  <div v-if="post">
    <el-card>
      <h1>{{ post.title }}</h1>
      <p style="color:#999;margin:10px 0">{{ post.addtime }}</p>
      <div style="line-height:2">{{ post.content }}</div>
    </el-card>
    <el-card style="margin-top:20px">
      <h3>回复列表</h3>
      <div v-for="r in replies" :key="r.id" style="border-bottom:1px solid #eee;padding:10px 0">
        <p>{{ r.content }}</p>
        <p style="color:#999;font-size:12px">{{ r.addtime }}</p>
      </div>
      <div v-if="!replies.length" style="color:#999;text-align:center;padding:20px">暂无回复</div>
      <div style="margin-top:20px;display:flex;gap:10px">
        <el-input v-model="replyContent" placeholder="输入回复内容" />
        <el-button type="primary" @click="handleReply">回复</el-button>
      </div>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { forumApi } from '../../api'
import { ElMessage } from 'element-plus'
const route = useRoute()
const post = ref(null)
const replies = ref([])
const replyContent = ref('')
const loadData = async () => {
  const res = await forumApi.getById(route.params.id)
  post.value = res.data
  const replyRes = await forumApi.getReplies(route.params.id)
  replies.value = replyRes.data
}
const handleReply = async () => {
  if (!localStorage.getItem('token')) { ElMessage.warning('请先登录'); return }
  await forumApi.addReply(route.params.id, { content: replyContent.value })
  ElMessage.success('回复成功')
  replyContent.value = ''
  loadData()
}
onMounted(loadData)
</script>
