package pl.edu.pjwstk.pawelkaminski.proj4smb.geolokacja;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class WyswietlDodaneMiejsca extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wyswietl_dodane_miejsca);

        SharedPreferences sP = this.getSharedPreferences("Dane", Context.MODE_PRIVATE);
        Integer iloscElementow = sP.getInt("IloscElementow",0);

        TextView wykaz_dodanych_miejsc = findViewById(R.id.tv_dodane_miejsca);
        String wynik = "\n";

        for(int i = 0; i<=iloscElementow;i++){

            String nazwa = sP.getString("Nazwa"+i,"");
            String opis = sP.getString("Opis"+i,"");
            String szerokosc = sP.getString("Szerokosc"+i,"");
            String wysokosc = sP.getString("Wysokosc"+i,"");

            if(!nazwa.equals("") && !szerokosc.equals("") && !wysokosc.equals("")){
                wynik = wynik + "Nazwa: " + nazwa + "\n";
                wynik = wynik +"Opis: " + opis + "\n";
                wynik = wynik + "Szerokosc: " + szerokosc + "\n";
                wynik = wynik + "Wysokosc: " + wysokosc + "\n"+ "\n";
            }
        }
        wykaz_dodanych_miejsc.setText(wynik);
    }
}