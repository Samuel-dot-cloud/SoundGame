package com.studiofive.soundgame;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showMainScreen();
    }

    //Show main page
    private void showMainScreen(){
        setContentView(R.layout.activity_main);
    }

    private void showDrinksPage(){
        setContentView(R.layout.drinks);
    }

    private void showFoodsPage(){
        setContentView(R.layout.foods);
    }

    private void showFruitsPage(){
        setContentView(R.layout.fruits);
    }
}