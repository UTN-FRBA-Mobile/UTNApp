package mobile20171c.utnapp.Modelo;

import java.util.Date;

public class Mensaje {

    public String id;

    public String autorId;

    public String autor;

    public String contenido;

    public Date fechaCreacion;

    public String curso;

    public Mensaje(){
        fechaCreacion = new Date();
    }

}
