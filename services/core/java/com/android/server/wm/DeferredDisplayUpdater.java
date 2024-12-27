package com.android.server.wm;

import android.graphics.Rect;
import android.os.Message;
import android.os.Trace;
import android.util.Slog;
import android.view.DisplayInfo;
import android.window.DisplayAreaInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.wm.RemoteDisplayChangeController;
import com.android.server.wm.TransitionController;
import com.android.server.wm.WindowManagerService;
import com.android.server.wm.utils.DisplayInfoOverrides$DisplayInfoFieldsUpdater;
import com.android.window.flags.Flags;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DeferredDisplayUpdater implements DisplayUpdater {
    static final DisplayInfoOverrides$DisplayInfoFieldsUpdater DEFERRABLE_FIELDS = new DeferredDisplayUpdater$$ExternalSyntheticLambda0();
    public final DisplayContent mDisplayContent;
    public DisplayInfo mLastDisplayInfo;
    public DisplayInfo mLastWmDisplayInfo;
    public final DisplayInfo mNonOverrideDisplayInfo;
    public final DisplayInfo mOutputDisplayInfo;
    public final DeferredDisplayUpdater$$ExternalSyntheticLambda2 mScreenUnblockTimeoutRunnable;
    public Message mScreenUnblocker;
    public boolean mShouldWaitForTransitionWhenScreenOn;

    public DeferredDisplayUpdater(DisplayContent displayContent) {
        DisplayInfo displayInfo = new DisplayInfo();
        this.mNonOverrideDisplayInfo = displayInfo;
        this.mOutputDisplayInfo = new DisplayInfo();
        this.mScreenUnblockTimeoutRunnable = new DeferredDisplayUpdater$$ExternalSyntheticLambda2(this, 0);
        this.mDisplayContent = displayContent;
        displayInfo.copyFrom(displayContent.mDisplayInfo);
    }

    public static int calculateDisplayInfoDiff(DisplayInfo displayInfo, DisplayInfo displayInfo2) {
        if (Objects.equals(displayInfo, displayInfo2)) {
            return 0;
        }
        if (displayInfo == null || displayInfo2 == null) {
            return -1;
        }
        int i = (displayInfo.layerStack == displayInfo2.layerStack && displayInfo.flags == displayInfo2.flags && displayInfo.type == displayInfo2.type && displayInfo.displayId == displayInfo2.displayId && displayInfo.displayGroupId == displayInfo2.displayGroupId && Objects.equals(displayInfo.deviceProductInfo, displayInfo2.deviceProductInfo) && displayInfo.modeId == displayInfo2.modeId && displayInfo.renderFrameRate == displayInfo2.renderFrameRate && displayInfo.defaultModeId == displayInfo2.defaultModeId && displayInfo.userPreferredModeId == displayInfo2.userPreferredModeId && Arrays.equals(displayInfo.supportedModes, displayInfo2.supportedModes) && Arrays.equals(displayInfo.appsSupportedModes, displayInfo2.appsSupportedModes) && displayInfo.colorMode == displayInfo2.colorMode && Arrays.equals(displayInfo.supportedColorModes, displayInfo2.supportedColorModes) && Objects.equals(displayInfo.hdrCapabilities, displayInfo2.hdrCapabilities) && Arrays.equals(displayInfo.userDisabledHdrTypes, displayInfo2.userDisabledHdrTypes) && displayInfo.minimalPostProcessingSupported == displayInfo2.minimalPostProcessingSupported && displayInfo.appVsyncOffsetNanos == displayInfo2.appVsyncOffsetNanos && displayInfo.presentationDeadlineNanos == displayInfo2.presentationDeadlineNanos && displayInfo.state == displayInfo2.state && displayInfo.committedState == displayInfo2.committedState && displayInfo.ownerUid == displayInfo2.ownerUid && Objects.equals(displayInfo.ownerPackageName, displayInfo2.ownerPackageName) && displayInfo.removeMode == displayInfo2.removeMode && displayInfo.getRefreshRate() == displayInfo2.getRefreshRate() && displayInfo.brightnessMinimum == displayInfo2.brightnessMinimum && displayInfo.brightnessMaximum == displayInfo2.brightnessMaximum && displayInfo.brightnessDefault == displayInfo2.brightnessDefault && displayInfo.installOrientation == displayInfo2.installOrientation && Objects.equals(displayInfo.layoutLimitedRefreshRate, displayInfo2.layoutLimitedRefreshRate) && BrightnessSynchronizer.floatEquals(displayInfo.hdrSdrRatio, displayInfo2.hdrSdrRatio) && displayInfo.thermalRefreshRateThrottling.contentEquals(displayInfo2.thermalRefreshRateThrottling) && Objects.equals(displayInfo.thermalBrightnessThrottlingDataId, displayInfo2.thermalBrightnessThrottlingDataId)) ? 0 : 2;
        return (displayInfo.appWidth == displayInfo2.appWidth && displayInfo.appHeight == displayInfo2.appHeight && displayInfo.smallestNominalAppWidth == displayInfo2.smallestNominalAppWidth && displayInfo.smallestNominalAppHeight == displayInfo2.smallestNominalAppHeight && displayInfo.largestNominalAppWidth == displayInfo2.largestNominalAppWidth && displayInfo.largestNominalAppHeight == displayInfo2.largestNominalAppHeight && displayInfo.logicalWidth == displayInfo2.logicalWidth && displayInfo.logicalHeight == displayInfo2.logicalHeight && displayInfo.physicalXDpi == displayInfo2.physicalXDpi && displayInfo.physicalYDpi == displayInfo2.physicalYDpi && displayInfo.rotation == displayInfo2.rotation && Objects.equals(displayInfo.displayCutout, displayInfo2.displayCutout) && displayInfo.logicalDensityDpi == displayInfo2.logicalDensityDpi && Objects.equals(displayInfo.roundedCorners, displayInfo2.roundedCorners) && Objects.equals(displayInfo.displayShape, displayInfo2.displayShape) && Objects.equals(displayInfo.uniqueId, displayInfo2.uniqueId) && Objects.equals(displayInfo.address, displayInfo2.address)) ? i : i | 1;
    }

    public final void applyLatestDisplayInfo() {
        DisplayInfo displayInfo = this.mOutputDisplayInfo;
        DisplayInfo displayInfo2 = this.mLastDisplayInfo;
        DisplayInfo displayInfo3 = this.mLastWmDisplayInfo;
        DisplayInfoOverrides$DisplayInfoFieldsUpdater displayInfoOverrides$DisplayInfoFieldsUpdater = DEFERRABLE_FIELDS;
        displayInfo.copyFrom(displayInfo2);
        if (displayInfo3 != null) {
            displayInfoOverrides$DisplayInfoFieldsUpdater.setFields(displayInfo, displayInfo3);
        }
        this.mDisplayContent.onDisplayInfoUpdated(this.mOutputDisplayInfo);
    }

    public final void continueScreenUnblocking() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mDisplayContent.mWmService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mShouldWaitForTransitionWhenScreenOn = false;
                this.mDisplayContent.mWmService.mH.removeCallbacks(this.mScreenUnblockTimeoutRunnable);
                Message message = this.mScreenUnblocker;
                if (message == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                message.sendToTarget();
                if (Trace.isTagEnabled(32L)) {
                    Trace.endAsyncSection("Screen unblock: wait for transition", this.mScreenUnblocker.hashCode());
                }
                this.mScreenUnblocker = null;
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final TransitionRequestInfo.DisplayChange getCurrentDisplayChange(int i, Rect rect) {
        DisplayContent displayContent = this.mDisplayContent;
        Rect rect2 = new Rect(0, 0, displayContent.mInitialDisplayWidth, displayContent.mInitialDisplayHeight);
        int i2 = displayContent.mDisplayRotation.mRotation;
        TransitionRequestInfo.DisplayChange displayChange = new TransitionRequestInfo.DisplayChange(displayContent.mDisplayId);
        displayChange.setStartAbsBounds(rect);
        displayChange.setEndAbsBounds(rect2);
        displayChange.setStartRotation(i);
        displayChange.setEndRotation(i2);
        return displayChange;
    }

    @Override // com.android.server.wm.DisplayUpdater
    public final void onDisplayContentDisplayPropertiesPostChanged(int i, int i2, DisplayAreaInfo displayAreaInfo) {
        if (this.mScreenUnblocker == null || this.mDisplayContent.mTransitionController.inTransition()) {
            return;
        }
        this.mScreenUnblocker.sendToTarget();
        this.mScreenUnblocker = null;
    }

    @Override // com.android.server.wm.DisplayUpdater
    public final void onDisplaySwitching(boolean z) {
        this.mShouldWaitForTransitionWhenScreenOn = z;
    }

    public final void onDisplayUpdated(final Transition transition, int i, Rect rect) {
        DisplayContent displayContent = this.mDisplayContent;
        int i2 = displayContent.mDisplayRotation.mRotation;
        TransitionRequestInfo.DisplayChange currentDisplayChange = getCurrentDisplayChange(i, rect);
        currentDisplayChange.setPhysicalDisplayChanged(true);
        DeferredDisplayUpdater$$ExternalSyntheticLambda2 deferredDisplayUpdater$$ExternalSyntheticLambda2 = new DeferredDisplayUpdater$$ExternalSyntheticLambda2(this, 1);
        if (transition.mTransactionCompletedListeners == null) {
            transition.mTransactionCompletedListeners = new ArrayList();
        }
        transition.mTransactionCompletedListeners.add(deferredDisplayUpdater$$ExternalSyntheticLambda2);
        displayContent.mTransitionController.requestStartTransition(transition, null, null, currentDisplayChange);
        if (displayContent.mRemoteDisplayChangeController.performRemoteDisplayChange(i, i2, displayContent.getDisplayAreaInfo(), new RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback() { // from class: com.android.server.wm.DeferredDisplayUpdater$$ExternalSyntheticLambda5
            @Override // com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback
            public final void onContinueRemoteDisplayChange(WindowContainerTransaction windowContainerTransaction) {
                DeferredDisplayUpdater deferredDisplayUpdater = DeferredDisplayUpdater.this;
                if (windowContainerTransaction != null) {
                    deferredDisplayUpdater.mDisplayContent.mAtmService.mWindowOrganizerController.applyTransaction(windowContainerTransaction);
                } else {
                    deferredDisplayUpdater.getClass();
                }
                transition.setAllReady();
            }
        })) {
            return;
        }
        transition.setAllReady();
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.server.wm.DeferredDisplayUpdater$$ExternalSyntheticLambda1] */
    @Override // com.android.server.wm.DisplayUpdater
    public final void updateDisplayInfo(final Runnable runnable) {
        DisplayContent displayContent = this.mDisplayContent;
        displayContent.mWmService.mDisplayManagerInternal.getNonOverrideDisplayInfo(displayContent.mDisplayId, this.mNonOverrideDisplayInfo);
        final DisplayInfo displayInfo = new DisplayInfo(this.mNonOverrideDisplayInfo);
        int calculateDisplayInfoDiff = calculateDisplayInfoDiff(this.mLastDisplayInfo, displayInfo);
        final boolean z = this.mLastDisplayInfo != null ? !Objects.equals(r3.uniqueId, displayInfo.uniqueId) : true;
        this.mLastDisplayInfo = displayInfo;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled;
        if (calculateDisplayInfoDiff == -1 || displayContent.mNeedImmediateDisplayUpdate || !displayContent.mLastHasContent || !displayContent.mTransitionController.isShellTransitionsEnabled()) {
            if (zArr[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -8058211784911995417L, 0, null, null);
            }
            displayContent.mNeedImmediateDisplayUpdate = false;
            this.mLastWmDisplayInfo = displayInfo;
            applyLatestDisplayInfo();
            runnable.run();
            return;
        }
        if ((calculateDisplayInfoDiff & 2) > 0) {
            if (zArr[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 1944392458089872195L, 0, null, null);
            }
            applyLatestDisplayInfo();
        }
        if ((calculateDisplayInfoDiff & 1) <= 0) {
            runnable.run();
            return;
        }
        if (zArr[0]) {
            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 8391643185322408089L, 0, null, null);
        }
        final ?? r2 = new Runnable() { // from class: com.android.server.wm.DeferredDisplayUpdater$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DeferredDisplayUpdater deferredDisplayUpdater = DeferredDisplayUpdater.this;
                DisplayInfo displayInfo2 = displayInfo;
                Runnable runnable2 = runnable;
                deferredDisplayUpdater.mLastWmDisplayInfo = displayInfo2;
                deferredDisplayUpdater.applyLatestDisplayInfo();
                runnable2.run();
            }
        };
        TransitionController transitionController = displayContent.mTransitionController;
        final Transition transition = new Transition(6, 0, transitionController, transitionController.mSyncEngine);
        displayContent.mAtmService.startPowerMode(2);
        displayContent.mTransitionController.startCollectOrQueue(transition, new TransitionController.OnStartCollect() { // from class: com.android.server.wm.DeferredDisplayUpdater$$ExternalSyntheticLambda3
            @Override // com.android.server.wm.TransitionController.OnStartCollect
            public final void onCollectStarted(boolean z2) {
                WindowState windowState;
                DeferredDisplayUpdater deferredDisplayUpdater = DeferredDisplayUpdater.this;
                boolean z3 = z;
                Runnable runnable2 = r2;
                Transition transition2 = transition;
                deferredDisplayUpdater.getClass();
                DisplayContent displayContent2 = deferredDisplayUpdater.mDisplayContent;
                Rect rect = new Rect(0, 0, displayContent2.mInitialDisplayWidth, displayContent2.mInitialDisplayHeight);
                int i = deferredDisplayUpdater.mDisplayContent.mDisplayRotation.mRotation;
                if (Flags.blastSyncNotificationShadeOnDisplaySwitch() && z3 && (windowState = deferredDisplayUpdater.mDisplayContent.mDisplayPolicy.mNotificationShade) != null && windowState.isVisible()) {
                    DisplayContent displayContent3 = deferredDisplayUpdater.mDisplayContent;
                    if (displayContent3.mAtmService.mKeyguardController.isKeyguardOrAodShowing(displayContent3.mDisplayId)) {
                        Slog.i("DeferredDisplayUpdater", windowState + " uses blast for display switch");
                        windowState.mSyncMethodOverride = 1;
                    }
                }
                deferredDisplayUpdater.mDisplayContent.mAtmService.deferWindowLayout();
                try {
                    runnable2.run();
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[0]) {
                        ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -915675022936690176L, 0, null, null);
                    }
                    if (z3) {
                        deferredDisplayUpdater.onDisplayUpdated(transition2, i, rect);
                    } else {
                        deferredDisplayUpdater.mDisplayContent.mTransitionController.requestStartTransition(transition2, null, null, deferredDisplayUpdater.getCurrentDisplayChange(i, rect));
                    }
                    deferredDisplayUpdater.mDisplayContent.mAtmService.continueWindowLayout();
                } catch (Throwable th) {
                    deferredDisplayUpdater.mDisplayContent.mAtmService.continueWindowLayout();
                    throw th;
                }
            }
        });
    }

    @Override // com.android.server.wm.DisplayUpdater
    public final boolean waitForTransition(Message message) {
        if (!Flags.waitForTransitionOnDisplaySwitch() || !this.mShouldWaitForTransitionWhenScreenOn) {
            return false;
        }
        this.mScreenUnblocker = message;
        if (Trace.isTagEnabled(32L)) {
            Trace.beginAsyncSection("Screen unblock: wait for transition", message.hashCode());
        }
        DisplayContent displayContent = this.mDisplayContent;
        WindowManagerService.H h = displayContent.mWmService.mH;
        DeferredDisplayUpdater$$ExternalSyntheticLambda2 deferredDisplayUpdater$$ExternalSyntheticLambda2 = this.mScreenUnblockTimeoutRunnable;
        h.removeCallbacks(deferredDisplayUpdater$$ExternalSyntheticLambda2);
        displayContent.mWmService.mH.postDelayed(deferredDisplayUpdater$$ExternalSyntheticLambda2, 1000L);
        return true;
    }
}
