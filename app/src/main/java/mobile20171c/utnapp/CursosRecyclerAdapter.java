package mobile20171c.utnapp;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CursosRecyclerAdapter extends RecyclerView.Adapter<CursosRecyclerAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<String> cursos;
    private FragmentManager fragmentManager;

    public CursosRecyclerAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        cursos = getCursos();
        fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cursos_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nombreCurso.setText(String.valueOf(position+1) + ". " + cursos.get(position));

        holder.nombreCurso.setOnClickListener(new OnCursoClickListener(fragmentManager, position));
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }

    public class OnCursoClickListener implements View.OnClickListener {

        private android.support.v4.app.FragmentManager fragmentManager;
        private int position;

        public OnCursoClickListener(FragmentManager fragmentManager, int position) {
            this.fragmentManager = fragmentManager;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            this.fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, CursoFragment.newInstance(position), "Fragment")
                    .addToBackStack("Curso")
                    .commit();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombreCurso;

        public ViewHolder(View itemView) {
            super(itemView);
            nombreCurso = (TextView) itemView.findViewById(R.id.nombreCurso);
        }
    }

    /**
     * TODO: obtener los datos de Firebase
     */
    private ArrayList<String> getCursos() {
        ArrayList<String> staticCursos = new ArrayList<String>();

        staticCursos.add("Analisis Matematico II");
        staticCursos.add("Sistemas Operativos");
        staticCursos.add("Fisica II");
        staticCursos.add("Proyecto Final");
        staticCursos.add("Sistemas de Gestion");

        return staticCursos;
    }
}
