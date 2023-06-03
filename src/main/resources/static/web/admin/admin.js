const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            mostrarFormCrear: false,
            mostrarFormModificar: false,
            mostrarFormDesactivar: false,
            mostrarFormActivar: false,
        }
    },

    methods: {
        seleccionarFormCrear(){
            this.mostrarFormCrear =! this.mostrarFormCrear;
        },

        seleccionarFormModificar(){
            this.mostrarFormModificar =! this.mostrarFormModificar;
        },

        seleccionarFormDesactivar(){
            this.mostrarFormDesactivar =! this.mostrarFormDesactivar;
        },

        seleccionarFormActivar(){
            this.mostrarFormActivar =! this.mostrarFormActivar;
        }
    }
})
app.mount('#vueApp')