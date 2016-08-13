package piyush.almanac.cs3yr_faculty;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Single_Notice_Public extends ArrayAdapter<String> {

    Activity context;
    String[] name,message;

    TextView textViewname,textViewmessage;

    public Single_Notice_Public(Activity context, String[] name,String[] message) {
        super(context, R.layout.notice_structure1, name);
        this.context=context;
        this.message=message;
        this.name=name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.notice_structure1,null,true);

        textViewname = (TextView)view.findViewById(R.id.textViewName1);
        textViewmessage = (TextView)view.findViewById(R.id.textViewMessage1);

        textViewname.setText(name[position]);
        textViewmessage.setText(message[position]);

        return view;
    }
}