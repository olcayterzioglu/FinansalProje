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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;


public class etkenmaddeActivity extends BaseActivity{

    final static String DB_URL = "https://ilac-prospektus.firebaseio.com/";
    Firebase firebase;
    ListView ilacList;
    Button etkenSearchButon;
    EditText etkenGir;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayList_EtkenMadde = new ArrayList<>();
    ArrayAdapter arrayAdapter, arrayAdapter_EtkenMadde;


    String [][] ilacDetay_Dizi;
    String [][] secilenIlacDetay_Dizi = new String[1][14];

    int veriAdeti=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkenmadde);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        etkenGir= (EditText)(findViewById(R.id.txtArama1)) ;
        ilacList = (ListView) findViewById(R.id.ilaclar_ListView);
        etkenSearchButon = (Button) findViewById(R.id.araEtkenButon);

        //Firebase veri listelemek için
        Firebase.setAndroidContext(this);
        firebase = new Firebase(DB_URL);
        ilacList = (ListView) findViewById(R.id.ilaclar_ListView);
        this.retrieveData();
        //Firebase veri listelemek için son



//Search butonuna tıklanıldığında yapılacaklar

        etkenSearchButon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v ){


                arrayList.clear();

                //arrayAdapter_EtkenMadde in içinde tüm ilaçların etken maddeleri var

                //Girilen etken maddeye göre ilaç etken maddelerini filtreledim

                //Eğer ilaç o etken maddeyi içeriyor ise adı arrayListe eklenecek en son arrayList görüntülenecek


                arrayAdapter_EtkenMadde.getFilter().filter(etkenGir.getText());


               for (int k=0; k<arrayAdapter_EtkenMadde.getCount(); k++){

                   String secilenEtkenMadde = arrayAdapter_EtkenMadde.getItem(k).toString();

                    for (int i=0; i<veriAdeti; i++)
                    {

                       if (ilacDetay_Dizi[i][4].equals(secilenEtkenMadde)) {


                               arrayList.add( ilacDetay_Dizi[i][0] );


                       }

                    }


               }

//Girilen etken maddeyi içeren ilaç var ise o ilacın adını listeye ekle

                if(arrayList.size()>0){
                arrayAdapter = new ArrayAdapter(etkenmaddeActivity.this, android.R.layout.simple_list_item_1, arrayList);
                 ilacList.setAdapter(arrayAdapter);
                }else{
                 Toast.makeText(etkenmaddeActivity.this, "Girilen etken maddeyi içeren ilaç bulunamadı", Toast.LENGTH_SHORT).show();
                 }

            }
        });

//Search butonuna tıklanıldığında yapılacaklar sonu






        //Listview item tıklandığında







        //Listview item tıklandığında sonu
        









        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

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


        arrayList.clear();
        Ilaclar ilaclar = new Ilaclar();

        for(DataSnapshot data : ds.getChildren()) {

            ilaclar.setAd(data.getValue(Ilaclar.class).getAd());
            ilaclar.setEtken_madde(data.getValue(Ilaclar.class).getEtken_madde());

            arrayList.add(ilaclar.getAd());


            //İlaçların etken maddelerini arrayList_EtkenMadde ye ekliyorum
            //Aynı etken maddeden var ise ekleme yapmayacak


            if (arrayList_EtkenMadde.contains(ilaclar.getEtken_madde()) == false)

            {

            arrayList_EtkenMadde.add((ilaclar.getEtken_madde()));

            }

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




        //if(arrayList.size()>0){
            //arrayAdapter = new ArrayAdapter(etkenmaddeActivity.this, android.R.layout.simple_list_item_1, arrayList);
           // ilacList.setAdapter(arrayAdapter);
        //}else{
           // Toast.makeText(etkenmaddeActivity.this, "Veri Yok", Toast.LENGTH_SHORT).show();
      //  }


        //arrayAdapter_EtkenMadde ye arrayLis_EtkenMaddeyi doldruruyorum

        if(arrayList_EtkenMadde.size()>0){
            arrayAdapter_EtkenMadde= new ArrayAdapter(etkenmaddeActivity.this, android.R.layout.simple_list_item_1, arrayList_EtkenMadde);
            //ilacList.setAdapter(arrayAdapter_EtkenMadde);

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
