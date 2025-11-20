package br.com.cardapio.service;

import br.com.cardapio.model.User;

public interface UserService {

    User findById(long id);
    
    User findByEmail(String email);

    User create(User user);
}