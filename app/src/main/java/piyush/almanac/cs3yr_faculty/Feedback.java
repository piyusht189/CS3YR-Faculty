package piyush.almanac.cs3yr_faculty;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.Hashtable;
import java.util.Map;

public class Feedback extends AppCompatActivity {
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        requestQueue = Volley.newRequestQueue(Feedback.this);
    }

    public void feedbackbtn(View view) {

        final EditText editText = (EditText)findViewById(R.id.feedback);
        final String em=Data.getEmail(Feedback.this);
        final String msg=Data.getEmail(Feedback.this)+":"+editText.getText().toString();
        if(Data.isNetworkAvailable(Feedback.this))
        {
            String feedback_url = getResources().getString(R.string.feedback_url);

            final ProgressDialog pDialog = ProgressDialog.show(this,"Sending...","Please wait...",false,false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, feedback_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();

                    if(response.equals(getString(R.string.login_Successresponse))) {
                        Toast.makeText(Feedback.this,"Feedback Sent Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Feedback.this,MainActivity.class));
                        finish();
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

                    params.put("feedback",msg);

                    //returning parameters
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(this,R.string.internet_not_connect,Toast.LENGTH_SHORT).show();
        }
    }
}
