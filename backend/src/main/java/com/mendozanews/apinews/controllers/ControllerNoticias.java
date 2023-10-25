package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.servicios.NoticiaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/noticia")
public class ControllerNoticias {

    private final NoticiaServicio noticiaServicio;

    public ControllerNoticias(NoticiaServicio noticiaServicio) {
        this.noticiaServicio = noticiaServicio;
    }

    @PostMapping(value = "/nueva", consumes = "application/json")
    public ResponseEntity<?> cargarNoticia(@RequestBody NoticiaRequest noticiaRequest) {
        try {
            noticiaServicio.cargarNoticia(noticiaRequest.getTitulo(), noticiaRequest.getSubtitulo(),
                    noticiaRequest.getParrafos(), noticiaRequest.getEtiquetas(), noticiaRequest.getIdSeccion(),
                    noticiaRequest.getIdAutor(), noticiaRequest.getPortada(), noticiaRequest.getImagenes());
            return new ResponseEntity<>("Noticia cargada con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al cargar la noticia: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public class NoticiaRequest {
        private String titulo;
        private String subtitulo;
        private String idSeccion;
        private String idAutor;
        private MultipartFile portada;
        private List<MultipartFile> imagenes;
        private List<String> parrafos;
        private List<String> etiquetas;

        public NoticiaRequest() {
            // Constructor vacío necesario para la deserialización
        }

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

        public void setPortada(MultipartFile portada) {
            this.portada = portada;
        }

        public List<MultipartFile> getImagenes() {
            return imagenes;
        }

        public void setImagenes(List<MultipartFile> imagenes) {
            this.imagenes = imagenes;
        }

        public List<String> getParrafos() {
            return parrafos;
        }

        public void setParrafos(List<String> parrafos) {
            this.parrafos = parrafos;
        }

        public List<String> getEtiquetas() {
            return etiquetas;
        }

        public void setEtiquetas(List<String> etiquetas) {
            this.etiquetas = etiquetas;
        }
    }

}
