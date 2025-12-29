package com.Finance.demo.Repository;

import com.Finance.demo.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
