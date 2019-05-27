package com.barvius.lab4;

import android.os.Bundle;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestFragment extends Fragment {
    private String directionTranslate;
    private boolean trueAnswer;
    private DBItems currentAnswer;

    public TestFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            directionTranslate = getArguments().getString("direction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button buttonArchive = (Button) view.findViewById(R.id.to_dict);
        buttonArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.m_fragment);
                navController.navigate(R.id.action_test_archive);
            }
        });

        Button buttonHome = (Button) view.findViewById(R.id.to_home_menu);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.m_fragment);
                navController.navigate(R.id.action_test_home);
            }
        });

        createTest(view);
    }

    protected void createTest(final View view){
        if (!LoadURL.getInstance().dictionaryIsAvailable()){
            Toast toast = Toast.makeText(getContext(),
                    "Словарь пуст, добавьте слова!", Toast.LENGTH_SHORT);
            toast.show();
            NavController navController = Navigation.findNavController(getActivity(), R.id.m_fragment);
            navController.navigate(R.id.action_test_home);
            return;
        }
        if (!LoadURL.getInstance().testIsAvailable()){
            Toast toast = Toast.makeText(getContext(),
                    "Все слова изучены, очистете архив!", Toast.LENGTH_SHORT);
            toast.show();
            NavController navController = Navigation.findNavController(getActivity(), R.id.m_fragment);
            navController.navigate(R.id.action_test_home);
            return;
        }


        List<Button> buttons = new ArrayList<Button>();
        LinearLayout buttonContainer = (LinearLayout) view.findViewById(R.id.answer_button);
        buttonContainer.removeAllViews();
        setTrueAnswer(true);
        currentAnswer = LoadURL.getInstance().getRandomItems();

        TextView text = view.findViewById(R.id.masterText);
        text.setText(getLabelFromDirection(currentAnswer));

        final Button btmpm = new Button(getContext());
        btmpm.setText(getButtonFromDirection(currentAnswer));
        btmpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                if(isTrueAnswer()){
                    LoadURL.getInstance().moveToArchive(currentAnswer);
                }
                createTest(view);
            }
        });
        buttons.add(btmpm);

        for (DBItems i : LoadURL.getInstance().getRandomSet(5, currentAnswer.getId())) {
            Button btmps = new Button(getContext());
            btmps.setText(getButtonFromDirection(i));
            btmps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(View.GONE);
                    setTrueAnswer(false);
                }
            });
            buttons.add(btmps);
        }

        Collections.shuffle(buttons);
        for (Button i :buttons) {
            buttonContainer.addView(i);
        }

    }

    public boolean isTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(boolean trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    String getLabelFromDirection(DBItems items){
        if (directionTranslate.equals("РУС -> ENG")){
            return items.getRu();
        } else {
            return items.getEn();
        }
    }

    String getButtonFromDirection(DBItems items){
        if (directionTranslate.equals("РУС -> ENG")){
            return items.getEn();
        } else {
            return items.getRu();
        }
    }
}
