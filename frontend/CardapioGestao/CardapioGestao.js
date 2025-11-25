    document.addEventListener("DOMContentLoaded", function() {
        const anoAtual = new Date().getFullYear();
        document.title = `Cardápio Gestão ${anoAtual}`;
    });
    const dayMap = {
        "Segunda-feira": 1,
        "Segunda-Feira": 1,
        "Terça-feira": 2,
        "Terça-Feira": 2,
        "Quarta-feira": 3,
        "Quarta-Feira": 3,
        "Quinta-feira": 4,
        "Quinta-Feira": 4,
        "Sexta-feira": 5,
        "Sexta-Feira": 5
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

    let editando = false;

    document.querySelector(".editar-btn").addEventListener("click", function () {
        if (!editando) {
            ativarModoEdicao();
        } else {
            salvarEdicao();
        }
    });

    function ativarModoEdicao() {
        editando = true;
        const btn = document.querySelector(".editar-btn");
        btn.textContent = "Salvar";

        const cells = document.querySelectorAll("td[id]");

        cells.forEach(cell => {
            const valorAtual = cell.textContent.trim();
            cell.innerHTML = `<input type="text" value="${valorAtual}" class="edit-input">`;
        });
    }

    function salvarEdicao() {
    editando = false;
    const btn = document.querySelector(".editar-btn");
    btn.textContent = "Editar";

    const cells = document.querySelectorAll("td[id]");

    const dadosAtualizados = [];

    cells.forEach(cell => {
        const input = cell.querySelector("input");
        if (!input) return;  // ignora células sem input
        const novoValor = input.value.trim();
        cell.textContent = novoValor;

        const partes = cell.id.split("_"); // ex: s1_manha_3
        const semana = partes[0] === "s1" ? 1 : 2; 
        const refeicao = partes[1]
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .replace("ã", "a")
            .replace("ç", "c");
        const dia = parseInt(partes[2]);

        dadosAtualizados.push({
            mealType: refeicao,
            dayOfWeek: dia.toString(),
            food: novoValor,
            calories: 0,
            week: semana
        });
    });

    console.log("JSON que será enviado para o backend:");
    console.log(dadosAtualizados);

    if (dadosAtualizados.length === 0) {
        console.warn("Nenhum dado para enviar. Verifique se ativou o modo edição e preencheu os campos!");
        return;
    }

    const token = localStorage.getItem("token");
    if (!token) {
        console.error("Token JWT não encontrado. Faça login primeiro!");
        return;
    }

    fetch("http://localhost:8080/menu/update-all", {
        method: "PUT",
        headers: { 
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
         },
        body: JSON.stringify(dadosAtualizados)
    })
    .then(res => {
        console.log("Resposta do fetch:", res);
        if (!res.ok) throw new Error("Erro ao salvar");
        return res.json();
    })
    .then(data => console.log("Dados retornados pelo backend:", data))
    .catch(err => console.error("Erro no fetch:", err));
}


