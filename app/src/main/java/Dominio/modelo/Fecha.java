package Dominio.modelo;


import java.util.Date;

public class Fecha {

    public Curso curso;

    public String descripcion;

    public Date fecha;

    public String getMateria() {

        return this.curso.materia;
    }
}
