import { createStore } from 'vuex'
import { AXIOS } from '@/http/http-common'

const state = {
  username: '',
  password: '',
  auth: false,
  points: []
}

const getters = {
  getUsername: state => {
    return state.username
  },
  getPassword: state => {
    return state.password
  },
  isAuth: state => {
    return state.auth
  },
  getPoints: state => {
    return state.points
  }
}

const mutations = {
  auth_login: (state, data) => {
    const { username, password, token } = data
    state.username = username
    state.password = password
    state.auth = true
    window.localStorage.currentUser = JSON.stringify({
      username,
      password,
      token
    })
  },
  auth_logout: state => {
    window.localStorage.currentUser = null
    state.points = []
    state.username = ''
    state.password = ''
    state.auth = false
  },
  add_entry: (state, point) => {
    state.points.push(point)
  },
  load_points: (state, data) => {
    state.points = data
  },
  clear_points: (state) => {
    state.points = []
  }
}

const actions = {
  login: (context, data) => {
    context.commit('auth_login', data)
  },
  logout: context => {
    context.commit('auth_logout')
  },
  update_login: () => {
    if (localStorage.getItem('currentUser') !== 'null') {
      const user = JSON.parse(localStorage.getItem('currentUser'))
      state.username = user.username
      state.password = user.password
      state.auth = true
    }
  },
  add: (context, data) => {
    context.commit('add_entry', data)
  },
  loadPoints: (context) => {
    AXIOS.get('point/get_points', {
      params: {
        username: state.username
      },
      headers: {
        Authorization: 'Basic ' + btoa(state.username + ':' + state.password)
      }
    })
      .then(response => {
        context.commit('load_points', response.data)
      })
      .catch(error => {
        this.$toast.add({ severity: 'error', summary: 'Cannot load points history', detail: 'Due to ' + error.message, group: 'tl', life: 3000 })
      })
  },
  clear: (context) => {
    context.commit('clear_points')
  }
}

const store = createStore({
  state,
  getters,
  mutations,
  actions
})

export default store
