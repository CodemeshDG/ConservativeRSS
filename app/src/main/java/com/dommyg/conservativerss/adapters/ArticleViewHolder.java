package com.dommyg.conservativerss.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dommyg.conservativerss.databinding.FragmentIndividualArticleBinding;
import com.dommyg.conservativerss.models.Article;

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    OnDisplayItemClickListener listener;
    FragmentIndividualArticleBinding binding;

    public ArticleViewHolder(FragmentIndividualArticleBinding binding,
                             OnDisplayItemClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public void onBind(Article article) {
        binding.setArticle(article);
        // Force the binding to update data synchronously in order to prevent wrong row size
        // measurements and potentially missing content.
        binding.executePendingBindings();
    }

    public void onClick() {

    }
}
