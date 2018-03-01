package ru.alexandrpokh.cheltanks.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.alexandrpokh.cheltanks.R;
import ru.alexandrpokh.cheltanks.homePackage.HomeGallerycard;
import ru.alexandrpokh.cheltanks.homePackage.HomeNews;
import ru.alexandrpokh.cheltanks.homePackage.Newscard;
import ru.alexandrpokh.cheltanks.homePackage.NewscardAdapter;

public class HomeFragment extends Fragment {

    ProgressDialog mProgressDialog;
    HashMap<String, String> map ;
    Context context;

    private List<Newscard> newscardList = new ArrayList<>();
    private NewscardAdapter nAdapter;
    private RecyclerView recyclerView;
    // URL Address
    String url = "http://firstandgoal.ru/?s=%D1%82%D0%B0%D0%BD%D0%BA";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_news_list, container, false);

        // RecyclerView Setup
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_news);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        nAdapter = new NewscardAdapter(getActivity(),newscardList);

        recyclerView.setAdapter(nAdapter);

        nAdapter.setOnItemClickListener(new NewscardAdapter.ClickListener() {

            @Override
            public void onItemClick(int position, View v) {

                String link = newscardList.get(position).getLink();
                String newstitle = newscardList.get(position).getNewstitle();
                String photo = newscardList.get(position).getPhoto();
                String date = newscardList.get(position).getDate();

                if (newstitle.contains("Фотогалерея")) {
                    Intent intent = new Intent(HomeFragment.this.getActivity(), HomeGallerycard.class);
                    intent.putExtra("link", link);
                    intent.putExtra("newstitle", newstitle);
                    //img.setImageResource(R.drawable.iconscamera);
                    HomeFragment.this.startActivity(intent);

                }
                else {
                    Intent intent = new Intent(HomeFragment.this.getActivity(), HomeNews.class);
                    intent.putExtra("link", link);
                    intent.putExtra("newstitle", newstitle);
                    intent.putExtra("photo", photo);
                    intent.putExtra("date", date);
                    //img.setImageResource(R.drawable.iconsnews);
                    HomeFragment.this.startActivity(intent);
                }
            }
        });

        new JsoupNewsListView().execute();

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void prepareNewscardData() {
        newscardList.add(new Newscard(map.get("newstitle"), map.get("date"),
                map.get("descript"), map.get("link"), map.get("photo")));
    }

    // Внутренний класс JsoupListView с потоком AsyncTask
    private class JsoupNewsListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            map = new HashMap<String, String>();
            try {

                // Connect to the Website URL
                Document doc = Jsoup.connect(url).get();

                //парсим и загоняем в map
                for (Element table : doc.select("section[class=container second-row]")) {

                    for (Element itemrow : table.select("div[class=row]")) {

                        for (Element item : itemrow.select("article[class=feed-item col-sm-6]")) {

                            Elements newstitle = item.select("h3[itemprop=headline name]");
                            if(!newstitle.toString().contains("Анонс")) {
                                for (Element item_newstitle : item.select("h3[itemprop=headline name]")) {
                                    map.put("newstitle", item_newstitle.text());
                                }

                                for (Element item_newsdate : item.select("time[itemprop=datePublished]")) {
                                    map.put("date", item_newsdate.text());
                                }

                                for (Element item_newsdescript : item.select("p")) {
                                    map.put("descript", item_newsdescript.text());
                                }

                                for (Element item_link : item.select("article[class=feed-item col-sm-6]")) {
                                    Elements url_href = item_link.select("a[href]");
                                    String link = url_href.attr("href");
                                    map.put("link", link);
                                }

                                for (Element item_photo : item.select("article[class=feed-item col-sm-6]")) {
                                    Elements imgSrc = item_photo.select("img[class=feed-image wp-post-image]");
                                    String imgSrcStr1 = imgSrc.attr("src");
                                    map.put("photo", imgSrcStr1);
                                }
                                prepareNewscardData();
                            }
                        }
                    }
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            nAdapter.notifyDataSetChanged();
            mProgressDialog.dismiss();
        }
    }

}
