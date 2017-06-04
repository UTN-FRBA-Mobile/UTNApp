package Dominio.modelo;

import java.util.Date;

public class Mensaje {

    static final int LARGOPREVIEW = 30;

    public String Autor;

    public String Contenido;

    public Date FechaCreacion;

    public Curso Curso;

    public Mensaje(){
        FechaCreacion = new Date();
    }


}
