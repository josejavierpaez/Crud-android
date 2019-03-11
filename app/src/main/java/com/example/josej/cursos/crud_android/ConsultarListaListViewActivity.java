package com.example.josej.cursos.crud_android;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.josej.cursos.crud_android.entidades.Usuarios;
import com.example.josej.cursos.crud_android.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultarListaListViewActivity extends AppCompatActivity {

    ListView listViewPersonas;
    ArrayList<String> listaInformacion;
    ArrayList<Usuarios> listaUsuarios;
    ConexionSQLiteHelper conn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_lista_list_view);


        conn = new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        //creamos la instancia de nuestro componente
        listViewPersonas = (ListView) findViewById(R.id.listViewPersonas);

        consultarListaPersonas();

        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listViewPersonas.setAdapter(adaptador);

        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String informacion = "id: " +listaUsuarios.get(position).getId()+ "\n";
                informacion += "Nombre: " +listaUsuarios.get(position).getNombre()+ "\n";
                informacion += "Telefono: " +listaUsuarios.get(position).getTelefono()+ "\n";

                Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_LONG).show();
            }
        });


    }

    private void consultarListaPersonas() {

        SQLiteDatabase db = conn.getReadableDatabase();

        Usuarios usuarios = null;
        listaUsuarios = new ArrayList<Usuarios>();
        //SELECT * FORM USUARIOS
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_USUARIO,null);

        while (cursor.moveToNext()){
            usuarios = new Usuarios();

            usuarios.setId(cursor.getInt(0));
            usuarios.setNombre(cursor.getString(1));
            usuarios.setTelefono(cursor.getString(2));

            listaUsuarios.add(usuarios);
        }

        obtenerLista();
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();

        for(int i = 0; i < listaUsuarios.size();i++){
            listaInformacion.add(listaUsuarios.get(i).getId() + " - "
                    + listaUsuarios.get(i).getNombre());
        }
    }

}
