package mobile20171c.utnapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import Calendario.*;
import mobile20171c.utnapp.Recycler.CalendarRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarioFragment extends Fragment {

    Calendario calendario;

    public CalendarioFragment() {
        // Required empty public constructor
    }

    public static CalendarioFragment newInstance() {
        return new CalendarioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendario, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            InputStream is = getActivity().getAssets().open("calendar.json");
            calendario = Calendario.getInstanceFromAsset(is);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCalendar);
        recyclerView.setAdapter(new CalendarRecyclerAdapter(getContext(), calendario.items));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
