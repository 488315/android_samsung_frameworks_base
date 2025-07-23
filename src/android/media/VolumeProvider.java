package android.media;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public abstract class VolumeProvider {
    public static final int VOLUME_CONTROL_ABSOLUTE = 2;
    public static final int VOLUME_CONTROL_FIXED = 0;
    public static final int VOLUME_CONTROL_RELATIVE = 1;
    private Callback mCallback;
    private final String mControlId;
    private final int mControlType;
    private int mCurrentVolume;
    private final int mMaxVolume;

    public static abstract class Callback {
        public abstract void onVolumeChanged(VolumeProvider volumeProvider);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ControlType {
    }

    public void onAdjustVolume(int i) {
    }

    public void onSetVolumeTo(int i) {
    }

    public VolumeProvider(int i, int i2, int i3) {
        this(i, i2, i3, null);
    }

    public VolumeProvider(int i, int i2, int i3, String str) {
        this.mControlType = i;
        this.mMaxVolume = i2;
        this.mCurrentVolume = i3;
        this.mControlId = str;
    }

    public final int getVolumeControl() {
        return this.mControlType;
    }

    public final int getMaxVolume() {
        return this.mMaxVolume;
    }

    public final int getCurrentVolume() {
        return this.mCurrentVolume;
    }

    public final void setCurrentVolume(int i) {
        this.mCurrentVolume = i;
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onVolumeChanged(this);
        }
    }

    public final String getVolumeControlId() {
        return this.mControlId;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }
}
