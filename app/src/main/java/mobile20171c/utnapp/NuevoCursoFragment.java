package mobile20171c.utnapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

        FirebaseDatabase.getInstance().getReference().child("materias").addValueEventListener(new MateriaValuesEventListener(getContext(), view));

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

                curso.put("id", key);

                dbRef.child("cursos").child(key).setValue(curso);

                // se lo asignamos al usuario actual
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                dbRef.child("usuarios").child(user.getUid()).child("cursos").child(key).setValue(curso);

                // TODO: cambiar el fragment
                Toast.makeText(v.getContext(), "Curso creado correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private class MateriaValuesEventListener implements ValueEventListener {

        Context context;
        View view;

        public MateriaValuesEventListener(Context context, View view) {
            this.context = context;
            this.view = view;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            final List<String> materias = new ArrayList<String>();

            for (DataSnapshot materiaSnapshot: dataSnapshot.getChildren()) {
                String nombreMateria = materiaSnapshot.getValue(String.class);
                materias.add(nombreMateria);
            }

            Spinner materiasSpiner = (Spinner) view.findViewById(R.id.nuevoCursoMateria);
            ArrayAdapter<String> materiasAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, materias);
            materiasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            materiasSpiner.setAdapter(materiasAdapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
