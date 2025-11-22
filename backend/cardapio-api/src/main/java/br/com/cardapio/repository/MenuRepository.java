package br.com.cardapio.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.cardapio.model.Menu;


@Repository
public interface MenuRepository extends CrudRepository<Menu, Long>{

    List<Menu> findByFoodContainingIgnoreCase(String food);

}

 