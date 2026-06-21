<template>
  <div>
    <h2>留言反馈</h2>
    <el-card style="margin-bottom:20px">
      <el-form :model="form">
        <el-form-item label="留言内容"><el-input v-model="form.content" type="textarea" :rows="4" placeholder="请输入您的反馈" /></el-form-item>
        <el-form-item><el-button type="primary" @click="handleAdd">提交</el-button></el-form-item>
      </el-form>
    </el-card>
    <el-card v-for="item in list" :key="item.id" style="margin-bottom:10px">
      <p>{{ item.content }}</p>
      <p v-if="item.reply" style="color:#409eff;margin-top:5px">回复：{{ item.reply }}</p>
      <p style="color:#999;font-size:12px">{{ item.addtime }}</p>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { feedbackApi } from '../../api'
import { ElMessage } from 'element-plus'
const list = ref([])
const form = ref({ content: '' })
const loadData = async () => {
  const res = await feedbackApi.list({ page: 1, pageSize: 100 })
  list.value = res.data.list
}
const handleAdd = async () => {
  if (!form.value.content) { ElMessage.warning('请输入内容'); return }
  await feedbackApi.add(form.value)
  ElMessage.success('提交成功')
  form.value.content = ''
  loadData()
}
onMounted(loadData)
</script>
