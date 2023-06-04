const { createApp } = Vue;
console.log("Hola")
const app = createApp({
    data() {
        return {
            mostrarFormCrear: false,
            mostrarFormModificar: false,
            mostrarFormDesactivar: false,
            mostrarFormActivar: false,
            productosPeso: [],
            productosUnidad: [],
            nombreActual: "",
            tipoProducto: "",
            descripcion: "",
            stock: 0,
            paisProducto: "",
            precio: 0,
            esPorPeso: "",
            noEsPorPeso: "",
            imagen: "",
            nuevoNombre: "",
        }
    },
    created(){
        this.cargarProductosUnidad(),
        this.cargarProductosPeso()
    },

    methods: {
        cargarProductosUnidad(){
            axios.get("/api/productoUni")
            .then(response => {
                this.productosUnidad = response.data;
                console.log(this.productosUnidad)
            })
            .catch(error => console.log("error"))
        },

        cargarProductosPeso(){
            axios.get("/api/productoPeso")
            .then(response => {
                this.productosPeso = response.data;
                console.log(this.productosPeso)
            })
            .catch(error => console.log("error"))
        },

        seleccionarFormCrear(){
            this.mostrarFormCrear = true;
            this.mostrarFormActivar = false;
            this.mostrarFormDesactivar = false;
            this.mostrarFormModificar = false;
        },

        seleccionarFormModificar(){
            this.mostrarFormCrear = false;
            this.mostrarFormModificar = true;
            this.mostrarFormDesactivar = false;
            this.mostrarFormActivar = false;
        },

        seleccionarFormDesactivar(){
            this.mostrarFormCrear = false;
            this.mostrarFormModificar = false;
            this.mostrarFormDesactivar = true;
            this.mostrarFormActivar = false;
        },

        seleccionarFormActivar(){
            this.mostrarFormCrear = false;
            this.mostrarFormModificar = false;
            this.mostrarFormDesactivar = false;
            this.mostrarFormActivar = true;
        }
    }
})
app.mount('#vueApp')