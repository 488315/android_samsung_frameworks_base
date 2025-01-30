package com.samsung.android.server.audio;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManagerInternal;
import android.media.VolumePolicy;
import android.os.Handler;
import android.provider.Settings;
import com.android.server.LocalServices;

/* loaded from: classes2.dex */
public class SamsungRingerModeDelegate extends ContentObserver implements AudioManagerInternal.RingerModeDelegate {
    public AudioManagerInternal mAudioManager;
    public ContentResolver mContentResolver;
    public NotificationManager mNm;
    public int mZenMode;

    public boolean canVolumeDownEnterSilent() {
        return true;
    }

    public int getRingerModeAffectedStreams(int i) {
        return i | 294;
    }

    public int onSetRingerModeExternal(int i, int i2, String str, int i3, VolumePolicy volumePolicy) {
        return i2;
    }

    public String toString() {
        return "SamsungRingerModeDelegate";
    }

    public SamsungRingerModeDelegate(Context context) {
        super(new Handler());
        this.mNm = (NotificationManager) context.getSystemService("notification");
        init(context);
    }

    public final void init(Context context) {
        this.mZenMode = this.mNm.getZenMode();
        this.mAudioManager = (AudioManagerInternal) LocalServices.getService(AudioManagerInternal.class);
        ContentResolver contentResolver = context.getContentResolver();
        this.mContentResolver = contentResolver;
        contentResolver.registerContentObserver(Settings.Global.getUriFor("zen_mode"), false, this);
    }

    public int onSetRingerModeInternal(int i, int i2, String str, int i3, VolumePolicy volumePolicy) {
        if ((i2 == 1 || i2 == 2) && this.mNm.getZenMode() != 0) {
            return 0;
        }
        return i2;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        super.onChange(z);
        int i = this.mZenMode;
        int i2 = Settings.Global.getInt(this.mContentResolver, "zen_mode", 0);
        this.mZenMode = i2;
        if (i == i2) {
            return;
        }
        if (i == 0 || i2 == 0) {
            this.mAudioManager.setRingerModeInternal(this.mAudioManager.getRingerModeInternal(), "SamsungRingerModeDelegate");
        }
    }
}
