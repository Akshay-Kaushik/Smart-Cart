package com.example.smartcart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.errorprone.annotations.Var;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Balance extends AppCompatActivity {
    ImageButton home,signout,cart;
    AlertDialog.Builder builder;
    private FirebaseAuth mAuth;
    Button button;
    EditText amount;
    TextView balance_field;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        home=findViewById(R.id.home_imbtn);
        signout=findViewById(R.id.logout_imbtn);
        cart=findViewById(R.id.cart_imbtn);
        amount=findViewById(R.id.amount_enter);
        balance_field=findViewById(R.id.balance_text);
        balance_field.setText("Current Balance: "+ Variables.balance);
        mAuth = FirebaseAuth.getInstance();
        button=findViewById(R.id.button);//Add Money
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount.getText().toString()!=null){
                    myRef.child(mAuth.getUid()).child("Balance").setValue(String.valueOf(Double.parseDouble(amount.getText().toString())+Variables.balance));
                    Variables.balance+=Double.parseDouble(amount.getText().toString());
                    balance_field.setText("Current Balance: "+ Variables.balance);
                }

            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Balance.this, MainActivity.class);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Balance.this, cart.class);
                startActivity(intent);
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(Balance.this);
                builder.setMessage("Are you sure you want to log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Balance.this, loginActivity.class);
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
}