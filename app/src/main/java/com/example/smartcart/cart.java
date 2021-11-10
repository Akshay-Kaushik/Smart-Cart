package com.example.smartcart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class cart extends AppCompatActivity {
    AlertDialog.Builder builder;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<ModelClass> userList;
    Adapter adapter;
    String rice="rice";
    ImageButton home,signout,add;
    TextView cost_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cost_text=findViewById(R.id.cost);
        mAuth = FirebaseAuth.getInstance();
        initData();
        initRecyclerView();
        home=findViewById(R.id.home_imbtn);
        signout=findViewById(R.id.logout_imbtn);
        add=findViewById(R.id.add_money_imbtn);
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
        userList=new ArrayList<>();
        userList.add(new ModelClass(R.drawable.ic_baseline_add_24,"ABC","10","100"));


    }

    private void initRecyclerView() {
        recyclerView=findViewById(R.id.recycler_view);
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new Adapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}