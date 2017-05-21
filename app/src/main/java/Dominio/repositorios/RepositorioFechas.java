package Dominio.repositorios;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import Dominio.modelo.Curso;
import Dominio.modelo.Fecha;

public class RepositorioFechas {
    public ArrayList<Fecha> getAllPara(String emailUsuario) {

        final ArrayList<Curso> cursos = new RepositorioCursos().GetAll();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1988);
        cal.set(Calendar.MONTH, Calendar.MAY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        final Date fechaCal = cal.getTime();

        final Fecha fecha1 = new Fecha(){{
            this.curso = cursos.get(1);
            this.descripcion = "Primer parcial";
            this.fecha = fechaCal;
        }};

        cal.set(Calendar.MONTH, Calendar.JUNE);
        final Date fechaCal2 = cal.getTime();

        final Fecha fecha2 = new Fecha(){{
            this.curso = cursos.get(2);
            this.descripcion = "Entrega T.P.";
            this.fecha = fechaCal2;
        }};

        return new ArrayList<Fecha>(){{
            add(fecha1);
            add(fecha2);
        }};
    }
}
