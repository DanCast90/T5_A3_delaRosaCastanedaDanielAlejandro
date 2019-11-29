package com.example.android_http;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controlador.AnalizadorJSON;

public class ACtivityLogin extends Activity {
    String res="";
    EditText cajausuario,cajacontrasena;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cajausuario=findViewById(R.id.caja_usuario);
        cajacontrasena=findViewById(R.id.caja_contrasena);


    }

    public void ingresar(View view){

        String usuario;
        String contrasena;
        if(cajausuario.getText().toString()!=null && cajacontrasena.getText().toString()!=null){
            new VerificarUsuario().execute(cajausuario.getText().toString(),cajacontrasena.getText().toString());
        }else{
            Toast toast=Toast.makeText(getApplicationContext(),"Error, ingresa los datos faltantes",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
        }
    }

    class VerificarUsuario extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            Map<String,String>mapAlumnos=new HashMap<>();
            mapAlumnos.put("usuario",strings[0]);
            mapAlumnos.put("contrasena",strings[1]);

            AnalizadorJSON analizadorJSON=new AnalizadorJSON();
            String url="http://176.48.16.9/Practicas_PHP/Api_PHP_Android/consulta_usuario_especifica.php";
            //String url="http://192.168.1.68:80/Practicas_PHP/Api_PHP_Android/consulta_usuario_especifica.php";
            JSONObject jsonObject=analizadorJSON.verificacionHTTP(url, "POST", mapAlumnos);
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
                Toast toast=Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                Intent i=new Intent(ACtivityLogin.this,MainActivity.class);
                startActivity(i);
            }else{
                Toast toast=Toast.makeText(getApplicationContext(),"Error, Verifique sus datos",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }
        }
    }
}
