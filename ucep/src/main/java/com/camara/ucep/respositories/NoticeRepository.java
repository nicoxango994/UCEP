/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.camara.ucep.respositories;

/**
 *
 * @author Nico
 */
import com.camara.ucep.entities.Notice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, String> {
    
     @Query("SELECT n FROM Notice n ORDER BY n.uploadDate DESC")
     List<Notice> findAllOrderedByUploadDateDesc();

}
