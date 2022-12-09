<template>
  <div id="channel" style="background:pink;width:2000px;height:2000px">
    <ul>
      <li v-for="item in list" :key="item.riskLevel">
        <a href="">{{item.title}}</a>
      </li>
    </ul>
  </div>
</template>

<script>
import { onMounted, reactive, toRefs } from 'vue'
import { getArticleList } from '../../api/article'
export default {
  name: 'channel',
  setup () {
    const state = reactive({
      list: []
    })
    // 调取api获取数据
    const getNewsList = () => {
      console.log(state)
      const params = {
        page: 1,
        pageSize: 5,
        
      }
      getArticleList(params).then(res => {
        console.log(res)
        const { data } = res
        state.list = data.data
      })
    }
    onMounted(getNewsList)
    // ...toRefs()将state里面得对象解构
    return {
      ...toRefs(state)
    }
  }
}
</script>


<style>

</style>