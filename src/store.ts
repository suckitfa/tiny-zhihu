import { createStore } from 'vuex'
import { testData, testPosts } from './testData'
export interface UserPorps {
    isLogin:boolean;
    name?:string;
    id?:number;
    columnId?:number;
}
export interface ColumnProps {
  id: number;
  title: string;
  avatar?: string;
  description: string;
}
export interface PostProps {
  id: number;
  title: string;
  content: string;
  image?: string;
  createdAt: string;
  columnId: number;
}
// 全局数据类型
export interface GlobalDataProps {
    columns:ColumnProps[];
    posts:PostProps[];
    user:UserPorps;
}

const store = createStore<GlobalDataProps>({
  state: {
    columns: testData,
    posts: testPosts,
    user: {
      isLogin: false,
      name: 'bob',
      columnId: 1
    }
  },
  mutations: {
    login (state) {
    // 更新登入状态信息
      state.user = { ...state.user, isLogin: true, name: 'bob' }
    },
    createPost (state, newPost) {
      state.posts.push(newPost)
    },
    logout (state) {
      state.user = { ...state.user, isLogin: false }
    }
  },
  getters: {
    biggerColumnsLen (state) {
      return state.columns.filter(c => c.id > 2).length
    },
    getColumnById: (state) => (id:number) => {
      return state.columns.find(c => c.id === id)
    },
    getPostsByCid: (state) => (cid:number) => {
      return state.posts.filter(post => post.columnId === cid)
    }
  }
})

export default store
