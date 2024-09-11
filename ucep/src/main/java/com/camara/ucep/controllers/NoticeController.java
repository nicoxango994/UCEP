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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    
    @GetMapping("/notice/create")
    public String showCreateNoticeForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "create_notice.html";  // Nombre de la vista con el formulario HTML
    }

    @PostMapping("/notice/upload")
    public String createNotice(
            @RequestParam("title") String title,
            @RequestParam("frontPage") MultipartFile frontPage,
            @RequestParam("content") String content) {
        try {
            noticeService.createNotice(title, frontPage, content);
            return "redirect:/notices";  // Redirige a la lista de noticias después de crear una noticia
        } catch (Exception e) {
            e.printStackTrace();
            return "error";  // En caso de error, redirige a una página de error
        }
    }
    
    
    @GetMapping("/noticias")
    public String showNotices(Model model) {
        List<Notice> notices = noticeService.getAllNotices();
        model.addAttribute("notices", notices);
        return "notices_grid.html";
    }

//    // Mapeo para listar todas las noticias
//    @GetMapping("/notice-list")
//    public String listNotices(Model model) {
//        List<Notice> notices = noticeService.getAllNotices();
//        model.addAttribute("notices", notices);
//        return "notice-list.html"; // Nombre de la vista Thymeleaf que lista las noticias
//    }
//
//    // Mapeo para ver el contenido completo de una noticia
//    @GetMapping("/notices/view/{id}")
//    public String viewNotice(@PathVariable("id") String id, Model model) {
//        Notice notice = noticeService.getNoticeById(id);
//        model.addAttribute("notice", notice);
//        return "notice-view.html"; // Nombre de la vista Thymeleaf que muestra la noticia completa
//    }
}
