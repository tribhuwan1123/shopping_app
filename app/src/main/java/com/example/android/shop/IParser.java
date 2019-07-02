package com.example.android.shop;

import com.example.android.shop.model.Questions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface IParser {

    void onPreExecute();

    void onPostExecute(List<Questions> resultArrayList);

    void onPostFailure();
}
