package piyush.almanac.cs3yr_faculty;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Super-Nova on 11-08-2016.
 */
public class Faculty_List extends ArrayAdapter<String> {

    Activity context;
    String[] name,email,phone;

    TextView textViewName,textViewEmail,textViewPhone;

    public Faculty_List(Activity context, String[] name, String[] email, String[] phone) {
        super(context, R.layout.teacher_structure, name);
        this.context = context;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.student_structure,null,true);

        textViewName = (TextView)view.findViewById(R.id.textViewfacName);
        textViewEmail = (TextView)view.findViewById(R.id.textViewfacEmail);
        textViewPhone = (TextView)view.findViewById(R.id.textViewfacPhone);


        textViewName.setText(name[position]);
        textViewEmail.setText(email[position]);
        textViewPhone.setText(phone[position]);


        return view;
    }
}
