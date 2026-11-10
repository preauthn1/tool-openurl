<template>
  <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" size="large">
    <el-form-item prop="username">
      <el-input v-model="loginForm.username" placeholder="请输入账号">
        <template #prefix>
          <el-icon class="el-input__icon">
            <user />
          </el-icon>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item prop="password" style="margin-bottom: 0">
      <el-input
        v-model="loginForm.password"
        type="password"
        placeholder="请输入密码"
        show-password
        autocomplete="new-password"
      >
        <template #prefix>
          <el-icon class="el-input__icon">
            <lock />
          </el-icon>
        </template>
      </el-input>
    </el-form-item>
  </el-form>
  <div class="login-btn">
    <el-button :icon="CircleClose" round size="large" @click="resetForm"> 重置 </el-button>
    <el-button :icon="UserFilled" round size="large" type="primary" :loading="loading" @click="login"> 登录 </el-button>
  </div>
  <div v-if="isPreview()" style="margin-top: 20px; color: var(--el-color-warning)">
    <span>如无法登陆请联系：网站管理员</span>
  </div>
  <SliderCaptcha ref="captchaRef" @success="onSliderSuccess" @close="onCaptchaClose" />
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
import { HOME_URL } from '@/config';
import { aesEncrypt, getTimeState } from '@/utils';
import { getChallengeApi, loginApi } from '@/api/modules/system/login';
import { useUserStore } from '@/stores/modules/user';
import { useTabsStore } from '@/stores/modules/tabs';
import { useKeepAliveStore } from '@/stores/modules/keepAlive';
import { CircleClose, Lock, User, UserFilled } from '@element-plus/icons-vue';
import { initDynamicRouter } from '@/router/modules/dynamicRouter';
//import { useOptionsStore } from '@/stores/modules/options'
import { onMounted, reactive, ref } from 'vue';
import { ElNotification } from 'element-plus';
import SliderCaptcha from '@/components/Captcha/SliderCaptcha.vue';
import { getCaptchaStatus } from '@/api/modules/system/captcha';
import type { LoginParams } from '@/api/types/system/login';

const router = useRouter();
const userStore = useUserStore();
const tabsStore = useTabsStore();
const keepAliveStore = useKeepAliveStore();
//const optionsStore = useOptionsStore()

const loginFormRef = ref();
const loginRules = reactive({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
});

const loading = ref(false);
const loginForm = reactive({
  username: '',
  password: '',
  clientId: '',
  grantType: '',
  iv: '',
  requestId: ''
});

const onSliderSuccess = async () => {
  // 缓存当前表单数据，避免后续被重置
  const formData = { ...loginForm };
  await performLogin(formData);
};

const performLogin = async (formData = loginForm) => {
  loading.value = true;
  try {
    // 获取login的一次性校验接口
    const challenge = await getChallengeApi();
    const pwd = formData.password;
    const secret = challenge.data.secretKey;
    const requestId = challenge.data.requestId;
    // 登录前校验，获取secretKey 和 requestId
    const { iv, encryptedData } = aesEncrypt(pwd, secret);

    const params: LoginParams = {
      username: formData.username,
      password: formData.password, // aes加密后的密码
      clientId: formData.clientId,
      grantType: formData.grantType,
      iv: iv, // aes加密的iv
      requestId: requestId // 一次性请求id
    };
    const { data } = await loginApi(params);

    userStore.setToken(data.accessToken);
    userStore.setUserInfo(data.userInfo);

    await initDynamicRouter();

    tabsStore.closeMultipleTab();
    keepAliveStore.setKeepAliveName([]);

    // 跳转到首页
    router.push('/home/index');
    ElNotification({
      title: getTimeState(),
      message: '欢迎登录 管理后台',
      type: 'success',
      duration: 3000
    });
  } catch (error) {
    ElNotification({
      title: '登录失败',
      message: error instanceof Error ? error.message : '未知错误',
      type: 'error',
      duration: 3000
    });
  } finally {
    loading.value = false;
  }
};

const captchaRef = ref<InstanceType<typeof SliderCaptcha>>();
// login
const login = () => {
  if (!loginFormRef.value) {
    return;
  }
  loginFormRef.value.validate(async (valid: boolean) => {
    if (!valid) {
      return;
    }

    try {
      loading.value = true;
      captchaRef.value?.acceptParams(); // 打开验证码弹窗

        // // 1.执行登录接口
          // const { data } = await loginApi({ ...loginForm });
          // userStore.setToken(data.accessToken);
          // userStore.setUserInfo(data.userInfo);
    
          // // 2.添加动态路由
          // await initDynamicRouter();
          // /*      optionsStore.setReloadOptions()
          // await optionsStore.getAllDictList()*/
    
          // // 3.清空 tabs、keepAlive 数据
          // tabsStore.closeMultipleTab();
          // keepAliveStore.setKeepAliveName();
    
          // // 4.跳转到首页
          // // router.push(HOME_URL);
          // router.push('/print/setting');
          // ElNotification({
          //   title: getTimeState(),
          //   message: '欢迎登录 公路隧道压入式通风计算软件',
          //   type: 'success',
          //   duration: 3000
          // });
    } finally {
      loading.value = false;
    }
  });
};

// resetForm
const resetForm = () => {
  if (!loginFormRef.value) return;
  loginFormRef.value.resetFields();
};

const onCaptchaClose = () => {
  resetForm();
};

onMounted(() => {
  // 监听 enter 事件（调用登录）
  document.onkeydown = (e: KeyboardEvent) => {
    e = (window.event as KeyboardEvent) || e;
    if (e.code === 'Enter' || e.code === 'enter' || e.code === 'NumpadEnter') {
      if (loading.value) return;
      login();
    }
  };
});

const isPreview = () => {
  return import.meta.env.VITE_PREVIEW;
};
</script>

<style scoped lang="scss">
@import '../index.scss';
</style>
