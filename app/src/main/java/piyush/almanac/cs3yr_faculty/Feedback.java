package piyush.almanac.cs3yr_faculty;

import android.app.ProgressDialog;
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

import java.util.Hashtable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Feedback extends AppCompatActivity {
    RequestQueue requestQueue;

    @Bind(R.id.feedback)
    EditText feedback;


    String login_url = "http://kgbvbundu.org/cs2014/feedback.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(Feedback.this);



    }

    public  void feedbackbtn(View view)
    {   feedback=(EditText) findViewById(R.id.feedback);
        final ProgressDialog pDialog = ProgressDialog.show(this,"Loading...","Please wait...",false,false);
        final String msg=Data.getEmail(Feedback.this)+":"+feedback.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                if(response.equals(getString(R.string.login_Successresponse))) {
                    Toast.makeText(Feedback.this, R.string.verifycode_Successresponse,Toast.LENGTH_SHORT).show();

                }
                else if(response.equals(getString(R.string.login_Failedresponse))){
                    Toast.makeText(Feedback.this, R.string.login_failedtext,Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(Feedback.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String,String> params = new Hashtable<>();

                //Adding parameters
                params.put("email", Data.getEmail(Feedback.this));
                params.put("feedback", msg);

                //returning parameters
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
