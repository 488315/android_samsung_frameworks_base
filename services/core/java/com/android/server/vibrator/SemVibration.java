package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.VibrationAttributes;
import android.util.Slog;
import com.samsung.android.server.vibrator.VibratorHelper;
import com.samsung.android.vibrator.SemHapticFeedbackConstants;

/* loaded from: classes3.dex */
public abstract class SemVibration {
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
  public VibratorHelper mVibratorHelper = VibratorHelper.getInstance();

  public abstract HalVibration getVibration();

  public SemVibration(SemVibrationBundle semVibrationBundle) {
    this.mToken = semVibrationBundle.getToken();
    this.mAttrs = semVibrationBundle.getAttrs();
    this.mUid = semVibrationBundle.getUid();
    this.mDisplayId = semVibrationBundle.getDisplayId();
    this.mOpPkg = semVibrationBundle.getOpPkg();
    this.mReason = semVibrationBundle.getReason();
    this.mEffect = semVibrationBundle.getEffect();
    this.mIndex = semVibrationBundle.getIndex();
    this.mRepeat = semVibrationBundle.getRepeat();
    this.mMagnitude = semVibrationBundle.getMagnitude();
  }

  public boolean commonValidation() {
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

  public Vibration.CallerInfo getCallerInfo() {
    return new Vibration.CallerInfo(
        this.mAttrs, this.mUid, this.mDisplayId, this.mOpPkg, this.mReason);
  }

  public String getCommonLog() {
    return SemHapticFeedbackConstants.getPatternTitle(this.mIndex)
        + "("
        + (this.mIndex - 50024)
        + "), repeat: "
        + this.mRepeat
        + ", usage: "
        + VibrationAttributes.usageToString(this.mAttrs.getUsage())
        + "("
        + this.mAttrs.getUsage()
        + "), magnitude: "
        + this.mMagnitude;
  }
}
