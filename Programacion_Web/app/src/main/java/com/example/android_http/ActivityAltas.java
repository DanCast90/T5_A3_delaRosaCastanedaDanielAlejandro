package com.example.android_http;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controlador.AnalizadorJSON;

public class ActivityAltas extends Activity {

    EditText cajaNumControl,cajaNombre,cajaPrimerAp,cajaSegundoAp;
    Spinner spinnerEdad,spinnerSem,spinnerCarr;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);


        cajaNumControl=findViewById(R.id.caja_Num_Control);
        cajaNombre=findViewById(R.id.caja_Nombre);
        cajaPrimerAp=findViewById(R.id.caja_Primer_Ap);
        cajaSegundoAp=findViewById(R.id.caja_Segundo_Ap);


        spinnerEdad=(Spinner) findViewById(R.id.sp_edad);
        spinnerEdad.setAdapter(llenarSpinnerEdad());

        spinnerSem=(Spinner) findViewById(R.id.sp_semestre);
        spinnerSem.setAdapter(llenarSpinnerSemestre());

        spinnerCarr=(Spinner)findViewById(R.id.sp_carrera);
        spinnerCarr.setAdapter(llenarSpinnerCarrera());
    }

    public void registrarAlumno(View v){

        String nc=cajaNumControl.getText().toString();
        String n=cajaNombre.getText().toString();
        String pa=cajaPrimerAp.getText().toString();
        String sa=cajaSegundoAp.getText().toString();
        byte e=Byte.parseByte(spinnerEdad.getSelectedItem().toString());
        byte s=Byte.parseByte(spinnerSem.getSelectedItem().toString());
        String c=(spinnerCarr.getSelectedItem().toString());;

        //Revisar conectividad Wifi
//
    ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo ni=cm.getActiveNetworkInfo();
       if(ni != null && ni.isConnected()){
           //proceso para enviar peticion HTTP con la cadena JSON que contendra los datos del alumno

           new AgregarAlumno().execute(nc,n,pa,sa,String.valueOf(e),String.valueOf(s),c);
           Toast toast=Toast.makeText(getApplicationContext(),"Registro insertado",Toast.LENGTH_SHORT);
           toast.setMargin(50,50);
           toast.show();
       }else{
           Toast.makeText(this,"ERROR DE CONEXION \n REVISA TU CONECTIVIDAD A INTERNET",Toast.LENGTH_LONG).show();
           Log.i("MSJ =>","Error en WIFI");
       }
    }


    public ArrayAdapter llenarSpinnerEdad(){
        String datos[]=new String[100];
        for (int i=0;i<100;i++){
            int aux=i+1;
            datos[i]=String.valueOf(aux);
        }
        ArrayAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,(datos));
        return adapter;
    }
    public ArrayAdapter llenarSpinnerCarrera(){
        String []datos={"ISC","IIA","IM","CP","LA"};
        List <String> listSpinner= Arrays.asList("ISC","IIA","IM","CP","LA");
        ArrayAdapter <String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,(datos));
        return adapter;
    }
    public ArrayAdapter llenarSpinnerSemestre(){
        String datos[]={"1","2","3","4","5","6","7","8","9","10"};
        List <String> listSpinner= Arrays.asList("1","2","3","4","5","6","7","8","9","10");
        ArrayAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,(datos));
        return adapter;
    }
    //Clase interna dentro de Activity Altas para un segundo Hilo de Ejecucion

    class AgregarAlumno extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            String res="null";
            Map<String,String> mapAlumnos=new HashMap<>();
            mapAlumnos.put("nc",strings[0]);
            mapAlumnos.put("n",strings[1]);
            mapAlumnos.put("pa",strings[2]);
            mapAlumnos.put("sa",strings[3]);
            mapAlumnos.put("e",strings[4]);
            mapAlumnos.put("s",strings[5]);
            mapAlumnos.put("c",strings[6]);


            //String url http://192.168.1.68:80/Practicas_PHP/Api_PHP_Android/altas_alumnos.php";
            String url="http://176.48.16.9/Practicas_PHP/Api_PHP_Android/altas_alumnos.php";
            AnalizadorJSON analizadorJSON=new AnalizadorJSON();
           JSONObject jsonObject= analizadorJSON.peticionHTTP(url,"POST",mapAlumnos);
            try {
                if(jsonObject.getInt("exito")==1){
                    Log.i("MSJ =>","OMAIGA, si jala ");
                }else{
                    Log.i("MSJ =>","Denme mi salida lateral ");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return res;
        }
    }

}
