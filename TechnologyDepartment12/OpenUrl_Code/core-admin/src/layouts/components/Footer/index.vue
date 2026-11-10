<template>
  <div class="footer flx-center">
    <a href="https://beian.miit.gov.cn/" target="_blank">
       v{{ version }} | Copyright © 2022-{{ new Date().getFullYear() }} 
    </a>
  </div>
</template>
<script setup lang="ts">
// import pkg from 'package.json';
// const version: string = pkg.version;
const version: string = "0.1.0";
</script>
<style scoped lang="scss">
@import './index.scss';
</style>
