/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.camara.ucep.entities;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;

/**
 *
 * @author Nico
 */
@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String mime;
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    @Transient
    private String url; // Agregado para incluir la URL de la imagen en la respuesta

    public Image() {
    }

    public Image(String id, String mime, String name, byte[] content) {
        this.id = id;
        this.mime = mime;
        this.name = name;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
