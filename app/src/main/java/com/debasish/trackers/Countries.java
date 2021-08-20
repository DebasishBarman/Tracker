package com.debasish.trackers;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.debasish.trackers.adapters.countryAdapter;
import com.debasish.trackers.model.CountryModel;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Countries extends AppCompatActivity {
    private EditText cName;
    private ListView listView;
    private SimpleArcLoader loader;
    public static List<CountryModel> countryModelList=new ArrayList<>();
    CountryModel countryModel;
    countryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        cName=findViewById(R.id.countryName);
        listView=findViewById(R.id.countryList);
        loader=findViewById(R.id.loaderList);
        fetchData();
    }

    private void fetchData() {
        String url="https://disease.sh/v3/covid-19/countries/";
        loader.start();
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String countryName=jsonObject.getString("country");
                        String active=jsonObject.getString("active");
                        String critical=jsonObject.getString("critical");
                        String cases=jsonObject.getString("cases");
                        String death=jsonObject.getString("deaths");
                        String recovered=jsonObject.getString("recovered");
                        String todays=jsonObject.getString("todayCases");
                        JSONObject object=jsonObject.getJSONObject("countryInfo");
                        String flag=object.getString("flag");

                        countryModel=new CountryModel(flag,countryName,todays,cases,death,recovered,active,critical);
                        countryModelList.add(countryModel);
                    }

                    adapter=new countryAdapter(getApplicationContext(),countryModelList);
                    loader.stop();
                    loader.setVisibility(View.GONE);
                    listView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.stop();
                loader.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);
    }

}