/* eslint-disable */
import { createApp, onMounted } from 'vue'
import App from './App'
import router from './router/router'
import store from './store/index'
import PrimeVue from 'primevue/config'
import InputText from 'primevue/inputtext'
import InputNumber from 'primevue/inputnumber'
import Button from 'primevue/button'
import Toast from 'primevue/toast'
import Toolbar from 'primevue/toolbar'
import Divider from 'primevue/divider'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Tag from 'primevue/tag'
import Badge from 'primevue/badge'
import BadgeDirective from 'primevue/badgedirective'
import ToastService from 'primevue/toastservice'

import './assets/app.css'
import 'primevue/resources/themes/lara-light-green/theme.css'
import 'primevue/resources/primevue.min.css'
import 'primeicons/primeicons.css'

const app = createApp(App)
app.use(router)
app.use(store)
app.use(PrimeVue)
app.use(ToastService)
app.component('Tag', Tag)
app.component('Badge', Badge)
app.component('DataTable', DataTable)
app.component('Column', Column)
app.component('InputNumber', InputNumber)
app.component('Divider', Divider)
app.component('Toolbar', Toolbar)
app.component('Button', Button)
app.component('InputText', InputText)
app.component('Toast', Toast)
app.directive('badge', BadgeDirective);
app.mount('#app')
