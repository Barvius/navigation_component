package com.barvius.lab4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginFragment extends Fragment {
    private final String LOGIN = "admin";
    private final String PASSWORD = "E10ADC3949BA59ABBE56E057F20F883E".toLowerCase();

    public LoginFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        autologin();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final TextView l = view.findViewById(R.id.login_f);
        final TextView p = view.findViewById(R.id.password_f);

        Button buttonArchive = (Button) view.findViewById(R.id.button_login);
        buttonArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l.getText().toString().equals(LOGIN) && PASSWORD.equals(md5(p.getText().toString()))){
                    ConfigManager.setSignIn(getContext());
                    NavController navController = Navigation.findNavController(getActivity(), R.id.m_fragment);
                    navController.navigate(R.id.action_loginFragment_to_homeFragment);
                } else {
                    Toast.makeText(getContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void autologin(){
        if(ConfigManager.isSignIn(getActivity())){
            NavController navController = Navigation.findNavController(getActivity(), R.id.m_fragment);
            navController.navigate(R.id.action_loginFragment_to_homeFragment);
        }
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}