<template>
  <div class="admin-layout">
    <el-aside width="220px" class="sidebar">
      <div class="logo">
        <h3>餐饮管理系统</h3>
      </div>
      <el-menu :default-active="activeMenu" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409eff">
        <el-menu-item index="/admin/profile">
          <el-icon><User /></el-icon>
          <span>个人中心</span>
        </el-menu-item>
        <template v-if="userStore.role === 'admin'">
          <el-menu-item index="/admin/admin-manage">
            <el-icon><UserFilled /></el-icon>
            <span>管理员管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/caipin-manage">
            <el-icon><Food /></el-icon>
            <span>菜品管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/caipin-type">
            <el-icon><Menu /></el-icon>
            <span>菜品类型管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/wupin-type">
            <el-icon><Goods /></el-icon>
            <span>物品类型管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/member-level">
            <el-icon><Medal /></el-icon>
            <span>会员等级管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/gonggao-type">
            <el-icon><Notebook /></el-icon>
            <span>公告类型管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/danpage-type">
            <el-icon><Document /></el-icon>
            <span>单页数据类型</span>
          </el-menu-item>
          <el-menu-item index="/admin/danpage">
            <el-icon><DocumentCopy /></el-icon>
            <span>单页数据管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/company">
            <el-icon><OfficeBuilding /></el-icon>
            <span>公司信息管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/lunbo">
            <el-icon><PictureFilled /></el-icon>
            <span>轮播图管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/user-manage">
            <el-icon><Avatar /></el-icon>
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/employee-manage">
            <el-icon><Postcard /></el-icon>
            <span>员工管理</span>
          </el-menu-item>
        </template>
        <template v-if="userStore.role === 'employee'">
          <el-menu-item index="/admin/caipin-manage">
            <el-icon><Food /></el-icon>
            <span>菜品管理</span>
          </el-menu-item>
        </template>
        <el-menu-item index="/admin/caipin-order">
          <el-icon><ShoppingCart /></el-icon>
          <span>菜品订单管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/caipin-comment">
          <el-icon><ChatDotSquare /></el-icon>
          <span>菜品评价管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/caipin-collect">
          <el-icon><Star /></el-icon>
          <span>菜品收藏管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/forum-manage">
          <el-icon><ChatLineSquare /></el-icon>
          <span>论坛管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/gongyingshang">
          <el-icon><Van /></el-icon>
          <span>供应商管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/gonggao">
          <el-icon><Bell /></el-icon>
          <span>公告信息管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <div class="main-area">
      <el-header class="top-bar">
        <span>欢迎，{{ userStore.username }}</span>
        <el-button type="danger" size="small" @click="handleLogout">退出登录</el-button>
      </el-header>
      <el-main class="content">
        <router-view />
      </el-main>
    </div>
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

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.admin-layout {
  display: flex;
  height: 100vh;
}
.sidebar {
  background: #304156;
  overflow-y: auto;
}
.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #263445;
}
.logo h3 {
  color: #fff;
  font-size: 16px;
}
.main-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
.top-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,0,0,0.1);
  height: 60px;
}
.content {
  flex: 1;
  background: #f0f2f5;
  overflow-y: auto;
}
</style>
