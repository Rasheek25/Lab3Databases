package com.example.lab3databases;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView productId;
    EditText productName, productPrice;
    Button addBtn, findBtn, deleteBtn;
    ListView productListView;

    ArrayList<String> productList;
    ArrayAdapter adapter;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = new ArrayList<>();

        // info layout
        productId = findViewById(R.id.productId);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);

        //buttons
        addBtn = findViewById(R.id.addBtn);
        findBtn = findViewById(R.id.findBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        // listview
        productListView = findViewById(R.id.productListView);

        // db handler
        dbHandler = new MyDBHandler(this);

        // button listeners
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = productName.getText().toString();
                double price = Double.parseDouble(productPrice.getText().toString());
                Product product = new Product(name, price);
                dbHandler.addProduct(product);
                productId.setText(String.valueOf(product.getId()));

                productName.setText("");
                productPrice.setText("");

//                Toast.makeText(MainActivity.this, "Add product", Toast.LENGTH_SHORT).show();
                viewProducts();
            }
        });

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product;
                if (productPrice.getText().toString().equals("")) {
                    product = dbHandler.findProduct(productName.getText().toString());
                    FindViewProducts3(product);
                }
                else if (productName.getText().toString().equals("")) {
                    product = dbHandler.findProduct(Double.parseDouble(productPrice.getText().toString()));
                    FindViewProducts2(product);
                }

                else {
                     product = dbHandler.findProduct(productName.getText().toString());
                    FindViewProducts1(product);
                }
                if (product != null) {

                    productPrice.setText(String.valueOf(product.getProductPrice()));

                } else {
                    productId.setText("No Match Found");
                }



                //  Toast.makeText(MainActivity.this, "Find product", Toast.LENGTH_SHORT).show();
            }

        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = dbHandler.deleteProduct(productName.getText().toString());
                if (result) {
                    productId.setText("Record deleted");
                    productName.setText("");
                    productPrice.setText("");
                }
                else {
                    productId.setText("No Match Found");
                }
                viewProducts();
                //Toast.makeText(MainActivity.this, "Delete product", Toast.LENGTH_SHORT).show();
            }
        });


        viewProducts();
    }

    /*public void newProduct (View v) {
        MyDBHandler dbHandler = new MyDBHandler(this);

        double price = Double.parseDouble(productPrice.getText().toString());
        Product product = new Product(productName.getText().toString(), price);
        dbHandler.addProduct(product);

        productName.setText("");
        productPrice.setText("");

        viewProducts();

    }*/

    /*public void lookupProduct(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);

        Product product = dbHandler.findProduct(productName.getText().toString());

        if (product != null) {
            productId.setText(String.valueOf(product.getId()));
            productPrice.setText(String.valueOf(product.getProductPrice()));

        } else {
            productId.setText("No Match Found");
        }
    }*/

    /*public void removeProduct(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);
        boolean result = dbHandler.deleteProduct(productName.getText().toString());
        if (result) {
            productId.setText("Record Deleted");
            productName.setText("");
            productPrice.setText("");
        }
        else {
            productId.setText("No Match Found");
        }
    }*/

    private void viewProducts() {
        productList.clear();
        Cursor cursor = dbHandler.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                productList.add(cursor.getString(0) + ' ' + cursor.getString(1) + ' ' + cursor.getString(2));

            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        productListView.setAdapter(adapter);
    }

    private void FindViewProducts1(Product product) {
        productList.clear();
        Cursor cursor = dbHandler.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }

        else {
            while (cursor.moveToNext()) {
                if (product.getProductName().equals(cursor.getString(1)) && product.getProductPrice() == Double.parseDouble(cursor.getString(2))) {
                    productList.add(cursor.getString(0) + ' ' + cursor.getString(1) + ' ' + cursor.getString(2));
                }
            }
        }



        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        productListView.setAdapter(adapter);

    }

    private void FindViewProducts2(Product product) {
        productList.clear();
        Cursor cursor = dbHandler.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }

        else {
            while (cursor.moveToNext()) {
                if (product.getProductPrice() == Double.parseDouble(cursor.getString(2))) {
                    productList.add(cursor.getString(0) + ' ' + cursor.getString(1) + ' ' + cursor.getString(2));
                }
            }
        }



        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        productListView.setAdapter(adapter);

    }

    private void FindViewProducts3(Product product) {
        productList.clear();
        Cursor cursor = dbHandler.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }

        else {
            while (cursor.moveToNext()) {
                if (product.getProductName().equals(cursor.getString(1))) {
                    productList.add(cursor.getString(0) + ' ' + cursor.getString(1) + ' ' + cursor.getString(2));
                }
            }
        }



        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        productListView.setAdapter(adapter);

    }




}