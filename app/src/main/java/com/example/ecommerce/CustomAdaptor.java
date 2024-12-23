package com.example.ecommerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerce.Models.Product;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdaptor extends BaseAdapter {
    Context context;
    ArrayList<Product> products;
    LayoutInflater inflater;

    public CustomAdaptor(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
    return  this.products.size();
    }

    @Override
    public Object getItem(int position) {
        return this.products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  this.products.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_product_list_item,null);

        TextView textView = (TextView) convertView.findViewById(R.id.itemName);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.itemImage);

        textView.setText(products.get(position).getName());
        imageView.setImageResource(products.get(position).getImage());

        return  convertView;
    }
}
