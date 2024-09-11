/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.camara.ucep.services;

import com.camara.ucep.entities.Image;
import com.camara.ucep.entities.User;
import com.camara.ucep.enums.Rol;
import com.camara.ucep.exceptions.MiException;
import com.camara.ucep.respositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nico
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageService imageService;

    
    @Transactional
    public void createUsuario(MultipartFile file, String accUser, Rol rol, String name, String email, String password, String password2) throws MiException {

        validation(name, email, accUser, password, password2);

        User user = new User();

        user.setAccUser(accUser);
        user.setName(name);
        user.setEmail(email);


        user.setPassword(new BCryptPasswordEncoder().encode(password));

        user.setRol(rol);

        Image image = imageService.createImage(file);

        user.setImage(image);

        userRepository.save(user);
    }

    public User getUsuarioById(String id) {
        return userRepository.getUserById(id);
    }

    public List<User> getUsuariosAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User FindUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }

    public List<User> findUsersByAltaTrue() {
        List<User> usuarios = userRepository.findUsersByStateTrue();
        return usuarios;
    }

    public List<User> findUsusersByAltaFalse() {
        List<User> users = userRepository.findUsersByStateFalse();
        return users;
    }

    public List<User> findUsersAdmin() {
        List<User> users = userRepository.findUsersAdmin();
        return users;
    }

    @Transactional
    public void updateUsuario(MultipartFile file, String idUser, Rol rol, String nombre, String accUser, String email) throws MiException {

        Optional<User> respuesta = userRepository.findById(idUser);

        if (respuesta.isPresent()) {

            User user = respuesta.get();

            validationUpdate(user, nombre, email, accUser);

            user.setName(nombre);
            user.setEmail(email);

            user.setRol(rol);

            String idImagen = null;

            if (user.getImage() != null) {
                idImagen = user.getImage().getId();
            }

            Image image = imageService.updateImage(file, idImagen);

            user.setImage(image);

            userRepository.save(user);
        }

    }

    @Transactional
    public void updateUsuarioPassword(String idUser, String password, String password2) throws MiException {
        try {
            validationPassword(password, password2);

            Optional<User> respuesta = userRepository.findById(idUser);

            if (respuesta.isPresent()) {
                User user = respuesta.get();
                user.setPassword(new BCryptPasswordEncoder().encode(password));

                userRepository.save(user);

            } else {
                throw new MiException("El usuario con ID " + idUser + " no fue encontrado.");
            }
        } catch (Exception ex) {
            throw new MiException(ex.getMessage());
        }
    }

