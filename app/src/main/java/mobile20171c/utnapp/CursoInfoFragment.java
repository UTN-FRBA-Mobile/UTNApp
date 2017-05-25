package mobile20171c.utnapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Dominio.modelo.Curso;
import Dominio.repositorios.RepositorioCursos;

public class CursoInfoFragment extends Fragment {

    private static final String ARG_PARAM1 = "cursoId";

    private Curso cursoActual;

    private OnFragmentInteractionListener mListener;

    public CursoInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CursoInfoFragment newInstance(String idCurso) {
        CursoInfoFragment fragment = new CursoInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, idCurso);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cursoActual = new RepositorioCursos().GetById(getArguments().getString(ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curso_info, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ((TextView) view.findViewById(R.id.cursoNameTxt)).setText(cursoActual.materia);
        ((TextView) view.findViewById(R.id.cursoCodigoTxt)).setText(cursoActual.codigo);
        ((TextView) view.findViewById(R.id.cursoProfesorTxt)).setText(cursoActual.profesor);
        ((TextView) view.findViewById(R.id.cursoAulaTxt)).setText(cursoActual.aula);
        ((TextView) view.findViewById(R.id.cursoSedeTxt)).setText(cursoActual.sede);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
