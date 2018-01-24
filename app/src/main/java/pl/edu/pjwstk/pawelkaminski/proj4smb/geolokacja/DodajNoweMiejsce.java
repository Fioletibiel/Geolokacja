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

    public void DodajNoweMiejsce(View view) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Proszę nadaj tej aplikacji uprawnienia GPS.", Toast.LENGTH_LONG).show();
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

        EditText et_nazwa_miejsca = findViewById(R.id.et_nazwa_miejsca) ;
        String nazwa = et_nazwa_miejsca.getText().toString();
        EditText et_opis_miejsca = findViewById(R.id.et_opis_miejsca) ;
        String opis = et_opis_miejsca.getText().toString();

        SharedPreferences sP = this.getSharedPreferences("Dane", Context.MODE_PRIVATE);
        Integer iloscElementow = sP.getInt("IloscElementow",0);
        iloscElementow+=1;

        SharedPreferences.Editor edytor = sP.edit();
        edytor.putInt("IloscElementow",iloscElementow);
        edytor.putString("Szerokosc"+iloscElementow,szerokosc.toString());
        edytor.putString("Wysokosc"+iloscElementow,wysokosc.toString());
        edytor.putString("Nazwa"+iloscElementow,nazwa);
        edytor.putString("Opis"+iloscElementow,opis);
        edytor.commit();

        Toast.makeText(this, "Zostało dodane nowe miejsce: \"" + nazwa + "\"", Toast.LENGTH_LONG).show();
    }
}
