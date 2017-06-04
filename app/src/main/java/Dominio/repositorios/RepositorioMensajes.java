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
        mensaje1.Contenido = "Proin quis rhoncus felis. Duis at mauris erat. Aenean mauris dolor, tempus in nisi in, blandit varius nunc. Nam blandit sapien enim, et efficitur dui pellentesque sed. Integer in nulla urna. Vivamus porttitor varius eleifend. Maecenas mollis fringilla purus nec lobortis. Sed justo augue, lacinia in erat quis, pulvinar tincidunt sapien.";

        Mensaje mensaje2 = new Mensaje();
        mensaje2.Autor = "Rodrigo Souto";
        mensaje2.Contenido = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam mollis bibendum sem at semper. In a dui iaculis, tempus libero vitae, feugiat nunc. Pellentesque dignissim erat vel imperdiet finibus. In eu tellus eget nisl elementum varius sit amet sit amet nisi. Sed vestibulum urna vel eleifend malesuada. Maecenas gravida, nisl rutrum sagittis ultricies, lacus nulla sagittis turpis, eu accumsan urna ipsum vitae purus. In pulvinar feugiat lectus quis dignissim. Donec at euismod ante. Interdum et malesuada fames ac ante ipsum primis in faucibus.";

        Mensaje mensaje3 = new Mensaje();
        mensaje3.Autor = "Fernando Rossselló";
        mensaje3.Contenido = "Sed viverra et nulla aliquam venenatis. Nunc vel odio in diam consectetur posuere vitae eu velit. Phasellus iaculis orci non ipsum aliquam vehicula ornare in est. Vivamus massa nulla, tincidunt vitae eros id, placerat fermentum diam. Quisque eleifend rhoncus nibh vitae suscipit. Suspendisse tristique ornare tortor fringilla maximus. Aliquam magna elit, varius at eros eu, placerat tempus velit. Donec egestas sagittis ligula, ac sollicitudin sapien eleifend ac. Nam auctor laoreet scelerisque. Curabitur dapibus posuere lectus, nec ultrices orci. Vestibulum erat felis, facilisis quis arcu eu, congue rutrum lorem. Mauris dignissim ipsum id nunc mattis feugiat vel et libero. Duis metus ante, dapibus in semper a, pretium sit amet tellus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Cras ac suscipit ante. Praesent ac accumsan nibh, nec vulputate nulla.";

        mensajes.add(mensaje1);
        mensajes.add(mensaje2);
        mensajes.add(mensaje3);

        return mensajes;
    }


}
