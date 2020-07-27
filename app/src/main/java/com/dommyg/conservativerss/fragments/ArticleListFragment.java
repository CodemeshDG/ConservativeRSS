package com.dommyg.conservativerss.fragments;

import android.content.Context;
import android.os.AsyncTask;
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

import com.dommyg.conservativerss.R;
import com.dommyg.conservativerss.databinding.FragmentArticleListBinding;
import com.dommyg.conservativerss.models.Article;
import com.dommyg.conservativerss.requests.responses.RssResponse;
import com.dommyg.conservativerss.requests.ServiceGenerator;
import com.dommyg.conservativerss.util.Resource;
import com.dommyg.conservativerss.viewmodels.ArticleListViewModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

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
        binding.setFragment(this);
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
                        for (Article article : listResource.data) {
                            Log.d(TAG, "onChanged: " + article.toString());
                        }
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

    public void testApiCall() {
        viewModel.retrieveRss("1");
    }
}
