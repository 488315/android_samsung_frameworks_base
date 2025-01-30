package com.android.server.display.mode;

import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.EventLog;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.core.SystemHistory;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.android.hardware.display.RefreshRateConfig;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class RefreshRateController {
  public static Context mContext;
  public static SystemHistory mDisplayModeDirectorHistory;
  public static DisplayModeDirector mDm;
  public static Handler mHandler;
  public static IBinder mPrimaryDisplayToken;
  public static RefreshRateTokenController mRefreshRateTokenController;
  public static VotesStorage mVotesStorage;
  public RefreshRateConfig mConfig;
  public IBinder mDisplayToken;
  public int mDisplayType;
  public boolean mIsSubScreen;
  public AtomicBoolean mPassive;
  public AtomicInteger mRefreshRateMode;
  public String mRefreshRateModeSetting;
  public AtomicInteger mReportedRefreshRateMode;
  public AtomicBoolean mUpdateRefreshRateMode;
  public static SparseArray mIsDisplayPowerModeOnByDisplayType = new SparseArray();
  public static AtomicInteger mBrightness = new AtomicInteger(-1);
  public static AtomicReference mAmbientLux = new AtomicReference(Float.valueOf(-1.0f));
  public static AtomicBoolean mIsWirelessCharging = new AtomicBoolean(false);

  public String getControllerType() {
    return "RefreshRateController";
  }

  public int getSwitchingType() {
    return 1;
  }

  public boolean isPassiveModeForTypeLocked() {
    return false;
  }

  public String logBrightnessStateLocked() {
    return "";
  }

  public void onDisplayStateOffLocked() {}

  public void updateDefaultDisplayOrOffDisplayLocked() {}

  public void updateLfdValueLocked(boolean z) {}

  public static void init(
      Context context,
      DisplayModeDirector displayModeDirector,
      Handler handler,
      VotesStorage votesStorage,
      RefreshRateTokenController refreshRateTokenController) {
    mDm = displayModeDirector;
    mContext = context;
    mHandler = handler;
    mVotesStorage = votesStorage;
    mRefreshRateTokenController = refreshRateTokenController;
    if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
      mDisplayModeDirectorHistory = new SystemHistory(25, "RefreshRateModeManager");
    }
  }

  public static int getBrightnessZone(int i) {
    if (RefreshRateConfig.getInstance().getBrightnessThreshold().mLowBrightnessThreshold > i) {
      return 1;
    }
    return RefreshRateConfig.getInstance().getBrightnessThreshold().mHighBrightnessThreshold < i
        ? 3
        : 2;
  }

  public static int getAmbientLuxZone(float f) {
    if (f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      return 0;
    }
    if (RefreshRateConfig.getInstance().getBrightnessThreshold().mLowAmbientLuxThreshold > f) {
      return 1;
    }
    return ((float)
                RefreshRateConfig.getInstance().getBrightnessThreshold().mHighAmbientLuxThreshold)
            < f
        ? 3
        : 2;
  }

  public boolean compareAndSetBrightnessZone(int i) {
    return getBrightnessZone(mBrightness.getAndSet(i)) == getBrightnessZone(i);
  }

  public boolean compareAndSetAmbientLuxZone(float f) {
    return getAmbientLuxZone(((Float) mAmbientLux.getAndSet(Float.valueOf(f))).floatValue())
        == getAmbientLuxZone(f);
  }

  public boolean compareAndSetIsWirelessCharging(boolean z) {
    return mIsWirelessCharging.getAndSet(z) == z;
  }

  public void setPrimaryDisplayToken(IBinder iBinder) {
    mPrimaryDisplayToken = iBinder;
  }

  public RefreshRateController(IBinder iBinder) {
    this.mRefreshRateModeSetting = "refresh_rate_mode";
    this.mDisplayType = 1;
    this.mIsSubScreen = false;
    this.mRefreshRateMode = new AtomicInteger(-1);
    this.mReportedRefreshRateMode = new AtomicInteger(-1);
    this.mUpdateRefreshRateMode = new AtomicBoolean(false);
    this.mPassive = new AtomicBoolean(false);
    this.mDisplayToken = iBinder;
    boolean z = CoreRune.FW_VRR_FOR_SUB_DISPLAY;
    if (z) {
      IBinder iBinder2 = mPrimaryDisplayToken;
      boolean z2 = (iBinder2 == null || iBinder2 == iBinder) ? false : true;
      this.mIsSubScreen = z2;
      if (z2) {
        this.mRefreshRateModeSetting = "refresh_rate_mode_cover";
        this.mDisplayType = 2;
      }
    }
    this.mConfig = RefreshRateConfig.getInstance(z && this.mIsSubScreen);
  }

  public static RefreshRateController makeController(IBinder iBinder) {
    if (iBinder == null) {
      return new NullController(null);
    }
    if (RefreshRateConfig.getInstance().isSwitchable()) {
      return new SwitchableController(iBinder);
    }
    if (RefreshRateConfig.getInstance().isSeamless()) {
      return new SeamlessController(iBinder);
    }
    if (RefreshRateConfig.getInstance().isSeamlessPlus()) {
      return new SeamlessPlusController(iBinder);
    }
    return new RefreshRateController(iBinder);
  }

  public Boolean getPowerModeOnByDisplayType(int i) {
    return (Boolean) mIsDisplayPowerModeOnByDisplayType.get(i);
  }

  public void setPowerModeOnByDisplayType(int i, boolean z) {
    mIsDisplayPowerModeOnByDisplayType.put(i, Boolean.valueOf(z));
  }

  public int getRefreshRateModeLocked() {
    return this.mRefreshRateMode.get();
  }

  public boolean isRefreshRateModeChangingLocked() {
    return this.mRefreshRateMode.get() != this.mReportedRefreshRateMode.get();
  }

  public boolean consumeUpdateRefreshRateMode() {
    return this.mUpdateRefreshRateMode.getAndSet(false);
  }

  public Vote getNormalSpeedVote() {
    return Vote.forPhysicalRefreshRates(
        this.mConfig.getNormalSpeedRefreshRates().min(),
        this.mConfig.getNormalSpeedRefreshRates().max());
  }

  public Vote getHighSpeedVote() {
    return Vote.forPhysicalRefreshRates(
        this.mConfig.getHighSpeedRefreshRates().min(),
        this.mConfig.getHighSpeedRefreshRates().max());
  }

  public final Vote getVoteForMode(int i) {
    if (i == 0) {
      return getNormalSpeedVote();
    }
    if (i == 1 || i == 2) {
      return getHighSpeedVote();
    }
    return null;
  }

  public void updateRefreshRateModeLocked(boolean z) {
    int intForUser =
        Settings.Secure.getIntForUser(
            mContext.getContentResolver(), this.mRefreshRateModeSetting, 0, -2);
    if (intForUser != getRefreshRateModeLocked() || z) {
      if (CoreRune.FW_VRR_FOR_SUB_DISPLAY && z) {
        mDm.setModeSwitchingType(getSwitchingType());
      }
      EventLog.writeEvent(
          1290001, String.format("%s", this.mDisplayToken), Integer.valueOf(intForUser));
      this.mRefreshRateMode.set(intForUser);
      this.mUpdateRefreshRateMode.set(true);
      mVotesStorage.updateVote(-1, 10, getVoteForMode(intForUser));
      if (CoreRune.FW_VRR_SEAMLESS) {
        onBrightnessChangedLocked(false, true);
      }
      notifyRefreshRateModeLocked();
    }
  }

  public boolean logCurrentStateLocked(
      DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
    StringBuilder sb = new StringBuilder();
    if (desiredDisplayModeSpecs != null) {
      sb.append("Schedule to change allowedModes=");
      sb.append(desiredDisplayModeSpecs);
    } else {
      sb.append("Schedule to change HFRmode=");
      sb.append(Settings.Secure.refreshRateModeToString(this.mReportedRefreshRateMode.get()));
    }
    sb.append(", displayToken=");
    sb.append(this.mDisplayToken);
    StringWriter stringWriter = new StringWriter();
    mVotesStorage.dump(new PrintWriter(stringWriter));
    sb.append(stringWriter);
    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append(" mModeSwitchingType: ");
    sb.append(DisplayModeDirector.switchingTypeToString(mDm.getModeSwitchingType()));
    sb.append(" mAlwaysRespectAppRequest: ");
    sb.append(mDm.shouldAlwaysRespectAppRequestedMode());
    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append(logModeStateLocked());
    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append(logBrightnessStateLocked());
    mDisplayModeDirectorHistory.add(sb.toString());
    return true;
  }

  public final String logModeStateLocked() {
    return " Current Mode mReportedRefreshRateMode(toSurfaceFlinger)="
        + Settings.Secure.refreshRateModeToString(this.mReportedRefreshRateMode.get())
        + ", mRefreshRateMode(fromSettings)="
        + Settings.Secure.refreshRateModeToString(this.mRefreshRateMode.get());
  }

  public void dumpHistory(PrintWriter printWriter) {
    printWriter.println("DisplayModeDirector History");
    mDisplayModeDirectorHistory.dump(printWriter);
    printWriter.println("RefreshRateToken History");
    mRefreshRateTokenController.dump(printWriter);
  }

  public void dumpLocked(PrintWriter printWriter, String str) {
    if (CoreRune.FW_VRR_FOR_SUB_DISPLAY) {
      StringBuilder sb = new StringBuilder();
      sb.append(str);
      sb.append("PrimaryDisplay: ");
      sb.append(!this.mIsSubScreen);
      printWriter.println(sb.toString());
    }
    printWriter.println(str + "ControllerType: " + getControllerType());
    printWriter.println(
        str
            + "RefreshRateMode: "
            + Settings.Secure.refreshRateModeToString(getRefreshRateModeLocked()));
    if (this.mDisplayToken != null) {
      RefreshRateConfig.dump(printWriter, str, this.mIsSubScreen);
    }
  }

  public final boolean notifyRefreshRateModeLocked() {
    final int refreshRateModeLocked;
    if (CoreRune.FW_VRR_SEAMLESS && this.mPassive.get()) {
      refreshRateModeLocked = 3;
    } else {
      refreshRateModeLocked = this.mConfig.unsupportedNS() ? 1 : getRefreshRateModeLocked();
    }
    if (this.mReportedRefreshRateMode.getAndSet(refreshRateModeLocked) == refreshRateModeLocked) {
      return false;
    }
    final IBinder iBinder = this.mDisplayToken;
    mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.display.mode.RefreshRateController$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            SurfaceControl.notifyHFRmode(iBinder, refreshRateModeLocked);
          }
        });
    if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
      logCurrentStateLocked(null);
    }
    return true;
  }

  public final boolean isPassiveModeLocked() {
    if (mDm.getModeSwitchingType() == 0 || mDm.shouldAlwaysRespectAppRequestedMode()) {
      return false;
    }
    if (getRefreshRateModeLocked() == 1 || this.mConfig.unsupportedNS()) {
      return isPassiveModeForTypeLocked();
    }
    return false;
  }

  public void onDisplayStateChangeLocked(boolean z) {
    if (z) {
      onBrightnessChangedLocked(false);
    } else {
      onDisplayStateOffLocked();
    }
  }

  public void updatePassiveLocked(boolean z) {
    boolean isPassiveModeLocked = isPassiveModeLocked();
    if (this.mPassive.getAndSet(isPassiveModeLocked) != isPassiveModeLocked || z) {
      Slog.d(
          "RefreshRateModeManager",
          "setPassiveMode="
              + isPassiveModeLocked
              + ", brightness="
              + mBrightness
              + ", lux="
              + mAmbientLux
              + ", PassiveModeToken="
              + checkPassiveModeToken());
      notifyRefreshRateModeLocked();
      updateRefreshRateMaxLimitTokenLocked();
    }
  }

  public void onBrightnessChangedLocked(boolean z) {
    onBrightnessChangedLocked(z, false);
  }

  public void onBrightnessChangedLocked(boolean z, boolean z2) {
    if (CoreRune.FW_VRR_FOR_SUB_DISPLAY && RefreshRateConfig.isSubScreen() != this.mIsSubScreen) {
      Slog.d(
          "RefreshRateModeManager",
          "default display was changed!, don't need to check passive mode for "
              + this.mDisplayToken);
      return;
    }
    if (mBrightness.get() < 0) {
      return;
    }
    Boolean powerModeOnByDisplayType = getPowerModeOnByDisplayType(this.mDisplayType);
    if (powerModeOnByDisplayType != null && !powerModeOnByDisplayType.booleanValue()) {
      Slog.d(
          "RefreshRateModeManager",
          "onBrightnessChangedLocked returned by powerMode, caller=" + Debug.getCallers(3));
      return;
    }
    Slog.d(
        "RefreshRateModeManager",
        "onBrightnessChangedLocked, brightness=" + mBrightness + ", lux=" + mAmbientLux);
    updateLfdValueLocked(z2);
    updatePassiveLocked(z);
  }

  public boolean isLowBrightnessZone() {
    return getBrightnessZone(mBrightness.get()) == 1;
  }

  public boolean isLowAmbientLuxZone() {
    return getAmbientLuxZone(((Float) mAmbientLux.get()).floatValue()) == 1;
  }

  public boolean isUnknownAmbientLuxZone() {
    return getAmbientLuxZone(((Float) mAmbientLux.get()).floatValue()) == 0;
  }

  public boolean isHighBrightnessAmbientLuxZone() {
    return this.mConfig.getBrightnessThreshold().mHighBrightnessThreshold != -1
        && this.mConfig.getBrightnessThreshold().mHighAmbientLuxThreshold != -1
        && getBrightnessZone(mBrightness.get()) == 3
        && getAmbientLuxZone(((Float) mAmbientLux.get()).floatValue()) == 3;
  }

  public boolean checkLowRefershRateToken() {
    return mRefreshRateTokenController.getRefreshRateTokens().stream()
        .anyMatch(
            new Predicate() { // from class:
                              // com.android.server.display.mode.RefreshRateController$$ExternalSyntheticLambda3
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$checkLowRefershRateToken$1;
                lambda$checkLowRefershRateToken$1 =
                    RefreshRateController.lambda$checkLowRefershRateToken$1((RefreshRateToken) obj);
                return lambda$checkLowRefershRateToken$1;
              }
            });
  }

  public static /* synthetic */ boolean lambda$checkLowRefershRateToken$1(
      RefreshRateToken refreshRateToken) {
    return refreshRateToken instanceof LowRefreshRateToken;
  }

  public boolean checkPassiveModeToken() {
    return mRefreshRateTokenController.getRefreshRateTokens().stream()
        .anyMatch(
            new Predicate() { // from class:
                              // com.android.server.display.mode.RefreshRateController$$ExternalSyntheticLambda4
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$checkPassiveModeToken$2;
                lambda$checkPassiveModeToken$2 =
                    RefreshRateController.lambda$checkPassiveModeToken$2((RefreshRateToken) obj);
                return lambda$checkPassiveModeToken$2;
              }
            });
  }

  public static /* synthetic */ boolean lambda$checkPassiveModeToken$2(
      RefreshRateToken refreshRateToken) {
    return refreshRateToken instanceof PassiveModeToken;
  }

  public void updateRefreshRateMaxLimitTokenLocked() {
    RefreshRateToken refreshRateToken =
        (RefreshRateToken)
            mRefreshRateTokenController.getRefreshRateTokens().stream()
                .filter(
                    new Predicate() { // from class:
                                      // com.android.server.display.mode.RefreshRateController$$ExternalSyntheticLambda0
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        boolean lambda$updateRefreshRateMaxLimitTokenLocked$3;
                        lambda$updateRefreshRateMaxLimitTokenLocked$3 =
                            RefreshRateController.lambda$updateRefreshRateMaxLimitTokenLocked$3(
                                (RefreshRateToken) obj);
                        return lambda$updateRefreshRateMaxLimitTokenLocked$3;
                      }
                    })
                .findFirst()
                .orElse(null);
    if (refreshRateToken != null) {
      refreshRateToken.accept();
    }
  }

  public static /* synthetic */ boolean lambda$updateRefreshRateMaxLimitTokenLocked$3(
      RefreshRateToken refreshRateToken) {
    return refreshRateToken instanceof RefreshRateMaxLimitToken;
  }

  public IRefreshRateToken createPassiveModeToken(IBinder iBinder, String str) {
    return mRefreshRateTokenController.createRefreshRateToken(
        new PassiveModeToken(),
        new RefreshRateToken.RefreshRateTokenInfo.Builder(iBinder, "PassiveModeToken:" + str)
            .build());
  }

  public class PassiveModeToken extends RefreshRateToken {
    public PassiveModeToken() {}

    @Override // com.android.server.display.mode.RefreshRateToken
    public void accept() {
      if (CoreRune.FW_VRR_FOR_SUB_DISPLAY
          && RefreshRateConfig.isSubScreen() != RefreshRateController.this.mIsSubScreen) {
        Slog.d(
            "RefreshRateModeManager",
            "default display was changed!, don't need to check passive mode for "
                + RefreshRateController.this.mDisplayToken);
        return;
      }
      RefreshRateController.this.updateLfdValueLocked(false);
      RefreshRateController.this.updatePassiveLocked(false);
    }
  }

  public IRefreshRateToken createLowRefreshRateToken(IBinder iBinder, String str) {
    return mRefreshRateTokenController.createRefreshRateToken(
        new LowRefreshRateToken(),
        new RefreshRateToken.RefreshRateTokenInfo.Builder(iBinder, "LowRefreshRateToken:" + str)
            .build());
  }

  public class LowRefreshRateToken extends RefreshRateToken {
    public LowRefreshRateToken() {}

    @Override // com.android.server.display.mode.RefreshRateToken
    public void accept() {
      RefreshRateController.mVotesStorage.updateVote(
          -1,
          14,
          RefreshRateController.mRefreshRateTokenController.getRefreshRateTokens().stream()
                  .anyMatch(
                      new Predicate() { // from class:
                                        // com.android.server.display.mode.RefreshRateController$LowRefreshRateToken$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                          boolean lambda$accept$0;
                          lambda$accept$0 =
                              RefreshRateController.LowRefreshRateToken.lambda$accept$0(
                                  (RefreshRateToken) obj);
                          return lambda$accept$0;
                        }
                      })
              ? Vote.forPhysicalRefreshRates(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 60.0f)
              : null);
    }

    public static /* synthetic */ boolean lambda$accept$0(RefreshRateToken refreshRateToken) {
      return refreshRateToken instanceof LowRefreshRateToken;
    }
  }

  public IRefreshRateToken createRefreshRateMaxLimitToken(IBinder iBinder, int i, String str) {
    return mRefreshRateTokenController.createRefreshRateToken(
        new RefreshRateMaxLimitToken(),
        new RefreshRateToken.RefreshRateTokenInfo.Builder(
                iBinder, "RefreshRateMaxLimitToken:" + str)
            .setRefreshRate(i)
            .build());
  }

  public class RefreshRateMaxLimitToken extends RefreshRateToken {
    public RefreshRateMaxLimitToken() {}

    @Override // com.android.server.display.mode.RefreshRateToken
    public void accept() {
      Vote vote;
      if (!CoreRune.FW_VRR_SEAMLESS || RefreshRateController.mDm.getModeSwitchingType() == 0) {
        return;
      }
      int intValue =
          ((Integer)
                  RefreshRateController.mRefreshRateTokenController.getRefreshRateTokens().stream()
                      .filter(
                          new Predicate() { // from class:
                                            // com.android.server.display.mode.RefreshRateController$RefreshRateMaxLimitToken$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                              boolean lambda$accept$0;
                              lambda$accept$0 =
                                  RefreshRateController.RefreshRateMaxLimitToken.lambda$accept$0(
                                      (RefreshRateToken) obj);
                              return lambda$accept$0;
                            }
                          })
                      .map(
                          new Function() { // from class:
                                           // com.android.server.display.mode.RefreshRateController$RefreshRateMaxLimitToken$$ExternalSyntheticLambda1
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                              Integer lambda$accept$1;
                              lambda$accept$1 =
                                  RefreshRateController.RefreshRateMaxLimitToken.lambda$accept$1(
                                      (RefreshRateToken) obj);
                              return lambda$accept$1;
                            }
                          })
                      .min(new C1343x36ef0a5())
                      .orElse(Integer.MAX_VALUE))
              .intValue();
      if (intValue >= Integer.MAX_VALUE) {
        vote = null;
      } else if (CoreRune.FW_VRR_SEAMLESS
          && RefreshRateController.mDm.getRefreshRateModeManager().getController().mPassive.get()) {
        vote =
            Vote.forPhysicalRefreshRates(
                DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
                RefreshRateController.this
                    .mConfig
                    .getHighSpeedRefreshRates()
                    .getSupportedRefreshRateForPassive(intValue));
      } else {
        vote = Vote.forPhysicalRefreshRates(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, intValue);
      }
      RefreshRateController.mVotesStorage.updateVote(-1, 1, vote);
    }

    public static /* synthetic */ boolean lambda$accept$0(RefreshRateToken refreshRateToken) {
      return refreshRateToken instanceof RefreshRateMaxLimitToken;
    }

    public static /* synthetic */ Integer lambda$accept$1(RefreshRateToken refreshRateToken) {
      return Integer.valueOf(refreshRateToken.mInfo.mRefreshRate);
    }
  }

  public IRefreshRateToken createRefreshRateMinLimitToken(IBinder iBinder, int i, String str) {
    return mRefreshRateTokenController.createRefreshRateToken(
        new RefreshRateMinLimitToken(),
        new RefreshRateToken.RefreshRateTokenInfo.Builder(
                iBinder, "RefreshRateMinLimitToken:" + str)
            .setRefreshRate(i)
            .build());
  }

  public class RefreshRateMinLimitToken extends RefreshRateToken {
    public RefreshRateMinLimitToken() {}

    @Override // com.android.server.display.mode.RefreshRateToken
    public void accept() {
      if (!CoreRune.FW_VRR_SEAMLESS || RefreshRateController.mDm.getModeSwitchingType() == 0) {
        return;
      }
      int intValue =
          ((Integer)
                  RefreshRateController.mRefreshRateTokenController.getRefreshRateTokens().stream()
                      .filter(
                          new Predicate() { // from class:
                                            // com.android.server.display.mode.RefreshRateController$RefreshRateMinLimitToken$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                              boolean lambda$accept$0;
                              lambda$accept$0 =
                                  RefreshRateController.RefreshRateMinLimitToken.lambda$accept$0(
                                      (RefreshRateToken) obj);
                              return lambda$accept$0;
                            }
                          })
                      .map(
                          new Function() { // from class:
                                           // com.android.server.display.mode.RefreshRateController$RefreshRateMinLimitToken$$ExternalSyntheticLambda1
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                              Integer lambda$accept$1;
                              lambda$accept$1 =
                                  RefreshRateController.RefreshRateMinLimitToken.lambda$accept$1(
                                      (RefreshRateToken) obj);
                              return lambda$accept$1;
                            }
                          })
                      .filter(
                          new Predicate() { // from class:
                                            // com.android.server.display.mode.RefreshRateController$RefreshRateMinLimitToken$$ExternalSyntheticLambda2
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                              boolean lambda$accept$2;
                              lambda$accept$2 =
                                  RefreshRateController.RefreshRateMinLimitToken.lambda$accept$2(
                                      (Integer) obj);
                              return lambda$accept$2;
                            }
                          })
                      .max(new C1343x36ef0a5())
                      .orElse(Integer.MIN_VALUE))
              .intValue();
      RefreshRateController.mVotesStorage.updateVote(
          -1,
          2,
          intValue > Integer.MIN_VALUE
              ? Vote.forPhysicalRefreshRates(intValue, Float.POSITIVE_INFINITY)
              : null);
    }

    public static /* synthetic */ boolean lambda$accept$0(RefreshRateToken refreshRateToken) {
      return refreshRateToken instanceof RefreshRateMinLimitToken;
    }

    public static /* synthetic */ Integer lambda$accept$1(RefreshRateToken refreshRateToken) {
      return Integer.valueOf(refreshRateToken.mInfo.mRefreshRate);
    }

    public static /* synthetic */ boolean lambda$accept$2(Integer num) {
      return num.intValue() >= 60;
    }
  }

  public void updateResolutionLocked(int i, DisplayInfo displayInfo) {
    final int min = Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight);
    Display.Mode mode =
        (Display.Mode)
            Arrays.stream(displayInfo.supportedModes)
                .filter(
                    new Predicate() { // from class:
                                      // com.android.server.display.mode.RefreshRateController$$ExternalSyntheticLambda1
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        boolean lambda$updateResolutionLocked$4;
                        lambda$updateResolutionLocked$4 =
                            RefreshRateController.lambda$updateResolutionLocked$4(
                                min, (Display.Mode) obj);
                        return lambda$updateResolutionLocked$4;
                      }
                    })
                .findFirst()
                .orElse(null);
    if (mode == null) {
      return;
    }
    int physicalWidth = mode.getPhysicalWidth();
    int physicalHeight = mode.getPhysicalHeight();
    Vote vote = mDm.getVote(i, 11);
    if (vote == null || vote.width != physicalWidth) {
      mVotesStorage.updateVote(i, 11, Vote.forSize(physicalWidth, physicalHeight));
    }
  }

  public static /* synthetic */ boolean lambda$updateResolutionLocked$4(int i, Display.Mode mode) {
    return mode.getPhysicalWidth() == i;
  }

  public class NullController extends RefreshRateController {
    @Override // com.android.server.display.mode.RefreshRateController
    public String getControllerType() {
      return "NullController";
    }

    public NullController(IBinder iBinder) {
      super(iBinder);
    }

    @Override // com.android.server.display.mode.RefreshRateController
    public void updatePassiveLocked(boolean z) {
      Slog.d(
          "RefreshRateModeManager", "NullController-updatePassiveLocked controller is not ready!");
    }
  }

  public class SwitchableController extends RefreshRateController {
    @Override // com.android.server.display.mode.RefreshRateController
    public String getControllerType() {
      return "SwitchableController";
    }

    @Override // com.android.server.display.mode.RefreshRateController
    public int getSwitchingType() {
      return 0;
    }

    @Override // com.android.server.display.mode.RefreshRateController
    public void onBrightnessChangedLocked(boolean z) {}

    public SwitchableController(IBinder iBinder) {
      super(iBinder);
    }
  }

  public class SeamlessController extends RefreshRateController {
    @Override // com.android.server.display.mode.RefreshRateController
    public String getControllerType() {
      return "SeamlessController";
    }

    public SeamlessController(IBinder iBinder) {
      super(iBinder);
    }

    @Override // com.android.server.display.mode.RefreshRateController
    public boolean isPassiveModeForTypeLocked() {
      return checkPassiveModeToken()
          || isLowBrightnessZone()
          || isLowAmbientLuxZone()
          || isHighBrightnessAmbientLuxZone();
    }

    @Override // com.android.server.display.mode.RefreshRateController
    public Vote getNormalSpeedVote() {
      if (this.mConfig.unsupportedNS()) {
        return Vote.forPhysicalRefreshRates(
            this.mConfig.getHighSpeedRefreshRates().min(),
            this.mConfig.getNormalSpeedRefreshRates().max());
      }
      return super.getNormalSpeedVote();
    }

    @Override // com.android.server.display.mode.RefreshRateController
    public String logBrightnessStateLocked() {
      return " BrightnessState mPassive="
          + this.mPassive
          + ", PassiveModeToken="
          + checkPassiveModeToken()
          + ", mBrightness="
          + RefreshRateController.mBrightness
          + ", mAmbientLux="
          + RefreshRateController.mAmbientLux;
    }
  }

  public class SeamlessPlusController extends RefreshRateController {
    public static AtomicInteger mReportedLfd = new AtomicInteger(-1);

    @Override // com.android.server.display.mode.RefreshRateController
    public String getControllerType() {
      return "SeamlessPlusController";
    }

    public SeamlessPlusController(IBinder iBinder) {
      super(iBinder);
    }

    @Override // com.android.server.display.mode.RefreshRateController
    public Vote getNormalSpeedVote() {
      if (this.mConfig.unsupportedNS()) {
        return Vote.forPhysicalRefreshRates(
            this.mConfig.getHighSpeedRefreshRates().min(),
            this.mConfig.getNormalSpeedRefreshRates().max());
      }
      return super.getNormalSpeedVote();
    }

    public final void sysfsWrite(String str, String str2) {
      File file = new File(str);
      if (file.exists()) {
        try {
          FileOutputStream fileOutputStream = new FileOutputStream(file);
          try {
            fileOutputStream.write(str2.getBytes());
            fileOutputStream.close();
          } finally {
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    public final void setLfd(final String str, final int i) {
      Slog.d(
          "RefreshRateModeManager",
          "set "
              + str
              + "="
              + i
              + ", brightness="
              + RefreshRateController.mBrightness
              + ", lux="
              + RefreshRateController.mAmbientLux
              + ", mIsWirelessCharging="
              + RefreshRateController.mIsWirelessCharging);
      new Thread(
              new Runnable() { // from class:
                               // com.android.server.display.mode.RefreshRateController$SeamlessPlusController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  RefreshRateController.SeamlessPlusController.this.lambda$setLfd$0(str, i);
                }
              })
          .start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLfd$0(String str, int i) {
      sysfsWrite("/sys/class/lcd/panel/vrr_lfd", "client=disp scope=normal,lpm " + str + "=" + i);
    }

    public final int getLfdFix() {
      if (checkPassiveModeToken() || RefreshRateController.mIsWirelessCharging.get()) {
        return 3;
      }
      return (isLowBrightnessZone() && isLowAmbientLuxZone()) ? 3 : 0;
    }

    public final void updateLfdFixLocked(boolean z) {
      int lfdFix = z ? 0 : getLfdFix();
      if (mReportedLfd.getAndSet(lfdFix) == lfdFix) {
        return;
      }
      setLfd("fix", lfdFix);
    }

    public final Vote getVoteAsLfdScalabilityMaxLocked() {
      if (getRefreshRateModeLocked() == 0) {
        return Vote.forPhysicalRefreshRates(
            this.mConfig.getNormalSpeedRefreshRates().max(),
            this.mConfig.getNormalSpeedRefreshRates().max());
      }
      return Vote.forPhysicalRefreshRates(
          this.mConfig.getHighSpeedRefreshRates().max(),
          this.mConfig.getHighSpeedRefreshRates().max());
    }

    public final void voteForScalabilityMaxLocked(int i) {
      Vote vote;
      Vote vote2;
      if (i == 1) {
        vote = getVoteAsLfdScalabilityMaxLocked();
        vote2 = Vote.forDisableRefreshRateSwitching();
      } else {
        vote = null;
        vote2 = null;
      }
      RefreshRateController.mVotesStorage.updateVote(-1, 3, vote);
      RefreshRateController.mVotesStorage.updateVote(-1, 15, vote2);
    }

    public final int getScalability() {
      if (RefreshRateController.mIsWirelessCharging.get()) {
        return 1;
      }
      if (isLowBrightnessZone() && (isLowAmbientLuxZone() || isUnknownAmbientLuxZone())) {
        return 1;
      }
      return isHighBrightnessAmbientLuxZone() ? 6 : 0;
    }

    public final void updateLfdScalabilityLocked(boolean z, boolean z2) {
      boolean z3 = false;
      int scalability = z ? 0 : getScalability();
      if (mReportedLfd.getAndSet(scalability) != scalability) {
        setLfd("scalability", scalability);
        z3 = true;
      }
      if (z3 || isRefreshRateModeChangingLocked() || z2) {
        voteForScalabilityMaxLocked(scalability);
      }
    }

    public boolean supportApsr() {
      return this.mConfig.getHighSpeedRefreshRates().min() == 10;
    }

    @Override // com.android.server.display.mode.RefreshRateController
    public void updateLfdValueLocked(boolean z) {
      if (supportApsr()) {
        updateLfdFixLocked(false);
      } else {
        updateLfdScalabilityLocked(false, z);
      }
    }

    @Override // com.android.server.display.mode.RefreshRateController
    public boolean isPassiveModeForTypeLocked() {
      if (supportApsr()) {
        return checkPassiveModeToken() || mReportedLfd.get() == 3;
      }
      return false;
    }

    @Override // com.android.server.display.mode.RefreshRateController
    public void onDisplayStateOffLocked() {
      if (isPassiveModeForTypeLocked()) {
        updateDefaultDisplayOrOffDisplayLocked();
      }
    }

    @Override // com.android.server.display.mode.RefreshRateController
    public void updateDefaultDisplayOrOffDisplayLocked() {
      if (supportApsr()) {
        updateLfdFixLocked(true);
        super.updatePassiveLocked(false);
      } else {
        updateLfdScalabilityLocked(true, false);
      }
    }

    @Override // com.android.server.display.mode.RefreshRateController
    public String logBrightnessStateLocked() {
      return " BrightnessState mPassive="
          + this.mPassive
          + ", PassiveModeToken="
          + checkPassiveModeToken()
          + ", mLfd="
          + mReportedLfd
          + ", mBrightness="
          + RefreshRateController.mBrightness
          + ", mAmbientLux="
          + RefreshRateController.mAmbientLux
          + ", mIsWirelessCharing="
          + RefreshRateController.mIsWirelessCharging;
    }
  }
}
