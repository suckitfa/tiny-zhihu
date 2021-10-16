<template>
    <pre>{{route}}</pre>
</template>
<script lang="ts">
import { testData, testPosts } from '@/testData'
import { defineComponent } from 'vue'
import { useRoute } from 'vue-router'
export default defineComponent({
  setup () {
    const route = useRoute()
    // 将id转为数字
    const currentId = +route.params.id
    const column = testData.find(c => c.id === currentId)
    const list = testPosts.filter(post => post.columnId === currentId)
    return {
      column,
      list,
      route
    }
  }
})
</script>
