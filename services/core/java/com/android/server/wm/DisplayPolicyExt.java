package com.android.server.wm;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.RemoteException;
import android.view.WindowManager;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.UiThread;
import com.android.server.knox.zt.usertrust.AuthFactorTouchManager;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.wm.CoverPolicy.CoverDisplayPolicy;
import com.android.server.wm.OneHandOpPolicy.OneHandOpMonitor;

import com.samsung.android.rune.CoreRune;

import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DisplayPolicyExt {
    public final AuthFactorTouchManager mAuthFactorTouchManager;
    public final CoverPolicy mCoverPolicy;
    public final DisplayPolicy mDisplayPolicy;
    public WindowState mFakeFocusedWindow;
    public WindowState mGameToolsOverlayWindow;
    public WindowState mGameToolsWindow;
    public final Rect mLastPayHandlerFrame = new Rect();
    public boolean mLastPayHandlerVisible;
    public boolean mNavBarImeBtnAllRotationsAllowed;
    public int mNavigationMode;
    public final OneHandOpPolicy mOneHandOpPolicy;
    public WindowState mPayHandlerWin;
    public final WindowManagerService mService;
    public final TaskbarController mTaskbarController;

    public DisplayPolicyExt(
            Context context,
            WindowManagerService windowManagerService,
            DisplayPolicy displayPolicy) {
        this.mCoverPolicy = null;
        this.mService = windowManagerService;
        this.mDisplayPolicy = displayPolicy;
        if (displayPolicy.mDisplayContent.mDisplayId == 0) {
            OneHandOpPolicy oneHandOpPolicy = new OneHandOpPolicy();
            oneHandOpPolicy.mHandler = new Handler();
            oneHandOpPolicy.mIsOneHandOpEnabled = false;
            oneHandOpPolicy.mHasOneHandOpSpec = false;
            oneHandOpPolicy.mOneHandOpMonitor = oneHandOpPolicy.new OneHandOpMonitor();
            oneHandOpPolicy.mRestartRunnable =
                    new OneHandOpPolicy$$ExternalSyntheticLambda0(oneHandOpPolicy, 0);
            oneHandOpPolicy.mContext = context;
            oneHandOpPolicy.mService = windowManagerService;
            this.mOneHandOpPolicy = oneHandOpPolicy;
        }
        if (WmCoverState.sIsEnabled) {
            DisplayContent displayContent = displayPolicy.mDisplayContent;
            if (displayContent.isDefaultDisplay) {
                CoverPolicy coverPolicy = new CoverPolicy();
                coverPolicy.mCoverWindow = null;
                coverPolicy.mHideSViewCoverWindow = null;
                coverPolicy.mAppsToBeHiddenBySViewCover = new HashSet();
                coverPolicy.mWallpaperTargetMayChange = false;
                coverPolicy.mLastClearCoverState = false;
                coverPolicy.mHandler =
                        new CoverPolicy.CoverPolicyHandler(UiThread.getHandler().getLooper());
                coverPolicy.mDisplayPolicyExt = this;
                coverPolicy.mWindowPolicy = windowManagerService.mPolicy;
                coverPolicy.mDisplayContent = displayContent;
                coverPolicy.mWmService = windowManagerService;
                windowManagerService.mExt.mExtraDisplayPolicy =
                        coverPolicy.new CoverDisplayPolicy();
                this.mCoverPolicy = coverPolicy;
            }
        }
        this.mTaskbarController = new TaskbarController(this);
        this.mAuthFactorTouchManager = AuthFactorTouchManager.getInstance(context);
    }

    public static boolean isUsingBlurEffect(WindowManager.LayoutParams layoutParams) {
        return ((layoutParams.samsungFlags & 64) == 0 || (layoutParams.flags & 2) == 0)
                ? false
                : true;
    }

    public final boolean canBeForceHiddenByAodLw(WindowState windowState) {
        int i;
        int displayId = windowState.getDisplayId();
        boolean z = this.mDisplayPolicy.mDisplayContent.isDefaultDisplay;
        WindowManagerService windowManagerService = this.mService;
        if ((!z && !windowManagerService.mExt.mExtraDisplayPolicy.hasCoverHome(displayId))
                || windowState.mActivityRecord != null
                || (i = windowState.mAttrs.type) == 2000
                || i == 2013
                || i == 2019
                || i == 2040
                || i == 2099) {
            return false;
        }
        if (i != 2415) {
            switch (i) {
            }
            return false;
        }
        if (CoreRune.FW_AOD_FACE_WIDGET) {
            return false;
        }
        if (this.mTaskbarController.mTaskbarWin == windowState) {
            return false;
        }
        windowManagerService.mPolicy.getClass();
        return WindowManagerPolicy.getWindowLayerLw(windowState) < 26;
    }

    public final void notifyRequestedGameToolsWin(boolean z) {
        StatusBarManagerInternal statusBarManagerInternal =
                this.mDisplayPolicy.getStatusBarManagerInternal();
        if (statusBarManagerInternal == null) {
            return;
        }
        StatusBarManagerService.AnonymousClass2 anonymousClass2 =
                (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "notifyRequestedGameToolsWin attached=", "StatusBarManagerService", z);
        if (StatusBarManagerService.this.mBar != null) {
            try {
                StatusBarManagerService.this.mBar.notifyRequestedGameToolsWin(z);
            } catch (RemoteException unused) {
            }
        }
    }
}
