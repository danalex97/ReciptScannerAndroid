package uk.ac.ic.ad5915.androidrs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

public class CameraActivity extends AppCompatActivity {

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        setPath();
        takePicture();
        cropPicture();
    }

    void setPath() {
        path = Environment.getExternalStorageDirectory() + File.separator + "make_machine_example.jpg";
    }

    void takePicture() {
        File file = new File( path );
        Uri outputFileUri = Uri.fromFile(file);

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
        intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );

        startActivityForResult( intent, 0 );
    }

    void cropPicture() {
        
    }

}
