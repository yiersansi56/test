const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,
  devServer:{
    proxy:{
      //设置允许跨域的路径(以api为例)
      '/api':{
        //设置允许跨域请求的域名(以我请求的api域名为例)
        target: 'https://apis.tianapi.com/guonei/index?key=5652583fa9a88a8d1e2d79d403fcae5b&num=10',
         secure: false,  // 如果是https接口，需要配置这个参数
        changeOrigin:true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }

  }

})
