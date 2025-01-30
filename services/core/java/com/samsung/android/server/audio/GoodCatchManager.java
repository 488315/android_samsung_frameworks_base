package com.samsung.android.server.audio;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.sepunion.SemGoodCatchManager;

/* loaded from: classes2.dex */
public class GoodCatchManager {
    public static final String[] SOUND_FUNC = {"ringermode", "callmode", "mediavolume", "playback"};
    public final Context mContext;
    public String mModule;
    public SemGoodCatchManager mSemGoodCatchManager;
    public boolean[] mSoundFunc = {false, false, false, false};
    public boolean mVibrationFunc = false;
    public SemGoodCatchManager.OnStateChangeListener mAudioStateListener = new SemGoodCatchManager.OnStateChangeListener() { // from class: com.samsung.android.server.audio.GoodCatchManager.1
        public void onStart(String str) {
            Log.d("AS.GoodCatchManager", "onStart " + str);
            if (GoodCatchManager.SOUND_FUNC[0].equals(str)) {
                GoodCatchManager.this.mSoundFunc[0] = true;
                return;
            }
            if (GoodCatchManager.SOUND_FUNC[1].equals(str)) {
                GoodCatchManager.this.mSoundFunc[1] = true;
            } else if (GoodCatchManager.SOUND_FUNC[2].equals(str)) {
                GoodCatchManager.this.mSoundFunc[2] = true;
            } else if (GoodCatchManager.SOUND_FUNC[3].equals(str)) {
                GoodCatchManager.this.mSoundFunc[3] = true;
            }
        }

        public void onStop(String str) {
            Log.d("AS.GoodCatchManager", "onStop " + str);
            if (GoodCatchManager.SOUND_FUNC[0].equals(str)) {
                GoodCatchManager.this.mSoundFunc[0] = false;
                return;
            }
            if (GoodCatchManager.SOUND_FUNC[1].equals(str)) {
                GoodCatchManager.this.mSoundFunc[1] = false;
            } else if (GoodCatchManager.SOUND_FUNC[2].equals(str)) {
                GoodCatchManager.this.mSoundFunc[2] = false;
            } else if (GoodCatchManager.SOUND_FUNC[3].equals(str)) {
                GoodCatchManager.this.mSoundFunc[3] = false;
            }
        }
    };
    public SemGoodCatchManager.OnStateChangeListener mVibrateStateListener = new SemGoodCatchManager.OnStateChangeListener() { // from class: com.samsung.android.server.audio.GoodCatchManager.2
        public void onStart(String str) {
            Log.d("AS.GoodCatchManager", "onStart " + str);
            GoodCatchManager.this.mVibrationFunc = true;
        }

        public void onStop(String str) {
            Log.d("AS.GoodCatchManager", "onStop " + str);
            GoodCatchManager.this.mVibrationFunc = false;
        }
    };

    public boolean isRingerModeCatchEnabled() {
        if (TextUtils.equals("AudioService", this.mModule)) {
            return this.mSoundFunc[0];
        }
        return false;
    }

    public boolean isCallModeCatchEnabled() {
        if (TextUtils.equals("AudioService", this.mModule)) {
            return this.mSoundFunc[1];
        }
        return false;
    }

    public boolean isVibrateCatchEnabled() {
        if (TextUtils.equals("VibratorService", this.mModule)) {
            return this.mVibrationFunc;
        }
        return false;
    }

    public boolean isMediaVolumeCatchEnabled() {
        if (TextUtils.equals("AudioService", this.mModule)) {
            return this.mSoundFunc[2];
        }
        return false;
    }

    public boolean isPlaybackCatchEnabled() {
        if (TextUtils.equals("AudioService", this.mModule)) {
            return this.mSoundFunc[3];
        }
        return false;
    }

    public GoodCatchManager(Context context, String str) {
        this.mContext = context;
        this.mModule = str;
        if (TextUtils.equals("AudioService", str)) {
            this.mSemGoodCatchManager = new SemGoodCatchManager(context, "AudioService", SOUND_FUNC, this.mAudioStateListener);
        } else if (TextUtils.equals("VibratorService", str)) {
            this.mSemGoodCatchManager = new SemGoodCatchManager(context, "VibratorService", new String[]{"vibration"}, this.mVibrateStateListener);
        }
    }

    public void updateCallMode(String str, int i, String str2) {
        this.mSemGoodCatchManager.update(SOUND_FUNC[1], str, i, "", str2);
    }

    public void updateRingerMode(String str, int i, String str2) {
        this.mSemGoodCatchManager.update(SOUND_FUNC[0], str, i, "", str2);
    }

    public void updateVibrateMode(String str) {
        this.mSemGoodCatchManager.update("vibration", str, 0, "", "");
    }

    public void updateVibrateMode(String str, int i, String str2) {
        this.mSemGoodCatchManager.update("vibration", str, i, "", str2);
    }

    public void updateMediaVolume(String str, String str2) {
        this.mSemGoodCatchManager.update(SOUND_FUNC[2], str, 0, "", str2);
    }

    public void updatePlayback(String str, int i) {
        this.mSemGoodCatchManager.update(SOUND_FUNC[3], str, i, "", "");
    }
}
