const { defineConfig } = require('@vue/cli-service')
const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers')
module.exports = defineConfig({
  publicPath:"./",
  transpileDependencies: true,
  lintOnSave:false,
  css:{
    loaderOptions:{
      scss:{
        additionalData:'@import "./src/styles/style.scss";'
      }
    }
  },
  devServer: {
  
    open: true,
    https: false,
    proxy: {
      'api': {
        target: 'http://c.m.163.com/nc/article/headline/T1348647853363/0-40.html',
        ws: true,
        secure: false, 
        changeOrigin: true,
        pathRewrite: {
          '^api': ''
      }}}},
  configureWebpack:{
    plugins:[
      AutoImport({
        resolvers: [ElementPlusResolver()],
      }),
      Components({
        resolvers: [ElementPlusResolver()],
      }),
    ],
  }
})
