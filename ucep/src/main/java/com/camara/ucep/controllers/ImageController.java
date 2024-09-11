/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.camara.ucep.controllers;

import com.camara.ucep.entities.Image;
import com.camara.ucep.services.ImageService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nico
 */
@Controller
public class ImageController {

    @Autowired
    ImageService imageService;
    
@GetMapping("/images/{id}")
public ResponseEntity<byte[]> getImageById(@PathVariable String id) {
    Image image = imageService.findById(id); // Método en el servicio para encontrar la imagen por id
    if (image == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok()
            .contentType(MediaType.valueOf(image.getMime()))
            .body(image.getContent());
}
}
