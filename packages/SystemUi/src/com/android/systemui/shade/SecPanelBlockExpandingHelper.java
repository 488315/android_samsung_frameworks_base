package com.android.systemui.shade;

import android.util.Log;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecPanelBlockExpandingHelper {
    public final CentralSurfaces mCentralSurfaces;
    public final CommandQueue mCommandQueue;
    public final KeyguardStateController mKeyguardStateController;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public final NavigationBarController mNavigationBarController;
    public final StatusBarStateController mStatusBarStateController;
    public final StatusBarWindowController mStatusBarWindowController;

    public SecPanelBlockExpandingHelper(CentralSurfaces centralSurfaces, CommandQueue commandQueue, NavigationBarController navigationBarController, StatusBarWindowController statusBarWindowController, KnoxStateMonitor knoxStateMonitor, KeyguardTouchAnimator keyguardTouchAnimator, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController) {
        this.mCentralSurfaces = centralSurfaces;
        this.mCommandQueue = commandQueue;
        this.mNavigationBarController = navigationBarController;
        this.mStatusBarWindowController = statusBarWindowController;
        this.mKnoxStateMonitor = knoxStateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mStatusBarStateController = statusBarStateController;
    }

    public final boolean isBlockedByKnoxPanel() {
        EdmMonitor edmMonitor;
        KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) this.mKnoxStateMonitor;
        CustomSdkMonitor customSdkMonitor = knoxStateMonitorImpl.mCustomSdkMonitor;
        boolean z = !((customSdkMonitor != null && customSdkMonitor.mStatusBarNotificationsState) || !((edmMonitor = knoxStateMonitorImpl.mEdmMonitor) == null || !edmMonitor.mStatusBarExpandAllowed || edmMonitor.mIsStatusBarHidden));
        if (z) {
            Log.d("SecPanelBlockExpandingHelper", "KnoxStateMonitor.isPanelExpandEnabled() is true");
        }
        return z;
    }

    public final boolean isDisabledExpandingOnKeyguard() {
        if (isBlockedByKnoxPanel()) {
            return true;
        }
        boolean z = false;
        boolean z2 = this.mStatusBarStateController.getState() == 0;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z3 = keyguardStateControllerImpl.mShowing;
        boolean z4 = keyguardStateControllerImpl.mOccluded;
        boolean z5 = (((CentralSurfacesImpl) this.mCentralSurfaces).mDisabled1 & QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE) != 0;
        if (!z2 && ((z3 || z4) && z5)) {
            z = true;
        }
        if (z) {
            StringBuilder sb = new StringBuilder("isDisabledExpandingOnKeyguard: !isShadeState[");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, !z2, "] && (isShowing[", z3, "] || isOcculleded[");
            sb.append(z4);
            sb.append("]) && disabledByFlag[");
            sb.append(z5);
            sb.append("]");
            Log.d("SecPanelBlockExpandingHelper", sb.toString());
        }
        return z;
    }
}
