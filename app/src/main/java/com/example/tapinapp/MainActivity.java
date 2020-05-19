package com.example.tapinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.URLUtil;

import com.example.tapinapp.Model.UrlData;
import com.example.tapinapp.Room.MyappDatabse;
import com.example.tapinapp.Room.RoomUrlData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String url;
    String websiteTitle;
    String description;
    String imgurl;
    RecyclerView recyclerView;
    UrlDataAdapter urlDataAdapter;
    List<UrlData> urlDataList;

    MyappDatabse myappDatabse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        url = clipboard.getText().toString();

        myappDatabse = Room.databaseBuilder(this, MyappDatabse.class, "mycliplinks").allowMainThreadQueries().build();


        urlDataList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        urlDataAdapter = new UrlDataAdapter(this,urlDataList);
        recyclerView.setAdapter(urlDataAdapter);

        if(myappDatabse.mydao().getAllLinks() != null)
        {
            List<RoomUrlData> roomUrlDataList = myappDatabse.mydao().getAllLinks();

            for(RoomUrlData roomUrlData : roomUrlDataList)
            {
                urlDataList.add(new UrlData(roomUrlData.getUrl(),roomUrlData.getTitle(),roomUrlData.getDescription()
                ,roomUrlData.getImgUrl()));
            }

            urlDataAdapter.notifyDataSetChanged();
        }

        if(URLUtil.isValidUrl(url))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setMessage("Do you really want to save this link?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            new FetchWebsiteData().execute();

                        }
                    }).setNegativeButton("No",null)
                    .show();

        }

    }

    private class FetchWebsiteData extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {


            try {
                // Connect to website
                Document doc = Jsoup.connect(url).get();
                // Get the html document title
                if(doc.title() != null)
                websiteTitle = doc.title();

                if(doc.select("meta[name=description]") != null)
                    if( doc.select("meta[name=description]").size() > 0)
                        if(doc.select("meta[name=description]").get(0).attr("content") != null)
                            description = doc.select("meta[name=description]").get(0).attr("content");

                // Locate the content attribute
                String ogImage = null;
                Elements metaOgImage = doc.select("meta[property=og:image]");

                if (metaOgImage != null) {
                    if(metaOgImage.first()!= null)
                    imgurl = metaOgImage.first().attr("content");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            UrlData urlData = new UrlData(url,websiteTitle,description,imgurl);
            urlDataList.add(urlData);
            urlDataAdapter.notifyDataSetChanged();
            RoomUrlData roomUrlData = new RoomUrlData(url,websiteTitle,description,imgurl);
            myappDatabse.mydao().addLink(roomUrlData);
        }
    }
}


