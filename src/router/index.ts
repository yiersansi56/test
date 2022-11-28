import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
const routes: Array<RouteRecordRaw> =[{
    path: '/index',
    name: 'index',
   
    component: () => import(/* webpackChunkName: "about" */ '../views/login/index.vue')
  }
 ]
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
