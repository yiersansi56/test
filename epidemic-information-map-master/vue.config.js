const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: process.env.NODE_ENV === 'production'
    ? '/epidemic-information-map/'
    : '/',
  chainWebpack: config => {
    config.when(process.env.NODE_ENV === 'production', config => {
      /* 设置打包入口 */
      config.entry('app').clear().add('./src/main.js')

      config.set('externals', {
        vue: 'Vue',
        axios: 'axios',
        echarts: 'echarts',
      })

      config.plugin('html').tap(args => {
        //添加参数isProd
        args[0].isProd = true
        return args
      })
    })
    config.when(process.env.NODE_ENV === 'development', config => {
      config.entry('app').clear().add('./src/main.js')
      config.plugin('html').tap(args => {
        //添加参数isProd
        args[0].isProd = false
        return args
      })
    })
  }
})
