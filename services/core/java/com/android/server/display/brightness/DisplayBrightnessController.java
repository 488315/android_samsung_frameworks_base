package com.android.server.display.brightness;

import android.R;
import android.content.Context;
import android.hardware.display.DisplayManagerInternal;
import android.os.HandlerExecutor;
import android.provider.Settings;
import android.util.IndentingPrintWriter;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.server.display.AutomaticBrightnessController;
import com.android.server.display.BrightnessSetting;
import com.android.server.display.DisplayBrightnessState;
import com.android.server.display.DisplayPowerController2;
import com.android.server.display.brightness.strategy.DisplayBrightnessStrategy;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public final class DisplayBrightnessController {
  public AutomaticBrightnessController mAutomaticBrightnessController;
  public final HandlerExecutor mBrightnessChangeExecutor;
  public final BrightnessSetting mBrightnessSetting;
  public BrightnessSetting.BrightnessSettingListener mBrightnessSettingListener;
  public float mCurrentScreenBrightness;
  public DisplayBrightnessStrategy mDisplayBrightnessStrategy;
  public DisplayBrightnessStrategySelector mDisplayBrightnessStrategySelector;
  public final int mDisplayId;
  public boolean mIsCoverDisplay;
  public Runnable mOnBrightnessChangeRunnable;
  public float mPendingScreenBrightness;
  public final boolean mPersistBrightnessNitsForDefaultDisplay;
  public final float mScreenBrightnessDefault;
  public final String mTag;
  public final Object mLock = new Object();
  public float mLastUserSetScreenBrightness = Float.NaN;

  public DisplayBrightnessController(
      Context context,
      Injector injector,
      int i,
      float f,
      BrightnessSetting brightnessSetting,
      Runnable runnable,
      HandlerExecutor handlerExecutor) {
    injector = injector == null ? new Injector() : injector;
    this.mDisplayId = i;
    this.mIsCoverDisplay = i == 1 && PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY;
    this.mBrightnessSetting = brightnessSetting;
    this.mPendingScreenBrightness = Float.NaN;
    this.mScreenBrightnessDefault = BrightnessUtils.clampAbsoluteBrightness(f);
    this.mCurrentScreenBrightness = getScreenBrightnessSettingOnBootPhase(context);
    this.mOnBrightnessChangeRunnable = runnable;
    this.mDisplayBrightnessStrategySelector =
        injector.getDisplayBrightnessStrategySelector(context, i);
    this.mBrightnessChangeExecutor = handlerExecutor;
    this.mPersistBrightnessNitsForDefaultDisplay =
        context.getResources().getBoolean(R.bool.config_magnification_area);
    this.mTag = "DisplayBrightnessController[" + i + "]";
  }

  public DisplayBrightnessState updateBrightness(
      DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, int i) {
    DisplayBrightnessState updateBrightness;
    synchronized (this.mLock) {
      DisplayBrightnessStrategy selectStrategy =
          this.mDisplayBrightnessStrategySelector.selectStrategy(displayPowerRequest, i);
      this.mDisplayBrightnessStrategy = selectStrategy;
      updateBrightness = selectStrategy.updateBrightness(displayPowerRequest);
    }
    return updateBrightness;
  }

  public void setTemporaryBrightness(Float f) {
    synchronized (this.mLock) {
      setTemporaryBrightnessLocked(f.floatValue());
    }
  }

  public void setBrightnessToFollow(Float f) {
    synchronized (this.mLock) {
      this.mDisplayBrightnessStrategySelector
          .getFollowerDisplayBrightnessStrategy()
          .setBrightnessToFollow(f.floatValue());
    }
  }

  public boolean isAllowAutoBrightnessWhileDozingConfig() {
    boolean isAllowAutoBrightnessWhileDozingConfig;
    synchronized (this.mLock) {
      isAllowAutoBrightnessWhileDozingConfig =
          this.mDisplayBrightnessStrategySelector.isAllowAutoBrightnessWhileDozingConfig();
    }
    return isAllowAutoBrightnessWhileDozingConfig;
  }

  public void setAndNotifyCurrentScreenBrightness(float f) {
    boolean z;
    synchronized (this.mLock) {
      z = f != this.mCurrentScreenBrightness;
      setCurrentScreenBrightnessLocked(f);
    }
    if (z) {
      notifyCurrentScreenBrightness();
    }
  }

  public float getCurrentBrightness() {
    float f;
    synchronized (this.mLock) {
      f = this.mCurrentScreenBrightness;
    }
    return f;
  }

  public float getPendingScreenBrightness() {
    float f;
    synchronized (this.mLock) {
      f = this.mPendingScreenBrightness;
    }
    return f;
  }

  public void setPendingScreenBrightness(float f) {
    synchronized (this.mLock) {
      this.mPendingScreenBrightness = f;
    }
  }

  public boolean updateUserSetScreenBrightness() {
    synchronized (this.mLock) {
      if (!BrightnessUtils.isValidBrightnessValue(this.mPendingScreenBrightness)) {
        if (BrightnessSynchronizer.floatEquals(
            getTemporaryBrightnessLocked(), this.mCurrentScreenBrightness)) {
          setTemporaryBrightnessLocked(Float.NaN);
        }
        return false;
      }
      float f = this.mCurrentScreenBrightness;
      float f2 = this.mPendingScreenBrightness;
      if (f == f2) {
        this.mPendingScreenBrightness = Float.NaN;
        setTemporaryBrightnessLocked(Float.NaN);
        return false;
      }
      setCurrentScreenBrightnessLocked(f2);
      this.mLastUserSetScreenBrightness = this.mPendingScreenBrightness;
      this.mPendingScreenBrightness = Float.NaN;
      setTemporaryBrightnessLocked(Float.NaN);
      notifyCurrentScreenBrightness();
      return true;
    }
  }

  public void registerBrightnessSettingChangeListener(
      BrightnessSetting.BrightnessSettingListener brightnessSettingListener) {
    this.mBrightnessSettingListener = brightnessSettingListener;
    this.mBrightnessSetting.registerListener(brightnessSettingListener);
  }

  public float getLastUserSetScreenBrightness() {
    float f;
    synchronized (this.mLock) {
      f = this.mLastUserSetScreenBrightness;
    }
    return f;
  }

  public float getScreenBrightnessSetting() {
    float clampAbsoluteBrightness;
    float brightness = this.mBrightnessSetting.getBrightness();
    synchronized (this.mLock) {
      if (Float.isNaN(brightness)) {
        Slog.m72d(
            this.mTag, "getScreenBrightnessSetting: default: " + this.mScreenBrightnessDefault);
        brightness = this.mScreenBrightnessDefault;
      }
      clampAbsoluteBrightness = BrightnessUtils.clampAbsoluteBrightness(brightness);
    }
    return clampAbsoluteBrightness;
  }

  public final float getScreenBrightnessSettingOnBootPhase(Context context) {
    float f;
    if (this.mDisplayId == 0) {
      f =
          BrightnessSynchronizer.brightnessIntToFloat(
              Settings.System.getIntForUser(
                  context.getContentResolver(), "screen_brightness", -1, -2));
      if (Float.isNaN(f)) {
        Slog.m72d(
            this.mTag,
            "getScreenBrightnessSettingOnBootPhase: default: " + this.mScreenBrightnessDefault);
        f = this.mScreenBrightnessDefault;
      }
    } else if (this.mIsCoverDisplay) {
      f =
          BrightnessSynchronizer.brightnessIntToFloat(
              Settings.System.getIntForUser(
                  context.getContentResolver(),
                  "sub_screen_brightness",
                  context
                      .getResources()
                      .getInteger(R.integer.config_triplePressOnStemPrimaryBehavior),
                  -2));
      if (Float.isNaN(f)) {
        f = this.mScreenBrightnessDefault;
      }
    } else {
      f = this.mScreenBrightnessDefault;
    }
    this.mBrightnessSetting.setBrightness(f);
    return f;
  }

  public void setBrightness(float f) {
    this.mBrightnessSetting.setBrightness(f);
    if (this.mDisplayId == 0 && this.mPersistBrightnessNitsForDefaultDisplay) {
      float convertToNits = convertToNits(f);
      if (convertToNits >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        this.mBrightnessSetting.setBrightnessNitsForDefaultDisplay(convertToNits);
      }
    }
  }

  public boolean updateScreenBrightnessSetting(float f) {
    float clampAbsoluteBrightness = BrightnessUtils.clampAbsoluteBrightness(f);
    synchronized (this.mLock) {
      if (BrightnessUtils.isValidBrightnessValue(clampAbsoluteBrightness)
          && clampAbsoluteBrightness != this.mCurrentScreenBrightness) {
        setCurrentScreenBrightnessLocked(clampAbsoluteBrightness);
        notifyCurrentScreenBrightness();
        setBrightness(clampAbsoluteBrightness);
        return true;
      }
      return false;
    }
  }

  public void setAutomaticBrightnessController(
      AutomaticBrightnessController automaticBrightnessController) {
    this.mAutomaticBrightnessController = automaticBrightnessController;
    loadNitBasedBrightnessSetting();
  }

  public float convertToNits(float f) {
    AutomaticBrightnessController automaticBrightnessController =
        this.mAutomaticBrightnessController;
    if (automaticBrightnessController == null) {
      return -1.0f;
    }
    return automaticBrightnessController.convertToNits(f);
  }

  public float convertToAdjustedNits(float f) {
    AutomaticBrightnessController automaticBrightnessController =
        this.mAutomaticBrightnessController;
    if (automaticBrightnessController == null) {
      return -1.0f;
    }
    return automaticBrightnessController.convertToAdjustedNits(f);
  }

  public float convertToFloatScale(float f) {
    AutomaticBrightnessController automaticBrightnessController =
        this.mAutomaticBrightnessController;
    if (automaticBrightnessController == null) {
      return Float.NaN;
    }
    return automaticBrightnessController.convertToFloatScale(f);
  }

  public void stop() {
    BrightnessSetting brightnessSetting = this.mBrightnessSetting;
    if (brightnessSetting != null) {
      brightnessSetting.unregisterListener(this.mBrightnessSettingListener);
    }
  }

  public void dump(PrintWriter printWriter) {
    printWriter.println();
    printWriter.println("DisplayBrightnessController:");
    printWriter.println("  mDisplayId=: " + this.mDisplayId);
    printWriter.println("  mScreenBrightnessDefault=" + this.mScreenBrightnessDefault);
    printWriter.println(
        "  mPersistBrightnessNitsForDefaultDisplay="
            + this.mPersistBrightnessNitsForDefaultDisplay);
    synchronized (this.mLock) {
      printWriter.println("  mPendingScreenBrightness=" + this.mPendingScreenBrightness);
      printWriter.println("  mCurrentScreenBrightness=" + this.mCurrentScreenBrightness);
      printWriter.println("  mLastUserSetScreenBrightness=" + this.mLastUserSetScreenBrightness);
      if (this.mDisplayBrightnessStrategy != null) {
        printWriter.println(
            "  Last selected DisplayBrightnessStrategy= "
                + this.mDisplayBrightnessStrategy.getName());
      }
      this.mDisplayBrightnessStrategySelector.dump(new IndentingPrintWriter(printWriter, " "));
    }
  }

  /* JADX INFO: Access modifiers changed from: package-private */
  public class Injector {
    public DisplayBrightnessStrategySelector getDisplayBrightnessStrategySelector(
        Context context, int i) {
      return new DisplayBrightnessStrategySelector(context, null, i);
    }
  }

  public BrightnessSetting.BrightnessSettingListener getBrightnessSettingListener() {
    return this.mBrightnessSettingListener;
  }

  public DisplayBrightnessStrategy getCurrentDisplayBrightnessStrategy() {
    DisplayBrightnessStrategy displayBrightnessStrategy;
    synchronized (this.mLock) {
      displayBrightnessStrategy = this.mDisplayBrightnessStrategy;
    }
    return displayBrightnessStrategy;
  }

  public final void setTemporaryBrightnessLocked(float f) {
    this.mDisplayBrightnessStrategySelector
        .getTemporaryDisplayBrightnessStrategy()
        .setTemporaryScreenBrightness(f);
  }

  public final float getTemporaryBrightnessLocked() {
    return this.mDisplayBrightnessStrategySelector
        .getTemporaryDisplayBrightnessStrategy()
        .getTemporaryScreenBrightness();
  }

  public final void setCurrentScreenBrightnessLocked(float f) {
    if (f != this.mCurrentScreenBrightness) {
      this.mCurrentScreenBrightness = f;
    }
  }

  public final void notifyCurrentScreenBrightness() {
    this.mBrightnessChangeExecutor.execute(this.mOnBrightnessChangeRunnable);
  }

  /* JADX WARN: Removed duplicated region for block: B:12:0x002b  */
  /* JADX WARN: Removed duplicated region for block: B:15:0x0032 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void loadNitBasedBrightnessSetting() {
    float f;
    if (this.mDisplayId == 0 && this.mPersistBrightnessNitsForDefaultDisplay) {
      float brightnessNitsForDefaultDisplay =
          this.mBrightnessSetting.getBrightnessNitsForDefaultDisplay();
      if (brightnessNitsForDefaultDisplay >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        f = convertToFloatScale(brightnessNitsForDefaultDisplay);
        if (BrightnessUtils.isValidBrightnessValue(f)) {
          this.mBrightnessSetting.setBrightness(f);
          if (Float.isNaN(f)) {
            f = getScreenBrightnessSetting();
          }
          synchronized (this.mLock) {
            this.mCurrentScreenBrightness = f;
          }
          return;
        }
      }
    }
    f = Float.NaN;
    if (Float.isNaN(f)) {}
    synchronized (this.mLock) {
    }
  }
}
