package mobile20171c.utnapp;


import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import Dominio.modelo.Fecha;
import Dominio.repositorios.RepositorioFechas;

public class CursoFechaFragment extends Fragment {

    private static final String ARG_ID_CURSO = "idCurso";
    private String idCurso;
    private FechaFragment.OnListFragmentInteractionListener mListener;

    public CursoFechaFragment() {
    }

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

        if (getArguments() != null) {
            idCurso = getArguments().getString(ARG_ID_CURSO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fecha_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

            ArrayList<Fecha> fechasImportantes = new RepositorioFechas().GetFechasDeCurso(this.idCurso);
            recyclerView.setAdapter(new MyFechaRecyclerViewAdapter(fechasImportantes, mListener));
        }
        return view;
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
