package ru.alexandrpokh.cheltanks.homePackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.alexandrpokh.cheltanks.R;
import ru.alexandrpokh.cheltanks.fragment.SlideshowDialogFragment;

public class HomeGallerycard extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    String link;
    String newstitle;
    ArrayList<String> arr_img;
    HashMap<String, String> map ;
    private RecyclerView recyclerView;
    Context context;
    private List<PhotoCard> photocardList = new ArrayList<>();
    GalleryAdapter gAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.homegalerycard);
        Toolbar toolbarnews = (Toolbar) findViewById(R.id.toolbarnews);
        toolbarnews.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.rv_gallery);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        gAdapter = new GalleryAdapter(this,photocardList);

        recyclerView.setAdapter(gAdapter);

         recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("photo", (Serializable) photocardList);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        Intent i = getIntent();
        // Get the result of rank
        link = i.getStringExtra("link");
        newstitle = i.getStringExtra("newstitle");

        toolbarnews.setTitle(newstitle);

        new JsoupNewsView().execute();

    }

    private void prepareGallery() {
        photocardList.add(new PhotoCard(map.get("photo")));
    }


    // Внутренний классJsoupListView с потоком AsyncTask
    private class JsoupNewsView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(HomeGallerycard.this);
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            map = new HashMap<String, String>();
            arr_img = new ArrayList<String>();

            try {
                // Connect to the Website URL
                Document doc = Jsoup.connect(link).get();

                //Парсинг страницы с галереей
                    for (Element container : doc.select("div[class=container second-row]")) {

                        for (Element article : container.select("article[class=article-content]")) {

                            for (Element body : article.select("div[id=gallery]")) {

                                for (Element img : body.select("div[id=gallery-1]")) {

                                    for (Element imgS : img.select("dt[class=gallery-icon landscape]")) {
                                        Elements imgSrc = imgS.select("img[src]");
                                        String imgSrcStr = imgSrc.attr("src");
                                       // arr_img.add(imgSrcStr);
                                        map.put("photo", imgSrcStr);
                                        prepareGallery();
                                      //  System.out.println(map);
                                    }

                                }
                            }
                        }

                    }

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            gAdapter.notifyDataSetChanged();
            //Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

}

