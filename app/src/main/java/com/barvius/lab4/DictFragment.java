package com.barvius.lab4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;


public class DictFragment extends Fragment {
    private List<DBItems> itemsList = new ArrayList<>();
    public DictFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsList = LoadURL.getInstance().getDict();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dict, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button button = (Button) view.findViewById(R.id.to_home_menu_d);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.m_fragment);
                navController.navigate(R.id.action_dictFragment_to_homeFragment);
            }
        });


        ListView list = (ListView) view.findViewById(R.id.archive_list_d);
        ArrayAdapter<DBItems> adapter = new ArrayAdapter<DBItems>(getContext(), android.R.layout.simple_list_item_1, this.itemsList);
        list.setAdapter(adapter);
//        registerForContextMenu(list);
    }

}
