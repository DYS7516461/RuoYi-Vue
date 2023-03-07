import request from '@/utils/request'

// 查询学生选题列表
export function listTopic(query) {
  return request({
    url: '/topic/stuTopic/list',
    method: 'get',
    params: query
  })
}

// 查询学生选题详细
export function getTopic(id) {
  return request({
    url: '/topic/stuTopic/' + id,
    method: 'get'
  })
}

// 新增学生选题
export function addTopic(data) {
  return request({
    url: '/topic/stuTopic',
    method: 'post',
    data: data
  })
}

// 修改学生选题
export function updateTopic(data) {
  return request({
    url: '/topic/stuTopic',
    method: 'put',
    data: data
  })
}

// 删除学生选题
export function delTopic(id) {
  return request({
    url: '/topic/stuTopic/' + id,
    method: 'delete'
  })
}
