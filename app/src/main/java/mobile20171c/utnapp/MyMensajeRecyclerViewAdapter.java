package mobile20171c.utnapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Dominio.modelo.Mensaje;
import mobile20171c.utnapp.CursoMensajeFragment.OnListFragmentInteractionListener;

import java.util.List;


public class MyMensajeRecyclerViewAdapter extends RecyclerView.Adapter<MyMensajeRecyclerViewAdapter.ViewHolder> {

    private final List<Mensaje> mMensajes;
    private final OnListFragmentInteractionListener mListener;

    public MyMensajeRecyclerViewAdapter(List<Mensaje> mensajesCurso, OnListFragmentInteractionListener listener) {
        mMensajes = mensajesCurso;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mensaje, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mMensaje = mMensajes.get(position);
        holder.mIdView.setText(mMensajes.get(position).Autor);
        holder.mContentView.setText(mMensajes.get(position).GetPreview());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mMensaje);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMensajes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Mensaje mMensaje;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
