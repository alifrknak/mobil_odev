package com.example.ecommerce;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ecommerce.Models.Product;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText username;
    private  EditText password;
    private  Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

               var isConnectToNetwork = NetworkUtils.isInternetAvailable(MainActivity.this);

               if (!isConnectToNetwork) {
                   Toast.makeText(MainActivity.this, "internet bağlantısı yok!!", Toast.LENGTH_LONG).show();
               }


        //  test=user :123 sifre:123

        this.login = findViewById(R.id.loginButton);
        this.username = findViewById(R.id.usernameText);
        this.password = findViewById(R.id.passwordText);
        this.register = findViewById(R.id.registerButton);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             var successLogin = login();
             if (successLogin){
                 Toast.makeText(MainActivity.this, "Giriş başarılı", Toast.LENGTH_LONG).show();
                 // giriş başarılı ise ürün liste sayfasını aç.
                 Intent productsList = new Intent(MainActivity.this, ProductList.class);
                 startActivity(productsList);
             }else{
                 Toast.makeText(MainActivity.this, "Bilgiler yanlış", Toast.LENGTH_LONG).show();
             }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    /**
     * kayıt olma ekranını açar.
     */
    private void register() {
        Intent register = new Intent(MainActivity.this, register.class);
        startActivity(register);
    }

    private boolean login() {
        var inputUsername = username.getText();
        var inputPassword = password.getText();
        if (inputUsername.length() == 0 || inputPassword.length() == 0) {
            Toast.makeText(MainActivity.this, "kullanıcı adı ve sifre boş olamaz!", Toast.LENGTH_LONG).show();
            return false;
        }

        DataBaseHelper db = new DataBaseHelper(MainActivity.this);
        var userCursor =db.readUserByUserName(inputUsername.toString());
        if (userCursor.moveToFirst()){
            var password = userCursor.getString(userCursor.getColumnIndexOrThrow("password"));
            if (!password.equals(inputPassword.toString())){
                return  false;
            }
        }else{
            return  false;
        }
        db.close();
        return  true;
    }

}