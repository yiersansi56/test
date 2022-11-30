 <template>
  <div class="hello">
    <div class="loading-wrap" v-if="!mapIsLoading">
      <vue-loading
        type="bars"
        color="#d9544e"
        :size="{ width: '50px', height: '50px' }"
      ></vue-loading>
    </div>

    <div class="tool" v-show="mapIsLoading">
      <span @click="goBackChina">{{ firstTitle }}</span>
      <template v-if="level >= 2">
        =>
        <span @click="goBackProvince">{{ currentProvince.name }}</span>
      </template>
      <template v-if="level == 3">
        =>
        <span @click="goBackCity">{{ currentCity.name }}</span>
      </template>
    </div>
    <div class="map-wrap" v-show="mapIsLoading">
      <div id="chinaMap"></div>
    </div>
  </div>
</template>
 <script>
import * as echarts from 'echarts'
const { province, city } = require('province-city-china/data')
import { VueLoading } from 'vue-loading-template'
import { getCovData } from '../api/api.js'

export default {
  name: 'hello-world',
  components: {
    VueLoading
  },
  data() {
    return {
      firstTitle: '中国',
      myChart: '', //ecahrts实例
      currentClick: '', //当前点击的层级 province / city
      currentProvince: {}, //当前省
      currentCity: {}, //当前市
      cityState: false, //当前市级是否还有下级地图数据 如点击成都市 有， 点击北京市昌平区 无
      provinceState: false, //当前省级是否还有下级
      covData: {}, //接口获取的COVID-19数据
      hasData: false, //是否获取到了COVID-19数据，没有就不显示地图
      level: 1, //地图层级
      mapIsLoading: false, //地图是否挂载成功
      timeTxt: ''
    }
  },
  created() {
    this.getData()
  },
  mounted() {
    /* 轮询获取 hasData 状态 */
    let iCount = 0
    const p = new Promise((resolve, reject) => {
      const func = () => {
        if (this.hasData) {
          if (this.timr) clearTimeout(this.timr)
          resolve(true)
        } else {
          iCount++
          clearTimeout(this.timr)
          if (iCount > 30) reject('超时')
          else {
            this.timr = setTimeout(func, 400)
          }
        }
      }
      func()
    })
    p.then((res) => {
      console.log(res)
      if (res) {
        this.initEcharts('china', '中国')
        this.$notify({
          message: 'Welcome，鼠标悬浮查看数据，鼠标点击进入下一级地区',
          type: 'success',
          timeout: 10000
        })
      }
    }).catch((err) => {
      this.$notify({
        message: '获取数据超时！！！',
        type: 'danger',
        timeout: 10000
      })
      console.warn('加载数据出错', err)
    })
  },
  methods: {
    /* 处理数据得到省级地区疫情数据 */
    getProvinceData() {
      let res = []
      this.covData.list.forEach((item) => {
        res.push({
          name: item.name,
          total: item.value,
          deathNum: item.deathNum,
          cureNum: item.cureNum,
          jwsrNum: item.jwsrNum,
          value: item.econNum
        })
      })
      return res
    },
    /* 获取城市疫情数据 */
    getCityData(provinceName) {
      let index = this.covData.list.findIndex(
        (item) => item.name == provinceName
      )
      let res = []
      this.covData.list[index].city.forEach((item) => {
        res.push({
          name: item.mapName,
          total: item.conNum,
          deathNum: item.deathNum,
          cureNum: item.cureNum,
          value: item.econNum
        })
      })
      return res
    },
    /* 网络请求获取数据 */
    async getData() {
      const res = await getCovData()
        .then((res) => {
          this.hasData = true
          return res.data
        })
        .catch((err) => {
          this.hasData = false
          return err
        })
      /* 冻结数据，提升性能 */
      if (this.hasData) {
        this.covData = Object.freeze(res.data)
        this.timeTxt = `${res.data.times} 现有确诊`
      } else {
        console.warn(res)
      }
    },
    /* 点击返回最顶层中国的回调 */
    goBackChina() {
      this.initEcharts('china', '中国')
      this.currentProvince.name = ''
      this.currentCity.name = ''
    },
    /* 点击返回省级地图的回调 */
    goBackProvince() {
      console.log(this.currentProvince.name)
      this.initEcharts(
        this.currentProvince.province,
        this.currentProvince.name,
        '1'
      )
      this.currentCity.name = ''
    },
    /* 返回市级地区回调 */
    goBackCity() {
      this.initEcharts(this.currentCity.code, this.currentCity.name)
    },
    /* 地图配置信息、渲染地图 */
    initEcharts(pName, Chinese_, state = '0') {
      this.myChart = echarts.init(document.getElementById('chinaMap'))
      let tmpSeriesData

      if (pName === 'china') {
        this.level = 1
        tmpSeriesData = this.getProvinceData()
        let geoJson = require('../assets/mapdata/china.json')
        let nameMap = require('../assets/data/nameMap.json')
        /* 处理数据 */
        tmpSeriesData.forEach((item) => {
          item.name = nameMap[item.name]
        })
        echarts.registerMap(pName, geoJson)
          document.title="中国疫情"

      } else {
        if (this.currentClick === 'province' || state === '1') {
          this.level = 2
          let geoJson = require(`../assets/mapdata/geometryProvince/${pName}.json`)
          let nameMap = require('../assets/data/nameMap.json')
          let arr = Object.keys(nameMap)
          let index = arr.findIndex((item) => nameMap[item] == Chinese_)
          tmpSeriesData = this.getCityData(arr[index])
          console.log(Chinese_)
          echarts.registerMap(pName, geoJson)
        } else {
          this.level = 3
          let geoJson = require(`../assets/mapdata/geometryCouties/${pName}.json`)
          echarts.registerMap(pName, geoJson)
        }
        document.title=`${Chinese_}疫情`
      }
      let formatter
      /* 根据地图等级输出toolTip的模板 */
      if (this.level !== 3) {
        formatter = function (data) {
          if (data.data?.name) {
            return `地区：${data.data.name}<br/>
            ${
              data.data.value == 0
                ? `<span>现有确诊：${data.data.value}</span>`
                : `<span style="color:red">现有确诊：${data.data.value}</span>`
            }<br/>
            累计确诊：${data.data.total}<br/>
            累计治愈：${data.data.cureNum}<br/>
            累计死亡：${data.data.deathNum}<br/>
            境外输入：${data.data.jwsrNum ? data.data.jwsrNum : '无数据'}`
          } else {
            return `该地区地图有变化，或暂无数据`
          }
        }
      } else {
        formatter = '地区：{b}<br/>暂无地级行政区数据'
      }
      let option = {
        title: {
          //标题样式
          text: '中国疫情地图（数据来源新浪,仅供参考）',
          x: 'center',
          subtext: this.timeTxt,
          textStyle: {
            fontSize: 18,
            color: 'red'
          }
        },
        tooltip: {
          //这里设置提示框
          trigger: 'item', //数据项图形触发
          backgroundColor: 'rgba(0,0,0,0.7)', //提示框浮层的背景颜色。
          //字符串模板(地图): {a}（系列名称），{b}（区域名称），{c}（合并数值）,{d}（无）
          formatter: formatter,
          textStyle: {
            color: '#fff'
          }
        },
        visualMap: {
          //视觉映射组件
          type: 'piecewise',
          outOfRange: {
            color: '#eee'
          },
          pieces: [
            { min: 10000, color: '#ff0000' },
            { min: 1000, max: 10000, color: '#ff4000' },
            { min: 100, max: 1000, color: '#ff8000' },
            { min: 10, max: 100, color: '#ffbf00' },
            { min: 1, max: 10, color: '#ffff00' },
            { max: 1, color: '#ccc' }
          ]
        },

        series: [
          {
            name: Chinese_ || pName,
            type: 'map',
            map: pName,
            roam: false, //是否开启鼠标缩放和平移漫游
            label: {
              show: true, //是否显示标签
              color: '#303133',
              fontSize: '10px'
            },
            itemStyle: {},
            emphasis: {
              //是图形在高亮状态下的样式,比如在鼠标悬浮或者图例联动高亮时
              label: {
                show: true,
                fontSize: '14px'
              },
              itemStyle:{
                areaColor:'#007fff'
              }
            },
            data: tmpSeriesData, //后端数据
            top: '10%' //组件距离容器的距离
          }
        ]
      }
      this.myChart.setOption(option, true)
      this.myChart.off('click')
      if (pName === 'china') {
        // 全国时，添加click 进入省级
        this.currentClick = 'province'
        this.myChart.on('click', this.mapClick)
      } else {
        this.currentClick = 'city'
        this.myChart.on('click', this.mapClick)
      }
      this.mapIsLoading = true
    },
    //地图点击事件
    mapClick(param) {
      this.provinceState = false
      this.cityState = false
      if (this.currentClick === 'province') {
        //取到province 中的下标  去拿到对应的省json
        let index_provin = province.findIndex(
          (item) => item.name === param.name
        )
        if (index_provin !== -1) {
          this.provinceState = true
          this.currentProvince = { ...province[index_provin] }
          this.currentProvince.name = `${this.currentProvince.name}`
          //显示对应省份的方法
          this.showProvince(
            province[index_provin].province,
            province[index_provin].name
          )
        }

        !this.provinceState ? alert('暂不支持该区域地图展示!') : ''
      } else {
        /* 查找点击的城市名对应的数据 */
        let index_city = city.findIndex((item) => item.name === param.name)
        if (index_city !== -1) {
          this.cityState = true
          this.currentCity = { ...city[index_city] }
          this.currentCity.name = `${this.currentCity.name}`
          //显示对应城市的方法
          this.showProvince(city[index_city].code, city[index_city].name)
        }
        !this.cityState ? alert('暂不支持该区域地图展示!') : ''
      }
    },
    /* 展示省/城市 */
    showProvince(pName, Chinese_) {
      this.initEcharts(pName, Chinese_)
    }
  }
}
</script>
<style lang="scss" scoped>
/* 工具栏 */
.tool {
  height: 30px;
  line-height: 30px;
  color: deepskyblue;
  cursor: pointer;
  position: absolute;
  top: 10px;
  left: 10px;
  z-index: 10000;
  font-size: 18px;
  text-align: left;
}
/* 渲染地图的外层元素 */
#chinaMap {
  width: 100vw;
  height: 100vh;
}
</style>>


