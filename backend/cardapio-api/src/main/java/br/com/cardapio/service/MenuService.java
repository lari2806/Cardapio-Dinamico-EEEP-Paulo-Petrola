package br.com.cardapio.service;

import java.util.List;

import br.com.cardapio.model.Menu;
import br.com.cardapio.dto.MenuDTO;

public interface MenuService {

    Menu findById(long id);
    
    Menu create(Menu menu);

    Menu update(Menu menu);

    List<Menu> findByWeek(Integer week);

    List<Menu> filtrar(String mealType, String dayOfWeek, String food);

    List<MenuDTO> updateAll(List<MenuDTO> menuList);
}