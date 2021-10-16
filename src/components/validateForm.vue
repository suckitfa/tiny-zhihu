<template>
    <form class="validate-form-container">
        <slot name="default"></slot>
        <div class="submit-area" @click.prevent="submitForm">
          <slot name="submit">
            <button type="submit" class="btn btn-primary">提交</button>
        </slot>
        </div>
    </form>
</template>
<script lang="ts">
import { defineComponent, onUnmounted } from 'vue'
import mitt, { Emitter } from 'mitt'

// 定义验证函数的类型
type ValidateFunc = ()=>boolean

// key:value => 事件：返回值
type Events = {
  'form-item-created':ValidateFunc;
}
export const emitter = mitt<Events>()

export default defineComponent({
  name: 'valideFrom',
  // 自定义事件
  emits: ['form-submit'],
  setup (props, context) {
    const funcArr:ValidateFunc[] = []

    // 循环调用传入的验证函数
    const submitForm = () => {
      const result:boolean = funcArr.map(func => func()).every(res => res)
      context.emit('form-submit', result)
    }

    const callback = (func:ValidateFunc) => {
      funcArr.push(func)
    }

    // 设置监听事件
    emitter.on('form-item-created', callback)
    onUnmounted(() => {
      emitter.off('form-item-created', callback)
      // 清空验证函数数组
      funcArr.length = 0
    })
    return {
      submitForm
    }
  }
})
</script>
