package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.VibrationAttributes;

import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemVibrationBundle {
    public VibrationAttributes mAttrs;
    public int mDeviceId;
    public CombinedVibration mEffect;
    public int mIndex;
    public int mMagnitude;
    public String mOpPkg;
    public String mReason;
    public int mRepeat;
    public IBinder mToken;
    public int mUid;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || SemVibrationBundle.class != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        SemVibrationBundle semVibrationBundle = (SemVibrationBundle) obj;
        return this.mUid == semVibrationBundle.mUid
                && this.mDeviceId == semVibrationBundle.mDeviceId
                && this.mIndex == semVibrationBundle.mIndex
                && this.mRepeat == semVibrationBundle.mRepeat
                && this.mMagnitude == semVibrationBundle.mMagnitude
                && Objects.equals(this.mToken, semVibrationBundle.mToken)
                && Objects.equals(this.mAttrs, semVibrationBundle.mAttrs)
                && Objects.equals(this.mOpPkg, semVibrationBundle.mOpPkg)
                && Objects.equals(this.mReason, semVibrationBundle.mReason)
                && Objects.equals(this.mEffect, semVibrationBundle.mEffect);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SemVibrationBundle{mToken=");
        sb.append(this.mToken);
        sb.append(", mAttrs=");
        sb.append(this.mAttrs);
        sb.append(", mUid=");
        sb.append(this.mUid);
        sb.append(", mDeviceId=");
        sb.append(this.mDeviceId);
        sb.append(", mOpPkg='");
        sb.append(this.mOpPkg);
        sb.append("', mReason='");
        sb.append(this.mReason);
        sb.append("', mEffect=");
        sb.append(this.mEffect);
        sb.append(", mIndex=");
        sb.append(this.mIndex);
        sb.append(", mRepeat=");
        sb.append(this.mRepeat);
        sb.append(", mMagnitude=");
        return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                sb, this.mMagnitude, '}');
    }
}
