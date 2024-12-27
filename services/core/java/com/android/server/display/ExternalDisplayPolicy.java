package com.android.server.display;

import android.os.Build;
import android.os.IThermalEventListener;
import android.os.SystemProperties;
import android.os.Temperature;
import android.util.Slog;
import android.view.DisplayInfo;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.display.notifications.DisplayNotificationManager;
import com.android.server.display.utils.DebugUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ExternalDisplayPolicy {
    public static final boolean DEBUG = DebugUtils.isDebuggable("ExternalDisplayPolicy");
    static final String ENABLE_ON_CONNECT = "persist.sys.display.enable_on_connect.external";
    public final DisplayNotificationManager mDisplayNotificationManager;
    public final ExternalDisplayStatsService mExternalDisplayStatsService;
    public final DisplayManagerFlags mFlags;
    public final DisplayManagerService.DisplayManagerHandler mHandler;
    public final DisplayManagerService.AnonymousClass1 mInjector;
    public boolean mIsBootCompleted;
    public final LogicalDisplayMapper mLogicalDisplayMapper;
    public final DisplayManagerService.SyncRoot mSyncRoot;
    public volatile int mStatus = 0;
    public final Set mDisplayIdsWaitingForBootCompletion = new HashSet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SkinThermalStatusObserver extends IThermalEventListener.Stub {
        public SkinThermalStatusObserver() {}

        public final void notifyThrottling(Temperature temperature) {
            int status = temperature.getStatus();
            int i = ExternalDisplayPolicy.this.mStatus;
            ExternalDisplayPolicy.this.mStatus = status;
            if (4 <= i || 4 > status) {
                return;
            }
            final ExternalDisplayPolicy externalDisplayPolicy = ExternalDisplayPolicy.this;
            synchronized (externalDisplayPolicy.mSyncRoot) {
                externalDisplayPolicy.mLogicalDisplayMapper.forEachLocked(
                        new Consumer() { // from class:
                            // com.android.server.display.ExternalDisplayPolicy$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ExternalDisplayPolicy externalDisplayPolicy2 =
                                        ExternalDisplayPolicy.this;
                                LogicalDisplay logicalDisplay = (LogicalDisplay) obj;
                                externalDisplayPolicy2.getClass();
                                if (ExternalDisplayPolicy.isExternalDisplayLocked(logicalDisplay)) {
                                    if (!externalDisplayPolicy2.mFlags
                                            .mConnectedDisplayManagementFlagState.isEnabled()) {
                                        Slog.e(
                                                "ExternalDisplayPolicy",
                                                "disableExternalDisplayLocked shouldn't be called"
                                                    + " when the connected display management flag"
                                                    + " is off");
                                        return;
                                    }
                                    if (!externalDisplayPolicy2.mFlags
                                            .mConnectedDisplayErrorHandlingFlagState.isEnabled()) {
                                        if (ExternalDisplayPolicy.DEBUG) {
                                            Slog.d(
                                                    "ExternalDisplayPolicy",
                                                    "disableExternalDisplayLocked shouldn't be"
                                                        + " called when the error handling flag is"
                                                        + " off");
                                            return;
                                        }
                                        return;
                                    }
                                    if (!logicalDisplay.mIsEnabled) {
                                        if (ExternalDisplayPolicy.DEBUG) {
                                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                    new StringBuilder(
                                                            "disableExternalDisplayLocked is not"
                                                                + " allowed: displayId="),
                                                    logicalDisplay.mDisplayId,
                                                    " isEnabledLocked=false",
                                                    "ExternalDisplayPolicy");
                                            return;
                                        }
                                        return;
                                    }
                                    if (!externalDisplayPolicy2.isExternalDisplayAllowed()) {
                                        Slog.w(
                                                "ExternalDisplayPolicy",
                                                "External display is currently not allowed and is"
                                                    + " getting disabled.");
                                        DisplayManagerService.DisplayManagerHandler
                                                displayManagerHandler =
                                                        externalDisplayPolicy2.mHandler;
                                        DisplayNotificationManager displayNotificationManager =
                                                externalDisplayPolicy2.mDisplayNotificationManager;
                                        Objects.requireNonNull(displayNotificationManager);
                                        displayManagerHandler.post(
                                                new ExternalDisplayPolicy$$ExternalSyntheticLambda0(
                                                        displayNotificationManager));
                                    }
                                    externalDisplayPolicy2.mLogicalDisplayMapper
                                            .setDisplayEnabledLocked(logicalDisplay, false);
                                    ExternalDisplayStatsService externalDisplayStatsService =
                                            externalDisplayPolicy2.mExternalDisplayStatsService;
                                    int i2 = logicalDisplay.mDisplayId;
                                    synchronized (
                                            externalDisplayStatsService.mExternalDisplayStates) {
                                        try {
                                            int i3 =
                                                    externalDisplayStatsService
                                                            .mExternalDisplayStates.get(i2, 1);
                                            if (i3 != 1 && i3 != 3) {
                                                for (int i4 = 0;
                                                        i4
                                                                < externalDisplayStatsService
                                                                        .mExternalDisplayStates
                                                                        .size();
                                                        i4++) {
                                                    if (externalDisplayStatsService
                                                                    .mExternalDisplayStates.keyAt(
                                                                    i4)
                                                            == i2) {
                                                        externalDisplayStatsService
                                                                .mExternalDisplayStates.put(i2, 3);
                                                        ExternalDisplayStatsService.Injector
                                                                injector =
                                                                        externalDisplayStatsService
                                                                                .mInjector;
                                                        int i5 = i4 + 1;
                                                        boolean z =
                                                                externalDisplayStatsService
                                                                        .mIsExternalDisplayUsedForAudio;
                                                        injector.getClass();
                                                        FrameworkStatsLog.write(
                                                                FrameworkStatsLog
                                                                        .EXTERNAL_DISPLAY_STATE_CHANGED,
                                                                3,
                                                                i5,
                                                                z);
                                                        if (ExternalDisplayStatsService.DEBUG) {
                                                            Slog.d(
                                                                    "ExternalDisplayStatsService",
                                                                    "logStateDisabled displayId="
                                                                            + i2
                                                                            + " countOfExternalDisplays="
                                                                            + i5
                                                                            + " currentState="
                                                                            + i3
                                                                            + " state=3"
                                                                            + " mIsExternalDisplayUsedForAudio="
                                                                            + externalDisplayStatsService
                                                                                    .mIsExternalDisplayUsedForAudio);
                                                        }
                                                    }
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                    if (ExternalDisplayPolicy.DEBUG) {
                                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                                new StringBuilder(
                                                        "disableExternalDisplayLocked complete"
                                                            + " displayId="),
                                                logicalDisplay.mDisplayId,
                                                "ExternalDisplayPolicy");
                                    }
                                }
                            }
                        },
                        true);
            }
        }
    }

    public ExternalDisplayPolicy(DisplayManagerService.AnonymousClass1 anonymousClass1) {
        this.mInjector = anonymousClass1;
        DisplayManagerService displayManagerService = DisplayManagerService.this;
        this.mLogicalDisplayMapper = displayManagerService.mLogicalDisplayMapper;
        this.mSyncRoot = displayManagerService.mSyncRoot;
        this.mFlags = displayManagerService.mFlags;
        this.mDisplayNotificationManager = displayManagerService.mDisplayNotificationManager;
        this.mHandler = displayManagerService.mHandler;
        this.mExternalDisplayStatsService = displayManagerService.mExternalDisplayStatsService;
    }

    public static boolean isExternalDisplayLocked(LogicalDisplay logicalDisplay) {
        return logicalDisplay.getDisplayInfoLocked().type == 2;
    }

    public final void handleExternalDisplayConnectedLocked(LogicalDisplay logicalDisplay) {
        if (!isExternalDisplayLocked(logicalDisplay)) {
            Slog.e(
                    "ExternalDisplayPolicy",
                    "handleExternalDisplayConnectedLocked called for non-external display");
            return;
        }
        if (!this.mFlags.mConnectedDisplayManagementFlagState.isEnabled()) {
            if (DEBUG) {
                Slog.d(
                        "ExternalDisplayPolicy",
                        "handleExternalDisplayConnectedLocked connected display management flag is"
                            + " off");
                return;
            }
            return;
        }
        if (!this.mIsBootCompleted) {
            ((HashSet) this.mDisplayIdsWaitingForBootCompletion)
                    .add(Integer.valueOf(logicalDisplay.mDisplayId));
            return;
        }
        ExternalDisplayStatsService externalDisplayStatsService = this.mExternalDisplayStatsService;
        externalDisplayStatsService.getClass();
        DisplayInfo displayInfoLocked = logicalDisplay.getDisplayInfoLocked();
        if (displayInfoLocked != null && displayInfoLocked.type == 2) {
            int i = logicalDisplay.mDisplayId;
            synchronized (externalDisplayStatsService.mExternalDisplayStates) {
                try {
                    int i2 = externalDisplayStatsService.mExternalDisplayStates.get(i, 1);
                    if (i2 == 1) {
                        externalDisplayStatsService.mExternalDisplayStates.put(i, 2);
                        int size = externalDisplayStatsService.mExternalDisplayStates.size();
                        if (size == 1) {
                            externalDisplayStatsService.mInjector.mHandler.post(
                                    new ExternalDisplayStatsService$$ExternalSyntheticLambda0(
                                            externalDisplayStatsService, 1));
                        }
                        ExternalDisplayStatsService.Injector injector =
                                externalDisplayStatsService.mInjector;
                        boolean z = externalDisplayStatsService.mIsExternalDisplayUsedForAudio;
                        injector.getClass();
                        FrameworkStatsLog.write(
                                FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, 2, size, z);
                        if (ExternalDisplayStatsService.DEBUG) {
                            StringBuilder m =
                                    ArrayUtils$$ExternalSyntheticOutline0.m(
                                            i,
                                            size,
                                            "logStateConnected displayId=",
                                            " countOfExternalDisplays=",
                                            " currentState=");
                            m.append(i2);
                            m.append(" state=2 mIsExternalDisplayUsedForAudio=");
                            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                    "ExternalDisplayStatsService",
                                    m,
                                    externalDisplayStatsService.mIsExternalDisplayUsedForAudio);
                        }
                    }
                } finally {
                }
            }
        }
        if ((Build.IS_ENG || Build.IS_USERDEBUG)
                && SystemProperties.getBoolean(ENABLE_ON_CONNECT, false)) {
            Slog.w(
                    "ExternalDisplayPolicy",
                    "External display is enabled by default, bypassing user consent.");
            DisplayManagerService.this.sendDisplayEventLocked(logicalDisplay, 6);
            return;
        }
        this.mLogicalDisplayMapper.setEnabledLocked(logicalDisplay, false);
        if (isExternalDisplayAllowed()) {
            DisplayManagerService.this.sendDisplayEventLocked(logicalDisplay, 6);
            if (DEBUG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder(
                                "handleExternalDisplayConnectedLocked complete displayId="),
                        logicalDisplay.mDisplayId,
                        "ExternalDisplayPolicy");
                return;
            }
            return;
        }
        Slog.w(
                "ExternalDisplayPolicy",
                "handleExternalDisplayConnectedLocked: External display can not be used because it"
                    + " is currently not allowed.");
        DisplayManagerService.DisplayManagerHandler displayManagerHandler = this.mHandler;
        DisplayNotificationManager displayNotificationManager = this.mDisplayNotificationManager;
        Objects.requireNonNull(displayNotificationManager);
        displayManagerHandler.post(
                new ExternalDisplayPolicy$$ExternalSyntheticLambda0(displayNotificationManager));
    }

    public boolean isExternalDisplayAllowed() {
        return this.mStatus < 4;
    }

    public final void setExternalDisplayEnabledLocked(LogicalDisplay logicalDisplay, boolean z) {
        if (!isExternalDisplayLocked(logicalDisplay)) {
            Slog.e(
                    "ExternalDisplayPolicy",
                    "setExternalDisplayEnabledLocked called for non external display");
            return;
        }
        if (!this.mFlags.mConnectedDisplayManagementFlagState.isEnabled()) {
            if (DEBUG) {
                Slog.d(
                        "ExternalDisplayPolicy",
                        "setExternalDisplayEnabledLocked: External display management is not"
                            + " enabled on your device, cannot enable/disable display.");
            }
        } else {
            if (!z || isExternalDisplayAllowed()) {
                this.mLogicalDisplayMapper.setDisplayEnabledLocked(logicalDisplay, z);
                return;
            }
            Slog.w(
                    "ExternalDisplayPolicy",
                    "setExternalDisplayEnabledLocked: External display can not be enabled because"
                        + " it is currently not allowed.");
            DisplayNotificationManager displayNotificationManager =
                    this.mDisplayNotificationManager;
            Objects.requireNonNull(displayNotificationManager);
            this.mHandler.post(
                    new ExternalDisplayPolicy$$ExternalSyntheticLambda0(
                            displayNotificationManager));
        }
    }
}
