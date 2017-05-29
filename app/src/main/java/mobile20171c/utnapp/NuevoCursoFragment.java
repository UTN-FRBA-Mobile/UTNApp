package mobile20171c.utnapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class NuevoCursoFragment extends Fragment {

    public NuevoCursoFragment() {
        // Required empty public constructor
    }

    public static NuevoCursoFragment newInstance() {
        return new NuevoCursoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nuevo_curso, container, false);

        Button button = (Button) view.findViewById(R.id.buttonCrearCurso);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Spinner spinnerMateria = (Spinner) v.getRootView().findViewById(R.id.nuevoCursoMateria);

                Object selectedMateria = spinnerMateria.getSelectedItem();

                if (selectedMateria == null) {
                    Toast.makeText(v.getContext(), "Selecciona la materia", Toast.LENGTH_SHORT).show();
                    return;
                }

                String materia = selectedMateria.toString();

                String codigo = ((EditText) v.getRootView().findViewById(R.id.nuevoCursoCodigo)).getText().toString();

                if (codigo.equals("")) {
                    Toast.makeText(v.getContext(), "Completa el codigo", Toast.LENGTH_SHORT).show();
                    return;
                }

                String profesor = ((EditText) v.getRootView().findViewById(R.id.nuevoCursoProfesor)).getText().toString();

                if (profesor.equals("")) {
                    Toast.makeText(v.getContext(), "Completa el profesor", Toast.LENGTH_SHORT).show();
                    return;
                }

                Object selectedSede = ((Spinner) v.getRootView().findViewById(R.id.nuevoCursoSede)).getSelectedItem();

                if (selectedSede == null) {
                    Toast.makeText(v.getContext(), "Completa la sede", Toast.LENGTH_SHORT).show();
                    return;
                }

                String sede = selectedSede.toString();

                String aula = ((EditText) v.getRootView().findViewById(R.id.nuevoCursoAula)).getText().toString();

                if (aula.equals("")) {
                    Toast.makeText(v.getContext(), "Completa el aula", Toast.LENGTH_SHORT).show();
                    return;
                }

                // creamos el curso
                HashMap<String, String> curso = new HashMap<>();
                curso.put("materia", materia);
                curso.put("codigo", codigo);
                curso.put("profesor", profesor);
                curso.put("sede", sede);
                curso.put("aula", aula);

                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

                String key = dbRef.child("cursos").push().getKey();
                dbRef.child("cursos").child(key).setValue(curso);

                // se lo asignamos al usuario actual
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                dbRef.child("cursosUsuario/"+user.getUid()).push().setValue(key);

                // TODO: cambiar el fragment
                Toast.makeText(v.getContext(), "Curso creado correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
