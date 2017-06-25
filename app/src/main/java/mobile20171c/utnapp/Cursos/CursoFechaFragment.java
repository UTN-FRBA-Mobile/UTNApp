package mobile20171c.utnapp.Cursos;


import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.FirebaseDatabase;

import mobile20171c.utnapp.Modelo.Fecha;
import mobile20171c.utnapp.DatePickerFragment;
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

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = DatePickerFragment.newInstance(idCurso);
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        /* recycler */
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerFechas);

        recyclerView.setAdapter(new FechaRecyclerAdapter(
                        FirebaseDatabase.getInstance().getReference().child("fechasCursos").child(idCurso)
                )
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
