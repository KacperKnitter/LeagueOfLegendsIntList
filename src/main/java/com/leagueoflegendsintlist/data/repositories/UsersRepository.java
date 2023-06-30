package com.leagueoflegendsintlist.data.repositories;


import com.leagueoflegendsintlist.data.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
