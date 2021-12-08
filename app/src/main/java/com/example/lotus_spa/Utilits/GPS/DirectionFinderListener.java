package com.example.lotus_spa.Utilits.GPS;

import com.example.lotus_spa.Class.Route;

import java.util.List;

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
