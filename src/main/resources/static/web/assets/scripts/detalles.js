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
        console.log(this.productosPeso)
        console.log(this.productoUni)

        for(let element of this.productosPeso){
            if(element.nombre === "JAMON CRUDO"){
                this.productoFiltrado = element
            }
        }

        console.log(this.productoFiltrado)
    }

    

},
})
app.mount('#vueApp')