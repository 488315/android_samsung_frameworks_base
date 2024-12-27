package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.VibrationAttributes;
import android.util.Slog;

import com.samsung.android.server.vibrator.VibratorHelper;
import com.samsung.android.vibrator.SemHapticFeedbackConstants;

public abstract class SemVibration {
    public final VibrationAttributes mAttrs;
    public final int mDeviceId;
    public final CombinedVibration mEffect;
    public final int mIndex;
    public final int mMagnitude;
    public final String mOpPkg;
    public final String mReason;
    public final int mRepeat;
    public final IBinder mToken;
    public final int mUid;
    public final VibratorHelper mVibratorHelper;

    public SemVibration(SemVibrationBundle semVibrationBundle) {
        this.mToken = semVibrationBundle.mToken;
        this.mAttrs = semVibrationBundle.mAttrs;
        this.mUid = semVibrationBundle.mUid;
        this.mDeviceId = semVibrationBundle.mDeviceId;
        this.mOpPkg = semVibrationBundle.mOpPkg;
        this.mReason = semVibrationBundle.mReason;
        this.mEffect = semVibrationBundle.mEffect;
        this.mIndex = semVibrationBundle.mIndex;
        this.mRepeat = semVibrationBundle.mRepeat;
        this.mMagnitude = semVibrationBundle.mMagnitude;
        if (VibratorHelper.sInstance == null) {
            VibratorHelper.sInstance = new VibratorHelper();
        }
        this.mVibratorHelper = VibratorHelper.sInstance;
    }

    public final boolean commonValidation() {
        if (this.mMagnitude <= 0) {
            Slog.d("VibratorManagerService", "magnitude value is under 0");
            return false;
        }
        if (this.mRepeat > 0) {
            Slog.d("VibratorManagerService", "repeat value is wrong.");
            return false;
        }
        if (this.mToken != null) {
            return true;
        }
        Slog.d("VibratorManagerService", "token is null");
        return false;
    }

    public final Vibration.CallerInfo getCallerInfo() {
        return new Vibration.CallerInfo(
                this.mAttrs, this.mUid, this.mDeviceId, this.mOpPkg, this.mReason);
    }

    public final String getCommonLog() {
        StringBuilder sb = new StringBuilder();
        int i = this.mIndex;
        sb.append(SemHapticFeedbackConstants.getPatternTitle(i));
        sb.append("(");
        sb.append(i - 50024);
        sb.append("), repeat: ");
        sb.append(this.mRepeat);
        sb.append(", usage: ");
        sb.append(VibrationAttributes.usageToString(this.mAttrs.getUsage()));
        sb.append("(");
        sb.append(this.mAttrs.getUsage());
        sb.append("), magnitude: ");
        sb.append(this.mMagnitude);
        return sb.toString();
    }

    public abstract HalVibration getVibration();
}
