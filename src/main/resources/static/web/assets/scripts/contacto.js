const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            valorSeleccionado: "",

            // DATOS CARRITO
            todosLosProductos: [],
            productos: [],
            imagenPro: undefined,
            nombrePro: undefined,
            precioPro: undefined,
            carritoPendientes: [],
            totalItems: 0,
            listaModalDetalles: [],
            busquedaSeleccionada: undefined,
            inputs: [],
            cantidadSeleccionada: Number,
            checkboxChecked: false,
            mostrarProductosUnidad: false,
            tipoDeProductos: [],
            selectedTipoProducto: undefined,
            isLoading: true,
            blurONoBluEsaEsLaCuestion: 'clase-no-blur',
            // FIN DATO CARRITO

            // MOSTRAR BOTON LOGOUT

            mostrarBoton : false,
            mostrarBotonLogIn : true
            // FIN MOSTRAR BOTON LOGOUT
        }
    },
    created() {
        this.metodoejemplo()
        this.metodosIniciales()
        this.estaLogueado()
    },
    methods: {
        // METODOS DEL CARRITO DE COMPRAS
        metodosIniciales() {
            axios.get('/api/productoPeso')
                .then(response => {
                    this.todosLosProductos = this.todosLosProductos.concat(response.data);
                    this.productos = this.todosLosProductos
                })
                .then(response => {
                    console.log(this.productos)
                })

            axios.get('/api/productoUni')
                .then(response => {
                    this.todosLosProductos = this.todosLosProductos.concat(response.data);
                    this.productos = this.todosLosProductos
                })
                .then(response => {
                    this.productos = this.todosLosProductos
                })

            axios.get('/api/tipos-producto')
                .then(response => {
                    this.tipoDeProductos = response.data
                    console.log(response.data)
                    this.isLoading = false
                }
                )
        },
        actualizarCantidad(cantidad) {
            let numero = parseFloat(cantidad);
            this.cantidadSeleccionada = numero
        },
        abrirCarrito() {
            let cart = document.querySelector(".cart");
            cart.classList.add('active');
            this.carritoPendientes = JSON.parse(localStorage.getItem('carritoDeCompras')) || []
            this.blurONoBluEsaEsLaCuestion = 'clase-blur';
            this.updatetotal()
        },
        cerrarCarrito() {
            let cart = document.querySelector(".cart");
            cart.classList.remove("active");
            this.blurONoBluEsaEsLaCuestion = 'clase-no-blur'
        },
        removeCartItem(nombre) {
            console.log('funciona borrar')
            console.log(nombre)

            let contador = 0
            for (let element of this.carritoPendientes) {
                if (element.nombre === nombre) {
                    console.log(element.nombre)
                    break;
                }
                contador++
            }
            console.log(contador)
            this.carritoPendientes.splice(contador, 1)
            console.log(this.carritoPendientes)
            this.updatetotal()
            localStorage.setItem('carritoDeCompras', JSON.stringify(this.carritoPendientes))
        },
        cantidadMas(nombre, valor) {
            for (let elemento of this.carritoPendientes) {
                if (elemento.nombre === nombre) {
                    if (valor == 1) {
                        elemento.precio += (elemento.precio / elemento.cantidad)
                        elemento.cantidad = elemento.cantidad + valor
                    } else {
                        elemento.precio += ((50 * elemento.precio) / elemento.cantidad)
                        elemento.cantidad = elemento.cantidad + valor
                    }
                }
            }
            this.updatetotal()
            localStorage.setItem('carritoDeCompras', JSON.stringify(this.carritoPendientes))
        },

        cantidadMenos(nombre, valor) {
            for (let elemento of this.carritoPendientes) {
                if (elemento.nombre === nombre && elemento.cantidad - valor >= 1) {
                    if (valor == 1) {
                        elemento.precio -= (elemento.precio / elemento.cantidad) * valor
                        elemento.cantidad = elemento.cantidad - valor
                    } else {
                        elemento.precio -= ((50 * elemento.precio) / elemento.cantidad)
                        elemento.cantidad = elemento.cantidad - valor
                    }
                }
            }
            this.updatetotal()
            localStorage.setItem('carritoDeCompras', JSON.stringify(this.carritoPendientes))

        },
        updatetotal() {
            this.totalItems = 0;
            console.log(this.carritoPendientes);
            for (let element of this.carritoPendientes) {
                this.totalItems += element.precio
            }

        },

        irAComprar() {
            axios.get('/api/clientes/actual')
                .then(response => {
                    window.location.replace('./compra.html')
                })
                .catch(error => {
                    console.log(error)
                    Swal.fire({
                        icon: 'info',
                        text: 'Tienes que estar logueado para comprar',
                    })
                })
        },
        // FIN METODOS DEL CARRITO DE COMPRAS
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

        enviarMensaje(){
            Swal.fire({
                title: 'Mensaje enviado!',
                text: 'A la brevedad te estaremos respondiendo',
                icon: 'success',
                confirmButtonText: 'CERRAR',
                confirmButtonColor: 'black',
            })
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

        metodoejemplo() {
            this.isLoading = false
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
        irALogin(){
            window.location.href = "/web/login.html"
        }
        //FIN  METODO PARA SABER SI ESTA LOGUEADO O NO
    }
})
app.mount('#vueApp')