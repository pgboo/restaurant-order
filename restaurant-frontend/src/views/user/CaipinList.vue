<template>
  <div>
    <h2>菜品展示</h2>
    <el-form :inline="true" style="margin-bottom:20px">
      <el-form-item><el-input v-model="searchName" placeholder="搜索菜品" clearable @clear="loadData" /></el-form-item>
      <el-form-item>
        <el-select v-model="searchType" placeholder="选择分类" clearable @change="loadData">
          <el-option v-for="t in typeList" :key="t.id" :label="t.name" :value="t.id" />
        </el-select>
      </el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
    </el-form>
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in list" :key="item.id">
        <el-card shadow="hover" @click="goDetail(item.id)" style="cursor:pointer;margin-bottom:20px">
          <img :src="item.image || 'https://via.placeholder.com/300x200?text=Food'" style="width:100%;height:200px;object-fit:cover" />
          <h3>{{ item.name }}</h3>
          <p style="color:#e6a23c;font-size:18px;font-weight:bold">￥{{ item.price }}</p>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" v-model:current-page="page" @current-change="loadData" style="text-align:center;margin-top:20px" />
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { caipinApi, caipinTypeApi } from '../../api'
const router = useRouter()
const list = ref([]); const typeList = ref([]); const total = ref(0); const page = ref(1); const pageSize = 8
const searchName = ref(''); const searchType = ref(null)
const goDetail = (id) => router.push('/caipin/' + id)
const loadData = async () => {
  const res = await caipinApi.list({ page: page.value, pageSize, name: searchName.value, typeId: searchType.value, status: '上架' })
  list.value = res.data.list; total.value = res.data.total
}
onMounted(async () => { loadData(); const typeRes = await caipinTypeApi.all(); typeList.value = typeRes.data })
</script>
