package mobile20171c.utnapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import Calendario.*;

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

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.calendarLayout);

        for (Year year : calendario.years) {

            TextView yearTextView = new TextView(getActivity());
            LinearLayout.LayoutParams yearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            yearParams.bottomMargin = 20;
            yearParams.topMargin = 20;
            yearTextView.setLayoutParams(yearParams);
            yearTextView.setText(year.year);
            yearTextView.setTextAppearance(getContext(), R.style.AppThemeTitle);
            layout.addView(yearTextView);

            for (Month month : year.months) {

                TextView monthTextView = new TextView(getActivity());
                LinearLayout.LayoutParams monthParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                monthParams.bottomMargin = 20;
                monthParams.topMargin = 20;
                monthParams.leftMargin = 20;
                monthTextView.setLayoutParams(monthParams);
                monthTextView.setText(month.month);
                monthTextView.setTextAppearance(getContext(), R.style.AppThemeFormLabel);
                layout.addView(monthTextView);

                for (Event event : month.events) {

                    TextView eventTextView = new TextView(getActivity());
                    LinearLayout.LayoutParams eventParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    eventParams.bottomMargin = 10;
                    eventParams.topMargin = 10;
                    eventParams.leftMargin = 40;
                    eventTextView.setLayoutParams(eventParams);
                    eventTextView.setText(event.date+" - "+event.text);
                    layout.addView(eventTextView);

                }
            }

        }
    }

}
