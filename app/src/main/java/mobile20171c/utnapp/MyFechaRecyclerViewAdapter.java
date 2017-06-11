package mobile20171c.utnapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Dominio.modelo.Fecha;
import mobile20171c.utnapp.FechaFragment.OnListFragmentInteractionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyFechaRecyclerViewAdapter extends RecyclerView.Adapter<MyFechaRecyclerViewAdapter.ViewHolder> {

    private final List<Fecha> mFechas;
    private final OnListFragmentInteractionListener mListener;

    public MyFechaRecyclerViewAdapter(List<Fecha> items, OnListFragmentInteractionListener listener) {
        mFechas = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_fecha, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mFechas.get(position);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        holder.mIdView.setText(df.format(holder.mItem.fecha));
        holder.mContentView.setText(holder.mItem.descripcion +" - " + holder.mItem.getMateria());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFechas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Fecha mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.mensajeAutor);
            mContentView = (TextView) view.findViewById(R.id.mensajeContenido);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
