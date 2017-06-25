package mobile20171c.utnapp.Cursos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import mobile20171c.utnapp.MainActivity;
import mobile20171c.utnapp.R;

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
        tabLayout.addTab(tabLayout.newTab().setText(R.string.cursos_informacion));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.cursos_mesajes));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.cursos_fechas));

        try {
            TabLayout tabs= (TabLayout)view.findViewById(R.id.tabsCurso);
            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    FragmentTransaction transaction;
                    switch (tab.getPosition()){
                        case 0:
                            transaction = getChildFragmentManager().beginTransaction();
                            transaction.replace(R.id.child_fragment_container, CursoInfoFragment.newInstance(cursoId)).commit();
                            break;
                        case 1:
                            transaction = getChildFragmentManager().beginTransaction();
                            transaction.replace(R.id.child_fragment_container, CursoMensajeFragment.newInstance(cursoId)).commit();
                            break;
                        case 2:
                            transaction = getChildFragmentManager().beginTransaction();
                            transaction.replace(R.id.child_fragment_container, CursoFechaFragment.newInstance(cursoId)).commit();
                            break;

                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            Fragment childFragment = CursoInfoFragment.newInstance(this.cursoId);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.child_fragment_container, childFragment).commit();
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
