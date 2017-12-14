package com.ilac.prospektus.ilacprojesi;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class endikasyonActivity extends BaseActivity {

    final static String DB_URL = "https://ilac-prospektus.firebaseio.com/";
    Firebase firebase;
    ListView ilacListAd;
    TextView bilgi;
    SearchView searchEndikasyon;
    ArrayList<String> arrayListAd = new ArrayList<>();
    ArrayAdapter arrayAdapter;


    String [][] ilacDetay_Dizi;
    String [][] secilenIlacDetay_Dizi = new String[1][14];

    int veriAdeti=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endikasyon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        searchEndikasyon =(SearchView) findViewById(R.id.searchEndikasyon);
        ilacListAd = (ListView) findViewById(R.id.ilaclar_ListViewAd);
        bilgi=(TextView) (findViewById(R.id.textviewEndikasyon));

        //textview altı çizili yap
        bilgi.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);



        //Firebase veri listelemek için
        Firebase.setAndroidContext(this);
        firebase = new Firebase(DB_URL);
        this.retrieveData();
        //Firebase veri listelemek için son







        //searchview işlemleri

        searchEndikasyon.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {


                ilacListAd.clearAnimation();


                arrayListAd.clear();

                //arrayAdapter_Madde in içinde tüm ilaçların etken maddeleri var veri tekrarı olmadan

                //Eğer ilaç o etken maddeyi içeriyor ise adı arrayList e eklenecek en son arrayList görüntülenecek

                for (int z=0; z<veriAdeti; z++)
                {
                    if ( ilacDetay_Dizi[z][7].contains(text)) {
                        arrayListAd.add( ilacDetay_Dizi[z][0] );
                    }
                }

//Girilen etken maddeyi içeren ilaç var ise o ilacların listesini listview a  ekle

                if(arrayListAd.size()>0){
                    arrayAdapter = new ArrayAdapter(endikasyonActivity.this, android.R.layout.simple_list_item_1, arrayListAd);
                    ilacListAd.setAdapter(arrayAdapter);
                }else{
                    Toast.makeText(endikasyonActivity.this, "Girilen endikasyon bilgisini içeren ilaç bulunamadı", Toast.LENGTH_SHORT).show();
                }


                bilgi.setText(text+" "+"içeren ilaçlar");

                ilacListAd.setVisibility(View.VISIBLE);


                return false;
            }
        });


        //searchview işlemleri sonu













        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
































    //Listeleme
    private void retrieveData(){
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    private void getUpdates(DataSnapshot ds){

        arrayListAd.clear();

        Ilaclar ilaclar = new Ilaclar();

        for(DataSnapshot data : ds.getChildren()) {

            ilaclar.setAd(data.getValue(Ilaclar.class).getAd());
            ilaclar.setEtken_madde(data.getValue(Ilaclar.class).getEtken_madde());

            arrayListAd.add(ilaclar.getAd());


        }

        veriAdeti = arrayListAd.size();

        //tüm ilaç bilgilerini çekip çok boyutlu dizi içine atıyorum
        ilacDetay_Dizi = new String[veriAdeti][14];
        int art=0;
        for(DataSnapshot data : ds.getChildren()){

            ilaclar.setAd(data.getValue(Ilaclar.class).getAd());
            ilaclar.setFirma_adi(data.getValue(Ilaclar.class).getFirma_adi());
            ilaclar.setBarkod_no(data.getValue(Ilaclar.class).getBarkod_no());
            ilaclar.setFiyat(data.getValue(Ilaclar.class).getFiyat());
            ilaclar.setEtken_madde(data.getValue(Ilaclar.class).getEtken_madde());

            ilaclar.setFormul(data.getValue(Ilaclar.class).getFormul());
            ilaclar.setFarmokolojik_ozellik(data.getValue(Ilaclar.class).getFarmokolojik_ozellik());
            ilaclar.setEndikasyonlar(data.getValue(Ilaclar.class).getEndikasyonlar());
            ilaclar.setKontrendikasyonlar(data.getValue(Ilaclar.class).getKontrendikasyonlar());
            ilaclar.setUyarilar(data.getValue(Ilaclar.class).getUyarilar());

            ilaclar.setYan_etkiler(data.getValue(Ilaclar.class).getYan_etkiler());
            ilaclar.setEtkilesimler(data.getValue(Ilaclar.class).getEtkilesimler());
            ilaclar.setKullanim_sekli(data.getValue(Ilaclar.class).getKullanim_sekli());
            ilaclar.setDoz_asimi(data.getValue(Ilaclar.class).getDoz_asimi());


            ilacDetay_Dizi[art][0]=ilaclar.getAd();
            ilacDetay_Dizi[art][1]=ilaclar.getFirma_adi();
            ilacDetay_Dizi[art][2]=ilaclar.getBarkod_no();
            ilacDetay_Dizi[art][3]=ilaclar.getFiyat();
            ilacDetay_Dizi[art][4]=ilaclar.getEtken_madde();

            ilacDetay_Dizi[art][5]=ilaclar.getFormul();
            ilacDetay_Dizi[art][6]=ilaclar.getFarmokolojik_ozellik();
            ilacDetay_Dizi[art][7]=ilaclar.getEndikasyonlar();
            ilacDetay_Dizi[art][8]=ilaclar.getKontrendikasyonlar();
            ilacDetay_Dizi[art][9]=ilaclar.getUyarilar();

            ilacDetay_Dizi[art][10]=ilaclar.getYan_etkiler();
            ilacDetay_Dizi[art][11]=ilaclar.getEtkilesimler();
            ilacDetay_Dizi[art][12]=ilaclar.getKullanim_sekli();
            ilacDetay_Dizi[art][13]=ilaclar.getDoz_asimi();

            //liste bitince çıkması için

            art = art + 1;
            if (art == veriAdeti){
                break;
            }
        }

        //if(arrayList.size()>0){
        //arrayAdapter = new ArrayAdapter(etkenmaddeActivity.this, android.R.layout.simple_list_item_1, arrayList);
        // ilacList.setAdapter(arrayAdapter);
        //}else{
        // Toast.makeText(etkenmaddeActivity.this, "Veri Yok", Toast.LENGTH_SHORT).show();
        //  }



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent i = new Intent(endikasyonActivity.this , MainActivity.class );
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.endikasyon, menu);
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
