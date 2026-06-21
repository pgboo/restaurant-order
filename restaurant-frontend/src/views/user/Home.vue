<template>
  <div class="home">
    <el-carousel height="400px" v-if="lunboList.length">
      <el-carousel-item v-for="item in lunboList" :key="item.id">
        <img :src="item.image || 'https://via.placeholder.com/1200x400?text=Restaurant'" style="width:100%;height:100%;object-fit:cover" />
      </el-carousel-item>
    </el-carousel>
    <div class="section">
      <h2>最新公告</h2>
      <div class="gonggao-list">
        <el-card v-for="item in gonggaoList" :key="item.id" class="gonggao-card" @click="$router.push('/gonggao/' + item.id)" shadow="hover">
          <h3>{{ item.title }}</h3>
          <p>{{ item.addtime }}</p>
        </el-card>
      </div>
    </div>
    <div class="section">
      <h2>热门菜品</h2>
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in caipinList" :key="item.id">
          <el-card shadow="hover" @click="$router.push('/caipin/' + item.id)" class="caipin-card">
            <img :src="item.image || 'https://via.placeholder.com/300x200?text=Food'" class="caipin-img" />
            <h3>{{ item.name }}</h3>
            <p class="price">￥{{ item.price }}</p>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { lunboApi, gonggaoApi, caipinApi } from '../../api'

const router = useRouter()
const lunboList = ref([])
const gonggaoList = ref([])
const caipinList = ref([])

onMounted(async () => {
  try {
    const [lunboRes, gonggaoRes, caipinRes] = await Promise.all([
      lunboApi.list({ page: 1, pageSize: 5 }),
      gonggaoApi.list({ page: 1, pageSize: 5 }),
      caipinApi.list({ page: 1, pageSize: 8, status: '上架' })
    ])
    lunboList.value = lunboRes.data.list
    gonggaoList.value = gonggaoRes.data.list
    caipinList.value = caipinRes.data.list
  } catch (e) {}
})
</script>

<style scoped>
.section { margin: 30px 0; }
.section h2 { margin-bottom: 20px; color: #333; border-left: 4px solid #e6a23c; padding-left: 10px; }
.gonggao-list { display: flex; gap: 15px; flex-wrap: wrap; }
.gonggao-card { cursor: pointer; min-width: 250px; }
.gonggao-card h3 { color: #409eff; }
.caipin-card { cursor: pointer; margin-bottom: 20px; }
.caipin-img { width: 100%; height: 200px; object-fit: cover; }
.caipin-card h3 { margin: 10px 0 5px; }
.price { color: #e6a23c; font-size: 18px; font-weight: bold; }
</style>
