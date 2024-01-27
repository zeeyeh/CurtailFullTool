import {defineStore} from "pinia";
import {computed, ref} from "vue";

export const useCurtailStore = defineStore('curtaillist', () => {
    const curtails = ref([]);

    const containsById = (id) => {
        for (let i = 0; i < curtails.value.length; i++) {
            const curtail = curtails.value.at(i);
            if (curtail.id === id) {
                return true;
            }
        }
        return false;
    }

    const containsByName = (name) => {
        for (let i = 0; i < curtails.value.length; i++) {
            const job = curtails.value.at(i);
            if (job.name === name) {
                return true;
            }
        }
        return false;
    }
    const addCurtail = (id, name, jobId, levelId, tools, foods, places, destructs, recipes, interacts, attacks, buffs) => {
        if (containsById(id)) {
            return;
        }
        curtails.value.push({
            id: id,
            name: name,
            jobId: jobId,
            levelId: levelId,
            tools: tools,
            foods: foods,
            places: places,
            destructs: destructs,
            recipes: recipes,
            interacts: interacts,
            attacks: attacks,
            buffs: buffs,
        })
    }

    const removeCurtailById = (id) => {
        if (!containsById(id)) {
            return;
        }
        for (let i = 0; i < curtails.value.length; i++) {
            if (curtails.value[i].id === id) {
                curtails.value.splice(i);
            }
        }
    }

    const removeCurtailByName = (name) => {
        if (!containsByName(name)) {
            return;
        }
        for (let i = 0; i < curtails.value.length; i++) {
            if (curtails.value[i].name === name) {
                curtails.value.splice(i);
            }
        }
    }

    const getCurtails = computed(() => {
        return curtails.value;
    });

    return {
        curtails,
        addCurtail,
        removeCurtailById,
        removeCurtailByName,
        getCurtails
    }
}, {
    persist: false
});