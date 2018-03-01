package ru.alexandrpokh.cheltanks.teamPackage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.alexandrpokh.cheltanks.R;


public class SingleItemView extends Activity {
    // Declare Variables
    String fio,links,link,photo,num, pos,info;
    ArrayList<String> arr_head;
    ArrayList<String> arr_znach;
    ArrayList<String> arr_info;
    ProgressDialog mProgressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.singleitemview);
        Toolbar toolbarroster = (Toolbar) findViewById(R.id.toolbarroster);
        //  setSupportActionBar(toolbarroster);
        toolbarroster.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });

        Intent i = getIntent();
        // Get the result of rank
        fio = i.getStringExtra("fio");
        link = i.getStringExtra("link");
        links = "http://"+(link + "&comp=64").substring(11);
        info = i.getStringExtra("info");
        num = i.getStringExtra("num");
        pos = i.getStringExtra("pos");
        // Get the result of flag
        photo = i.getStringExtra("photo");


        // Locate the TextViews in singleitemview.xml
        TextView num_txt = (TextView) findViewById(R.id.num);
        TextView pos_txt = (TextView) findViewById(R.id.pos);
        toolbarroster.setTitle(fio);


        // Locate the ImageView in singleitemview.xml
        ImageView imgphoto = (ImageView) findViewById(R.id.photo);

        // Set results to the TextViews
        num_txt.setText(num);
        pos_txt.setText(pos);

        Glide.with(this)
                .load(photo)
                .placeholder(R.drawable.temp_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgphoto);

        new JsoupView().execute();
    }


    // Внутренний классJsoupListView с потоком AsyncTask
    private class JsoupView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SingleItemView.this);
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            arr_head = new ArrayList<String>();
            arr_znach = new ArrayList<String>();
            arr_info = new ArrayList<String>();

            try {
                System.out.println(links);
                // Connect to the Website URL
                Document doc = Jsoup.connect(links).get();

                //тег для проверки наличия таблицы
                Elements etab =doc.select("table[class=stats_maintable]");

                //Если в таблице НЕ пусто, то парсим дальше
                if(etab!=null) {
                    for (Element table : doc.select("table[class=stats_maintable]")) {

                        Elements row_head = table.select("thead[class=datagrid_thead]");
                        Elements th_head = row_head.select("th");

                        Elements row_znach = table.select("tbody[class=datagrid_tbody]");
                        Elements tr_znach = row_znach.select("tr[class=stats-item]");
                        Elements td_znach = tr_znach.select("td");

                        for (int i = 0; i < th_head.size(); i++) {
                            Element row = th_head.get(i);
                            arr_head.add(row.text());

                        }

                        for (int i = 0; i < td_znach.size(); i++) {
                            Element row = td_znach.get(i);
                            arr_znach.add(row.text());

                        }
                    }

                }

                for (Element infoBlock : doc.select("div[class=main-infoBlock person-infoBlock]")){
                    Elements row_info = infoBlock.select("span[class=main-infoText__content]");

                    for (int i = 0; i < row_info.size(); i++) {
                        Element row = row_info.get(i);
                        arr_info.add(row.text());

                    }
                }


            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return null;
        }

            @Override
        protected void onPostExecute(Void result) {
                TextView txt_data = (TextView) findViewById(R.id.data);
                TextView txt_growth = (TextView) findViewById(R.id.growth);
                TextView txt_weight = (TextView) findViewById(R.id.weight);
                TextView txt_grajd = (TextView) findViewById(R.id.grajd);

                TextView txth0 = (TextView) findViewById(R.id.txth0);
                TextView txth1 = (TextView) findViewById(R.id.txth1);
                TextView txth2 = (TextView) findViewById(R.id.txth2);
                TextView txth3= (TextView) findViewById(R.id.txth3);
                TextView txth4 = (TextView) findViewById(R.id.txth4);
                TextView txth5 = (TextView) findViewById(R.id.txth5);
                TextView txth6 = (TextView) findViewById(R.id.txth6);
                TextView txth7 = (TextView) findViewById(R.id.txth7);
                TextView txth8 = (TextView) findViewById(R.id.txth8);
                TextView txth9 = (TextView) findViewById(R.id.txth9);
                TextView txth10 = (TextView) findViewById(R.id.txth10);
                TextView txth11 = (TextView) findViewById(R.id.txth11);
                TextView txth12 = (TextView) findViewById(R.id.txth12);
                TextView txth13 = (TextView) findViewById(R.id.txth13);
                TextView txth14 = (TextView) findViewById(R.id.txth14);
                TextView txth15 = (TextView) findViewById(R.id.txth15);
                TextView txth16 = (TextView) findViewById(R.id.txth16);
                TextView txth17 = (TextView) findViewById(R.id.txth17);
                TextView txth18 = (TextView) findViewById(R.id.txth18);
                TextView txtz0 = (TextView) findViewById(R.id.txtz0);
                TextView txtz1 = (TextView) findViewById(R.id.txtz1);
                TextView txtz2 = (TextView) findViewById(R.id.txtz2);
                TextView txtz3= (TextView) findViewById(R.id.txtz3);
                TextView txtz4 = (TextView) findViewById(R.id.txtz4);
                TextView txtz5 = (TextView) findViewById(R.id.txtz5);
                TextView txtz6 = (TextView) findViewById(R.id.txtz6);
                TextView txtz7 = (TextView) findViewById(R.id.txtz7);
                TextView txtz8 = (TextView) findViewById(R.id.txtz8);
                TextView txtz9 = (TextView) findViewById(R.id.txtz9);
                TextView txtz10 = (TextView) findViewById(R.id.txtz10);
                TextView txtz11 = (TextView) findViewById(R.id.txtz11);
                TextView txtz12 = (TextView) findViewById(R.id.txtz12);
                TextView txtz13 = (TextView) findViewById(R.id.txtz13);
                TextView txtz14 = (TextView) findViewById(R.id.txtz14);
                TextView txtz15 = (TextView) findViewById(R.id.txtz15);
                TextView txtz16 = (TextView) findViewById(R.id.txtz16);
                TextView txtz17 = (TextView) findViewById(R.id.txtz17);
                TextView txtz18 = (TextView) findViewById(R.id.txtz18);

              //  txt_data.setText(data);
              //  txt_growth.setText(growth);
             //   txt_weight.setText(weight);

                List<TextView> textViews = new ArrayList<>();
                List<TextView> textViews1 = new ArrayList<>();
                List<TextView> textViews2 = new ArrayList<>();
                textViews.add(txth0);
                textViews.add(txth1);
                textViews.add(txth2);
                textViews.add(txth3);
                textViews.add(txth4);
                textViews.add(txth5);
                textViews.add(txth6);
                textViews.add(txth7);
                textViews.add(txth8);
                textViews.add(txth9);
                textViews.add(txth10);
                textViews.add(txth11);
                textViews.add(txth12);
                textViews.add(txth13);
                textViews.add(txth14);
                textViews.add(txth15);
                textViews.add(txth16);
                textViews.add(txth17);
                textViews.add(txth18);
                textViews1.add(txtz0);
                textViews1.add(txtz1);
                textViews1.add(txtz2);
                textViews1.add(txtz3);
                textViews1.add(txtz4);
                textViews1.add(txtz5);
                textViews1.add(txtz6);
                textViews1.add(txtz7);
                textViews1.add(txtz8);
                textViews1.add(txtz9);
                textViews1.add(txtz10);
                textViews1.add(txtz11);
                textViews1.add(txtz12);
                textViews1.add(txtz13);
                textViews1.add(txtz14);
                textViews1.add(txtz15);
                textViews1.add(txtz16);
                textViews1.add(txtz17);
                textViews1.add(txtz18);
                textViews2.add(txt_data);
                textViews2.add(txt_growth);
                textViews2.add(txt_weight);
                textViews2.add(txt_grajd);


                for(int i = 0; i < arr_head.size(); i++) {
                    String str = arr_head.get(i);
                    textViews.get(i).setText(str);
                }
                for(int i = 0; i < arr_znach.size(); i++) {
                    String str = arr_znach.get(i);
                    textViews1.get(i).setText(str);
                    if(str.matches("^-?\\D+$")){
                       textViews1.get(i).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                       textViews1.get(i).setTypeface(null, Typeface.BOLD);
                       textViews1.get(i).setTextColor(Color.parseColor("#000000"));
                    }
                }
                for(int i = 0; i < arr_info.size(); i++) {
                    String str = arr_info.get(i);
                    textViews2.get(i).setText(str);
                }
            //Close the progressdialog
                mProgressDialog.dismiss();
        }
    }

}
