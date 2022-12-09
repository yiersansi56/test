// 公共状态存储管理器
const news = {
    state: {
      // 用户选择的频道
      channel: '',
      // 起始位置
      start: 0,
      // 一次向接口接取新闻的条数
      num: 10,
      // 存放拉取下来的新闻
      newsData: [],
      // 当前正在查看的新闻
      newsIndex: -1,
      // 是否加载状态
      loading: false
    },
  
    mutations: {
  
      SET_CHANNEL: (state, channel) => {
        state.channel = channel
      },
      SET_START: (state, start) => {
        state.start = start
      },
      SET_NUM: (state, num) => {
        state.num = num
      },
  
      SET_NEWS: (state, news) => {
        state.newsData = news
      },
  
      SET_NEWS_INDEX: (state, newsIndex) => {
        state.newsIndex = newsIndex
      },
      SET_LOADING: (state, loading) => {
        state.loading = loading
      }
  
    },
  
    actions: {
      setChannel({ commit }, channel) {
        return new Promise(resolve => {
          commit('SET_CHANNEL', channel)
        })
      },
  
      setStart({ commit }, start) {
        return new Promise(resolve => {
          commit('SET_START', start)
        })
      },
  
      setNum({ commit }, num) {
        return new Promise(resolve => {
          commit('SET_NUM', num)
        })
      },
  
      setNews({ commit }, news) {
        return new Promise(resolve => {
          commit('SET_NEWS', news)
        })
      },
      setNewsIndex({ commit }, newsIndex) {
        return new Promise(resolve => {
          commit('SET_NEWS_INDEX', newsIndex)
        })
      },
      setLoading({ commit }, loading) {
        return new Promise(resolve => {
          commit('SET_LOADING', loading)
        })
      }
    }
  }
  
  export default news
  
  