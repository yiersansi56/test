<template>
  <div class="body">
    <div>
    <div class="word" style="position: absolute;width: 400px;height: 300px;border: 4px solid black;display: inline-block;left: 37%;top: 40%;border-radius: 10px">
      <h2 style="display: block;float: top;height: 30%;width: 100%;font-size: 50px;letter-spacing: 5px;margin-top:50px;margin-left: 30px;color: #eeeeee">Welcome to</h2>
      <h2 style="display: block;float: top;height: 30%;width: 100%;font-size: 42px;letter-spacing: 8px;color: #eeeeee">救援物资管理系统</h2>
    </div>
      <video src="../assets/imgs/kangyi.mp4" loop autoplay muted class="video">
        <div class="zimu">welcome to 救援物资管理系统</div>
      </video>
    </div>
      <div class="navbox">
        <div class="image" style="z-index: -999">
          <img>
        </div>
        <div class="nav">
          <ul class="ul_nav">
            <li class="li_nav">关于我们</li>
<!--            <li class="li_nav">网页模板</li>-->
<!--            <li class="li_nav">网页制作</li>-->
<!--            <li class="li_nav">画师预约</li>-->
            <li class="li_nav"><a href="Login">登录</a></li>
          </ul>
        </div>

      </div>
<
    <div class="carousel">
      <div class="leftbtn" @click="leftTo"><h2 style="font-size: 30px;top:46%;left:40%;position:absolute"><</h2></div>
      <div class="content">
        <ul class="ul_content" >
          <li class="li_content appear1">嘘寒问暖问病情，脚步匆匆巡病房。打针送药脸含笑，病人家属多赞扬。关怀备至细节讲，不厌其烦服务忙。你们是白衣天使，为人间送来安康。愿白衣天使如意吉祥！</li>
          <li class="li_content appear2">致敬白衣天使，你们是最美的逆行者，你们无私，无畏，无惧，你们勇往直前，战无不胜！你们是中国最闪耀的明星！中国的骄傲！</li>
          <li class="li_content appear3">岁月静好，真的是背后有一群人默默做着负重前行的事，谢谢你们。今年的病毒也是要辛苦你们了。希望今年的病毒早日被你们攻破，武汉加油！中国加油。</li>
          <li class="li_content appear4">把苦、累、怨留给自己，将乐、安、康送给病人。谢谢你们用生命。守护生命。致敬每一位与死神搏斗、奋战在一线的所有工作人员！医生总给人无限的希望与阳光，向你们致敬。</li>
          <li class="li_content appear5">穿上白大褂。无论怎样的病人，都要尽力去帮患者，这就是我们的专业精神。再次致敬白衣天使们。你们是最了不起的。</li>
        </ul>
      </div>
      <div class="rightbtn" @click="rightTo"><h2 style="font-size: 30px;top:46%;left:40%;position:absolute">></h2></div>
    </div>
    <div style="height: 100px;width: 100%;background-color: #333333;font-size: 40px;text-align: center;line-height: 100px"><span>版权所有-救援物资管理系统小组</span></div>
  </div>
</template>
<script>
export default {
  name: "First",
  methods:{
    leftTo(){
      this.num++;
      $('.li_content').eq(this.num-1).css('display','none');
      if(this.num>4){
        this.num=0;
      }
      $('.li_content').eq(this.num).css('display','flex');


      let left=this.arr[4];
      for(let i=3;i>=0;i--){
        this.arr[i+1]=this.arr[i];
      }
      this.arr[0]=left;


      for(let i=0;i<5;i++){
        $('.li_content').eq(i).css('left',this.arr[i]);
      }

    },
    rightTo(){
      console.log(this.num);
      this.num--;
      $('.li_content').eq(this.num+1).css('display','none');
      if(this.num<0){
        this.num=4;
      }
      $('.li_content').eq(this.num).css('display','flex');


      let right=this.arr[0];
      for(let i=1;i<=4;i++){
        this.arr[i-1]=this.arr[i];
      }
      this.arr[4]=right;
      console.log(this.arr);

      for(let i=0;i<5;i++){
        $('.li_content').eq(i).css('left',this.arr[i]);
      }
    }
  },
  data(){
    return{
      num:2,
      arr:['-75%','-25%','25%','75%','125%']
    }
  }
};
window.addEventListener('scroll',function (){
  let top=window.scrollY;
  if(top!=0){
    $('.video').css('height','0');
    $('.navbox').css('display','block');
  }else{
    $('.video').css('height','100vh');
    $('.navbox').css('display','none');
  }
})
</script>

