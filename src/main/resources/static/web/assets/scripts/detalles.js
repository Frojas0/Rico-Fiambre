const {createApp} = Vue

const app = createApp({
data(){
        return {
            productosPeso: [],
            productoUni: [],
            nombreParams: undefined,
            productoFiltrado: undefined
        }
    },

    created(){
        const params =  new URLSearchParams(location.search)
        this.nombreParams = params.get("nombre")
        this.getData()

    },

    methods:{
    async getData(){
            axios.get('/api/productoPeso')
            .then(elemento =>{
                this.productosPeso = elemento.data
                axios.get('/api/productoUni')
                .then(elemento =>{
                    this.productoUni = elemento.data

                    this.getFilterData()

                })
            })
    },

    getFilterData(){
        for(let element of this.productosPeso){
            if(element.nombre === "HUEVO FRITO"){
                this.productoFiltrado = element
            }
        }

        if(this.productoFiltrado === undefined){
            for(let element of this.productoUni){
                if(element.nombre === "CERVEZA CORONA LATA 269ml"){
                    this.productoFiltrado = element
                }
            }
        }
        console.log(this.productoFiltrado)
    }
    

},
})
app.mount('#vueApp')