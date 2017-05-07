package mobile20171c.utnapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CursosFragment extends Fragment {

    private RecyclerView recyclerView;

    public CursosFragment() {
        // Required empty public constructor
    }

    public static CursosFragment newInstance() {
        return new CursosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cursos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCursos);
        recyclerView.setAdapter(new CursosRecyclerAdapter(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
