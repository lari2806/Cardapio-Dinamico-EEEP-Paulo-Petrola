package br.com.cardapio.controller;

import java.util.List;

import br.com.cardapio.dto.MenuDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cardapio.model.Menu;
import br.com.cardapio.service.MenuService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
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
        menu.setWeek(dto.getWeek());
    
    
        Menu created = menuService.create(menu);
    
        MenuDTO response = new MenuDTO();
        response.setMealType(created.getMealType());
        response.setDayOfWeek(created.getDayOfWeek());
        response.setFood(created.getFood());
        response.setCalories(created.getCalories());
        response.setWeek(created.getWeek());
    
        return ResponseEntity.ok(response);
    }

    @GetMapping("/week/{week}")
    public List<MenuDTO> getByWeek(@PathVariable Integer week) {
    List<Menu> menus = menuService.findByWeek(week);

    return menus.stream().map(menu -> {
        MenuDTO dto = new MenuDTO();
        dto.setMealType(menu.getMealType());
        dto.setDayOfWeek(menu.getDayOfWeek());
        dto.setFood(menu.getFood());
        dto.setCalories(menu.getCalories());
        dto.setWeek(menu.getWeek());
        return dto;
    }).toList();
    }
    
@PutMapping("/update-all")
@PreAuthorize("hasRole('GESTAO')")
public ResponseEntity<List<MenuDTO>> updateAll(@RequestBody List<MenuDTO> lista) {

    List<MenuDTO> updatedList = menuService.updateAll(lista);

    return ResponseEntity.ok(updatedList);
}


}
