<template>
  <div>
    <h2>个人中心</h2>
    <el-card style="max-width:500px;margin:20px auto">
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名"><el-input v-model="form.username" disabled /></el-form-item>
        <el-form-item label="昵称"><el-input v-model="form.nickname" /></el-form-item>
        <el-form-item label="手机"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="会员等级"><el-input v-model="form.memberLevel" disabled /></el-form-item>
        <el-form-item label="新密码"><el-input v-model="form.password" type="password" placeholder="不修改请留空" show-password /></el-form-item>
        <el-form-item><el-button type="primary" @click="handleSave">保存</el-button></el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { usersApi } from '../../api'
import { ElMessage } from 'element-plus'
const form = ref({})
onMounted(async () => {
  const res = await usersApi.getById(localStorage.getItem('userId'))
  form.value = res.data
  form.value.password = ''
})
const handleSave = async () => {
  await usersApi.updateProfile(form.value)
  ElMessage.success('保存成功')
}
</script>
