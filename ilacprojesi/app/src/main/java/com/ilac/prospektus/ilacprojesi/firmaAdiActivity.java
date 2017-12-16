package com.ilac.prospektus.ilacprojesi;

import android.content.Intent;
import android.graphics.Paint;
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
import android.widget.AdapterView;
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

public class firmaAdiActivity extends BaseActivity {

    final static String DB_URL = "https://ilac-prospektus.firebaseio.com/";
    Firebase firebase;
    ListView ilacListAd,ilacListFirma;
    TextView bilgi;
    SearchView searchViewFirma;
    ArrayList<String> arrayListAd = new ArrayList<>();
    ArrayList<String> arrayList_FirmaAd = new ArrayList<>();
    ArrayAdapter arrayAdapter,arrayAdapterFirma;


    String [][] secilenIlacDetay_Dizi = new String[1][14];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma_adi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchViewFirma =(SearchView) findViewById(R.id.searchFirma);
        ilacListFirma = (ListView) findViewById(R.id.ilaclar_ListViewFirma);
        bilgi=(TextView) (findViewById(R.id.textviewFirma));
        ilacListAd = (ListView) findViewById(R.id.ilaclar_ListViewFirmailac);

        //textview altı çizili yap
        bilgi.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


        //Firebase veri listelemek için
        Firebase.setAndroidContext(this);
        firebase = new Firebase(DB_URL);

        this.retrieveData();
        //Firebase veri listelemek için son


        //searchview işlemleri

        searchViewFirma.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {

                arrayAdapterFirma.getFilter().filter(text);
                ilacListFirma.setVisibility(View.VISIBLE);
                bilgi.setVisibility(View.INVISIBLE);
                ilacListAd.setVisibility(View.INVISIBLE);

                return false;
            }
        });

        //searchview işlemleri sonu


        //Listview item tıklandığında
        ilacListAd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //listView da elemanlardan herhangi birine tıklandığında ne yapılmasını istiyorsak buraya kodlayacağız

                String secilenIlacAdi = arrayAdapter.getItem(i).toString();

                Intent intent = new Intent(firmaAdiActivity.this, ilacDetay.class).putExtra("from" , "firma");
                Bundle mBundle = new Bundle();


                //arrayList in içinde tüm ilaçların adı var
                //ilacDetay_Dizi nin içinde tüm ilaçların bilgileri var
                //seçilen ilacın ismi ile ilacDetay dizisinde aynı olanı bulup secilenIlacDetay_Dizi ye kopyalıyorum
                for (int k=0; k<veriAdeti; k++){
                    if(ilacDetay_Dizi[k][0].equals(secilenIlacAdi)){
                        for (int j=0; j<14; j++){
                            secilenIlacDetay_Dizi[0][j] = ilacDetay_Dizi[k][j];
                        }
                    }
                }

                mBundle.putSerializable("gonderDizi", secilenIlacDetay_Dizi);
                intent.putExtras(mBundle);

                startActivity(intent);
                finish();
            }
        });

        //Listview item tıklandığında sonu




        //ListviewFirma item tıklandığında

        ilacListFirma.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ilacListAd.clearAnimation();

                String secilenFirma = arrayAdapterFirma.getItem(i).toString();

                arrayListAd.clear();

                //arrayAdapter_Madde in içinde tüm ilaçların etken maddeleri var veri tekrarı olmadan

                //Eğer ilaç o etken maddeyi içeriyor ise adı arrayList e eklenecek en son arrayList görüntülenecek

                for (int z=0; z<veriAdeti; z++)
                {
                    if (ilacDetay_Dizi[z][1].contains(secilenFirma)) {
                        arrayListAd.add( ilacDetay_Dizi[z][0] );
                    }
                }

                //Girilen firmanın ilaçlarını listele

                if(arrayListAd.size()>0){
                    arrayAdapter = new ArrayAdapter(firmaAdiActivity.this, android.R.layout.simple_list_item_1, arrayListAd);
                    ilacListAd.setAdapter(arrayAdapter);
                }else{
                    Toast.makeText(firmaAdiActivity.this, "Girilen etken maddeyi içeren ilaç bulunamadı", Toast.LENGTH_SHORT).show();
                }

                bilgi.setText(secilenFirma+" "+"Firmasının ilaçları");
                bilgi.setVisibility(View.VISIBLE);
                ilacListAd.setVisibility(View.VISIBLE);
                ilacListFirma.setVisibility(View.INVISIBLE);

            }

        });

        //ListviewMadde item tıklandığında sonu


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
        arrayList_FirmaAd.clear();


        Ilaclar ilaclar = new Ilaclar();

        for(DataSnapshot data : ds.getChildren()) {

            ilaclar.setAd(data.getValue(Ilaclar.class).getAd());
            ilaclar.setFirma_adi(data.getValue(Ilaclar.class).getFirma_adi());

            arrayListAd.add(ilaclar.getAd());

            // firmaları adaptere doldurdum

            if (arrayList_FirmaAd.contains(ilaclar.getFirma_adi()) == false)
            {
                arrayList_FirmaAd.add((ilaclar.getFirma_adi()));


            }
        }

        // firma sayısı sıfır değilse  ilacliste yukledim

        if(arrayList_FirmaAd.size()>0){
            arrayAdapterFirma= new ArrayAdapter(firmaAdiActivity.this, android.R.layout.simple_list_item_1, arrayList_FirmaAd);
            ilacListFirma.setAdapter(arrayAdapterFirma);

        }else{
            Toast.makeText(firmaAdiActivity.this, "Firmalar yüklenemedi", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent i = new Intent(firmaAdiActivity.this , MainActivity.class );
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.firma_adi, menu);
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
