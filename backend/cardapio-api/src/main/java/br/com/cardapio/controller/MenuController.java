package br.com.cardapio.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import br.com.cardapio.model.Menu;
import br.com.cardapio.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        return menuService.create(menu);
    }

    @GetMapping("/search")
    public List<Menu> searchMenu(@RequestParam(required = false) String food) {
        return menuService.filtrar(food);
    }
}
