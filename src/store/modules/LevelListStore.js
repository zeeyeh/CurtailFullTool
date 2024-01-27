import {defineStore} from "pinia";
import {computed, ref} from "vue";

export const useLevelStore = defineStore('levellist', () => {
    const levels = ref([]);
    const containsById = (id) => {
        for (let i = 0; i < levels.value.length; i++) {
            const level = levels.value.at(i);
            // console.log(level)
            if (level.id === id) {
                return true;
            }
        }
        return false;
    }

    const containsByName = (name) => {
        for (let i = 0; i < levels.value.length; i++) {
            const job = levels.value.at(i);
            if (job.name === name) {
                return true;
            }
        }
        return false;
    }

    const addLevel = (id, name, title, maxExp, nextId, players) => {
        if (containsById(id)) {
            return;
        }
        levels.value.push({
            id: id,
            name: name,
            title: title,
            maxExp: maxExp,
            nextId: nextId,
            players: players
        })
    }

    const removeLevelById = (id) => {
        if (!containsById(id)) {
            return;
        }
        for (let i = 0; i < levels.value.length; i++) {
            if (levels.value[i].id === id) {
                levels.value.splice(i);
            }
        }
    }

    const removeLevelByName = (name) => {
        if (!containsByName(name)) {
            return;
        }
        for (let i = 0; i < levels.value.length; i++) {
            if (levels.value[i].name === name) {
                levels.value.splice(i);
            }
        }
    }

    const getLevels = computed(() => {
        return levels.value;
    });

    return {
        levels,
        getLevels,
        addLevel,
        removeLevelById,
        removeLevelByName
    }
}, {
    persist: false
})