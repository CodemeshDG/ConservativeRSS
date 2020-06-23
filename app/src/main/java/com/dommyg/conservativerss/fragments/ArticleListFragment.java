package com.dommyg.conservativerss.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dommyg.conservativerss.R;
import com.dommyg.conservativerss.databinding.FragmentArticleListBinding;

public class ArticleListFragment extends Fragment {
    private FragmentArticleListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // When using data binding on a fragment layout, you must call DataBindingUtil.inflate()
        // instead of DataBindingUtil.setContentView() used in activities.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_list, container,
                false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Retrieve the ViewModel.
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Set the interface instance so that this fragment can communicate with other fragments.
    }
}
