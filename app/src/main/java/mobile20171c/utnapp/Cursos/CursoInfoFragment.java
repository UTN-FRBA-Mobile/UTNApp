package mobile20171c.utnapp.Cursos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mobile20171c.utnapp.Modelo.Curso;
import mobile20171c.utnapp.R;

public class CursoInfoFragment extends Fragment {

    private static final String ARG_ID_CURSO = "cursoId";

    private OnFragmentInteractionListener mListener;

    public CursoInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CursoInfoFragment newInstance(String idCurso) {
        CursoInfoFragment fragment = new CursoInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_CURSO, idCurso);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        FirebaseDatabase.getInstance()
                .getReference("cursos")
                .child(getArguments().getString(ARG_ID_CURSO))
                .addListenerForSingleValueEvent(new CursoValueEventListener(view));

        final Button btnCurso = (Button)view.findViewById(R.id.cursoEstadoBtn);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance()
                .getReference("usuarios")
                .child(user.getUid())
                .child("cursos")
                .child(getArguments().getString(ARG_ID_CURSO))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            btnCurso.setText("Abandonar este curso");

                            btnCurso.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    FirebaseDatabase.getInstance()
                                            .getReference("usuarios")
                                            .child(user.getUid())
                                            .child("cursos")
                                            .child(getArguments().getString(ARG_ID_CURSO))
                                            .removeValue();
                                }
                            });
                        } else {
                            btnCurso.setText("Anotarme a este curso");

                            btnCurso.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    FirebaseDatabase.getInstance()
                                            .getReference("cursos")
                                            .child(getArguments().getString(ARG_ID_CURSO))
                                            .addListenerForSingleValueEvent(new ValueEventListener() {

                                                @Override
                                                public void onDataChange(DataSnapshot snapshot) {
                                                    if (snapshot.exists()) {
                                                        FirebaseDatabase.getInstance()
                                                                .getReference("usuarios")
                                                                .child(user.getUid())
                                                                .child("cursos")
                                                                .child(getArguments().getString(ARG_ID_CURSO))
                                                                .setValue(snapshot.getValue(Curso.class));
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                }

                                            });
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("CursoValueEventListener", databaseError.toString());
                    }
                });
    }

    private class CursoValueEventListener implements ValueEventListener {

        View view;

        public CursoValueEventListener(View view) {
            this.view = view;
        }

        @Override
        public void onDataChange(DataSnapshot snapshot) {
            if (snapshot.exists()) {

                Curso cursoActual = snapshot.getValue(Curso.class);

                ((TextView) view.findViewById(R.id.cursoNameTxt)).setText(cursoActual.materia);
                ((TextView) view.findViewById(R.id.cursoCodigoTxt)).setText(cursoActual.codigo);
                ((TextView) view.findViewById(R.id.cursoProfesorTxt)).setText(cursoActual.profesor);
                ((TextView) view.findViewById(R.id.cursoAulaTxt)).setText(cursoActual.aula);
                ((TextView) view.findViewById(R.id.cursoSedeTxt)).setText(cursoActual.sede);
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
