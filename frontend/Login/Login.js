document.addEventListener("DOMContentLoaded", function() {
    const anoAtual = new Date().getFullYear();
    document.title = `Cardápio Paulo Petrola - Login ${anoAtual}`;
});

document.querySelector('form.register-form').addEventListener('submit', function(e) {

     const email = document.getElementById("email").value;
     const password = document.getElementById("password").value;

    fetch('https://localhost:8080/auth/login', {   
        method: 'POST',                      
        headers: {
            'Content-Type': 'application/json' // Informa que vamos enviar JSON
        },
        body: JSON.stringify({ email, password }) // Converte os dados para JSON
    })

    .then(response => {

        
        if (!response.ok) {
            throw new Error('Login falhou');  // se essa condição for true, vai ir logo pro catch na linha 55
        }

       
        return response.json();
    })

    .then(data => {

        // se tudo der certo, então  vai fazer a verificação do email.
        if (data.success) {
            window.location.href = 'CardapioGestao.html';

        } else {
            alert('Email ou senha inválidos');
        }
    })

    .catch(error => {
        console.error('Erro no login:', error); 
        alert('Erro ao tentar fazer login');  
    });
});
