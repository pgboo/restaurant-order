<template>
  <div class="login-page">
    <div class="login-box">
      <h2>餐饮管理系统登录</h2>
      <el-tabs v-model="loginType">
        <el-tab-pane label="用户登录" name="user" />
        <el-tab-pane label="员工登录" name="employee" />
        <el-tab-pane label="管理员登录" name="admin" />
      </el-tabs>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="0">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" size="large" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" size="large" style="width:100%" :loading="loading">登 录</el-button>
        </el-form-item>
      </el-form>
      <div class="login-footer">
        <template v-if="loginType !== 'admin'">
          <span>还没有账号？</span>
          <el-link type="primary" @click="$router.push('/register')">立即注册</el-link>
        </template>
        <el-link type="info" @click="$router.push('/')">返回首页</el-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { authApi } from '../api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)
const loginType = ref('user')
const form = ref({ username: '', password: '' })

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    let res
    if (loginType.value === 'user') {
      res = await authApi.userLogin(form.value)
    } else if (loginType.value === 'employee') {
      res = await authApi.employeeLogin(form.value)
    } else {
      res = await authApi.adminLogin(form.value)
    }
    userStore.setLogin(res.data)
    ElMessage.success('登录成功')
    if (res.data.role === 'user') {
      router.push('/')
    } else {
      router.push('/admin')
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.login-box {
  width: 400px;
  padding: 40px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.2);
}
.login-box h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}
.login-footer {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}
</style>
