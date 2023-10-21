
package com.mendozanews.apinews.repositorios;

import com.mendozanews.apinews.entidades.Noticia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, String> {

    @Query("SELECT n FROM Noticia n WHERE n.titulo LIKE :titulo")
    public List<Noticia> buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT n FROM Noticia n WHERE n.autor.id = :id")
    public List<Noticia> buscarPorAutor(@Param("id") String id);

    @Query("SELECT n FROM Noticia n WHERE n.seccion.id = :id")
    public List<Noticia> buscarPorSeccion(@Param("id") String id);

    @Query("SELECT n FROM Noticia n WHERE n.seccion.id = :id")
    public List<Noticia> findTop6BySeccionId(@Param("id") String id);

    @Query("SELECT n FROM Noticia n WHERE n.seccion.id = :id")
    public Noticia findFirstBySeccionId(@Param("id") String id);

    @Query("SELECT n FROM Noticia n WHERE n.id IN ('1', '2', '3')")
    List<Noticia> listar3principales();
}
