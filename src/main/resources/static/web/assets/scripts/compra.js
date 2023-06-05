const { createApp } = Vue;
const app = createApp({
    data() {
        return {
        itemsParaPagar: [],
        numDeProductos: Number,
        total: Number,
        clienteActual: undefined,
        costoEnvio: Number,
        numeroTarjeta: undefined,
        cvvTarjeta: undefined,
        nombreTarjeta: undefined,
        fecha:undefined
        }
    },
    created() {
        this.traerDatos();
        axios.get('/api/clientes/actual')
        .then(response => {
            this.clienteActual = response.data;
            // console.log(response.data);
            this.costoEnvio = parseInt(response.data.codPostal.toString().substring(0, 2)) * 16;
            this.isLoading = false;  // Establecemos isLoading en false al finalizar la petición
        })
        .catch(error => {
            console.error(error);
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
        }
    }
});
app.mount('#vueApp');