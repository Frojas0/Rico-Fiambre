const { createApp } = Vue;

// let cartIcon = document.querySelector("#cart-icon");
// let cart = document.querySelector(".cart");
// let closeCart = document.querySelector("#close-cart");

const app = createApp({
    data() {
        return {
          todosLosProductos: [],
          productos : [],
          imagenPro: undefined,
          nombrePro: undefined,
          precioPro: undefined,
          carritoPendientes:[],
          totalItems:0,
          listaModalDetalles: []
        }
    },
    created() {
        this.ejecutarPrograma()

    },
    methods: {
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
            console.log(this.productos)
          })
        },
        mostrarProductosPorPeso(){
          this.productos = this.todosLosProductos
          productoPeso1 = []
          for (let i = 0; i < this.productos.length; i++) {
            if (this.productos[i].esPorPeso) {
              productoPeso1.push(this.productos[i])
            }
          }
          this.productos = productoPeso1
        },
        mostrarProductosPorUnidad(){
          this.productos = this.todosLosProductos
          productoUni1 = []
          for (let i = 0; i < this.productos.length; i++) {
            if (this.productos[i].esPorPeso == false) {
              productoUni1.push(this.productos[i])
            }
          }
          this.productos = productoUni1
        },
        mostrarDetails(valor) {
          for (let i of this.todosLosProductos) {
              if (valor == i.nombre) {
                  this.listaModalDetalles = i
              }
          }
      },



        abrirCarrito(){
            let cart = document.querySelector(".cart");
            cart.classList.add('active');
            console.log(cart)
        },

        cerrarCarrito(){
            let cart = document.querySelector(".cart");
            cart.classList.remove("active");
        },
        removeCartItem(nombre) {
        console.log('funciona borrar')
        console.log(nombre)

        let contador =0
        for(let element of this.carritoPendientes){
            if(element.nombre === nombre){
                console.log(element.nombre)
                break;
            }
            contador++
        }

        console.log(contador)
        this.carritoPendientes.splice(contador,1)

        console.log(this.carritoPendientes)
        this.updatetotal()
        localStorage.setItem('carritoDeCompras', JSON.stringify(this.carritoPendientes))
        },
          cantidadMas(nombre) {

          for(let elemento of this.carritoPendientes){
            if(elemento.nombre === nombre)
              elemento.cantidad =  elemento.cantidad + 1
          }


          this.updatetotal()
          localStorage.setItem('carritoDeCompras', JSON.stringify(this.carritoPendientes))

        },

        cantidadMenos(nombre) {
          for(let elemento of this.carritoPendientes){
            if(elemento.nombre === nombre)
              elemento.cantidad = elemento.cantidad - 1
          }


          this.updatetotal()
          localStorage.setItem('carritoDeCompras', JSON.stringify(this.carritoPendientes))

        },

            addCartClicked(imagen, nombre, precio) {
            let itemNuevo = {
              imagen: imagen,
              nombre: nombre,
              precio: precio,
              cantidad: 1
            }

            this.carritoPendientes.push(itemNuevo)

            console.log( this.carritoPendientes)

            // this.addProductToCart()
            this.updatetotal()
            localStorage.setItem('carritoDeCompras', JSON.stringify(this.carritoPendientes))
          },


            updatetotal() {
            console.log(this.totalItem)

            this.totalItems =0
            for(let element of this.carritoPendientes){
              this.totalItems = this.totalItems + parseInt(element.precio)*element.cantidad

            }
            console.log(this.contador)
            console.log(this.totalItems)
          }
      },

      computed:{
          actualizarLocalStorage(){
            localStorage.setItem('carritoDeCompras', JSON.stringify(this.carritoPendientes))
          }
      }
})
app.mount('#vueApp')