package com.grsu.runa.buttonNav.newsFeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.grsu.runa.databinding.FragmentNewsFeedBinding;

public class NewsFeedFragment extends Fragment {
    FragmentNewsFeedBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentNewsFeedBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
