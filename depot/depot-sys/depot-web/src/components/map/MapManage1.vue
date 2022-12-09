<template>
	<div  class="container">
        <div class="chart">
          <div style="text-align: center; margin-bottom: 20px; font-size: 24px">
            <b>全国疫情实时数据</b>
          </div>

            <div class="item" style="background-color: lightblue">
              <div class="item_title">境外输入</div>
              <div class="item_num" style="color: RoyalBlue">{{ 232}}</div>
              <div class="item_num item_num_plus">较昨日 <span style="color: RoyalBlue">+{{ }}</span></div>
            </div>

            <div class="item" style="background-color: aliceblue">
              <div class="item_title">无症状感染者</div>
              <div class="item_num" style="color: LightCoral"></div>
              <div class="item_num item_num_plus">较昨日 <span style="color: LightCoral">+{{ 123 }}</span></div>
            </div>

            <div class="item" style="background-color: lightgoldenrodyellow">
              <div class="item_title">现有确诊</div>
              <div class="item_num" style="color: OrangeRed">{{ econNum }}</div>
              <div class="item_num item_num_plus">较昨日 <span style="color: OrangeRed">+{{ 123 }}</span></div>
            </div>

            <div class="item" style="background-color: lightgray">
              <div class="item_title">累计确诊</div>
              <div class="item_num" style="color: Brown">{{ gntotal }}</div>
              <div class="item_num item_num_plus">较昨日 <span style="color: Brown">+{{ 123}}</span></div>
            </div>

            <div class="item" style="background-color: lightpink">
              <div class="item_title">累计死亡</div>
              <div class="item_num" style="color: #333">{{deathtotal}}</div>
              <div class="item_num item_num_plus">较昨日 <span style="color: #333">+{{ 123 }}</span></div>
            </div>

            <div class="item" style="background-color: lightgreen">
              <div class="item_title">累计治愈</div>
              <div class="item_num" style="color: green">{{addcure}}</div>
              <div class="item_num item_num_plus">较昨日 <span style="color: green">+{{123}}</span></div>
            </div>
        </div>
		<div ref="mapbox" class="box" ></div>
	</div>
</template>
<script>
    import * as echarts from 'echarts';
	import '../../utils/china'
	import jsonp from '../../jsonp';


	const option = {
		title: {
			text: '疫情地图'
		},
		series: [{
			type: 'map', //告诉echarts 要去渲染的是一个地图
			map: 'china',//告诉cehcarts要去渲染中国地图
			label: {
				//控制各个地区的汉字
				show: true, //显示各个省份名称
				//color:'red',//控制地区名的字体的颜色
				fontSize: 8,
			},
			itemStyle: {
				areaColor: '#fff' //控制地图的背景颜色
			},
			emphasis: {
				//控制鼠标滑过时的高亮样式
				itemStyle: {
					areaColor: '#c7fffd'
				}

			},
			zoom: 1.2, //控制地图的放大和缩小
			dat1:[]
		}],
		visualMap: [{
			type: 'piecewise',//数字分块
			show: true,
			splitNumber: 6,
			pieces: [{
					min: 10000
				}, // 不指定 max，表示 max 为无限大（Infinity）。
				{
					min: 1000,
					max: 9999
				},
				{
					min: 100,
					max: 999
				},
				{
					min: 10,
					max: 99
				},
				{
					min: 1,
					max: 9
				},
				{
					min: 0,
					max: 0
				}
			],
			//align:'right' // 默认是left
			inRange: {//小方块的样式
				symbol: 'rect',
				color: ['#fff', '#ffaa85', '#ff7b69', '#cc2929', '#8c0d0d', '#660208']
			},
			itemHeight: 10,
			itemWidth: 20
		}],
    toolbox: {
      feature: {
        saveAsImage: {}
      }
    },
		tooltip:{
			// position: function (pos, params, dom, rect, size) {
			//       // 鼠标在左侧时 tooltip 显示到右侧，鼠标在右侧时 tooltip 显示到左侧。
			//       var obj = {top: 60};
			//       obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 5;
			//       return obj;
			//   }
		}
	};


	export default {
		
		data() {
			return {
				gntotal:[],
				deathtotal:[],
				econNum:[],
				addcure:[],
			}
		},
		

		name: 'Map',
		mounted() {
			this.getData();
			this.created1();
			this.mychart = echarts.init(this.$refs.mapbox); //获取mapbox盒子
			this.mychart.setOption(option);
			
		},
		methods: {
			created1(){	
			//'https://c.m.163.com/ug/api/wuhan/app/data/list-total?t=333993884095'
			
		    },
			getData() {
				jsonp(//数据地址
					'http://interface.sina.cn/news/wap/fymap2020_data.d.json?_=1580892522427', {}, (erros, data) => {						
						// console.log(data);
						// console.log(data.data.list)
						
						var lists = data.data.list.map(item => {
							return {
								name: item.name,
								value: item.econNum
							}
						})
						option.series[0].data = lists;
						
						this.gntotal=data.data.gntotal;
						this.deathtotal=data.data.deathtotal;
						this.econNum=data.data.econNum;
						this.addcure=data.data.add_daily.addcure;
						
						this.mychart.setOption(option);//这行代码能执行的前提是DOM已经渲染完成才能够执行echarts初始化

				    })
			}
		},
	
	}


	
</script>

<style scoped>

.chart{
	width: 500px;
	margin: 0 auto;
	padding: 20px;
	box-shadow: 0 5px 15px -5px rgba(0,0,0,.5);
	border-radius: 10px;float: left;
}
.container{
	background-color: rgb(226, 234, 235);
	height: 900px;
	width: 1700px;
	margin: auto;
	display: flex;
	align-items: center;
}
	.box{
		width:1600px;
		height: 800px;
		margin: auto;
		float: right;
		/* border: 2px solid aquamarine; */
	}

	.item {
  text-align: center;
  width: 163px;
  height: 150px;
  float: left;
  border-radius: 20px;
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

</style>
