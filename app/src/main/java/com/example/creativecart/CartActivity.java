package com.example.creativecart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creativecart.Model.Cart;
import com.example.creativecart.Prevalent.Prevalent;
import com.example.creativecart.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Button NextProcessBtn;
    private TextView txtTotalAmount, txtMsg1;

    private int overTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessBtn = (Button) findViewById(R.id.next_process_btn);
        txtTotalAmount = (TextView) findViewById(R.id.total_price);
        //txtMsg1 = (TextView) findViewById(R.id.msg1);

        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (overTotalPrice != 0) {
                    Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                    intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CartActivity.this, "No items in the cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // CheckOrderState();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child(Prevalent.currentOnlineUser.getUser())
                                .child(Prevalent.currentOnlineUser.getPhone()), Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {
                holder.txtProductQuantity.setText("Quantity = " + model.getQuantity());
                holder.txtProductPrice.setText("Price " + model.getPrice() + "$");
                holder.txtProductName.setText(model.getPname());
//                int x,y;
//                if (!TextUtils.isEmpty(model.getPrice()) && TextUtils.isDigitsOnly(model.getPrice())) {
//                    x = Integer.parseInt(model.getPrice());
//                } else {
//                    x = 0;
//                }
//                if (!TextUtils.isEmpty(model.getQuantity()) && TextUtils.isDigitsOnly(model.getQuantity())) {
//                    y = Integer.parseInt(model.getPrice());
//                } else {
//                    y = 0;
//                }
                int oneTyprProductTPrice = Integer.parseInt(model.getPrice()) * Integer.parseInt(model.getQuantity());
                overTotalPrice = overTotalPrice + oneTyprProductTPrice;

                txtTotalAmount.setText("Total Price: Rs." + String.valueOf(overTotalPrice));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }
                                if (i == 1) {
                                    cartListRef.child(Prevalent.currentOnlineUser.getUser())
                                            .child(Prevalent.currentOnlineUser.getPhone())
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(CartActivity.this, "Item removed successfully.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}