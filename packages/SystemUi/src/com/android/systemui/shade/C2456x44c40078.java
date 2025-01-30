package com.android.systemui.shade;

import android.app.SemWallpaperColors;
import android.os.SystemClock;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$pluginLockListener$1 */
/* loaded from: classes2.dex */
public final class C2456x44c40078 {
    public final /* synthetic */ SecNotificationShadeWindowControllerHelperImpl this$0;

    public C2456x44c40078(SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl) {
        this.this$0 = secNotificationShadeWindowControllerHelperImpl;
    }

    public final void onScreenTimeoutChanged(long j) {
        Log.m138d("NotificationShadeWindowController", "onScreenTimeoutChanged timeOut : " + j);
        String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = this.this$0;
        secNotificationShadeWindowControllerHelperImpl.getCurrentState().lockTimeOutValue = j;
        NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
        long userActivityTimeout = secNotificationShadeWindowControllerHelperImpl.getUserActivityTimeout();
        if (currentState.keyguardUserActivityTimeout != userActivityTimeout) {
            currentState.keyguardUserActivityTimeout = userActivityTimeout;
            if (currentState.statusBarState == 1) {
                secNotificationShadeWindowControllerHelperImpl.apply(currentState);
            }
        }
    }

    public final void onViewModeChanged(int i) {
        Log.m138d("NotificationShadeWindowController", "onViewModeChanged mode : " + i);
        boolean z = i == 1;
        if (LsRune.LOCKUI_BLUR) {
            SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = this.this$0;
            if (z) {
                Log.m138d("NotificationShadeWindowController", "prepareToApplyBlurDimEffect");
                String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
                WindowManager.LayoutParams layoutParamsChanged = secNotificationShadeWindowControllerHelperImpl.getLayoutParamsChanged();
                layoutParamsChanged.flags |= 2;
                layoutParamsChanged.dimAmount = 0.125f;
                layoutParamsChanged.samsungFlags |= 64;
            } else {
                Log.m138d("NotificationShadeWindowController", "prepareToRemoveBlurDimEffect");
                String str2 = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
                WindowManager.LayoutParams layoutParamsChanged2 = secNotificationShadeWindowControllerHelperImpl.getLayoutParamsChanged();
                layoutParamsChanged2.flags &= -3;
                layoutParamsChanged2.dimAmount = 0.0f;
                layoutParamsChanged2.samsungFlags &= -65;
            }
        }
        updateBiometricRecognition(z);
        updateOverlayUserTimeout(z);
    }

    public final void onViewModePageChanged(SemWallpaperColors semWallpaperColors) {
        if (semWallpaperColors != null) {
            boolean z = semWallpaperColors.get(256L).getFontColor() == 1;
            ViewGroup viewGroup = this.this$0.notificationShadeView;
            if (viewGroup != null) {
                int systemUiVisibility = viewGroup.getSystemUiVisibility();
                viewGroup.setSystemUiVisibility(z ? systemUiVisibility | 16 : systemUiVisibility & (-17));
            }
        }
    }

    public final void updateBiometricRecognition(boolean z) {
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = this.this$0;
        secNotificationShadeWindowControllerHelperImpl.powerManager.userActivity(SystemClock.uptimeMillis(), true);
        secNotificationShadeWindowControllerHelperImpl.keyguardUpdateMonitor.dispatchDlsBiometricMode(z);
    }

    public final void updateOverlayUserTimeout(boolean z) {
        String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = this.this$0;
        NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
        currentState.userScreenTimeOut = z;
        secNotificationShadeWindowControllerHelperImpl.apply(currentState);
    }
}
