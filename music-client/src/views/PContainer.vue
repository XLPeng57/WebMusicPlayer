<template>
  <el-container>
    <el-header>
      <Header></Header>
    </el-header>
    <el-main>
      <router-view />
      <CurrentPlay></CurrentPlay>
      <PlayBar></PlayBar>
      <ScrollTop></ScrollTop>
      <Audio></Audio>
    </el-main>
    <el-footer>
      <Footer></Footer>
    </el-footer>
  </el-container>
</template>

<script lang="ts" setup>
import { getCurrentInstance } from "vue";
import Header from "@/components/layouts/Header.vue";
import CurrentPlay from "@/components/layouts/CurrentPlay.vue";
import PlayBar from "@/components/layouts/PlayBar.vue";
import ScrollTop from "@/components/layouts/ScrollTop.vue";
import Footer from "@/components/layouts/Footer.vue";
import Audio from "@/components/layouts/Audio.vue";

const { proxy } = getCurrentInstance() as any;

if (sessionStorage.getItem("dataStore")) {
  proxy.$store.replaceState(Object.assign({}, proxy.$store.state, JSON.parse(sessionStorage.getItem("dataStore"))));
}

window.addEventListener("beforeunload", () => {
  sessionStorage.setItem("dataStore", JSON.stringify(proxy.$store.state));
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/var.scss";
@import "@/assets/css/global.scss";

.el-container {
  min-height: calc(100% - 60px);
}
.el-header {
  padding: 0;
}
.el-main {
  padding-left: 0;
  padding-right: 0;
}
</style>
