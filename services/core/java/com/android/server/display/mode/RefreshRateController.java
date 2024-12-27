package com.android.server.display.mode;

import android.content.Context;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.EventLog;
import android.util.Slog;
import android.util.SparseArray;
import android.view.SurfaceControl;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.core.SystemHistory;
import com.samsung.android.hardware.display.RefreshRateConfig;
import com.samsung.android.rune.CoreRune;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class RefreshRateController {
    public static Context mContext;
    public static SystemHistory mDisplayModeDirectorHistory;
    public static DisplayModeDirector mDm;
    public static Handler mHandler;
    public static RefreshRateTokenController mRefreshRateTokenController;
    public static VotesStorage mVotesStorage;
    public static IBinder sPrimaryDisplayToken;
    public final RefreshRateConfig mConfig;
    public final IBinder mDisplayToken;
    public final int mDisplayType;
    public final boolean mIsSubScreen;
    public final String mRefreshRateModeSetting;
    public static final SparseArray mIsDisplayPowerModeOnByDisplayType = new SparseArray();
    public static final AtomicInteger mBrightness = new AtomicInteger(-1);
    public static final AtomicReference mAmbientLux = new AtomicReference(Float.valueOf(-1.0f));
    public static final AtomicBoolean mIsWirelessCharging = new AtomicBoolean(false);
    public final AtomicInteger mRefreshRateMode = new AtomicInteger(-1);
    public final AtomicInteger mReportedRefreshRateMode = new AtomicInteger(-1);
    public final AtomicBoolean mUpdateRefreshRateMode = new AtomicBoolean(false);
    public final AtomicBoolean mPassive = new AtomicBoolean(false);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LowRefreshRateToken extends RefreshRateToken {
        @Override // com.android.server.display.mode.RefreshRateToken
        public final void accept() {
            RefreshRateController.mVotesStorage.updateVote(
                    -1,
                    19,
                    RefreshRateController.mRefreshRateTokenController.mRefreshRateTokens.stream()
                                    .anyMatch(
                                            new RefreshRateController$$ExternalSyntheticLambda1(3))
                            ? Vote.forPolicyRate(
                                    FullScreenMagnificationGestureHandler.MAX_SCALE, 60.0f)
                            : null);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NullController extends RefreshRateController {
        public final /* synthetic */ int $r8$classId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ NullController(int i, IBinder iBinder) {
            super(iBinder);
            this.$r8$classId = i;
        }

        private final void
                onBrightnessChangedLocked$com$android$server$display$mode$RefreshRateController$SwitchableController() {}

        @Override // com.android.server.display.mode.RefreshRateController
        public final String getControllerType() {
            switch (this.$r8$classId) {
                case 0:
                    return "NullController";
                case 1:
                    return "SeamlessController";
                default:
                    return "SwitchableController";
            }
        }

        @Override // com.android.server.display.mode.RefreshRateController
        public Vote getNormalSpeedVote() {
            switch (this.$r8$classId) {
                case 2:
                    return Vote.forPolicyRate(
                            this.mConfig.getNormalSpeedRefreshRates().min(),
                            this.mConfig.getNormalSpeedRefreshRates().max());
                default:
                    return super.getNormalSpeedVote();
            }
        }

        @Override // com.android.server.display.mode.RefreshRateController
        public int getSwitchingType() {
            switch (this.$r8$classId) {
                case 2:
                    return 0;
                default:
                    return super.getSwitchingType();
            }
        }

        @Override // com.android.server.display.mode.RefreshRateController
        public boolean isPassiveModeForTypeLocked() {
            switch (this.$r8$classId) {
                case 1:
                    return RefreshRateController.getBrightnessZone(
                                            RefreshRateController.mBrightness.get())
                                    == 1
                            || RefreshRateController.isLowAmbientLuxZone()
                            || isHighBrightnessAmbientLuxZone()
                            || RefreshRateController.hasPassiveModeToken();
                default:
                    return super.isPassiveModeForTypeLocked();
            }
        }

        @Override // com.android.server.display.mode.RefreshRateController
        public String logBrightnessStateLocked() {
            switch (this.$r8$classId) {
                case 1:
                    return " BrightnessState mPassive="
                            + this.mPassive
                            + ", PassiveModeToken="
                            + RefreshRateController.hasPassiveModeToken()
                            + ", mBrightness="
                            + RefreshRateController.mBrightness
                            + ", mAmbientLux="
                            + RefreshRateController.mAmbientLux;
                default:
                    return super.logBrightnessStateLocked();
            }
        }

        @Override // com.android.server.display.mode.RefreshRateController
        public void onBrightnessChangedLocked() {
            switch (this.$r8$classId) {
                case 2:
                    break;
                default:
                    super.onBrightnessChangedLocked();
                    break;
            }
        }

        @Override // com.android.server.display.mode.RefreshRateController
        public void updatePassiveLocked() {
            switch (this.$r8$classId) {
                case 0:
                    Slog.d(
                            "RefreshRateModeManager",
                            "NullController-updatePassiveLocked controller is not ready!");
                    break;
                default:
                    super.updatePassiveLocked();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PassiveModeToken extends RefreshRateToken {
        public PassiveModeToken() {}

        @Override // com.android.server.display.mode.RefreshRateToken
        public final void accept() {
            if (!CoreRune.FW_VRR_FOR_SUB_DISPLAY
                    || RefreshRateConfig.isSubScreen() == RefreshRateController.this.mIsSubScreen) {
                RefreshRateController.this.updateLfdValueLocked(false);
                RefreshRateController.this.updatePassiveLocked();
            } else {
                Slog.d(
                        "RefreshRateModeManager",
                        "default display was changed!, don't need to check passive mode for "
                                + RefreshRateController.this.mDisplayToken);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RefreshRateMaxLimitToken extends RefreshRateToken {
        public RefreshRateMaxLimitToken() {}

        @Override // com.android.server.display.mode.RefreshRateToken
        public final void accept() {
            Vote vote;
            boolean z = CoreRune.FW_VRR_SEAMLESS;
            if (!z || RefreshRateController.mDm.getModeSwitchingType() == 0) {
                return;
            }
            int intValue =
                    ((Integer)
                                    RefreshRateController.mRefreshRateTokenController
                                            .mRefreshRateTokens
                                            .stream()
                                            .filter(
                                                    new RefreshRateController$$ExternalSyntheticLambda1(
                                                            4))
                                            .map(
                                                    new RefreshRateController$RefreshRateMaxLimitToken$$ExternalSyntheticLambda1(
                                                            0))
                                            .min(
                                                    new RefreshRateController$RefreshRateMaxLimitToken$$ExternalSyntheticLambda2())
                                            .orElse(Integer.MAX_VALUE))
                            .intValue();
            if (intValue < Integer.MAX_VALUE) {
                float f = intValue;
                if (z
                        && RefreshRateController.mDm
                                .mRefreshRateModeManager
                                .getController()
                                .mPassive
                                .get()) {
                    f =
                            RefreshRateController.this
                                    .mConfig
                                    .getHighSpeedRefreshRates()
                                    .getSupportedRefreshRateForPassive(intValue);
                }
                vote = Vote.forPolicyRate(FullScreenMagnificationGestureHandler.MAX_SCALE, f);
            } else {
                vote = null;
            }
            RefreshRateController.mVotesStorage.updateVote(-1, 1, vote);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RefreshRateMinLimitToken extends RefreshRateToken {
        @Override // com.android.server.display.mode.RefreshRateToken
        public final void accept() {
            if (!CoreRune.FW_VRR_SEAMLESS
                    || RefreshRateController.mDm.getModeSwitchingType() == 0) {
                return;
            }
            int intValue =
                    ((Integer)
                                    RefreshRateController.mRefreshRateTokenController
                                            .mRefreshRateTokens
                                            .stream()
                                            .filter(
                                                    new RefreshRateController$$ExternalSyntheticLambda1(
                                                            5))
                                            .map(
                                                    new RefreshRateController$RefreshRateMaxLimitToken$$ExternalSyntheticLambda1(
                                                            1))
                                            .filter(
                                                    new RefreshRateController$$ExternalSyntheticLambda1(
                                                            6))
                                            .max(
                                                    new RefreshRateController$RefreshRateMaxLimitToken$$ExternalSyntheticLambda2())
                                            .orElse(Integer.MIN_VALUE))
                            .intValue();
            Vote forPolicyRate =
                    intValue == Integer.MIN_VALUE
                            ? null
                            : Vote.forPolicyRate(intValue, Float.POSITIVE_INFINITY);
            RefreshRateController.mVotesStorage.updateVote(-1, 2, forPolicyRate);
            if (CoreRune.FW_VRR_DISCRETE) {
                RefreshRateController.mVotesStorage.updateVote(-1, 7, forPolicyRate);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SeamlessPlusController extends RefreshRateController {
        public static final AtomicInteger mReportedLfd = new AtomicInteger(-1);

        @Override // com.android.server.display.mode.RefreshRateController
        public final String getControllerType() {
            return "SeamlessPlusController";
        }

        @Override // com.android.server.display.mode.RefreshRateController
        public final boolean isPassiveModeForTypeLocked() {
            if (supportApsr()) {
                return mReportedLfd.get() == 3 || RefreshRateController.hasPassiveModeToken();
            }
            return false;
        }

        @Override // com.android.server.display.mode.RefreshRateController
        public final String logBrightnessStateLocked() {
            return " BrightnessState mPassive="
                    + this.mPassive
                    + ", PassiveModeToken="
                    + RefreshRateController.hasPassiveModeToken()
                    + ", mLfd="
                    + mReportedLfd
                    + ", mBrightness="
                    + RefreshRateController.mBrightness
                    + ", mAmbientLux="
                    + RefreshRateController.mAmbientLux
                    + ", mIsWirelessCharing="
                    + RefreshRateController.mIsWirelessCharging;
        }

        @Override // com.android.server.display.mode.RefreshRateController
        public final void onDisplayStateOffLocked() {
            if (isPassiveModeForTypeLocked()) {
                updateDefaultDisplayOrOffDisplayLocked();
            }
        }

        public final void setLfd(final int i, final String str) {
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i, "set ", str, "=", ", brightness=");
            m.append(RefreshRateController.mBrightness);
            m.append(", lux=");
            m.append(RefreshRateController.mAmbientLux);
            m.append(", mIsWirelessCharging=");
            m.append(RefreshRateController.mIsWirelessCharging);
            Slog.d("RefreshRateModeManager", m.toString());
            new Thread(
                            new Runnable() { // from class:
                                             // com.android.server.display.mode.RefreshRateController$SeamlessPlusController$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    RefreshRateController.SeamlessPlusController
                                            seamlessPlusController =
                                                    RefreshRateController.SeamlessPlusController
                                                            .this;
                                    String str2 = str;
                                    int i2 = i;
                                    seamlessPlusController.getClass();
                                    String str3 = "client=disp scope=normal,lpm " + str2 + "=" + i2;
                                    File file = new File("/sys/class/lcd/panel/vrr_lfd");
                                    if (file.exists()) {
                                        try {
                                            FileOutputStream fileOutputStream =
                                                    new FileOutputStream(file);
                                            try {
                                                fileOutputStream.write(str3.getBytes());
                                                fileOutputStream.close();
                                            } finally {
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            })
                    .start();
        }

        public final boolean supportApsr() {
            return this.mConfig.getHighSpeedRefreshRates().min() == 10;
        }

        @Override // com.android.server.display.mode.RefreshRateController
        public final void updateDefaultDisplayOrOffDisplayLocked() {
            if (!supportApsr()) {
                updateLfdScalabilityLocked(true, false);
            } else {
                updateLfdFixLocked(true);
                updatePassiveLocked();
            }
        }

        public final void updateLfdFixLocked(boolean z) {
            int i = 0;
            if (!z
                    && (RefreshRateController.mIsWirelessCharging.get()
                            || ((RefreshRateController.getBrightnessZone(
                                                            RefreshRateController.mBrightness.get())
                                                    == 1
                                            && RefreshRateController.isLowAmbientLuxZone())
                                    || RefreshRateController.hasPassiveModeToken()))) {
                i = 3;
            }
            if (mReportedLfd.getAndSet(i) == i) {
                return;
            }
            setLfd(i, "fix");
        }

        public final void updateLfdScalabilityLocked(boolean z, boolean z2) {
            Vote vote;
            DisableRefreshRateSwitchingVote disableRefreshRateSwitchingVote;
            int i = 0;
            if (!z) {
                if (RefreshRateController.mIsWirelessCharging.get()
                        || (RefreshRateController.getBrightnessZone(
                                                RefreshRateController.mBrightness.get())
                                        == 1
                                && (RefreshRateController.isLowAmbientLuxZone()
                                        || RefreshRateController.getAmbientLuxZone(
                                                        ((Float)
                                                                        RefreshRateController
                                                                                .mAmbientLux
                                                                                .get())
                                                                .floatValue())
                                                == 0))) {
                    i = 1;
                } else if (isHighBrightnessAmbientLuxZone()) {
                    i = 6;
                }
            }
            if (mReportedLfd.getAndSet(i) != i) {
                setLfd(i, "scalability");
            } else if (this.mRefreshRateMode.get() == this.mReportedRefreshRateMode.get() && !z2) {
                return;
            }
            if (i == 1) {
                float max =
                        this.mRefreshRateMode.get() == 0
                                ? this.mConfig.getNormalSpeedRefreshRates().max()
                                : this.mConfig.getHighSpeedRefreshRates().max();
                vote = Vote.forPolicyRate(max, max);
                disableRefreshRateSwitchingVote = new DisableRefreshRateSwitchingVote(true);
            } else {
                vote = null;
                disableRefreshRateSwitchingVote = null;
            }
            RefreshRateController.mVotesStorage.updateVote(-1, 3, vote);
            RefreshRateController.mVotesStorage.updateVote(-1, 21, disableRefreshRateSwitchingVote);
        }

        @Override // com.android.server.display.mode.RefreshRateController
        public final void updateLfdValueLocked(boolean z) {
            if (supportApsr()) {
                updateLfdFixLocked(false);
            } else {
                updateLfdScalabilityLocked(false, z);
            }
        }
    }

    public RefreshRateController(IBinder iBinder) {
        this.mRefreshRateModeSetting = "refresh_rate_mode";
        this.mDisplayType = 1;
        this.mIsSubScreen = false;
        this.mDisplayToken = iBinder;
        boolean z = CoreRune.FW_VRR_FOR_SUB_DISPLAY;
        if (z) {
            IBinder iBinder2 = sPrimaryDisplayToken;
            boolean z2 = (iBinder2 == null || iBinder2 == iBinder) ? false : true;
            this.mIsSubScreen = z2;
            if (z2) {
                this.mRefreshRateModeSetting = "refresh_rate_mode_cover";
                this.mDisplayType = 2;
            }
        }
        this.mConfig = RefreshRateConfig.getInstance(z && this.mIsSubScreen);
    }

    public static RefreshRateMinLimitToken createRefreshRateMinLimitToken(
            int i, IBinder iBinder, String str) {
        RefreshRateTokenController refreshRateTokenController = mRefreshRateTokenController;
        RefreshRateMinLimitToken refreshRateMinLimitToken = new RefreshRateMinLimitToken();
        String m =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "RefreshRateMinLimitToken:", str);
        long uptimeMillis = SystemClock.uptimeMillis();
        RefreshRateToken.RefreshRateTokenInfo refreshRateTokenInfo =
                new RefreshRateToken.RefreshRateTokenInfo();
        refreshRateTokenInfo.mToken = iBinder;
        refreshRateTokenInfo.mTag = m;
        refreshRateTokenInfo.mAcquireTime = uptimeMillis;
        refreshRateTokenInfo.mRefreshRate = i;
        refreshRateTokenController.createRefreshRateToken(
                refreshRateMinLimitToken, refreshRateTokenInfo);
        return refreshRateMinLimitToken;
    }

    public static int getAmbientLuxZone(float f) {
        if (f < FullScreenMagnificationGestureHandler.MAX_SCALE) {
            return 0;
        }
        if (RefreshRateConfig.getInstance().getBrightnessThreshold().mLowAmbientLuxThreshold > f) {
            return 1;
        }
        return ((float)
                                RefreshRateConfig.getInstance()
                                        .getBrightnessThreshold()
                                        .mHighAmbientLuxThreshold)
                        < f
                ? 3
                : 2;
    }

    public static int getBrightnessZone(int i) {
        if (RefreshRateConfig.getInstance().getBrightnessThreshold().mLowBrightnessThreshold > i) {
            return 1;
        }
        return RefreshRateConfig.getInstance().getBrightnessThreshold().mHighBrightnessThreshold < i
                ? 3
                : 2;
    }

    public static boolean hasPassiveModeToken() {
        return mRefreshRateTokenController.mRefreshRateTokens.stream()
                .anyMatch(new RefreshRateController$$ExternalSyntheticLambda1(1));
    }

    public static boolean isLowAmbientLuxZone() {
        return getAmbientLuxZone(((Float) mAmbientLux.get()).floatValue()) == 1;
    }

    public static RefreshRateController makeController(IBinder iBinder) {
        if (iBinder == null) {
            return new NullController(0, null);
        }
        return RefreshRateConfig.getInstance().isSwitchable()
                ? new NullController(2, iBinder)
                : RefreshRateConfig.getInstance().isSeamless()
                        ? new NullController(1, iBinder)
                        : RefreshRateConfig.getInstance().isSeamlessPlus()
                                ? new SeamlessPlusController(iBinder)
                                : new RefreshRateController(iBinder);
    }

    public static void updateRefreshRateMaxLimitTokenLocked() {
        RefreshRateToken refreshRateToken =
                (RefreshRateToken)
                        mRefreshRateTokenController.mRefreshRateTokens.stream()
                                .filter(new RefreshRateController$$ExternalSyntheticLambda1(0))
                                .findFirst()
                                .orElse(null);
        if (refreshRateToken != null) {
            refreshRateToken.accept();
        }
    }

    public final PassiveModeToken createPassiveModeToken(IBinder iBinder, String str) {
        RefreshRateTokenController refreshRateTokenController = mRefreshRateTokenController;
        PassiveModeToken passiveModeToken = new PassiveModeToken();
        String m =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("PassiveModeToken:", str);
        long uptimeMillis = SystemClock.uptimeMillis();
        RefreshRateToken.RefreshRateTokenInfo refreshRateTokenInfo =
                new RefreshRateToken.RefreshRateTokenInfo();
        refreshRateTokenInfo.mToken = iBinder;
        refreshRateTokenInfo.mTag = m;
        refreshRateTokenInfo.mAcquireTime = uptimeMillis;
        refreshRateTokenInfo.mRefreshRate = 0;
        refreshRateTokenController.createRefreshRateToken(passiveModeToken, refreshRateTokenInfo);
        return passiveModeToken;
    }

    public String getControllerType() {
        return "RefreshRateController";
    }

    public Vote getNormalSpeedVote() {
        return this.mConfig.unsupportedNS()
                ? Vote.forPolicyRate(
                        this.mConfig.getHighSpeedRefreshRates().min(),
                        this.mConfig.getNormalSpeedRefreshRates().max())
                : Vote.forPolicyRate(
                        this.mConfig.getNormalSpeedRefreshRates().min(),
                        this.mConfig.getNormalSpeedRefreshRates().max());
    }

    public int getSwitchingType() {
        return 1;
    }

    public final boolean isHighBrightnessAmbientLuxZone() {
        return this.mConfig.getBrightnessThreshold().mHighBrightnessThreshold != -1
                && this.mConfig.getBrightnessThreshold().mHighAmbientLuxThreshold != -1
                && getBrightnessZone(mBrightness.get()) == 3
                && getAmbientLuxZone(((Float) mAmbientLux.get()).floatValue()) == 3;
    }

    public boolean isPassiveModeForTypeLocked() {
        return false;
    }

    public String logBrightnessStateLocked() {
        return "";
    }

    public final void logCurrentStateLocked(
            DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
        boolean z;
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
        sb.append("\n mModeSwitchingType: ");
        sb.append(DisplayModeDirector.switchingTypeToString(mDm.getModeSwitchingType()));
        sb.append(" mAlwaysRespectAppRequest: ");
        DisplayModeDirector displayModeDirector = mDm;
        synchronized (displayModeDirector.mLock) {
            z = displayModeDirector.mAlwaysRespectAppRequest;
        }
        sb.append(z);
        sb.append("\n");
        sb.append(
                " Current Mode mReportedRefreshRateMode(toSurfaceFlinger)="
                        + Settings.Secure.refreshRateModeToString(
                                this.mReportedRefreshRateMode.get())
                        + ", mRefreshRateMode(fromSettings)="
                        + Settings.Secure.refreshRateModeToString(this.mRefreshRateMode.get()));
        sb.append("\n");
        sb.append(logBrightnessStateLocked());
        mDisplayModeDirectorHistory.add(sb.toString());
    }

    public final void notifyRefreshRateModeLocked() {
        final int i =
                (CoreRune.FW_VRR_SEAMLESS && this.mPassive.get())
                        ? 3
                        : this.mConfig.unsupportedNS() ? 1 : this.mRefreshRateMode.get();
        if (this.mReportedRefreshRateMode.getAndSet(i) == i) {
            return;
        }
        final IBinder iBinder = this.mDisplayToken;
        mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.display.mode.RefreshRateController$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceControl.notifyHFRmode(iBinder, i);
                    }
                });
        if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
            logCurrentStateLocked(null);
        }
    }

    public void onBrightnessChangedLocked() {
        onBrightnessChangedLocked(false);
    }

    public final void onBrightnessChangedLocked(boolean z) {
        if (CoreRune.FW_VRR_FOR_SUB_DISPLAY
                && RefreshRateConfig.isSubScreen() != this.mIsSubScreen) {
            Slog.d(
                    "RefreshRateModeManager",
                    "default display was changed!, don't need to check passive mode for "
                            + this.mDisplayToken);
            return;
        }
        AtomicInteger atomicInteger = mBrightness;
        if (atomicInteger.get() < 0) {
            return;
        }
        Boolean bool = (Boolean) mIsDisplayPowerModeOnByDisplayType.get(this.mDisplayType);
        if (bool != null && !bool.booleanValue()) {
            ActivityManagerService$$ExternalSyntheticOutline0.m(
                    3,
                    new StringBuilder("onBrightnessChangedLocked returned by powerMode, caller="),
                    "RefreshRateModeManager");
            return;
        }
        Slog.d(
                "RefreshRateModeManager",
                "onBrightnessChangedLocked, brightness=" + atomicInteger + ", lux=" + mAmbientLux);
        updateLfdValueLocked(z);
        updatePassiveLocked();
    }

    public void onDisplayStateOffLocked() {}

    public void updateDefaultDisplayOrOffDisplayLocked() {}

    public void updateLfdValueLocked(boolean z) {}

    public void updatePassiveLocked() {
        boolean z;
        boolean z2 = false;
        if (mDm.getModeSwitchingType() != 0) {
            DisplayModeDirector displayModeDirector = mDm;
            synchronized (displayModeDirector.mLock) {
                z = displayModeDirector.mAlwaysRespectAppRequest;
            }
            if (!z && (this.mRefreshRateMode.get() == 1 || this.mConfig.unsupportedNS())) {
                z2 = isPassiveModeForTypeLocked();
            }
        }
        if (this.mPassive.getAndSet(z2) == z2) {
            return;
        }
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m("setPassiveMode=", ", brightness=", z2);
        m.append(mBrightness);
        m.append(", lux=");
        m.append(mAmbientLux);
        m.append(", PassiveModeToken=");
        m.append(hasPassiveModeToken());
        Slog.d("RefreshRateModeManager", m.toString());
        notifyRefreshRateModeLocked();
        if (CoreRune.FW_VRR_REFRESH_RATE_TOKEN) {
            updateRefreshRateMaxLimitTokenLocked();
        }
    }

    public final void updateRefreshRateModeLocked(boolean z) {
        int intForUser =
                Settings.Secure.getIntForUser(
                        mContext.getContentResolver(), this.mRefreshRateModeSetting, 0, -2);
        if (intForUser != this.mRefreshRateMode.get() || z) {
            if (CoreRune.FW_VRR_FOR_SUB_DISPLAY && z) {
                DisplayModeDirector displayModeDirector = mDm;
                int switchingType = getSwitchingType();
                synchronized (displayModeDirector.mLock) {
                    try {
                        if (switchingType != displayModeDirector.mModeSwitchingType) {
                            displayModeDirector.mModeSwitchingType = switchingType;
                            displayModeDirector.notifyDesiredDisplayModeSpecsChangedLocked();
                        }
                    } finally {
                    }
                }
            }
            EventLog.writeEvent(
                    1290001, String.format("%s", this.mDisplayToken), Integer.valueOf(intForUser));
            this.mRefreshRateMode.set(intForUser);
            Message.obtain(mHandler, 31, intForUser, -1).sendToTarget();
            this.mUpdateRefreshRateMode.set(true);
            mVotesStorage.updateVote(
                    -1,
                    11,
                    intForUser != 0
                            ? (intForUser == 1 || intForUser == 2)
                                    ? Vote.forPolicyRate(
                                            this.mConfig.getHighSpeedRefreshRates().min(),
                                            this.mConfig.getHighSpeedRefreshRates().max())
                                    : null
                            : getNormalSpeedVote());
            if (CoreRune.FW_VRR_SEAMLESS) {
                onBrightnessChangedLocked(true);
            }
            notifyRefreshRateModeLocked();
        }
    }
}
