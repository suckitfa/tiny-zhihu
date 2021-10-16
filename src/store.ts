import { createStore } from 'vuex'
import { testData, testPosts, ColumnProps, PostProps } from './testData'
interface UserPorps {
    isLogin:boolean;
    name?:string;
    id?:number;
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
      isLogin: false
    }
  },
  mutations: {
    login (state) {
    // 更新登入状态信息
      state.user = { ...state.user, isLogin: true, name: 'bob' }
    }
  }
})

export default store
