/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.camara.ucep.entities;

import com.camara.ucep.enums.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
/**
 *
 * @author Nico
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String accUser;
    private String password;
    private String name;
    @OneToOne
    private Image image;
    private String email;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    private boolean state;

    public User() {
        this.state = true;
    }

    public User(String id, String accUser, String password, String name, Image image, String email, Rol rol, boolean state) {
        this.id = id;
        this.accUser = accUser;
        this.password = password;
        this.name = name;
        this.image = image;
        this.email = email;
        this.rol = rol;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getAccUser() {
        return accUser;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public String getEmail() {
        return email;
    }

    public Rol getRol() {
        return rol;
    }

    public boolean isState() {
        return state;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAccUser(String accUser) {
        this.accUser = accUser;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setState(boolean state) {
        this.state = state;
    }
 
}
