# epidemic-information-map
基于新浪疫情信息API、province-city-china省市数据、以及echarts-china的mapJson数据
使用Vue2 + axios + echarts + vue-loading-template + vue-notifyjs 进行开发

## 效果展示

[项目效果展示 ](https://www.bilibili.com/video/BV1ZZ4y127Gf)
展示中国各省市/地区简单的疫情数据

## 目录结构
- dist : 打包输出目录
- public : html模板
- sever : 代理服务器（直接访问新浪接口会跨域）
- src
  - api : 接口及axios配置
  - assets
    - data : 地区名称映射
    - mapData : 地图mapJson数据
  - components:组件

## 安装依赖
```
npm install
```
## 启动后台服务

```
npm run start
```
## 启动Vue项目
```
npm run serve
```

