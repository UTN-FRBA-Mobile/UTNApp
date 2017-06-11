package mobile20171c.utnapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Dominio.modelo.Mensaje;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CursoMensajeFragment extends Fragment {

    private static final String ARG_ID_CURSO = "CursoId";
    private String midCurso;
    private OnListFragmentInteractionListener mListener;

    public static CursoMensajeFragment newInstance(String idCurso) {
        CursoMensajeFragment fragment = new CursoMensajeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_CURSO, idCurso);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        midCurso = getArguments().getString(ARG_ID_CURSO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mensaje_list, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* recycler */
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMensajes);

        recyclerView.setAdapter(new MensajesRecyclerAdapter(
                        FirebaseDatabase.getInstance().getReference().child("mensajes").child(midCurso)
                )
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /* button */
        Button button = (Button) view.findViewById(R.id.buttonNuevoMensaje);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String contenido = ((EditText) v.getRootView().findViewById(R.id.nuevoMensaje)).getText().toString();

                if (contenido.equals("")) {
                    Toast.makeText(v.getContext(), "Completa el mensaje.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // se lo asignamos al usuario actual
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                // creamos el mensaje
                Mensaje mensaje = new Mensaje();
                mensaje.autorId = user.getUid();
                mensaje.autor = user.getEmail(); // TODO: por que user.getDisplayName() no  devuelve nada?
                mensaje.contenido = contenido;
                mensaje.curso = midCurso;

                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

                String key = dbRef.child("mensajes").child(midCurso).push().getKey();

                mensaje.id = key;

                dbRef.child("mensajes").child(midCurso).child(key).setValue(mensaje);

                Toast.makeText(v.getContext(), "Mensaje enviado.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Mensaje mensaje);
    }
}
