package mobile20171c.utnapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import mobile20171c.utnapp.Modelo.Fecha;
import mobile20171c.utnapp.Recycler.FechaRecyclerAdapter;

public class FechaFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    public FechaFragment() {
    }

    public static FechaFragment newInstance() {
        return new FechaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fecha_list, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* recycler */
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerFechas);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setAdapter(new FechaRecyclerAdapter(
                        FirebaseDatabase.getInstance().getReference().child("fechasUsuarios").child(user.getUid())
                )
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Fecha item);
    }
}
