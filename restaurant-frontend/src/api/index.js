import request from '../utils/request'

// Auth API
export const authApi = {
  userLogin: (data) => request.post('/auth/user/login', data),
  userRegister: (data) => request.post('/auth/user/register', data),
  employeeLogin: (data) => request.post('/auth/employee/login', data),
  employeeRegister: (data) => request.post('/auth/employee/register', data),
  adminLogin: (data) => request.post('/auth/admin/login', data)
}

// Generic CRUD API factory
function createApi(basePath) {
  return {
    list: (params) => request.get(`${basePath}/list`, { params }),
    all: () => request.get(`${basePath}/all`),
    getById: (id) => request.get(`${basePath}/${id}`),
    add: (data) => request.post(basePath, data),
    update: (data) => request.put(basePath, data),
    delete: (id) => request.delete(`${basePath}/${id}`)
  }
}

export const adminApi = { ...createApi('/admin'), updateProfile: (data) => request.put('/admin/profile', data) }
export const usersApi = { ...createApi('/users'), updateProfile: (data) => request.put('/users/profile', data) }
export const employeeApi = { ...createApi('/employee'), updateProfile: (data) => request.put('/employee/profile', data) }
export const caipinApi = {
  ...createApi('/caipin'),
  updateStatus: (id, status) => request.put(`/caipin/status/${id}?status=${status}`)
}
export const caipinTypeApi = createApi('/caipin-type')
export const caipinOrderApi = {
  ...createApi('/caipin-order'),
  report: () => request.get('/caipin-order/report')
}
export const caipinCommentApi = {
  ...createApi('/caipin-comment'),
  reply: (id, reply) => request.put(`/caipin-comment/reply/${id}?reply=${encodeURIComponent(reply)}`)
}
export const caipinCollectApi = createApi('/caipin-collect')
export const gongyingshangApi = createApi('/gongyingshang')
export const gonggaoTypeApi = createApi('/gonggao-type')
export const gonggaoApi = createApi('/gonggao')
export const forumApi = {
  ...createApi('/forum'),
  getReplies: (forumId) => request.get(`/forum/${forumId}/replies`),
  addReply: (forumId, data) => request.post(`/forum/${forumId}/reply`, data),
  deleteReply: (id) => request.delete(`/forum/reply/${id}`)
}
export const wupinTypeApi = createApi('/wupin-type')
export const memberLevelApi = createApi('/member-level')
export const danpageTypeApi = createApi('/danpage-type')
export const danpageApi = createApi('/danpage')
export const companyApi = createApi('/company')
export const lunboApi = createApi('/lunbo')
export const feedbackApi = {
  ...createApi('/feedback'),
  reply: (id, reply) => request.put(`/feedback/reply/${id}?reply=${encodeURIComponent(reply)}`)
}

// File upload
export const uploadFile = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/file/upload', formData, { headers: { 'Content-Type': 'multipart/form-data' } })
}
