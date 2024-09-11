/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.camara.ucep.services;

import com.camara.ucep.entities.Image;
import com.camara.ucep.exceptions.MiException;
import com.camara.ucep.respositories.ImageRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nico
 */
@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Transactional
    public Image createImage(MultipartFile file) throws MiException {

        try {
            if (file == null || file.isEmpty()) { //(if archivo == null || archivo.isEmpty())

                Image image = new Image();

                // Cargar imagen predeterminada desde la carpeta resources/static/img/
                ClassPathResource defaultImageResource = new ClassPathResource("static/img/default.jpeg");
                byte[] defaultImageBytes = StreamUtils.copyToByteArray(defaultImageResource.getInputStream());

                image.setMime("image/jpeg");  // Establecer el tipo MIME de la imagen predeterminada
                image.setName("default.jpeg");  // Nombre de la imagen predeterminada
                image.setContent(defaultImageBytes);

                return imageRepository.save(image);

            } else if (file != null) {

                Image image = new Image();
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContent(file.getBytes());
                return imageRepository.save(image);
            }
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Image findById(String id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        return optionalImage.orElse(null); // Devuelve la imagen si existe, sino devuelve null
    }

    @Transactional(readOnly = true)
    public List<Image> getImagAll() {
        return imageRepository.findAll();
    }

    @Transactional
    public Image updateImage(MultipartFile file, String idImage) throws MiException {

        try {

            if (file == null || file.isEmpty()) {
                Image image = new Image();
                image = imageRepository.getReferenceById(idImage);
                return imageRepository.save(image);

            } else if (file != null) {

                Image image = new Image();
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContent(file.getBytes());
                return (Image) imageRepository.save(image);
            }
            return null;

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

}
