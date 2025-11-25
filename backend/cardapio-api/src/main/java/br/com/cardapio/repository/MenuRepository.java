package br.com.cardapio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import br.com.cardapio.model.Menu;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Long> {

    List<Menu> findByWeekAndDayOfWeekAndMealType(
        Integer week,
        String dayOfWeek,
        String mealType
    );

    List<Menu> findByMealTypeAndDayOfWeekAndFoodContainingIgnoreCase(
        String mealType,
        String dayOfWeek,
        String food
    );

    List<Menu> findByWeek(Integer week);

}
