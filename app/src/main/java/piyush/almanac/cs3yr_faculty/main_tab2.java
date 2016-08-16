package piyush.almanac.cs3yr_faculty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class main_tab2 extends Fragment {
    ImageView fulltimetable;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_main_tab2,container,false);

        fulltimetable = (ImageView)v.findViewById(R.id.fulltimetable);
        fulltimetable.setImageBitmap(Data.getSavedImage(getActivity(),"FullTT"));
        return v;
    }
}