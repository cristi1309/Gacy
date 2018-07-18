package com.Gacy.gacyProject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class InicioAppActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText mCorreoLogin, mContrasenaLogin;
    private TextView aRegistro;
    private Button mBotonLogin;
    private DatabaseReference mAnfitrionReference;
    private String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_app);

        //estas lineas se inicializan liganolas con el edit text de la IU
        mCorreoLogin = (EditText) findViewById(R.id.correoLogin);
        mContrasenaLogin = (EditText) findViewById(R.id.contrasenaLogin);
        mBotonLogin = (Button) findViewById(R.id.botonInicioS);
        aRegistro = (TextView) findViewById(R.id.accesoRegistro);
        mAuth = FirebaseAuth.getInstance(); // verifica si tiene inicio de sesión o no
        //metodo para obtener el usuario
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//obtiene la info del usuario actual.
                mAnfitrionReference = FirebaseDatabase.getInstance().getReference().child("Usuario");
                getTipoUsuario();
                if(user != null){
                    if(userType == "Anfitrion"){
                        //Intent intent = new Intent(Register.this, AnfitrionMapsActivity.class);
                        Intent intent = new Intent(getApplicationContext(), MenuDrawerActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }else{
                        //Intent intent = new Intent(Register.this, AnfitrionMapsActivity.class);
                        Intent intent = new Intent(getApplicationContext(), AnfitrionMapsActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }

            }
        };

        //metodo para el inicio de sesión
        mBotonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtener los datos que ingreso el usuario : correo e email
                String correo = mCorreoLogin.getText().toString();
                String contrasena = mContrasenaLogin.getText().toString();
                // se trata de hacer el login con correo y contraeña
                mAuth.signInWithEmailAndPassword(correo,contrasena).addOnCompleteListener(InicioAppActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //si no se se encuentra el usuario con esos datos no se puede iniciar sesión
                        if(!task.isSuccessful()){
                            //mensaje al usuario
                            Toast.makeText(InicioAppActivity.this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        aRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

    }

    //metodo para parar la accion
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

    private void getTipoUsuario(){
        mAnfitrionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){

                    Map<String, Object> map = (Map<String,Object>) dataSnapshot.getValue();

                    if(map.get("Anfitrion") != null){
                        userType = "Anfitrion";
                        //Toast.makeText(InicioAppActivity.this, userType, Toast.LENGTH_SHORT).show();
                    }
                    if(map.get("Ciclista") != null){
                        userType = "Ciclista";
                        //Toast.makeText(InicioAppActivity.this, userType, Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
