package piyush.almanac.cs3yr_faculty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_content);
        startActivity(new Intent(LoadContent.this, MainActivity.class));
    }
}
