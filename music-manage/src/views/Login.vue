<template>
    <div class="login-container">
        <div class="title">{{musicName}}</div>
        <div class="login">
            <el-form :model="ruleForm" :rules="rules">
                <el-form-item prop="username">
                    <el-input v-model="ruleForm.username" placeholder="Username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" v-model="ruleForm.password" placeholder="Password" @keyup.enter="submitForm"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="login-btn" type="primary" @click="submitForm">Log In</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, getCurrentInstance, ref, reactive } from "vue";
import mixin from "@/mixins/mixin"
import { HttpManager } from "@/api/index"
import { RouterName, MUSICNAME } from "@/enums"

export default defineComponent({
    setup() {
        
        const { proxy } = getCurrentInstance() as any;
        const { routerManager } = mixin();

        const musicName = ref(MUSICNAME);
        const ruleForm = reactive({
            username: "",
            password: "",
        });

        const rules = reactive({
            username: [{ required: true, message: "Please enter your username.", trigger: blur}],
            password: [{ required: true, message: "Please enter your password.", trigger: blur}],
        })

        async function submitForm() {
            let username = ruleForm.username;
            let password = ruleForm.password;

            const result = (await HttpManager.getLoginStatus({username,password})) as ResponseBody;
            (proxy as any).$message({
                message: result.message,
                type: result.type,
            });

            if (result.success) routerManager(RouterName.Info, { path: RouterName.Info});
        }

        return {
            musicName,
            ruleForm,
            rules,
            submitForm,
        };
    },
})

</script>

<style scoped>
.login-container {
  position: relative;
  background: url("../assets/images/background.jpg");
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
  width: 100%;
  height: 100%;
}

.title {
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -230px;
  text-align: center;
  font-size: 30px;
  font-weight: 600;
  color: #fff;
}

.login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 300px;
  margin: -150px 0 0 -190px;
  padding: 40px;
  border-radius: 5px;
  background: #fff;
}

.login-btn {
  width: 100%;
}
</style>
