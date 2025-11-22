package br.com.cardapio.controller;

import java.util.List;

import br.com.cardapio.dto.MenuDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cardapio.model.Menu;
import br.com.cardapio.service.MenuService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "*")
public class MenuController {

    @Autowired
    private MenuService menuService; 

    @GetMapping("/filter")
    public List<MenuDTO> filter(
        @RequestParam(required = false) String mealType,
        @RequestParam(required = false) String dayOfWeek,
        @RequestParam(required = false) String food
    ) {
        List<Menu> menus = menuService.filtrar(mealType, dayOfWeek, food);

        return menus.stream().map(menu -> {
        MenuDTO dto = new MenuDTO();  
        dto.setMealType(menu.getMealType());
        dto.setDayOfWeek(menu.getDayOfWeek());
        dto.setFood(menu.getFood());
        dto.setCalories(menu.getCalories());
        return dto;
    }).toList();

    }

    @PostMapping("/create")
    public ResponseEntity<MenuDTO> create(@RequestBody MenuDTO dto) {
    
    
        Menu menu = new Menu();
        menu.setMealType(dto.getMealType());
        menu.setDayOfWeek(dto.getDayOfWeek());
        menu.setFood(dto.getFood());
        menu.setCalories(dto.getCalories());
    
    
        Menu created = menuService.create(menu);
    
        MenuDTO response = new MenuDTO();
        response.setMealType(created.getMealType());
        response.setDayOfWeek(created.getDayOfWeek());
        response.setFood(created.getFood());
        response.setCalories(created.getCalories());
    
        return ResponseEntity.ok(response);
    }
}
