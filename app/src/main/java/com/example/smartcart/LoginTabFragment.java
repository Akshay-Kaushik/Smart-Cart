package com.example.smartcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

public class LoginTabFragment extends Fragment {
    EditText email,pass;
    Button login;
    TextView forgetPass;
    float v=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root =(ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container, false);

        email=root.findViewById(R.id.email);
        pass=root.findViewById(R.id.pass);
        forgetPass=root.findViewById(R.id.forgetPass);
        login=root.findViewById(R.id.login);
//
//        email.setTranslationX(0);
//        pass.setTranslationX(0);
//        forgetPass.setTranslationX(0);
//        login.setTranslationX(0);

        email.setAlpha(v);
        pass.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }
}
