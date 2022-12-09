import request from '../utils/request' // 引入封装得axios

// 获取新闻列表
export function getArticleList (data) {
  return request({
    url: '/0-40.html',
    method: 'POST',
    data
  })
}
