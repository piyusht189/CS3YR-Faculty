package piyush.almanac.cs3yr_faculty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationCode extends AppCompatActivity {

    RequestQueue requestQueue;

    @Bind(R.id.verificationtext)
    EditText vctext;

    String verify_url = "http://kgbvbundu.org/cs2014teach/verify.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);


        ButterKnife.bind(VerificationCode.this);
        requestQueue = Volley.newRequestQueue(VerificationCode.this);
    }

    @OnClick(R.id.verify_button)
    public void onClick(View view) {

        final String vc = vctext.getText().toString();

        if (isNetworkAvailable()) {
            if(!vc.equalsIgnoreCase("")) {
                final ProgressDialog pDialog = ProgressDialog.show(this, "Verifying...", "Please wait...", false, false);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, verify_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        if (response.equals(getString(R.string.verifycode_Successresponse))) {
                            savedata2(loadData());
                            savedata1("");
                            startActivity(new Intent(VerificationCode.this, LoadContent.class));
                            finish();
                        }
                        else if(response.equals(getString(R.string.verifycode_Failedresponse))){
                            Toast.makeText(VerificationCode.this, R.string.verifycode_failedtext,Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(VerificationCode.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //Creating parameters
                        Map<String, String> params = new Hashtable<>();

                        //Adding parameters
                        params.put("email",loadData());
                        params.put("verifycode",vc);

                        //returning parameters
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
            else {
                Toast.makeText(VerificationCode.this,"Please enter the verification code to verify.",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(VerificationCode.this,"Please connect to internet and try again.",Toast.LENGTH_SHORT).show();
        }
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

    public void savedata2(String email)
    {
        String FILENAME = "email.txt";
        try {
            FileOutputStream fos = getApplication().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(email.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}