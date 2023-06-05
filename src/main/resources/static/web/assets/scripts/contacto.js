const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            valorSeleccionado: "",
            isLoading: true
        }
    },
    created() {
        this.metodoejemplo()
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
        metodoejemplo() {
            this.isLoading = false
        }
    }
})
app.mount('#vueApp')