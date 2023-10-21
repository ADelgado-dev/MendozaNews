
package com.mendozanews.apinews.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Noticia {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", updatable = true, nullable = false)
    private String id;

    private String titulo;

    @Column(length = 1500)
    private String subtitulo;

    @ElementCollection
    @CollectionTable(name = "parrafos", joinColumns = @JoinColumn(name = "noticia_id"))
    @Column(name = "parrafo", length = 3000)
    private List<String> parrafos;

    @ElementCollection
    @CollectionTable(name = "etiquetas", joinColumns = @JoinColumn(name = "noticia_id"))
    @Column(name = "etiqueta", length = 30)
    private List<String> etiquetas;

    @ManyToOne
    private Seccion seccion;

    @ManyToOne
    private Autor autor;

    @Column(name = "fecha_publicacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPublicacion;

    @Column(name = "fecha_edicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;

    private Boolean activa;

    @OneToOne
    private Imagen portada;

    @OneToMany
    private List<Imagen> imagenes;

}
