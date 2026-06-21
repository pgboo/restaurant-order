<template>
  <div>
    <h2>菜品订单管理</h2>
    <el-row :gutter="20" style="margin-bottom:20px">
      <el-col :span="6"><el-card shadow="hover"><p>总订单数：{{ report.totalOrders || 0 }}</p></el-card></el-col>
      <el-col :span="6"><el-card shadow="hover"><p>总金额：￥{{ report.totalAmount || 0 }}</p></el-card></el-col>
      <el-col :span="6"><el-card shadow="hover"><p>待处理：{{ report.pendingCount || 0 }}</p></el-card></el-col>
      <el-col :span="6"><el-card shadow="hover"><p>已完成：{{ report.completedCount || 0 }}</p></el-card></el-col>
    </el-row>
    <el-table :data="list" stripe border>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="orderNo" label="订单号" width="220" />
      <el-table-column prop="userId" label="用户ID" width="80" />
      <el-table-column prop="caipinId" label="菜品ID" width="80" />
      <el-table-column prop="quantity" label="数量" width="60" />
      <el-table-column prop="totalPrice" label="总价" width="80" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === '已完成' ? 'success' : 'warning'">{{ row.status }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="addtime" label="时间" width="180" />
      <el-table-column label="操作" width="160">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleStatus(row)">更新状态</el-button>
          <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="prev, pager, next" :total="total" :page-size="10" v-model:current-page="page" @current-change="loadData" style="text-align:center;margin-top:20px" />
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { caipinOrderApi } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'
const list = ref([]); const total = ref(0); const page = ref(1); const report = ref({})
const loadData = async () => { const res = await caipinOrderApi.list({ page: page.value, pageSize: 10 }); list.value = res.data.list; total.value = res.data.total }
const loadReport = async () => { const res = await caipinOrderApi.report(); report.value = res.data }
const handleStatus = async (row) => {
  const statuses = ['待处理', '处理中', '已完成', '已取消']
  const idx = statuses.indexOf(row.status)
  const next = statuses[(idx + 1) % statuses.length]
  await caipinOrderApi.update({ id: row.id, status: next }); ElMessage.success('状态已更新'); loadData(); loadReport()
}
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除？'); await caipinOrderApi.delete(id); ElMessage.success('已删除'); loadData(); loadReport() }
onMounted(() => { loadData(); loadReport() })
</script>
