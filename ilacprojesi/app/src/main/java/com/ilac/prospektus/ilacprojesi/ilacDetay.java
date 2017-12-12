package com.ilac.prospektus.ilacprojesi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ilacDetay extends BaseActivity{

    String gelenAd, gelenEtkenMadde;

    String [][] gelenIlacDetay_Dizi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilac_detay);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Gelen veri çek
        Intent intent = getIntent();
        gelenAd = intent.getStringExtra("SecilenIlacAdi");
        gelenEtkenMadde = intent.getStringExtra("SecilenIlacEtkenMadde");

        Bundle b = getIntent().getExtras();
        gelenIlacDetay_Dizi = (String[][])b.getSerializable("gonderDizi");

        final TextView detay = (TextView)findViewById(R.id.textView);
        final TextView detay1 = (TextView)findViewById(R.id.textView1);
        final TextView detay2 = (TextView)findViewById(R.id.textView2);
        final TextView detay3 = (TextView)findViewById(R.id.textView3);
        final TextView detay4 = (TextView)findViewById(R.id.textView4);
        final TextView detay5 = (TextView)findViewById(R.id.textView5);
        final TextView detay6 = (TextView)findViewById(R.id.textView6);
        final TextView detay7 = (TextView)findViewById(R.id.textView7);
        final TextView detay8 = (TextView)findViewById(R.id.textView8);
        final TextView detay9 = (TextView)findViewById(R.id.textView9);
        final TextView detay10 = (TextView)findViewById(R.id.textView10);
        final TextView detay11= (TextView)findViewById(R.id.textView11);
        final TextView detay12 = (TextView)findViewById(R.id.textView12);
        final TextView detay13 = (TextView)findViewById(R.id.textView13);

        ;



        //final Ilaclar ilaclar = new Ilaclar(this);
//        final TextView ad = (TextView)findViewById(R.id.ilacDetay_textView);
//        final TextView etkenMadde = (TextView)findViewById(R.id.ilacEtkenMadde_textView);

        //set data
//        ad.setText(gelenAd);
//        etkenMadde.setText(gelenEtkenMadde);


        detay.setText("AD: \n" + gelenIlacDetay_Dizi[0][0]);
        detay1.setText("FİRMA ADI: \n" + gelenIlacDetay_Dizi[0][1]);
        detay2.setText("BARKOD NO: \n" + gelenIlacDetay_Dizi[0][2]);
        detay3.setText("FİYAT: \n" + gelenIlacDetay_Dizi[0][3]);
        detay4.setText("ETKEN MADDE: \n" + gelenIlacDetay_Dizi[0][4]);
        detay5.setText("FORMÜL: \n" + gelenIlacDetay_Dizi[0][5]);
        detay6.setText("FARMOKOLOJİK ÖZELLİKLER:\n " + gelenIlacDetay_Dizi[0][6]);
        detay7.setText("ENDİKASYONLAR: \n" + gelenIlacDetay_Dizi[0][7]);
        detay8.setText("KONTENDİKASYONLAR: \n" + gelenIlacDetay_Dizi[0][8]);
        detay9.setText("UYARILAR: \n" + gelenIlacDetay_Dizi[0][9]);
        detay10.setText("YAN ETKİLERİ: \n" + gelenIlacDetay_Dizi[0][10]);
        detay11.setText("ETKİLEŞİMLER: \n" + gelenIlacDetay_Dizi[0][11]);
        detay12.setText("KULLANIM ŞEKLİ: \n" + gelenIlacDetay_Dizi[0][12]);
        detay13.setText("DOZ AŞIMI: \n" + gelenIlacDetay_Dizi[0][13]);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent i = new Intent(ilacDetay.this , MainActivity.class );
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ilac_detay, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}
