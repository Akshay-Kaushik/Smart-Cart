package com.example.smartcart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class cart extends AppCompatActivity {
    AlertDialog.Builder builder;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<ModelClass> userList;
    Adapter adapter;
    String cart_linked;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    DatabaseReference myRef_Product = database.getReference("Products");
    DatabaseReference Ref = database.getReference("Carts");
    ImageButton home, signout, add;
    TextView cost_text,balance_text;
    List<ModelClass> cartItemsList;
    Button pay;
    Double Balance=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cost_text = findViewById(R.id.cost);
        balance_text=findViewById(R.id.balance);
        pay=findViewById(R.id.pay_btn);
        mAuth = FirebaseAuth.getInstance();
        fetch_data();
        initData();
        initRecyclerView();
        Log.d("Hello",Ref.toString());
//        Log.d("Cart Linked",cart_linked);
        home = findViewById(R.id.home_imbtn);
        signout = findViewById(R.id.logout_imbtn);
        add = findViewById(R.id.add_money_imbtn);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Variables.price<Variables.balance){
                    Variables.balance-=Variables.price;
                    Variables.price=0;
                    cost_text.setText("Total Cost: "+String.valueOf(Variables.price));
                    myRef.child(mAuth.getUid()).child("Balance").setValue(String.valueOf(Variables.balance));
                    cartItemsList=new ArrayList<>();
                    Ref.child(cart_linked).setValue(cartItemsList);
                    Ref.child(cart_linked).child("0").child("Category").setValue("0");
                    initData();
                    initRecyclerView();
                }
                else{
                    Toast.makeText(cart.this,"You do not have sufficient balance!", Toast.LENGTH_LONG);
                }
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cart.this, MainActivity.class);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cart.this, Balance.class);
                startActivity(intent);
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(cart.this);
                builder.setMessage("Are you sure you want to log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(cart.this, loginActivity.class);
                                startActivity(intent);
//                        overridePendingTransition(R.anim.slidein_left,R.anim.slideout_right);
                                finishAffinity();
                                mAuth.signOut();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Log out?");
                alert.show();
            }
        });
    }

    private void initData() {
        userList = new ArrayList<>();

        //   Log.d("ABC",cart_linked);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Adapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void fetch_data() {
        myRef.child(mAuth.getUid()).child("Cart Linked").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                cart_linked = dataSnapshot.getValue(String.class);
                Log.d("TAG", "Value is: " + cart_linked);
                open_cart_from_link();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        myRef.child(mAuth.getUid()).child("Balance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Balance = Double.parseDouble(dataSnapshot.getValue(String.class));
                balance_text.setText("Balance: "+Balance);
//                Log.d("TAG", "Value is: " + cart_linked);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }

    public void open_cart_from_link() {
            Ref.child(cart_linked).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    ArrayList<Map<String,String>> value;
                    value = (ArrayList<Map<String,String>>) dataSnapshot.getValue();
                    if(value!=null) {
                        value.remove(0);
                        for (int i = 0; i < value.size(); i++) {
                            Log.d("TAG", "Value is: " + value.get(i));
                        }
                        insert_to_cart(value);
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });

    }
    public void insert_to_cart(ArrayList<Map<String,String>> value) {
        int size = value.size();
        cartItemsList=new ArrayList<>();
        Variables.price=0;
        for (int i = 0; i < size; i++) {
            String category=value.get(i).get("Category");
            String ID=value.get(i).get("ID");
            myRef_Product.child(category).child(ID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Map<String,String> value = (Map<String, String>) dataSnapshot.getValue();
                    Log.d("TAG", "Value is: " + value.get("comm"));
                    Variables.price+=Double.parseDouble(value.get("price"));
                    cartItemsList.add(
                            new ModelClass(
                                    value.get("comm"),
                                    value.get("qty"),
                                    value.get("price")
                            )
                    );
                    adapter = new Adapter(cartItemsList);
                    recyclerView.setAdapter(adapter);
                    cost_text.setText("Total Cost: "+String.valueOf(Variables.price));
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });


        }



    }


    }
