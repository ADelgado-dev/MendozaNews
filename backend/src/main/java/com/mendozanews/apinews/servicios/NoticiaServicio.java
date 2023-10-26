package com.mendozanews.apinews.servicios;

import com.mendozanews.apinews.entidades.Autor;
import com.mendozanews.apinews.entidades.Imagen;
import com.mendozanews.apinews.entidades.Noticia;
import com.mendozanews.apinews.entidades.Seccion;
import com.mendozanews.apinews.excepciones.MiException;
import com.mendozanews.apinews.repositorios.AutorRepositorio;
import com.mendozanews.apinews.repositorios.NoticiaRepositorio;
import com.mendozanews.apinews.repositorios.SeccionRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticiaServicio {
    @Autowired
    private NoticiaRepositorio noticiaRepository;

    @Autowired
    private AutorRepositorio autorRepository;

    @Autowired
    private SeccionRepositorio seccionRepository;

    @Autowired
    private NoticiaRepositorio nr;
    @Autowired
    private AutorRepositorio ar;
    @Autowired
    private SeccionRepositorio sr;
    @Autowired
    private ImagenServicio is;

    // CARGA UNA NOTICIA ENTERA
    @Transactional
    public void cargarNoticia(String titulo, String subtitulo, List<String> parrafos,
            List<String> etiquetas, String idSeccion, String idAutor,
            MultipartFile portada, List<MultipartFile> imagenes, MultipartFile file) throws MiException {

        validar(titulo, subtitulo, idSeccion, idAutor);

        Noticia noticia = new Noticia();
        Autor autor = ar.findById(idAutor).get();
        Seccion seccion = sr.findById(idSeccion).get();

        noticia.setTitulo(titulo);
        noticia.setSubtitulo(subtitulo);
        noticia.setParrafos(parrafos);
        noticia.setEtiquetas(etiquetas);
        noticia.setSeccion(seccion);
        noticia.setAutor(autor);
        noticia.setFechaPublicacion(new Date());
        noticia.setActiva(true);

        Imagen img = is.guardar(portada);
        noticia.setPortada(img);

        List<Imagen> imgs = is.guardarLista(imagenes);
        noticia.setImagenes(imgs);

        nr.save(noticia);
    }

    // EDITA UNA NOTICIA SIN CAMBIARLE SUS IMAGENES (SOLO PORTADA)
    @Transactional
    public void modificarNoticia(String titulo, String subtitulo, List<String> parrafos,
            List<String> etiquetas, String idSeccion, String idAutor,
            MultipartFile portada, String idNoticia) throws MiException {

        validar(titulo, subtitulo, idSeccion, idAutor);

        Optional<Noticia> respuesta = nr.findById(idNoticia);
        Optional<Autor> respuestaAutor = ar.findById(idAutor);
        Optional<Seccion> respuestaSeccion = sr.findById(idSeccion);

        Autor autor = new Autor();
        Seccion seccion = new Seccion();

        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }

        if (respuestaSeccion.isPresent()) {
            seccion = respuestaSeccion.get();
        }

        if (respuesta.isPresent()) {

            Noticia noticia = respuesta.get();

            validarTituloNuevo(noticia.getTitulo(), titulo);

            noticia.setTitulo(titulo);
            noticia.setSubtitulo(subtitulo);
            noticia.setParrafos(parrafos);
            noticia.setEtiquetas(etiquetas);
            noticia.setSeccion(seccion);
            noticia.setAutor(autor);
            noticia.setFechaEdicion(new Date());

            String idImg = null;
            if (noticia.getPortada() != null) {
                idImg = noticia.getPortada().getId();
            }
            Imagen img = is.actualizar(portada, idImg);
            noticia.setPortada(img);

            nr.save(noticia);

        }
    }

    // LISTA TODAS LAS NOTICIAS
    public List<Noticia> listarNoticias() {
        List<Noticia> noticias = nr.findAll();
        return noticias;
    }

    // BUSCA UNA o MAS NOTICIAS QUE CONTENGAN EL STRING QUE SE LE PASA
    public List<Noticia> buscarPorTitulo(String titulo) {
        List<Noticia> noticias = nr.buscarPorTitulo(titulo);
        return noticias;
    }

    // BUSCA NOTICIAS POR AUTOR
    public List<Noticia> buscarPorAutor(String idAutor) {
        List<Noticia> noticias = nr.buscarPorAutor(idAutor);
        return noticias;
    }

    // BUSCA NOTICIAS POR SECCION
    public List<Noticia> buscarPorSeccion(String idSeccion) {
        List<Noticia> noticias = nr.buscarPorSeccion(idSeccion);
        return noticias;
    }

    // BUSCA 6 NOTICIAS POR SECCION
    public List<Noticia> buscar6PorSeccion(String idSeccion) {
        List<Noticia> noticias = nr.findTop6BySeccionId(idSeccion);
        return noticias;
    }

    // BUSCA 1 NOTICIA DE CADA SECCION
    public List<Noticia> unaPorSeccion() {
        List<Seccion> secciones = (List<Seccion>) sr.findAll();
        List<Noticia> noticias = new ArrayList<>();

        for (Seccion seccion : secciones) {
            Noticia noticia = nr.findFirstBySeccionId(seccion.getId());
            if (noticia != null) {
                noticias.add(noticia);
            }
        }
        return noticias;
    }

    // OBTIENE 1 NOTICIA POR ID
    public Noticia getOne(String id) {
        return nr.getReferenceById(id);
    }

    // ELIMINA NOTICIA POR ID
    @Transactional
    public void eliminarNoticiaId(String id) throws MiException {
        Optional<Noticia> respuesta = nr.findById(id);
        if (respuesta.isPresent()) {
            nr.deleteById(id);
        } else {
            throw new MiException("No se encontro la noticia");
        }
    }

    // COPIA UNA NOTICIA Y LE ASIGNA EL ID NUEVO
    @Transactional
    public void asignarID(String id, String idNuevo) throws MiException {
        Noticia principal = getOne(id);
        principal.setId(idNuevo);
        nr.save(principal);
    }

    // LISTA LAS 3 NOTICIAS PRINCIPALES
    public List<Noticia> listarPrincipales() throws MiException {
        try {
            List<Noticia> noticias = nr.listar3principales();
            return noticias;
        } catch (Exception e) {
            throw new MiException("Error al listar las 3 noticias principales");
        }
    }

    // VALIDA LOS ATRIBUTOS STRINGS DE UNA NOTICIA
    private void validar(String titulo, String subtitulo, String idSeccion, String idAutor) throws MiException {
        if (titulo == null || titulo.isEmpty()) {
            throw new MiException("El título no puede ser nulo o estar vacío");
        }
        if (subtitulo == null || subtitulo.isEmpty() || subtitulo.length() > 220) {
            throw new MiException("El subtítulo no puede ser nulo o estar vacío");
        }
        if (idAutor == null || idAutor.isEmpty()) {
            throw new MiException("Debe indicar un autor");
        }
        if (idSeccion == null || idSeccion.isEmpty()) {
            throw new MiException("Debe indicar la sección");
        }
    }

    // VALIDA QUE EL TITULO NO EXISTA YA
    private void validarTituloNuevo(String titulo, String tituloNuevo) throws MiException {
        if (!titulo.equals(tituloNuevo) && nr.buscarPorTitulo(tituloNuevo) != null) {
            throw new MiException("Ya existe una noticia con ese título");
        }
    }

    // PRECARGA NOTICIAS SI LA BASE DE DATOS ESTA VACIA
    @Transactional
    public String iniciarPreloads(Integer cantidad) throws MiException {

        if (nr.findAll().size() < cantidad) {
            for (int i = 1; i <= cantidad; i++) {
                Noticia noticia = new Noticia();

                Autor autor = new Autor();
                autor.setNombre("Autor");
                autor.setApellido(Integer.toString(i));
                autor = ar.save(autor);

                Seccion seccion = new Seccion();
                seccion.setId("seccion_" + i);
                seccion.setNombre("Sección " + i);
                seccion = sr.save(seccion);

                noticia.setTitulo("titulo " + i);
                noticia.setSubtitulo("subtitulo " + i);

                List<String> parrafos = new ArrayList<>();
                parrafos.add("parrafo 1 de noticia " + i);
                parrafos.add("parrafo 2 de noticia " + i);
                noticia.setParrafos(parrafos);

                List<String> etiquetas = new ArrayList<>();
                parrafos.add("etiquetas 1 N" + i);
                parrafos.add("etiquetas 2 N" + i);
                noticia.setEtiquetas(etiquetas);

                noticia.setSeccion(seccion);
                noticia.setAutor(autor);
                noticia.setFechaPublicacion(new Date());
                noticia.setActiva(true);

                Imagen img = null;
                noticia.setPortada(img);

                List<Imagen> imgs = null;
                noticia.setImagenes(imgs);

                nr.save(noticia);
            }
            return "Se cargo la lista porque ingresaste un numero de noticias mayor al existente en la base de datos";
        } else {
            return "Error al cargar: Ya hay mas noticias guardadas de las que deseas";
        }

    }

    @Transactional
    public void cargarNoticia(String titulo, String subtitulo, String parrafos, String etiquetas, String idSeccion,
            String idAutor, Object object, Object object2) throws MiException {

        validar(titulo, subtitulo, idSeccion, idAutor);

        Noticia noticia = new Noticia();
        Autor autor = autorRepository.findById(idAutor).orElseThrow(() -> new MiException("Autor no encontrado"));
        Seccion seccion = seccionRepository.findById(idSeccion)
                .orElseThrow(() -> new MiException("Sección no encontrada"));

        noticia.setTitulo(titulo);
        noticia.setSubtitulo(subtitulo);
        // Convertir parrafos y etiquetas en listas si es necesario
        List<String> parrafosList = new ArrayList<>();
        parrafosList.add(parrafos);
        List<String> etiquetasList = new ArrayList<>();
        etiquetasList.add(etiquetas);
        noticia.setParrafos(parrafosList);
        noticia.setEtiquetas(etiquetasList);
        noticia.setSeccion(seccion);
        noticia.setAutor(autor);
        noticia.setFechaPublicacion(new Date());
        noticia.setActiva(true);

        noticiaRepository.save(noticia);
    }

}
