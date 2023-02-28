package com.project.memories.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.memories.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

//	@Query("SELECT u FROM User u WHERE u.email = ?1")
//    User findByEmail(String email);

	@Query("SELECT s FROM User s WHERE s.email = ?1")
	Optional<User> findUserByEmail(String email);

}
