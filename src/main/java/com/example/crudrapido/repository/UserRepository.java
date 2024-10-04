package com.example.crudrapido.repository;

import com.example.crudrapido.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
