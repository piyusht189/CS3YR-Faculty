package piyush.almanac.cs3yr_faculty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Students extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView listView;
    Spinner spinner;
    String arr[];
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        for(int i=0;i<10;i++)
        {
            arr[i]=Data.getSections(getApplicationContext(),i);
        }
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name= spinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), name+":Batch Students", Toast.LENGTH_SHORT).show();
                StudentFetchDetail(name);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        listView = (ListView)findViewById(R.id.listViewStudents);

        if(isNetworkAvailable()) {
            StudentFetchDetail(name);
        }
        else {
            Toast.makeText(this, R.string.internet_not_connect, Toast.LENGTH_SHORT).show();
        }


    }
    public void StudentFetchDetail(String name1)
    {
        final ProgressDialog progressDialog = ProgressDialog.show(this,"Fetching...","Please wait",false,false);

        String url = getResources().getString(R.string.Student_url)+name1;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl(url);

        ref.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                String name[] = new String[(int)dataSnapshot.getChildrenCount()];
                String email[] = new String[(int)dataSnapshot.getChildrenCount()];
                String phone[] = new String[(int)dataSnapshot.getChildrenCount()];
                String roll[] = new String[(int)dataSnapshot.getChildrenCount()];

                int i=0;

                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    Student_Structure student_structure = snapshot.getValue(Student_Structure.class);
                    name[i] = student_structure.getName();
                    email[i] = student_structure.getEmail();
                    phone[i] = student_structure.getPhone();
                    roll[i] = student_structure.getRoll();
                    i++;
                }
                Student_List adapter = new Student_List(Students.this,name,email,phone,roll);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
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
        getMenuInflater().inflate(R.menu.students, menu);
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
            startActivity(new Intent(this,myprofile.class));
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
}
