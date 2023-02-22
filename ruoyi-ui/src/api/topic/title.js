import request from '@/utils/request'

// 查询学生分配列表
export function listTitle(query) {
  return request({
    url: '/topic/title/list',
    method: 'get',
    params: query
  })
}

// 查询选题详细
export function getTitle(titleId) {
  return request({
    url: '/topic/title/' + titleId,
    method: 'get'
  })
}


// 新增选题
export function addTitle(data) {
  return request({
    url: '/topic/title',
    method: 'post',
    data: data
  })
}

// 修改选题
export function updateTitle(data) {
  return request({
    url: '/topic/title',
    method: 'put',
    data: data
  })
}

// 删除选题
export function delTitle(StudentId) {
  return request({
    url: '/topic/title/' + StudentId,
    method: 'delete'
  })
}

