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
            if (!response.ok) {
                throw new Error("Credenciais inválidas");
            }
            return response.json();
        })
        .then(data => {

            if (data.token) {
                localStorage.setItem("token", data.token);
                window.location.href = "../CardapioGestao/CardapioGestao.html";
            } else {
                alert("Email ou senha inválidos");
            }
        })
        .catch(error => {
            console.error("Erro no login:", error);
            alert("Erro ao conectar ao servidor");
        });
});
