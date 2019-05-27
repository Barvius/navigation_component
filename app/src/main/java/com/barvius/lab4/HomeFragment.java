package com.barvius.lab4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class HomeFragment extends Fragment {


    public HomeFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        if (getArguments() != null) {

//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button buttonTest = (Button) view.findViewById(R.id.btnStart);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] list = {"РУС -> ENG", "ENG -> РУС"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Язык");

                builder.setSingleChoiceItems(list, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("direction", list[which]);
                        NavController navController = Navigation.findNavController(getActivity(), R.id.m_fragment);
                        navController.navigate(R.id.action_test_start,bundle);
                    }
                });

                builder.setCancelable(true);
                builder.create().show();
            }
        });
        Button buttonArchive = (Button) view.findViewById(R.id.to_archive);
        buttonArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.m_fragment);
                navController.navigate(R.id.action_home_archive);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(0, 1, 0, "Добавить слово").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                addDialog();
                return true;
            }
        });



        menu.add(0, 2, 0, "Очистить архив").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LoadURL.getInstance().truncateArchive();
                return true;
            }
        });

        menu.add(0, 3, 0, "Выход").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ConfigManager.logOut(getContext());
                NavController navController = Navigation.findNavController(getActivity(), R.id.m_fragment);
                navController.navigate(R.id.action_homeFragment_to_loginFragment);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    protected void addDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Добавление слова в словарь");

        final EditText ru = new EditText(getContext());
        ru.setHint("ru");
        ru.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    if (!s.toString().matches("[а-я]+")) {
                        s.delete(s.length()-1,s.length());
                    }
                }
            }
        });

        final EditText en = new EditText(getContext());
        en.setHint("en");
        en.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    if (!s.toString().matches("[a-z]+")) {
                        s.delete(s.length()-1,s.length());
                    }
                }
            }
        });

        LinearLayout lay = new LinearLayout(getContext());
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(ru);
        lay.addView(en);
        builder.setView(lay);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoadURL.getInstance().addItems(new DBItems(ru.getText().toString(),en.getText().toString()));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}