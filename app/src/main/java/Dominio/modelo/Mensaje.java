package Dominio.modelo;

import java.util.Date;

public class Mensaje {

    public String id;

    public String autorId;

    public String autor;

    public String contenido;

    public Date fechaCreacion;

    public int tipoMensaje;

    public String curso;

    public Mensaje(){
        fechaCreacion = new Date();
    }

}
