package com.example.quiroz.conbd;

/**
 * Created by Quiroz on 25/02/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SQLvista extends Activity {
    TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista);

        texto = (TextView) findViewById(R.id.tvTexto);

        Telefonos info= new Telefonos(this);
        try {
            info.abrir();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String datos= info.recibir();
        info.cerrar();
        texto.setText(datos);


    }
}