
import VueRouter from "vue-router";

const routes = [
    {
        path:'/',
        name:'first',
        component:()=>import('../components/First')
    },
    {
        path:'/Login',
        name:'login',
        component:()=>import('../components/Login')
    },
    {
        path:'/Register',
        name:'register',
        component:()=>import('../components/Register')
    },
    {
        path:'/Index',
        name:'index',
        component:()=>import('../components/Index'),
        children:[
            {
                path:'/Personal',
                name:'personal',
                meta:{
                    title:'首页'
                },
                component:()=>import('../components/Personal'),
            },
        ]
    }
]


const router = new VueRouter({
    mode:'history',
    routes
})
export function resetRouter() {
    router.matcher = new VueRouter({
        mode:'history',
        routes: []
    }).matcher
}
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}
export default router;