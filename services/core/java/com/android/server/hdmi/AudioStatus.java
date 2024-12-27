package com.android.server.hdmi;

import java.util.Objects;

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
