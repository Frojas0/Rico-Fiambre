const { createApp } = Vue;

// let cartIcon = document.querySelector("#cart-icon");
// let cart = document.querySelector(".cart");
// let closeCart = document.querySelector("#close-cart");

const app = createApp({
    data() {
        return {
          productos: [],
          imagenPro: undefined,
          nombrePro: undefined,
          precioPro: undefined,
          carritoPendientes:[],
          totalItems:0
        }
    },
    created() {
        this.ejecutarPrograma()
    },
    methods: {
        ejecutarPrograma() {

            const produtoTest1 = {
              imagen: './assets/imagenes/1.png',
              nombre: 'PICADA1',
              precio: '25',
              cantidad: 0
            } 

            const produtoTest2 = {
              imagen: './assets/imagenes/1.png',
              nombre: 'PICADA2',
              precio: '410',
              cantidad: 0

            } 

            const produtoTest3 = {
              imagen: './assets/imagenes/1.png',
              nombre: 'PICADA3',
              precio: '300',
              cantidad: 0,
            } 

            const produtoTest4 = {
              imagen: './assets/imagenes/1.png',
              nombre: 'PICADA4',
              precio: '250',
              cantidad: 0
            } 


            this.productos.push(produtoTest1)
            this.productos.push(produtoTest2)
            this.productos.push(produtoTest3)
            this.productos.push(produtoTest4)


            console.log(this.productos)

            // axios.get('api/test').then( elemento =>{
            //     this.productos = elemento
            // }).catch(err => {
            //   console.log(err)})

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

        buyButtonClicked() {
            // alert("Your Order is placed");
            // var cartContent = document.getElementsByClassName("cart-content")[0];
            // while (cartContent.hasChildNodes()) {
            //   cartContent.removeChild(cartContent.firstChild);
            // }
            // updatetotal();
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
        //   // Quantity Changes
            cantidadMas(nombre) {
            // var input = event.target;
            // if (isNaN(input.value) || input.value <= 0) {
            //   input.value = 1;
            // }

            for(let elemento of this.carritoPendientes){
              if(elemento.nombre === nombre)
                elemento.cantidad =  elemento.cantidad + 1
            }


            this.updatetotal()
            localStorage.setItem('carritoDeCompras', JSON.stringify(this.carritoPendientes))

          },

          cantidadMenos(nombre) {
            // var input = event.target;
            // if (isNaN(input.value) || input.value <= 0) {
            //   input.value = 1;
            // }
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

            // var cartContent = document.getElementsByClassName("cart-content")[0];
            // var cartBoxes = cartContent.getElementsByClassName("cart-box");
            // var total = 0;
            // for (var i = 0; i < cartBoxes.length; i++) {
            //   var cartBox = cartBoxes[i];
            //   var priceElement = cartBox.getElementsByClassName("cart-price")[0];
            //   var quantityElement = cartBox.getElementsByClassName("cart-quantity")[0];
            //   var price = parseFloat(priceElement.innerText.replace("$", ""));
            //   var quantity = quantityElement.value;
            //   total = total + price * quantity;
            // }
            // // If price Contain some Cents Value
            // total = Math.round(total * 100) / 100;
          
            // document.getElementsByClassName("total-price")[0].innerText = "$" + total;


          }
      },

      computed:{
          actualizarLocalStorage(){
            localStorage.setItem('carritoDeCompras', JSON.stringify(this.carritoPendientes))
          }
      }
})
app.mount('#vueApp')