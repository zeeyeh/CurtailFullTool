import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import Components from "unplugin-vue-components/vite"
import { AntDesignVueResolver } from "unplugin-vue-components/resolvers"
import UnoCSS from "unocss/vite";
import {resolve} from "node:path";
import VitePluginVueDevTools from "vite-plugin-vue-devtools";

export default defineConfig({
  plugins: [
    vue(),
      VitePluginVueDevTools(),
    UnoCSS(),
    Components({
      resolvers:[
        AntDesignVueResolver({
          importStyle: false
        })
      ]
    })
  ],
  resolve: {
    alias: {
      '~': resolve(__dirname, '.'),
      '@': resolve(__dirname, '.', 'src'),
      "components": resolve(__dirname, '.', 'src', 'components')
    }
  },
  css: {
    preprocessorOptions: {
      scss: {
        javascriptEnable: true
      }
    }
  }
})