//    @Transactional
//    public void updateUsuarioRecoveryPassword (String email, String password) throws MiException {
//        try {
//            Optional<User> respuesta = userRepository.findByEmail(email);
//
//            if (respuesta.isPresent()) {
//
//                User user = respuesta.get();
//                user.setEmail(email);
//
//                user.setPassword(new BCryptPasswordEncoder().encode(password));
//
//                userRepository.save(user);
//            }
//        } catch (Exception ex) {
//            throw new MiException(ex.getMessage());
//        }
//    }

    @Transactional
    public void updateUserStateTrue (String id) {
        Optional<User> userResponse = userRepository.findById(id);
        // Persistimos con repositorio, buscamos por id, verificamos que la respuesta este presente y la asignamos a una variable usuario,
        // en esta se setea el alta como falso("eliminado") y se vuelve a persistir para guardar en el repositorio.
        if (userResponse.isPresent()) {
            User user = userResponse.get();
            user.setState(true);

            userRepository.save(user);
        }
    }

    @Transactional
    public void updateUserState(String id, Boolean state) {
        Optional<User> userResponse = userRepository.findById(id);
        //Persistimos con repositorio, buscamos por id, verificamos que la respuesta este presente y la asignamos a una variable usuario,
        // en esta se setea el alta como falso("eliminado") y se vuelve a persistir para guardar en el repositorio.
        if (userResponse.isPresent()) {
            User user = userResponse.get();
            user.setState(state);

            userRepository.save(user);
        }
    }

    @Transactional
    public void updateUsuarioRolAdmin(String id) {
        Optional<User> userResponse = userRepository.findById(id);
        if (userResponse.isPresent()) {
            User user = userResponse.get();
            user.setRol(Rol.ADMIN);

            userRepository.save(user);
        }
    }

    @Transactional
    public void deleteUsuarioById (String id) {
        Optional<User> userResponse = userRepository.findById(id);
        //Persistimos con repositorio, buscamos por id, verificamos que la respuesta este presente y la asignamos a una variable usuario,
        // en esta se setea el alta como falso("eliminado") y se vuelve a persistir para guardar en el repositorio.
        if (userResponse.isPresent()) {
            User user = userResponse.get();
            user.setState(false);

            userRepository.save(user);
        }
    }

    private void validation (String name, String email, String accUser, String password, String password2) throws MiException {

        // byte bites = (byte) 1048576;

        if (name == null || name.trim().isEmpty()) {
            throw new MiException("El Nombre no puede ser nulo o estar vacío");
        }
        if (accUser == null || accUser.trim().isEmpty()) {
            throw new MiException("El Nombre de usuario no puede ser nulo o estar vacio");
        } else if (existsByAccUser(accUser)) {
            throw new MiException("Ya existe una cuenta con ese Nombre de usuario registrado..");
        }
//        if (ubicacion == null || ubicacion.equals("")) {
//            throw new MiException("La Ubicacion no puede ser nula o estar vacia");
//        }
        if (email == null || email.isEmpty()) {
            throw new MiException("El Email no puede ser nulo o estar vacio");
        } else if (existsByEmail(email)) {
            throw new MiException("Ya existe una cuenta con ese Email registrado..");
        }
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiException("La Contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las Contraseñas ingresadas deben ser iguales");
        }

    }

    private void validationUpdate (User user, String nombre, String email, String accUser) throws MiException {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new MiException("El Nombre no puede ser nulo o estar vacío");
        }
        if (accUser == null || accUser.trim().isEmpty()) {
            throw new MiException("El Nombre de usuario no puede ser nulo o estar vacio");
        } else if (!accUser.equals(user.getAccUser()) && existsByAccUser(accUser)) {
            throw new MiException("Ya existe una cuenta con ese Nombre de usuario registrado..");
        }
//        if (ubicacion == null || ubicacion.equals("")) {
//            throw new MiException("La Ubicacion no puede ser nula o estar vacia");
//        }
        if (email == null || email.isEmpty()) {
            throw new MiException("El Email no puede ser nulo o estar vacio");
        } else if (!email.equals(user.getEmail()) && existsByEmail(email)) {
            throw new MiException("Ya existe una cuenta con ese Email registrado..");
        }

    }

    private void validationPassword (String password, String password2) throws MiException {

        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiException("La Contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las Contraseñas ingresadas deben ser iguales");
        }

    }

    public boolean existsByAccUser(String accUser) throws MiException {
        try {
            Optional<User> user = userRepository.findByAccUser(accUser);
            if (user.isPresent()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public boolean existsByEmail(String email) throws MiException {
        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }


@Transactional
@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(email);

    if (user.isPresent()) {
        User user1 = user.get();

        // Si el usuario tiene el alta en 'false'
        if (!user1.isState()) {
            throw new UsernameNotFoundException("La cuenta ha sido baneada o eliminada: " + email);
        }

        List<GrantedAuthority> permissions = new ArrayList<>();
        // Se añaden los permisos
        GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user1.getRol().toString());
        permissions.add(p);

        return new org.springframework.security.core.userdetails.User(user1.getEmail(), user1.getPassword(), permissions);
    } else {
        throw new UsernameNotFoundException("Usuario no encontrado: " + email);
    }
}

}
