<template>
<div class="validate-input-container pb-3">
  <input
    class="form-control"
    :value="inputRef.val"
    @blur="validateInput"
    :class="{'is-invalid':inputRef.error}"
    @input="updateValue"
    v-bind="$attrs"
  />

  <span class="invalid-feedback">{{inputRef.message}}</span>
</div>
</template>
<script lang="ts">
import { defineComponent, reactive, PropType, onMounted } from 'vue'
import { emitter } from './ValidateForm.vue'
const emailReg = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
interface RuleProp {
  type: 'required' | 'email' | 'password';
  message: string;
}
export type RulesProp = RuleProp[];
export default defineComponent({
  inheritAttrs: false,
  props: {
    rules: Array as PropType<RulesProp>,
    modelValue: String
  },
  setup (props, context) {
    const inputRef = reactive({
      val: props.modelValue || '',
      error: false,
      message: ''
    })
    // 这里支持v-model
    const updateValue = (e:KeyboardEvent) => {
      const targetValue = (e.target as HTMLInputElement).value
      inputRef.val = targetValue
      context.emit('update:modelValue', targetValue)
    }

    const validateInput = () => {
      if (props.rules) {
        const allPassed = props.rules.every(rule => {
          let passed = true
          inputRef.message = rule.message
          switch (rule.type) {
            case 'email':
              passed = (inputRef.val.trim() !== '')
              break
            case 'required':
              passed = emailReg.test(inputRef.val)
              break
            case 'password':
              passed = (inputRef.val.trim() !== '')
              break
            default:
              break
          }
          return passed
        })
        inputRef.error = !allPassed
        return allPassed
      }
      return true
    }

    onMounted(() => {
      emitter.emit('form-item-created', validateInput)
    })
    return {
      inputRef,
      validateInput,
      updateValue
    }
  }
})
</script>
