// import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
// import HomeView from "../views/HomeView.vue";

// const routes: Array<RouteRecordRaw> = [
//   {
//     path: "/",
//     name: "home",
//     component: HomeView,
//   },
//   {
//     path: "/about",
//     name: "about",
//     // route level code-splitting
//     // this generates a separate chunk (about.[hash].js) for this route
//     // which is lazy-loaded when the route is visited.
//     component: () =>
//       import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
//   },
// ];

// const router = createRouter({
//   history: createWebHashHistory(),
//   routes,
// });

// export default router;

import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/Home',
        component: () => import("@/views/Home.vue"),
        meta: { title: 'Home' },
        children: [

          {
            path: '/Info',
            component: () => import('@/views/InfoPage.vue'),
            meta: { title: 'Info' }
          },
          {
            path: '/Consumer',
            component: () => import('@/views/Consumer.vue'),
            meta: { title: 'Consumer' }
          },
          {
            path: '/Collect',
            component: () => import('@/views/Collect.vue'),
            meta: { title: 'Collect' }
          },
          {
            path: '/Comment',
            component: () => import('@/views/Comment.vue'),
            meta: { title: 'Comment' }
          },
          {
            path: '/singer',
            component: () => import('@/views/Singer.vue'),
            meta: { title: 'Singer' }
          },
          {
            path: '/ListSong',
            component: () => import('@/views/ListSong.vue'),
            meta: { title: 'ListSong' }
          },
          {
            path: '/SongList',
            component: () => import('@/views/SongList.vue'),
            meta: { title: 'SongList' }
          },
          {
            path: '/song',
            component: () => import('@/views/Song.vue'),
            meta: { title: 'Song' }
          },

        ]
    },
    {
        path: '/',
        component: () => import('@/views/Login.vue')
    }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;