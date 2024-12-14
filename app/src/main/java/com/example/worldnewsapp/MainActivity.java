package com.example.worldnewsapp;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private String[] categories = {
            "business", "entertainment", "general", "health", "science", "sports", "technology"
    };
    private LinearLayout categoryLayout;
    private Button selectedButton; // Bouton actuellement sélectionné

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation du RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

// Applique l'espacement entre les éléments du RecyclerView
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL) {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 16; // Définit l'espacement entre les cartes (16dp)
            }
        });

        adapter = new NewsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Récupérer le LinearLayout où les boutons de catégories seront ajoutés
        categoryLayout = findViewById(R.id.categoryLayout);

        // Dynamically create category buttons
        for (String category : categories) {
            // Création du bouton
            Button categoryButton = new Button(this);
            categoryButton.setText(category);
            categoryButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            // Définition des marges pour l'espacement entre les boutons
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) categoryButton.getLayoutParams();
            params.setMargins(16, 0, 16, 0);  // Marge gauche, droite (et optionnellement haut et bas)
            categoryButton.setLayoutParams(params);

            categoryButton.setPadding(16, 8, 16, 8);
            categoryButton.setTextColor(getResources().getColor(android.R.color.black));

            // Utilisation du drawable personnalisé pour les états
            categoryButton.setBackgroundResource(R.drawable.category_button);  // Selector personnalisé pour les boutons

            // Application de l'ombre avec l'élévation
            categoryButton.setElevation(8f);  // Ajuste cette valeur pour contrôler l'ombre

            categoryButton.setAllCaps(false);

            // Gestion du clic sur le bouton pour sélectionner la catégorie
            categoryButton.setOnClickListener(v -> {
                // Si un bouton était déjà sélectionné, on le désélectionne
                if (selectedButton != null) {
                    selectedButton.setSelected(false);  // Désélectionner le bouton précédent
                }

                // Sélectionner le nouveau bouton
                categoryButton.setSelected(true);
                selectedButton = categoryButton;

                // Charger les actualités pour la catégorie sélectionnée
                String selectedCategory = categoryButton.getText().toString();
                fetchNewsByCategory(selectedCategory);
            });

            // Ajouter le bouton au LinearLayout
            categoryLayout.addView(categoryButton);
        }


        // Vérification de la connexion à Internet avant de récupérer les actualités
        if (NetworkUtil.isConnectedToInternet(this)) {
            fetchNewsByCategory("general"); // Par défaut, charger les actualités générales
        } else {
            Toast.makeText(this, "Pas de connexion Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchNewsByCategory(String category) {
        String apiKey = "d97b805120384a42b76c813f9c1ce605";
        NewsApiService apiService = RetrofitClient.getInstance().create(NewsApiService.class);

        // Appel à l'API pour obtenir les actualités de la catégorie
        Call<NewsResponse> call = apiService.getTopHeadlines(category, apiKey);

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<News> newsList = response.body().getArticles();
                    if (newsList != null && !newsList.isEmpty()) {
                        // Mettre à jour la liste des actualités dans l'adaptateur
                        adapter.updateNewsList(newsList);
                    } else {
                        Toast.makeText(MainActivity.this, "Aucune actualité disponible", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Erreur lors de la récupération des actualités", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
