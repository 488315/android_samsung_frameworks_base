package com.android.server.hdmi;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AudioStatus {
    public final boolean mMute;
    public final int mVolume;

    public AudioStatus(int i, boolean z) {
        this.mVolume = Math.max(Math.min(i, 100), 0);
        this.mMute = z;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof AudioStatus)) {
            return false;
        }
        AudioStatus audioStatus = (AudioStatus) obj;
        return this.mVolume == audioStatus.mVolume && this.mMute == audioStatus.mMute;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mVolume), Boolean.valueOf(this.mMute));
    }

    public final String toString() {
        return "AudioStatus mVolume:" + this.mVolume + " mMute:" + this.mMute;
    }
}
