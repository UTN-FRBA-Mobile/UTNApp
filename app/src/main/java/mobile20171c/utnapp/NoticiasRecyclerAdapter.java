package mobile20171c.utnapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NoticiasRecyclerAdapter extends RecyclerView.Adapter<NoticiasRecyclerAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<String> noticias;

    public NoticiasRecyclerAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        noticias = getNoticias();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.noticias_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(String.valueOf(position+1) + ". " + noticias.get(position));
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }

    /**
     * TODO: obtener los datos directamente de la web del SIGA
     */
    private ArrayList<String> getNoticias() {
        ArrayList<String> staticNoticias = new ArrayList<String>();

        staticNoticias.add("INSCRIPCION A SISTEMAS DE REPRESENTACION TUTORIAS E INGLES TECNICO I TUTORIAS");
        staticNoticias.add("MESAS ESPECIALES ABRIL 2017");
        staticNoticias.add("MODIFICACIONES AL REGIMEN DE APROBACION A PARTIR DEL NUEVO REGLAMENTO DE ESTUDIOS");
        staticNoticias.add("PREINSCRIPCION: ASPECTOS IMPORTANTES DEL NUEVO REGLAMENTO A TENER EN CUENTA");
        staticNoticias.add("TRAMITE DE EXCEPCION DE CORRELATIVAS Y CURSADO SIN PASE");
        staticNoticias.add("PREINSCRIPCION A MATERIAS CICLO LECTIVO 2017");
        staticNoticias.add("MESAS DE FINAL FEBRERO MARZO 2017");
        staticNoticias.add("INFORMACION IMPORTANTE MATERIAS CON MODALIDAD VIRTUAL");
        staticNoticias.add("ATENCION - PARO DE TRANSPORTE");
        staticNoticias.add("ELECCIONES DE CLAUSTROS 23 DE JUNIO");

        return staticNoticias;
    }
}
