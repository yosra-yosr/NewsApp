package com.example.worldnewsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class DetailNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        // Initialiser les vues
        ImageView detailImage = findViewById(R.id.detailImage);
        TextView detailTitle = findViewById(R.id.detailTitle);
        TextView detailContent = findViewById(R.id.detailContent);
        Button shareButton = findViewById(R.id.shareButton);

        // Récupérer l'objet News sérialisé passé via l'Intent
        News news = (News) getIntent().getSerializableExtra("news");

        if (news != null) {
            // Afficher les détails de l'actualité
            detailTitle.setText(news.getTitle());
            detailContent.setText(news.getDescription());
            Picasso.get().load(news.getUrlToImage()).into(detailImage);

            // Bouton de partage
            shareButton.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, news.getUrl());
                startActivity(Intent.createChooser(shareIntent, "Partager via"));
            });
        }
    }
}
