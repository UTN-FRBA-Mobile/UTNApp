package Dominio.modelo;

public class Curso {

    public String materia;

    public String codigo;

    public String profesor;

    public String aula;

    public String sede;

    public String getIdentificador(){
        return materia + "_"+ codigo;
    }
}
