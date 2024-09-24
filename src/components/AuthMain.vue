<template>
    <div div="Home">
      <Toast position="top-left" group="tl" />
      <div class="main_container">
        <div class="login_container w-full md:w-5 flex flex-column align-items-center justify-content-center gap-3 py-5">
          <div class="flex flex-wrap justify-content-center align-items-center gap-2">
            <label class="w-6rem">Username</label>
            <InputText id="username" type="text" class="w-12rem" style="margin-left: 20px" v-model="username"/>
          </div>
          <div class="flex flex-wrap justify-content-center align-items-center gap-2" style="margin-top: 20px">
            <label class="w-6rem">Password</label>
            <InputText id="password" type="password" class="w-12rem" style="margin-left: 23px" v-model="password"/>
          </div>
          <Button @click="login" label="Login" icon="pi pi-user" class="w-10rem mx-auto" style="margin-top: 20px; width: 150px; margin-left: 25px"></Button>
          <Button @click="signup" label="Sign Up" icon="pi pi-user-plus" severity="success" class="w-10rem" style="width: 150px; margin-left: 25px"></Button>
        </div>
      </div>
    </div>
</template>

<script>
import { AXIOS } from '@/http/http-common'
export default {
  name: 'AuthMain',
  data () {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    async login () {
      AXIOS.post('user/sign_in', { username: this.$data.username, password: this.$data.password })
        .then(response => {
          if (response.status === 200) {
            this.$store.dispatch('login', { username: this.$data.username, password: this.$data.password })
            this.$toast.add({ severity: 'success', summary: 'Successful login', detail: 'You can go to table', group: 'tl', life: 3000 })
          }
        })
        .catch(error => {
          if (error.response.status === 401) {
            this.$toast.add({ severity: 'error', summary: 'Does not login', detail: 'Bad credentials', group: 'tl', life: 3000 })
          } else {
            this.$toast.add({ severity: 'error', summary: 'Does not login', detail: 'Due to' + error.message, group: 'tl', life: 3000 })
          }
        })
    },
    async signup () {
      AXIOS.post('user/sign_up', { username: this.$data.username, password: this.$data.password })
        .then(response => {
          if (response.status === 201) {
            this.$toast.add({ severity: 'success', summary: 'Successfully registered', detail: 'Login to go to table', group: 'tl', life: 3000 })
          }
        })
        .catch(error => {
          if (error.response.status === 409) {
            this.$toast.add({ severity: 'error', summary: 'Does not registered', detail: 'Username is already taken', group: 'tl', life: 3000 })
          } else {
            this.$toast.add({ severity: 'error', summary: 'Could not registered', detail: 'Due to ' + error.message, group: 'tl', life: 3000 })
          }
        })
    }
  }
}
</script>

<style >
.main_container {
  border-radius: 2rem;
  padding-bottom: 50px;
  width: 100%;
  margin-top: 100px;
  background-image: linear-gradient(to right, var(--bluegray-500), var(--bluegray-800));
}
.login_container {
  padding-top: 100px;
  width: 46%;
  margin-left: 25%;
  padding-bottom: 50px;
}
</style>
