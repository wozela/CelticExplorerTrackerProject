package ie.gmit.celticexplorertracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Elizabeth Wozniak
 */
public class SaveActivity extends Activity {

    private ImageView imgPhoto;
    Bitmap photoFromCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        byte[] byteArray = getIntent().getByteArrayExtra("photoToSave");
        photoFromCamera = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);


        //Bitmap bitmap = (Bitmap) this.getIntent().getParcelableExtra("PhotoToSave");
        imgPhoto = (ImageView)findViewById(R.id.imgPhoto);
        imgPhoto.setImageBitmap(photoFromCamera);
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }

}
