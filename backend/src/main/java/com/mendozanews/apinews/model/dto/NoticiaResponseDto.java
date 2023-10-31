package com.mendozanews.apinews.model.dto;

import java.util.Date;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NoticiaResponseDto {
    private String titulo;
     private String id;
    private String idSeccion;

   
    private Date fechaPublicacion;

  

    public NoticiaResponseDto(String titulo, String idSeccion, String id, Date fechaPublicacion) {
        this.titulo = titulo;
        this.idSeccion = idSeccion;
       this.id = id;
        
        this.fechaPublicacion = fechaPublicacion;
    }

    public NoticiaResponseDto() {
    }

    public void setFechaPublicacion(Date fechaPublicacion) { // Cambia el nombre del método a camelCase
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaPublicacion() { // Cambia el nombre del método a camelCase
        return this.fechaPublicacion;
    }

}
