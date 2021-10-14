<template>
  <div class="container">
    <!-- 引入boostrap的类 -->
  <GlobalHeader :user="currentUser" />
    <validateForm @form-submit="onFormSubmit">
      <div class="mb-3">
        <label for="validateInput">电子邮箱</label>
        <validateInput
          placeholder="请输入邮箱地址"
          id="validateInput"
          :rules="emailRules"
          type="text"
          ref="inputRef"
          v-model="emailVal"
        />
      </div>
      <div class="mb-3">
        <label for="exampleInputPassword1" class="form-label">密码</label>
        <validateInput
          type="password"
          class="form-control"
          id="exampleInputPassword1"
          placeholder="请输入密码"
          :rules="passwordRules"
          v-model="passwordVal"
        />
      </div>
      <template #submit>
        <button class="btn btn-primary">登入</button>
      </template>
    </validateForm>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, reactive, ref } from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import ColumnList, { ColumnProps } from './components/ColumnList.vue'
import GlobalHeader, { UserProps } from './components/GlobalHeader.vue'
import validateInput, { RulesProp } from './components/ValidateInput.vue'
import validateForm, { emitter } from './components/validateForm.vue'
const emailReg = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
interface RuleProp {
  type: 'required' | 'email';
}
const testData: ColumnProps[] = [
  {
    id: 1,
    title: 'test1的专栏',
    description: '这是的test1专栏，有一段非常有意思的简介，可以更新一下欧',
    avatar:
      'http://vue-maker.oss-cn-hangzhou.aliyuncs.com/vue-marker/5ee22dd58b3c4520912b9470.jpg?x-oss-process=image/resize,m_pad,h_100,w_100'
  },
  {
    id: 2,
    title: 'test2的专栏',
    description: '这是的test2专栏，有一段非常有意思的简介，可以更新一下欧',
    avatar:
      'http://vue-maker.oss-cn-hangzhou.aliyuncs.com/vue-marker/5ee22dd58b3c4520912b9470.jpg?x-oss-process=image/resize,m_pad,h_100,w_100'
  },
  {
    id: 3,
    title: 'test2的专栏',
    description: '这是的test2专栏，有一段非常有意思的简介，可以更新一下欧',
    avatar:
      'http://vue-maker.oss-cn-hangzhou.aliyuncs.com/vue-marker/5ee22dd58b3c4520912b9470.jpg?x-oss-process=image/resize,m_pad,h_100,w_100'
  },
  {
    id: 4,
    title: 'test2的专栏',
    description: '这是的test2专栏，有一段非常有意思的简介，可以更新一下欧',
    avatar:
      'http://vue-maker.oss-cn-hangzhou.aliyuncs.com/vue-marker/5ee22dd58b3c4520912b9470.jpg?x-oss-process=image/resize,m_pad,h_100,w_100'
  },
  {
    id: 5,
    title: 'test2的专栏',
    description: '这是的test2专栏，有一段非常有意思的简介，可以更新一下欧'
    // avatar: 'http://vue-maker.oss-cn-hangzhou.aliyuncs.com/vue-marker/5ee22dd58b3c4520912b9470.jpg?x-oss-process=image/resize,m_pad,h_100,w_100'
  },
  {
    id: 5,
    title: 'test2的专栏',
    description: '这是的test2专栏，有一段非常有意思的简介，可以更新一下欧',
    avatar:
      'http://vue-maker.oss-cn-hangzhou.aliyuncs.com/vue-marker/5ee22dd58b3c4520912b9470.jpg?x-oss-process=image/resize,m_pad,h_100,w_100'
  }
]
const currentUser: UserProps = {
  isLogin: true,
  name: 'Bob',
  id: 1
}
export default defineComponent({
  name: 'App',
  components: {
    GlobalHeader,
    validateInput,
    validateForm
  },
  setup () {
    const emailRef = reactive({
      val: '',
      error: false,
      message: ''
    })

    const emailVal = ref('')
    const validateEmail = () => {
      if (emailRef.val.trim() === '') {
        emailRef.error = true
        emailRef.message = 'can not be empty'
      } else if (!emailReg.test(emailRef.val)) {
        emailRef.error = true
        emailRef.message = 'should be valid'
      }
    }

    const emailRules:RulesProp = [
      { type: 'required', message: '电子邮箱地址不能为空' },
      { type: 'email', message: '请输入正确的邮箱格式' }
    ]
    const passwordVal = ref('')
    const passwordRules:RulesProp = [
      { type: 'password', message: '密码不能为空' }
    ]
    const inputRef = ref<any>()
    const onFormSubmit = (test:boolean) => {
      console.log('result = ', inputRef.value.validateInput())
    }
    return {
      list: testData,
      currentUser,
      emailRef,
      validateEmail,
      emailRules,
      passwordRules,
      emailVal,
      onFormSubmit,
      inputRef,
      passwordVal
    }
  }
})
</script>
