package com.studiofive.soundgame;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends Activity {
    public static Integer goBackCounter = 0;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Activates context
        mContext = MainActivity.this;
        showMainScreen();
    }

    //Show main page
    private void showMainScreen(){
        setContentView(R.layout.activity_main);
    }

    private void showDrinksPage(){
        setContentView(R.layout.drinks);
        //Resets counter
        goBackCounter = 0;
    }

    private void showFoodsPage(){
        setContentView(R.layout.foods);

        //Resets counter
        goBackCounter = 0;
    }

    private void showFruitsPage(){
        setContentView(R.layout.fruits);

        //Resets counter
        goBackCounter = 0;
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //When clicking on the back key in the phone/tablet
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (goBackCounter >= 3)) {
            finish();
            System.exit(0);
        } else {
            goBackCounter++;
            showMainScreen();
        }
        return super.onKeyDown(keyCode, event);
    }

    //When clicking on the back key in the phone show message
    @Override
    public void onBackPressed() {
        if (goBackCounter == 2) {
            if (!((Activity) mContext).isFinishing()) {
                Toasty.success(mContext,  getResources().getString(R.string.exit), Toast.LENGTH_SHORT, true).show();
            }
        }
    }
}