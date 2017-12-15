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

    final static String DB_URL = "https://ilac-prospektus.firebaseio.com/";
    Firebase firebase;

    private String ilacAdi;
    ListView ilacList;
    ProgressDialog pd;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayList_EtkenMadde = new ArrayList<>();
    ArrayAdapter arrayAdapter, arrayAdapter_EtkenMadde;

    static String [][] ilacDetay_Dizi;
    static String [][] secilenIlacDetay_Dizi = new String[1][14];
    static int veriAdeti=0;

    //Search
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pd = new ProgressDialog(MainActivity.this);

        pd.setMessage("İlaçlar yükleniyor..");

        pd.show();

        //Main search için  işlemler
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Arama Yap");


        //Firebase veri listelemek için
        Firebase.setAndroidContext(this);
        firebase = new Firebase(DB_URL);
        ilacList = (ListView) findViewById(R.id.ilaclar_ListView);


        //search
        searchView=(SearchView) findViewById(R.id.searchView1);




        this.retrieveData();
        //Firebase veri listelemek için son




        //search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                arrayAdapter.getFilter().filter(text);

                return false;
            }
        });


        ilacList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //listView da elemanlardan herhangi birine tıklandığında ne yapılmasını istiyorsak buraya kodlayacağız

                String secilenIlacAdi = arrayAdapter.getItem(i).toString();

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

        arrayList.clear();
        Ilaclar ilaclar = new Ilaclar();

        for(DataSnapshot data : ds.getChildren()){

            ilaclar.setAd(data.getValue(Ilaclar.class).getAd());
            ilaclar.setEtken_madde(data.getValue(Ilaclar.class).getEtken_madde());

            arrayList.add(ilaclar.getAd());
            arrayList_EtkenMadde.add((ilaclar.getEtken_madde()));
        }
        veriAdeti = arrayList.size();
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

        if(arrayList.size()>0){
            arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
            ilacList.setAdapter(arrayAdapter);
            pd.dismiss();
        }else{
            Toast.makeText(MainActivity.this, "Veri Yok", Toast.LENGTH_SHORT).show();
        }
        if(arrayList_EtkenMadde.size()>0){
            arrayAdapter_EtkenMadde= new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrayList_EtkenMadde);

        }else{
            Toast.makeText(MainActivity.this, "Etken Madde Yok", Toast.LENGTH_SHORT).show();
        }
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
