package com.example.josej.cursos.crud_android;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.josej.cursos.crud_android.entidades.Usuarios;
import com.example.josej.cursos.crud_android.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultaComboActivity extends AppCompatActivity {

    Spinner comboPersonas;
    TextView txtDocumento;
    TextView txtNombre;
    TextView txtTelefono;
    ArrayList<String> listaPersonas;
    ArrayList<Usuarios> personasList;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_combo);

        //convirtiendo en objetos los componentes de la vista
        comboPersonas = (Spinner) findViewById(R.id.comboPersonas);
        txtDocumento = (TextView) findViewById(R.id.txtDocumento);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtTelefono = (TextView) findViewById(R.id.txtTelefono);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_usuarios", null, 1);

        consultarListaPersonas();
    //llenado el Spinner con la lista
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item,listaPersonas);

        comboPersonas.setAdapter(adaptador);

        comboPersonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /**
                 * como el primer dato de nuestra lissaPersona es selecionar, realizamos esta
                 * validacion ya que si se selecciona " SELECIONAR" no se muestre en los txt */
                if (position !=0){//si la posicion es diferente de 0
                    txtDocumento.setText(personasList.get(position-1).getId().toString());
                    txtNombre.setText(personasList.get(position-1).getNombre().toString());
                    txtTelefono.setText(personasList.get(position-1).getTelefono().toString());
                }else{//los txt se mostraran vacios
                    txtDocumento.setText("");
                    txtNombre.setText("");
                    txtTelefono.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void consultarListaPersonas() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Usuarios persona = null;
        personasList = new ArrayList<Usuarios>();
        //consulta para traer toda la data de la tabla SELECT * FROM NOMBRE_DE_LA_TABLA
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_USUARIO, null);

        /**mientras el cursor tenga datos se seguiran agregando
         aun nuevo objeto persona*/
        while (cursor.moveToNext()) {
            persona = new Usuarios();

            //creando un objeto persona por cada fila
            persona.setId(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setTelefono(cursor.getString(2));

            personasList.add(persona);//agregando todos los objetos persona a la lista
        }

        obtenerLista();
    }

    private void obtenerLista() {
        listaPersonas = new ArrayList<String>();
        listaPersonas.add("Seleccione");
        //agregando a otra lista el  id y el nombre de todos los objetos
        for (int i = 0; i < personasList.size(); i++) {
            listaPersonas.add(personasList.get(i).getId() + "-" + personasList.get(i).getNombre());
        }
    }

}
