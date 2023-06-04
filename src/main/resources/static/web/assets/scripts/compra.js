const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            itemsParaPagar: []
        }
    },
    created() {
        this.traerDatos()
    },
    methods: {
        traerDatos() {
            this.itemsParaPagar = JSON.parse(localStorage.getItem('carritoDeCompras'))
            console.log(this.itemsParaPagar)
        },
    }
})
app.mount('#vueApp')