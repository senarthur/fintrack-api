package com.arthursena.fin_track.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.arthursena.fin_track.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
}
