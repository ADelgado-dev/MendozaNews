package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.servicios.NoticiaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/noticia")
@Validated
public class ControllerNoticias {
    private final NoticiaServicio noticiaServicio;

    public ControllerNoticias(NoticiaServicio noticiaServicio) {
        this.noticiaServicio = noticiaServicio;
    }

    @PostMapping(value = "/nueva", consumes = { "application/json" })
    public ResponseEntity<?> cargarNoticia(@RequestBody NoticiaRequest request) {
        try {
            noticiaServicio.cargarNoticia(request.getTitulo(), request.getSubtitulo(), request.getParrafos(),
                    request.getEtiquetas(), request.getIdSeccion(), request.getIdAutor(), null, null);
            return new ResponseEntity<>("Noticia cargada con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al cargar la noticia: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    static class NoticiaRequest {
        private String titulo;
        private String subtitulo;
        private String parrafos;
        private String etiquetas;
        private String idSeccion;
        private String idAutor;

        // Getters y setters

        public String getTitulo() {
            return titulo;
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
    }
}
