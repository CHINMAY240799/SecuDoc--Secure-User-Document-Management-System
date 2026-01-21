package com.secudoc.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.secudoc.auth_service.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	 boolean existsByUserName(String username);
}
