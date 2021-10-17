<template>
  <div class="column-detail-page w-75 mx-auto">
    <div class="column-info row mb-4 border-bottom pb-4 align-items-center" v-if="column">
      <div class="col-3 text-center">
        <img :src="column.avatar" :alt="column.title" class="rounded-circle border w-100">
      </div>
      <div class="col-9">
        <h4>{{column.title}}</h4>
        <p class="text-muted">{{column.description}}</p>
      </div>
    </div>
    <post-list :list="list"></post-list>
    <button class="btn btn-primary" @click="handleClick">显示数据</button>
  </div>
</template>

<script lang="ts">
import { defineComponent, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useStore } from 'vuex'
import { GlobalDataProps, ColumnProps } from '../store'
import PostList from '../components/PostList.vue'
export default defineComponent({
  components: {
    PostList
  },
  setup () {
    const route = useRoute()
    const store = useStore<GlobalDataProps>()
    const currentId = route.params.id
    const column = computed(() => {
      const selectColumn = store.getters.getColumnById(currentId) as ColumnProps | undefined
      return selectColumn
    })
    const list = computed(() => store.getters.getPostsByCid(currentId))
    const handleClick = () => {
      console.log('currentId = ', currentId)
      const column = store.getters.getColumnById
      console.log(typeof column)
      // console.log(column)
      // console.log(store.state.columns.find(c => c.id === currentId))
    }
    return {
      column,
      list,
      handleClick
    }
  }
})
</script>
