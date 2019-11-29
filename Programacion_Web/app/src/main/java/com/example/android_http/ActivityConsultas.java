package com.example.android_http;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.DisplayContext;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controlador.AnalizadorJSON;

public class ActivityConsultas extends AppCompatActivity {
    ArrayList<StringBuilder> arrayList;
    EditText caja_clave;
    String dato;
    String campo;
    ArrayAdapter<StringBuilder> adapter;
    ListView lv;
    String res ="null";
    Spinner sp;
    Button b1, b2;
    private final static String[] op = { "Num_Control", "Nombre_Alumno", "Primer_Ap_Alumno",
            "Segundo_Ap_Alumno", "edad", "Semestre", "Carrera" };
    private final static String[] op1={"ISC","IIA","IM","CP","LA"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, op);
        sp = findViewById(R.id.opciones);
        sp.setAdapter(adapter);
        caja_clave=findViewById(R.id.caja_Num_Control_Consultas);
        b1=findViewById(R.id.btn_consultar);
        b2=findViewById(R.id.btn_consultar_todo);


//        sp.setOnItemSelectedListener(new OnItemSelectedListener() {
//            @SuppressLint("NewApi")
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                String s=sp.getSelectedItem().toString();
//                new mostrarAlumno().execute("Carrera",s);
//                Toast toast=Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
//                toast.setMargin(50,50);
//                toast.show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//        });

    }

    public void consultarAlumnos(View v){

        switch (v.getId()){
            case R.id.btn_consultar_todo:
                dato=caja_clave.getText().toString();
                campo = sp.getSelectedItem().toString();
                if(dato.equals("")){
                    Toast toast=Toast.makeText(getApplicationContext(),"Ingrese clave",Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }else{

                    new mostrarAlumno().execute(campo,dato);
                }

                break;
            case R.id.btn_consultar:
                new mostrarAlumnos().execute();
                break;
        }



    }
    //Hilo para consulta simple
    class mostrarAlumno extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            Map<String, String> mapAlumnos=new HashMap<>();
            mapAlumnos.put("ca",strings[0]);
            mapAlumnos.put("da",strings[1]);


            AnalizadorJSON analizadorJSON=new AnalizadorJSON();
            //String url http://192.168.1.68:80/Practicas_PHP/Api_PHP_Android/consulta_especifica_Final.php";
            String url="http://176.48.16.9/Practicas_PHP/Api_PHP_Android/consulta_especifica_Final.php";
            JSONObject jsonObject=analizadorJSON.consultaEspecificaHTTP(url, "POST", mapAlumnos);

            arrayList=new ArrayList<>();
            try {
                JSONArray jsonArray=jsonObject.getJSONArray("alumnos");
                for (int i = 0; i<jsonArray.length(); i++){
                    StringBuilder cadenaAlumno=new StringBuilder();
                    cadenaAlumno.append(jsonArray.getJSONObject(i).getString("nc")).append("-")
                            .append(jsonArray.getJSONObject(i).getString("n")).append("-")
                            .append(jsonArray.getJSONObject(i).getString("pa")).append("-")
                            .append(jsonArray.getJSONObject(i).getString("sa")).append("-")
                            .append(jsonArray.getJSONObject(i).getString("e")).append("-")
                            .append(jsonArray.getJSONObject(i).getString("s")).append("-")
                            .append(jsonArray.getJSONObject(i).getString("c"));
                    arrayList.add(cadenaAlumno);
                }
                res="1";

            } catch (JSONException e) {
                e.printStackTrace();
                res ="null";
            }
            return null;
        }
        protected void onPostExecute(String resu){
            resu=res;
            if(resu=="1"){
                lv = findViewById(R.id.tabla);
                adapter =new ArrayAdapter<>(ActivityConsultas.this, android.R.layout.simple_list_item_1, arrayList);
                lv.setAdapter(adapter);
            }else{
                Toast toast=Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }
        }
    }
    //Hilo para consulta completa
    class mostrarAlumnos extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            AnalizadorJSON analizadorJSON=new AnalizadorJSON();
            //String url http://192.168.1.68:80/Practicas_PHP/Api_PHP_Android/consultas_alumnos.php";
            String url="http://176.48.16.9/Practicas_PHP/Api_PHP_Android/consultas_alumnos.php";
            JSONObject jsonObject=analizadorJSON.consultaHTTP(url);


            arrayList=new ArrayList<>();
            try {
                JSONArray jsonArray=jsonObject.getJSONArray("alumnos");
                for (int i = 0; i<jsonArray.length(); i++){
                    StringBuilder cadenaAlumno=new StringBuilder();
                    cadenaAlumno.append(jsonArray.getJSONObject(i).getString("nc")).append("-")
                            .append(jsonArray.getJSONObject(i).getString("n")).append("-")
                            .append(jsonArray.getJSONObject(i).getString("pa")).append("-")
                            .append(jsonArray.getJSONObject(i).getString("sa")).append("-")
                            .append(jsonArray.getJSONObject(i).getString("e")).append("-")
                            .append(jsonArray.getJSONObject(i).getString("s")).append("-")
                            .append(jsonArray.getJSONObject(i).getString("c"));
                    arrayList.add(cadenaAlumno);
                }
                res="1";

            } catch (JSONException e) {
                e.printStackTrace();
                res ="null";
            }
            return null;
        }
        protected void onPostExecute(String resu){
            resu=res;
            if(resu=="1"){
                lv = findViewById(R.id.tabla);
                adapter =new ArrayAdapter<>(ActivityConsultas.this, android.R.layout.simple_list_item_1, arrayList);
                lv.setAdapter(adapter);
            }else{
                Toast toast=Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }
        }
    }
}
