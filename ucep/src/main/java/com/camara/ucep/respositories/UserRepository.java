/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.camara.ucep.respositories;

import com.camara.ucep.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Nico
 */
public interface UserRepository extends JpaRepository<User, String> {
    
    @Query("SELECT u FROM User u WHERE u.id = :id")
    public User getUserById(@Param("id") String id);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    public Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.accUser = :accUser")
    public Optional<User> findByAccUser(@Param("accUser") String accUser);

    @Query("SELECT u FROM User u WHERE u.state = true")
    public List<User> findUsersByStateTrue();

    @Query("SELECT u FROM User u WHERE u.state = false")
    public List<User> findUsersByStateFalse();

    @Query("SELECT u FROM User u WHERE u.rol = 'ADMIN' ORDER BY u.name ASC")
    public List<User> findUsersAdmin();
}
