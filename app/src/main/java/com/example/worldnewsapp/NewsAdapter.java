package com.example.worldnewsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<News> newsList;

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList != null ? newsList : new ArrayList<>();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());

        // Ajouter un onClickListener sur l'élément
        holder.itemView.setOnClickListener(v -> {
            // Créer un Intent pour ouvrir DetailNewsActivity
            Context context = v.getContext();
            Intent intent = new Intent(context, DetailNewsActivity.class);

            // Passer l'objet News complet via l'Intent (assurez-vous que News est sérialisable)
            intent.putExtra("news", news);

            // Démarrer l'activité
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newsList != null ? newsList.size() : 0;
    }

    public void updateNewsList(List<News> newNewsList) {
        this.newsList = newNewsList != null ? newNewsList : new ArrayList<>();
        notifyDataSetChanged();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;

        public NewsViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }
    }
}
