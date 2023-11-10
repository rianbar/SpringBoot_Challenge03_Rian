package com.compassuol.msuser.repository;

import com.compassuol.msuser.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByCpf(String cpf);
    Optional<UserModel> findByEmail(String email);
}
