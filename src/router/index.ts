import { createRouter, createWebHistory, createWebHashHistory,RouteRecordRaw } from 'vue-router'
const routes: Array<RouteRecordRaw> =[
  {
    path:"/app",
    name:"app",
    component: () => import('../../src/App.vue'),
    children:[
      {
        path:"/example",
        name:"example",
        component: () => import('../views/login/example.vue')
      },
      
      {
        path: '/index',
        name: 'index',
        component: () => import('../views/login/index.vue')
      },
      {
        path: '/chart',
        name: 'chart',
        component: () => import('../views/login/chart.vue')
      },
      
      {
        path: '/mask',
        name: 'mask',
        component: () => import('../views/login/mask.vue')
      },
      {
        path: '/qrcode',
        name: 'qrcode',
        component: () => import('../views/login/qrcode.vue')
      }
    ]
  },
 ]
const router = createRouter({
  history: createWebHashHistory(process.env.BASE_URL),
  routes
})

export default router
