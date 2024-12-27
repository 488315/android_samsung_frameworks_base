package com.android.server.wm;

import android.view.ViewRootImpl;

import java.util.function.Consumer;

public final /* synthetic */ class WindowManagerService$$ExternalSyntheticLambda7
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WindowManagerService$$ExternalSyntheticLambda7(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((DisplayContent) obj).reconfigureDisplayLocked();
                break;
            case 1:
                ((DisplayPolicy) obj).systemReady();
                break;
            case 2:
                DisplayContent displayContent = (DisplayContent) obj;
                int i = WindowManagerService.MY_PID;
                boolean z = displayContent.mDisplayPolicy.mHasNavigationBar;
                DisplayPolicy displayPolicy = displayContent.mDisplayPolicy;
                displayPolicy.updateCurrentUserResources();
                displayPolicy.mDisplayContent.requestDisplayUpdate(
                        new DisplayPolicy$$ExternalSyntheticLambda2(displayPolicy, 2));
                if (z != displayContent.mDisplayPolicy.mHasNavigationBar) {
                    displayContent.reconfigureDisplayLocked();
                    break;
                }
                break;
            case 3:
                WindowContainer windowContainer = (WindowContainer) obj;
                int i2 = WindowManagerService.MY_PID;
                if (windowContainer.isAnimating(1, 1)) {
                    windowContainer.cancelAnimation();
                    break;
                }
                break;
            case 4:
                DisplayPolicy displayPolicy2 = (DisplayPolicy) obj;
                int i3 = WindowManagerService.MY_PID;
                displayPolicy2.getClass();
                if (!ViewRootImpl.CLIENT_TRANSIENT && !ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION) {
                    displayPolicy2.mImmersiveModeConfirmation.getClass();
                    break;
                }
                break;
            default:
                DisplayPolicy displayPolicy3 = (DisplayPolicy) obj;
                displayPolicy3.mLastDisableFlags = 0;
                displayPolicy3.updateSystemBarAttributes();
                break;
        }
    }
}
