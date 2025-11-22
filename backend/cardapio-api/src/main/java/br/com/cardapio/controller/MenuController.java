package br.com.cardapio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cardapio.model.Menu;
import br.com.cardapio.service.MenuService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "*")
public class MenuController {

    @Autowired
    private MenuService service; 

     @GetMapping("/filter")
    public List<Menu> filtrar(
            @RequestParam(required = false) String mealType,
            @RequestParam(required = false) String dayOfWeek,
            @RequestParam(required = false) String food
    ){
        return service.filtrar(mealType, dayOfWeek, food);
    }

     @PostMapping("/create")
    public Menu create(@RequestBody Menu menu) {
        return service.create(menu);
    }
}
