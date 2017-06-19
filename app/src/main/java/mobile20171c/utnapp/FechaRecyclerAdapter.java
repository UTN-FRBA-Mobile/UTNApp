package mobile20171c.utnapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import Dominio.modelo.Fecha;
import Dominio.modelo.Mensaje;
import mobile20171c.utnapp.FechaFragment.OnListFragmentInteractionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class FechaRecyclerAdapter extends FirebaseRecyclerAdapter<Fecha, FechaRecyclerAdapter.FechasViewHolder> {

    public FechaRecyclerAdapter(DatabaseReference ref) {
        super(Fecha.class, R.layout.fragment_fecha, FechaRecyclerAdapter.FechasViewHolder.class, ref);
    }

    @Override
    protected void populateViewHolder(FechaRecyclerAdapter.FechasViewHolder viewHolder, Fecha fecha, int position) {
        viewHolder.setContent(fecha);
    }

    static class FechasViewHolder extends RecyclerView.ViewHolder {
        View view;

        public FechasViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setContent(Fecha fecha){
            TextView autor = (TextView) view.findViewById(R.id.mensajeAutor);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            autor.setText(df.format(fecha.fecha));

            TextView contenido = (TextView) view.findViewById(R.id.mensajeContenido);
            contenido.setText(fecha.evento);
        }

    }

}
