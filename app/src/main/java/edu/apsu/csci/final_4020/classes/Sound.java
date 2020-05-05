package edu.apsu.csci.final_4020.classes;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import edu.apsu.csci.final_4020.R;

public class Sound {
    enum MediaState {INACTIVE, STARTED, PAUSED, STOPPED}
    private MediaState mediaState;
    private MediaPlayer mediaPlayer;
    private SoundPool soundPool;

    private Activity activity;

    public Sound(Activity a) {
        activity = a;
    }

    public void loadSoundPool() {
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        spBuilder.setMaxStreams(4);
        soundPool = spBuilder.build();

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                if (i1 == 0) {
                    Log.i("Loaded sound", Integer.toString(i));
                }
            }
        });

        soundPool.load(activity, R.raw.correct_answer, 1);
        soundPool.load(activity, R.raw.game_over, 1);
    }

    public void playSound(int resID) {
        if (resID == R.raw.correct_answer) {
            soundPool.play(1, 1.0f, 1.0f, 0, 0, 1.0f);
        } else if (resID == R.raw.game_over) {
            soundPool.play(2, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    public void playMusic(int resID) {
        if (mediaPlayer == null) {
            mediaState = MediaState.INACTIVE;

            mediaPlayer = MediaPlayer.create(activity.getApplicationContext(), resID);
            mediaPlayer.setLooping(true);

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    mediaState = MediaState.STARTED;
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.i("Music ", "ERROR");
                    return false;
                }
            });
        } else if (mediaState == MediaState.PAUSED) {
            mediaPlayer.start();
            mediaState = MediaState.STARTED;
        } else if (mediaState == MediaState.STOPPED) {
            mediaPlayer.prepareAsync();
            mediaState = MediaState.INACTIVE;
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaState = MediaState.STOPPED;
        }
    }

    public void releaseSound() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            mediaState = MediaState.INACTIVE;
        }
    }
}
