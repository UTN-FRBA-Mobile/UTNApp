package Dominio.modelo;

import java.util.Date;

public class Mensaje {

    static final int LARGOPREVIEW = 30;

    public String Autor;

    public String Contenido;

    public Date FechaCreacion;

    public Curso Curso;

    public String GetPreview(){

        String preview;

        if(this.Contenido.length() > LARGOPREVIEW){

            preview = this.Contenido.substring(0,LARGOPREVIEW)+"[...]";

        } else {
            preview = this.Contenido;
        }

        return preview;
    }


}
