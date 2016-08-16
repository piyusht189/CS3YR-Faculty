package piyush.almanac.cs3yr_faculty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoadContent extends AppCompatActivity {
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_content);
        requestQueue = Volley.newRequestQueue(LoadContent.this);
        if(isNetworkAvailable()) {
            savealldata();
            storeImagesRoutine();
            startActivity(new Intent(LoadContent.this,MainActivity.class));
            finish();
        }
        else {
            Toast.makeText(LoadContent.this, R.string.Internet_Not_Connected, Toast.LENGTH_SHORT).show();
        }
    }

    public void savealldata()
    {
        JSONObject params = new JSONObject();
        try {
            params.put("emai", loadData());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String load_url = getResources().getString(R.string.load_url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, load_url,params, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    Toast.makeText(LoadContent.this,response.toString(),Toast.LENGTH_LONG).show();
                    FileOutputStream fos = openFileOutput("details", MODE_PRIVATE);
                    fos.write(response.toString().getBytes());
                    fos.close();
                    Toast.makeText(LoadContent.this,"Details Fetched",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(LoadContent.this,"Failed. Try Again Later",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoadContent.this,"Internet is slow. Please try again with good internet speed.",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    protected String loadData() {
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


    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public String getStringFromJson(Context context)
    {
        try {
            FileInputStream fis = context.openFileInput("details");
            BufferedInputStream bis = new BufferedInputStream(fis);
            StringBuilder b = new StringBuilder();
            while ((bis.available() != 0)) {
                char c = (char) bis.read();
                b.append(c);
            }
            bis.close();
            fis.close();
           // Toast.makeText(context,b.toString(),Toast.LENGTH_SHORT).show();
            return b.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void storeImagesRoutine()
    {
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(LoadContent.this));
            JSONArray jsonArray = jsonObject.getJSONArray("tt");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            new SaveImageAsync().execute(jsonObject1.getString("mon"),"Monday");
            new SaveImageAsync().execute(jsonObject1.getString("tue"),"Tuesday");
            new SaveImageAsync().execute(jsonObject1.getString("wed"),"Wednesday");
            new SaveImageAsync().execute(jsonObject1.getString("thurs"),"Thursday");
            new SaveImageAsync().execute(jsonObject1.getString("fri"),"Friday");
            new SaveImageAsync().execute(jsonObject1.getString("sat"),"Saturday");
           // new SaveImageAsync().execute(jsonObject1.getString("main"),"FullTT");
            storeImageProfilePic();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void storeImageProfilePic()
    {
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(LoadContent.this));
            JSONArray jsonArray = jsonObject.getJSONArray("teachdetails");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            new SaveImageAsync().execute(jsonObject1.getString("profilepic"),"ProfilePic");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    class SaveImageAsync extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... args) {
            saveImage(getApplicationContext(),args[1],getBitmapFromURL(args[0]));
            return "1";
        }
        protected void onPostExecute(String message) {
            if(!String.valueOf(1).equals(message))
            {
                Toast.makeText(LoadContent.this,"Failed. Please Try Again",Toast.LENGTH_SHORT).show();
            }
        }

    }

    public Bitmap getBitmapFromURL(String src)
    {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveImage(Context context, String name, Bitmap bitmap){
        name=name+".JPG";
        FileOutputStream out;
        try {
            out = context.openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
