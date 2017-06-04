package Dominio.repositorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Dominio.modelo.Mensaje;

public class RepositorioMensajes {

    public List<Mensaje> GetMensajesDeCurso(String cursoID){

        ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();

        Mensaje mensaje1 = new Mensaje();
        mensaje1.Autor = "Fernando Rossselló";
        mensaje1.Contenido = "Hola, esto es una prueba";
        mensaje1.FechaCreacion = new Date();

        Mensaje mensaje2 = new Mensaje();
        mensaje2.Autor = "Rodrigo Souto";
        mensaje2.Contenido = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam mollis bibendum sem at semper. In a dui iaculis, tempus libero vitae, feugiat nunc. Pellentesque dignissim erat vel imperdiet finibus. In eu tellus eget nisl elementum varius sit amet sit amet nisi. Sed vestibulum urna vel eleifend malesuada. Maecenas gravida, nisl rutrum sagittis ultricies, lacus nulla sagittis turpis, eu accumsan urna ipsum vitae purus. In pulvinar feugiat lectus quis dignissim. Donec at euismod ante. Interdum et malesuada fames ac ante ipsum primis in faucibus.";
        mensaje2.FechaCreacion = new Date();

        mensajes.add(mensaje1);
        mensajes.add(mensaje2);

        return mensajes;
    }


}
