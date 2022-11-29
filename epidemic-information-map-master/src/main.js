import Vue from 'vue'
import App from './App.vue'
import Notify from 'vue-notifyjs'
Vue.use(Notify)
Vue.config.productionTip = false
new Vue({
  render: h => h(App),
}).$mount('#app')

