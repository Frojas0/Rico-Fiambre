const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            valorSeleccionado: ""
        }
    },
    created() {
        this.metodoejemplo()
    },
    methods: {
        metodoejemplo() {

        }
    }
})
app.mount('#vueApp')