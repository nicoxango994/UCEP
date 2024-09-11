
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.camara.ucep.controllers;

/**
 *
 * @author Nico
 */
import com.camara.ucep.entities.Notice;
import com.camara.ucep.services.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/new-notice")
    public String showNewNoticeForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "new-notice";
    }

//    @PostMapping("/new-notice")
//    public String saveNotice(Notice notice, MultipartFile imageFile) {
//        // Guarda la imagen de portada (imagenFile) en el servidor o en un servicio de almacenamiento
//        // Luego establece la URL en la propiedad frontPage de notice
//
//        notice.setUploadDate(LocalDate.now());
//        noticeService.saveNotice(notice);
//
//        return "redirect:/notices"; // Redirige a la lista de noticias después de guardar
//    }
}