package com.gmail.weatherapp.service;

import com.gmail.weatherapp.data.Channel;

public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);

}
