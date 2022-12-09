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
      },
      {
        path: '/channel',
        name: 'channel',
        component: () => import('../views/login/channel.vue')
      },
      {
        path: '/tabBarItem',
        name: 'tabBarItem',
        component: () => import('../views/login/tabBarItem.vue')
      },
      {
        path: '/tabBar',
        name: 'tabBar',
        component: () => import('../views/login/tabBar.vue')
      },
    ]
  },
 ]
const router = createRouter({
  history: createWebHashHistory(process.env.BASE_URL),
  routes
})

export default router
