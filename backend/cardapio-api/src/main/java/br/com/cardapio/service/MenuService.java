package br.com.cardapio.service;

import br.com.cardapio.model.Menu;

public interface MenuService {

    Menu findById(long id);
    
    Menu create(Menu menu);

    Menu update(Menu menu);
}