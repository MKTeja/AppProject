package com.example.creativecart.Retailer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.creativecart.R;

public class RetailerCategoryActivity extends AppCompatActivity {
    private ImageView Vegetable, Dairy, Beverages, Staples, Meat, Snacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_category);

        Vegetable = findViewById(R.id.vegetable);
        Dairy = findViewById(R.id.dairy);
        Beverages = findViewById(R.id.beverages);
        Staples = findViewById(R.id.staples);
        Meat = findViewById(R.id.meat);
        Snacks = findViewById(R.id.snacks);

        Vegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RetailerCategoryActivity.this, RetailerAddNewActivity.class);
                intent.putExtra("category", "Vegetable");
                intent.putExtra("pid", "");
                startActivity(intent);
            }
        });

        Dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RetailerCategoryActivity.this, RetailerAddNewActivity.class);
                intent.putExtra("category", "Dairy");
                intent.putExtra("pid", "");
                startActivity(intent);
            }
        });

        Beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RetailerCategoryActivity.this, RetailerAddNewActivity.class);
                intent.putExtra("category", "Beverages");
                intent.putExtra("pid", "");
                startActivity(intent);
            }
        });

        Snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RetailerCategoryActivity.this, RetailerAddNewActivity.class);
                intent.putExtra("category", "Snacks");
                intent.putExtra("pid", "");
                startActivity(intent);
            }
        });

        Staples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RetailerCategoryActivity.this, RetailerAddNewActivity.class);
                intent.putExtra("category", "Staples");
                intent.putExtra("pid", "");
                startActivity(intent);
            }
        });

        Meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RetailerCategoryActivity.this, RetailerAddNewActivity.class);
                intent.putExtra("category", "Personal");
                intent.putExtra("pid", "");
                startActivity(intent);
            }
        });
    }
}
