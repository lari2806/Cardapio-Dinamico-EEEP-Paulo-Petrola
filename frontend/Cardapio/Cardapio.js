// title-year.js
document.addEventListener("DOMContentLoaded", function() {
    const anoAtual = new Date().getFullYear();
    document.title = `Cardápio Paulo Petrola ${anoAtual}`;
});