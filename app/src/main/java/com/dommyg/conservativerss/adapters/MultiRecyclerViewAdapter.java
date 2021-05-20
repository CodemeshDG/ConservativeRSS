package com.dommyg.conservativerss.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dommyg.conservativerss.R;
import com.dommyg.conservativerss.databinding.FragmentIndividualArticleBinding;
import com.dommyg.conservativerss.models.Article;

import java.util.ArrayList;
import java.util.List;

public class MultiRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ARTICLE = 1;
    private static final int TYPE_SOURCE_HEADER = 2;
    private static final int TYPE_SOURCE_MENU = 3;
    private static final int TYPE_ARTICLE_SEARCH = 4;
    private static final int TYPE_SETTINGS_MENU = 5;
    private static final int TYPE_LOADING = 6;
    private static final int TYPE_EXHAUSTED = 7;
    private static final int TYPE_ERROR = 8;

    private List<Object> displayItems;
    private OnDisplayItemClickListener listener;

    public MultiRecyclerViewAdapter(OnDisplayItemClickListener listener) {
        initDisplayItems();
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
//            case TYPE_ARTICLE:
            default:
                FragmentIndividualArticleBinding binding = FragmentIndividualArticleBinding.inflate(
                        inflater, parent, false);
                return new ArticleViewHolder(binding, listener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        ((ArticleViewHolder)holder).onBind((Article) displayItems.get(position));
    }

    @Override
    public int getItemCount() {
        return displayItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (displayItems.get(position) instanceof Article) {
            return TYPE_ARTICLE;
        } else {
            return TYPE_ERROR;
        }
    }

    public void setDisplayItems(List<Object> displayItems) {
        this.displayItems = displayItems;
        notifyDataSetChanged();
    }

    private void initDisplayItems() {
        if (isDisplayItemsNull()) {
            displayItems = new ArrayList<>();
        }
    }

    private boolean isDisplayItemsNull() {
        return displayItems == null;
    }
}
