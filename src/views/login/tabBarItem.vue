<template>
  <div class="tab-bar-item" @click="itemClick">
    <div v-if="!isActive">
      <slot name="item-icon" />
    </div>
    <div v-else>
      <slot name="item-icon-active" />
    </div>
    <div><slot name="item-text" /></div>
  </div>
</template>

<script>
export default {
  name: 'TabBarItem',
  props: {
    path: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
    }
  },
  computed: {
    // 判断当前条目是否被选中
    isActive() {
      return !this.$route.path.indexOf(this.path)
    }
  },
  methods: {
    // 跳转路由
    itemClick() {
      this.$router.replace(this.path)
    }
  }

}
</script>

<style>
.tab-bar-item {
  display: flex;
  flex: 1;
  justify-content: center;
  text-align: center;
  height: 49px;
  line-height: 49px;
  cursor: pointer;
}
</style>