<style scoped>

.body{

}
video{
  width:99vw;
  height:100vh;
  transition: 0.2s;
}
.video::after{
  content: "hhhh";
  width: 1000px;
  height: 400px;
  z-index: 999;
}
.navbox{
  position: fixed;
  z-index: 999;
  top: 0;
  display: none;
  width:100vw;
  height: 10vh;
  background-color:white;
  border-bottom:1px solid black;
}
.navbox::before{
  content: "救援物资管理系统";
  line-height: 10vh;
  font-size: 40px;
  letter-spacing: 5px;
  left:40%;
  position: absolute;
}

.ul_nav{
  list-style: none;
  height: 100%;

}
.li_nav{
  display: flex;
  align-items: center;
  float: right;
  width: 140px;
  height: 100%;
  justify-content: center;
  background: white;
  transition: 0.2s;
  cursor: pointer;
  margin-top: -21px;
}
.li_nav a{
  text-decoration: none;
  color: #333333;
}
.li_nav:nth-last-child(1):hover{
  transition: 0.2s;
  background-color:lightgreen;
}
.li_nav:nth-last-child(2):hover{
  transition: 0.2s;
  background-color: lavender;
}
.nav{
  height: 100%;
}
.carousel{
  display: block;
  position: relative;
  background-color: white;
  width: 100%;
  margin-top: 150px;
  height: 700px;
}
.leftbtn{
  display: block;
  position: absolute;
  left: 0;
  top: 150px;
  z-index: 2;
  height: 400px;
  width: 100px;
  background-color: pink;
}
.rightbtn{
  display: block;
  position: absolute;
  right: 0;
  top: 150px;
  z-index: 2;
  height: 400px;
  width: 100px;
  background-color: pink;
}
.content{
  display: flex;
  position: absolute;
  align-items: center;
  justify-items: center;
  top: 100px;
  left:25%;
  width: 50%;
  height: 400px;
  box-sizing: border-box;
  padding-left: 140px;
  background-image: url("../assets/imgs/追赶活动.png");
  background-repeat: no-repeat;
  margin-left: 200px;
  margin-top: -20px;
}
.ul_content{
  display: flex;
  justify-content: center;
  align-items: center;
  justify-items: center;
  list-style: none;
  height: 100%;
  width: 100%;
  z-index: 1;
  /*background-image: url("../assets/imgs/追赶活动.png");*/
  /*background-repeat: no-repeat;*/
  /*background-size:90%;*/

}
.li_content{
  height: 60%;
  width: 70%;
  position: absolute;
  display: flex;
  float: left;
  justify-items: center;
  justify-content: center;
  align-items: center;
  transition: 0.2s;
  border: 2px solid black;
  background-color: transparent;
  backdrop-filter: blur(3px);
  border-radius: 3px;
  margin-top: 130px;
  margin-left: -290px;
  text-indent: 25px;
  font-size: 25px;

}
.appear1{
  display: none;
  left: -75%;
  /*background-color: #111;*/
  transition: 0.2s;
}
.appear2{
  display: none;
  left: -25%;
  /*background-color: #222;*/
  transition: 0.2s;
}
.appear3{
  left: 25%;
  /*background-color: #333;*/
  transition: 0.2s;
}
.appear4{
  display: none;
  left: 75%;
  /*background-color: #444;*/
  transition: 0.2s;
}
.appear5{
  display: none;
  left: 125%;
  /*background-color: #555;*/
  transition: 0.2s;
}
</style>