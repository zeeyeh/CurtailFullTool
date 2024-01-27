import {ref} from "vue";
import axios from "axios";

export const setupSplit = (asidePanelEl, dividerEl) => {
    const asidePanel = asidePanelEl.value;
    const divider = dividerEl.value;
    const maxWidth = 480;
    let canMove = ref(false);

    const onMouseDown = () => {
        canMove.value = true;
    }

    const onMouseMove = (e) => {
        if (canMove.value) {
            let x = e.clientX;
            if (x < maxWidth) {
                asidePanel.style.width = x + 'px';
            }
        }
    }

    const onMouseUp = () => {
        canMove.value = false;
    }
    divider.addEventListener('mousedown', onMouseDown);
    window.addEventListener('mousemove', onMouseMove);
    document.addEventListener('mouseup', onMouseUp);
}

export const setupMinecraftIds = async (result)  => {

    return response;
}

// /**
//  * 职业信息
//  */
// interface Job {
//     /**
//      * 职业唯一Id
//      */
//     id?: String;
//     /**
//      * 职业名称
//      */
//     name?: String;
//     /**
//      * 职业标题
//      */
//     title?: String;
//     /**
//      * 所处该职业的所有玩家名称
//      */
//     players?: Array<String>;
// }
//
// /**
//  * 等级信息
//  */
// interface Level {
//     /**
//      * 等级唯一Id
//      */
//     id?: String;
//     /**
//      * 等级名称
//      */
//     name?: String;
//     /**
//      * 等级标题
//      */
//     title?: String;
//     /**
//      * 所处该等级的所有玩家名称
//      */
//     players?: Array<String>;
//     /**
//      * 最大经验值
//      */
//     maxExp?: Number;
//     /**
//      * 下一阶段等级Id
//      */
//     nextId?: String;
// }
//
// /**
//  * 限制信息
//  */
// interface Curtail {
//     /**
//      * 限制唯一Id
//      */
//     id?: String;
//     /**
//      * 限制命名
//      */
//     name?: String;
//     /**
//      * 生效职业
//      */
//     jobId?: String;
//     /**
//      * 生效等级
//      */
//     levelId?: String;
//     /**
//      * 可使用的工具列表
//      */
//     tools?: Array<CurtailInfo>;
//     /**
//      * 可食用的食物列表
//      */
//     foods?: Array<CurtailInfo>;
//     /**
//      * 可放置的方块列表
//      */
//     places?: Array<CurtailInfo>;
//     /**
//      * 可破坏的方块列表
//      */
//     destructs?: Array<CurtailInfo>;
//     /**
//      * 可合成的物品列表
//      */
//     recipes?: Array<CurtailInfo>;
//     /**
//      * 可交互的实体列表
//      */
//     interacts?: Array<CurtailInfo>;
//     /**
//      * 可攻击的实体列表
//      */
//     attacks?: Array<CurtailInfo>;
//     /**
//      * 手持工具获得的Buff增益列表
//      */
//     buffs?: Array<CurtailInfo>;
// }
//
// /**
//  * 限制条目信息
//  */
// interface CurtailInfo {
//     /**
//      * 物品英文Id
//      */
//     itemId?: String;
//     /**
//      * 获得的经验值
//      */
//     acquired?: Number;
// }