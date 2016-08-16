package piyush.almanac.cs3yr_faculty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class main_tab1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_main_tab1,container,false);

        TextView date2 =(TextView) v.findViewById(R.id.date);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date1 = df.format(c.getTime());

        TextView txt = (TextView) v.findViewById(R.id.day);
        ImageView img= (ImageView) v.findViewById(R.id.ttimage);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                txt.setText("SUNDAY");
                date2.setText(date1);
                img.setImageResource(R.drawable.sunday);
                break;
            case Calendar.MONDAY:
                txt.setText("MONDAY");
                img.setImageBitmap(Data.getSavedImage(getActivity(),"Monday"));
                date2.setText(date1);
                break;
            case Calendar.TUESDAY:
                txt.setText("TUESDAY");
                img.setImageBitmap(Data.getSavedImage(getActivity(),"Tuesday"));
                date2.setText(date1);
                break;
            case Calendar.WEDNESDAY:
                txt.setText("WEDNESDAY");
                img.setImageBitmap(Data.getSavedImage(getActivity(),"Wednesday"));
                date2.setText(date1);
                break;
            case Calendar.THURSDAY:
                txt.setText("THURSDAY");
                img.setImageBitmap(Data.getSavedImage(getActivity(),"Thursday"));
                date2.setText(date1);
                break;
            case Calendar.FRIDAY:
                txt.setText("FRIDAY");
                img.setImageBitmap(Data.getSavedImage(getActivity(),"Friday"));
                date2.setText(date1);
                break;
            case Calendar.SATURDAY:
                txt.setText("SATURDAY");
                img.setImageBitmap(Data.getSavedImage(getActivity(),"Saturday"));
                date2.setText(date1);
                break;
        }
        return v;
    }
}