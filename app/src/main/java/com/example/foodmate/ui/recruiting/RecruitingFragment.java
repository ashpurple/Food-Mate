package com.example.foodmate.ui.recruiting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodmate.R;
import com.example.foodmate.ui.recruiting.RecruitingViewModel;

public class RecruitingFragment extends Fragment {

    private RecruitingViewModel recruitingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recruitingViewModel =
                new ViewModelProvider(this).get(com.example.foodmate.ui.recruiting.RecruitingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recruiting, container, false);
        final TextView textView = root.findViewById(R.id.text_recruiting);
        recruitingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
