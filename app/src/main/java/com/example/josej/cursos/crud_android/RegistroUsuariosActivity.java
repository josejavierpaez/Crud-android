package com.example.josej.cursos.crud_android;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.josej.cursos.crud_android.utilidades.Utilidades;

public class RegistroUsuariosActivity extends AppCompatActivity {
    //convirtiendo en objetos los componentes de la vista
    EditText campoId, campoNombre, campoTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);
        campoId = (EditText) findViewById(R.id.campoId);
        campoNombre = (EditText) findViewById(R.id.campoNombre);
        campoTelefono = (EditText) findViewById(R.id.campoTelefono);

    }


    public void onClick(View view) {
       // registrarUsuarios();

       registrarUsuariosSQL();
    }

    //metodo para regeristrar usuario usando sentencias SQL
    private void registrarUsuariosSQL() {
        ConexionSQLiteHelper conexicion = new ConexionSQLiteHelper(this, "bd_usuarios", null, 1);

        SQLiteDatabase db = conexicion.getWritableDatabase();
        ContentValues values = new ContentValues();

        String insert = "INSERT INTO " + Utilidades.TABLA_USUARIO + " ( " + Utilidades.CAMPO_ID + ","
                + Utilidades.CAMPO_NOMBRE + "," + Utilidades.CAMPO_TELEFONO + ") " +
                "VALUES (" + campoId.getText().toString() + ", '" + campoNombre.getText().toString() +
                "','" + campoTelefono.getText().toString() + "')";
        db.execSQL(insert);
        Toast.makeText(getApplicationContext(),"Registrado Id" + campoId.getText().toString(),Toast.LENGTH_SHORT).show();
        db.close();
    }

    //metodo para regeristrar usuario
    private void registrarUsuarios() {
        ConexionSQLiteHelper conexicion = new ConexionSQLiteHelper(this, "bd_usuarios", null, 1);

        SQLiteDatabase db = conexicion.getWritableDatabase();

        ContentValues values = new ContentValues();
        //primre dato indica el campo que se afectara y el segundo dato el falor que se se le mandara a el campo
        values.put(Utilidades.CAMPO_ID, campoId.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRE, campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_TELEFONO, campoTelefono.getText().toString());

        try {
            long idResultante = db.insert(Utilidades.TABLA_USUARIO, Utilidades.CAMPO_ID, values);

            Toast.makeText(getApplicationContext(), "Id registro" + idResultante, Toast.LENGTH_SHORT).show();
            db.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Ocurrio un error", Toast.LENGTH_SHORT).show();
        }
    }
}
