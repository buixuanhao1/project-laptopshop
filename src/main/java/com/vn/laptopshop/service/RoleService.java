package com.vn.laptopshop.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.Role;
import com.vn.laptopshop.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

}
