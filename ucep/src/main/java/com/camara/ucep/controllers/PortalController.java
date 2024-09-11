/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.camara.ucep.controllers;

import com.camara.ucep.entities.Notice;
import com.camara.ucep.services.NoticeService;
import com.camara.ucep.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nico
 */
@Controller
@RequestMapping("/")
public class PortalController {

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Método para manejar la página de inicio
    @GetMapping("/")
    public String home(Model model) {
        // Obtén la lista de noticias desde el servicio
        List<Notice> notices = noticeService.getAllNotices();
        
        // Añade la lista de noticias al modelo
        model.addAttribute("notices", notices);
        
        // Devuelve la vista "index", Thymeleaf buscará src/main/resources/templates/index.html
        return "index";
    }
}
