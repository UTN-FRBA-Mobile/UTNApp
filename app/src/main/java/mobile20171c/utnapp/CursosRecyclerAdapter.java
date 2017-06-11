package mobile20171c.utnapp;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import Dominio.modelo.Curso;

public class CursosRecyclerAdapter extends FirebaseRecyclerAdapter<Curso, CursosRecyclerAdapter.CursosViewHolder> {

    private FragmentManager fragmentManager;

    public CursosRecyclerAdapter(Context context, DatabaseReference ref) {
        super(Curso.class, R.layout.cursos_item, CursosRecyclerAdapter.CursosViewHolder.class, ref);

        fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
    }

    @Override
    protected void populateViewHolder(CursosViewHolder viewHolder, Curso model, int position) {
        viewHolder.setContent(model.materia);

        viewHolder.view.setOnClickListener(new OnCursoClickListener(fragmentManager, model.id));
    }

    static class CursosViewHolder extends RecyclerView.ViewHolder {
        View view;

        public CursosViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setContent(String materia){
            TextView field = (TextView) view.findViewById(R.id.nombreCurso);
            field.setText(materia);
        }

    }

    private class OnCursoClickListener implements View.OnClickListener {

        private android.support.v4.app.FragmentManager fragmentManager;
        private String cursoId;

        private OnCursoClickListener(FragmentManager fragmentManager, String cursoId) {
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

}
