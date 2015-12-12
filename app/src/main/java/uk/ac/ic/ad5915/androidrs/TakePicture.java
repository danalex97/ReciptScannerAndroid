package uk.ac.ic.ad5915.androidrs;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class TakePicture extends AppCompatActivity {

    private static final int PIC_TAKE = 1;

    private Uri takeUri;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        takePicture();

        Intent startTakePicture = new Intent(this, CropNames.class);
        startActivity(startTakePicture);

        finish();
    }

    private void takePicture() {
        path = Environment.getExternalStorageDirectory() + File.separator + "picture.jpg";

        File file = new File( path );
        takeUri = Uri.fromFile(file);

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
        intent.putExtra(MediaStore.EXTRA_OUTPUT, takeUri);

        startActivityForResult(intent, PIC_TAKE);
    }
}
