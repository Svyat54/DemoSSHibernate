package org.example.service;

import org.example.entity.Role;
import org.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void save(Long id, String name){
        Role roleUser = roleRepository.save(new Role(id, name));
    }

    public Role getById(Long id){
        return roleRepository.findById(id).orElse(null);
    }
}
