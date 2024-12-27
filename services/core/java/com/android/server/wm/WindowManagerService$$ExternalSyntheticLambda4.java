package com.android.server.wm;

import android.os.SystemClock;
import android.view.ViewRootImpl;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class WindowManagerService$$ExternalSyntheticLambda4
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ WindowManagerService$$ExternalSyntheticLambda4(int i, boolean z) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        boolean z = this.f$0;
        switch (i) {
            case 0:
                DisplayPolicy displayPolicy = (DisplayPolicy) obj;
                int i2 = WindowManagerService.MY_PID;
                displayPolicy.getClass();
                boolean z2 = false;
                if (ViewRootImpl.CLIENT_TRANSIENT || ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION) {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    boolean isImmersiveMode =
                            displayPolicy.isImmersiveMode(displayPolicy.mSystemUiControllingWindow);
                    boolean z3 = (displayPolicy.mLastDisableFlags & 23068672) == 23068672;
                    if (!z
                            && elapsedRealtime - displayPolicy.mPanicTime
                                    < displayPolicy.mPanicThresholdMs) {
                        z2 = !displayPolicy.mImmersiveConfirmationWindowExists;
                    } else if (z && isImmersiveMode && !z3) {
                        displayPolicy.mPanicTime = elapsedRealtime;
                    } else {
                        displayPolicy.mPanicTime = 0L;
                    }
                } else {
                    long elapsedRealtime2 = SystemClock.elapsedRealtime();
                    boolean isImmersiveMode2 =
                            displayPolicy.isImmersiveMode(displayPolicy.mSystemUiControllingWindow);
                    boolean z4 = (displayPolicy.mLastDisableFlags & 23068672) == 23068672;
                    ImmersiveModeConfirmation immersiveModeConfirmation =
                            displayPolicy.mImmersiveModeConfirmation;
                    immersiveModeConfirmation.getClass();
                    if (!z
                            && elapsedRealtime2 - immersiveModeConfirmation.mPanicTime
                                    < immersiveModeConfirmation.mPanicThresholdMs) {
                        z2 = immersiveModeConfirmation.mClingWindow == null;
                    } else if (z && isImmersiveMode2 && !z4) {
                        immersiveModeConfirmation.mPanicTime = elapsedRealtime2;
                    } else {
                        immersiveModeConfirmation.mPanicTime = 0L;
                    }
                }
                if (z2) {
                    displayPolicy.mHandler.post(displayPolicy.mHiddenNavPanic);
                    break;
                }
                break;
            case 1:
                int i3 = WindowManagerService.MY_PID;
                ((WindowState) obj).setForceHideNonSystemOverlayWindowIfNeeded(z);
                break;
            default:
                DisplayPolicy displayPolicy2 = (DisplayPolicy) obj;
                displayPolicy2.getClass();
                if (!ViewRootImpl.CLIENT_TRANSIENT && !ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION) {
                    ImmersiveModeConfirmation immersiveModeConfirmation2 =
                            displayPolicy2.mImmersiveModeConfirmation;
                    immersiveModeConfirmation2.getClass();
                    if (z) {
                        ImmersiveModeConfirmation.H h = immersiveModeConfirmation2.mHandler;
                        h.removeMessages(1);
                        h.sendEmptyMessage(2);
                        break;
                    }
                }
                break;
        }
    }
}
