const { createApp } = Vue;
console.log("Hola")
const app = createApp({
    data() {
        return {
            mostrarFormCrear: false,
            mostrarFormCrearDescuento: false,
            mostrarFormModificar: false,
            mostrarFormDesactivar: false,
            mostrarFormActivar: false,
            productosPeso: [],
            productosUnidad: [],
            productosActivos: [],
            productosInactivos: [],
            tiposProductos: [],
            paisesProductos: [],
            nombreActual: "",
            tipoProducto: "",
            descripcion: "",
            stock: 0,
            paisProducto: "",
            precio: 0,
            esPorPeso: false,
            imagen: "",
            nuevoNombre: "",
            valorDescuento: "",

        }
    },
    created() {
        this.cargarProductosUnidad(),
            this.cargarProductosPeso(),
            this.cargarTiposProductos(),
            this.cargarPaisesProductos(),
            this.cargarProductosActivos(),
            this.cargarProductosInactivos()
    },

    methods: {
        cargarProductosUnidad() {
            axios.get("/api/productoUni")
                .then(response => {
                    this.productosUnidad = response.data;
                    console.log(this.productosUnidad)
                })
                .catch(error => console.log("error"))
        },

        cargarProductosPeso() {
            axios.get("/api/productoPeso")
                .then(response => {
                    this.productosPeso = response.data;
                    console.log(this.productosPeso)
                })
                .catch(error => console.log("error"))
        },

        cargarProductosActivos() {
            axios.get("/api/productos-activos")
                .then(response => {
                    this.productosActivos = response.data;
                    console.log(this.productosActivos)
                })
                .catch(error => console.log("error"))
        },

        cargarProductosInactivos() {
            axios.get("/api/productos-inactivos")
                .then(response => {
                    this.productosInactivos = response.data;
                    console.log(this.productosInactivos)
                })
                .catch(error => console.log("error"))
        },

        cargarTiposProductos() {
            axios.get("/api/tipos-producto")
                .then(response => {
                    this.tiposProductos = response.data;
                    console.log(this.tiposProductos)
                })
                .catch(error => console.log("error"))
        },

        cargarPaisesProductos() {
            axios.get("/api/pais-producto")
                .then(response => {
                    this.paisesProductos = response.data;
                    console.log(this.paisesProductos)
                })
                .catch(error => console.log("error"))
        },

        crearProducto() {
            Swal.fire({
                title: '¿Estas seguro de crear este producto?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: 'black',
                cancelButtonColor: '#d33',
                cancelButtonText: 'Cancelar',
                confirmButtonText: 'Crear'
            }).then(result => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: 'Producto creado',
                        icon: 'success',
                        confirmButtonText: 'CERRAR',
                        confirmButtonColor: 'black',
                    })

                axios.post('/api/crear-producto', 'nombre=' + this.nombreActual
                    + '&tipoProducto=' + this.tipoProducto +
                    '&descripcion=' + this.descripcion +
                    '&stock=' + this.stock +
                    '&precio=' + this.precio +
                    '&paisProducto=' + this.paisProducto +
                    '&esPorPeso=' + this.esPorPeso +
                    '&url=' + this.imagen)
                    .then(response => {
                        console.log("producto creado")
                        window.location.href = "/web/admin/admin.html"
                    })

                    .catch(error => Swal.fire({
                        title: 'Error',
                        text: error.response.data,
                        icon: 'error'
                    }))
                }
            })
        },

        crearDescuentoProducto() {
            Swal.fire({
                title: '¿Estas seguro de crear este descuento?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: 'black',
                cancelButtonColor: '#d33',
                cancelButtonText: 'Cancelar',
                confirmButtonText: 'Crear'
            }).then(result => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: 'Descuento aplicado',
                        icon: 'success',
                        confirmButtonText: 'CERRAR',
                        confirmButtonColor: 'black',
                    })

                axios.post('/api/crear-descuento-producto', 'nombreProducto=' + this.nombreActual + '&valorDescuento=' + this.valorDescuento)
                    .then(response => {
                        console.log("producto con descuento")
                        window.location.href = "/web/admin/admin.html"
                    })

                    .catch(error => Swal.fire({
                        title: 'Error',
                        text: error.response.data,
                        icon: 'error'
                    }))
                }
            })
        },

        modificarProducto() {
            Swal.fire({
                title: '¿Estas seguro de modificar este producto?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: 'black',
                cancelButtonColor: '#d33',
                cancelButtonText: 'Cancelar',
                confirmButtonText: 'Modificar'
            }).then(result => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: 'Producto modificado',
                        icon: 'success',
                        confirmButtonText: 'CERRAR',
                        confirmButtonColor: 'black',
                    })

                axios.post('/api/modificar-producto', {
                    "nombre": this.nombreActual,
                    "nuevoNombre": this.nuevoNombre,
                    "tipo": this.tipoProducto,
                    "descripcion": this.descripcion,
                    "stock": this.stock,
                    "precio": this.precio,
                    "origen": this.paisProducto,
                    "url": this.imagen
                })
                    .then(response => {
                        console.log("producto modificado")
                        window.location.href = "/web/admin/admin.html"
                    })

                    .catch(error => Swal.fire({
                        title: 'Error',
                        text: error.response.data,
                        icon: 'error'
                    }))
                }
            })
        },

        desactivarProducto() {
            Swal.fire({
                title: '¿Estas seguro de desactivar este producto?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: 'black',
                cancelButtonColor: '#d33',
                cancelButtonText: 'Cancelar',
                confirmButtonText: 'Desactivar'
            }).then(result => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: 'Producto desactivado',
                        icon: 'success',
                        confirmButtonText: 'CERRAR',
                        confirmButtonColor: 'black',
                    })
                axios.post('/api/desactivar-producto', 'nombre=' + this.nombreActual)
                    .then(response => {
                        console.log("producto desactivado")
                        window.location.href = "/web/admin/admin.html"
                    })

                    .catch(error => Swal.fire({
                        title: 'Error',
                        text: error.response.data,
                        icon: 'error'
                    }))
                }
            })
        },

        activarProducto() {
            Swal.fire({
                title: '¿Estas seguro de activar este producto?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: 'black',
                cancelButtonColor: '#d33',
                cancelButtonText: 'Cancelar',
                confirmButtonText: 'Activar'
            }).then(result => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: 'Producto activado',
                        icon: 'success',
                        confirmButtonText: 'CERRAR',
                        confirmButtonColor: 'black',
                    })

                axios.post('/api/activar-producto', 'nombre=' + this.nombreActual)
                    .then(response => {
                        console.log("producto activado")
                        window.location.href = "/web/admin/admin.html"
                    })

                    .catch(error => Swal.fire({
                        title: 'Error',
                        text: error.response.data,
                        icon: 'error'
                    }))
                }
            })
        },

        cerrarSesion() {
            Swal.fire({
                title: '¿Estas seguro de salir?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: 'black',
                cancelButtonColor: '#d33',
                cancelButtonText: 'Cancelar',
                confirmButtonText: 'Cerrar sesion'
            }).then(result => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: '¡Hasta pronto!',
                        icon: 'success',
                        confirmButtonText: 'CERRAR',
                        confirmButtonColor: 'black',
                    })     

                    axios.post('/api/logout')
                        .then(response => window.location.href = "/web/index.html")
                        .catch(error => Swal.fire({
                            title: 'Error',
                            text: error.response.data,
                            icon: 'error'
                        }))
                }
            })
        },

        seleccionarFormCrear() {
            this.mostrarFormCrear = true;
            this.mostrarFormCrearDescuento = false;
            this.mostrarFormActivar = false;
            this.mostrarFormDesactivar = false;
            this.mostrarFormModificar = false;
        },

        seleccionarFormCrearDescuento() {
            this.mostrarFormCrear = false;
            this.mostrarFormCrearDescuento = true;
            this.mostrarFormActivar = false;
            this.mostrarFormDesactivar = false;
            this.mostrarFormModificar = false;
        },

        seleccionarFormModificar() {
            this.mostrarFormCrear = false;
            this.mostrarFormCrearDescuento = false;
            this.mostrarFormModificar = true;
            this.mostrarFormDesactivar = false;
            this.mostrarFormActivar = false;
        },

        seleccionarFormDesactivar() {
            this.mostrarFormCrear = false;
            this.mostrarFormCrearDescuento = false;
            this.mostrarFormModificar = false;
            this.mostrarFormDesactivar = true;
            this.mostrarFormActivar = false;
        },

        seleccionarFormActivar() {
            this.mostrarFormCrear = false;
            this.mostrarFormCrearDescuento = false;
            this.mostrarFormModificar = false;
            this.mostrarFormDesactivar = false;
            this.mostrarFormActivar = true;
        }
    }
})
app.mount('#vueApp')