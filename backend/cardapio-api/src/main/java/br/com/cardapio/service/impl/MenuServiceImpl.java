package br.com.cardapio.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;

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
    public Menu updateMenu(Long id, MenuDTO menuDTO) {

        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu não encontrado"));

        existingMenu.setMealType(menuDTO.getMealType());
        existingMenu.setDayOfWeek(menuDTO.getDayOfWeek());
        existingMenu.setFood(menuDTO.getFood());
        existingMenu.setCalories(menuDTO.getCalories());

        return menuRepository.save(existingMenu);
    }

}
