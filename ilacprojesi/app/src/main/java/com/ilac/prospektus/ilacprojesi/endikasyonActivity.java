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

public class endikasyonActivity extends BaseActivity {

    final static String DB_URL = "https://ilac-prospektus.firebaseio.com/";
    Firebase firebase;
    ListView ilacListAd;
    TextView bilgi;
    SearchView searchEndikasyon;
    ArrayList<String> arrayListAd = new ArrayList<>();
    ArrayAdapter arrayAdapter;


    //String [][] ilacDetay_Dizi;
    String [][] secilenIlacDetay_Dizi = new String[1][14];
    //int veriAdeti=0;


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



        /*
        *
        * Searchview alanında yazılan metni ilacların endikasyon bilgisinde ara
        * Eşleşen ilaçların bilgilerini ilacListAd'a ekle
        * */

        searchEndikasyon.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {

                if(text.length()>2) {

                    ilacListAd.clearAnimation();
                    arrayListAd.clear();

                    // İlaçları Say
                    for (int z = 0; z < MainActivity.veriAdeti; z++) {
                        // Tüm ilaçların endikasyon bilgisinde arama yap
                        if (ilacDetay_Dizi[z][7].contains(text)) {
                            arrayListAd.add(ilacDetay_Dizi[z][0]);
                        }
                    }

                    //Girilen endikasyon bilgisini içeren ilaç var ise o ilacların listesini listview a  ekle

                    if (arrayListAd.size() > 0) {
                        arrayAdapter = new ArrayAdapter(endikasyonActivity.this, android.R.layout.simple_list_item_1, arrayListAd);
                        ilacListAd.setAdapter(arrayAdapter);
                        ilacListAd.setVisibility(View.VISIBLE);
                    } else {
                        ilacListAd.setVisibility(View.INVISIBLE);
                        Toast.makeText(endikasyonActivity.this, "Girilen endikasyon bilgisini içeren ilaç bulunamadı", Toast.LENGTH_SHORT).show();
                    }


                    bilgi.setText(text + " " + "içeren ilaçlar");


                }else{

                    bilgi.setText("Endikasyon bilgisi giriniz...");
                    ilacListAd.setVisibility(View.INVISIBLE);

                }

                return false;
            }
        });




        //Listview item tıklandığında
        ilacListAd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //listView da elemanlardan herhangi birine tıklandığında ne yapılmasını istiyorsak buraya kodlayacağız

                String secilenIlacAdi = arrayAdapter.getItem(i).toString();

                Intent intent = new Intent(endikasyonActivity.this, ilacDetay.class).putExtra("from" , "endikasyon");
                Bundle mBundle = new Bundle();


                //arrayList in içinde tüm ilaçların adı var
                //ilacDetay_Dizi nin içinde tüm ilaçların bilgileri var
                //seçilen ilacın ismi ile ilacDetay dizisinde aynı olanı bulup secilenIlacDetay_Dizi ye kopyalıyorum
                for (int k=0; k<MainActivity.veriAdeti; k++){
                    if(ilacDetay_Dizi[k][0].equals(secilenIlacAdi)){
                        for (int j=0; j<14; j++){
                            secilenIlacDetay_Dizi[0][j] = ilacDetay_Dizi[k][j];
                        }
                    }
                }

                mBundle.putSerializable("gonderDizi", secilenIlacDetay_Dizi);
                intent.putExtras(mBundle);

                startActivity(intent);
            }
        });

        //Listview item tıklandığında sonu



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
