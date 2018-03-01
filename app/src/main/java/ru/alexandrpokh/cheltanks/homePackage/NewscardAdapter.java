package ru.alexandrpokh.cheltanks.homePackage;

import android.content.Context;
import android.support.v7.widget.CardView;
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

public class NewscardAdapter extends RecyclerView.Adapter<NewscardAdapter.MyViewHolder>  {

    Context context;
    private List<Newscard> newscardsList;
    private static ClickListener clickListener;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        NewscardAdapter.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView newstitle, date, descript;
        public ImageView photo;
        public CardView cardView;
        public  ImageView icnCard;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            newstitle = (TextView) view.findViewById(R.id.news_title);
            date = (TextView) view.findViewById(R.id.date);
            descript = (TextView) view.findViewById(R.id.descript);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            cardView = (CardView)view.findViewById(R.id.card_view);
            icnCard = (ImageView) view.findViewById(R.id.iconCard);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public NewscardAdapter(Context context, List<Newscard> newscardsList) {
        this.context = context;
        this.newscardsList = newscardsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newscard_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Newscard newscard = newscardsList.get(position);
        holder.newstitle.setText(newscard.getNewstitle());
        holder.date.setText(newscard.getDate());
        holder.descript.setText(newscard.getDescript());

        ImageView imageView = holder.photo;

        Glide.with(context)
                .load(newscard.getPhoto())
                .placeholder(R.drawable.templogo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);


       if(newscard.getNewstitle().contains("Фотогалерея"))
           holder.icnCard.setImageResource(R.drawable.iconscamera128);

       else
           holder.icnCard.setImageResource(R.drawable.iconsnews128);

    }

    @Override
    public int getItemCount() {
        return newscardsList.size();
    }

}
