package br.com.cardapio.service.impl;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import br.com.cardapio.model.User;
import br.com.cardapio.repository.UserRepository;
import br.com.cardapio.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NoSuchElementException("Usuário não encontrado com email: " + email);
        }
        return user;
    }

    @Override
    public User create(User user) {
        if (userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("Este usuário já existe.");
        }
        
            return userRepository.save(user);
        
    }
    
}