package piyush.almanac.cs3yr_faculty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class Faculties extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    String tname[];
    String temail[];
    String phone[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculties);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.faculties_list_view);
       try {
           JSONArray te = Data.getTeacherEmail(Faculties.this);
           temail = new String[te.length()];
           for (int i = 0; i < te.length(); i++) {
               JSONObject d1 = te.getJSONObject(i);
               temail[i]=d1.getString("temail");
           }
           JSONArray tn = Data.getTeacherName(Faculties.this);
           tname = new String[tn.length()];
           for (int i = 0; i < tn.length(); i++) {
               JSONObject d2 = tn.getJSONObject(i);
               tname[i]=d2.getString("tname");
           }
           JSONArray tp = Data.getTeacherPhone(Faculties.this);
           phone = new String[tp.length()];
           for (int i = 0; i < tp.length(); i++) {
               JSONObject d3 = tp.getJSONObject(i);
                 phone[i]=d3.getString("phone");
           }

           Faculty_List adapter = new Faculty_List(Faculties.this,tname,temail,phone);
           listView.setAdapter(adapter);

       }catch (Exception e)
        {
            e.printStackTrace();

        }





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
        getMenuInflater().inflate(R.menu.faculties, menu);
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
            startActivity(new Intent(Faculties.this,Login.class));
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
}
