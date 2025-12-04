document.addEventListener("DOMContentLoaded", function () {
    const anoAtual = new Date().getFullYear();
    document.title = `Cardápio Paulo Petrola - Login ${anoAtual}`;
});

document.querySelector('form.register-form').addEventListener('submit', function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
    })
        .then(response => {

            // 🔴 LOGIN INCORRETO
            if (response.status === 401) {
                Swal.fire({
                    icon: 'error',
                    title: 'Credenciais inválidas',
                    text: 'Email ou senha incorretos.',
                    confirmButtonColor: '#d33'
                });
                return null;
            }

            // 🔴 ERROS DO SERVIDOR
            if (!response.ok) {
                Swal.fire({
                    icon: 'error',
                    title: 'Erro no servidor',
                    text: 'Algo inesperado ocorreu no servidor.',
                    confirmButtonColor: '#d33'
                });
                return null;
            }

            return response.json();
        })
        .then(data => {
            if (!data) return;

            // ✔ LOGIN BEM-SUCEDIDO
            if (data.token) {
                localStorage.setItem("token", data.token);
                localStorage.setItem("role", data.role); // caso queira guardar a role

                Swal.fire({
                    icon: 'success',
                    title: 'Login realizado!',
                    showConfirmButton: false,
                    timer: 1200
                });

                setTimeout(() => {
                    window.location.href = "../CardapioGestao/CardapioGestao.html";
                }, 1200);
            }
        })
        .catch(error => {
            // 🔴 SÓ PEGA ERROS REAIS: servidor offline, CORS, fetch quebrado
            console.error("Erro no login:", error);

            Swal.fire({
                icon: 'error',
                title: 'Erro no servidor',
                text: 'Não foi possível conectar ao servidor.',
                confirmButtonColor: '#d33'
            });
        });
});
