import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询学生分配列表
export function listDist(query) {
  return request({
    url: '/topic/dist/list',
    method: 'get',
    params: query
  })
}

// 新增学生分配关系
export function addDist(data) {
  return request({
    url: '/topic/dist',
    method: 'post',
    data: data
  })
}

// 修改学生分配关系
export function updateDist(data) {
  return request({
    url: '/topic/dist',
    method: 'put',
    data: data
  })
}

// 删除学生分配关系
export function delDist(StudentId) {
  return request({
    url: '/topic/dist/' + StudentId,
    method: 'delete'
  })
}


// 查询部门下拉树结构
export function deptTreeSelect() {
  return request({
    url: '/system/user/deptTree',
    method: 'get'
  })
}
