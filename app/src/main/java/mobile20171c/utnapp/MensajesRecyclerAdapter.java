package mobile20171c.utnapp;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import Dominio.modelo.Mensaje;


public class MensajesRecyclerAdapter extends FirebaseRecyclerAdapter<Mensaje, MensajesRecyclerAdapter.MensajesViewHolder> {

    public MensajesRecyclerAdapter(DatabaseReference ref) {
        super(Mensaje.class, R.layout.mensajes_item, MensajesRecyclerAdapter.MensajesViewHolder.class, ref);
    }

    @Override
    protected void populateViewHolder(MensajesRecyclerAdapter.MensajesViewHolder viewHolder, Mensaje mensaje, int position) {
        viewHolder.setContent(mensaje);
    }

    static class MensajesViewHolder extends RecyclerView.ViewHolder {
        View view;

        public MensajesViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setContent(Mensaje mensaje){
            TextView autor = (TextView) view.findViewById(R.id.mensajeAutor);
            autor.setText(mensaje.autor);

            TextView contenido = (TextView) view.findViewById(R.id.mensajeContenido);
            contenido.setText(mensaje.contenido);
        }

    }

}