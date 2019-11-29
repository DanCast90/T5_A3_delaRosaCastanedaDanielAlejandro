package com.example.android_http;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controlador.AnalizadorJSON;

public class ActivityBajas extends Activity {
    String res="null";
    ArrayList<String>arrayList;
    EditText caja_clave;
    String dato,campo;
    Spinner sp_opcion,sp_edad,sp_semestre,sp_carrera;
    Button b1,b2;
    EditText cajaNumControl,cajaNombre,cajaPrimerAp,cajaSegundoAp;
    private final static String[] op = { "Num_Control"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bajas);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, op);
        sp_opcion = findViewById(R.id.opciones_cambios);
        sp_opcion.setAdapter(adapter);
        caja_clave=findViewById(R.id.caja_Num_Control_Bajas);

        cajaNumControl=findViewById(R.id.caja_Num_Control_cambios);
        cajaNombre=findViewById(R.id.caja_Nombre_cambios);
        cajaPrimerAp=findViewById(R.id.caja_Primer_Ap_cambios);
        cajaSegundoAp=findViewById(R.id.caja_Segundo_Ap_cambios);

        sp_edad=findViewById(R.id.sp_edad_cambios);
        sp_edad.setAdapter(llenarSpinnerEdad());

        sp_semestre=findViewById(R.id.sp_semestre_cambios);
        sp_semestre.setAdapter(llenarSpinnerSemestre());

        sp_carrera=findViewById(R.id.sp_carrera_cambios);
        sp_carrera.setAdapter(llenarSpinnerCarrera());

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
        List<String> listSpinner= Arrays.asList("ISC","IIA","IM","CP","LA");
        ArrayAdapter <String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,(datos));
        return adapter;
    }
    public ArrayAdapter llenarSpinnerSemestre(){
        String datos[]={"1","2","3","4","5","6","7","8","9","10"};
        List <String> listSpinner= Arrays.asList("1","2","3","4","5","6","7","8","9","10");
        ArrayAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,(datos));
        return adapter;
    }


    public void consultarAlumnos(View v){
        switch (v.getId()){
            case R.id.btn_consultar_cambios:
                dato=caja_clave.getText().toString();
                campo = sp_opcion.getSelectedItem().toString();
                if(dato.equals("")){
                    Toast toast=Toast.makeText(getApplicationContext(),"Ingrese clave",Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }else{

                    new mostrarAlumno().execute(campo,dato);
                }
                break;
            case R.id.btn_dar_cambio:
                String nc=cajaNumControl.getText().toString();
                String n=cajaNombre.getText().toString();
                String pa=cajaPrimerAp.getText().toString();
                String sa=cajaSegundoAp.getText().toString();
                byte e=Byte.parseByte(sp_edad.getSelectedItem().toString());
                byte s=Byte.parseByte(sp_semestre.getSelectedItem().toString());
                String c=(sp_carrera.getSelectedItem().toString());
                new ActualizarAlumnos().execute(nc,n,pa,sa,String.valueOf(e),String.valueOf(s),c);
                break;
            case R.id.btn_dar_baja:
                String nc1=caja_clave.getText().toString();
                new EliminarAlumnos().execute(nc1);
                break;
        }
    }
    //Mostrar alumno en los campos
    class mostrarAlumno extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            Map<String, String> mapAlumnos=new HashMap<>();
            mapAlumnos.put("ca",strings[0]);
            mapAlumnos.put("da",strings[1]);


            AnalizadorJSON analizadorJSON=new AnalizadorJSON();

            //String url http://192.168.1.68:80/Practicas_PHP/Api_PHP_Android/consulta_especifica_Final.php";
            String url="http://176.48.16.9/Practicas_PHP/Api_PHP_Android/consulta_especifica_Final.php";
            JSONObject jsonObject=analizadorJSON.consultaEspecificaHTTP(url, "POST", mapAlumnos);

            arrayList=new ArrayList<String>();
            try {
                JSONArray jsonArray=jsonObject.getJSONArray("alumnos");
                for (int i = 0; i<jsonArray.length(); i++){
                    arrayList.add(jsonArray.getJSONObject(i).getString("nc"));
                    arrayList.add(jsonArray.getJSONObject(i).getString("n"));
                    arrayList.add(jsonArray.getJSONObject(i).getString("pa"));
                    arrayList.add(jsonArray.getJSONObject(i).getString("sa"));
                    arrayList.add(jsonArray.getJSONObject(i).getString("e"));
                    arrayList.add(jsonArray.getJSONObject(i).getString("s"));
                    arrayList.add(jsonArray.getJSONObject(i).getString("c"));
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
                cajaNumControl.setText(arrayList.get(0));
                cajaNombre.setText(arrayList.get(1));
                cajaPrimerAp.setText(arrayList.get(2));
                cajaSegundoAp.setText(arrayList.get(3));
                sp_edad.setSelection(Integer.parseInt(arrayList.get(4))-1);
                sp_semestre.setSelection(Integer.parseInt(arrayList.get(5))-1);

                String texto=sp_carrera.getSelectedItem().toString();
                switch (texto){
                    case "ISC":
                        sp_carrera.setSelection(0);
                        break;
                    case "IIA":
                        sp_carrera.setSelection(1);
                        break;
                    case "IM":
                        sp_carrera.setSelection(2);
                        break;
                    case "CP":
                        sp_carrera.setSelection(3);
                        break;
                    case "LA":
                        sp_carrera.setSelection(4);
                        break;
                }

            }else{
                Toast toast=Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }
        }
    }
    //Actualizar
    class ActualizarAlumnos extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

            Map<String, String>mapAlumnos=new HashMap<>();
            mapAlumnos.put("nc",strings[0]);
            mapAlumnos.put("n",strings[1]);
            mapAlumnos.put("pa",strings[2]);
            mapAlumnos.put("sa",strings[3]);
            mapAlumnos.put("e",strings[4]);
            mapAlumnos.put("s",strings[5]);
            mapAlumnos.put("c",strings[6]);
            AnalizadorJSON analizadorJSON=new AnalizadorJSON();
            //String url http://192.168.1.68:80/Practicas_PHP/Api_PHP_Android/actualizacion_Final.php";
            String url="http://176.48.16.9/Practicas_PHP/Api_PHP_Android/actualizacion_Final.php";
            JSONObject jsonObject=analizadorJSON.actualizacionHTTP(url, "POST", mapAlumnos);
            try {
                if (jsonObject.getInt("exito")==1){
                    Log.i("MSJ==>", "Tododo salio bien");
                    res="1";

                }else{
                    Log.i("MSJ==>", "Error"+jsonObject.getString("mensaje"));
                    res=null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String resu){
            resu=res;
            if(resu=="1"){
                Toast toast=Toast.makeText(getApplicationContext(),"Registro insertado",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }else{
                Toast toast=Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }
        }
    }
    //Eliminacion
    class EliminarAlumnos extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

            Map<String, String>mapAlumnos=new HashMap<>();
            mapAlumnos.put("nc1",strings[0]);

            AnalizadorJSON analizadorJSON=new AnalizadorJSON();
            //String url http://192.168.1.68:80/Practicas_PHP/Api_PHP_Android/eliminacion_Final.php";
            String url="http://176.48.16.9/Practicas_PHP/Api_PHP_Android/eliminacion_Final.php";
            JSONObject jsonObject=analizadorJSON.eliminacionHTTP(url, "POST", mapAlumnos);

            try {
                if (jsonObject.getInt("exito")==1){
                    Log.i("MSJ==>", "Tododo salio bien");
                    res="1";

                }else{
                    Log.i("MSJ==>", "Error"+jsonObject.getString("mensaje"));
                    res=null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String resu){
            resu=res;
            if(resu=="1"){
                Toast toast=Toast.makeText(getApplicationContext(),"Registro eliminado",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }else{
                Toast toast=Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }
        }
    }



}
