import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
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
      }
    ]
  },
 ]
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
