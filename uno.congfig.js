import { defineConfig } from "unocss";
import {presetUno} from "@unocss/preset-uno";
import presetAttributify from "@unocss/preset-attributify";
import transformerAttributifyJsx from "@unocss/transformer-attributify-jsx";

export default defineConfig({
    presets:[
        presetUno(),
        presetAttributify()
    ],
    transformers:[
        transformerAttributifyJsx()
    ],
    rules: []
})