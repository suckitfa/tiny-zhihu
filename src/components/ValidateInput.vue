<template>
<div class="validate-input-container pb-3">
  <input
  v-if="tag !== 'textarea'"
    class="form-control"
    :value="inputRef.val"
    @blur="validateInput"
    :class="{'is-invalid':inputRef.error}"
    @input="updateValue"
    v-bind="$attrs"
  />
  <textarea
    v-else
    :class="{'is-invalid':inputRef.error}"
    :value="inputRef.val"
    @blur="validateInput"
    @input="updateValue"
    v-bind="$attrs"
  >
  </textarea>
  <span class="invalid-feedback">{{inputRef.message}}</span>
</div>
</template>
<script lang="ts">
import { emitter } from './ValidateForm.vue'
import { defineComponent, reactive, PropType, onMounted, Prop } from 'vue'
const emailReg = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
interface RuleProp {
  type: 'required' | 'email' | 'password';
  message: string;
}
export type RulesProp = RuleProp[];
export type TagType = 'input' | 'textarea'
export default defineComponent({
  inheritAttrs: false,
  props: {
    rules: Array as PropType<RulesProp>,
    modelValue: String,
    tag: {
      type: String as PropType<TagType>,
      default: 'input'
    }
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
            case 'required':
              passed = (inputRef.val.trim() !== '')
              break
            case 'email':
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
