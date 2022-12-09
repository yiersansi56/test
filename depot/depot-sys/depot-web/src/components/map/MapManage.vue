<template>
  <div style="color: #666;font-size: 14px;">
    <div style="padding-bottom: 20px">
    </div>
    <el-row style="margin-top: 40px">
      <el-col :span="12">
        <div style="width: 650px;height: 360px ;margin-left: 50px; padding: 20px; box-shadow: 0 5px 15px -5px rgba(0,0,0,.5); border-radius: 20px;">
          <div style="text-align: center; margin-bottom: 20px; font-size: 24px">
            <b>全国疫情实时数据</b>
          </div>
          <div style="display: flex; flex-wrap:wrap; justify-content: flex-start" >
            <div class="item" style="background-color: lightblue">
              <div class="item_title">境外输入</div>
              <div class="item_num" style="color: RoyalBlue">{{ total.input ? total.input : '未更新' }}</div>
              <div class="item_num item_num_plus">较昨日 <span style="color: RoyalBlue">+{{ today.input }}</span></div>
            </div>
            <div class="item" style="background-color: aliceblue">
              <div class="item_title">无症状感染者</div>
              <div class="item_num" style="color: LightCoral">{{ extData.noSymptom ? extData.noSymptom : '未更新' }}</div>
              <div class="item_num item_num_plus">较昨日 <span style="color: LightCoral">+{{ extData.incrNoSymptom }}</span></div>
            </div>
            <div class="item" style="background-color: lightgoldenrodyellow">
              <div class="item_title">现有确诊</div>
              <div class="item_num" style="color: OrangeRed">{{ ( total.confirm - total.dead - total.heal ) ? ( total.confirm - total.dead - total.heal ) : '未更新' }}</div>
              <div class="item_num item_num_plus">较昨日 <span style="color: OrangeRed">+{{ today.confirm - today.dead - today.heal ? today.confirm - today.dead - today.heal : '' }}</span></div>
            </div>
            <div class="item" style="background-color: lightgray">
              <div class="item_title">累计确诊</div>
              <div class="item_num" style="color: Brown">{{ total.confirm ? total.confirm : '未更新' }}</div>
              <div class="item_num item_num_plus">较昨日 <span style="color: Brown">+{{ today.confirm }}</span></div>
            </div>
            <div class="item" style="background-color: lightpink">
              <div class="item_title">累计死亡</div>
              <div class="item_num" style="color: #333">{{ total.dead ? total.dead : '未更新' }}</div>
              <div class="item_num item_num_plus">较昨日 <span style="color: #333">+{{ today.dead }}</span></div>
            </div>
            <div class="item" style="background-color: lightgreen">
              <div class="item_title">累计治愈</div>
              <div class="item_num" style="color: green">{{ total.heal ? total.heal : '未更新' }}</div>
              <div class="item_num item_num_plus">较昨日 <span style="color: green">+{{ today.heal }}</span></div>
            </div>
          </div>
        </div>
      </el-col>

      <el-col :span="12" style="background-color: white;border-radius: 20px;margin-left: -20px;width:49%;height:80%;box-shadow: 0 5px 15px -5px rgba(0,0,0,.5);">
        <div id="main" style="width: 100%; height: 800px;"></div>
      </el-col>

    </el-row>
    <div class="news" style="width: 690px;height: 360px;background-color: white;margin-top: -360px;
    margin-left:45px;position: absolute;display: inline-block;border-radius: 20px;box-shadow: 0 5px 15px -5px rgba(0,0,0,.5);">
      <ul>
        <h1>实时疫情新闻</h1>
        <a :href="i.url" target="_blank" v-for="i in data" style="overflow: auto;padding: 5px">
          <img :src="i.picUrl" >
          <h3>{{i.title}}</h3>
          <h4 >发布时间: {{i.ctime}}</h4>
          <h4 >来源: {{i.source}}</h4>


<!--          <p style="text-indent: 25px">{{i.summary}}......</p>-->
        </a>
      </ul>
    </div>
  </div>

</template>


<script>
import * as echarts from 'echarts'
import '../../utils/china'

