// URL do backend
const API_URL = "http://localhost:8080/menu";

const dayIndex = {
    "Segunda-feira": 0,
    "Terça-feira": 1,
    "Quarta-feira": 2,
    "Quinta-feira": 3,
    "Sexta-feira": 4
};


function getCellId(week, mealType, day) {
    let prefix = `s${week}_`;
    switch(mealType) {
        case "Merenda da manhã":
            prefix += "manha_";
            break;
        case "Almoço":
            prefix += "almoco_";
            break;
        case "Merenda da tarde":
            prefix += "tarde_";
            break;
        default:
            return null;
    }
    return prefix + (day + 1); 
}


fetch(API_URL)
    .then(res => res.json())
    .then(data => {
        data.forEach(item => {
            let w = item.week; 
            let d = dayIndex[item.dayOfWeek]; 
            let cellId = getCellId(w, item.mealType, d);
            if(cellId) {
                let cell = document.getElementById(cellId);
                if(cell) {
                    cell.innerText = item.food;
                }
            }
        });
    })
    .catch(err => console.error("Erro ao carregar o cardápio:", err));
