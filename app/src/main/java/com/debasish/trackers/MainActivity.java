package com.debasish.trackers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tvCases, tvDeaths, tvRecovered, tvActive, tvCritical, tvToday, tvTotalDeaths, tvCountries;
    SimpleArcLoader loader;
    PieChart pieChart;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        fetchData();//to fetch data from Api
    }

    private void fetchData() {
        String url="https://disease.sh/v3/covid-19/countries/India";
        loader.start();
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                    loader.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loader.stop();
                            loader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    },1000);

                    JSONObject jsonObject=new JSONObject(response.toString());
                    tvCases.setText(jsonObject.getString("cases"));
                    tvRecovered.setText(jsonObject.getString("recovered"));
                    tvActive.setText(jsonObject.getString("active"));
                    tvCritical.setText(jsonObject.getString("critical"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvCountries.setText(jsonObject.getString("country"));
                    tvToday.setText(jsonObject.getString("todayCases"));
                    pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvCases.getText().toString()),Color.parseColor("#66bb6a")));
                    pieChart.addPieSlice(new PieModel("Active Cases",Integer.parseInt(tvActive.getText().toString()),Color.parseColor("#29b6f6")));
                    pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvTotalDeaths.getText().toString()),Color.parseColor("#ef5350")));
                    pieChart.addPieSlice(new PieModel("Cases",
                            Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#ffa726")));
                    pieChart.startAnimation();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);

    }

    private void initData() {
        tvActive = findViewById(R.id.tvActive);
        tvCases = findViewById(R.id.tvCases);
        tvTotalDeaths = findViewById(R.id.tvtotalDeaths);
        tvCritical = findViewById(R.id.tvCritical);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvDeaths = findViewById(R.id.tvtotalDeaths);
        tvToday = findViewById(R.id.tvToday);
        tvCountries = findViewById(R.id.tvAffected);
        loader = findViewById(R.id.loader);
        pieChart = findViewById(R.id.piechart);
        scrollView=findViewById(R.id.scrollStats);
    }

    public void gotoTrack(View view) {
        startActivity(new Intent(MainActivity.this,Countries.class));
        finish();
    }
}