package mobile20171c.utnapp.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import mobile20171c.utnapp.Modelo.Fecha;
import mobile20171c.utnapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FechaRecyclerAdapter extends FirebaseRecyclerAdapter<Fecha, FechaRecyclerAdapter.FechasViewHolder> {

    private boolean mostrarMateria = false;

    public FechaRecyclerAdapter(DatabaseReference ref) {
        super(Fecha.class, R.layout.fragment_fecha, FechaRecyclerAdapter.FechasViewHolder.class, ref);
    }

    public FechaRecyclerAdapter(DatabaseReference ref, boolean mostrarMateria) {
        super(Fecha.class, R.layout.fragment_fecha, FechaRecyclerAdapter.FechasViewHolder.class, ref);
        this.mostrarMateria = mostrarMateria;
    }

    @Override
    protected void populateViewHolder(FechaRecyclerAdapter.FechasViewHolder viewHolder, Fecha fecha, int position) {
        viewHolder.setContent(fecha);
    }

    @Override
    public FechaRecyclerAdapter.FechasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FechasViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

        viewHolder.mostrarMateria(mostrarMateria);

        return viewHolder;
    }

    static class FechasViewHolder extends RecyclerView.ViewHolder {
        View view;
        Boolean mostrarMateria = false;

        public FechasViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        private void mostrarMateria(boolean mostrarMateria) {
            this.mostrarMateria = mostrarMateria;
        }

        public void setContent(Fecha fecha){
            TextView autor = (TextView) view.findViewById(R.id.mensajeAutor);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            autor.setText(df.format(fecha.fecha));

            TextView contenido = (TextView) view.findViewById(R.id.mensajeContenido);

            if (mostrarMateria) {
                contenido.setText(fecha.materia+" - "+fecha.evento);
            } else {
                contenido.setText(fecha.evento);
            }
        }

    }

}
