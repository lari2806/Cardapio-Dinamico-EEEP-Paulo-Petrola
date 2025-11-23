package br.com.cardapio.service;

import java.util.List;

import br.com.cardapio.model.Menu;

public interface MenuService {

    Menu findById(long id);
    
    Menu create(Menu menu);

    Menu update(Menu menu);

    List<Menu> findByWeek(Integer week);

    List<Menu> filtrar(String mealType, String dayOfWeek, String food);
}