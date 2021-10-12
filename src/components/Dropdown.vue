<template>
  <div class="dropdown" ref="dropdownRef">
    <a href="#" class="btn btn-outline-light my-2 dropdown-toggle" @click.prevent="toggleOpen">
      {{title}}
    </a>
  </div>
  <ul class="dropdown-menu" :style="{display:'block'}" v-if="isOpen">
    <slot> </slot>
  </ul>
</template>
<script lang="ts">
import { computed, defineComponent, onMounted, onUnmounted, ref, watch } from 'vue'
import useClickOutside from '../hooks/useClickOutside'
export default defineComponent({
  name: 'Dropdown',
  props: {
    title: {
      type: String,
      requrired: true,
      default: '标题'
    }
  },
  // 这个函数中没法使用this
  setup () {
    const dropdownRef = ref<null|HTMLElement>(null)
    const isOpen = ref(false)
    const toggleOpen = () => {
      isOpen.value = !isOpen.value
    }
    const isClickOutside = useClickOutside(dropdownRef)
    // 监听器
    watch(isClickOutside, () => {
      if (isClickOutside.value && isOpen.value) {
        isOpen.value = false
      }
    })
    return {
      isOpen,
      toggleOpen,
      dropdownRef
    }
  }
})
</script>
