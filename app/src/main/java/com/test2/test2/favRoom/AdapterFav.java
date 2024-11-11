package com.test2.test2.favRoom;



import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.test2.test2.Data;
import com.test2.test2.Languages;
import com.test2.test2.MainActivity;
import com.test2.test2.R;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterFav extends RecyclerView.Adapter<AdapterFav.MyViewHolder> {

    private final Context context;
    List<Fav> favList;

    List<Languages> languagesList;

    public AdapterFav(Context context, List<Fav> favList) {
        this.context = context;
        this.favList = favList;

        languagesList = Data.data();

    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterFav.MyViewHolder holder, int position) {

        Fav fav = favList.get(position);

        for (int x=0; x<languagesList.size(); x++){
            if (fav.getLang1Name().equals(languagesList.get(x).getLang())){
                Glide.with(context).load(languagesList.get(x).getUrlImag()).into(holder.lang1Icon);
                holder.titleLang1.setText(fav.getLang1Name());
                holder.txtLang1.setText(fav.getLang1Text());
            }
            if (fav.getLang2Name().equals(languagesList.get(x).getLang())){
                Glide.with(context).load(languagesList.get(x).getUrlImag()).into(holder.lang2Icon);
                holder.titleLang2.setText(fav.getLang2Name());
                holder.txtLang2.setText(fav.getLang2Text());
            }
        }

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KAlertDialog kAlertDialog = new KAlertDialog(v.getRootView().getContext(), KAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Are you sure you to delete this item!")
                        .setConfirmText("Yes, delete it!")
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                deleteFav(fav);
                                context.startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                sDialog.dismissWithAnimation();
                            }
                        });
                kAlertDialog.show();
             }
        });



    }

    private void deleteFav(Fav fav) {
        class DeleteFav extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(context).getMyDatabase().favDao().delete(fav);
                return null;
            }
            @Override
            protected void onPostExecute(Void unused) {
                Toast.makeText(context, "is deleted", Toast.LENGTH_SHORT).show();
                super.onPostExecute(unused);
            }
        }
        DeleteFav deleteFav = new DeleteFav();
        deleteFav.execute();
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView lang1Icon, lang2Icon, btnRemove;
        TextView txtLang1, titleLang2,txtLang2,titleLang1;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            lang1Icon = itemView.findViewById(R.id.lang1Icon);
            lang2Icon = itemView.findViewById(R.id.lang2Icon);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            txtLang1 = itemView.findViewById(R.id.txtLang1);
            titleLang2 = itemView.findViewById(R.id.titleLang2);
            titleLang1 = itemView.findViewById(R.id.titleLang1);
            txtLang2 = itemView.findViewById(R.id.txtLang2);

        }
    }
}
