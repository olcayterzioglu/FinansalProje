package com.ilac.prospektus.ilacprojesi;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;



public class etkenmaddeActivity extends BaseActivity{

    final static String DB_URL = "https://ilac-prospektus.firebaseio.com/";
    Firebase firebase;
    ListView ilacList,ilacListMadde;
    TextView bilgi;
    SearchView searchViewMadde;
    ArrayList<String> arrayListAd = new ArrayList<>();

    ArrayList<String> arrayList_Madde = new ArrayList<>();
    ArrayList<String> arrayList_EtkenMadde = new ArrayList<>();
    ArrayAdapter arrayAdapter, arrayAdapter_EtkenMadde,arrayAdapter_Madde;

    String [][] secilenIlacDetay_Dizi = new String[1][14];


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkenmadde);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchViewMadde =(SearchView) findViewById(R.id.searchView1);
        ilacListMadde = (ListView) findViewById(R.id.ilaclar_ListViewMadde);
        bilgi=(TextView) (findViewById(R.id.textviewMadde));

        //textview altı çizili yap
        bilgi.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


        //Firebase veri listelemek için
        Firebase.setAndroidContext(this);
        firebase = new Firebase(DB_URL);
        ilacList = (ListView) findViewById(R.id.ilaclar_ListView);
        this.retrieveData();
        //Firebase veri listelemek için son


        //ListviewMadde item tıklandığında

        ilacListMadde.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ilacList.clearAnimation();

                String secilenMadde = arrayAdapter_Madde.getItem(i).toString();

                arrayListAd.clear();

                //arrayAdapter_Madde in içinde tüm ilaçların etken maddeleri var veri tekrarı olmadan

                //Eğer ilaç o etken maddeyi içeriyor ise adı arrayList e eklenecek en son arrayList görüntülenecek

                    for (int z=0; z<veriAdeti; z++)
                    {
                        if ( ilacDetay_Dizi[z][4].contains(secilenMadde)) {
                            arrayListAd.add( ilacDetay_Dizi[z][0] );
                        }
                    }

//Girilen etken maddeyi içeren ilaç var ise o ilacların listesini listview a  ekle

                if(arrayListAd.size()>0){
                    arrayAdapter = new ArrayAdapter(etkenmaddeActivity.this, android.R.layout.simple_list_item_1, arrayListAd);
                    ilacList.setAdapter(arrayAdapter);
                    bilgi.setText(secilenMadde+" "+"içeren ilaçlar");
                    bilgi.setVisibility(View.VISIBLE);
                    ilacList.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(etkenmaddeActivity.this, "Girilen etken maddeyi içeren ilaç bulunamadı", Toast.LENGTH_SHORT).show();
                }


                ilacListMadde.setVisibility(View.INVISIBLE);

            }

        });

        //ListviewMadde item tıklandığında sonu


        //Listview item tıklandığında
        ilacList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //listView da elemanlardan herhangi birine tıklandığında ne yapılmasını istiyorsak buraya kodlayacağız

                String secilenIlacAdi = arrayAdapter.getItem(i).toString();

                Intent intent = new Intent(etkenmaddeActivity.this, ilacDetay.class).putExtra("from" , "etkenmadde");
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




        //searchview işlemleri

        searchViewMadde.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {

                arrayAdapter_Madde.getFilter().filter(text);
                ilacListMadde.setVisibility(View.VISIBLE);
                bilgi.setVisibility(View.INVISIBLE);
                ilacList.setVisibility(View.INVISIBLE);

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
        arrayList_Madde.clear();
        arrayList_EtkenMadde.clear();

        Ilaclar ilaclar = new Ilaclar();

        for(DataSnapshot data : ds.getChildren()) {

            ilaclar.setAd(data.getValue(Ilaclar.class).getAd());
            ilaclar.setEtken_madde(data.getValue(Ilaclar.class).getEtken_madde());

            arrayListAd.add(ilaclar.getAd());

            //İlaçların etken maddelerini arrayList_EtkenMadde ye ekliyorum
            //Aynı etken maddeden var ise ekleme yapmayacak
            if (arrayList_EtkenMadde.contains(ilaclar.getEtken_madde()) == false)
            {
                arrayList_EtkenMadde.add((ilaclar.getEtken_madde()));

                //Etken maddeleri ayrıştırarak veri tekrarını yok edip etken madde listesi oluşturdum

                String maddeler = ilaclar.getEtken_madde().toString();
                String[] parts = maddeler.split(",");

                for (int i=0; i<parts.length; i++)
                {
                   if( arrayList_Madde.contains(parts[i])==false)
                   {
                       arrayList_Madde.add(parts[i]);
                   }
                }
            }
        }

        //arrayAdapter_EtkenMadde ye arrayLis_EtkenMaddeyi doldruruyorum

        if(arrayList_EtkenMadde.size()>0){
            arrayAdapter_EtkenMadde= new ArrayAdapter(etkenmaddeActivity.this, android.R.layout.simple_list_item_1, arrayList_EtkenMadde);
            //ilacList.setAdapter(arrayAdapter_EtkenMadde);

        }else{
            Toast.makeText(etkenmaddeActivity.this, "Etken Madde Yok", Toast.LENGTH_SHORT).show();
        }


        if(arrayList_Madde.size()>0){
            arrayAdapter_Madde= new ArrayAdapter(etkenmaddeActivity.this, android.R.layout.simple_list_item_1, arrayList_Madde);
            ilacListMadde.setAdapter(arrayAdapter_Madde);

        }else{
            Toast.makeText(etkenmaddeActivity.this, "Etken Madde Yok", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent i = new Intent(etkenmaddeActivity.this , MainActivity.class );
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.etkenmadde, menu);
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
