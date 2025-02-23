package com.nationalbank.nationalbankperu.service.impl;

import com.nationalbank.nationalbankperu.model.User;
import com.nationalbank.nationalbankperu.repository.IUserRepository;
import com.nationalbank.nationalbankperu.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNumIdentification(String numIdentification) {
        return userRepository.existsByNumIdentification(numIdentification);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByNumIdentification(String numIdentification) {
        return userRepository.findByNumIdentification(numIdentification);
    }
}
