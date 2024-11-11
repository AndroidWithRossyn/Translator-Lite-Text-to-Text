package com.test2.test2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterLangs extends RecyclerView.Adapter<AdapterLangs.MyViewHolder> {

    private Context context;
    private List<Languages> languages;
    String who;
    private InterstitialAd mInterstitialAd;

    public AdapterLangs(Context context, List<Languages> languages) {
        this.context = context;
        this.languages = languages;
    }

    public AdapterLangs(Context context, List<Languages> languages, String who) {
        this.context = context;
        this.languages = languages;
        this.who = who;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public AdapterLangs.MyViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_item, parent, false);

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, context.getResources().getString(R.string.interstitial), adRequest,
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


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull AdapterLangs.MyViewHolder holder, int position) {
        Languages lang = languages.get(position);
        Glide.with(context).load(lang.getUrlImag()).placeholder(R.drawable.star2).into(holder.icon);
        holder.title.setText(lang.getLang());





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInterstitialAd != null) {
                    mInterstitialAd.show((Activity) context);
                }

                KAlertDialog kAlertDialog = new KAlertDialog(v.getRootView().getContext(), KAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Are you sure you to select this language :" + lang.getLang())
                        .setConfirmText("Yes, select it!")
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {

                                Intent intent = new Intent(context,MainActivity.class);
                                ArrayList<String> list = new ArrayList<>();
                                list.add(lang.getCode());
                                list.add(lang.getLang());
                                list.add(lang.getUrlImag());
                                list.add(who);
                                intent.putExtra("data",list);
                                intent.putExtra("ads","show");
                                context.startActivity(intent);

                                sDialog.dismissWithAnimation();
                            }
                        });
                kAlertDialog.show();




            }
        });

    }


    @Override
    public int getItemCount() {
        return languages.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView icon;
        private TextView title;
        public MyViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
        }
    }
}
