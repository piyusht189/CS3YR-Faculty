package piyush.almanac.cs3yr_faculty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

public class Login extends AppCompatActivity {
    RequestQueue requestQueue;

    @Bind(R.id.login_email) EditText emailtext;
    @Bind(R.id.login_pass) EditText passtext;

    String login_url = "http://kgbvbundu.org/cs2014teach/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ButterKnife.bind(Login.this);
        requestQueue = Volley.newRequestQueue(Login.this);

        if (!loadData2().equals("")) {
            startActivity(new Intent(Login.this, VerificationCode.class));
            finish();
        } else if (!loadData1().equals("")) {
            startActivity(new Intent(Login.this, LoadContent.class));
            finish();
        }

    }


    public void onClickLogin(View view)
    {
        login();
    }

    public void login()
    {
        final String email = emailtext.getText().toString();
        final String pass = passtext.getText().toString();

        final ProgressDialog pDialog = ProgressDialog.show(this,"Loading...","Please wait...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                if(response.equals(getString(R.string.login_Successresponse))) {
                    savedata(email);
                    startActivity(new Intent(Login.this,MainActivity.class));
                    finish();
                }
                else if(response.equals(getString(R.string.login_Pendingresponse))) {
                    Toast.makeText(Login.this,R.string.login_pendingtext,Toast.LENGTH_SHORT).show();
                }
                else if(response.equals(getString(R.string.login_NotRegisteredresponse))) {
                    Toast.makeText(Login.this, R.string.login_notregisteredtext,Toast.LENGTH_SHORT).show();
                }
                else if(response.equals(getString(R.string.login_Failedresponse))){
                    Toast.makeText(Login.this, R.string.login_failedtext,Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(Login.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String,String> params = new Hashtable<>();

                //Adding parameters
                params.put("email", email);
                params.put("password", pass);
                // params.put("macid", loadData3());

                //returning parameters
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void savedata(String email)
    {
        String FILENAME = "email.txt";
        try {
            FileOutputStream fos = getApplication().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(email.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String loadData1() {
        String FILENAME = "email.txt";
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

    protected String loadData2() {
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

    /*  protected String loadData3() {
          String FILENAME = "id.txt";
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
  */
    public void onClickLoginToSignUp(View view)
    {
        startActivity(new Intent(Login.this,Register.class));
    }


}
