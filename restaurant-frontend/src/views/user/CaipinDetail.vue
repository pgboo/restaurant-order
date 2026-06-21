<template>
  <div v-if="caipin">
    <el-card>
      <el-row :gutter="30">
        <el-col :span="10"><img :src="caipin.image || 'https://via.placeholder.com/400x300?text=Food'" style="width:100%;border-radius:8px" /></el-col>
        <el-col :span="14">
          <h1>{{ caipin.name }}</h1>
          <p style="color:#e6a23c;font-size:28px;font-weight:bold;margin:15px 0">￥{{ caipin.price }}</p>
          <p>{{ caipin.description }}</p>
          <p style="margin-top:10px;color:#999">状态：{{ caipin.status }} | 创建时间：{{ caipin.addtime }}</p>
          <div style="margin-top:20px;display:flex;gap:10px">
            <el-button type="primary" @click="handleCollect">收藏</el-button>
            <el-button type="success" @click="handleOrder">立即预定</el-button>
          </div>
        </el-col>
      </el-row>
    </el-card>
    <el-card style="margin-top:20px">
      <h3>菜品评价</h3>
      <div v-for="c in comments" :key="c.id" style="border-bottom:1px solid #eee;padding:10px 0">
        <p>{{ c.content }}</p>
        <p v-if="c.reply" style="color:#409eff;margin-top:5px">商家回复：{{ c.reply }}</p>
        <p style="color:#999;font-size:12px">{{ c.addtime }}</p>
      </div>
      <div v-if="!comments.length" style="color:#999;text-align:center;padding:20px">暂无评价</div>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { caipinApi, caipinCollectApi, caipinOrderApi, caipinCommentApi } from '../../api'
import { ElMessage } from 'element-plus'
const route = useRoute()
const router = useRouter()
const caipin = ref(null)
const comments = ref([])
onMounted(async () => {
  const res = await caipinApi.getById(route.params.id)
  caipin.value = res.data
  const commentRes = await caipinCommentApi.list({ caipinId: route.params.id, page: 1, pageSize: 100 })
  comments.value = commentRes.data.list
})
const handleCollect = async () => {
  if (!localStorage.getItem('token')) { ElMessage.warning('请先登录'); router.push('/login'); return }
  try { await caipinCollectApi.add({ caipinId: Number(route.params.id) }); ElMessage.success('收藏成功') } catch(e) {}
}
const handleOrder = async () => {
  if (!localStorage.getItem('token')) { ElMessage.warning('请先登录'); router.push('/login'); return }
  try { await caipinOrderApi.add({ caipinId: Number(route.params.id), quantity: 1, totalPrice: caipin.value.price }); ElMessage.success('预定成功') } catch(e) {}
}
</script>
