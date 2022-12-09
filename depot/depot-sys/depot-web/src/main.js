import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
import axios from 'axios';
import VueRouter from "vue-router";
import router from "@/router";
import store from "@/store";
import request from "@/utils/request";


import $ from 'jquery';
window.jQuery = $;
window.$ = $;

Vue.prototype.$axios=axios
axios.defaults.withCredentials = true
Vue.prototype.$httpUrl='http://localhost:8082'
Vue.prototype.request=request

// Vue.use(ElementUI);
Vue.use(VueRouter);
Vue.use(ElementUI,{size:'small'});
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
