package piyush.almanac.cs3yr_faculty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Register extends AppCompatActivity {


    RequestQueue requestQueue;

    @Bind(R.id.register_name)
    EditText nametext;
    @Bind(R.id.register_email) EditText emailtext;
    @Bind(R.id.register_pass1) EditText pass1text;
    @Bind(R.id.register_pass2) EditText pass2text;
    @Bind(R.id.register_design) EditText designationtext;
    @Bind(R.id.register_phone) EditText phonetext;
    @Bind(R.id.description) EditText summary;

    String register_url = "http://kgbvbundu.org/cs2014teach/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(Register.this);
        requestQueue = Volley.newRequestQueue(Register.this);

        if(!loadData().equalsIgnoreCase("")) {
            startActivity(new Intent(Register.this,VerificationCode.class));
            finish();
        }
    }

    @OnClick(R.id.register_button)
    public void onClick(View view)
    {
        register();
    }

    public void register()
    {
        final String name = nametext.getText().toString();
        final String email = emailtext.getText().toString();
        final String pass1 = pass1text.getText().toString();
        final String pass2 = pass2text.getText().toString();
        final String designation = designationtext.getText().toString();
        final String phone = phonetext.getText().toString();
        final String summ = summary.getText().toString();

        if(!name.equals("") && !email.equals("") && !pass1.equals("") && !pass2.equals("") && !designation.equals("") && !phone.equals("")) {

                if(pass1.equalsIgnoreCase(pass2)) {

                        if(phone.length()==10) {

                                if(isNetworkAvailable()) {

                                    final ProgressDialog pDialog = ProgressDialog.show(this,"Registering...","Please wait...",false,false);
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, register_url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            pDialog.dismiss();
                                            if(response.equals(getString(R.string.register_Successresponse))) {
                                                savedata1(email);
                                                savedata2(getMacid());
                                                startActivity(new Intent(Register.this,VerificationCode.class));
                                                finish();
                                            }
                                            else if(response.equals(getString(R.string.register_Alreadyresponse))) {
                                                Toast.makeText(Register.this,R.string.register_registeredtext,Toast.LENGTH_SHORT).show();
                                            }
                                            else if(response.equals(getString(R.string.register_Failedresponse))){
                                                Toast.makeText(Register.this, R.string.register_failedtext,Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            pDialog.dismiss();
                                            Toast.makeText(Register.this,"errorrr",Toast.LENGTH_SHORT).show();
                                        }
                                    }){
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            //Creating parameters
                                            Map<String,String> params = new Hashtable<>();

                                            //Adding parameters
                                            params.put("name", name);
                                            params.put("email", email);
                                            params.put("password", pass1);
                                            params.put("designation", designation);
                                            params.put("phone", phone);
                                            params.put("summary", summ);


                                            //returning parameters
                                            return params;
                                        }
                                    };
                                    requestQueue.add(stringRequest);
                                }
                                else {
                                    Toast.makeText(Register.this,"Please connect to internet and try again.",Toast.LENGTH_SHORT).show();
                                }

                        }
                        else {
                            Toast.makeText(Register.this,"Phone Number Invalid. Please Enter correct one",Toast.LENGTH_SHORT).show();
                        }

                }
                else {
                    Toast.makeText(Register.this,"Passwords didn't matched. Please try again.",Toast.LENGTH_SHORT).show();
                }

        }
        else {
            Toast.makeText(Register.this,"Please fill out all the fields",Toast.LENGTH_SHORT).show();
        }

    }

    public void savedata1(String email)
    {
        String FILENAME = "emailregister.txt";
        try {
            FileOutputStream fos = getApplication().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(email.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savedata2(String id)
    {
        String FILENAME = "id.txt";
        try {
            FileOutputStream fos = getApplication().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(id.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMacid() {
        return UUID.randomUUID().toString();
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    protected String loadData() {
        String FILENAME = "emailregister.txt";
        String out = "";

        try {
            FileInputStream fis1 = getApplication().openFileInput(FILENAME);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
            String sLine1;
            while (((sLine1 = br1.readLine()) != null)) {
                out += sLine1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return out;
    }

}