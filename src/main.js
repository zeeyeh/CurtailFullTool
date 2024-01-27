import { createApp } from 'vue'
import "./style.css";
import "virtual:uno.css";
import App from './App.vue'
import {setupStore} from "@/store/index.js";
import {setupRouter} from "@/router/index.js";
import "highlight.js/styles/atom-one-dark.css"
import "highlight.js/lib/common"
import hljsVuePlugin from "@highlightjs/vue-plugin"

const app = createApp(App)

setupStore(app)

setupRouter(app)

app.use(hljsVuePlugin)

app.config.errorHandler = () => null;
app.config.warnHandler = () => null;

app.mount('#app')
