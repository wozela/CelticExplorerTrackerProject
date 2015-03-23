package ie.gmit.celticexplorertracker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.security.Provider;

import ie.gmit.celticexplorertracker.R;




public class MainActivity extends ActionBarActivity implements LocationListener {

    private static final int CAMERA_CODE=1;
    private Bitmap photoCamera;
    private ImageView imgCamera;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private TextView lblLongitudeValue;
    private TextView lblLatitudeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference to image view to display photo from camera
        imgCamera = (ImageView)findViewById(R.id.imgCamera);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        lblLongitudeValue = (TextView) findViewById(R.id.lblLongitudeValue);
        lblLatitudeValue = (TextView)findViewById(R.id.lblLatitudeValue);



    }

    public void onTakePhoto (View v){
        //invoke camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_CODE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //take photo from the camera and display in ImageView
        if(requestCode == CAMERA_CODE)
        {
            if(resultCode==RESULT_OK) {
                photoCamera = (Bitmap) data.getExtras().get("data");
                imgCamera.setImageBitmap(photoCamera);
            }
        }
    }

    //passing photo to Save screen
    public void onSavePhoto (View v){
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        photoCamera.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] byteArray = bStream.toByteArray();

        Intent photoToSaveIntent = new Intent(this, SaveActivity.class);
        photoToSaveIntent.putExtra("photoToSave", byteArray);
        MainActivity.this.startActivity(photoToSaveIntent);
        //finish();


//        Intent photoToSaveIntent = new Intent(this, SaveActivity.class);
//        photoToSaveIntent.putExtra("PhotoToSave", photoCamera);
//        startActivity(photoToSaveIntent);
    }

    public void onNext (View v){
        Intent nextIntent = new Intent(this, DecisionTreeActivity.class);
        MainActivity.this.startActivity(nextIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //subscribe to location service
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        if (locationManager != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,600,0,this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        //update user interface
        lblLongitudeValue.setText(" " + longitude);
        lblLatitudeValue.setText(" " + latitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
