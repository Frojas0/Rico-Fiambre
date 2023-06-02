const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            email: "",
            contrasena: "",
        }
    },

    methods: {
        iniciarSesion(){
            axios.post('/api/login','email=' + this.email + '&contrasena=' + this.contrasena)
            .then(response => {
                if(this.email == "admin@admin.com"){
                    window.location.href = "/admin/admi.html"
                }
                else{
                    window.location.href = "/web/index.html"
                }
                })
            
            .catch(error => Swal.fire({
                title: 'Error',
                text: error.response.data,
                icon: 'error'
            }))
        }
    }
})
app.mount('#vueApp')