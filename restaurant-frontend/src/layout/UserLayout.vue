<template>
  <div class="user-layout">
    <el-header class="header">
      <div class="logo">
        <h2>美味餐厅</h2>
      </div>
      <el-menu mode="horizontal" :default-active="activeMenu" router class="nav-menu">
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/caipin">菜品展示</el-menu-item>
        <el-menu-item index="/gonggao">公告信息</el-menu-item>
        <el-menu-item index="/about">酒店介绍</el-menu-item>
        <el-menu-item index="/contact">联系我们</el-menu-item>
        <el-menu-item index="/forum">在线论坛</el-menu-item>
      </el-menu>
      <div class="user-actions">
        <template v-if="userStore.token && userStore.role === 'user'">
          <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link">
              <el-icon><User /></el-icon> {{ userStore.username }}
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <el-dropdown-item command="my-collect">我的收藏</el-dropdown-item>
                <el-dropdown-item command="my-order">我的订单</el-dropdown-item>
                <el-dropdown-item command="feedback">留言反馈</el-dropdown-item>
                <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>
        <template v-else>
          <el-button type="primary" @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </template>
      </div>
    </el-header>
    <el-main class="main-content">
      <router-view />
    </el-main>
    <el-footer class="footer">
      <p>© 2024 美味餐厅 版权所有</p>
    </el-footer>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../store/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout()
    router.push('/login')
  } else {
    router.push('/' + command)
  }
}
</script>

<style scoped>
.user-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
.header {
  display: flex;
  align-items: center;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  padding: 0 20px;
  height: 60px;
  z-index: 10;
}
.logo h2 {
  color: #e6a23c;
  margin-right: 30px;
  white-space: nowrap;
}
.nav-menu {
  flex: 1;
  border-bottom: none;
}
.user-actions {
  display: flex;
  align-items: center;
  gap: 10px;
}
.el-dropdown-link {
  display: flex;
  align-items: center;
  gap: 4px;
  cursor: pointer;
  color: #409eff;
}
.main-content {
  flex: 1;
  background: #f5f7fa;
  padding: 20px;
}
.footer {
  text-align: center;
  background: #333;
  color: #fff;
  line-height: 60px;
}
</style>
