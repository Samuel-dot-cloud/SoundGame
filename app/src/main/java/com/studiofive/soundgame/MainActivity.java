package com.studiofive.soundgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

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

    public void clickButton(View view){
        if (view.getId() == R.id.drinksBtn){
            showDrinksPage();
        } else if (view.getId() == R.id.foodsBtn){
            showFoodsPage();
        }else if (view.getId() == R.id.fruitsBtn){
            showFruitsPage();
        }else if (view.getId() == R.id.goBack){
            showMainScreen();
        }
    }
}