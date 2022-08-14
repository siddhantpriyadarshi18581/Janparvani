package org.bnmit.www.janaparvani;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText name, password, mail, phone, con_pass;
    TextView signin;
    Button reg_btn;
    String str_name, str_pass, str_mail, str_conf_pass, str_phone, str_des;
    RadioGroup radioGroup;
    RadioButton radioButton;


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        mail = findViewById(R.id.mail);
        phone = findViewById(R.id.phone);
        con_pass = findViewById(R.id.con_pass);
        signin = findViewById(R.id.sign_tv);
        reg_btn = findViewById(R.id.reg_btn);
        radioGroup = findViewById(R.id.radioGroup);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        //progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });
    }
    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);


    }

    private void Validation() {
        str_name = name.getText().toString();
        str_mail = mail.getText().toString();
        str_pass = password.getText().toString();
        str_conf_pass = con_pass.getText().toString();
        str_phone = phone.getText().toString();
        str_des = radioButton.getText().toString();

        if (str_name.isEmpty()) {
            name.setError("Please fill Field");
            name.requestFocus();
            return;
        }
        if (str_phone.isEmpty()) {
            phone.setError("Please fill the phone number");
            phone.requestFocus();
            return;
        }
        if (!numberCheck(str_phone)) {
            phone.setError("Invalid Mobile Number");
            phone.requestFocus();
            return;
        }
        if (str_mail.isEmpty()) {
            mail.setError("Please fill the email");
            mail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(str_mail).matches()) {
            mail.setError("Please enter the valid email");
            mail.requestFocus();
            return;
        }
        if (str_pass.isEmpty()) {
            password.setError("Please fill field");
            password.requestFocus();
            return;
        } else if (!passwordValidation(str_pass)) {
            password.setError("Enter maximum 6 digits");
            password.requestFocus();
        }
        if (str_conf_pass.isEmpty()) {
            password.setError("Please fill field");
            password.requestFocus();
            return;
        }
        createAccount();
    }

    private void createAccount() {
        progressDialog.setMessage("Creating Account");
        progressDialog.show();

        sendDataToDb();
    }

    private void sendDataToDb() {
        String regtime = "" + System.currentTimeMillis();
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", str_name);
        data.put("mail", str_mail);
        data.put("password", str_pass);
        data.put("phone", str_phone);
        data.put("registrationTime", regtime);
        data.put("designation",str_des);


        //database

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.child(str_name).setValue(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //db update
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();

                        //Dashboard
                        Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private boolean passwordValidation(String str_pass) {
        Pattern p = Pattern.compile(".{6}");
        Matcher m = p.matcher(str_pass);
        return m.matches();
    }

    private boolean numberCheck(String str_phone) {
        Pattern p = Pattern.compile("[0-9]{11}");
        Matcher m = p.matcher(str_phone);
        return m.matches();
    }
}
