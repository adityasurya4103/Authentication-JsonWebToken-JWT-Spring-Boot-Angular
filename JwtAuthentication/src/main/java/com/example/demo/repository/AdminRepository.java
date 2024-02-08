package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	boolean existsByEmail(String email);

	Optional<Admin> findByEmail(String email);

	// Optional<Admin>: The method returns an Optional object containing an Admin
	// entity if one is found with the specified email address. If no such entity is
	// found, the Optional object will be empty (i.e., it will not contain any
	// value).

}