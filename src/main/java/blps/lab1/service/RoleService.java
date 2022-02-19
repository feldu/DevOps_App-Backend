package blps.lab1.service;


import blps.lab1.entity.Role;
import blps.lab1.exception.DataNotFoundException;
import blps.lab1.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new DataNotFoundException("Such role doesn't exits"));
    }
}
