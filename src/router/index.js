import {createRouter, createWebHistory} from "vue-router";

export const publicRoutes = [
    {
        path: "/",
        name: "index",
        redirect: to => {
            return "/add";
        },
        component: () => import("@/views/Index/index.vue")
    },
    {
        path: "/add",
        name: "Add",
        redirect: to => {
            return "/add/job";
        },
        component: () => import("@/views/add/index.vue"),
        children: [
            {
                path: "job",
                name: "AddJob",
                component: () => import("@/views/add/job/index.vue")
            },
            {
                path: "level",
                name: "AddLevel",
                component: () => import("@/views/add/level/index.vue")
            },
            {
                path: "curtail",
                name: "AddCurtail",
                component: () => import("@/views/add/curtail/index.vue")
            },
        ]
    },
    {
        path: "/info",
        name: "Info",
        component: () => import("@/views/info/index.vue"),
        redirect: to => {
            return "/info/job";
        },
        children: [
            {
                path: "job",
                name: "InfoJob",
                component: () => import("@/views/info/job/index.vue")
            },
            {
                path: "level",
                name: "InfoLevel",
                component: () => import("@/views/info/level/index.vue")
            },
            {
                path: "curtail",
                name: "InfoCurtail",
                component: () => import("@/views/info/curtail/index.vue")
            },
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: publicRoutes,
})

export const setupRouter = (app) => {
    app.use(router);
}