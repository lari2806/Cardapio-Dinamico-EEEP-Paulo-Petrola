package br.com.cardapio.service;

import br.com.cardapio.model.Menu;
import java.util.List;

public interface MenuService {

    Menu findById(long id);

    Menu create(Menu menu);

    Menu update(Menu menu);

    List<Menu> filtrar(String food);
}
