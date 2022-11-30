<template>
  <div class="content">
      <div class="inputDom">
          <el-input v-model="input" placeholder="please input money"/>
          <el-button type="success" @click="MakeQrcode">生成二维码</el-button>

      </div>
      <div class="qrcode" ref="qrcodeDom"></div>

  </div>
</template>

<script setup>
import {ref,nextTick} from 'vue'
import QRcode from "qrcodejs2"
const input = ref('')
let qrcodeDom = ref(null)
let MakeQrcode=()=>{
    qrcodeDom.value.innerHTML=""
    nextTick(()=>{
        new QRcode(qrcodeDom.value,{
            text:input.value,//生成二维内容
            colorDark:"#000",//二维码颜色
            colorLight:"#fff"//二维码的背景色
          
        })

    })
}

</script>

<style lang="scss" scoped>
.qrcode{
    width: 300px;
    height: 300px;
    margin: 0 auto;
}
</style>