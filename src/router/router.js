/* eslint-disable */
import { createRouter, createWebHistory } from 'vue-router'
import TableView from '@/components/TableView'
import AuthMain from '@/components/AuthMain'

const routerHistory = createWebHistory()

const router = createRouter({
  history: routerHistory,
  routes: [
    {
      path: '/',
      name: 'AuthMain',
      component: AuthMain
    },
    {
      path: '/login',
      name: 'AuthMain',
      component: AuthMain
    },
    {
      path: '/table',
      name: 'TableView',
      component: TableView
    }]
})

export default router
