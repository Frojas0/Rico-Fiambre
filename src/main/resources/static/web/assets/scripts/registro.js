const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            nombre: "",
            apellido: "",
            email: "",
            direccion: "",
            ciudad: "",
            codigoPostal: "",
            telefono: "",
            contrasena: "",
        }
    },

    methods: {
        iniciarSesion(){
            axios.post('/api/login','email=' + this.email + '&contrasena=' + this.contrasena)
            .then(response => {
               window.location.href = "/web/index.html"})
            
            .catch(error => Swal.fire({
                title: 'Error',
                text: error.response.data,
                icon: 'error'
            }))
        },

        registrarse(){
            axios.post('/api/registrar-cliente',{
                "nombre": this.nombre,
                "apellido": this.apellido,
                "email": this.email,
                "direccion": this.direccion,
                "ciudad": this.ciudad,
                "codPostal": this.codigoPostal,
                "telefono": this.telefono,
                "contrasena":this.contrasena
            })

            .then(response => {
                console.log('cliente registrado');
                this.iniciarSesion();
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