package mobile20171c.utnapp.Cursos;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import mobile20171c.utnapp.Modelo.Curso;
import mobile20171c.utnapp.Modelo.Fecha;
import mobile20171c.utnapp.R;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static final String ARG_ID_CURSO = "idCurso";
    private String idCurso;
    private Curso curso;

    public static DatePickerFragment newInstance(String idCurso) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_CURSO, idCurso);
        fragment.setArguments(args);
        return fragment;
    }

    private void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        idCurso = getArguments().getString(ARG_ID_CURSO);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(R.layout.fecha_dialog);

        builder.setPositiveButton("Enviar", null)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        FirebaseDatabase.getInstance()
                .getReference("cursos")
                .child(getArguments().getString(ARG_ID_CURSO))
                .addListenerForSingleValueEvent(new CursoValueEventListener(this));

        final AlertDialog d = builder.create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        EditText evento = (EditText) view.getRootView().findViewById(R.id.fechaEvento);
                        DatePicker datepicker = (DatePicker) view.getRootView().findViewById(R.id.fechaDatepicker);

                        if (evento.getText().toString().equals("")) {
                            Toast.makeText(view.getContext(), "Completa el evento.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // creamos el mensaje
                        Fecha fecha = new Fecha();
                        fecha.idCurso = idCurso;
                        fecha.evento = evento.getText().toString();

                        fecha.materia = curso.materia;

                        int   day  = datepicker.getDayOfMonth();
                        int   month= datepicker.getMonth();
                        int   year = datepicker.getYear();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DATE, day);
                        fecha.fecha = calendar.getTime();

                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

                        // lo asignamos al curso
                        String key = dbRef.child("fechasCursos").child(idCurso).push().getKey();
                        fecha.id = key;
                        dbRef.child("fechasCursos").child(idCurso).child(key).setValue(fecha);

                        // y al usuario
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        dbRef.child("fechasUsuarios").child(user.getUid()).child(key).setValue(fecha);

                        // ok
                        Toast.makeText(view.getContext(), "Mensaje enviado.", Toast.LENGTH_SHORT).show();

                        d.dismiss();
                    }
                });
            }
        });

        // Create the AlertDialog object and return it
        return d;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
    }

    private class CursoValueEventListener implements ValueEventListener {

        DatePickerFragment fragment;

        private CursoValueEventListener(DatePickerFragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onDataChange(DataSnapshot snapshot) {
            if (snapshot.exists()) {
                fragment.setCurso(snapshot.getValue(Curso.class));
            } else {
                Log.e("CursoValueEventListener", "Curso not found");
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e("CursoValueEventListener", databaseError.toString());
        }
    }
}
