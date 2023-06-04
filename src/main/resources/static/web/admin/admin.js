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
            esPorPeso: false,
            imagen: "",
            nuevoNombre: "",
            tiposProductos: [],
        }
    },
    created(){
        this.cargarProductosUnidad(),
        this.cargarProductosPeso(),
        this.cargarTiposProductos()
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

        cargarTiposProductos(){
            axios.get("/api/tipos-producto")
            .then(response => {
                this.tiposProductos = response.data;
                console.log(this.tiposProductos)
            })
            .catch(error => console.log("error"))
        },

        crearProducto(){
            axios.post('/api/crear-producto','nombre=' + this.nombreActual 
            + '&tipoProducto=' + this.tipoProducto + 
            '&descripcion=' + this.descripcion + 
            '&stock=' + this.stock + 
            '&precio=' + this.precio + 
            '&paisProducto=' + this.paisProducto + 
            '&esPorPeso=' + this.esPorPeso + 
            '&url=' + this.imagen)
            .then(response => {
                console.log("producto creado")
            })
            
            .catch(error => Swal.fire({
                title: 'Error',
                text: error.response.data,
                icon: 'error'
            }))
        },

        modificarProducto(){
            axios.post('/api/modificar-producto',{
                "nombre": this.nombreActual,
                "nuevoNombre": this.nuevoNombre,
                "descripcion": this.descripcion,
                "stock": this.stock, 
                "precio": this.precio,
                "url": this.imagen})
            .then(response => {
                console.log("producto modificado")
            })
            
            .catch(error => Swal.fire({
                title: 'Error',
                text: error.response.data,
                icon: 'error'
            }))
        },

        desactivarProducto(){
            axios.post('/api/desactivar-producto','nombre=' + this.nombreActual)
            .then(response => {
                console.log("producto desactivado")
            })
            
            .catch(error => Swal.fire({
                title: 'Error',
                text: error.response.data,
                icon: 'error'
            }))
        },

        activarProducto(){
            axios.post('/api/activar-producto','nombre=' + this.nombreActual)
            .then(response => {
                console.log("producto activado")
            })
            
            .catch(error => Swal.fire({
                title: 'Error',
                text: error.response.data,
                icon: 'error'
            }))
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