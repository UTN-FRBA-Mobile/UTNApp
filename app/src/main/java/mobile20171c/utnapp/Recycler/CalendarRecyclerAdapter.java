package mobile20171c.utnapp.Recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Calendario.Item;
import mobile20171c.utnapp.R;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Item[] items;

    public CalendarRecyclerAdapter(Context context, Item[] items) {
        layoutInflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items[position].type.equals("year")) {
            return 0;
        }

        if (items[position].type.equals("month")) {
            return 1;
        }

        return 2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        switch(viewType) {
            case 0:
                view = layoutInflater.inflate(R.layout.calendar_title_year, parent, false);
                break;
            case 1:
                view = layoutInflater.inflate(R.layout.calendar_title_month, parent, false);
                break;
            case 2:
            default:
                view = layoutInflater.inflate(R.layout.calendar_title_day, parent, false);
                break;
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(items[position].text);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }

}
