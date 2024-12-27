package com.android.server.display.mode;

import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class VoteSummary {
    public float appRequestBaseModeRefreshRate;
    public boolean disableRefreshRateSwitching;
    public int height;
    public final boolean mIsDisplayResolutionRangeVotingEnabled;
    public final boolean mLoggingEnabled;
    public final boolean mSupportedModesVoteEnabled;
    public final boolean mSupportsFrameRateOverride;
    public float maxPhysicalRefreshRate;
    public float maxRenderFrameRate;
    public int minHeight;
    public float minPhysicalRefreshRate;
    public float minRenderFrameRate;
    public int minWidth;
    public final Set requestedRefreshRates = new HashSet();
    public List supportedModeIds;
    public List supportedRefreshRates;
    public int width;

    public VoteSummary(boolean z, boolean z2, boolean z3, boolean z4) {
        this.mIsDisplayResolutionRangeVotingEnabled = z;
        this.mSupportedModesVoteEnabled = z2;
        this.mLoggingEnabled = z3;
        this.mSupportsFrameRateOverride = z4;
        reset();
    }

    public static boolean equalsWithinFloatTolerance(float f, float f2) {
        return f >= f2 - 0.01f && f <= f2 + 0.01f;
    }

    public final void adjustSize(Display.Mode mode, Display.Mode[] modeArr) {
        int i;
        int i2 = this.height;
        if (i2 == -1 || (i = this.width) == -1) {
            this.width = mode.getPhysicalWidth();
            this.height = mode.getPhysicalHeight();
        } else if (this.mIsDisplayResolutionRangeVotingEnabled) {
            this.width = -1;
            this.height = -1;
            int i3 = 0;
            for (Display.Mode mode2 : modeArr) {
                if (mode2.getPhysicalWidth() <= i
                        && mode2.getPhysicalHeight() <= i2
                        && mode2.getPhysicalWidth() >= this.minWidth
                        && mode2.getPhysicalHeight() >= this.minHeight
                        && mode2.getRefreshRate() >= this.minPhysicalRefreshRate - 0.01f
                        && mode2.getRefreshRate() <= this.maxPhysicalRefreshRate + 0.01f) {
                    int physicalWidth = mode2.getPhysicalWidth() * mode2.getPhysicalHeight();
                    if (physicalWidth > i3
                            || (mode2.getPhysicalWidth() == i && mode2.getPhysicalHeight() == i2)) {
                        this.width = mode2.getPhysicalWidth();
                        this.height = mode2.getPhysicalHeight();
                        i3 = physicalWidth;
                    }
                }
            }
        }
        if (this.mLoggingEnabled) {
            Slog.i("VoteSummary", "adjustSize: " + this);
        }
    }

    public final void applyVotes(int i, int i2, SparseArray sparseArray) {
        reset();
        for (int i3 = i2; i3 >= i; i3--) {
            Vote vote = (Vote) sparseArray.get(i3);
            if (vote != null) {
                vote.updateSummary(this);
            }
        }
        if (this.mLoggingEnabled) {
            Slog.i(
                    "VoteSummary",
                    "applyVotes for range ["
                            + Vote.priorityToString(i)
                            + ", "
                            + Vote.priorityToString(i2)
                            + "]: "
                            + this);
        }
    }

    public final void disableModeSwitching(float f) {
        this.maxPhysicalRefreshRate = f;
        this.minPhysicalRefreshRate = f;
        this.maxRenderFrameRate = Math.min(this.maxRenderFrameRate, f);
        if (this.mLoggingEnabled) {
            Slog.i("VoteSummary", "Disabled mode switching on summary: " + this);
        }
    }

    public final void disableRenderRateSwitching(float f) {
        this.minRenderFrameRate = this.maxRenderFrameRate;
        if (!isRenderRateAchievable(f)) {
            this.maxRenderFrameRate = f;
            this.minRenderFrameRate = f;
        }
        if (this.mLoggingEnabled) {
            Slog.i("VoteSummary", "Disabled render rate switching on summary: " + this);
        }
    }

    public final List filterModes(Display.Mode[] modeArr) {
        char c;
        float f = this.minRenderFrameRate;
        float f2 = this.maxRenderFrameRate + 0.01f;
        boolean z = this.mLoggingEnabled;
        if (f <= f2) {
            List list = this.supportedRefreshRates;
            boolean z2 = this.mSupportedModesVoteEnabled;
            if (list == null || !z2 || !((ArrayList) list).isEmpty()) {
                Iterator it = ((HashSet) this.requestedRefreshRates).iterator();
                while (it.hasNext()) {
                    Float f3 = (Float) it.next();
                    if (f3.floatValue() < this.minRenderFrameRate
                            || f3.floatValue() > this.maxRenderFrameRate) {
                        if (z) {
                            Slog.w(
                                    "VoteSummary",
                                    "Requested refreshRate is outside frame rate range:"
                                        + " requestedRefreshRates="
                                            + this.requestedRefreshRates
                                            + ", requestedRefreshRate="
                                            + f3
                                            + ", minRenderFrameRate="
                                            + this.minRenderFrameRate
                                            + ", maxRenderFrameRate="
                                            + this.maxRenderFrameRate);
                        }
                    }
                }
                ArrayList arrayList = new ArrayList();
                boolean z3 =
                        this.appRequestBaseModeRefreshRate
                                > FullScreenMagnificationGestureHandler.MAX_SCALE;
                for (Display.Mode mode : modeArr) {
                    List list2 = this.supportedRefreshRates;
                    if (list2 != null && z2) {
                        Iterator it2 = ((ArrayList) list2).iterator();
                        while (it2.hasNext()) {
                            SupportedRefreshRatesVote.RefreshRates refreshRates =
                                    (SupportedRefreshRatesVote.RefreshRates) it2.next();
                            if (!equalsWithinFloatTolerance(
                                            mode.getRefreshRate(), refreshRates.mPeakRefreshRate)
                                    || !equalsWithinFloatTolerance(
                                            mode.getVsyncRate(), refreshRates.mVsyncRate)) {}
                        }
                        if (z) {
                            Slog.w(
                                    "VoteSummary",
                                    "Discarding mode "
                                            + mode.getModeId()
                                            + ", supportedRefreshRates not found: mode.refreshRate="
                                            + mode.getRefreshRate()
                                            + ", mode.vsyncRate="
                                            + mode.getVsyncRate()
                                            + ", supportedRefreshRates="
                                            + this.supportedRefreshRates);
                        }
                        c = 55050;
                    }
                    List list3 = this.supportedModeIds;
                    if (list3 == null || !z2 || list3.contains(Integer.valueOf(mode.getModeId()))) {
                        if (mode.getPhysicalWidth() == this.width
                                && mode.getPhysicalHeight() == this.height) {
                            float refreshRate = mode.getRefreshRate();
                            c = 55050;
                            if (refreshRate >= this.minPhysicalRefreshRate - 0.01f
                                    && refreshRate <= this.maxPhysicalRefreshRate + 0.01f) {
                                float refreshRate2 = mode.getRefreshRate();
                                if (this.mSupportsFrameRateOverride
                                        || (refreshRate2 >= this.minRenderFrameRate - 0.01f
                                                && refreshRate2
                                                        <= this.maxRenderFrameRate + 0.01f)) {
                                    float refreshRate3 = mode.getRefreshRate();
                                    if (isRenderRateAchievable(refreshRate3)) {
                                        arrayList.add(mode);
                                        if (equalsWithinFloatTolerance(
                                                mode.getRefreshRate(),
                                                this.appRequestBaseModeRefreshRate)) {
                                            z3 = false;
                                        }
                                    } else if (z) {
                                        Slog.w(
                                                "VoteSummary",
                                                "Discarding mode "
                                                        + mode.getModeId()
                                                        + ", outside frame rate bounds:"
                                                        + " minRenderFrameRate="
                                                        + this.minRenderFrameRate
                                                        + ", maxRenderFrameRate="
                                                        + this.maxRenderFrameRate
                                                        + ", modePhysicalRefreshRate="
                                                        + refreshRate3);
                                    }
                                } else if (z) {
                                    Slog.w(
                                            "VoteSummary",
                                            "Discarding mode "
                                                    + mode.getModeId()
                                                    + ", outside render rate bounds:"
                                                    + " minRenderFrameRate="
                                                    + this.minRenderFrameRate
                                                    + ", maxRenderFrameRate="
                                                    + this.maxRenderFrameRate
                                                    + ", modeRefreshRate="
                                                    + refreshRate2);
                                }
                            } else if (z) {
                                Slog.w(
                                        "VoteSummary",
                                        "Discarding mode "
                                                + mode.getModeId()
                                                + ", outside refresh rate bounds:"
                                                + " minPhysicalRefreshRate="
                                                + this.minPhysicalRefreshRate
                                                + ", maxPhysicalRefreshRate="
                                                + this.maxPhysicalRefreshRate
                                                + ", modeRefreshRate="
                                                + refreshRate);
                            }
                        } else {
                            c = 55050;
                            if (z) {
                                Slog.w(
                                        "VoteSummary",
                                        "Discarding mode "
                                                + mode.getModeId()
                                                + ", wrong size: desiredWidth="
                                                + this.width
                                                + ": desiredHeight="
                                                + this.height
                                                + ": actualWidth="
                                                + mode.getPhysicalWidth()
                                                + ": actualHeight="
                                                + mode.getPhysicalHeight());
                            }
                        }
                    } else {
                        if (z) {
                            Slog.w(
                                    "VoteSummary",
                                    "Discarding mode "
                                            + mode.getModeId()
                                            + ", supportedMode not found: mode.modeId="
                                            + mode.getModeId()
                                            + ", supportedModeIds="
                                            + this.supportedModeIds);
                        }
                        c = 55050;
                    }
                }
                return z3 ? new ArrayList() : arrayList;
            }
            if (z) {
                Slog.w("VoteSummary", "Vote summary resulted in empty set (empty supportedModes)");
            }
        } else if (z) {
            Slog.w(
                    "VoteSummary",
                    "Vote summary resulted in empty set (invalid frame rate range):"
                        + " minRenderFrameRate="
                            + this.minRenderFrameRate
                            + ", maxRenderFrameRate="
                            + this.maxRenderFrameRate);
        }
        return new ArrayList();
    }

    public final boolean isRenderRateAchievable(float f) {
        return f / ((float) ((int) Math.ceil((double) ((f / this.maxRenderFrameRate) - 0.01f))))
                >= this.minRenderFrameRate - 0.01f;
    }

    public final void limitRefreshRanges(VoteSummary voteSummary) {
        this.minPhysicalRefreshRate =
                Math.min(this.minPhysicalRefreshRate, voteSummary.minPhysicalRefreshRate);
        this.maxPhysicalRefreshRate =
                Math.max(this.maxPhysicalRefreshRate, voteSummary.maxPhysicalRefreshRate);
        this.minRenderFrameRate = Math.min(this.minRenderFrameRate, voteSummary.minRenderFrameRate);
        this.maxRenderFrameRate = Math.max(this.maxRenderFrameRate, voteSummary.maxRenderFrameRate);
        if (this.mLoggingEnabled) {
            Slog.i("VoteSummary", "limitRefreshRanges: " + this);
        }
    }

    public final void reset() {
        this.minPhysicalRefreshRate = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.maxPhysicalRefreshRate = Float.POSITIVE_INFINITY;
        this.minRenderFrameRate = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.maxRenderFrameRate = Float.POSITIVE_INFINITY;
        this.width = -1;
        this.height = -1;
        this.minWidth = 0;
        this.minHeight = 0;
        this.disableRefreshRateSwitching = false;
        this.appRequestBaseModeRefreshRate = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.requestedRefreshRates.clear();
        this.supportedRefreshRates = null;
        this.supportedModeIds = null;
        if (this.mLoggingEnabled) {
            Slog.i("VoteSummary", "Summary reset: " + this);
        }
    }

    public final Display.Mode selectBaseMode(List list, Display.Mode mode, int i) {
        float f = this.appRequestBaseModeRefreshRate;
        if (f <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            f = mode.getRefreshRate();
        }
        if (CoreRune.FW_VRR_POLICY
                && !CoreRune.FW_VRR_SEAMLESS
                && i == 0
                && !list.isEmpty()
                && this.appRequestBaseModeRefreshRate
                        == FullScreenMagnificationGestureHandler.MAX_SCALE) {
            f =
                    ((Display.Mode)
                                    list.stream()
                                            .max(new VoteSummary$$ExternalSyntheticLambda0())
                                            .get())
                            .getRefreshRate();
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Display.Mode mode2 = (Display.Mode) it.next();
            if (equalsWithinFloatTolerance(f, mode2.getRefreshRate())) {
                return mode2;
            }
        }
        if (list.isEmpty()) {
            return null;
        }
        return (Display.Mode) list.get(0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("VoteSummary{ minPhysicalRefreshRate=");
        sb.append(this.minPhysicalRefreshRate);
        sb.append(", maxPhysicalRefreshRate=");
        sb.append(this.maxPhysicalRefreshRate);
        sb.append(", minRenderFrameRate=");
        sb.append(this.minRenderFrameRate);
        sb.append(", maxRenderFrameRate=");
        sb.append(this.maxRenderFrameRate);
        sb.append(", width=");
        sb.append(this.width);
        sb.append(", height=");
        sb.append(this.height);
        sb.append(", minWidth=");
        sb.append(this.minWidth);
        sb.append(", minHeight=");
        sb.append(this.minHeight);
        sb.append(", disableRefreshRateSwitching=");
        sb.append(this.disableRefreshRateSwitching);
        sb.append(", appRequestBaseModeRefreshRate=");
        sb.append(this.appRequestBaseModeRefreshRate);
        sb.append(", requestRefreshRates=");
        sb.append(this.requestedRefreshRates);
        sb.append(", supportedRefreshRates=");
        sb.append(this.supportedRefreshRates);
        sb.append(", supportedModeIds=");
        sb.append(this.supportedModeIds);
        sb.append(", mIsDisplayResolutionRangeVotingEnabled=");
        sb.append(this.mIsDisplayResolutionRangeVotingEnabled);
        sb.append(", mSupportedModesVoteEnabled=");
        sb.append(this.mSupportedModesVoteEnabled);
        sb.append(", mSupportsFrameRateOverride=");
        return OptionalBool$$ExternalSyntheticOutline0.m(" }", sb, this.mSupportsFrameRateOverride);
    }
}
