<template>
  <div class="register-page">
    <div class="register-box">
      <h2>用户注册</h2>
      <el-tabs v-model="regType">
        <el-tab-pane label="用户注册" name="user" />
        <el-tab-pane label="员工注册" name="employee" />
      </el-tabs>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="0">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" size="large" show-password />
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input v-model="form.confirmPassword" type="password" placeholder="请确认密码" prefix-icon="Lock" size="large" show-password />
        </el-form-item>
        <template v-if="regType === 'user'">
          <el-form-item prop="nickname">
            <el-input v-model="form.nickname" placeholder="请输入昵称" prefix-icon="UserFilled" size="large" />
          </el-form-item>
          <el-form-item prop="phone">
            <el-input v-model="form.phone" placeholder="请输入手机号" prefix-icon="Phone" size="large" />
          </el-form-item>
        </template>
        <template v-else>
          <el-form-item prop="name">
            <el-input v-model="form.name" placeholder="请输入姓名" prefix-icon="UserFilled" size="large" />
          </el-form-item>
          <el-form-item prop="phone">
            <el-input v-model="form.phone" placeholder="请输入手机号" prefix-icon="Phone" size="large" />
          </el-form-item>
        </template>
        <el-form-item>
          <el-button type="primary" @click="handleRegister" size="large" style="width:100%" :loading="loading">注 册</el-button>
        </el-form-item>
      </el-form>
      <div class="register-footer">
        <span>已有账号？</span>
        <el-link type="primary" @click="$router.push('/login')">去登录</el-link>
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
const regType = ref('user')
const form = ref({ username: '', password: '', confirmPassword: '', nickname: '', name: '', phone: '' })

const validateConfirm = (rule, value, callback) => {
  if (value !== form.value.password) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }, { min: 6, message: '密码至少6位', trigger: 'blur' }],
  confirmPassword: [{ required: true, message: '请确认密码', trigger: 'blur' }, { validator: validateConfirm, trigger: 'blur' }]
}

const handleRegister = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    let res
    if (regType.value === 'user') {
      res = await authApi.userRegister({ username: form.value.username, password: form.value.password, nickname: form.value.nickname, phone: form.value.phone })
    } else {
      res = await authApi.employeeRegister({ username: form.value.username, password: form.value.password, name: form.value.name, phone: form.value.phone })
    }
    userStore.setLogin(res.data)
    ElMessage.success('注册成功')
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
.register-page {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.register-box {
  width: 400px;
  padding: 40px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.2);
}
.register-box h2 { text-align: center; margin-bottom: 20px; color: #333; }
.register-footer { text-align: center; margin-top: 10px; }
</style>
