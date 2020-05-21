package com.example.imageviewer;

import androidx.appcompat.app.AppCompatActivity;

public class Refresh {
    public static void finishApp(AppCompatActivity appCompatActivity) {
        appCompatActivity.finish();
    }

    public static void refreshApp(AppCompatActivity appCompatActivity) {
        appCompatActivity.recreate();
    }
}
