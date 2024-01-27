import {defineStore} from "pinia";
import {computed, ref} from "vue";
// import {Job} from "@/utils/common.js";


export const useJobStore = defineStore('joblist', () => {
    const jobs = ref([]);

    const containsById = (id) => {
        for (let i = 0; i < jobs.value.length; i++) {
            const job = jobs.value.at(i);
            if (job.id === id) {
                return true;
            }
        }
        return false;
    }

    const containsByName = (name) => {
        for (let i = 0; i < jobs.value.length; i++) {
            const job = jobs.value.at(i);
            if (job.name === name) {
                return true;
            }
        }
        return false;
    }

    const addJob = (id, name, title, players) => {
        if (containsById(id)) {
            return;
        }
        jobs.value.push({
            id: id,
            name: name,
            title: title,
            players: players
        })
    }

    const removeJobById = (id) => {
        if (!containsById(id)) {
            return;
        }
        for (let i = 0; i < jobs.value.length; i++) {
            if (jobs.value[i].id === id) {
                jobs.value.splice(i);
            }
        }
    }

    const removeJobByName = (name) => {
        if (!containsByName(name)) {
            return;
        }
        for (let i = 0; i < jobs.value.length; i++) {
            if (jobs.value[i].name === name) {
                jobs.value.splice(i);
            }
        }
    }

    const getJobs = computed(() => {
        return jobs.value;
    });

    return {
        jobs,
        getJobs,
        addJob,
        removeJobById,
        removeJobByName
    }
}, {
    persist: false
})
