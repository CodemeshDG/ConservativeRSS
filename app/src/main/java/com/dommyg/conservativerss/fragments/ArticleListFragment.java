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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dommyg.conservativerss.R;
import com.dommyg.conservativerss.databinding.FragmentArticleListBinding;
import com.dommyg.conservativerss.viewmodels.ArticleListViewModel;

public class ArticleListFragment extends Fragment {
    private FragmentArticleListBinding binding;
    private ArticleListViewModel viewModel;

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
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getActivity().getApplication()))
                .get(ArticleListViewModel.class);

        subscribeObservers();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Set the interface instance so that this fragment can communicate with other fragments.
    }

    private void subscribeObservers() {
        viewModel.getViewState().observe(this, new Observer<ArticleListViewModel.ViewState>() {
            @Override
            public void onChanged(ArticleListViewModel.ViewState viewState) {
                if (viewState != null) {
                    switch (viewState) {
                        case SOURCE:

                            break;
                        case SEARCH:

                            break;
                    }
                }
            }
        });
    }
}
