package com.test2.test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.test2.test2.favRoom.AdapterFav;
import com.test2.test2.favRoom.DatabaseClient;
import com.test2.test2.favRoom.Fav;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FavActivity extends AppCompatActivity {

    RecyclerView recyclerViewFav;
    public AdapterFav adapterFav;
    List<Fav> favList = new ArrayList<>();
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        btnBack = findViewById(R.id.btnBack);
        recyclerViewFav = findViewById(R.id.recyclerViewFav);
        recyclerViewFav.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        getFavs();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void getFavs() {
        class GetFavs extends AsyncTask<Void, Void, List<Fav>>{
            @Override
            protected List<Fav> doInBackground(Void... voids) {

                return DatabaseClient.getInstance(getApplicationContext())
                        .getMyDatabase().favDao().getAll();
            }
            @Override
            protected void onPostExecute(List<Fav> favs) {
                super.onPostExecute(favs);
                favList = favs;
                Collections.reverse(favList);
                adapterFav = new AdapterFav(getApplicationContext(),favList);
                recyclerViewFav.setAdapter(adapterFav);
            }
        }
        GetFavs getFavs = new GetFavs();
        getFavs.execute();
    }
}