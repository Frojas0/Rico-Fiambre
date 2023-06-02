// const params =  new URLSearchParams(location.search)
// let nombre = params.get("nombre")
let nombre = "JAMON CRUDO"

const {createApp} = Vue
const app = createApp({
data(){
        return {
            productosPeso: [],
            productoUni: [],
            nombreParams: undefined,
            productoFiltrado: undefined,
            tipoProducto: undefined,
            todosLosProductos: []
        }
    },

    created(){
        this.nombreParams = nombre
        this.getProductoPeso()
        this.getProductoUni()
    },

    methods:{
    getProductoPeso(){
            axios.get('/api/productoPeso')
            .then(elemento =>{
                this.productosPeso = elemento.data
                console.log(this.productosPeso)
            })
    },

    getProductoUni(){
        axios.get('/api/productoUni')
        .then(elemento =>{
            this.productoUni = elemento.data
            console.log(this.productoUni)
            this.getFilterData()
        })
    },

    getFilterData(){
        for(let element of this.productosPeso){
            this.todosLosProductos.push(element)
        }

        for(let element of this.productoUni){
            this.todosLosProductos.push(element)

        }
        console.log(this.todosLosProductos)
        
        this.productoEncontrado()
    },

    productoEncontrado(){
        this.todosLosProductos.forEach(element => {
            if(element.nombre === nombre){
                this.productoFiltrado = element
                
            }
        });
    console.log(this.productoFiltrado)
    },

},

})
app.mount('#vueApp')