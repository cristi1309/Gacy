package com.Gacy.gacyProject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MenuAnfitrionActivity extends AppCompatActivity {


    private ImageView mPerfil,mReserva,mConfigu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_anfitrion);

        mPerfil = (ImageView) findViewById(R.id.perfil);
        mReserva = (ImageView) findViewById(R.id.verReservas);
        mConfigu = (ImageView) findViewById(R.id.configur);

        mPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuAnfitrionActivity.this, PerfilAnfitrionActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });

        mReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuAnfitrionActivity.this, "Estamos trabajando para esta actividad", Toast.LENGTH_SHORT).show();
            }
        });
        mConfigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAnfitrionActivity.this, MenuDrawerActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }
}
