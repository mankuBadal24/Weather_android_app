package com.example.weather_app_by_mb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    TextView cityname;
    Button search;
    TextView show;

    String url ;

    class getWeather extends AsyncTask<String, Void,String>{
        protected String doInBackground(String... urls){
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line="";
            while ((line = reader.readLine())!= null){
                result.append(line).append("/n");
//                result = 273.15-result;
            }
            return result.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        try{
            JSONObject jsonObject = new JSONObject(result);
            String weatherInfo  = jsonObject.getString("main");
            weatherInfo = weatherInfo.replace("temp","Temperature");
            weatherInfo = weatherInfo.replace("{","");
            weatherInfo = weatherInfo.replace("}","");
            weatherInfo = weatherInfo.replace("feels_like", "Feels_Like");
            weatherInfo = weatherInfo.replace(":","=");
            weatherInfo = weatherInfo.replace("temp_min","Temperature_Min");
            weatherInfo = weatherInfo.replace("temp_max","Temperature_Max");
            weatherInfo = weatherInfo.replace("pressure","Pressure");
            weatherInfo = weatherInfo.replace("humidity","Humidity");
            weatherInfo = weatherInfo.replace("sea_level","Sea_Level");
            weatherInfo = weatherInfo.replace("grnd_level","Ground_Level");
            weatherInfo = weatherInfo.replace(",","\n");
            show.setText(weatherInfo);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityname = findViewById(R.id.cityName);
        search = findViewById(R.id.search);
        show = findViewById(R.id.weather);

        final  String[] temp = {""};

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button Clicked! ", Toast.LENGTH_SHORT).show();
                String city = cityname.getText().toString();
                try {
                    if (city != null) {
                        url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=451dcc14e60f22a17613637cf8125070";
                    } else {
                        Toast.makeText(MainActivity.this, "Enter City", Toast.LENGTH_SHORT).show();
                    }
                    getWeather task = new getWeather();
                    temp[0] = task.execute(url).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (temp[0] == null) {
                    show.setText("cannot able to find weather");
                }
            }
        });
    }
}