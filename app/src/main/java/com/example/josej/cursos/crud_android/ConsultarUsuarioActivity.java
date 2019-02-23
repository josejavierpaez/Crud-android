package com.example.josej.cursos.crud_android;

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

        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_usuarios", null, 1);
        campoId = (EditText) findViewById(R.id.documentoId);
        campoNombre = (EditText) findViewById(R.id.campoNombreConsulta);
        campoTelefono = (EditText) findViewById(R.id.campoTelefonoConsulta);

    }

    public void onClick (View view) {

        switch (view.getId()) {
            case R.id.btnConsultar:
                consultar();
                break;

            case R.id.btnActualizar:
                break;

            case R.id.btnEliminar:
                break;
        }
    }

    private void consultar() {

        SQLiteDatabase db=conn.getReadableDatabase();

        String[] parametros={campoId.getText().toString()};

        String[] campos={Utilidades.CAMPO_NOMBRE,Utilidades.CAMPO_TELEFONO};



        try {

            Cursor cursor =db.query(Utilidades.TABLA_USUARIO,campos,Utilidades.CAMPO_ID+"=?",parametros,null,null,null);

            cursor.moveToFirst();

            campoNombre.setText(cursor.getString(0));

            campoTelefono.setText(cursor.getString(1));

            cursor.close();

        }catch (Exception e){

            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();

            limpiar();

        }








}

    private void limpiar() {
        campoNombre.setText("");
        campoTelefono.setText("");
    }
}
