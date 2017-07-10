package mobile20171c.utnapp.Cursos;

import android.app.SearchableInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import mobile20171c.utnapp.R;
import mobile20171c.utnapp.Recycler.CursosRecyclerAdapter;


public class CursosFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final String ARG_ID_CURSO = "CursoId";

    private String cursoId;

    private RecyclerView recyclerCursos;

    private CursosRecyclerAdapter misCursosRecycler;
    private CursosRecyclerAdapter todosLosCursosRecycler;

    private boolean misCursosTabActive;

    public CursosFragment() {
        // Required empty public constructor
    }

    public static CursosFragment newInstance() {
        return new CursosFragment();
    }

    public static CursosFragment newInstanceMsg(String cursoId) {
        CursosFragment fragment = new CursosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_CURSO, cursoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            cursoId = getArguments().getString(ARG_ID_CURSO);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cursos, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setVisible(true);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, NuevoCursoFragment.newInstance(), "Fragment")
                        .addToBackStack("Nuevo Curso")
                        .commit();
            }
        });


        final RecyclerView recyclerView = recyclerCursos = (RecyclerView) view.findViewById(R.id.recyclerViewCursos);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        misCursosRecycler = new CursosRecyclerAdapter(
                getContext(),
                FirebaseDatabase.getInstance().getReference().child("usuarios").child(user.getUid()).child("cursos")
        );

        todosLosCursosRecycler = new CursosRecyclerAdapter(
                getContext(),
                FirebaseDatabase.getInstance().getReference().child("cursos")
        );

        misCursosTabActive = true;

        recyclerView.setAdapter(misCursosRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabsCursos);;
        tabs.addTab(tabs.newTab().setText("Mis Cursos"));
        tabs.addTab(tabs.newTab().setText("Todos los cursos"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        // mis cursos
                        recyclerView.setAdapter(misCursosRecycler);
                        misCursosTabActive = true;
                        break;
                    case 1:
                        // todos los cursos
                        recyclerView.setAdapter(todosLosCursosRecycler);
                        misCursosTabActive = false;
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

        if (cursoId != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, CursoFragment.newInstanceMsg(cursoId), "Fragment")
                    .addToBackStack("Curso")
                    .commit();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        CursosRecyclerAdapter adapter;

        if (misCursosTabActive) {
            Query consulta = FirebaseDatabase.getInstance().getReference().child("usuarios").child(user.getUid()).child("cursos")
                    .orderByChild("materia").startAt(query).endAt(query+"\uf8ff");

            adapter = new CursosRecyclerAdapter(
                    getContext(),
                    consulta
            );
        } else {
            Query consulta = FirebaseDatabase.getInstance().getReference().child("cursos")
                    .orderByChild("materia").startAt(query).endAt(query+"\uf8ff");
            adapter = new CursosRecyclerAdapter(
                    getContext(),
                    consulta
            );
        }

        recyclerCursos.setAdapter(adapter);


        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText.equals("")) {
            recyclerCursos.setAdapter(misCursosTabActive ? misCursosRecycler : todosLosCursosRecycler);

            return false;
        }

        return false;
    }
}
