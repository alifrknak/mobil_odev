package com.example.ecommerce;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ecommerce.Models.User;

public class register extends AppCompatActivity {

    private Button register;
    private Button back;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.register = findViewById(R.id.approveRegister);
        this.back = findViewById(R.id.back);
        this.username = findViewById(R.id.registerUsername);
        this.password = findViewById(R.id.registerPassword);

        // bu activity i kapatır.
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveRegister();
            }
        });

    }

    private void approveRegister() {
        var inputUsername = this.username.getText();
        var inputPassword = this.password.getText();
        if (inputUsername.length() == 0 || inputPassword.length() == 0) {
            Toast.makeText(register.this, "kullanıcı adı ve sifre boş olamaz!", Toast.LENGTH_LONG).show();
        }

        DataBaseHelper db = new DataBaseHelper(register.this);
        db.addUser(new User(inputUsername.toString(),inputPassword.toString()));

        db.close();

    }
}