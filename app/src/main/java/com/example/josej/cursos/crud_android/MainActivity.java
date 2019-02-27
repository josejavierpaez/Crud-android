package com.example.josej.cursos.crud_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_usuarios"
                , null, 1);

    }

    public void onClick(View view) {
        Intent miIntent = null;

        switch (view.getId()) {
            case R.id.btnRegistarUsuario:
                miIntent = new Intent(MainActivity.this, RegistroUsuariosActivity.class);
                break;

            case R.id.btnConsultarUsuario:
                miIntent = new Intent(MainActivity.this, ConsultarUsuarioActivity.class);
                break;

            case R.id.btnConsultarUsuarioSpinner:
                miIntent = new Intent(MainActivity.this,ConsultaComboActivity.class);
                break;
        }

      if (miIntent!= null) {
          startActivity(miIntent);
      }

    }
}
