package pl.edu.pjwstk.pawelkaminski.proj4smb.geolokacja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeneraws or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Nadaj aplikacji uprawnienia do GPSu", Toast.LENGTH_LONG).show();
            return;
        }

        // Add a marker in Sydney and move the camera
        LatLng Warsaw = new LatLng(-52.237049, 21.017532);
        mMap.addMarker(new MarkerOptions().position(Warsaw).title("Marker in Warsaw"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Warsaw));

        mMap.setMyLocationEnabled(true);

        SharedPreferences sharedPreferences = this.getSharedPreferences("Dane", Context.MODE_PRIVATE);
        Integer iloscElementow = sharedPreferences.getInt("IloscElementow",0);

        for(int i = 0; i<=iloscElementow;i++){

            String nazwa = sharedPreferences.getString("Nazwa"+i,"");
            String opis = sharedPreferences.getString("Opis"+i,"");
            String szerokosc = sharedPreferences.getString("Szerokosc"+i,"");
            String wysokosc = sharedPreferences.getString("Wysokosc"+i,"");

            if(!nazwa.equals("") && !szerokosc.equals("") && !wysokosc.equals("")){
                LatLng pozycja = new LatLng(Double.parseDouble(szerokosc), Double.parseDouble(wysokosc));
                mMap.addMarker(new MarkerOptions().position(pozycja).title(nazwa)).setSnippet(opis);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pozycja));
            }
        }

    }

    public void DodajNoweMiejsce(View view){
        Intent intent = new Intent(this, DodajNoweMiejsce.class);
        startActivity(intent);
    }

    public void WyswietlDodaneMiejsca(View view) {
        Intent intent = new Intent(this, WyswietlDodaneMiejsca.class);
        startActivity(intent);
    }
}
