package br.com.cardapio.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.cardapio.dto.MenuDTO;
import br.com.cardapio.model.Menu;
import br.com.cardapio.repository.MenuRepository;
import br.com.cardapio.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu findById(long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu não encontrado"));
    }

    @Override
    public Menu create(Menu menu) {
        
        return menuRepository.save(menu);
    }


    @Override
    public Menu update(Menu menu) {
        if (!menuRepository.existsById(menu.getId())) {
            throw new IllegalArgumentException("Este menu não existe.");
        }
        return menuRepository.save(menu);
    }

    @Override
    public List<Menu> filtrar(String mealType, String dayOfWeek, String food) {
        // Se o parâmetro food for null, transformamos em "" para buscar todos
        String foodFilter = food == null ? "" : food;
        return menuRepository.findByMealTypeAndDayOfWeekAndFoodContainingIgnoreCase(
                mealType, dayOfWeek, foodFilter
        );
    }

    @Override
    public List<Menu> findByWeek(Integer week) {
        return menuRepository.findByWeek(week);
    }

    @Override
public List<MenuDTO> updateAll(List<MenuDTO> listaDto) {

    Map<Integer, String> dias = Map.of(
        1, "Segunda-feira",
        2, "Terça-feira",
        3, "Quarta-feira",
        4, "Quinta-feira",
        5, "Sexta-feira"
    );

    Map<String, String> refeicoes = Map.of(
        "manha", "Merenda da manhã",
        "almoco", "Almoço",
        "tarde", "Merenda da tarde"
    );

    List<MenuDTO> result = new ArrayList<>();

    for (MenuDTO dto : listaDto) {

        Integer semana = dto.getWeek();
        Integer diaNumero;
        try {
            diaNumero = Integer.parseInt(dto.getDayOfWeek());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Dia inválido enviado pelo front: " + dto.getDayOfWeek());
        }

        String dia = dias.get(diaNumero);
        String refeicao = refeicoes.get(dto.getMealType().toLowerCase());

        if (dia == null || refeicao == null) {
            throw new RuntimeException(
                "Valores inválidos recebidos do front: dia=" + dto.getDayOfWeek() +
                ", refeição=" + dto.getMealType()
            );
        }

        // Busca se já existe
        List<Menu> lista = menuRepository.findByWeekAndDayOfWeekAndMealType(
                semana,
                dia,
                refeicao
        );

        if (lista.isEmpty()) {
            // Não existe → cria novo registro
            Menu novoMenu = new Menu();
            novoMenu.setWeek(semana);
            novoMenu.setDayOfWeek(dia);
            novoMenu.setMealType(refeicao);
            novoMenu.setFood(dto.getFood());
            novoMenu.setCalories(dto.getCalories());
            menuRepository.save(novoMenu);
        } else {
            // Atualiza todos os registros encontrados
            for (Menu menu : lista) {
                menu.setFood(dto.getFood());
                menu.setCalories(dto.getCalories());
                menuRepository.save(menu);
            }
        }

        result.add(dto);
    }

    return result;
}

}



