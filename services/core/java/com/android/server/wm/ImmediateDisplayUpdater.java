package com.android.server.wm;

import android.R;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.DisplayInfo;
import android.window.DisplayAreaInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ImmediateDisplayUpdater implements DisplayUpdater {
    public final DisplayContent mDisplayContent;
    public final DisplayInfo mDisplayInfo;

    public ImmediateDisplayUpdater(DisplayContent displayContent) {
        DisplayInfo displayInfo = new DisplayInfo();
        this.mDisplayInfo = displayInfo;
        this.mDisplayContent = displayContent;
        displayInfo.copyFrom(displayContent.mDisplayInfo);
    }

    @Override // com.android.server.wm.DisplayUpdater
    public final void onDisplayContentDisplayPropertiesPostChanged(
            int i, int i2, DisplayAreaInfo displayAreaInfo) {
        Transition transition;
        final PhysicalDisplaySwitchTransitionLauncher physicalDisplaySwitchTransitionLauncher =
                this.mDisplayContent.mDisplaySwitchTransitionLauncher;
        if (physicalDisplaySwitchTransitionLauncher.mTransition == null
                || physicalDisplaySwitchTransitionLauncher.mDisplayContent
                        .mRemoteDisplayChangeController.performRemoteDisplayChange(
                        i,
                        i2,
                        displayAreaInfo,
                        new RemoteDisplayChangeController
                                .ContinueRemoteDisplayChangeCallback() { // from class:
                                                                         // com.android.server.wm.PhysicalDisplaySwitchTransitionLauncher$$ExternalSyntheticLambda0
                            @Override // com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback
                            public final void onContinueRemoteDisplayChange(
                                    WindowContainerTransaction windowContainerTransaction) {
                                PhysicalDisplaySwitchTransitionLauncher
                                        physicalDisplaySwitchTransitionLauncher2 =
                                                PhysicalDisplaySwitchTransitionLauncher.this;
                                if (physicalDisplaySwitchTransitionLauncher2.mTransition == null) {
                                    return;
                                }
                                if (windowContainerTransaction != null) {
                                    physicalDisplaySwitchTransitionLauncher2.mAtmService
                                            .mWindowOrganizerController.applyTransaction(
                                            windowContainerTransaction);
                                }
                                Transition transition2 =
                                        physicalDisplaySwitchTransitionLauncher2.mTransition;
                                if (transition2 == null) {
                                    return;
                                }
                                transition2.setAllReady();
                                physicalDisplaySwitchTransitionLauncher2.mTransition = null;
                            }
                        })
                || (transition = physicalDisplaySwitchTransitionLauncher.mTransition) == null) {
            return;
        }
        transition.setAllReady();
        physicalDisplaySwitchTransitionLauncher.mTransition = null;
    }

    @Override // com.android.server.wm.DisplayUpdater
    public final void onDisplayContentDisplayPropertiesPreChanged(
            int i, int i2, int i3, int i4, int i5) {
        PhysicalDisplaySwitchTransitionLauncher physicalDisplaySwitchTransitionLauncher =
                this.mDisplayContent.mDisplaySwitchTransitionLauncher;
        if (physicalDisplaySwitchTransitionLauncher.mShouldRequestTransitionOnDisplaySwitch) {
            TransitionController transitionController =
                    physicalDisplaySwitchTransitionLauncher.mTransitionController;
            if (transitionController.isShellTransitionsEnabled()) {
                DisplayContent displayContent =
                        physicalDisplaySwitchTransitionLauncher.mDisplayContent;
                if (displayContent.mLastHasContent
                        && physicalDisplaySwitchTransitionLauncher
                                .mContext
                                .getResources()
                                .getBoolean(R.bool.config_useSystemProvidedLauncherForSecondary)
                        && ValueAnimator.areAnimatorsEnabled()) {
                    physicalDisplaySwitchTransitionLauncher.mTransition = null;
                    if (transitionController.isCollecting()) {
                        Transition transition = transitionController.mCollectingTransition;
                        physicalDisplaySwitchTransitionLauncher.mTransition = transition;
                        transition.collect(displayContent, false);
                        physicalDisplaySwitchTransitionLauncher.mTransition.setReady(
                                displayContent, false);
                        physicalDisplaySwitchTransitionLauncher.mTransition.addFlag(
                                EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION);
                        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[0]) {
                            ProtoLogImpl_54989576.d(
                                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                                    5106303602270682056L,
                                    0,
                                    null,
                                    null);
                        }
                    } else {
                        TransitionRequestInfo.DisplayChange displayChange =
                                new TransitionRequestInfo.DisplayChange(i);
                        displayChange.setStartAbsBounds(new Rect(0, 0, i2, i3));
                        displayChange.setEndAbsBounds(new Rect(0, 0, i4, i5));
                        displayChange.setPhysicalDisplayChanged(true);
                        Transition createTransition = transitionController.createTransition(6, 0);
                        transitionController.requestStartTransition(
                                createTransition, null, null, displayChange);
                        Rect startAbsBounds = displayChange.getStartAbsBounds();
                        Rect endAbsBounds = displayChange.getEndAbsBounds();
                        if (startAbsBounds != null && endAbsBounds != null) {
                            TransitionController.setDisplaySyncMethod(
                                    startAbsBounds, endAbsBounds, displayContent);
                        }
                        physicalDisplaySwitchTransitionLauncher.mTransition = createTransition;
                        createTransition.collect(displayContent, false);
                    }
                    if (physicalDisplaySwitchTransitionLauncher.mTransition != null) {
                        physicalDisplaySwitchTransitionLauncher.mAtmService.startPowerMode(2);
                    }
                    physicalDisplaySwitchTransitionLauncher
                                    .mShouldRequestTransitionOnDisplaySwitch =
                            false;
                }
            }
        }
    }

    @Override // com.android.server.wm.DisplayUpdater
    public final void updateDisplayInfo(Runnable runnable) {
        DisplayContent displayContent = this.mDisplayContent;
        displayContent.mWmService.mDisplayManagerInternal.getNonOverrideDisplayInfo(
                displayContent.mDisplayId, this.mDisplayInfo);
        displayContent.onDisplayInfoUpdated(this.mDisplayInfo);
        runnable.run();
    }
}
