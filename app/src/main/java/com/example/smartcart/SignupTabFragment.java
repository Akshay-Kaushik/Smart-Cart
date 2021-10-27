package com.example.smartcart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupTabFragment extends Fragment {
    EditText firstname, lastname, email, phone, pass, confirm;
    Button signup;
    float v = 0;
    private FirebaseAuth mAuth;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    String full_name, password, phone_number, mail;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String MobilePattern = "[0-9]{10}";

    @Override
    public View onCreateView(@org.jetbrains.annotations.NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firstname = root.findViewById(R.id.firstname);
        lastname = root.findViewById(R.id.lastname);
        phone = root.findViewById(R.id.phone);
        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pass);
        confirm = root.findViewById(R.id.confirm);
        signup = root.findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    firebase_register();
                }
            }
        });

        firstname.setAlpha(v);
        lastname.setAlpha(v);
        phone.setAlpha(v);
        email.setAlpha(v);
        pass.setAlpha(v);
        confirm.setAlpha(v);
        signup.setAlpha(v);

        firstname.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(100).start();
        lastname.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(200).start();
        phone.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        confirm.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(600).start();
        signup.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }

    public boolean validate() {
        full_name = firstname.getText().toString() + " " + lastname.getText().toString();
        phone_number = phone.getText().toString();
        mail = email.getText().toString();
        password = pass.getText().toString();
        Log.d("a", full_name);

        if (full_name.length() == 1) {
            firstname.setError("Name cannot be empty");
            lastname.setError("Name cannot be empty");
            return false;
        } else if (!phone_number.matches(MobilePattern)) {
            phone.setError("Enter Correct Mobile Number");
            return false;
        } else if (!mail.trim().matches(emailPattern)) {
            email.setError("Enter Correct Email");
            return false;
        } else if (password.isEmpty()) {
            pass.setError("Password cannot be empty");
            return false;
        } else if (password.length() < 6) {
            pass.setError("Password should contain atleast 6characters");
        } else if (confirm.getText().toString().isEmpty()) {
            confirm.setError("Confirm your password");
            return false;
        } else if (!password.equals(confirm.getText().toString())) {
            confirm.setError("Make sure you confirm your password correctly!");
            return false;
        } else {
            return true;
        }

        return false;
    }

    public void firebase_register() {
        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("abc", mAuth.toString());
                            Toast.makeText(getActivity(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                            store_info();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Verification Email Sent !", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(getActivity(), "Email already registered or some error occurred!",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }



    public void store_info() {
        myRef.child(mAuth.getUid()).child("Full Name").setValue(full_name);
        myRef.child(mAuth.getUid()).child("Email").setValue(mail);
        myRef.child(mAuth.getUid()).child("Phone").setValue(phone_number);
        myRef.child(mAuth.getUid()).child("Balance").setValue(0.0);
    }
}
