package com.example.smartcart.;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {
    EditText firstname,lastname,email,phone,pass,confirm;
    Button signup;
    float v=0;
    @Override
    public View onCreateView(@org.jetbrains.annotations.NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root =(ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container, false);

        firstname=root.findViewById(R.id.firstname);
        lastname=root.findViewById(R.id.lastname);
        phone=root.findViewById(R.id.phone);
        email=root.findViewById(R.id.email);
        pass=root.findViewById(R.id.pass);
        confirm=root.findViewById(R.id.confirm);
        signup=root.findViewById(R.id.signup);

//        firstname.setTranslationX(800);
//        lastname.setTranslationX(800);
//        phone.setTranslationX(800);
//        email.setTranslationX(800);
//        pass.setTranslationX(800);
//        confirm.setTranslationX(800);
//        signup.setTranslationX(800);

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
}
