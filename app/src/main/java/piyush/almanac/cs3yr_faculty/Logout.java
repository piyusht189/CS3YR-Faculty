package piyush.almanac.cs3yr_faculty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

public class Logout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
    }

    public void onLogout()
    {
        File dir = getFilesDir();
        File file = new File(dir, "email.txt");
        file.delete();
        startActivity(new Intent(getApplicationContext(),Login.class));
    }

}
