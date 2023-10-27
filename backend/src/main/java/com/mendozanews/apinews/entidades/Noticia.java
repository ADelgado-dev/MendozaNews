package com.mendozanews.apinews.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.GenericGenerator;

@Table(name = "noticia")
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
    @JoinColumn(name = "seccion_id", referencedColumnName = "id")
    private Seccion seccion;

    @ManyToOne
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    private Autor autor;

    @Column(name = "fecha_publicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;

    @Column(name = "fecha_edicion")
    @Temporal(TemporalType.DATE)
    private Date fechaEdicion;

    private Boolean activa;

    private String portadaId;
    @OneToOne
    @JoinColumn(name = "portada_id")
    private Imagen portada;

    @OneToMany
    private List<Imagen> imagenes;

    public String getPortadaId() {
        return portadaId;
    }

    public void setPortadaId(String portadaId) {
        this.portadaId = portadaId;
    }

}
