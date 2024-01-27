import{j as G,m as J,k as K,l as Q,n as w,p as Y,q as N,s as W,v as T,x as L,e as g,y as S,z as Z,A as ee,C as oe,D as le,F as x,W as te,P as D,E as ae,G as ne,b as re,o as se,H as ce,a as C,I as b,J as H,c as A,w as f,f as E,K as M,h as ie}from"./index-o81tgMgb.js";import{I as de}from"./snowflake-id-adSuaare.js";const y=(e,r,l)=>{const o=K(l);return{[`${e.componentCls}-${r}`]:{color:e[`color${l}`],background:e[`color${o}Bg`],borderColor:e[`color${o}Border`],[`&${e.componentCls}-borderless`]:{borderColor:"transparent"}}}},ue=e=>Q(e,(r,l)=>{let{textColor:o,lightBorderColor:a,lightColor:t,darkColor:s}=l;return{[`${e.componentCls}-${r}`]:{color:o,background:t,borderColor:a,"&-inverse":{color:e.colorTextLightSolid,background:s,borderColor:s},[`&${e.componentCls}-borderless`]:{borderColor:"transparent"}}}}),ge=e=>{const{paddingXXS:r,lineWidth:l,tagPaddingHorizontal:o,componentCls:a}=e,t=o-l,s=r-l;return{[a]:w(w({},Y(e)),{display:"inline-block",height:"auto",marginInlineEnd:e.marginXS,paddingInline:t,fontSize:e.tagFontSize,lineHeight:`${e.tagLineHeight}px`,whiteSpace:"nowrap",background:e.tagDefaultBg,border:`${e.lineWidth}px ${e.lineType} ${e.colorBorder}`,borderRadius:e.borderRadiusSM,opacity:1,transition:`all ${e.motionDurationMid}`,textAlign:"start",[`&${a}-rtl`]:{direction:"rtl"},"&, a, a:hover":{color:e.tagDefaultColor},[`${a}-close-icon`]:{marginInlineStart:s,color:e.colorTextDescription,fontSize:e.tagIconSize,cursor:"pointer",transition:`all ${e.motionDurationMid}`,"&:hover":{color:e.colorTextHeading}},[`&${a}-has-color`]:{borderColor:"transparent",[`&, a, a:hover, ${e.iconCls}-close, ${e.iconCls}-close:hover`]:{color:e.colorTextLightSolid}},"&-checkable":{backgroundColor:"transparent",borderColor:"transparent",cursor:"pointer",[`&:not(${a}-checkable-checked):hover`]:{color:e.colorPrimary,backgroundColor:e.colorFillSecondary},"&:active, &-checked":{color:e.colorTextLightSolid},"&-checked":{backgroundColor:e.colorPrimary,"&:hover":{backgroundColor:e.colorPrimaryHover}},"&:active":{backgroundColor:e.colorPrimaryActive}},"&-hidden":{display:"none"},[`> ${e.iconCls} + span, > span + ${e.iconCls}`]:{marginInlineStart:t}}),[`${a}-borderless`]:{borderColor:"transparent",background:e.tagBorderlessBg}}},O=G("Tag",e=>{const{fontSize:r,lineHeight:l,lineWidth:o,fontSizeIcon:a}=e,t=Math.round(r*l),s=e.fontSizeSM,i=t-o*2,c=e.colorFillAlter,p=e.colorText,n=J(e,{tagFontSize:s,tagLineHeight:i,tagDefaultBg:c,tagDefaultColor:p,tagIconSize:a-2*o,tagPaddingHorizontal:8,tagBorderlessBg:e.colorFillTertiary});return[ge(n),ue(n),y(n,"success","Success"),y(n,"processing","Info"),y(n,"error","Error"),y(n,"warning","Warning")]}),pe=()=>({prefixCls:String,checked:{type:Boolean,default:void 0},onChange:{type:Function},onClick:{type:Function},"onUpdate:checked":Function}),ve=N({compatConfig:{MODE:3},name:"ACheckableTag",inheritAttrs:!1,props:pe(),setup(e,r){let{slots:l,emit:o,attrs:a}=r;const{prefixCls:t}=W("tag",e),[s,i]=O(t),c=n=>{const{checked:v}=e;o("update:checked",!v),o("change",!v),o("click",n)},p=T(()=>L(t.value,i.value,{[`${t.value}-checkable`]:!0,[`${t.value}-checkable-checked`]:e.checked}));return()=>{var n;return s(g("span",S(S({},a),{},{class:[p.value,a.class],onClick:c}),[(n=l.default)===null||n===void 0?void 0:n.call(l)]))}}}),I=ve,Ce=()=>({prefixCls:String,color:{type:String},closable:{type:Boolean,default:!1},closeIcon:D.any,visible:{type:Boolean,default:void 0},onClose:{type:Function},onClick:ae(),"onUpdate:visible":Function,icon:D.any,bordered:{type:Boolean,default:!0}}),m=N({compatConfig:{MODE:3},name:"ATag",inheritAttrs:!1,props:Ce(),slots:Object,setup(e,r){let{slots:l,emit:o,attrs:a}=r;const{prefixCls:t,direction:s}=W("tag",e),[i,c]=O(t),p=Z(!0);ee(()=>{e.visible!==void 0&&(p.value=e.visible)});const n=u=>{u.stopPropagation(),o("update:visible",!1),o("close",u),!u.defaultPrevented&&e.visible===void 0&&(p.value=!1)},v=T(()=>oe(e.color)||le(e.color)),d=T(()=>L(t.value,c.value,{[`${t.value}-${e.color}`]:v.value,[`${t.value}-has-color`]:e.color&&!v.value,[`${t.value}-hidden`]:!p.value,[`${t.value}-rtl`]:s.value==="rtl",[`${t.value}-borderless`]:!e.bordered})),h=u=>{o("click",u)};return()=>{var u,$,_;const{icon:R=(u=l.icon)===null||u===void 0?void 0:u.call(l),color:B,closeIcon:P=($=l.closeIcon)===null||$===void 0?void 0:$.call(l),closable:j=!1}=e,U=()=>j?P?g("span",{class:`${t.value}-close-icon`,onClick:n},[P]):g(ne,{class:`${t.value}-close-icon`,onClick:n},null):null,X={backgroundColor:B&&!v.value?B:void 0},k=R||null,z=(_=l.default)===null||_===void 0?void 0:_.call(l),V=k?g(x,null,[k,g("span",null,[z])]):z,q=e.onClick!==void 0,F=g("span",S(S({},a),{},{onClick:h,class:[d.value,a.class],style:[X,a.style]}),[V,U()]);return i(q?g(te,null,{default:()=>[F]}):F)}}});m.CheckableTag=I;m.install=function(e){return e.component(m.name,m),e.component(I.name,I),e};const he=m,me={key:0,class:"tag-input mt-1.5"},be={key:1,class:"tag-input mt--1.5"},Se={__name:"TagInput",props:{list:{type:Array,default:[]},deduplication:{type:Boolean,default:!1},placeholder:{type:String,default:""}},emits:["input","tagClose"],setup(e,{emit:r}){const l=e,o=re({list:[],value:""}),a=r;se(()=>{o.list=l.list,a("input",o.list)});const t=()=>{if(o.value=o.value.trim(),o.value!==""&&o.value!==" "){if(o.list.push(o.value),l.list){let i=o.list,c=new Set(i);o.list=Array.from(c)}o.value="",a("input",o.list)}};ce(l.list,()=>{o.list=l.list},{deep:!0});const s=i=>{a("tagClose",i),o.list.splice(o.list.indexOf(i),1)};return(i,c)=>{const p=he,n=de,v=ie;return o.list.length>0?(C(),b("div",me,[(C(!0),b(x,null,H(o.list,(d,h)=>(C(),A(p,{color:"blue",closable:"",onClose:u=>s(d),key:h},{default:f(()=>[E(M(d),1)]),_:2},1032,["onClose"]))),128)),g(v,{title:"按下回车添加玩家到列表",placement:"bottom"},{default:f(()=>[g(n,{class:"mt-2",type:"text",placeholder:e.placeholder,value:o.value,"onUpdate:value":c[0]||(c[0]=d=>o.value=d),onPressEnter:t},null,8,["placeholder","value"])]),_:1})])):(C(),b("div",be,[(C(!0),b(x,null,H(o.list,(d,h)=>(C(),A(p,{color:"blue",closable:"",onClose:u=>s(d),key:h},{default:f(()=>[E(M(d),1)]),_:2},1032,["onClose"]))),128)),g(v,{title:"按下回车添加玩家到列表",placement:"bottom"},{default:f(()=>[g(n,{class:"mt-2",type:"text",placeholder:e.placeholder,value:o.value,"onUpdate:value":c[1]||(c[1]=d=>o.value=d),onPressEnter:t},null,8,["placeholder","value"])]),_:1})]))}}};export{Se as _};