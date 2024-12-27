package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import com.sec.ims.presence.ServiceTuple;

public final class ScreenshotCaptureSound {
    public static final String[] SOUND_FILES = {"/system/media/audio/ui/Shutter.ogg"};
    public final Context mContext;
    public final int[] mForcedSoundIds;
    public final SoundPool mSoundPool = createSoundPool(false);
    public final SoundPool mForcedSoundPool = createSoundPool(true);
    public final int[] mSoundIds = new int[1];

    public ScreenshotCaptureSound(Context context) {
        this.mContext = context;
        int i = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.mSoundIds;
            if (i2 >= iArr.length) {
                break;
            }
            iArr[i2] = -1;
            i2++;
        }
        this.mForcedSoundIds = new int[1];
        while (true) {
            int[] iArr2 = this.mForcedSoundIds;
            if (i >= iArr2.length) {
                return;
            }
            iArr2[i] = -1;
            i++;
        }
    }

    public static SoundPool createSoundPool(boolean z) {
        Log.d("Screenshot", "createSoundPool : " + z);
        AudioAttributes.Builder usage = new AudioAttributes.Builder().setContentType(4).setUsage(5);
        if (z) {
            usage.setLegacyStreamType(AudioManager.semGetStreamType(5));
        } else {
            usage.setLegacyStreamType(1);
        }
        usage.semAddAudioTag("CAMERA");
        usage.semAddAudioTag("stv_shutter");
        SoundPool build = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(usage.build()).build();
        build.setOnLoadCompleteListener(null);
        return build;
    }

    public final synchronized void play(boolean z) {
        SoundPool soundPool;
        float f;
        try {
            String[] strArr = SOUND_FILES;
            SoundPool soundPool2 = this.mSoundPool;
            int[] iArr = this.mSoundIds;
            int i = iArr[0];
            if (z) {
                SoundPool soundPool3 = this.mForcedSoundPool;
                soundPool = soundPool3;
                i = this.mForcedSoundIds[0];
            } else {
                soundPool = soundPool2;
            }
            if (i != -1) {
                try {
                    f = Float.parseFloat(((AudioManager) this.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO)).getParameters("g_volume_situation_key;type=3;device=0"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    f = 1.0f;
                }
                float f2 = f;
                soundPool.play(i, f2, f2, 0, 0, 1.0f);
            } else if (z) {
                this.mForcedSoundIds[0] = this.mForcedSoundPool.load(strArr[0], 1);
            } else {
                iArr[0] = soundPool2.load(strArr[0], 1);
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
