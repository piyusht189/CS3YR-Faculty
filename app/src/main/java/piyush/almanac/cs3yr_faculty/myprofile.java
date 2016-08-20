package piyush.almanac.cs3yr_faculty;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class myprofile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView profilepic;
    TextView name,email,designation,description,phone;
    String em;
    private static final int PICK_IMAGE_ID1 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        profilepic = (ImageView)findViewById(R.id.profilepic);

        name = (TextView)findViewById(R.id.name);
        email = (TextView)findViewById(R.id.email);
        designation = (TextView)findViewById(R.id.designation);
        description = (TextView)findViewById(R.id.summary);
        phone = (TextView)findViewById(R.id.phone);

        if(Data.getSavedImage(myprofile.this,"ProfilePic")!=null) {
            profilepic.setImageBitmap(Data.getSavedImage(myprofile.this, "ProfilePic"));
        }

        name.setText(Data.getName(myprofile.this));
        email.setText(Data.getEmail(myprofile.this));
        description.setText(Data.getSummary(myprofile.this));
        designation.setText(Data.getDesignation(myprofile.this));
        phone.setText(Data.getPhone(myprofile.this));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent g=new Intent(this,MainActivity.class);
            startActivity(g);
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.myprofile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.feed) {
            startActivity(new Intent(this,Feedback.class));

            return true;
        }
        if (id == R.id.aboutdev) {
            startActivity(new Intent(this,AboutDeveloper.class));

            return true;
        }
        if (id == R.id.logout) {
            File dir = getFilesDir();
            File file = new File(dir, "email.txt");
            file.delete();
            startActivity(new Intent(myprofile.this,Login.class));
            finish();
            return true;
        }
        if (id == R.id.uf) {
            startActivity(new Intent(this,UpcomingFeatures.class));

            return true;
        }
        if (id == R.id.query) {
            startActivity(new Intent(this,Query.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.notices) {
            startActivity(new Intent(this,Notices.class));finish();
        } else if (id == R.id.myprofile) {
            startActivity(new Intent(this,myprofile.class));finish();
        } else if (id == R.id.students) {
            startActivity(new Intent(this,Students.class));finish();
        } else if (id == R.id.faculties) {
            startActivity(new Intent(this,Faculties.class));finish();
        } else if (id == R.id.today) {
            startActivity(new Intent(this,MainActivity.class));finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickPicUpload(View view) {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case PICK_IMAGE_ID1:
                Bitmap bitmap1 = ImagePicker.getImageFromResult(this, resultCode, data);
                profilepic.setImageBitmap(bitmap1);
                uploadImage(bitmap1);
                break;
            default: super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void uploadImage(final Bitmap bitmap){

        if(bitmap!=null) {
            em=Data.getEmail(myprofile.this);
            //Showing the progress dialog
            final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
            String UPLOAD_URL="http://kgbvbundu.org/cs2014teach/profilepic/uploadprofilepic.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            //Disimissing the progress dialog
                            loading.dismiss();
                            //Showing toast message of the response
                            if(s.equalsIgnoreCase("Success")) {
                                saveData();
                                Toast.makeText(myprofile.this, "Pic Uploaded" , Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    //Dismissing the progress dialog
                    loading.dismiss();

                    //Showing toast
                    Toast.makeText(myprofile.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    String body;
                    //get response body and parse with appropriate encoding

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //Converting Bitmap to String
                    String image = getStringImage(bitmap);

                    //Creating parameters
                    Map<String,String> params = new Hashtable<>();

                    //Adding parameters
                    params.put("image", image);
                    params.put("email",em);

                    //returning parameters
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            //Creating a Request Queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //Adding request to the queue
            requestQueue.add(stringRequest);
        }
        else {
            Toast.makeText(myprofile.this,"Operation Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void saveData()
    {
        String email = Data.getEmail(myprofile.this);

        JSONObject params = new JSONObject();
        try {
            params.put("emai", email);
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
                    FileOutputStream fos = openFileOutput("details", MODE_PRIVATE);
                    fos.write(response.toString().getBytes());
                    fos.close();
                    storeProfilePic();
                }
                catch (Exception e)
                {
                    Toast.makeText(myprofile.this,"Failed. Try Again Later",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(myprofile.this,"Internet is slow. Please try again with good internet speed.",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(myprofile.this);

        requestQueue.add(jsonObjectRequest);
    }

    public void storeProfilePic()
    {
        try {
            JSONObject jsonObject2 = new JSONObject(new LoadContent().getStringFromJson(myprofile.this));
            JSONArray jsonArray2 = jsonObject2.getJSONArray("teachdetails");
            JSONObject jsonObject3 = jsonArray2.getJSONObject(0);
            if(jsonObject3.getString("profilepic")!=null)
            {
                new SaveImageAsync().execute(jsonObject3.getString("profilepic"),"ProfilePic");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    class SaveImageAsync extends AsyncTask<String, String, String> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(myprofile.this,"Fetching Your Profile Pic...","Please Wait...",false,false);
        }
        @Override
        protected String doInBackground(String... args) {
            saveImage(getApplicationContext(),args[1],getBitmapFromURL(args[0]));
            return "1";
        }
        protected void onPostExecute(String message) {
            pDialog.dismiss();
            if(!String.valueOf(1).equals(message)) {
                Toast.makeText(myprofile.this,"Failed. Please Try Again",Toast.LENGTH_SHORT).show();
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
