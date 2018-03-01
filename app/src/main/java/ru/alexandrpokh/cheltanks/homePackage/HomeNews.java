package ru.alexandrpokh.cheltanks.homePackage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
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

public class HomeNews extends Activity {
    String newstitle,link,photo,date,score;
    ArrayList<String> arrLogo,arrArticle,arrHeadScore,arrTbodyScore,arrTbodyGame;
    ProgressDialog mProgressDialog;
    Context mContext;
    Boolean tableCourseVis = true;
    Boolean tableResVis = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_news);
        Toolbar toolbarnews = (Toolbar) findViewById(R.id.toolbarnews);
        toolbarnews.setTitle("");
        toolbarnews.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });

        TextView titlename = (TextView) toolbarnews.findViewById(R.id.titlename);
        titlename.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        titlename.setSingleLine(true);
        titlename.setMarqueeRepeatLimit(-1); // '-1' for infinite
        titlename.setSelected(true);

        Intent i = getIntent();
        // Get the result of rank
        newstitle = i.getStringExtra("newstitle");
        link = i.getStringExtra("link");
        photo = i.getStringExtra("photo");
        date =  i.getStringExtra("date");
        titlename.setText(newstitle);

        ImageView imgphoto = (ImageView) findViewById(R.id.photo);

        Glide.with(this)
                .load(photo)
                .placeholder(R.drawable.templogo)
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
            mProgressDialog = new ProgressDialog(HomeNews.this);
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

             arrLogo = new ArrayList<String>(); //логотипы команд
             arrArticle = new ArrayList<String>(); //текст статьи по абзацам
             arrHeadScore = new ArrayList<String>(); //заголовок таблицы счёта игры
             arrTbodyScore = new ArrayList<String>(); //тело таблицы счёта игры
             arrTbodyGame = new ArrayList<String>(); //тело таблицы хода игры

            try {

                // Connect to the Website URL
                Document doc = Jsoup.connect(link).get();

                for (Element block : doc.select("article[class=inside news-post]")){

                        Elements row = block.select("div[class=row]");
                        Elements col = row.select("div[class=col-md-8 news-text]");

                        Elements tableTeam = col.select("table[class=easy-table easy-table-default score]");
                        Elements th = tableTeam.select("th");
                        score = th.text();

                        Elements articleBody = row.select("span[itemprop=articleBody]");
                        Elements paragraph = articleBody.select("p");

                        for (int i = 0; i < paragraph.size(); i++) {
                            Element p = paragraph.get(i);
                            arrArticle.add(p.text());
                        }

                        for (int i = 0; i < th.size(); i++) {
                            Element logo = th.get(i);
                            if (logo != null) {
                                Elements imgSrc = logo.select("img[src]");
                                String imgSrcStr = imgSrc.attr("src");
                                if (imgSrcStr.contains("http")) {
                                    arrLogo.add(imgSrcStr);
                                }
                            }
                        }

                        Elements tables = col.select("table[class=easy-table easy-table-default]");
                        Elements tablScore = tables.eq(0); //таблица счёта игры
                        Elements tablGame = tables.eq(1); //таблица хода игры

                        Elements theadScore = tablScore.select("th");
                        for (int i = 0; i < theadScore.size(); i++) {
                            Element thScore = theadScore.get(i);
                            arrHeadScore.add(thScore.text());
                        }
                        Elements tbodyScore = tablScore.select("td");
                        for (int i = 0; i < tbodyScore.size(); i++) {
                            Element tdScore = tbodyScore.get(i);
                            arrTbodyScore.add(tdScore.text());
                        }

                        Elements tbodyGame = tablGame.select("td");
                        for (int i = 0; i < tbodyGame.size(); i++) {
                            Element tdGame = tbodyGame.get(i);
                            arrTbodyGame.add(tdGame.text());
                        }

                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            TextView tvResult = (TextView) findViewById(R.id.tvResultGame);
            TextView tvCourse = (TextView) findViewById(R.id.tvCourseGame);
            TableLayout tableScore = (TableLayout)findViewById(R.id.tableScore);

            TextView txt_score = (TextView) findViewById(R.id.score);

            TextView paragraph1 = (TextView) findViewById(R.id.paragraph1);
            TextView paragraph2 = (TextView) findViewById(R.id.paragraph2);
            TextView paragraph3 = (TextView) findViewById(R.id.paragraph3);
            TextView paragraph4 = (TextView) findViewById(R.id.paragraph4);
            TextView paragraph5 = (TextView) findViewById(R.id.paragraph5);
            TextView paragraph6 = (TextView) findViewById(R.id.paragraph6);
            TextView paragraph7 = (TextView) findViewById(R.id.paragraph7);
            TextView paragraph8 = (TextView) findViewById(R.id.paragraph8);
            TextView paragraph9 = (TextView) findViewById(R.id.paragraph9);
            TextView paragraph10 = (TextView) findViewById(R.id.paragraph10);
            TextView paragraph11 = (TextView) findViewById(R.id.paragraph11);
            TextView paragraph12 = (TextView) findViewById(R.id.paragraph12);
            TextView paragraph13 = (TextView) findViewById(R.id.paragraph13);
            TextView paragraph14 = (TextView) findViewById(R.id.paragraph14);
            TextView paragraph15 = (TextView) findViewById(R.id.paragraph15);
            TextView paragraph16 = (TextView) findViewById(R.id.paragraph16);
            TextView paragraph17 = (TextView) findViewById(R.id.paragraph17);
            TextView paragraph18 = (TextView) findViewById(R.id.paragraph18);
            TextView paragraph19 = (TextView) findViewById(R.id.paragraph19);
            TextView paragraph20 = (TextView) findViewById(R.id.paragraph20);

            TextView txtHeadScore0 = (TextView) findViewById(R.id.txtHeadScore0);
            TextView txtHeadScore1 = (TextView) findViewById(R.id.txtHeadScore1);
            TextView txtHeadScore2 = (TextView) findViewById(R.id.txtHeadScore2);
            TextView txtHeadScore3 = (TextView) findViewById(R.id.txtHeadScore3);
            TextView txtHeadScore4 = (TextView) findViewById(R.id.txtHeadScore4);
            TextView txtHeadTotal = (TextView) findViewById(R.id.txtHeadTotal);
            TextView txtBodyScore0 = (TextView) findViewById(R.id.txtBodyScore0);
            TextView txtBodyScore1 = (TextView) findViewById(R.id.txtBodyScore1);
            TextView txtBodyScore2 = (TextView) findViewById(R.id.txtBodyScore2);
            TextView txtBodyScore3 = (TextView) findViewById(R.id.txtBodyScore3);
            TextView txtBodyScore4 = (TextView) findViewById(R.id.txtBodyScore4);
            TextView txtBodyScore5 = (TextView) findViewById(R.id.txtBodyScore5);
            TextView txtBodyScore6 = (TextView) findViewById(R.id.txtBodyScore6);
            TextView txtBodyScore7 = (TextView) findViewById(R.id.txtBodyScore7);
            TextView txtBodyScore8 = (TextView) findViewById(R.id.txtBodyScore8);
            TextView txtBodyScore9 = (TextView) findViewById(R.id.txtBodyScore9);
            TextView txtBodyScore10 = (TextView) findViewById(R.id.txtBodyScore10);
            TextView txtBodyScore11 = (TextView) findViewById(R.id.txtBodyScore11);

            TextView txtGame1 = (TextView) findViewById(R.id.txtGame1);
            TextView txtGame2 = (TextView) findViewById(R.id.txtGame2);
            TextView txtGame3 = (TextView) findViewById(R.id.txtGame3);
            TextView txtGame4 = (TextView) findViewById(R.id.txtGame4);
            TextView txtGame5 = (TextView) findViewById(R.id.txtGame5);
            TextView txtGame6 = (TextView) findViewById(R.id.txtGame6);
            TextView txtGame7 = (TextView) findViewById(R.id.txtGame7);
            TextView txtGame8 = (TextView) findViewById(R.id.txtGame8);
            TextView txtGame9 = (TextView) findViewById(R.id.txtGame9);
            TextView txtGame10 = (TextView) findViewById(R.id.txtGame10);
            TextView txtGame11 = (TextView) findViewById(R.id.txtGame11);
            TextView txtGame12 = (TextView) findViewById(R.id.txtGame12);
            TextView txtGame13 = (TextView) findViewById(R.id.txtGame13);
            TextView txtGame14 = (TextView) findViewById(R.id.txtGame14);
            TextView txtGame15 = (TextView) findViewById(R.id.txtGame15);
            TextView txtGame16 = (TextView) findViewById(R.id.txtGame16);
            TextView txtGame17 = (TextView) findViewById(R.id.txtGame17);
            TextView txtGame18 = (TextView) findViewById(R.id.txtGame18);
            TextView txtGame19 = (TextView) findViewById(R.id.txtGame19);
            TextView txtGame20 = (TextView) findViewById(R.id.txtGame20);
            TextView txtGame21 = (TextView) findViewById(R.id.txtGame21);
            TextView txtGame22 = (TextView) findViewById(R.id.txtGame22);
            TextView txtGame23 = (TextView) findViewById(R.id.txtGame23);
            TextView txtGame24 = (TextView) findViewById(R.id.txtGame24);
            TextView txtGame25 = (TextView) findViewById(R.id.txtGame25);
            TextView txtGame26 = (TextView) findViewById(R.id.txtGame26);
            TextView txtGame27 = (TextView) findViewById(R.id.txtGame27);
            TextView txtGame28 = (TextView) findViewById(R.id.txtGame28);
            TextView txtGame29 = (TextView) findViewById(R.id.txtGame29);
            TextView txtGame30 = (TextView) findViewById(R.id.txtGame30);
            TextView txtGame31 = (TextView) findViewById(R.id.txtGame31);
            TextView txtGame32 = (TextView) findViewById(R.id.txtGame32);
            TextView txtGame33 = (TextView) findViewById(R.id.txtGame33);
            TextView txtGame34 = (TextView) findViewById(R.id.txtGame34);
            TextView txtGame35 = (TextView) findViewById(R.id.txtGame35);
            TextView txtGame36 = (TextView) findViewById(R.id.txtGame36);
            TextView txtGame37 = (TextView) findViewById(R.id.txtGame37);
            TextView txtGame38 = (TextView) findViewById(R.id.txtGame38);
            TextView txtGame39 = (TextView) findViewById(R.id.txtGame39);
            TextView txtGame40 = (TextView) findViewById(R.id.txtGame40);

            ImageView logoleft = (ImageView) findViewById(R.id.logoleft);
            ImageView logoright = (ImageView) findViewById(R.id.logoright);

            if(score!=null){
                txt_score.setText(score);
            }
            else
                txt_score.setVisibility(View.GONE);


            if(arrLogo.size()!=0){
                Glide.with(HomeNews.this)
                        .load(arrLogo.get(0))
                        .placeholder(R.drawable.templogosquare)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(logoleft);

                Glide.with(HomeNews.this)
                        .load(arrLogo.get(1))
                        .placeholder(R.drawable.templogosquare)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(logoright);
            }
            else {
                logoleft.setVisibility(View.GONE);
                logoright.setVisibility(View.GONE);
            }

            List<TextView> listParagraphs = new ArrayList<>();
            listParagraphs.add(paragraph1);
            listParagraphs.add(paragraph2);
            listParagraphs.add(paragraph3);
            listParagraphs.add(paragraph4);
            listParagraphs.add(paragraph5);
            listParagraphs.add(paragraph6);
            listParagraphs.add(paragraph7);
            listParagraphs.add(paragraph8);
            listParagraphs.add(paragraph9);
            listParagraphs.add(paragraph10);
            listParagraphs.add(paragraph11);
            listParagraphs.add(paragraph12);
            listParagraphs.add(paragraph13);
            listParagraphs.add(paragraph14);
            listParagraphs.add(paragraph15);
            listParagraphs.add(paragraph16);
            listParagraphs.add(paragraph17);
            listParagraphs.add(paragraph18);
            listParagraphs.add(paragraph19);
            listParagraphs.add(paragraph20);

            for(int i = 0; i < arrArticle.size(); i++) {
                String str = arrArticle.get(i);
                listParagraphs.get(i).setText(str);
            }

            if(listParagraphs.get(0).getText()==""){listParagraphs.get(0).setVisibility(View.GONE);}
            if(listParagraphs.get(1).getText()==""){listParagraphs.get(1).setVisibility(View.GONE);}
            if(listParagraphs.get(2).getText()==""){listParagraphs.get(2).setVisibility(View.GONE);}
            if(listParagraphs.get(3).getText()==""){listParagraphs.get(3).setVisibility(View.GONE);}
            if(listParagraphs.get(4).getText()==""){listParagraphs.get(4).setVisibility(View.GONE);}
            if(listParagraphs.get(5).getText()==""){listParagraphs.get(5).setVisibility(View.GONE);}
            if(listParagraphs.get(6).getText()==""){listParagraphs.get(6).setVisibility(View.GONE);}
            if(listParagraphs.get(7).getText()==""){listParagraphs.get(7).setVisibility(View.GONE);}
            if(listParagraphs.get(8).getText()==""){listParagraphs.get(8).setVisibility(View.GONE);}
            if(listParagraphs.get(9).getText()==""){listParagraphs.get(9).setVisibility(View.GONE);}
            if(listParagraphs.get(10).getText()==""){listParagraphs.get(10).setVisibility(View.GONE);}
            if(listParagraphs.get(11).getText()==""){listParagraphs.get(11).setVisibility(View.GONE);}
            if(listParagraphs.get(12).getText()==""){listParagraphs.get(12).setVisibility(View.GONE);}
            if(listParagraphs.get(13).getText()==""){listParagraphs.get(13).setVisibility(View.GONE);}
            if(listParagraphs.get(14).getText()==""){listParagraphs.get(14).setVisibility(View.GONE);}
            if(listParagraphs.get(15).getText()==""){listParagraphs.get(15).setVisibility(View.GONE);}
            if(listParagraphs.get(16).getText()==""){listParagraphs.get(16).setVisibility(View.GONE);}
            if(listParagraphs.get(17).getText()==""){listParagraphs.get(17).setVisibility(View.GONE);}
            if(listParagraphs.get(18).getText()==""){listParagraphs.get(18).setVisibility(View.GONE);}
            if(listParagraphs.get(19).getText()==""){listParagraphs.get(19).setVisibility(View.GONE);}

            List<TextView> tableHeadScore = new ArrayList<>();
            tableHeadScore.add(txtHeadScore0);
            tableHeadScore.add(txtHeadScore1);
            tableHeadScore.add(txtHeadScore2);
            tableHeadScore.add(txtHeadScore3);
            tableHeadScore.add(txtHeadScore4);
            tableHeadScore.add(txtHeadTotal);

            for(int i = 0; i < arrHeadScore.size(); i++) {
                String str = arrHeadScore.get(i);
                tableHeadScore.get(i).setText(str);
            }

            List<TextView> tableBodyScore = new ArrayList<>();
            tableBodyScore.add(txtBodyScore0);
            tableBodyScore.add(txtBodyScore1);
            tableBodyScore.add(txtBodyScore2);
            tableBodyScore.add(txtBodyScore3);
            tableBodyScore.add(txtBodyScore4);
            tableBodyScore.add(txtBodyScore5);
            tableBodyScore.add(txtBodyScore6);
            tableBodyScore.add(txtBodyScore7);
            tableBodyScore.add(txtBodyScore8);
            tableBodyScore.add(txtBodyScore9);
            tableBodyScore.add(txtBodyScore10);
            tableBodyScore.add(txtBodyScore11);

            for(int i = 0; i < arrTbodyScore.size(); i++) {
                String str = arrTbodyScore.get(i);
                tableBodyScore.get(i).setText(str);
            }


            List<TextView> tableCourseGame = new ArrayList<>();
            tableCourseGame.add(txtGame1);
            tableCourseGame.add(txtGame2);
            tableCourseGame.add(txtGame3);
            tableCourseGame.add(txtGame4);
            tableCourseGame.add(txtGame5);
            tableCourseGame.add(txtGame6);
            tableCourseGame.add(txtGame7);
            tableCourseGame.add(txtGame8);
            tableCourseGame.add(txtGame9);
            tableCourseGame.add(txtGame10);
            tableCourseGame.add(txtGame11);
            tableCourseGame.add(txtGame12);
            tableCourseGame.add(txtGame13);
            tableCourseGame.add(txtGame14);
            tableCourseGame.add(txtGame15);
            tableCourseGame.add(txtGame16);
            tableCourseGame.add(txtGame17);
            tableCourseGame.add(txtGame18);
            tableCourseGame.add(txtGame19);
            tableCourseGame.add(txtGame20);
            tableCourseGame.add(txtGame21);
            tableCourseGame.add(txtGame22);
            tableCourseGame.add(txtGame23);
            tableCourseGame.add(txtGame24);
            tableCourseGame.add(txtGame25);
            tableCourseGame.add(txtGame26);
            tableCourseGame.add(txtGame27);
            tableCourseGame.add(txtGame28);
            tableCourseGame.add(txtGame29);
            tableCourseGame.add(txtGame30);
            tableCourseGame.add(txtGame31);
            tableCourseGame.add(txtGame32);
            tableCourseGame.add(txtGame33);
            tableCourseGame.add(txtGame34);
            tableCourseGame.add(txtGame35);
            tableCourseGame.add(txtGame36);
            tableCourseGame.add(txtGame37);
            tableCourseGame.add(txtGame38);
            tableCourseGame.add(txtGame39);
            tableCourseGame.add(txtGame40);

            for(int i = 0; i < arrTbodyGame.size(); i++) {
                String str = arrTbodyGame.get(i);
                tableCourseGame.get(i).setText(str);
            }

            if(tableCourseGame.get(0).getText()==""){tableCourseGame.get(0).setVisibility(View.GONE);}
            if(tableCourseGame.get(1).getText()==""){tableCourseGame.get(1).setVisibility(View.GONE);}
            if(tableCourseGame.get(2).getText()==""){tableCourseGame.get(2).setVisibility(View.GONE);}
            if(tableCourseGame.get(3).getText()==""){tableCourseGame.get(3).setVisibility(View.GONE);}
            if(tableCourseGame.get(4).getText()==""){tableCourseGame.get(4).setVisibility(View.GONE);}
            if(tableCourseGame.get(5).getText()==""){tableCourseGame.get(5).setVisibility(View.GONE);}
            if(tableCourseGame.get(6).getText()==""){tableCourseGame.get(6).setVisibility(View.GONE);}
            if(tableCourseGame.get(7).getText()==""){tableCourseGame.get(7).setVisibility(View.GONE);}
            if(tableCourseGame.get(8).getText()==""){tableCourseGame.get(8).setVisibility(View.GONE);}
            if(tableCourseGame.get(9).getText()==""){tableCourseGame.get(9).setVisibility(View.GONE);}
            if(tableCourseGame.get(10).getText()==""){tableCourseGame.get(10).setVisibility(View.GONE);}
            if(tableCourseGame.get(11).getText()==""){tableCourseGame.get(11).setVisibility(View.GONE);}
            if(tableCourseGame.get(12).getText()==""){tableCourseGame.get(12).setVisibility(View.GONE);}
            if(tableCourseGame.get(13).getText()==""){tableCourseGame.get(13).setVisibility(View.GONE);}
            if(tableCourseGame.get(14).getText()==""){tableCourseGame.get(14).setVisibility(View.GONE);}
            if(tableCourseGame.get(15).getText()==""){tableCourseGame.get(15).setVisibility(View.GONE);}
            if(tableCourseGame.get(16).getText()==""){tableCourseGame.get(16).setVisibility(View.GONE);}
            if(tableCourseGame.get(17).getText()==""){tableCourseGame.get(17).setVisibility(View.GONE);}
            if(tableCourseGame.get(18).getText()==""){tableCourseGame.get(18).setVisibility(View.GONE);}
            if(tableCourseGame.get(19).getText()==""){tableCourseGame.get(19).setVisibility(View.GONE);}
            if(tableCourseGame.get(20).getText()==""){tableCourseGame.get(20).setVisibility(View.GONE);}
            if(tableCourseGame.get(21).getText()==""){tableCourseGame.get(21).setVisibility(View.GONE);}
            if(tableCourseGame.get(22).getText()==""){tableCourseGame.get(22).setVisibility(View.GONE);}
            if(tableCourseGame.get(23).getText()==""){tableCourseGame.get(23).setVisibility(View.GONE);}
            if(tableCourseGame.get(24).getText()==""){tableCourseGame.get(24).setVisibility(View.GONE);}
            if(tableCourseGame.get(25).getText()==""){tableCourseGame.get(25).setVisibility(View.GONE);}
            if(tableCourseGame.get(26).getText()==""){tableCourseGame.get(26).setVisibility(View.GONE);}
            if(tableCourseGame.get(27).getText()==""){tableCourseGame.get(27).setVisibility(View.GONE);}
            if(tableCourseGame.get(28).getText()==""){tableCourseGame.get(28).setVisibility(View.GONE);}
            if(tableCourseGame.get(29).getText()==""){tableCourseGame.get(29).setVisibility(View.GONE);}
            if(tableCourseGame.get(30).getText()==""){tableCourseGame.get(30).setVisibility(View.GONE);}
            if(tableCourseGame.get(31).getText()==""){tableCourseGame.get(31).setVisibility(View.GONE);}
            if(tableCourseGame.get(32).getText()==""){tableCourseGame.get(32).setVisibility(View.GONE);}
            if(tableCourseGame.get(33).getText()==""){tableCourseGame.get(33).setVisibility(View.GONE);}
            if(tableCourseGame.get(34).getText()==""){tableCourseGame.get(34).setVisibility(View.GONE);}
            if(tableCourseGame.get(35).getText()==""){tableCourseGame.get(35).setVisibility(View.GONE);}
            if(tableCourseGame.get(36).getText()==""){tableCourseGame.get(36).setVisibility(View.GONE);}
            if(tableCourseGame.get(37).getText()==""){tableCourseGame.get(37).setVisibility(View.GONE);}
            if(tableCourseGame.get(38).getText()==""){tableCourseGame.get(38).setVisibility(View.GONE);}
            if(tableCourseGame.get(39).getText()==""){tableCourseGame.get(39).setVisibility(View.GONE);}


            if(arrTbodyScore.size()==0){
                tableResVis = false;
            }
            if(arrTbodyGame.size()==0){
                tableCourseVis = false;
            }

            if(!tableResVis){
                tvResult.setVisibility(View.GONE);
                tableScore.setVisibility(View.GONE);
            }
            if(!tableCourseVis){
                tvCourse.setVisibility(View.GONE);
            }


            //Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

}
