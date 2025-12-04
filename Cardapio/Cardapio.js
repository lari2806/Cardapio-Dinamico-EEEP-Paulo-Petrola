document.addEventListener("DOMContentLoaded", function() {
    const anoAtual = new Date().getFullYear();
    document.title = `Cardápio Paulo Petrola ${anoAtual}`;
});
const dayMap = {
    "Segunda-feira": 1,
    "Terça-feira": 2,
    "Quarta-feira": 3,
    "Quinta-feira": 4,
    "Sexta-feira": 5
};

const mealMap = {
    "merenda da manhã": "manha",
    "merenda manhã": "manha",
    "merenda_manha": "manha",

    "almoço": "almoco",
    "almoco": "almoco",

    "merenda da tarde": "tarde",
    "merenda_tarde": "tarde"
};

function preencherTabela(cardapio) {
    cardapio.forEach(item => {

        const week = item.week;    
        const day = dayMap[item.dayOfWeek];
        const meal = mealMap[item.mealType.toLowerCase()];

        if (!week || !day || !meal) {
            console.warn("Ignorado por dados faltando:", item);
            return;
        }

        const tabela = (week === 1 || week === 3) ? "s1" : "s2";

        const cellId = `${tabela}_${meal}_${day}`;
        const cell = document.getElementById(cellId);

        if (cell) {
            cell.textContent = item.food;
        }
    });
}


function carregarSemana(semana) {
    fetch(`http://localhost:8080/menu/week/${semana}`)
        .then(res => res.json())
        .then(data => {
            console.log("Semana carregada:", semana, data);
            preencherTabela(data);
        })
        .catch(err => console.error("Erro ao carregar semana " + semana, err));
}

carregarSemana(1);

carregarSemana(2);


