package com.ilac.prospektus.ilacprojesi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

//    final static String DB_URL = "https://ilac-prospektus.firebaseio.com/";
//    Firebase firebase;

    private String ilacAdi;
//    ListView ilacList;
//    ProgressDialog pd;
//    ArrayList<String> arrayList = new ArrayList<>();
//    ArrayAdapter arrayAdapter;

    //static String [][] ilacDetay_Dizi;
    static String [][] secilenIlacDetay_Dizi = new String[1][14];
    //static int veriAdeti=0;

    //Search
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Main search için  işlemler
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Arama Yap");


//        //Firebase veri listelemek için
//        Firebase.setAndroidContext(this);
//        firebase = new Firebase(DB_URL);
        ilacAdListesi = (ListView) findViewById(R.id.ilaclar_ListView);

        arrayIlacAdList.clear();
        for(int r=0; r<veriAdeti; r++){
            arrayIlacAdList.add(ilacDetay_Dizi[r][0]);
        }
        arrayListAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrayIlacAdList);
        ilacAdListesi.setAdapter(arrayListAdapter);


        //search
        searchView=(SearchView) findViewById(R.id.searchView1);


       // this.retrieveData();
        //Firebase veri listelemek için son

        //search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                arrayListAdapter.getFilter().filter(text);

                return false;
            }
        });


        ilacAdListesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //listView da elemanlardan herhangi birine tıklandığında ne yapılmasını istiyorsak buraya kodlayacağız

                String secilenIlacAdi = arrayListAdapter.getItem(i).toString();

                Intent intent = new Intent(MainActivity.this, ilacDetay.class).putExtra("from" , "main");
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
                //finish();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Firebase veri ekleme
//        Ilaclar ilaclar = new Ilaclar("Olcay3", "Bilim İlaç Sanayi ve Ticaret A.Ş.", "8699569090717", "13,86 TL", "Parasetamol + Kafein","Her kontrollü salımlı film tablette, 50 mg - 100 mg metoprolol tartarata eşdeğer 47.5 mg metoprolol suksinat bulunur.Boyar madde: Titanyum dioksit" , "", "Ağırlık hissi, ağrı ve gece gelen kramp gibi alt ekstremitelerin fonksiyonel ve organik kronik venöz yetersizliğine ait belirtilerin tedavisi ile hemoroid krizlerinin fonksiyonel belirtilerinin tedavisinde endikedir.","Formül bileşenlerinden herhangi birine karşı aşırı duyarlılık hallerinde kullanılmamalıdır.","Gebelik ve laktasyonda kullanılmamalıdır.Süt veren annelerde kullanılmamalıdır.","Bulantı ve kusma tarzında nonspesifik sindirimsel ve terleme gibi nörovejetatif.","" ,"Venöz yetersizliklerde: 1x2 tablet, Hemoroid krizlerinde: ilk 4 gün 6 tablet/gün, takibeden 3 gün 4 tablet/gün uygulanır. Sonraki günlerde 2 tabletle devam edilir." , "");
//
//        databaseReference.child("ilaclar").child(ilaclar.getAd()).setValue(ilaclar , new DatabaseReference.CompletionListener() {
//
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if(databaseError != null)
//                {
//                    Toast toast = Toast.makeText(getApplicationContext(), "Kaydedildi", Toast.LENGTH_LONG);
//                    toast.show();
//                }
//            }
//        } );

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
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
