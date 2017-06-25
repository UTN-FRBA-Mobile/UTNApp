package mobile20171c.utnapp.Cursos;


import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mobile20171c.utnapp.Modelo.Curso;
import mobile20171c.utnapp.Modelo.Fecha;
import mobile20171c.utnapp.FechaFragment;
import mobile20171c.utnapp.R;
import mobile20171c.utnapp.Recycler.FechaRecyclerAdapter;

public class CursoFechaFragment extends Fragment {

    private static final String ARG_ID_CURSO = "idCurso";
    private String idCurso;
    private FechaFragment.OnListFragmentInteractionListener mListener;

    public static CursoFechaFragment newInstance(String idCurso) {
        CursoFechaFragment fragment = new CursoFechaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_CURSO, idCurso);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idCurso = getArguments().getString(ARG_ID_CURSO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fecha_list, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseDatabase.getInstance()
                .getReference("cursos")
                .child(idCurso)
                .addListenerForSingleValueEvent(new CursoFechaValueEventListener(view));

        /* recycler */
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerFechas);

        recyclerView.setAdapter(new FechaRecyclerAdapter(
                        FirebaseDatabase.getInstance().getReference().child("fechasCursos").child(idCurso)
                )
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private class CursoFechaValueEventListener implements ValueEventListener {

        View view;

        private CursoFechaValueEventListener(View view) {
            this.view = view;
        }

        @Override
        public void onDataChange(DataSnapshot snapshot) {
            if (snapshot.exists()) {
                Curso curso = snapshot.getValue(Curso.class);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user.getUid().equals(curso.adminUserId)) {
                    // solo el admin puede agregar fechas
                    FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
                    fab.setVisibility(View.VISIBLE);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogFragment newFragment = DatePickerFragment.newInstance(idCurso);
                            newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                        }
                    });
                }
            } else {
                Log.e("CursoValueEventListener", "Curso not found");
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e("CursoValueEventListener", databaseError.toString());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FechaFragment.OnListFragmentInteractionListener) {
            mListener = (FechaFragment.OnListFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Fecha item);
    }

}
