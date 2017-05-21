package mobile20171c.utnapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CalendarioFragment extends Fragment {

    public CalendarioFragment() {
        // Required empty public constructor
    }

    public static CalendarioFragment newInstance() {
        return new CalendarioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendario, container, false);
    }

}
