package com.example.smartcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class cart extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<ModelClass> userList;
    Adapter adapter;
    String rice="rice";
    ImageButton imBtn;
    TextView cost_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cost_text=findViewById(R.id.cost);
        initData();
        initRecyclerView();
        cost_text.setText(String.valueOf(Variables.price));
        imBtn=findViewById(R.id.home_imbtn);
        imBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://nutritionix-api.p.rapidapi.com/v1_1/search/"+rice+"?fields=item_name%2Citem_id%2Cbrand_name%2Cnf_calories%2Cnf_total_fat")
                        .get()
                        .addHeader("x-rapidapi-host", "nutritionix-api.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "3d39c04950msh3d47403afe51807p1855d7jsn77a67ff0aaea")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    Log.d("Response",response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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