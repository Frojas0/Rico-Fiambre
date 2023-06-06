const { createApp } = Vue;
const params = new URLSearchParams(location.search)
let nombreURL = ""
try {
  nombreURL = params.get("nombre").toUpperCase()
} catch (error) {
}

const app = createApp({
  data() {
    return {
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

      // MOSTRAR BOTON LOGOUT

      mostrarBoton : false,
      mostrarBotonLogIn : true
      // FIN MOSTRAR BOTON LOGOUT
    }
  },
  created() {
    this.estaLogueado()
    if (nombreURL === null || nombreURL === undefined || nombreURL === "") {
      this.ejecutarPrograma()
    } else {
      axios.get('/api/productoPeso')
        .then(response => {
          this.todosLosProductos = this.todosLosProductos.concat(response.data);
          this.productos = this.todosLosProductos.filter(producto => producto.nombre.includes(nombreURL))
          console.log(this.productos)
          this.isLoading = false
        })
        .then(response => {
          console.log(this.productos)
        })

      axios.get('/api/productoUni')
        .then(response => {
          this.todosLosProductos = this.todosLosProductos.concat(response.data);
          this.isLoading = false
        })
        .then(response => {
          this.productos = this.todosLosProductos.filter(producto => producto.nombre.includes(nombreURL))
          console.log(nombreURL)
          console.log(this.productos)

        })
    }
    axios.get('/api/tipos-producto')
      .then(response => {
        this.tipoDeProductos = response.data
        console.log(response.data)
        this.isLoading = false
      }
      )
  },
  methods: {
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

    enviarSuscripcion(){
      Swal.fire({
          title: '¡Suscripcion exitosa!',
          text: 'A partir de ahora recibirás todas nuestras novedades.',
          icon: 'success',
          confirmButtonText: 'CERRAR',
          confirmButtonColor: 'black',
      })
    },

    filtrarPor() {
      this.productos = this.todosLosProductos
      if (this.selectedTipoProducto === 'TODOS') {
        this.productos = this.todosLosProductos
      } else if (this.selectedTipoProducto === 'precioMayor') {
        this.productos.sort((a, b) => b.precio - a.precio)
      } else if (this.selectedTipoProducto === 'precioMenor') {
        this.productos.sort((a, b) => a.precio - b.precio)
      } else if (this.selectedTipoProducto === 'productoPeso') {
        this.productos = this.todosLosProductos.filter(producto => producto.esPorPeso)
      } else if (this.selectedTipoProducto === 'productoUni') {
        this.productos = this.todosLosProductos.filter(producto => !producto.esPorPeso)
      } else {
        this.productos = this.todosLosProductos
        productoTipo = []
        for (let i = 0; i < this.productos.length; i++) {
          if (this.productos[i].tipo === this.selectedTipoProducto) {
            productoTipo.push(this.productos[i])
          }
        }
        this.productos = productoTipo
      }
    },
    ejecutarPrograma() {
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
          console.log(nombreURL)
          this.productos = this.todosLosProductos
        })

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

    // ------------------------------------------------------------------------------------------------
    //               ESTO SOLO VA EN PRODUCTO.JS, LOS DEMAS JS NO LO NECESITAN

    addCartClicked(imagen, nombrePorParametro, precio, esPorPeso) {
      let encontrado = false;

      for (let i = 0; i < this.carritoPendientes.length; i++) {
        if (this.carritoPendientes[i].nombre === nombrePorParametro) {
          if (esPorPeso) {
            this.cantidadMas(nombrePorParametro, 50)
            encontrado = true;
            break;
          } else {
            this.cantidadMas(nombrePorParametro, 1)
            encontrado = true;
            break;
          }
        }
      }

      if (!encontrado) {
        if (esPorPeso) {
          let itemNuevo = {
            url: imagen,
            nombre: nombrePorParametro,
            precio: precio * (this.cantidadSeleccionada / 1000),
            cantidad: this.cantidadSeleccionada,
            esPorPeso: esPorPeso
          }
          this.carritoPendientes.push(itemNuevo);
        } else {
          let itemNuevo = {
            url: imagen,
            nombre: nombrePorParametro,
            precio: precio * this.cantidadSeleccionada,
            cantidad: this.cantidadSeleccionada,
            esPorPeso: esPorPeso
          }
          this.carritoPendientes.push(itemNuevo);
        }

      }

      // console.log( this.carritoPendientes)
      this.updatetotal()
      localStorage.setItem('carritoDeCompras', JSON.stringify(this.carritoPendientes))
    },

    // ------------------------------------------------------------------------------------------------

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
  },

  computed: {
    // actualizarLocalStorage() {
    //   localStorage.setItem('carritoDeCompras', JSON.stringify(this.carritoPendientes))
    // },
    filtro() {
      try {
        this.productos = this.todosLosProductos.filter(producto => producto.nombre.toUpperCase().includes(this.busquedaSeleccionada.toUpperCase()))
      } catch (error) {
      }
    }
  }
})
app.mount('#vueApp')