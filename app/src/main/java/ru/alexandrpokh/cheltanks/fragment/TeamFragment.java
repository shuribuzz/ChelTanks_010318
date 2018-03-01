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
import ru.alexandrpokh.cheltanks.other.ItemDivider;
import ru.alexandrpokh.cheltanks.teamPackage.PlaycardItem;
import ru.alexandrpokh.cheltanks.teamPackage.PlaycardAdapter;
import ru.alexandrpokh.cheltanks.teamPackage.SingleItemView;

public class TeamFragment extends Fragment {
    ProgressDialog mProgressDialog;
    HashMap<String, String> map ;
    Context context;
    private List<PlaycardItem> playcardItemList = new ArrayList<>();
    private PlaycardAdapter pAdapter;
    private RecyclerView recyclerView;

    // URL Address
    String url = "http://amfoot.ru/?r=roster&team=102&comp=64";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_team_list, container, false);

        // RecyclerView Setup
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
   //     recyclerView.addItemDecoration(new ItemDivider(getActivity()));
        recyclerView.addItemDecoration(new ItemDivider(getActivity(),
                R.drawable.item_divider));
        pAdapter = new PlaycardAdapter(getActivity(), playcardItemList);
        recyclerView.setAdapter(pAdapter);
        pAdapter.setOnItemClickListener(new PlaycardAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                String fio = playcardItemList.get(position).getFio();
                String info = playcardItemList.get(position).getInfo();
                String link = playcardItemList.get(position).getLink();
                String photo = playcardItemList.get(position).getPhoto();
                String num = playcardItemList.get(position).getNum();
                String pos = playcardItemList.get(position).getPos();


                Intent intent = new Intent(TeamFragment.this.getActivity(), SingleItemView.class);
                intent.putExtra("fio", fio);
                intent.putExtra("info", info);
                intent.putExtra("link", link);
                intent.putExtra("photo", photo);
                intent.putExtra("num", num);
                intent.putExtra("pos", pos);

                TeamFragment.this.startActivity(intent);
            }

        });

        new JsoupListView().execute();
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void preparePlaycardData() {
             playcardItemList.add(new PlaycardItem(map.get("fio"), map.get("info"),
                     map.get("link"), map.get("photo"),
                     map.get("num"), map.get("pos")));
    }

        // Внутренний классJsoupListView с потоком AsyncTask
        private class JsoupListView extends AsyncTask<Void, Void, Void> {

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
                    //   int coun=0;//с помощью счётчика отделим официальные лица
                    //парсим и загоняем в map
                    for (Element table : doc.select("div[class=roster-view]")) {
                        //  if (coun>1)
                        //     break;
                        for (Element item : table.select("div[class=roster-view_itemContent]")) {


                            for (Element item_fio : item.select("div[class=roster-view_fio]")) {
                                map.put("fio", item_fio.text());

                            }

                            for (Element item_info : item.select("div[class=roster-view_info]")) {
                                map.put("info", item_info.text());

                                String str = item_info.text();
                                String str_num = "";
                                String str_pos = "";
                                char c;
                                int count = 0;

                                for (int i = 0; i < str.length(); i++) {
                                    c = str.charAt(i);

                                    if (count == 1) {
                                        str_pos = str_pos + c;
                                        if (c == ' ')
                                            break;
                                    } else if (c == ' ') {
                                        count++;
                                        //i++;
                                        str_pos = str_pos + c;
                                    } else
                                        str_num = str_num + c;
                                }
                                map.put("num", str_num);
                                map.put("pos", str_pos);

                            }

                            for (Element item_photo : item.select("div[class=roster-view_photo]")) {
                                Elements imgSrc = item_photo.select("img[src]");
                                String imgSrcStr1 = imgSrc.attr("src");
                                String imgSrcStr = "http:" + imgSrcStr1;

                                map.put("photo", imgSrcStr);
                            }

                            for (Element item_link : item.select("div[class=roster-view_fio]")) {
                                Elements url_href = item_link.select("a[href]");
                                String url1 = url_href.attr("href");
                                String link = "http://laf.amfoot.ru" + url1;
                                map.put("link", link);

                            }
                            //  coun++;
                            preparePlaycardData();
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
                pAdapter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            }
        }

}