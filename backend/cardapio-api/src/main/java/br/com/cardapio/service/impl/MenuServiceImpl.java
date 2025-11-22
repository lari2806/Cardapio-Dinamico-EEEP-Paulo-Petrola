package br.com.cardapio.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
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
        // Não verifica ID, será gerado automaticamente
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
    public List<Menu> filtrar(String food) {
        return menuRepository.findByFoodContainingIgnoreCase(food == null ? "" : food);
    }
}
