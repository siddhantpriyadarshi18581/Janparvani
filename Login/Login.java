package org.bnmit.www.janaparvani;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText name, password;
    Button log_btn;
    TextView reg_log;
    String str_name, str_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.name_log);
        password = findViewById(R.id.password_log);
        log_btn = findViewById(R.id.login_btn);
        reg_log = findViewById(R.id.register_tv_log);

        reg_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Validation();
            }
        });

        //login_btn



    }
    private void Validation(){
        str_name = name.getText().toString();
        str_pass = password.getText().toString();

        if (str_name.isEmpty()) {
            name.setError("Please fill the field");
            name.requestFocus();
            return;
        }
        if (str_pass.isEmpty()) {
            password.setError("Please fill the field");
            password.requestFocus();
            return;
        }

        checkFromDB();
    }

    private void checkFromDB() {
         //FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
         DatabaseReference mGetReference = FirebaseDatabase.getInstance().getReference("User");
        mGetReference.child(str_name)
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //String db_pass = dataSnapshot.child("password").getValue(String.class);
                //String db_name = dataSnapshot.child("name").getValue(String.class);
                //int db_pass1;
                //db_pass1 = Log.d("db_pass", "Value: " + Float.parseFloat(db_pass));
                //System.out.println(db_pass1);

                if (dataSnapshot.exists()) {

                    //System.out.println(db_pass);
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        String db_pass = dataSnapshot.child("password").getValue(String.class);
                    if (str_pass.equals(db_pass)) {
                        Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                        intent.putExtra("name", str_name);
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "password is Incorrect", Toast.LENGTH_SHORT).show();
                    }
                }}
                 else {
                    Toast.makeText(LoginActivity.this, "Record Not Found", Toast.LENGTH_SHORT).show();
                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
