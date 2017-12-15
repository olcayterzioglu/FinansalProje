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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class barkodActivity extends BaseActivity {

    final static String DB_URL = "https://ilac-prospektus.firebaseio.com/";
    Firebase firebase;
    Button button;
    EditText editText;
    ArrayList<String> arrayListAdBarkod = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    //String [][] ilacDetay_Dizi;
    String [][] secilenIlacDetay_Dizi = new String[1][14];

    //int veriAdeti=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barkod);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        button = (Button) findViewById(R.id.button3);
        editText = (EditText) findViewById(R.id.EditTextBarkod);


        //Firebase veri listelemek için
        Firebase.setAndroidContext(this);
        firebase = new Firebase(DB_URL);
        this.retrieveData();
        //Firebase veri listelemek için son
        Toast.makeText(barkodActivity.this, "Barkod numarası giriniz.", Toast.LENGTH_SHORT).show();


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(editText.getText().length()==13) {

                    arrayListAdBarkod.clear();

                    // İlaçları Say
                    for (int z = 0; z < MainActivity.veriAdeti; z++) {
                        // Tüm ilaçların barkod bilgisinde arama yap
                        if (MainActivity.ilacDetay_Dizi[z][2].contains(editText.getText())) {
                            arrayListAdBarkod.add(MainActivity.ilacDetay_Dizi[z][0]);
                        }
                    }

                    //Girilen barkod numarasına ait bir ilaç var ise o ilacların listesini listview a  ekle

                    if (arrayListAdBarkod.size() > 0) {
                        arrayAdapter = new ArrayAdapter(barkodActivity.this, android.R.layout.simple_list_item_1, arrayListAdBarkod);

                        String secilenIlacAdi = arrayAdapter.getItem(0).toString();

                        Intent intent = new Intent(barkodActivity.this, ilacDetay.class).putExtra("from" , "barkod");
                        Bundle mBundle = new Bundle();

                        //arrayList in içinde tüm ilaçların adı var
                        //ilacDetay_Dizi nin içinde tüm ilaçların bilgileri var
                        //seçilen ilacın ismi ile ilacDetay dizisinde aynı olanı bulup secilenIlacDetay_Dizi ye kopyalıyorum
                        for (int k=0; k<MainActivity.veriAdeti; k++){
                            if(MainActivity.ilacDetay_Dizi[k][0].equals(secilenIlacAdi)){
                                for (int j=0; j<14; j++){
                                    secilenIlacDetay_Dizi[0][j] = MainActivity.ilacDetay_Dizi[k][j];
                                }
                            }
                        }

                        mBundle.putSerializable("gonderDizi", secilenIlacDetay_Dizi);
                        intent.putExtras(mBundle);

                        startActivity(intent);



                    } else {

                        Toast.makeText(barkodActivity.this, "Girilen barkoda ait ilaç bulunamadı", Toast.LENGTH_SHORT).show();
                    }
                }
                else{


                    Toast.makeText(barkodActivity.this, "Barkod numarası 13 haneli olmalıdır.", Toast.LENGTH_SHORT).show();
                }
            }
        });




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

        arrayListAdBarkod.clear();

        Ilaclar ilaclar = new Ilaclar();

        for(DataSnapshot data : ds.getChildren()) {

            ilaclar.setAd(data.getValue(Ilaclar.class).getAd());
            ilaclar.setEtken_madde(data.getValue(Ilaclar.class).getEtken_madde());

            arrayListAdBarkod.add(ilaclar.getAd());
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent i = new Intent(barkodActivity.this , MainActivity.class );
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.barkod, menu);
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
