package piyush.almanac.cs3yr_faculty;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Student_List extends ArrayAdapter<String> {

    Activity context;
    String[] name,email,phone,roll;

    TextView textViewName,textViewEmail,textViewPhone,textViewRoll;

    public Student_List(Activity context, String[] name, String[] email, String[] phone, String[] roll) {
        super(context, R.layout.student_structure, name);
        this.context = context;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.roll = roll;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.student_structure,null,true);

        textViewName = (TextView)view.findViewById(R.id.textViewStudName);
        textViewEmail = (TextView)view.findViewById(R.id.textViewStudEmail);
        textViewPhone = (TextView)view.findViewById(R.id.textViewStudPhone);
        textViewRoll = (TextView)view.findViewById(R.id.textViewStudRoll);

        textViewName.setText(name[position]);
        textViewEmail.setText(email[position]);
        textViewPhone.setText(phone[position]);
        textViewRoll.setText(roll[position]);

        return view;
    }
}
