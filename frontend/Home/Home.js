document.addEventListener("DOMContentLoaded", function() {
    const anoAtual = new Date().getFullYear();
    const titulo = document.getElementById("titulo");
    titulo.textContent = `Tela de Inicio - ${anoAtual}`;
});
