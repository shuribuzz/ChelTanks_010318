package ru.alexandrpokh.cheltanks.teamPackage;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ru.alexandrpokh.cheltanks.R;

public class PlaycardAdapter extends RecyclerView.Adapter<PlaycardAdapter.MyViewHolder> {

    Context mContext;
    private List<PlaycardItem> playcardsList;
    private static ClickListener clickListener;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        PlaycardAdapter.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView fio, info;
        public ImageView photo;


        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            fio = (TextView) view.findViewById(R.id.fio);
            info = (TextView) view.findViewById(R.id.info);
            photo = (ImageView) itemView.findViewById(R.id.photo);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public PlaycardAdapter(Context context, List<PlaycardItem> playcardsList) {
          mContext = context;
          this.playcardsList = playcardsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playcard_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PlaycardItem playcardItem = playcardsList.get(position);

        holder.fio.setText(playcardItem.getFio());
        holder.info.setText(playcardItem.getInfo());

        ImageView imageView = holder.photo;

        Glide.with(mContext)
                .load(playcardItem.getPhoto())
                .placeholder(R.drawable.temp_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return playcardsList.size();
    }
}
