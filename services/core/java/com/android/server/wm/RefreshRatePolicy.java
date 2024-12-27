package com.android.server.wm;

import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManager;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.window.flags.Flags;

import com.samsung.android.core.SystemHistory;
import com.samsung.android.rune.CoreRune;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RefreshRatePolicy {
    public final Display.Mode mDefaultMode;
    public final DisplayInfo mDisplayInfo;
    public final HighRefreshRateDenylist mHighRefreshRateDenylist;
    public Display.Mode mLowRefreshRateMode;
    public float mMaxSupportedRefreshRate;
    public float mMinSupportedRefreshRate;
    public final RefreshRatePolicyLogger mRefreshRatePolicyLogger;
    public final WindowManagerService mWmService;
    public final PackageRefreshRate mNonHighRefreshRatePackages = new PackageRefreshRate();
    public final ConcurrentHashMap mFixedRefreshRatePackages = new ConcurrentHashMap();
    public boolean mRestrictHighRefreshRate = false;
    public final AtomicBoolean mReportedRestrictHighRefreshRate = new AtomicBoolean(false);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FrameRateVote {
        public int mCompatibility;
        public float mRefreshRate;
        public int mSelectionStrategy;

        public final boolean equals(Object obj) {
            if (!(obj instanceof FrameRateVote)) {
                return false;
            }
            FrameRateVote frameRateVote = (FrameRateVote) obj;
            float f = frameRateVote.mRefreshRate;
            float f2 = this.mRefreshRate;
            return f2 <= f + 0.01f
                    && f2 >= f - 0.01f
                    && this.mCompatibility == frameRateVote.mCompatibility
                    && this.mSelectionStrategy == frameRateVote.mSelectionStrategy;
        }

        public final int hashCode() {
            return Objects.hash(
                    Float.valueOf(this.mRefreshRate),
                    Integer.valueOf(this.mCompatibility),
                    Integer.valueOf(this.mSelectionStrategy));
        }

        public final String toString() {
            return "mRefreshRate="
                    + this.mRefreshRate
                    + ", mCompatibility="
                    + this.mCompatibility
                    + ", mSelectionStrategy="
                    + this.mSelectionStrategy;
        }

        public final boolean update(int i, int i2, float f) {
            float f2 = this.mRefreshRate;
            if (f2 <= f + 0.01f
                    && f2 >= f - 0.01f
                    && this.mCompatibility == i
                    && this.mSelectionStrategy == i2) {
                return false;
            }
            this.mRefreshRate = f;
            this.mCompatibility = i;
            this.mSelectionStrategy = i2;
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageRefreshRate {
        public final HashMap mPackages = new HashMap();

        public PackageRefreshRate() {}
    }

    public RefreshRatePolicy(
            WindowManagerService windowManagerService,
            DisplayInfo displayInfo,
            HighRefreshRateDenylist highRefreshRateDenylist) {
        this.mRefreshRatePolicyLogger = new RefreshRatePolicyLogger();
        this.mDisplayInfo = displayInfo;
        Display.Mode defaultMode = displayInfo.getDefaultMode();
        this.mDefaultMode = defaultMode;
        this.mLowRefreshRateMode = findLowRefreshRateMode(displayInfo, defaultMode);
        this.mHighRefreshRateDenylist = highRefreshRateDenylist;
        this.mWmService = windowManagerService;
        if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
            this.mRefreshRatePolicyLogger = new RefreshRatePolicyLogger();
        }
    }

    public static boolean shouldIgnoreRestrictedRange(WindowState windowState) {
        if (windowState.getTask() == null || !windowState.getTask().inPinnedWindowingMode()) {
            WindowManager.LayoutParams layoutParams = windowState.mAttrs;
            if (layoutParams.type != 2038 || layoutParams.isFullscreen()) {
                return false;
            }
        }
        return true;
    }

    public final Display.Mode findLowRefreshRateMode(DisplayInfo displayInfo, Display.Mode mode) {
        float[] defaultRefreshRates =
                CoreRune.FW_VRR_DISCRETE
                        ? displayInfo.getDefaultRefreshRates(displayInfo.appsSupportedModes)
                        : displayInfo.getDefaultRefreshRates();
        float refreshRate = mode.getRefreshRate();
        this.mMinSupportedRefreshRate = refreshRate;
        this.mMaxSupportedRefreshRate = refreshRate;
        for (int length = defaultRefreshRates.length - 1; length >= 0; length--) {
            this.mMinSupportedRefreshRate =
                    Math.min(this.mMinSupportedRefreshRate, defaultRefreshRates[length]);
            this.mMaxSupportedRefreshRate =
                    Math.max(this.mMaxSupportedRefreshRate, defaultRefreshRates[length]);
            float f = defaultRefreshRates[length];
            if (f >= 60.0f
                    && ((CoreRune.FW_VRR_POLICY && refreshRate < 60.0f) || f < refreshRate)) {
                refreshRate = f;
            }
        }
        return CoreRune.FW_VRR_DISCRETE
                ? displayInfo.findDefaultModeByRefreshRate(
                        refreshRate, displayInfo.appsSupportedModes)
                : displayInfo.findDefaultModeByRefreshRate(refreshRate);
    }

    public final int getPreferredModeId(WindowState windowState) {
        Display.Mode mode;
        int i = windowState.mAttrs.preferredDisplayModeId;
        if (i <= 0) {
            return 0;
        }
        if (!Flags.explicitRefreshRateHints() && windowState.isAnimationRunningSelfOrParent()) {
            Display.Mode[] modeArr = this.mDisplayInfo.supportedModes;
            int length = modeArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    mode = null;
                    break;
                }
                mode = modeArr[i2];
                if (i == mode.getModeId()) {
                    break;
                }
                i2++;
            }
            if (mode != null) {
                int physicalWidth = mode.getPhysicalWidth();
                int physicalHeight = mode.getPhysicalHeight();
                if ((physicalWidth != this.mDefaultMode.getPhysicalWidth()
                                || physicalHeight != this.mDefaultMode.getPhysicalHeight())
                        && physicalWidth == this.mDisplayInfo.getNaturalWidth()
                        && physicalHeight == this.mDisplayInfo.getNaturalHeight()) {
                    return i;
                }
            }
            return 0;
        }
        String str = CoreRune.FW_VRR_POLICY ? windowState.mAttrs.packageName : "";
        if (CoreRune.FW_VRR_FIXED_REFRESH_RATE_PACKAGE
                && this.mFixedRefreshRatePackages.containsKey(str)) {
            return ((Integer) this.mFixedRefreshRatePackages.get(str)).intValue();
        }
        boolean z = CoreRune.FW_VRR_LOW_REFRESH_RATE_LIST;
        WindowManagerService windowManagerService = this.mWmService;
        if (z
                && windowState.isFocused()
                && windowManagerService.mAtmService.mExt.mLowRefreshRateList.contains(str)) {
            return this.mLowRefreshRateMode.getModeId();
        }
        if (CoreRune.FW_VRR_HRR_CHINA_DELTA
                && i != 0
                && windowState.getDisplayInfo() != null
                && windowManagerService.mAtmService.mExt.mHighRefreshRateBlockList.contains(str)) {
            for (Display.Mode mode2 :
                    CoreRune.FW_VRR_DISCRETE
                            ? windowState.getDisplayInfo().appsSupportedModes
                            : windowState.getDisplayInfo().supportedModes) {
                if (i == mode2.getModeId() && mode2.getRefreshRate() >= 119.99f) {
                    return 0;
                }
            }
        }
        return i;
    }

    public final float getRefreshRateFromFixedRefreshRatePackages(WindowState windowState) {
        String str = windowState.mAttrs.packageName;
        if (!this.mFixedRefreshRatePackages.containsKey(str)) {
            return FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
        DisplayInfo displayInfo = windowState.getDisplayInfo();
        int intValue = ((Integer) this.mFixedRefreshRatePackages.get(str)).intValue();
        for (Display.Mode mode : displayInfo.appsSupportedModes) {
            if (intValue == mode.getModeId()) {
                return mode.getRefreshRate();
            }
        }
        return FullScreenMagnificationGestureHandler.MAX_SCALE;
    }

    public final void updateLog(WindowState windowState, int i, float f, int i2) {
        RefreshRatePolicyLogger.RefreshRateHistory refreshRateHistory =
                (RefreshRatePolicyLogger.RefreshRateHistory)
                        this.mRefreshRatePolicyLogger.mRefreshRateHistories.get(i2);
        if (refreshRateHistory != null) {
            if (refreshRateHistory.mLastRequester == windowState
                    && refreshRateHistory.mModeId == i
                    && refreshRateHistory.mRefreshRate == f) {
                return;
            }
            refreshRateHistory.mLastRequester = windowState;
            refreshRateHistory.mModeId = i;
            refreshRateHistory.mRefreshRate = f;
            if (windowState != null) {
                SystemHistory systemHistory = refreshRateHistory.mHistory;
                StringBuilder sb = new StringBuilder("Requested (");
                if (refreshRateHistory.mRefreshRate != -1.0f) {
                    sb.append(" refreshRate=");
                    sb.append(refreshRateHistory.mRefreshRate);
                }
                if (refreshRateHistory.mModeId != -1) {
                    sb.append(" modeId=");
                    sb.append(refreshRateHistory.mModeId);
                }
                sb.append(" w=");
                sb.append(refreshRateHistory.mLastRequester);
                sb.append(")");
                systemHistory.add(sb.toString());
            }
        }
    }
}
