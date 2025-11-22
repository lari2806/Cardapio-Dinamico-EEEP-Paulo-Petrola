async function carregarMenu() {
    const res = await fetch("http://localhost:8080/menu");
    const dados = await res.json();

    dados.forEach(item => {
        const dia = item.dayOfWeek.toLowerCase();   
        const refeicao = item.mealType.toLowerCase(); 

        const id = `${refeicao}_${dia}`;  
        const celula = document.getElementById(id);

        if (celula) {
            celula.innerText = item.food; 
        }
    });
}

carregarMenu();

