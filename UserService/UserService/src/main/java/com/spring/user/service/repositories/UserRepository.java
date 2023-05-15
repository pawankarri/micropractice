package com.spring.user.service.repositories;

import com.spring.user.service.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

}
