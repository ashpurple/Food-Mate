package com.example.foodmate.ui.recruited;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodmate.R;

public class RecruitedFragment extends Fragment {

    private RecruitedViewModel recruitedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recruitedViewModel =
                new ViewModelProvider(this).get(com.example.foodmate.ui.recruited.RecruitedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recruited, container, false);
        final TextView textView = root.findViewById(R.id.text_recruited);
        recruitedViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;

    }
}