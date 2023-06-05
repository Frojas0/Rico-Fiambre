const { createApp } = Vue;
const app = createApp({
    data() {
        return {
        valorSeleccionado: "",
        itemsParaPagar: [],
        numDeProductos: Number,
        total: Number,
        clienteActual: undefined,
        costoEnvio: Number,
        numeroTarjeta: undefined,
        cvvTarjeta: undefined,
        nombreTarjeta: undefined,
        fecha:undefined,

        
        // MOSTRAR BOTON LOGOUT

        mostrarBoton : false,
        mostrarBotonLogIn : true
        // FIN MOSTRAR BOTON LOGOUT
        }
    },
    created() {
        this.estaLogueado()
        this.traerDatos()
        axios.get('/api/clientes/actual')
        .then(response => {
            this.clienteActual = response.data;
            // console.log(response.data);
            this.costoEnvio = parseInt(response.data.codPostal.toString().substring(0, 2)) * 16;
            this.isLoading = false;  // Establecemos isLoading en false al finalizar la petición
        })
        .catch(error => {
            console.log(error);
            this.isLoading = false;  // Manejamos el error y establecemos isLoading en false
        })
    },
    methods: {
        traerDatos() {
        productosDelCarrito = JSON.parse(localStorage.getItem('carritoDeCompras'));
        this.itemsParaPagar = productosDelCarrito;
        this.numDeProductos = productosDelCarrito.length;
        this.total = productosDelCarrito.reduce((suma, producto) => suma + producto.precio, 0);
        console.log(this.itemsParaPagar);
        },
        pagar(){
            const inputOptions = new Promise((resolve) => {
                setTimeout(() => {
                    resolve({
                    '#ff0000': 'Red',
                    '#00ff00': 'Green',
                    '#0000ff': 'Blue'
                    })
                }, 1000)
            })
            lista = []
            if(this.fecha == undefined || this.nombreTarjeta == undefined || this.cvvTarjeta == undefined || this.numeroTarjeta == undefined){
                Swal.fire({
                    icon: 'info',
                    text: 'Faltan datos por completar',
                })
            } else {
                for (const i of this.itemsParaPagar) {
                    if(i.esPorPeso){
                        lista.push(i.nombre + "-" + (i.cantidad / 1000))
                    } else {
                        lista.push(i.nombre + "-" + i.cantidad)
                    }
                }
                Swal.fire({
                    title: 'Realizando pago, por favor espere',
                    allowEscapeKey: false,
                    allowOutsideClick: false,
                    didOpen: () => {
                        Swal.showLoading();
                    }
                });
                axios.post('/api/carrito-compra',{ productos: lista, numero: this.numeroTarjeta, cvv: this.cvvTarjeta, descripcion: "Rico Fiambre - COMPRA"})
                .then(response => {
                    Swal.fire({
                        icon: 'success',
                        title: 'Gracias por su compra!',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    setTimeout(() => {
                        window.location.replace('./productos.html');
                    }, 2000);

                    keys = Object.keys(localStorage)
                    keys.forEach((key) => {
                    if (key.includes("carritoDeCompras")) {
                        localStorage.removeItem(key)
                    }
                    })
                })
                .catch(error => {
                    console.log(error)
                    Swal.fire({
                        icon: 'error',
                        text: 'Se produjo un error al realizar el pago',
                    })
                })
            }
        },
        // METODO PARA SABER SI ESTA LOGUEADO O NO
        estaLogueado(){
            axios.get('/api/clientes/actual')
            .then(response => {
                this.mostrarBoton = true
                this.mostrarBotonLogIn = false
            })
            .catch(error => {
                this.mostrarBoton = false
                this.mostrarBotonLogIn = true
            }
            )
        },

        enviarSuscripcion(){
            Swal.fire({
                title: '¡Suscripcion exitosa!',
                text: 'A partir de ahora recibirás todas nuestras novedades.',
                icon: 'success',
                confirmButtonText: 'CERRAR',
                confirmButtonColor: 'black',
            })
        },
        
        irALogin(){
            window.location.href = "/web/login.html"
        },
        //FIN  METODO PARA SABER SI ESTA LOGUEADO O NO
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
    }
});
app.mount('#vueApp');