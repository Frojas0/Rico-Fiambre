const params = new URLSearchParams(location.search)
let nombre = params.get("nombre")
// let nombre = "JAMON COCIDO NATURAL"

const { createApp } = Vue
const app = createApp({
    data() {
        return {
            productosPeso: [],
            productoUni: [],
            // nombreParams: undefined,
            productoFiltrado: undefined,
            tipoProducto: undefined,
            todosLosProductos: []
        }
    },

    created() {
        this.getProductoPeso()
        this.getProductoUni()
    },

    methods: {
        getProductoPeso() {
            axios.get('/api/productoPeso')
                .then(res => {
                    this.todosLosProductos = this.todosLosProductos.concat(res.data)
                    console.log(this.todosLosProductos);
                    this.encontrarProducto();
                })
        },

        getProductoUni() {
            axios.get('/api/productoUni')
                .then(res => {
                    this.todosLosProductos = this.todosLosProductos.concat(res.data)
                    console.log(this.todosLosProductos);
                    this.encontrarProducto();
                })
        },

        encontrarProducto() {
            this.todosLosProductos.forEach(element => {
                if (element.nombre === nombre) {
                    this.productoFiltrado = element
                }
            });
            // console.log(this.productoFiltrado)
        },

    },

})
app.mount('#vueApp')