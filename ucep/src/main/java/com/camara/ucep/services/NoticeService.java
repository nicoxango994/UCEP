/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.camara.ucep.services;

/**
 *
 * @author Nico
 */
import com.camara.ucep.entities.Image;
import com.camara.ucep.entities.Notice;
import com.camara.ucep.exceptions.MiException;
import com.camara.ucep.respositories.NoticeRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private ImageService imageService;

    @Transactional
    public void createNotice(String title, MultipartFile frontPage, String Content) throws MiException {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setUploadDate(LocalDate.now());  // Establece la fecha actual
        notice.setContent(Content);

        if (frontPage != null && !frontPage.isEmpty()) {
            Image image = imageService.createImage(frontPage);
            notice.setFrontPage(image);
        }

        noticeRepository.save(notice);
    }
    
    @Transactional(readOnly = true)
    public List<Notice> getAllNotices() {
        return noticeRepository.findAllOrderedByUploadDateDesc();
    }

    @Transactional(readOnly = true)
    public Notice getNoticeById(String id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        return notice.orElse(null);  // Manejo básico de null, puedes ajustar según necesites
    }
    
}