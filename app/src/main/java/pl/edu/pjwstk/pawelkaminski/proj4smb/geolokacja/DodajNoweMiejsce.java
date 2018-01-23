package pl.edu.pjwstk.pawelkaminski.proj4smb.geolokacja;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DodajNoweMiejsce extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_nowe_miejsce);
    }

    public void DodajPunkt(View view) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Nadaj aplikacji uprawnienia do GPSu", Toast.LENGTH_LONG).show();
            return;
        }

        LocationManager LocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = LocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationGsm = LocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        Double szerokosc = 0d;
        Double wysokosc = 0d;

        long GPSLocationTime = 0;
        long NetLocationTime = 0;

        if (location != null)
            GPSLocationTime = location.getTime();

        if (locationGsm != null)
            NetLocationTime = locationGsm.getTime();


        if (  GPSLocationTime - NetLocationTime > 0 ) {
            szerokosc = location.getLatitude();
            wysokosc = location.getLongitude();
        }
        else {
            szerokosc = locationGsm.getLatitude();
            wysokosc = locationGsm.getLongitude();
        }

        EditText nazwaEditText = (EditText) findViewById(R.id.Nazwa) ;
        String nazwa = nazwaEditText.getText().toString();

        EditText opisEditText = (EditText) findViewById(R.id.Opis) ;
        String opis = opisEditText.getText().toString();

        SharedPreferences sharedPreferences = this.getSharedPreferences("Dane", Context.MODE_PRIVATE);
        Integer iloscElementow = sharedPreferences.getInt("IloscElementow",0);
        iloscElementow = iloscElementow +1;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("IloscElementow",iloscElementow);
        editor.putString("Szerokosc"+iloscElementow,szerokosc.toString());
        editor.putString("Wysokosc"+iloscElementow,wysokosc.toString());
        editor.putString("Nazwa"+iloscElementow,nazwa);
        editor.putString("Opis"+iloscElementow,opis);

        editor.commit();
        Toast.makeText(this, "Nowy punkt \""+nazwa+"\", został dodany. ", Toast.LENGTH_LONG).show();

    }

}
