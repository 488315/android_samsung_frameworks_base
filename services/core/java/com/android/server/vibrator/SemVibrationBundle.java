package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.VibrationAttributes;
import com.android.server.vibrator.Vibration;
import java.util.Objects;

/* loaded from: classes3.dex */
public class SemVibrationBundle {
    public VibrationAttributes mAttrs;
    public int mDisplayId;
    public CombinedVibration mEffect;
    public int mIndex;
    public int mMagnitude;
    public String mOpPkg;
    public String mReason;
    public int mRepeat;
    public IBinder mToken;
    public int mUid;

    public SemVibrationBundle(IBinder iBinder, CombinedVibration combinedVibration, int i, int i2, int i3, Vibration.CallerInfo callerInfo) {
        this.mToken = iBinder;
        this.mEffect = combinedVibration;
        this.mIndex = i;
        this.mRepeat = i2;
        this.mMagnitude = i3;
        this.mAttrs = callerInfo.attrs;
        this.mUid = callerInfo.uid;
        this.mDisplayId = callerInfo.displayId;
        this.mOpPkg = callerInfo.opPkg;
        this.mReason = callerInfo.reason;
    }

    public IBinder getToken() {
        return this.mToken;
    }

    public VibrationAttributes getAttrs() {
        return this.mAttrs;
    }

    public int getUid() {
        return this.mUid;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public String getOpPkg() {
        return this.mOpPkg;
    }

    public String getReason() {
        return this.mReason;
    }

    public CombinedVibration getEffect() {
        return this.mEffect;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public int getRepeat() {
        return this.mRepeat;
    }

    public int getMagnitude() {
        return this.mMagnitude;
    }

    public void setMagnitude(int i) {
        this.mMagnitude = i;
    }

    public String toString() {
        return "SemVibrationBundle{mToken=" + this.mToken + ", mAttrs=" + this.mAttrs + ", mUid=" + this.mUid + ", mDisplayId=" + this.mDisplayId + ", mOpPkg='" + this.mOpPkg + "', mReason='" + this.mReason + "', mEffect=" + this.mEffect + ", mIndex=" + this.mIndex + ", mRepeat=" + this.mRepeat + ", mMagnitude=" + this.mMagnitude + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        SemVibrationBundle semVibrationBundle = (SemVibrationBundle) obj;
        return this.mUid == semVibrationBundle.mUid && this.mDisplayId == semVibrationBundle.mDisplayId && this.mIndex == semVibrationBundle.mIndex && this.mRepeat == semVibrationBundle.mRepeat && this.mMagnitude == semVibrationBundle.mMagnitude && Objects.equals(this.mToken, semVibrationBundle.mToken) && Objects.equals(this.mAttrs, semVibrationBundle.mAttrs) && Objects.equals(this.mOpPkg, semVibrationBundle.mOpPkg) && Objects.equals(this.mReason, semVibrationBundle.mReason) && Objects.equals(this.mEffect, semVibrationBundle.mEffect);
    }
}
