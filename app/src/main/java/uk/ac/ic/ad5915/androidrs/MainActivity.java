package uk.ac.ic.ad5915.androidrs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doLogin();

        Log.d("debuggggggger", "passed login");

        RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.main_layout);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCameraActivity();
            }
        });
    }

    public void startCameraActivity() {
        Log.d("debuggggggggggggggggggg", "camera baby");
        Intent startCamera = new Intent(this, CameraActivity.class);
        startActivity(startCamera);
    }

    public void doLogin() {
        Intent startLogin = new Intent(this, LoginActivity.class);
        startActivity(startLogin) ;
    }
}
