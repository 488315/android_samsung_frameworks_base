package com.android.server.display.brightness.strategy;

import android.content.Context;
import android.hardware.display.BrightnessConfiguration;
import android.provider.Settings;
import android.view.Display;
import com.android.server.display.AutomaticBrightnessController;
import com.android.server.display.DisplayPowerController2;
import com.android.server.display.brightness.BrightnessEvent;
import com.android.server.display.brightness.BrightnessUtils;
import com.android.server.power.PowerManagerUtil;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class AutomaticBrightnessStrategy {
  public boolean mAppliedTemporaryAutoBrightnessAdjustment;
  public boolean mAutoBrightnessAdjustmentChanged;
  public AutomaticBrightnessController mAutomaticBrightnessController;
  public BrightnessConfiguration mBrightnessConfiguration;
  public final Context mContext;
  public final int mDisplayId;
  public boolean mGameAutoBrightnessLocked;
  public int mAutoBrightnessAdjustmentReasonsFlags = 0;
  public boolean mShouldResetShortTermModel = false;
  public boolean mAppliedAutoBrightness = false;
  public boolean mUseAutoBrightness = false;
  public boolean mIsAutoBrightnessEnabled = false;
  public boolean mIsShortTermModelActive = false;
  public float mAutoBrightnessAdjustment = getAutoBrightnessAdjustmentSetting();
  public float mPendingAutoBrightnessAdjustment = Float.NaN;
  public float mTemporaryAutoBrightnessAdjustment = Float.NaN;

  public AutomaticBrightnessStrategy(Context context, int i) {
    this.mContext = context;
    this.mDisplayId = i;
  }

  public void setAutoBrightnessState(
      int i,
      boolean z,
      float f,
      int i2,
      int i3,
      float f2,
      boolean z2,
      boolean z3,
      boolean z4,
      int i4) {
    boolean z5 = false;
    boolean z6 = (z || PowerManagerUtil.isFakeAodAvailable(i4)) && Display.isDozeState(i);
    this.mIsAutoBrightnessEnabled =
        shouldUseAutoBrightness()
            && (i == 2 || z6)
            && !((!Float.isNaN(f) && i2 != 7 && i2 != 8)
                || this.mAutomaticBrightnessController == null
                || i2 == 10
                || this.mGameAutoBrightnessLocked
                || z3);
    if (shouldUseAutoBrightness() && i != 2 && !z6) {
      z5 = true;
    }
    accommodateUserBrightnessChanges(
        z2,
        f2,
        i3,
        this.mBrightnessConfiguration,
        this.mIsAutoBrightnessEnabled ? 1 : z5 ? 3 : 2,
        z4,
        i4);
  }

  public boolean isAutoBrightnessEnabled() {
    return this.mIsAutoBrightnessEnabled;
  }

  public void setBrightnessConfiguration(
      BrightnessConfiguration brightnessConfiguration, boolean z) {
    this.mBrightnessConfiguration = brightnessConfiguration;
    setShouldResetShortTermModel(z);
  }

  public boolean processPendingAutoBrightnessAdjustments() {
    this.mAutoBrightnessAdjustmentChanged = false;
    if (Float.isNaN(this.mPendingAutoBrightnessAdjustment)) {
      return false;
    }
    float f = this.mAutoBrightnessAdjustment;
    float f2 = this.mPendingAutoBrightnessAdjustment;
    if (f == f2) {
      this.mPendingAutoBrightnessAdjustment = Float.NaN;
      return false;
    }
    this.mAutoBrightnessAdjustment = f2;
    this.mPendingAutoBrightnessAdjustment = Float.NaN;
    this.mTemporaryAutoBrightnessAdjustment = Float.NaN;
    this.mAutoBrightnessAdjustmentChanged = true;
    return true;
  }

  public void setAutomaticBrightnessController(
      AutomaticBrightnessController automaticBrightnessController) {
    AutomaticBrightnessController automaticBrightnessController2 =
        this.mAutomaticBrightnessController;
    if (automaticBrightnessController == automaticBrightnessController2) {
      return;
    }
    if (automaticBrightnessController2 != null) {
      automaticBrightnessController2.stop();
    }
    this.mAutomaticBrightnessController = automaticBrightnessController;
  }

  public boolean shouldUseAutoBrightness() {
    return this.mUseAutoBrightness;
  }

  public void setUseAutoBrightness(boolean z) {
    this.mUseAutoBrightness = z;
  }

  public boolean isShortTermModelActive() {
    return this.mIsShortTermModelActive;
  }

  public void updatePendingAutoBrightnessAdjustments(boolean z) {
    float floatForUser =
        Settings.System.getFloatForUser(
            this.mContext.getContentResolver(),
            "screen_auto_brightness_adj",
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            -2);
    this.mPendingAutoBrightnessAdjustment =
        Float.isNaN(floatForUser)
            ? Float.NaN
            : BrightnessUtils.clampBrightnessAdjustment(floatForUser);
    if (z) {
      processPendingAutoBrightnessAdjustments();
    }
  }

  public void setTemporaryAutoBrightnessAdjustment(float f) {
    this.mTemporaryAutoBrightnessAdjustment = f;
  }

  public void dump(PrintWriter printWriter) {
    printWriter.println("AutomaticBrightnessStrategy:");
    printWriter.println("  mDisplayId=" + this.mDisplayId);
    printWriter.println("  mAutoBrightnessAdjustment=" + this.mAutoBrightnessAdjustment);
    printWriter.println(
        "  mPendingAutoBrightnessAdjustment=" + this.mPendingAutoBrightnessAdjustment);
    printWriter.println(
        "  mTemporaryAutoBrightnessAdjustment=" + this.mTemporaryAutoBrightnessAdjustment);
    printWriter.println("  mShouldResetShortTermModel=" + this.mShouldResetShortTermModel);
    printWriter.println("  mAppliedAutoBrightness=" + this.mAppliedAutoBrightness);
    printWriter.println(
        "  mAutoBrightnessAdjustmentChanged=" + this.mAutoBrightnessAdjustmentChanged);
    printWriter.println(
        "  mAppliedTemporaryAutoBrightnessAdjustment="
            + this.mAppliedTemporaryAutoBrightnessAdjustment);
    printWriter.println("  mUseAutoBrightness=" + this.mUseAutoBrightness);
    printWriter.println("  mWasShortTermModelActive=" + this.mIsShortTermModelActive);
    printWriter.println(
        "  mAutoBrightnessAdjustmentReasonsFlags=" + this.mAutoBrightnessAdjustmentReasonsFlags);
  }

  public boolean getAutoBrightnessAdjustmentChanged() {
    return this.mAutoBrightnessAdjustmentChanged;
  }

  public boolean isTemporaryAutoBrightnessAdjustmentApplied() {
    return this.mAppliedTemporaryAutoBrightnessAdjustment;
  }

  public float getAutomaticScreenBrightness(BrightnessEvent brightnessEvent) {
    AutomaticBrightnessController automaticBrightnessController =
        this.mAutomaticBrightnessController;
    float automaticScreenBrightness =
        automaticBrightnessController != null
            ? automaticBrightnessController.getAutomaticScreenBrightness(brightnessEvent)
            : Float.NaN;
    adjustAutomaticBrightnessStateIfValid(automaticScreenBrightness);
    return automaticScreenBrightness;
  }

  public int getAutoBrightnessAdjustmentReasonsFlags() {
    return this.mAutoBrightnessAdjustmentReasonsFlags;
  }

  public boolean hasAppliedAutoBrightness() {
    return this.mAppliedAutoBrightness;
  }

  public void adjustAutomaticBrightnessStateIfValid(float f) {
    boolean z = true;
    this.mAutoBrightnessAdjustmentReasonsFlags =
        isTemporaryAutoBrightnessAdjustmentApplied() ? 1 : 2;
    boolean isValidBrightnessValue = BrightnessUtils.isValidBrightnessValue(f);
    float f2 = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    if (!isValidBrightnessValue && f != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      z = false;
    }
    this.mAppliedAutoBrightness = z;
    AutomaticBrightnessController automaticBrightnessController =
        this.mAutomaticBrightnessController;
    if (automaticBrightnessController != null) {
      f2 = automaticBrightnessController.getAutomaticScreenBrightnessAdjustment();
    }
    if (!Float.isNaN(f2) && this.mAutoBrightnessAdjustment != f2) {
      putAutoBrightnessAdjustmentSetting(f2);
    } else {
      this.mAutoBrightnessAdjustmentReasonsFlags = 0;
    }
  }

  public void setShouldResetShortTermModel(boolean z) {
    this.mShouldResetShortTermModel = z;
  }

  public boolean shouldResetShortTermModel() {
    return this.mShouldResetShortTermModel;
  }

  public float getAutoBrightnessAdjustment() {
    return this.mAutoBrightnessAdjustment;
  }

  public float getPendingAutoBrightnessAdjustment() {
    return this.mPendingAutoBrightnessAdjustment;
  }

  public float getTemporaryAutoBrightnessAdjustment() {
    return this.mTemporaryAutoBrightnessAdjustment;
  }

  public void putAutoBrightnessAdjustmentSetting(float f) {
    if (this.mDisplayId == 0) {
      this.mAutoBrightnessAdjustment = f;
      Settings.System.putFloatForUser(
          this.mContext.getContentResolver(), "screen_auto_brightness_adj", f, -2);
    }
  }

  public void setAutoBrightnessApplied(boolean z) {
    this.mAppliedAutoBrightness = z;
  }

  public void accommodateUserBrightnessChanges(
      boolean z,
      float f,
      int i,
      BrightnessConfiguration brightnessConfiguration,
      int i2,
      boolean z2,
      int i3) {
    processPendingAutoBrightnessAdjustments();
    float updateTemporaryAutoBrightnessAdjustments = updateTemporaryAutoBrightnessAdjustments();
    this.mIsShortTermModelActive = false;
    AutomaticBrightnessController automaticBrightnessController =
        this.mAutomaticBrightnessController;
    if (automaticBrightnessController != null) {
      automaticBrightnessController.configure(
          i2,
          brightnessConfiguration,
          f,
          z,
          updateTemporaryAutoBrightnessAdjustments,
          this.mAutoBrightnessAdjustmentChanged,
          i,
          this.mShouldResetShortTermModel,
          z2,
          i3);
      this.mShouldResetShortTermModel = false;
      this.mIsShortTermModelActive = this.mAutomaticBrightnessController.hasUserDataPoints();
    }
  }

  public final float updateTemporaryAutoBrightnessAdjustments() {
    boolean z = !Float.isNaN(this.mTemporaryAutoBrightnessAdjustment);
    this.mAppliedTemporaryAutoBrightnessAdjustment = z;
    return z ? this.mTemporaryAutoBrightnessAdjustment : this.mAutoBrightnessAdjustment;
  }

  public final float getAutoBrightnessAdjustmentSetting() {
    float floatForUser =
        Settings.System.getFloatForUser(
            this.mContext.getContentResolver(),
            "screen_auto_brightness_adj",
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            -2);
    return Float.isNaN(floatForUser)
        ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON
        : BrightnessUtils.clampBrightnessAdjustment(floatForUser);
  }

  public void updateGameAutoBrightnessLock() {
    this.mGameAutoBrightnessLocked =
        Settings.Secure.getIntForUser(
                this.mContext.getContentResolver(), "game_autobrightness_lock", 0, -2)
            != 0;
  }

  public boolean isGameAutoBrightnessLocked() {
    return this.mGameAutoBrightnessLocked;
  }
}
