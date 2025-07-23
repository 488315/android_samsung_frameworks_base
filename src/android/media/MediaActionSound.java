package android.media;

import android.media.AudioAttributes;
import android.media.IAudioService;
import android.media.SoundPool;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

/* loaded from: classes2.dex */
public class MediaActionSound {
    public static final int FOCUS_COMPLETE = 1;
    private static final int NUM_MEDIA_SOUND_STREAMS = 1;
    public static final int SHUTTER_CLICK = 0;
    private static final String[] SOUND_DIRS = {"/product/media/audio/ui/", "/system/media/audio/ui/"};
    private static final String[] SOUND_FILES = {"camera_click.ogg", "camera_focus.ogg", "VideoRecord.ogg", "VideoStop.ogg"};
    public static final int START_VIDEO_RECORDING = 2;
    private static final int STATE_LOADED = 3;
    private static final int STATE_LOADING = 1;
    private static final int STATE_LOADING_PLAY_REQUESTED = 2;
    private static final int STATE_NOT_LOADED = 0;
    public static final int STOP_VIDEO_RECORDING = 3;
    private static final String TAG = "MediaActionSound";
    private SoundPool.OnLoadCompleteListener mLoadCompleteListener = new SoundPool.OnLoadCompleteListener() { // from class: android.media.MediaActionSound.1
        @Override // android.media.SoundPool.OnLoadCompleteListener
        public void onLoadComplete(SoundPool soundPool, int i, int i2) {
            int i3 = 0;
            for (SoundState soundState : MediaActionSound.this.mSounds) {
                if (soundState.id == i) {
                    synchronized (soundState) {
                        if (i2 != 0) {
                            soundState.state = 0;
                            soundState.id = 0;
                            Log.e(MediaActionSound.TAG, "OnLoadCompleteListener() error: " + i2 + " loading sound: " + soundState.name);
                            return;
                        }
                        int i4 = soundState.state;
                        if (i4 == 1) {
                            soundState.state = 3;
                        } else if (i4 == 2) {
                            i3 = soundState.id;
                            soundState.state = 3;
                        } else {
                            Log.e(MediaActionSound.TAG, "OnLoadCompleteListener() called in wrong state: " + soundState.state + " for sound: " + soundState.name);
                        }
                        int i5 = i3;
                        if (i5 != 0) {
                            soundPool.play(i5, 1.0f, 1.0f, 0, 0, 1.0f);
                            return;
                        }
                        return;
                    }
                }
            }
        }
    };
    private SoundPool mSoundPool;
    private SoundState[] mSounds;

    public static boolean mustPlayShutterSound() {
        try {
            return IAudioService.Stub.asInterface(ServiceManager.getService("audio")).isCameraSoundForced();
        } catch (RemoteException unused) {
            Log.e(TAG, "audio service is unavailable for queries, defaulting to false");
            return false;
        }
    }

    private class SoundState {
        public final int name;
        public int id = 0;
        public int state = 0;

        public SoundState(MediaActionSound mediaActionSound, int i) {
            this.name = i;
        }
    }

    public MediaActionSound() {
        SoundPool build = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(13).setFlags(1).setContentType(4).build()).build();
        this.mSoundPool = build;
        build.setOnLoadCompleteListener(this.mLoadCompleteListener);
        this.mSounds = new SoundState[SOUND_FILES.length];
        int i = 0;
        while (true) {
            SoundState[] soundStateArr = this.mSounds;
            if (i >= soundStateArr.length) {
                return;
            }
            soundStateArr[i] = new SoundState(this, i);
            i++;
        }
    }

    private int loadSound(SoundState soundState) {
        String str = SOUND_FILES[soundState.name];
        for (String str2 : SOUND_DIRS) {
            int load = this.mSoundPool.load(str2 + str, 1);
            if (load > 0) {
                soundState.state = 1;
                soundState.id = load;
                return load;
            }
        }
        return 0;
    }

    public void load(int i) {
        if (i < 0 || i >= SOUND_FILES.length) {
            throw new RuntimeException("Unknown sound requested: " + i);
        }
        SoundState soundState = this.mSounds[i];
        synchronized (soundState) {
            if (soundState.state == 0) {
                if (loadSound(soundState) <= 0) {
                    Log.e(TAG, "load() error loading sound: " + i);
                }
            } else {
                Log.e(TAG, "load() called in wrong state: " + soundState + " for sound: " + i);
            }
        }
    }

    public void play(int i) {
        if (i < 0 || i >= SOUND_FILES.length) {
            throw new RuntimeException("Unknown sound requested: " + i);
        }
        SoundState soundState = this.mSounds[i];
        synchronized (soundState) {
            int i2 = soundState.state;
            if (i2 == 0) {
                loadSound(soundState);
                if (loadSound(soundState) <= 0) {
                    Log.e(TAG, "play() error loading sound: " + i);
                }
                soundState.state = 2;
            } else {
                if (i2 != 1) {
                    if (i2 == 3) {
                        this.mSoundPool.play(soundState.id, 1.0f, 1.0f, 0, 0, 1.0f);
                    } else {
                        Log.e(TAG, "play() called in wrong state: " + soundState.state + " for sound: " + i);
                    }
                }
                soundState.state = 2;
            }
        }
    }

    public void release() {
        if (this.mSoundPool != null) {
            for (SoundState soundState : this.mSounds) {
                synchronized (soundState) {
                    soundState.state = 0;
                    soundState.id = 0;
                }
            }
            this.mSoundPool.release();
            this.mSoundPool = null;
        }
    }
}
