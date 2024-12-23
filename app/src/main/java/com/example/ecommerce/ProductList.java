package com.example.ecommerce;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ecommerce.Models.Product;

import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

    // veritabanından gelen veriler için
    ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listView = findViewById(R.id.productListView);

        //var test =new Product("bilgisayar","sadifmsidpf0",200,2);test.setImage(R.drawable.laptop1);

        // veritabanından tüm ürünleri okur.
        DataBaseHelper db = new DataBaseHelper(ProductList.this);
        Cursor cursor = db.readAllProducts();

       ArrayList<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.laptop1);
        imageList.add(R.drawable.laptop2);
        imageList.add(R.drawable.laptop3);
        imageList.add(R.drawable.laptop4);

        while (cursor.moveToNext()){
            int i =0;
               Product product = new Product();
                 product.setName(cursor.getString(0));
                 product.setDescription(cursor.getString(1));
                 product.setPrice(cursor.getInt(3));
                 product.setQuantity(cursor.getInt(4));
                 product.setImage(R.drawable.laptop3);
                product.setImage(imageList.get(i++));
        }
        cursor.close();
        db.close();

        CustomAdaptor customAdaptor = new CustomAdaptor(getApplicationContext(),products);
        listView.setAdapter(customAdaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Product product = (Product)  parent.getItemAtPosition(position);

                Intent detailActivity = new Intent(ProductList.this, ProductDetails.class);
                detailActivity.putExtra("selectedProduct",product);

                startActivity(detailActivity);
            }
        });

    }
}