package com.test2.test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

public class ChooseLanguageActivity extends AppCompatActivity {

    List<Languages> myLangList;
    RecyclerView recyclerView;
    AdapterLangs adapterLangs;
    ImageView btnBack, btnFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        myLangList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewLanguage);
        btnBack = findViewById(R.id.btnBack);
        btnFav = findViewById(R.id.btnFav);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChooseLanguageActivity.this));
        myLangList = Data.data();

        if (getIntent().hasExtra("who")){
            String who = getIntent().getExtras().getString("who");
            adapterLangs = new AdapterLangs(ChooseLanguageActivity.this,myLangList,who);
        }else {
            adapterLangs = new AdapterLangs(ChooseLanguageActivity.this,myLangList);
        }
        recyclerView.setAdapter(adapterLangs);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),FavActivity.class));
            }
        });

    }


}