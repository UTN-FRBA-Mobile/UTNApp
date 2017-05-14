package Dominio.repositorios;

import java.util.ArrayList;
import java.util.List;

import Dominio.modelo.Curso;

public class RepositorioCursos {

    public ArrayList<Curso> GetAll(){

        final Curso curso1 = new Curso()
        {{  aula = "418";
            codigo ="K5051";
            materia = "Sistemas de Gestion";
            profesor = "José Tana";
            sede = "Medrano";
        }};

        final Curso curso2 = new Curso()
        {{  aula = "512";
            codigo ="K5251";
            materia = "Proyecto Final";
            profesor = "Inés Casanovas";
            sede = "Medrano";
        }};

        final Curso curso3 = new Curso()
        {{      aula = "417";
                codigo ="";
                materia = "Fisica II";
                profesor = "Andrés Zuccalá";
                sede = "Campus";
        }};

        final Curso curso4 = new Curso()
    {{          aula = "504";
                codigo ="K5250";
                materia = "Sistemas Operativos";
                profesor = "Adriano Filgueiras";
                sede = "Campus";
        }};

        final Curso curso5 = new Curso()
        {{      aula = "319";
                codigo ="K5051";
                materia = "Analisis Matematico II";
                profesor = "Edith Amed";
                sede = "Campus";
        }};

        ArrayList<Curso> cursos = new ArrayList<Curso>()
        {{  add(curso1);
            add(curso2);
            add(curso3);
            add(curso4);
            add(curso5);
        }};

        return cursos;
    }
}
