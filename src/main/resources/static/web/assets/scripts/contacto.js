const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            valorSeleccionado: "",
            isLoading: true
        }
    },
    created() {
        this.metodoejemplo()
    },
    methods: {
        metodoejemplo() {
            this.isLoading = false
        }
    }
})
app.mount('#vueApp')