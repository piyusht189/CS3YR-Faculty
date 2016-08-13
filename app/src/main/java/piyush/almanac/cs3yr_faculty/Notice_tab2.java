package piyush.almanac.cs3yr_faculty;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Notice_tab2 extends Fragment {

    ListView listView;
    EditText editText;
    Spinner spinner;
    String arr[]={"piyush","Mayank"};
    public Notice_tab2(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notice_tab2, container, false);
        editText = (EditText)view.findViewById(R.id.batch_notice_text);
        listView = (ListView)view.findViewById(R.id.listView_teach_notice);
        final Button button = (Button)view.findViewById(R.id.batch_notice_send_button);
        spinner = (Spinner) view.findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arr);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name= spinner.getSelectedItem().toString();
                Toast.makeText(getActivity(), "Selected ", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        if (isNetworkAvailable()) {
            TeacherNoticeSet();
        } else {
            Toast.makeText(getContext(), R.string.internet_not_connect, Toast.LENGTH_SHORT).show();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    onClickSendStudNotice();
                } else {
                    Toast.makeText(getContext(), R.string.internet_not_connect, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void TeacherNoticeSet()
    {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),"Fetching...","Please wait",false,false);

        String url = getActivity().getResources().getString(R.string.recent_notice_url)+Data.getSection();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl(url);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                String name[] = new String[(int)dataSnapshot.getChildrenCount()];
                String message[] = new String[(int)dataSnapshot.getChildrenCount()];
                int i=0;

                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    Notice_Structure notice_structure = snapshot.getValue(Notice_Structure.class);
                    name[i] = notice_structure.getName();
                    message[i] = notice_structure.getMessage();
                    i++;
                }

                if (getActivity() != null) {
                    Single_Notice notice = new Single_Notice(getActivity(),name,message);
                    listView.setAdapter(notice);
                    listView.setSelection(notice.getCount()-1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

    public void onClickSendStudNotice()
    {
        String url = getActivity().getResources().getString(R.string.recent_notice_url)+Data.getSection();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl(url);

        Notice_Structure notice_structure = new Notice_Structure();
        notice_structure.setName(getName());
        notice_structure.setMessage(editText.getText().toString());
        ref.push().setValue(notice_structure);
        editText.setText("");
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

    public String getName()
    {
        return "Piyush";
    }
}