/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.camara.ucep.controllers;

import com.camara.ucep.entities.Notice;
import com.camara.ucep.respositories.NoticeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nico
 */


@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Permite solicitudes desde el origen especificado
public class ApiController {

    @Autowired
    private NoticeRepository noticeRepository;

//    @GetMapping
//    public List<Notice> getAllNews() {
//        return noticeRepository.findAll();
//    }
//    
    @GetMapping
    public List<Notice> getAllNews() {
        List<Notice> notices = noticeRepository.findAll();

        for (Notice notice : notices) {
            if (notice.getFrontPage() != null) {
                String imageUrl = "http://localhost:8080/api/images/" + notice.getFrontPage().getId();
                notice.getFrontPage().setUrl(imageUrl);
            }
        }

        return notices;
    }
}