export default {
  name: "MapManage",
  data() {
    return {
      data:[],
      total: {},
      today: {},
      extData: {}
    }
  },
  mounted() {

    this.requestData()
    const options = {
      //标题样式
      title: {
        text: '中国疫情地图',
        x: "center",
        y: '5%',
        textStyle: {
          fontSize: 40,
          color: "#333",
          letterSpacing:4
        },
      },
      //这里设置提示框 (鼠标悬浮效果)
      tooltip: {
        //数据项图形触发
        trigger: 'item',
        //提示框浮层的背景颜色。 (鼠标悬浮后的提示框背景颜色)
        backgroundColor: "white",
        //字符串模板(地图): {a}（系列名称），{b}（区域名称），{c}（合并数值）,{d}（无）
        formatter: '地区：{b}<br/>现有确诊病例：{c}'
      },
      //视觉映射组件
      visualMap: {
        top: 'bottom',
        left: 'left',
        // 数据的范围
        min: 0,
        max: 300,
        text: ['高', '低'],
        realtime: true,  //拖拽时，是否实时更新
        calculable: true,  //是否显示拖拽用的手柄
        inRange: {
          // 颜色分布
          color: ['white', 'Tomato', 'orangered']
        }
      },
      series: [
        {
          name: '确诊',
          type: 'map',
          mapType: 'china',
          roam: true,
          //是否开启鼠标缩放和平移漫游
          itemStyle: {
            //地图区域的多边形 图形样式
            normal: {
              //是图形在默认状态下的样式
              label: {
                show: true,//是否显示标签
                textStyle: {
                  color: "black"
                }
              }
            },
            zoom: 1,
            //地图缩放比例,默认为1
            emphasis: {
              //是图形在高亮状态下的样式,比如在鼠标悬浮或者图例联动高亮时
              label: {show: true}
            }
          },
          top: '20%',
          left: '15%',
          data: []
        }
      ]
    }
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);

    // 获取数据
    this.request.get("/user/epidemicMap").then(res => {
      this.total = res.data.chinaTotal.total
      this.today = res.data.chinaTotal.today
      this.extData = res.data.chinaTotal.extData

      let data = [ {name: '南海诸岛', value: 0} ]
      res.data.areaTree[2].children.forEach(item => {
        data.push({ name: item.name, value: item.total.confirm - item.total.dead - item.total.heal })

        options.series[0].data = data
        myChart.setOption(options)
      })
    })

  },
  methods:{
    requestData(){
      this.$axios({
        method:'get',
        headers: {
          ContentType: "application/json",
          "Access-Control-Allow-Origin": "*",
        },
        dataType: "json",
        ContentType: "application/json;charset-utf-8",
        url:'/api',
      }).then(res=>{

        console.log(res.data.result)
        this.data = res.data.result.newslist
      })

    }


  }
}
</script>

<style>
.item {
  text-align: center;
  width: 200px;
  height: 140px;
  margin-right: 10px;
  margin-bottom: 10px;
  border-radius: 50%;
}
.item:hover{
  width: 215px;
  height: 155px;
}
.item:nth-child(3n) {
  margin-right: 0;
}
.item_title {
  font-size: 16px;
  font-weight: bold;
  margin-top: 20px;
}
.item_num {
  margin-top: 10px;
  font-size: 30px;
  font-weight: bold;
}
.item_num_plus {
  font-size: 16px;
  color: #666;
}
.news{
  overflow: auto;
  color: #333333;
  text-decoration: none;
}
.news a{
  margin:10px 30px;
  width: 600px;
  height: 150px;
  background-color: lightcyan;
  border:2px solid black ;
  border-radius: 10px;
  text-decoration: none;
  list-style: none;
  display: inline-block;
}
.news h1{
  margin-top: 10px;
  margin-left: 270px;
  font-size: 30px;

}
.news img{
  height: 140px;
  width: 140px;
  border-radius: 10px;
  float: left;
  margin-top: 5px;
  margin-right: 8px;
}
.news h3{
  margin-top: 9px;
  margin-left: 9px;
  color: #333333;
  font-size: 18px;
}
</style>
