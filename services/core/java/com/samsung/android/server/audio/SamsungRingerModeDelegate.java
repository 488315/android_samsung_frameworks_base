package com.samsung.android.server.audio;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.media.AudioManagerInternal;
import android.media.VolumePolicy;
import android.provider.Settings;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SamsungRingerModeDelegate extends ContentObserver
        implements AudioManagerInternal.RingerModeDelegate {
    public AudioManagerInternal mAudioManager;
    public ContentResolver mContentResolver;
    public NotificationManager mNm;
    public int mZenMode;

    public final boolean canVolumeDownEnterSilent() {
        return true;
    }

    public final int getRingerModeAffectedStreams(int i) {
        return i | 294;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        super.onChange(z);
        int i = this.mZenMode;
        int i2 = Settings.Global.getInt(this.mContentResolver, "zen_mode", 0);
        this.mZenMode = i2;
        if (i == i2) {
            return;
        }
        if (i == 0 || i2 == 0) {
            this.mAudioManager.setRingerModeInternal(
                    this.mAudioManager.getRingerModeInternal(), "SamsungRingerModeDelegate");
        }
    }

    public final int onSetRingerModeExternal(
            int i, int i2, String str, int i3, VolumePolicy volumePolicy) {
        return i2;
    }

    public final int onSetRingerModeInternal(
            int i, int i2, String str, int i3, VolumePolicy volumePolicy) {
        if ((i2 == 1 || i2 == 2) && this.mNm.getZenMode() != 0) {
            return 0;
        }
        return i2;
    }

    public final String toString() {
        return "SamsungRingerModeDelegate";
    }
}
