package com.gmail.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.weatherapp.data.Channel;
import com.gmail.weatherapp.data.Item;
import com.gmail.weatherapp.service.WeatherServiceCallback;
import com.gmail.weatherapp.service.YahooWeatherService;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private YahooWeatherService service;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherIconImageView = (ImageView)findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView)findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView)findViewById(R.id.conditionTextView);
        locationTextView = (TextView)findViewById(R.id.locationTextView);

        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loader...");
        dialog.show();


        service.refreshWeather("Austin, TX");
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon"+ item.getCondition().getCode(), null, getPackageName());

        Drawable weatherIconDrawable = getResources().getDrawable(resourceId, null);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(item.getCondition().getTemperature()+"\u00B0"+channel.getUnits().getTemperature());
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(service.getLocation());

        
    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();

    }
}
