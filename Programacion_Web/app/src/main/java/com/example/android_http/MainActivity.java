package com.example.android_http;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirActivities(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btn_altas:
                i=new Intent(this,ActivityAltas.class);
                startActivity(i);
                break;
            case R.id.btn_bajas_cambios:
                i=new Intent(this,ActivityBajas.class);
                startActivity(i);
                break;
            case R.id.btn_consultas:
                i=new Intent(this,ActivityConsultas.class);
                startActivity(i);
                break;


        }

    }


}
