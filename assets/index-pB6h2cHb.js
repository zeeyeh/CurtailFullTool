import{_ as x}from"./TagInput-37XLSddd.js";import{b as y,d as k,a as w,c as C,w as a,e,f as c,t as B,g as p,B as I,_ as S,h as F}from"./index-bgJT2_l9.js";import{S as J,C as U,_ as N,a as V,I as $,F as h}from"./snowflake-id-Y9dR8_kY.js";const A={__name:"index",setup(R){const s=new J,t=y({id:s.generate(),name:"",title:"",players:[]}),u=k(),f=()=>{u.addJob(...B(t)),p.success("职业添加成功"),m()},m=(d=!1)=>{t.id=s.generate(),t.name="",t.title="",t.players=[],d&&p.success("数据清空成功")};return(d,o)=>{const b=N,n=V,_=$,r=I,v=S,i=F,j=h,g=U;return w(),C(g,{title:"添加职业"},{default:a(()=>[e(j,{class:"add-job-form","label-col":{style:{width:"90px"}}},{default:a(()=>[e(n,{label:"职业唯一Id",name:"id",class:"add-job-form-item"},{default:a(()=>[e(b,{value:t.id,"onUpdate:value":o[0]||(o[0]=l=>t.id=l),placeholder:"请输入职业唯一Id",class:"add-job-form-item-id"},null,8,["value"])]),_:1}),e(n,{label:"职业名称",name:"name",class:"add-job-form-item"},{default:a(()=>[e(_,{value:t.name,"onUpdate:value":o[1]||(o[1]=l=>t.name=l),placeholder:"请输入职业名称",class:"add-job-form-item-id"},null,8,["value"])]),_:1}),e(n,{label:"职业标题",name:"title",class:"add-job-form-item"},{default:a(()=>[e(_,{value:t.title,"onUpdate:value":o[2]||(o[2]=l=>t.title=l),placeholder:"请输入职业标题",class:"add-job-form-item-id"},null,8,["value"])]),_:1}),e(n,{label:"拥有玩家",name:"players",class:"add-job-form-item"},{default:a(()=>[e(x,{list:t.players,placeholder:"请输入玩家名称"},null,8,["list"])]),_:1}),e(n,{class:"add-job-form-item float-right"},{default:a(()=>[e(i,{title:"重置所有输入",placement:"bottom"},{default:a(()=>[e(v,{"ok-text":"确定","cancel-text":"取消",title:"确定要清空所有输入吗？",onConfirm:o[3]||(o[3]=l=>m(!0))},{default:a(()=>[e(r,{class:"mr-2"},{default:a(()=>[c("取消")]),_:1})]),_:1})]),_:1}),e(i,{title:"添加职业",placement:"bottom"},{default:a(()=>[e(r,{type:"primary",onClick:f},{default:a(()=>[c("确定")]),_:1})]),_:1})]),_:1})]),_:1})]),_:1})}}};export{A as default};
