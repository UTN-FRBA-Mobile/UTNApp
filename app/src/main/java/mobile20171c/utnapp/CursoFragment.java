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


/**
 * A simple {@link Fragment} subclass.
 */
public class CursoFragment extends Fragment implements MainActivity.getAppTitleListener {

    //private RecyclerView recyclerView;

    private int cursoId;

    public CursoFragment() {
        // Required empty public constructor
    }

    public static CursoFragment newInstance(int cursoId) {
        CursoFragment fragment = new CursoFragment();

        Bundle args = new Bundle();
        args.putInt("cursoId", cursoId);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabsCurso);;
        tabLayout.addTab(tabLayout.newTab().setText("Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Mensajes"));
        tabLayout.addTab(tabLayout.newTab().setText("Fechas"));

        TextView textView = (TextView) view.findViewById(R.id.cursoName);
        textView.setText("Curso "+String.valueOf(getArguments().get("cursoId")));
    }

    public String getAppTitle() {
        return "Materia: "+String.valueOf(getArguments().get("cursoId"));
    }

}
