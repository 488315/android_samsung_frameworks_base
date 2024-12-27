package com.android.server.wm;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayContent f$0;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda1(
            int i, DisplayContent displayContent) {
        this.$r8$classId = i;
        this.f$0 = displayContent;
    }

    private final void accept$com$android$server$wm$DisplayContent$$ExternalSyntheticLambda37(
            Object obj) {
        DisplayContent displayContent = this.f$0;
        WindowState windowState = (WindowState) obj;
        displayContent.getClass();
        if (windowState.mLayoutAttached) {
            if ((windowState.mViewVisibility == 8 || !windowState.mRelayoutCalled)
                    && windowState.mHaveFrame
                    && !windowState.mLayoutNeeded) {
                return;
            }
            if (displayContent.mTmpInitial) {
                windowState.mWindowFrames.mContentChanged = false;
            }
            windowState.mSurfacePlacementNeeded = true;
            windowState.mLayoutNeeded = false;
            displayContent.mDisplayPolicy.layoutWindowLw(
                    windowState, windowState.getParentWindow(), displayContent.mDisplayFrames);
            windowState.mLayoutSeq = displayContent.mLayoutSeq;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:115:0x03a7, code lost:

       if (r2 > com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MAX_SCALE) goto L252;
    */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0335, code lost:

       if (r2 > com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MAX_SCALE) goto L202;
    */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x05f3, code lost:

       if (r2.alpha == com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MAX_SCALE) goto L341;
    */
    /* JADX WARN: Code restructure failed: missing block: B:405:0x0805, code lost:

       if (r0.mTargetWindowTokens.containsKey(r7.mToken) != false) goto L449;
    */
    /* JADX WARN: Removed duplicated region for block: B:107:0x042a  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0434  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x03a1  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x03b6  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0323  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x074f  */
    /* JADX WARN: Removed duplicated region for block: B:377:0x084a  */
    /* JADX WARN: Removed duplicated region for block: B:385:0x07bc  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x07d0  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x02cf  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0364  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x036e  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0384  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void accept$com$android$server$wm$DisplayContent$$ExternalSyntheticLambda40(
            java.lang.Object r24) {
        /*
            Method dump skipped, instructions count: 2164
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayContent$$ExternalSyntheticLambda1.accept$com$android$server$wm$DisplayContent$$ExternalSyntheticLambda40(java.lang.Object):void");
    }

    private final void accept$com$android$server$wm$DisplayContent$$ExternalSyntheticLambda41(
            Object obj) {
        DisplayContent displayContent = this.f$0;
        DeviceStateController.DeviceState deviceState = (DeviceStateController.DeviceState) obj;
        PhysicalDisplaySwitchTransitionLauncher physicalDisplaySwitchTransitionLauncher =
                displayContent.mDisplaySwitchTransitionLauncher;
        DeviceStateController.DeviceState deviceState2 =
                physicalDisplaySwitchTransitionLauncher.mDeviceState;
        DeviceStateController.DeviceState deviceState3 = DeviceStateController.DeviceState.FOLDED;
        DeviceStateController.DeviceState deviceState4 = DeviceStateController.DeviceState.OPEN;
        DeviceStateController.DeviceState deviceState5 =
                DeviceStateController.DeviceState.HALF_FOLDED;
        if (deviceState2 == deviceState3
                && (deviceState == deviceState5 || deviceState == deviceState4)) {
            physicalDisplaySwitchTransitionLauncher.mShouldRequestTransitionOnDisplaySwitch = true;
        } else if (deviceState != deviceState5 && deviceState != deviceState4) {
            physicalDisplaySwitchTransitionLauncher.mShouldRequestTransitionOnDisplaySwitch = false;
        }
        physicalDisplaySwitchTransitionLauncher.mDeviceState = deviceState;
        DisplayRotation displayRotation = displayContent.mDisplayRotation;
        if (displayRotation.mFoldController != null) {
            synchronized (displayRotation.mLock) {
                displayRotation.mFoldController.foldStateChanged(deviceState);
            }
        }
    }

    private final void accept$com$android$server$wm$DisplayContent$$ExternalSyntheticLambda46(
            Object obj) {
        DisplayContent displayContent = this.f$0;
        WindowState windowState = (WindowState) obj;
        displayContent.getClass();
        WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
        ActivityRecord activityRecord = windowState.mActivityRecord;
        if (windowStateAnimator.mDrawState == 3) {
            if ((activityRecord == null || activityRecord.canShowWindows())
                    && windowState.performShowLocked()) {
                displayContent.pendingLayoutChanges |= 8;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:111:0x0205, code lost:

       if (r15 == false) goto L133;
    */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x021e, code lost:

       if (r12.mHideSViewCoverWindow != null) goto L155;
    */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0220, code lost:

       r14 = r1.getCoverMode();
    */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0224, code lost:

       if (r14 == 0) goto L155;
    */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x022a, code lost:

       if (r1.canShowWhenLocked() == false) goto L155;
    */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x022c, code lost:

       r15 = r1.mActivityRecord;
    */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x022e, code lost:

       if (r15 == null) goto L152;
    */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x0230, code lost:

       r15 = r15.token;
    */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0232, code lost:

       if (r14 != 1) goto L144;
    */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0234, code lost:

       r12.mAppsToBeHiddenBySViewCover.remove(r15);
    */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x023a, code lost:

       if (r14 != 2) goto L150;
    */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x023e, code lost:

       if (r1.mShouldHideSViewOnce == false) goto L152;
    */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0242, code lost:

       if (r1.mDisableHideSViewOnce != false) goto L152;
    */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0244, code lost:

       r12.mAppsToBeHiddenBySViewCover.remove(r15);
    */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x024a, code lost:

       if (r15 == null) goto L152;
    */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x024c, code lost:

       r12.mAppsToBeHiddenBySViewCover.add(r15);
    */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0257, code lost:

       if (r12.mAppsToBeHiddenBySViewCover.isEmpty() == false) goto L155;
    */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x0259, code lost:

       r12.mHideSViewCoverWindow = r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x021a, code lost:

       if (r1.getTask().isAnimatingByRecents() == false) goto L133;
    */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x04db, code lost:

       if (r6.isAodShowing() != false) goto L348;
    */
    /* JADX WARN: Code restructure failed: missing block: B:347:0x066b, code lost:

       if (r7 != false) goto L494;
    */
    /* JADX WARN: Code restructure failed: missing block: B:548:0x03ba, code lost:

       if (com.android.server.policy.WindowManagerPolicy.getWindowLayerLw(r1) <= com.android.server.policy.WindowManagerPolicy.getWindowLayerFromTypeLw(2099)) goto L266;
    */
    /* JADX WARN: Code restructure failed: missing block: B:586:0x0467, code lost:

       if (r7 == false) goto L325;
    */
    /* JADX WARN: Code restructure failed: missing block: B:622:0x03ab, code lost:

       if (r9 != 2631) goto L264;
    */
    /* JADX WARN: Code restructure failed: missing block: B:641:0x019d, code lost:

       if ((r1.mStartingData instanceof com.android.server.wm.SnapshotStartingData) == false) goto L94;
    */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x017a, code lost:

       if (r11 == false) goto L94;
    */
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:150:0x028c  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x02f5  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x046e  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x051a  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0541  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x056a  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x05da  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x05e9  */
    /* JADX WARN: Removed duplicated region for block: B:320:0x061d  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x0630  */
    /* JADX WARN: Removed duplicated region for block: B:327:0x0636  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x07c6  */
    /* JADX WARN: Removed duplicated region for block: B:433:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:512:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:537:0x035e  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01dd  */
    /* JADX WARN: Type inference failed for: r0v117 */
    /* JADX WARN: Type inference failed for: r0v30 */
    /* JADX WARN: Type inference failed for: r0v31, types: [boolean] */
    @Override // java.util.function.Consumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void accept(java.lang.Object r19) {
        /*
            Method dump skipped, instructions count: 2530
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayContent$$ExternalSyntheticLambda1.accept(java.lang.Object):void");
    }
}
