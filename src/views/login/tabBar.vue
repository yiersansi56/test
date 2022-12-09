<template>

  <div id="tab-bar">
    <tab-bar-item path="/home">
      <i slot="item-icon-active" class="el-icon-message-solid" />
      <i slot="item-icon" class="el-icon-bell" />
      <div slot="item-text">首页</div>
    </tab-bar-item>

    <tab-bar-item path="/my">
      <i slot="item-icon-active" class="el-icon-user-solid" />
      <i slot="item-icon" class="el-icon-user" />
      <div slot="item-text">我的</div>
    </tab-bar-item>

  </div>

</template>

<script>
import TabBarItem from '../login/tabBarItem'
export default {
  name: 'TabBar',
  components: { TabBarItem },
  data() {
    return {

    }
  },
  methods: {

  }
}

</script>

<style>
#tab-bar {
  display: flex;
  background-color: #f6f6f6;
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  box-shadow: 0 -1px 1px rgba(100, 100, 100, 0.08);
}
</style>

