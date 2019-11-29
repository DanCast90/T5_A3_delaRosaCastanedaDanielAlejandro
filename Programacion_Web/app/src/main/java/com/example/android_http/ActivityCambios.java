package com.example.android_http;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class ActivityCambios extends Activity {
    Spinner spinnerEdadC,spinnerSemC,spinnerCarrC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambios);

        spinnerEdadC=(Spinner) findViewById(R.id.sp_edad_cambios);
        spinnerEdadC.setAdapter(llenarSpinnerEdad());

        spinnerSemC=(Spinner) findViewById(R.id.sp_semestre_cambios);
        spinnerSemC.setAdapter(llenarSpinnerSemestre());

        spinnerCarrC=(Spinner)findViewById(R.id.sp_carrera_cambios);
        spinnerCarrC.setAdapter(llenarSpinnerCarrera());

    }



    public ArrayAdapter llenarSpinnerEdad(){
        String datos[]=new String[100];
        for (int i=0;i<100;i++){
            int aux=i+1;
            datos[i]=String.valueOf(aux);
        }
        ArrayAdapter <String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_edad_values,(datos));
        return adapter;
    }
    public ArrayAdapter llenarSpinnerCarrera(){
        List<String> listSpinner= Arrays.asList("ISC","IIA","IM","CP","LA");
        ArrayAdapter <String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_edad_values,(listSpinner));
        return adapter;
    }
    public ArrayAdapter llenarSpinnerSemestre(){
        List <String> listSpinner= Arrays.asList("1","2","3","4","5","6","7","8","9","10");
        ArrayAdapter <String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_edad_values,(listSpinner));
        return adapter;
    }

}
