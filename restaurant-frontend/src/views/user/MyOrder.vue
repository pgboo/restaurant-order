<template>
  <div>
    <h2>我的订单</h2>
    <el-table :data="list" stripe>
      <el-table-column prop="orderNo" label="订单号" width="220" />
      <el-table-column prop="caipinId" label="菜品ID" width="80" />
      <el-table-column prop="quantity" label="数量" width="80" />
      <el-table-column prop="totalPrice" label="总价" width="100" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === '已完成' ? 'success' : 'warning'">{{ row.status }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="addtime" label="下单时间" />
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button v-if="row.status === '待处理'" type="primary" size="small" @click="handleUpdate(row)">确认收货</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="prev, pager, next" :total="total" :page-size="10" v-model:current-page="page" @current-change="loadData" style="text-align:center;margin-top:20px" />
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { caipinOrderApi } from '../../api'
import { ElMessage } from 'element-plus'
const list = ref([])
const total = ref(0)
const page = ref(1)
const loadData = async () => {
  const res = await caipinOrderApi.list({ page: page.value, pageSize: 10 })
  list.value = res.data.list
  total.value = res.data.total
}
const handleUpdate = async (row) => {
  await caipinOrderApi.update({ id: row.id, status: '已完成' })
  ElMessage.success('已确认')
  loadData()
}
onMounted(loadData)
</script>
