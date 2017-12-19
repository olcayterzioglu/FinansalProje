package com.ilac.prospektus.ilacprojesi;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Lenovo on 7.12.2017.
 */

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    static String [][] ilacDetay_Dizi = null;
    static int veriAdeti=0;

    static ListView ilacAdListesi;
    static ArrayList<String> arrayIlacAdList = new ArrayList<>();
    static ArrayAdapter arrayListAdapter;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_anasayfa) {
            Intent i = new Intent(BaseActivity.this , MainActivity.class );
            startActivity(i);
            finish();
        }
        else if (id == R.id.nav_etkenMaddeIlac) {
            Intent h = new Intent(BaseActivity.this , etkenmaddeActivity.class );
            startActivity(h);
            finish();
        } else if (id == R.id.nav_endikasyonBilgiIlac) {
            Intent j = new Intent(BaseActivity.this , endikasyonActivity.class );
            startActivity(j);
            finish();

        } else if (id == R.id.nav_barkod) {
            Intent k = new Intent(BaseActivity.this , barkodActivity.class );
            startActivity(k);
            finish();

        } else if (id == R.id.nav_firmaAdi) {
            Intent o = new Intent(BaseActivity.this , firmaAdiActivity.class );
            startActivity(o);
            finish();

        }else if (id == R.id.nav_hakkimizda) {
            Intent o = new Intent(BaseActivity.this , hakkimizdaActivity.class );
            startActivity(o);
            finish();

        }else if (id == R.id.nav_cikis) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
