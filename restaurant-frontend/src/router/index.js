import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  { path: '/register', component: () => import('../views/Register.vue') },
  // 用户端
  {
    path: '/',
    component: () => import('../layout/UserLayout.vue'),
    children: [
      { path: '', component: () => import('../views/user/Home.vue') },
      { path: 'caipin', component: () => import('../views/user/CaipinList.vue') },
      { path: 'caipin/:id', component: () => import('../views/user/CaipinDetail.vue') },
      { path: 'gonggao', component: () => import('../views/user/GonggaoList.vue') },
      { path: 'gonggao/:id', component: () => import('../views/user/GonggaoDetail.vue') },
      { path: 'about', component: () => import('../views/user/About.vue') },
      { path: 'contact', component: () => import('../views/user/Contact.vue') },
      { path: 'forum', component: () => import('../views/user/ForumList.vue') },
      { path: 'forum/:id', component: () => import('../views/user/ForumDetail.vue') },
      { path: 'feedback', component: () => import('../views/user/Feedback.vue') },
      { path: 'profile', component: () => import('../views/user/Profile.vue') },
      { path: 'my-collect', component: () => import('../views/user/MyCollect.vue') },
      { path: 'my-order', component: () => import('../views/user/MyOrder.vue') }
    ]
  },
  // 管理端 (管理员+员工)
  {
    path: '/admin',
    component: () => import('../layout/AdminLayout.vue'),
    children: [
      { path: '', redirect: '/admin/profile' },
      { path: 'profile', component: () => import('../views/admin/Profile.vue') },
      // 管理员独有
      { path: 'admin-manage', component: () => import('../views/admin/AdminManage.vue') },
      { path: 'caipin-manage', component: () => import('../views/admin/CaipinManage.vue') },
      { path: 'caipin-type', component: () => import('../views/admin/CaipinTypeManage.vue') },
      { path: 'wupin-type', component: () => import('../views/admin/WupinTypeManage.vue') },
      { path: 'member-level', component: () => import('../views/admin/MemberLevelManage.vue') },
      { path: 'gonggao-type', component: () => import('../views/admin/GonggaoTypeManage.vue') },
      { path: 'danpage-type', component: () => import('../views/admin/DanpageTypeManage.vue') },
      { path: 'danpage', component: () => import('../views/admin/DanpageManage.vue') },
      { path: 'company', component: () => import('../views/admin/CompanyManage.vue') },
      { path: 'lunbo', component: () => import('../views/admin/LunboManage.vue') },
      { path: 'user-manage', component: () => import('../views/admin/UserManage.vue') },
      { path: 'employee-manage', component: () => import('../views/admin/EmployeeManage.vue') },
      // 管理员+员工共用
      { path: 'caipin-order', component: () => import('../views/admin/CaipinOrderManage.vue') },
      { path: 'caipin-comment', component: () => import('../views/admin/CaipinCommentManage.vue') },
      { path: 'caipin-collect', component: () => import('../views/admin/CaipinCollectManage.vue') },
      { path: 'forum-manage', component: () => import('../views/admin/ForumManage.vue') },
      { path: 'gongyingshang', component: () => import('../views/admin/GongyingshangManage.vue') },
      { path: 'gonggao', component: () => import('../views/admin/GonggaoManage.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const role = localStorage.getItem('role')
  if (to.path.startsWith('/admin') && !token) {
    next('/login')
  } else if (to.path.startsWith('/admin') && role === 'user') {
    next('/')
  } else {
    next()
  }
})

export default router
