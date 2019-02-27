package com.example.josej.cursos.crud_android;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.josej.cursos.crud_android.utilidades.Utilidades;

public class ConsultarUsuarioActivity extends AppCompatActivity {

    EditText campoId, campoNombre, campoTelefono;

    ConexionSQLiteHelper conn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_ususarios);

        //abrimos la DB
        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_usuarios", null, 1);

        //convirtiendo en objetos los componentes de la vista
        campoId = (EditText) findViewById(R.id.documentoId);
        campoNombre = (EditText) findViewById(R.id.campoNombreConsulta);
        campoTelefono = (EditText) findViewById(R.id.campoTelefonoConsulta);

    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnConsultar:
                //consultar();
                consultarSQL();
                break;

            case R.id.btnActualizar:
                actualizar();
                break;

            case R.id.btnEliminar:
                eliminar();
                break;
        }
    }

    //metodo para eliminar
    private void eliminar() {
        SQLiteDatabase db = conn.getWritableDatabase();//indicamos que escribiremos en la DB
        String parametros[] = {campoId.getText().toString()};

        try {
            db.delete(Utilidades.TABLA_USUARIO,Utilidades.CAMPO_ID+"=?",parametros);
            campoId.setText("");    //limpiamos el campo id
            limpiar();  //limpiamos los campon de nombre y telefono
            Toast.makeText(getApplicationContext(), "usuario eliminado" +
                    campoId.getText().toString(), Toast.LENGTH_SHORT).show();
            db.close();    //cerramos la DB
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Ocurrio un error", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo para actualizar datos
    private void actualizar() {
        SQLiteDatabase db = conn.getWritableDatabase();//le decimnos que escribiremos en la DB
        String[] parametros = {campoId.getText().toString()};
        ContentValues values = new ContentValues();
        //primre dato indica el campo que se afectara y el segundo dato el falor que se se le mandara a el campo
        values.put(Utilidades.CAMPO_NOMBRE, campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_TELEFONO, campoTelefono.getText().toString());

        try {
            //actualizamos los datos
            db.update(Utilidades.TABLA_USUARIO, values, Utilidades.CAMPO_ID + "=?", parametros);
            Toast.makeText(getApplicationContext(), "usuario actualizado" +
                    campoId.getText().toString(), Toast.LENGTH_SHORT).show();
            db.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Ocurrio un error", Toast.LENGTH_SHORT).show();
        }

    }
    //metodo para consultar el usuario usando sentenciasSQL
    private void consultarSQL() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {campoId.getText().toString()};

        try {
            //select nombre,telefono from usuarios where id =?
            Cursor cursor = db.rawQuery("SELECT " + Utilidades.CAMPO_NOMBRE + "," +
                    Utilidades.CAMPO_TELEFONO + " FROM " + Utilidades.TABLA_USUARIO + " WHERE " +
                    Utilidades.CAMPO_ID + "=?", parametros);
            cursor.moveToFirst();
            campoNombre.setText(cursor.getString(0));
            campoTelefono.setText(cursor.getString(1));
            cursor.close();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "El documento no existe", Toast.LENGTH_LONG).show();

            limpiar();
        }

    }
    //metodo para consultar usuario
    private void consultar() {

        SQLiteDatabase db = conn.getReadableDatabase();

        String[] parametros = {campoId.getText().toString()};

        String[] campos = {Utilidades.CAMPO_NOMBRE, Utilidades.CAMPO_TELEFONO};


        try {

            Cursor cursor = db.query(Utilidades.TABLA_USUARIO, campos, Utilidades.CAMPO_ID + "=?", parametros, null, null, null);

            cursor.moveToFirst();

            campoNombre.setText(cursor.getString(0));

            campoTelefono.setText(cursor.getString(1));

            cursor.close();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "El documento no existe", Toast.LENGTH_LONG).show();

            limpiar();

        }


    }
    //metodo para limpiar los campos nombre y telefono
    private void limpiar() {
        campoNombre.setText("");
        campoTelefono.setText("");
    }
}
