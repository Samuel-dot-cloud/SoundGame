package com.studiofive.soundgame;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import es.dmoral.toasty.Toasty;

public class MainActivity extends Activity {
    public static Integer goBackCounter = 0;
    public Context mContext;
    private MediaPlayer musicFile;
    private AudioManager audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializes audio sound volume
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

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

            //Drinks section
        }else if (view.getId() == R.id.drink3) {
            playSound("blender.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.success(mContext, getResources().getString(R.string.drink3), Toast.LENGTH_SHORT, true).show();
            }

        }else if (view.getId() == R.id.drink4) {
            playSound("milkshake.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.success(mContext, getResources().getString(R.string.drink4), Toast.LENGTH_SHORT, true).show();
            }
        }else if (view.getId() == R.id.drink7) {
            playSound("soda.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.success(mContext, getResources().getString(R.string.drink7), Toast.LENGTH_SHORT, true).show();
            }

            //Foods section
        }else if (view.getId() == R.id.foods3) {
            playSound("frying.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.success(mContext, getResources().getString(R.string.food3), Toast.LENGTH_SHORT, true).show();
            }
        }else if (view.getId() == R.id.foods4) {
            playSound("kitchen.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.success(mContext, getResources().getString(R.string.food4), Toast.LENGTH_SHORT, true).show();
            }
        }else if (view.getId() == R.id.foods7) {
            playSound("eating.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.success(mContext, getResources().getString(R.string.food7), Toast.LENGTH_SHORT, true).show();
            }

            //Fruits section
        }else if (view.getId() == R.id.fruit1) {
            playSound("juice.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.success(mContext, getResources().getString(R.string.fruit1), Toast.LENGTH_SHORT, true).show();
            }
        }else if (view.getId() == R.id.fruit3) {
            playSound("apple.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.success(mContext, getResources().getString(R.string.fruit3), Toast.LENGTH_SHORT, true).show();
            }
        }else if (view.getId() == R.id.fruit8) {
            playSound("nuts.mp3", 0);
            if (!((Activity) mContext).isFinishing()) {
                Toasty.success(mContext, getResources().getString(R.string.fruit8), Toast.LENGTH_SHORT, true).show();
            }
        }


    }

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