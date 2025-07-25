package com.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglot.entity.AuthorityEntity;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {

	AuthorityEntity findByName(String name);

}