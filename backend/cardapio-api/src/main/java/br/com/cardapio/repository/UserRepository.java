package br.com.cardapio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.cardapio.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
}