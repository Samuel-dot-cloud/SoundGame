package com.studiofive.soundgame;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;

import es.dmoral.toasty.Toasty;

public class MainActivity extends Activity {
    public static Integer goBackCounter = 0;
    public Context mContext;
    private MediaPlayer musicFile;
    private AudioManager audio;

    private static boolean popupShow = false;
    private Integer ScreenHeight, ScreenWidth, ScreenSize = 0;
    private AlertDialog.Builder PopWelcome;
    private Activity mActivity;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Activates context
        mContext = MainActivity.this;
        mActivity = MainActivity.this;

        //Show once popup welcome message
        popupShow = true;

       //Check screen pixels size
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        ScreenHeight = displaymetrics.heightPixels;
        ScreenWidth = displaymetrics.widthPixels;

       //Check device screen size
        ScreenSize = 0;
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                ScreenSize = 4;
                Log.d(TAG, "Check Screen Size - XLarge screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                ScreenSize = 3;
                Log.d(TAG, "Check Screen Size - Large screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                ScreenSize = 2;
                Log.d(TAG, "Check Screen Size - Normal screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                ScreenSize = 1;
                Log.d(TAG, "Check Screen Size - Small screen");
                break;
            default:
                ScreenSize = 2;
                Log.d(TAG, "Check Screen Size - Screen size is neither large, normal or small");
        }


        //Initializes audio sound volume
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        showMainScreen();
        //Activate popup message
        showPopupWelcomeMessage();

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
        //Release sound
        releaseSound();

        if (view.getId() == R.id.drinksBtn){
            showDrinksPage();
        } else if (view.getId() == R.id.foodsBtn){
            showFoodsPage();
        }else if (view.getId() == R.id.fruitsBtn){
            showFruitsPage();
        }else if (view.getId() == R.id.goBack){
            showMainScreen();
        }else if (view.getId() == R.id.icon_share){
            showInviteFriends();
        }else if (view.getId() == R.id.icon_about){
            popupShow = true;
            showPopupWelcomeMessage();

            //Drinks section
        }else if (view.getId() == R.id.drink3) {
            playSound("blender.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.info(mContext, getResources().getString(R.string.drink3), Toast.LENGTH_SHORT, true).show();
            }

        }else if (view.getId() == R.id.drink4) {
            playSound("milkshake.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.info(mContext, getResources().getString(R.string.drink4), Toast.LENGTH_SHORT, true).show();
            }
        }else if (view.getId() == R.id.drink7) {
            playSound("soda.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.info(mContext, getResources().getString(R.string.drink7), Toast.LENGTH_SHORT, true).show();
            }

            //Foods section
        }else if (view.getId() == R.id.foods3) {
            playSound("frying.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.info(mContext, getResources().getString(R.string.food3), Toast.LENGTH_SHORT, true).show();
            }
        }else if (view.getId() == R.id.foods4) {
            playSound("kitchen.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.info(mContext, getResources().getString(R.string.food4), Toast.LENGTH_SHORT, true).show();
            }
        }else if (view.getId() == R.id.foods7) {
            playSound("eating.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.info(mContext, getResources().getString(R.string.food7), Toast.LENGTH_SHORT, true).show();
            }

            //Fruits section
        }else if (view.getId() == R.id.fruit1) {
            playSound("juice.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.info(mContext, getResources().getString(R.string.fruit1), Toast.LENGTH_SHORT, true).show();
            }
        }else if (view.getId() == R.id.fruit3) {
            playSound("apple.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.info(mContext, getResources().getString(R.string.fruit3), Toast.LENGTH_SHORT, true).show();
            }
        }else if (view.getId() == R.id.fruit8) {
            playSound("nuts.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.info(mContext, getResources().getString(R.string.fruit8), Toast.LENGTH_SHORT, true).show();
            }
        }

    }

    //Show invite friends message
    public void showInviteFriends() {
        //Define the GooglePlay share url
        String playerShareURL = "https://play.google.com/store/apps/details?id=com.studiofive.soundgame";

        //Open sharing option
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        //What we going to share
        String shareBody = getResources().getString(R.string.msg_invite1) + "\r\n" + playerShareURL;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent,getResources().getString(R.string.msg_invite2)));
    }

    //Show popup welcome message
    private void showPopupWelcomeMessage(){

        //Case true show popup welcome message
        if (popupShow) {

            //Show once only
            popupShow = false;

            //Build message
            String message;
            message = getResources().getString(R.string.popup_message1) + "\r\n" +
                    getResources().getString(R.string.popup_message2) + "\r\n" +
                    getResources().getString(R.string.popup_message3) + "\r\n";

            //Pop data
            if (ScreenSize == 3) {
                PopWelcome = new AlertDialog.Builder(MainActivity.this, R.style.WelcomeDialogThemeLargeScreens);
            } else {
                PopWelcome = new AlertDialog.Builder(MainActivity.this, R.style.WelcomeDialogThemeNormalScreens);
            }
            PopWelcome.setTitle(getResources().getString(R.string.app_name));
            PopWelcome.setIcon(R.mipmap.ic_launcher);
            PopWelcome.setMessage(message);
            PopWelcome.setPositiveButton("", CloseWelcomePage);
            PopWelcome.setNegativeButton(getResources().getString(R.string.popup_close), CloseWelcomePage);

            if(!((Activity) mContext).isFinishing()) {
                final AlertDialog alert = PopWelcome.create();
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(alert.getWindow().getAttributes());
                lp.width = Math.round((ScreenWidth / 100) * 80);
                lp.height = Math.round((ScreenHeight / 100) * 80);
                alert.getWindow().setAttributes(lp);
                TextView textViewData = (TextView) alert.findViewById(android.R.id.message);
                if (textViewData != null) {
                    textViewData.setTextColor(Color.WHITE);
                    if ((ScreenWidth > 1199) || (ScreenHeight > 1000)) {
                        textViewData.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                    } else {
                        textViewData.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    }
                    textViewData.setScroller(new Scroller(this));
                    textViewData.setVerticalScrollBarEnabled(true);
                    textViewData.setMovementMethod(new ScrollingMovementMethod());
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    alert.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                    alert.getWindow().getDecorView().setSystemUiVisibility(mActivity.getWindow().getDecorView().getSystemUiVisibility());
                    alert.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {
                            //Clear the not focusable flag from the window
                            alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

                            //Update the WindowManager with the new attributes (no nicer way I know of to do this)..
                            WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
                            wm.updateViewLayout(alert.getWindow().getDecorView(), alert.getWindow().getAttributes());
                        }
                    });
                }
                alert.show();
            }
        }
    }

    //When clicking on the popup welcome message
    private final DialogInterface.OnClickListener CloseWelcomePage = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    if (!((Activity) mContext).isFinishing()) {
                        Toasty.success(mContext, getResources().getString(R.string.popup_message3), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    /*
     Play sounds from assets folder
     looping = 1 loop sound / 0 = do not loop sound
    */
    @SuppressWarnings({"SameParameterValue", "deprecation"})
    private void playSound(String fileName, int looping) {
        if ((fileName != null) && (!fileName.equals("-1"))) {
            AssetFileDescriptor afd = null;
            try {
                afd = getAssets().openFd(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ((afd != null) && (afd.getLength() > 0) && (afd.getStartOffset() > 0)) {
                releaseSound();
                musicFile = new MediaPlayer();
                try {
                    long start;
                    long end;
                    start = afd.getStartOffset();
                    end = afd.getLength();
                    String fileCheckMP;
                    fileCheckMP = afd.getFileDescriptor().toString();
                    if (fileCheckMP != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_GAME)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .build();
                            musicFile.setAudioAttributes(audioAttributes);
                        } else {
                            musicFile.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        }
                        if (musicFile != null) {
                            musicFile.reset();
                        }
                        musicFile.setDataSource(afd.getFileDescriptor(), start, end);
                        afd.close();
                        try {
                            if (musicFile != null) {
                                musicFile.prepare();
                                if (looping == 1) {
                                    musicFile.setLooping(true);
                                } else {
                                    musicFile.setLooping(false);
                                }
                                if (musicFile.getDuration() > 0) {
                                    musicFile.start();
                                    musicFile.setVolume(3, 3);
                                }
                            }
                        } catch (IllegalStateException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch(IllegalArgumentException | IllegalStateException | IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //Release sound from assests folder
    private void releaseSound() {
        if (musicFile != null) {
            musicFile.release();
            musicFile = null;
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
        releaseSound();
        //Make sure popup message is reset
        if (PopWelcome != null) {
            PopWelcome = null;
        }
    }


    //When clicking on the back key in the phone/tablet
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (goBackCounter >= 3)) {
            finish();
            System.exit(0);
        } else if ((keyCode == KeyEvent.KEYCODE_BACK) && (goBackCounter < 3)) {
            goBackCounter++;
            showMainScreen();
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            return true;
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