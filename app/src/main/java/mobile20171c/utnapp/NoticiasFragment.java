package mobile20171c.utnapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobile20171c.utnapp.Recycler.NoticiasRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasFragment extends Fragment {

    private RecyclerView recyclerView;

    public NoticiasFragment() {
        // Required empty public constructor
    }

    public static NoticiasFragment newInstance() {
        return new NoticiasFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noticias, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewNoticias);
        recyclerView.setAdapter(new NoticiasRecyclerAdapter(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
