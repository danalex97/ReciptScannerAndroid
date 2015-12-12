package uk.ac.ic.ad5915.androidrs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

public class CropNumbers extends AppCompatActivity {

    private static final int PIC_CROP = 2;

    private Uri takeUri;
    private Uri cropUri;
    private String from;
    private String to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_numbers);

        cropPicture();
        finish();
    }

    private void cropPicture() {
        from = Environment.getExternalStorageDirectory() + File.separator + "picture.jpg";
        to = Environment.getExternalStorageDirectory() + File.separator + "numbers.jpg";

        takeUri = Uri.fromFile( new File(from) );
        cropUri = Uri.fromFile( new File(to) );

        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(takeUri, "image/*");
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);

        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("return-data", cropUri);

        startActivityForResult(cropIntent, PIC_CROP);
    }
}
