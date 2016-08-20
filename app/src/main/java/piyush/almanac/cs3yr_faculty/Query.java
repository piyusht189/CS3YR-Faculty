package piyush.almanac.cs3yr_faculty;

import android.app.ProgressDialog;
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

import java.util.Hashtable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Query extends AppCompatActivity {

    RequestQueue requestQueue;
    @Bind(R.id.query) EditText query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ButterKnife.bind(Query.this);

        requestQueue = Volley.newRequestQueue(Query.this);
    }

    public void querybtn(View view) {
        final ProgressDialog pDialog = ProgressDialog.show(this, "Loading...", "Please wait...", false, false);

        String  query_url = getResources().getString(R.string.query_url);

        final String msg=Data.getEmail(Query.this)+":"+query.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, query_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                if (response.equals(getString(R.string.login_Successresponse))) {
                    Toast.makeText(Query.this,R.string.query_success_Message,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Query.this,MainActivity.class));
                    finish();
                } else if (response.equals(getString(R.string.login_Failedresponse))) {
                    Toast.makeText(Query.this, R.string.login_failedtext, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(Query.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String, String> params = new Hashtable<>();

                //Adding parameters

                params.put("query",msg);

                //returning parameters
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
