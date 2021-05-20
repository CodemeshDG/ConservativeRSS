package com.dommyg.conservativerss.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dommyg.conservativerss.R;
import com.dommyg.conservativerss.activities.MainActivity;
import com.dommyg.conservativerss.adapters.MultiRecyclerViewAdapter;
import com.dommyg.conservativerss.adapters.OnDisplayItemClickListener;
import com.dommyg.conservativerss.databinding.FragmentArticleListBinding;
import com.dommyg.conservativerss.models.Article;
import com.dommyg.conservativerss.util.MarginDecoration;
import com.dommyg.conservativerss.util.Resource;
import com.dommyg.conservativerss.viewmodels.ArticleListViewModel;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static android.content.ContentValues.TAG;

public class ArticleListFragment extends Fragment {
    private FragmentArticleListBinding binding;
    private ArticleListViewModel viewModel;
    private MultiRecyclerViewAdapter adapter;

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
        binding.setFragment(this);
        initRecyclerView();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Set the interface instance so that this fragment can communicate with other fragments.
    }

    private void subscribeObservers() {
        viewModel.getArticles().observe(this, new Observer<Resource<List<Article>>>() {
            @Override
            public void onChanged(Resource<List<Article>> listResource) {
                if (listResource != null) {
                    Log.d(TAG, "onChanged: status " + listResource.status);

                    if (listResource.status == Resource.Status.ERROR) {
                        Log.d(TAG, "onChanged: error message --- " + listResource.message);
                    } else if (listResource.data != null) {
                        adapter.setDisplayItems((List)listResource.data);
                    }
                }
            }
        });

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

    private void initRecyclerView() {
        RecyclerView recyclerView = binding.recyclerViewArticles;
        adapter = new MultiRecyclerViewAdapter((MainActivity)getActivity());

        // Sets ItemDecoration to give space between items in the recyclerViewArticles.
        Drawable divider = Objects.requireNonNull(
                getActivity()).getDrawable(R.drawable.divider_white);
        MarginDecoration decoration = new MarginDecoration(divider);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // TODO: Add Glide image handling configuration code here.

        recyclerView.setAdapter(adapter);
    }

    public void testApiCall() {
        viewModel.retrieveRss("1");
    }
}
