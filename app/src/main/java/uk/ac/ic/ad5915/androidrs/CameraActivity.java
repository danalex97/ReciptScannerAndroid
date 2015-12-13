package uk.ac.ic.ad5915.androidrs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.io.File;


public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent intent = new Intent(this, CropNames.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeImageView("names.jpg", R.id.firstImage);
        changeImageView("numbers.jpg", R.id.secondImage);
    }

    private void changeImageView(String path, int id) {
        path = Environment.getExternalStorageDirectory() + File.separator + path;
        File imgFile = new File(path);

        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        ImageView imgView = (ImageView) findViewById(id);

        imgView.setImageBitmap(bitmap);
    }


   public void stop(View view) {
       Log.e("alex", "seen some shit");
       Ion.with(this)
               .load("PUT", "http://73e40815.ngrok.io/api/upload/")
               .setMultipartParameter("username", LoginActivity.loggedInUsername)
               .setMultipartFile("names_file", "image/jpg", new File("/sdcard/numbers.jpg"))
               .setMultipartFile("prices_file", "image/jpg", new File("/sdcard/names.jpg"))
               .asString()
               .withResponse()
               .setCallback(new FutureCallback<Response<String>>() {
                   @Override
                   public void onCompleted(Exception e, Response<String> response) {
                       Log.e("alex", "the response was: " + response.getHeaders().code() + " with string " + response.getResult());
                   }
               });
       Log.e("alex", "did some stuff");
       finish();
    }

    public void redo(View view) {
        Intent intent = new Intent(this, CropNumbers.class);
        startActivity(intent);
    }

    /* ---------------------------------------------------------------------------------


        --------------------------------------------------------------------------------------
     */
}