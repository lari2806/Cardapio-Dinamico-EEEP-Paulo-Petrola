package br.com.cardapio.service;

import br.com.cardapio.model.User;

public interface UserService {

    User findById(long id);
    
    User create(User user);
}