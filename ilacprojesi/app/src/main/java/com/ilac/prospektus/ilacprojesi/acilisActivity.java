package com.ilac.prospektus.ilacprojesi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class acilisActivity extends BaseActivity {

    final static String DB_URL = "https://ilac-prospektus.firebaseio.com/";
    Firebase firebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acilis);

        //Firebase veri listelemek için
        Firebase.setAndroidContext(this);
        firebase = new Firebase(DB_URL);



        Thread myThread = new Thread(){
            @Override
            public void run(){
                try{

                    retrieveData();
                    sleep( 1500);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();

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

        //arrayList.clear();

        Ilaclar ilaclar = new Ilaclar();

        if(veriAdeti == 0){
            for(DataSnapshot data : ds.getChildren()){

                ilaclar.setAd(data.getValue(Ilaclar.class).getAd());
                ilaclar.setEtken_madde(data.getValue(Ilaclar.class).getEtken_madde());

                arrayIlacAdList.add(ilaclar.getAd());
            }

            veriAdeti = arrayIlacAdList.size();
        }
        else{
            for(int r=0; r<veriAdeti; r++){
                arrayIlacAdList.add(ilacDetay_Dizi[r][0]);
            }
        }

        //Eğer liste boş ise verileri çek boş değilse verileri boşuna çekme
        if(ilacDetay_Dizi==null){


            int art=0;

            ilacDetay_Dizi = new String[veriAdeti][14];

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
        }

        if(arrayIlacAdList.size()>0){
            arrayListAdapter = new ArrayAdapter(acilisActivity.this, android.R.layout.simple_list_item_1, arrayIlacAdList);
            ilacAdListesi.setAdapter(arrayListAdapter);
        }
    }



}
