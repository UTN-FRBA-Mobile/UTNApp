package mobile20171c.utnapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import Dominio.modelo.Curso;
import Dominio.repositorios.RepositorioCursos;

public class CursoFragment extends Fragment implements MainActivity.getAppTitleListener {

    //private RecyclerView recyclerView;

    private String cursoId;

    public CursoFragment() {
        // Required empty public constructor
    }

    public static CursoFragment newInstance(String cursoId) {

        CursoFragment fragment = new CursoFragment();

        Bundle args = new Bundle();
        args.putString("cursoId", cursoId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curso, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cursoId = getArguments().getString("cursoId");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabsCurso);;
        tabLayout.addTab(tabLayout.newTab().setText("Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Mensajes"));
        tabLayout.addTab(tabLayout.newTab().setText("Fechas"));

        try {
            Curso cursoActual = new RepositorioCursos().GetById(this.cursoId);

            ((TextView) view.findViewById(R.id.cursoNameTxt)).setText(cursoActual.materia);
            ((TextView) view.findViewById(R.id.cursoCodigoTxt)).setText(cursoActual.codigo);
            ((TextView) view.findViewById(R.id.cursoProfesorTxt)).setText(cursoActual.profesor);
            ((TextView) view.findViewById(R.id.cursoAulaTxt)).setText(cursoActual.aula);
            ((TextView) view.findViewById(R.id.cursoSedeTxt)).setText(cursoActual.sede);

        }
        catch (RuntimeException ex){
            Toast.makeText(this.getContext(),ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public String getAppTitle() {
        return "Materia: "+String.valueOf(getArguments().get("cursoId"));
    }

}
