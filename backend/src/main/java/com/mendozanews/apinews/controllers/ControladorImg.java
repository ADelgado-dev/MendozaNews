package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.repositorios.ImagenRepositorio;

import io.jsonwebtoken.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mendozanews.apinews.entidades.Imagen;

@RestController
@RequestMapping("/api")
public class ControladorImg {
    @Autowired
    private ImagenRepositorio ImagenRepositorio;

    @PostMapping("/imagen")
    public ResponseEntity<String> guardarImagenPortada(@RequestParam("imagen") MultipartFile imagen,
            @RequestParam("portada") MultipartFile portada) {
        try {
            // Guardar imagen
            Imagen imagenGuardada = new Imagen();
            imagenGuardada.setNombre(imagen.getOriginalFilename());
            try {
                imagenGuardada.setContenido(imagen.getBytes());
            } catch (java.io.IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            imagenGuardada.setMime(imagen.getContentType());
            ImagenRepositorio.save(imagenGuardada);

            // Guardar portada
            // Imagen portadaGuardada = new ImagenPortada();
            // portadaGuardada.setNombre(portada.getOriginalFilename());
            // portadaGuardada.setContenido(portada.getBytes());
            // portadaGuardada.setMime(portada.getContentType());
            // imagenPortadaRepository.save(portadaGuardada);

            return ResponseEntity.ok("Imagen y portada guardadas correctamente.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar imagen y portada.");
        }
    }
}