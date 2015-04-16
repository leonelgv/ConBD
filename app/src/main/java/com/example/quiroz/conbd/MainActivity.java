package com.example.quiroz.conbd;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends Activity implements OnClickListener {

    EditText nombre, telefono, ebuscar;
    Button insertar, ver, buscar, editar, eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = (EditText) findViewById(R.id.edNombre);
        telefono = (EditText) findViewById(R.id.edTelefono);
        insertar = (Button) findViewById(R.id.btInsertar);
        ver = (Button) findViewById(R.id.btVer);
        ebuscar = (EditText) findViewById(R.id.edBuscar);
        buscar = (Button) findViewById(R.id.btnBuscar);
        editar = (Button) findViewById(R.id.btnEditar);
        eliminar = (Button) findViewById(R.id.btnEliminar);

        insertar.setOnClickListener(this);
        ver.setOnClickListener(this);
        buscar.setOnClickListener(this);
        editar.setOnClickListener(this);
        eliminar.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btInsertar:

                EditText nomEditText = (EditText) findViewById(R.id.edNombre);
                //String snomEditText= nomEditText.getText().toString();

                EditText telEditText = (EditText) findViewById(R.id.edTelefono);
                // String stelEditText= telEditText.getText().toString();
                //ESTA LINEA PERMITE MOSTRAR MENSAJE DE ERROR DE DIFERENTE MANERA
                //if(snomEditText.matches("")){
                // Toast.makeText(this,"No has ingresado valores", Toast.LENGTH_SHORT).show();
                // return;
                //}
                //ESTA PERMITE SEÑALARTE DONDE ESTA
                if (TextUtils.isEmpty(nomEditText.getText().toString()) || TextUtils.isEmpty(telEditText.getText().toString())) {
                    nomEditText.setError("el nombre no puede ir vacio");
                    telEditText.setError("el telefono no puede ir vacio");
                    return;
                } else {


                    boolean funciona = true;
                    try {

                        String nom = nombre.getText().toString();
                        String tel = telefono.getText().toString();
                        nombre.setText("");
                        telefono.setText("");

                        Telefonos entrada = new Telefonos(MainActivity.this);
                        entrada.abrir();
                        entrada.crearEntrada(nom, tel);
                        entrada.cerrar();

                    } catch (Exception e) {
                        funciona = false;
                        String error = e.toString();
                        Dialog d = new Dialog(this);
                        d.setTitle("No hemos podido guardar el registro");
                        TextView tv = new TextView(this);
                        tv.setText(error);
                        d.setContentView(tv);
                        d.show();
                    } finally {
                        if (funciona) {
                            Dialog d = new Dialog(this);
                            d.setTitle("Verificando...");
                            TextView tv = new TextView(this);
                            tv.setText("Registro Guardado Correctamente");
                            d.setContentView(tv);
                            d.show();
                        }
                    }

                    break;
                }
            case R.id.btVer:
                Intent i = new Intent("com.example.quiroz.conbd.SQLvista");
                startActivity(i);
                break;
            case R.id.btnBuscar:
                EditText butBuscar = (EditText) findViewById(R.id.edBuscar);
                if (TextUtils.isEmpty(butBuscar.getText().toString())) {
                    butBuscar.setError("Ingrese un dato");

                    return;
                }

                boolean existe=true;
                try{
                String b = ebuscar.getText().toString();
                Long lb = Long.parseLong(b);


                Telefonos tel = new Telefonos(this);
                try {
                    tel.abrir();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String bN = tel.getN(lb);
                String bT = tel.getT(lb);
                tel.cerrar();
                nombre.setText(bN);
                telefono.setText(bT);
            }
                catch(Exception e){
                    existe = false;
                    String error = e.toString();
                    Dialog d = new Dialog(this);
                    d.setTitle("El registro no existe ");
                    TextView tv = new TextView(this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();}


        break;
        case R.id.btnEditar:
            boolean funca=true;
            try {
                String eNom = nombre.getText().toString();
                String eTel = telefono.getText().toString();
                String eFila = ebuscar.getText().toString();
                long eFilal = Long.parseLong(eFila);
                nombre.setText("");
                telefono.setText("");
                ebuscar.setText("");

                Telefonos editar = new Telefonos(this);
                editar.abrir();
                editar.editar(eFilal, eNom, eTel);
                editar.cerrar();
            }
            catch (Exception e){
                String error = e.toString();
                Dialog d= new Dialog(this);
                d.setTitle("No podemos editar sus datos");
                TextView tv = new TextView(this);
                tv.setText(error);
                d.setContentView(tv);
                d.show();
            }
            finally {
                if (funca){
                   Dialog d= new Dialog(this);
                    d.setTitle("Verificando");
                    TextView tv = new TextView(this);
                    tv.setText("Registro Actualizado Correctamente");
                    d.setContentView(tv);
                    d.show();


                }


            }

            break;
        case R.id.btnEliminar:
            boolean sirve= true;
            try {
                String elFila = ebuscar.getText().toString();
                ebuscar.setText("");
                nombre.setText("");
                telefono.setText("");
                long elFilal = Long.parseLong(elFila);
                Telefonos borrar = new Telefonos(this);
                borrar.abrir();
                borrar.borrar(elFilal);
                borrar.cerrar();


            }
            catch (Exception e){
                String error = e.toString();
                Dialog d= new Dialog(this);
                d.setTitle("No podemos eliminar sus datos");
                TextView tv = new TextView(this);
                tv.setText(error);
                d.setContentView(tv);
                d.show();
            }
            if (sirve){
                Dialog d= new Dialog(this);
                d.setTitle("Verificando");
                TextView tv = new TextView(this);
                tv.setText("Registro Eliminado Correctamente");
                d.setContentView(tv);
                d.show();


            }
            break;

    }

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}