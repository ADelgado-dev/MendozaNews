package com.mendozanews.apinews.controllers;

import org.springframework.web.multipart.MultipartFile;

import com.mendozanews.apinews.entidades.Imagen;
import com.mendozanews.apinews.entidades.Portada;
import com.mendozanews.apinews.repositorios.ImagenRepositorio;
import com.mendozanews.apinews.repositorios.PortadaRepositorio;
import com.mendozanews.apinews.servicios.NoticiaServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
public class ControllerNoticias {

    @Autowired
    private ImagenRepositorio imagenRepositorio;
    private final NoticiaServicio noticiaServicio;
    @Autowired
    private PortadaRepositorio portadaRepositorio;

    public ControllerNoticias(NoticiaServicio noticiaServicio) {
        this.noticiaServicio = noticiaServicio;
    }

    @PostMapping(value = "/imagenes")
    public ResponseEntity<?> cargarNoticia(@ModelAttribute NoticiaRequest request,
            @RequestParam("imagenes") MultipartFile[] imagenes, @RequestParam("portada") MultipartFile portada) {
        String idPortada = null;
        for (MultipartFile imagen : imagenes) {
            if (imagen.isEmpty()) {
                return new ResponseEntity<>("No se ha seleccionado ninguna imagen", HttpStatus.BAD_REQUEST);
            }
            try {
                noticiaServicio.cargarNoticia(request.getTitulo(), request.getSubtitulo(), request.getParrafos(),
                        request.getEtiquetas(), request.getIdSeccion(), request.getIdAutor(), imagenes,
                        request.getPortada());
                Imagen imagenGuardada = new Imagen();
                imagenGuardada.setNombre(imagen.getOriginalFilename());

                try {
                    imagenGuardada.setContenido(imagen.getBytes());
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                imagenGuardada.setMime(imagen.getContentType());
                imagenRepositorio.save(imagenGuardada);

                Imagen portadaGuardada = new Imagen();
                portadaGuardada.setNombre(request.getPortada().getOriginalFilename());
                try {
                    portadaGuardada.setContenido(request.getPortada().getBytes());
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                portadaGuardada.setMime(request.getPortada().getContentType());
                imagenRepositorio.save(portadaGuardada);

                idPortada = portadaGuardada.getId();
                Portada portadaEntity = new Portada();
                portadaEntity.setPortada(idPortada);
                portadaRepositorio.save(portadaEntity);

            } catch (Exception e) {
                return new ResponseEntity<>("Error al cargar la noticia: " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("Noticia cargada con éxito. ID de portada: " + idPortada, HttpStatus.OK);
    }

}

class NoticiaRequest {
    private String titulo;
    private String subtitulo;
    private String parrafos;
    private String etiquetas;
    private String idSeccion;
    private String idAutor;
    private MultipartFile portada;

    public String getTitulo() {
        return titulo;
    }

    public void setPortada(MultipartFile portada) {
        this.portada = portada;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getParrafos() {
        return parrafos;
    }

    public void setParrafos(String parrafos) {
        this.parrafos = parrafos;
    }

    public String getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }

    public String getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(String idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(String idAutor) {
        this.idAutor = idAutor;
    }

    public MultipartFile getPortada() {
        return portada;
    }
}
