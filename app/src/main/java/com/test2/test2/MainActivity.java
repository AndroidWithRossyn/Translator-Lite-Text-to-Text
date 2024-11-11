package com.test2.test2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.test2.test2.favRoom.DatabaseClient;
import com.test2.test2.favRoom.Fav;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private ImageView btnCopy, btnReplace, btnFav, btnSaveFav,btnMenu;
    private SharedPreferences sharedPreferences;
    List<Languages> languagesList = new ArrayList<>();
    SpinKitView spinKit;
    private TextInputLayout inputTranslation;
    private LinearLayout contentTo;
    private TextInputEditText txtTranslation;
    private TextView txtWelcome, txtWhatYouDo , btnTranslate, title , btnLangOne, btnLangTwo, displayTranslate, dLang1, dLang2;
    private String MyPreferences = "MyPREFERENCES";
    private String firstTranslation = "";
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        mAdView = findViewById(R.id.adView);
                            MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
                                @Override
                                public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                                    banner();
                                    interstitial();
                                }
                            });

        txtTranslation = findViewById(R.id.txtTranslation);
        txtWelcome = findViewById(R.id.txtWelcome);
        txtWhatYouDo = findViewById(R.id.txtWhatYouDo);
        btnTranslate = findViewById(R.id.btnTranslate);
        contentTo = findViewById(R.id.contentTo);
        inputTranslation = findViewById(R.id.inputTranslation);
        title = findViewById(R.id.title);
        btnLangTwo = findViewById(R.id.btnLangTwo);
        btnLangOne = findViewById(R.id.btnLangOne);
        displayTranslate = findViewById(R.id.displayTranslate);
        spinKit = findViewById(R.id.spinKit);
        dLang1 = findViewById(R.id.dLang1);
        dLang2 = findViewById(R.id.dLang2);
        btnCopy = findViewById(R.id.btnCopy);
        btnReplace = findViewById(R.id.btnReplace);
        btnFav = findViewById(R.id.btnFav);
        btnSaveFav = findViewById(R.id.btnSaveFav);
        btnMenu = findViewById(R.id.btnMenu);

        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);

        Bundle list = null;
        if (getIntent().hasExtra("data")){
             list = getIntent().getExtras().getBundle("data");
             ArrayList<String> myl = getIntent().getExtras().getStringArrayList("data");
                if (myl.get(3).equals("lang1")){
                    SharedPreferences.Editor editor3 = sharedPreferences.edit();
                    editor3.putString("code1", myl.get(0));
                    editor3.putString("lang1",  myl.get(1));
                    editor3.apply();
                }else {
                    SharedPreferences.Editor editor2 = sharedPreferences.edit();
                    editor2.putString("code2",  myl.get(0));
                    editor2.putString("lang2",  myl.get(1));
                    editor2.apply();
                }

        }else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("code1", "ar");
            editor.putString("lang1", "ARABIC");
            editor.putString("code2", "en");
            editor.putString("lang2", "ENGLISH");
            editor.apply();
        }
        feelGoodNow(list);



        txtTranslation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    txtWelcome.setVisibility(View.GONE);
                    txtWhatYouDo.setVisibility(View.GONE);
                }else {
                    txtWelcome.setVisibility(View.VISIBLE);
                    txtWhatYouDo.setVisibility(View.VISIBLE);
                }
            }
        });
        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTranslation.getText().toString().isEmpty()){
                    YoYo.with(Techniques.Shake).duration(500).repeat(1).playOn(inputTranslation);
                }else{
                    String name = btnTranslate.getText().toString();
                    if (name.equals(getResources().getString(R.string.translate))){
                        spinKit.setVisibility(View.VISIBLE);
                        Sprite doubleBounce = new WanderingCubes();
                        spinKit.setIndeterminateDrawable(doubleBounce);
                        YoYo.with(Techniques.SlideOutDown).duration(500).repeat(0).playOn(btnTranslate);
                        //new TranslationTask().execute(txtTranslation.getText().toString());

                        translationWithFirebase();

                     }else {
                        btnTranslate.setText(R.string.translate);
                        contentTo.setVisibility(View.GONE);
                        inputTranslation.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.BounceInRight).duration(1000).repeat(0).playOn(inputTranslation);
                    }
                    YoYo.with(Techniques.Shake).duration(500).repeat(0).playOn(txtWelcome);
                    YoYo.with(Techniques.BounceInRight).duration(500).repeat(0).playOn(title);
                    YoYo.with(Techniques.BounceInDown).duration(500).repeat(0).playOn(btnTranslate);
                }
            }
        });
        YoYo.with(Techniques.FadeIn).duration(1000).repeat(0).playOn(title);
        YoYo.with(Techniques.BounceInDown).duration(500).repeat(0).playOn(btnTranslate);
        btnLangOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChooseLanguageActivity.class);
                intent.putExtra("who","lang1");
                startActivity(intent);
            }
        });
        btnLangTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChooseLanguageActivity.class);
                intent.putExtra("who","lang2");
                startActivity(intent);
            }
        });
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(displayTranslate.getText());
                YoYo.with(Techniques.RollIn).duration(500).repeat(0).playOn(btnCopy);

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                }

            }
        });
        btnReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("MyPREFERENCES",MODE_PRIVATE);
                String s1 = prefs.getString("lang1",null);
                String s2 = prefs.getString("lang2",null);
                String c1 = prefs.getString("code1",null);
                String c2 = prefs.getString("code2",null);
                sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("code1", c2);
                editor.putString("lang1", s2);
                editor.putString("code2", c1);
                editor.putString("lang2", s1);
                editor.apply();
                SharedPreferences prefs2 = getSharedPreferences("MyPREFERENCES",MODE_PRIVATE);
                String s11 = prefs2.getString("lang1",null);
                String s22 = prefs2.getString("lang2",null);
                btnLangOne.setText(s11);
                btnLangTwo.setText(s22);
                YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(btnLangOne);
                YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(btnLangTwo);
            }
        });
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FavActivity.class));
            }
        });
        btnSaveFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                }
                saveFav();
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MenuActivity.class));
            }
        });
    }




    private void translationWithFirebase(){


        SharedPreferences prefs = getSharedPreferences("MyPREFERENCES",MODE_PRIVATE);
        String c1 = prefs.getString("code1",null);
        String c2 = prefs.getString("code2",null);

        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(c1)
                        .setTargetLanguage(c2)
                        .build();


        final Translator translator = Translation.getClient(options);


        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Log.d("TAG00", "onSuccess: " + o);

                                translator.translate(txtTranslation.getText().toString())
                                        .addOnSuccessListener(
                                                new OnSuccessListener() {
                                                    @Override
                                                    public void onSuccess(Object o) {
                                                        Log.d("TAG00", "onSuccess2: " + o);


                                                        displayTranslate.setText(o.toString());
                                                        btnTranslate.setText(R.string.back);
                                                        contentTo.setVisibility(View.VISIBLE);
                                                        inputTranslation.setVisibility(View.GONE);
                                                        YoYo.with(Techniques.SlideInLeft).duration(500).repeat(0).playOn(contentTo);
                                                        spinKit.setVisibility(View.GONE);
                                                        SharedPreferences prefs = getSharedPreferences("MyPREFERENCES",MODE_PRIVATE);
                                                        String l1 = prefs.getString("lang1",null);
                                                        String l2 = prefs.getString("lang2",null);
                                                        dLang1.setText(l1);
                                                        dLang2.setText(l2);
                                                        YoYo.with(Techniques.RollIn).duration(1000).repeat(0).playOn(btnSaveFav);


                                                    }
                                                })
                                        .addOnFailureListener(
                                                new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("TAG00", "onFailure2: " + e.getMessage());
                                                    }
                                                });



                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG00", "onFailure: " + e.getMessage());
                            }
                        });



    }







    private void interstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,getResources().getString(R.string.interstitial), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }
    private void banner() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                

            }
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {



            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdClosed() {
            }
        });
    }
    private void saveFav() {
        class SaveFav extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                Fav fav = new Fav();
                fav.setLang1Name(dLang1.getText().toString());
                fav.setLang2Name(dLang2.getText().toString());
                fav.setLang1Text(txtTranslation.getText().toString());
                fav.setLang2Text(displayTranslate.getText().toString());
                DatabaseClient.getInstance(getApplicationContext())
                        .getMyDatabase().favDao().insert(fav);
                return null;
            }
            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                YoYo.with(Techniques.RollOut).duration(500).repeat(0).playOn(btnSaveFav);
            }
        }
        SaveFav saveFav = new SaveFav();
        saveFav.execute();

    }

    private void feelGoodNow(Bundle list) {
        SharedPreferences prefs = getSharedPreferences("MyPREFERENCES",MODE_PRIVATE);
        if (getIntent().hasExtra("data")) {
            String s1 = prefs.getString("lang1",null);
            btnLangOne.setText(s1);
            String s2 = prefs.getString("lang2",null);
            btnLangTwo.setText(s2);
        }else {
            languagesList.add(new Languages(prefs.getString("code1",null), prefs.getString("lang1",null), ""));
            languagesList.add(new Languages(prefs.getString("code2",null), prefs.getString("lang2",null), ""));
            btnLangOne.setText(languagesList.get(0).getLang());
            btnLangTwo.setText(languagesList.get(1).getLang());
        }

    }


}