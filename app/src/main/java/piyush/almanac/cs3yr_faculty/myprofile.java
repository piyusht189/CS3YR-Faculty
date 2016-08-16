package piyush.almanac.cs3yr_faculty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;

public class myprofile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView profilepic;
    TextView name,email,designation,description,phone;

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
        description = (TextView)findViewById(R.id.description);
        phone = (TextView)findViewById(R.id.phone);


        profilepic.setImageBitmap(Data.getSavedImage(My_Profile.this,"ProfilePic"));

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
            finish();
            return true;
        }
        if (id == R.id.aboutdev) {
            startActivity(new Intent(this,AboutDeveloper.class));
            finish();
            return true;
        }
        if (id == R.id.logout) {
            Logout logout = new Logout();
            logout.onLogout();
            finish();

            return true;
        }
        if (id == R.id.uf) {
            startActivity(new Intent(this,UpcomingFeatures.class));
            finish();
            return true;
        }
        if (id == R.id.query) {
            startActivity(new Intent(this,Query.class));
            finish();
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
            startActivity(new Intent(this,Notices.class));
        } else if (id == R.id.myprofile) {
            startActivity(new Intent(this,MyProfile.class));
        } else if (id == R.id.students) {
            startActivity(new Intent(this,Students.class));
        } else if (id == R.id.faculties) {
            startActivity(new Intent(this,Faculties.class));
        } else if (id == R.id.today) {
            startActivity(new Intent(this,MainActivity.class));
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
String em=
        if(bitmap!=null) {
            //Showing the progress dialog
            final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
            String UPLOAD_URL;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            //Disimissing the progress dialog
                            loading.dismiss();
                            //Showing toast message of the response
                            if(s.equalsIgnoreCase("success")) {
                                Toast.makeText(My_Profile.this, "Pic Uploaded" , Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    //Dismissing the progress dialog
                    loading.dismiss();

                    //Showing toast
                    Toast.makeText(My_Profile.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    String body;
                    //get response body and parse with appropriate encoding
                    if(volleyError.networkResponse.data!=null) {
                        try {
                            body = new String(volleyError.networkResponse.data,"UTF-8");
                            Toast.makeText(My_Profile.this,body, Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            Toast.makeText(My_Profile.this,e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
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
                    params.put("email", Data.getEmail(My_Profile.this));

                    //returning parameters
                    return params;
                }
            };

            //Creating a Request Queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //Adding request to the queue
            requestQueue.add(stringRequest);
        }
        else {
            Toast.makeText(MyProfile.this,"Operation Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

}
