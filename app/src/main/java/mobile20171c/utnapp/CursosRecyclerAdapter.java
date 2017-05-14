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

import Dominio.modelo.Curso;
import Dominio.repositorios.RepositorioCursos;

public class CursosRecyclerAdapter extends RecyclerView.Adapter<CursosRecyclerAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<Curso> cursos;
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
        Curso cursoSeleccionado = cursos.get(position);

        holder.nombreCurso.setText(cursoSeleccionado.materia);
        holder.nombreCurso.setOnClickListener(new OnCursoClickListener(fragmentManager, cursoSeleccionado.getIdentificador()));
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }

    public class OnCursoClickListener implements View.OnClickListener {

        private android.support.v4.app.FragmentManager fragmentManager;
        private String cursoId;

        public OnCursoClickListener(FragmentManager fragmentManager, String cursoId) {
            this.fragmentManager = fragmentManager;
            this.cursoId = cursoId;
        }

        @Override
        public void onClick(View v) {
            this.fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, CursoFragment.newInstance(cursoId), "Fragment")
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

    private ArrayList<Curso> getCursos() {
        return new RepositorioCursos().GetAll();
    }
}
