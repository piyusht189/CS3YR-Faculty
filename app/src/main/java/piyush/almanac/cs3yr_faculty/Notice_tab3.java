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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Notice_tab3 extends Fragment {

    EditText editText;
    ListView listView;

    public Notice_tab3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notice_tab3, container, false);

        editText = (EditText)view.findViewById(R.id.public_notice_text);
        listView = (ListView)view.findViewById(R.id.listView_public_notice);
        final Button button = (Button)view.findViewById(R.id.public_notice_send_button);

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
            StudentNoticeSet();
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

    public void StudentNoticeSet()
    {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),"Fetching...","Please wait",false,false);

        String url = getActivity().getResources().getString(R.string.public_notice_url)+Data.getSection();
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
                    Notice_Structure1 notice_structure1 = snapshot.getValue(Notice_Structure1.class);
                    name[i] = notice_structure1.getName();
                    message[i] = notice_structure1.getMessage();
                    i++;
                }

                if (getActivity() != null) {
                    Single_Notice_Public notice = new Single_Notice_Public(getActivity(),name,message);
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
        String url = getActivity().getResources().getString(R.string.public_notice_url)+Data.getSection();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl(url);

        Notice_Structure1 notice_structure1 = new Notice_Structure1();
        notice_structure1.setName(getName());
        notice_structure1.setMessage(editText.getText().toString());
        ref.push().setValue(notice_structure1);
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
        return "mayank";
    }
}
