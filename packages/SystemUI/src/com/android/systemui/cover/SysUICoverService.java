package com.android.systemui.cover;

import android.content.ComponentName;
import android.content.res.Configuration;
import android.os.RemoteException;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.cover.CoverScreenManager;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.cover.PluginCover;
import com.android.systemui.plugins.cover.PluginViewCover;
import com.android.systemui.statusbar.phone.CoverHostImpl;
import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.SemCoverService;

public class SysUICoverService extends SemCoverService {
    public final CoverScreenManager mCoverScreenManager;

    public SysUICoverService(CoverScreenManager coverScreenManager) {
        this.mCoverScreenManager = coverScreenManager;
    }

    public final Object getCoverHost() {
        return this.mCoverScreenManager.mCoverHost;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        PluginCover pluginCover = this.mCoverScreenManager.mCoverPlugin;
        if (pluginCover == null || !(pluginCover instanceof PluginViewCover)) {
            return;
        }
        pluginCover.onConfigurationChanged(configuration);
    }

    public final int onCoverAppCovered(boolean z) {
        CoverScreenManager coverScreenManager = this.mCoverScreenManager;
        CoverHost coverHost = coverScreenManager.mCoverHost;
        if (coverHost == null) {
            return 0;
        }
        Log.d("CoverScreenManager", "onCoverAppCovered() " + z);
        CoverHostImpl coverHostImpl = (CoverHostImpl) coverHost;
        if (z != coverHostImpl.mIsCoverAppCovered) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onCoverAppCovered: covered = ", "CoverHostImpl", z);
            coverHostImpl.mIsCoverAppCovered = z;
            coverHostImpl.updateCoverWindow();
        }
        PluginCover pluginCover = coverScreenManager.mCoverPlugin;
        if (pluginCover != null) {
            pluginCover.onCoverAppCovered(z);
        }
        return z ? 16 : 32;
    }

    public final void onCoverAttached(CoverState coverState) {
        if (coverState == null) {
            return;
        }
        CoverScreenManager coverScreenManager = this.mCoverScreenManager;
        coverScreenManager.getClass();
        Log.d("CoverScreenManager", "onCoverAttached() " + coverState);
        coverScreenManager.mIsAttached = true;
        coverScreenManager.mCoverState = coverState;
        coverScreenManager.initialize(coverState);
        coverScreenManager.mKeyguardUpdateMonitor.registerCallback(coverScreenManager.mCallback);
        CoverHost coverHost = coverScreenManager.mCoverHost;
        if (coverHost != null) {
            ((CoverHostImpl) coverHost).updateCoverState(coverState);
        } else {
            android.util.Log.d("CoverScreenManager", "onCoverAttached: CoverHost is null");
        }
        try {
            IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
            if (windowManagerService != null) {
                windowManagerService.registerDisplayWindowListener(coverScreenManager.mDisplayContainerListener);
            }
        } catch (RemoteException e) {
            android.util.Log.d("CoverScreenManager", "onCoverAttached exception");
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void onCoverDetached() {
        CoverState coverState;
        CoverScreenManager coverScreenManager = this.mCoverScreenManager;
        coverScreenManager.getClass();
        Log.d("CoverScreenManager", "onCoverDetached()");
        CoverHost coverHost = coverScreenManager.mCoverHost;
        if (coverHost == null || (coverState = coverScreenManager.mCoverState) == null) {
            android.util.Log.d("CoverScreenManager", "onCoverDetached: CoverHost is null");
        } else {
            coverState.attached = false;
            coverState.switchState = true;
            ((CoverHostImpl) coverHost).updateCoverState(coverState);
        }
        coverScreenManager.mIsAttached = false;
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            coverScreenManager.removeVirtualDisplay();
            if (LsRune.COVER_ADJUST_REFRESH_RATE) {
                coverScreenManager.updateRefreshRate(false);
            }
        }
        coverScreenManager.removePluginListener();
        coverScreenManager.mKeyguardUpdateMonitor.removeCallback(coverScreenManager.mCallback);
        coverScreenManager.mHandler.removeMessages(1000);
        try {
            IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
            if (windowManagerService != null) {
                windowManagerService.unregisterDisplayWindowListener(coverScreenManager.mDisplayContainerListener);
            }
        } catch (RemoteException unused) {
        }
    }

    public final void onCoverStateUpdated(CoverState coverState) {
        if (coverState == null) {
            return;
        }
        CoverScreenManager coverScreenManager = this.mCoverScreenManager;
        if (coverScreenManager.mScreenLifecycle == null) {
            android.util.Log.d("CoverScreenManager", "onCoverStateUpdated need to register mScreenLifecycle.Observer");
            ScreenLifecycle screenLifecycle = (ScreenLifecycle) Dependency.sDependency.getDependencyInner(ScreenLifecycle.class);
            coverScreenManager.mScreenLifecycle = screenLifecycle;
            if (screenLifecycle != null) {
                screenLifecycle.addObserver(coverScreenManager);
            }
        }
        if (coverScreenManager.mWakefulnessLifecycle == null) {
            android.util.Log.d("CoverScreenManager", "onCoverStateUpdated need to register mWakefulnessLifecycle.Observer");
            WakefulnessLifecycle wakefulnessLifecycle = (WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class);
            coverScreenManager.mWakefulnessLifecycle = wakefulnessLifecycle;
            if (wakefulnessLifecycle != null) {
                wakefulnessLifecycle.addObserver(coverScreenManager);
            }
        }
        Log.d("CoverScreenManager", "onCoverStateUpdated() " + coverState);
        boolean z = LsRune.COVER_VIRTUAL_DISPLAY;
        if (z && !coverState.getVisibleRect().isEmpty() && !coverState.getVisibleRect().equals(coverScreenManager.mCoverState.getVisibleRect()) && (Math.abs(coverState.getVisibleRect().left - coverScreenManager.mCoverState.getVisibleRect().left) > 1 || Math.abs(coverState.getVisibleRect().top - coverScreenManager.mCoverState.getVisibleRect().top) > 1 || Math.abs(coverState.getVisibleRect().right - coverScreenManager.mCoverState.getVisibleRect().right) > 1 || Math.abs(coverState.getVisibleRect().bottom - coverScreenManager.mCoverState.getVisibleRect().bottom) > 1)) {
            android.util.Log.d("CoverScreenManager", "recreateVirtualDisplay() ");
            CoverHomeActivity coverHomeActivity = coverScreenManager.mActivity;
            if (coverHomeActivity != null) {
                coverHomeActivity.finish();
            }
            coverScreenManager.removeVirtualDisplay();
            coverScreenManager.removePluginListener();
            coverScreenManager.mCoverWindowDelegate.detach();
            CoverScreenManager.AnonymousClass2 anonymousClass2 = coverScreenManager.mHandler;
            anonymousClass2.removeMessages(1000);
            anonymousClass2.sendEmptyMessageDelayed(1000, 10000L);
        }
        coverScreenManager.mCoverState = coverState;
        CoverHost coverHost = coverScreenManager.mCoverHost;
        if (coverHost != null) {
            ((CoverHostImpl) coverHost).updateCoverState(coverState);
        } else {
            android.util.Log.d("CoverScreenManager", "onCoverStateUpdated: CoverHost is null");
        }
        PluginCover pluginCover = coverScreenManager.mCoverPlugin;
        if (pluginCover != null) {
            pluginCover.onCoverStateUpdated(coverState);
            if (z) {
                coverScreenManager.prepareCoverWindow();
                return;
            }
            return;
        }
        if (coverScreenManager.mCoverState.attached) {
            android.util.Log.d("CoverScreenManager", "onCoverStateUpdated: addPluginListener");
            coverScreenManager.requestPluginConnection(coverState);
            if (!z || coverScreenManager.mVirtualDisplay == null) {
                return;
            }
            if (coverScreenManager.mCoverState.getSwitchState()) {
                coverScreenManager.mVirtualDisplay.setDisplayState(false);
                return;
            }
            coverScreenManager.mVirtualDisplay.setDisplayState(true);
            if (coverScreenManager.mKeyguardUpdateMonitor.mDeviceProvisioned) {
                coverScreenManager.startCoverHomeActivity(coverScreenManager.mVirtualDisplay.getDisplay());
            }
        }
    }

    public final void onCreate() {
        super.onCreate();
        CoverScreenManager coverScreenManager = this.mCoverScreenManager;
        coverScreenManager.getClass();
        Log.d("CoverScreenManager", "onCreate()");
        if (coverScreenManager.mCoverHost == null) {
            android.util.Log.d("CoverScreenManager", "onCreate() fail to get CoverHost");
            return;
        }
        ScreenLifecycle screenLifecycle = coverScreenManager.mScreenLifecycle;
        if (screenLifecycle != null) {
            screenLifecycle.addObserver(coverScreenManager);
        }
        WakefulnessLifecycle wakefulnessLifecycle = coverScreenManager.mWakefulnessLifecycle;
        if (wakefulnessLifecycle != null) {
            wakefulnessLifecycle.addObserver(coverScreenManager);
        }
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            ComponentName componentName = new ComponentName("com.android.systemui", "com.android.systemui.cover.CoverHomeActivity");
            try {
                if (coverScreenManager.mContext.getPackageManager().getComponentEnabledSetting(componentName) != 1) {
                    coverScreenManager.mContext.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
                }
            } catch (IllegalArgumentException e) {
                android.util.Log.w("CoverScreenManager", "There is no component  " + e.getMessage());
            }
        }
    }

    public final void onDestroy() {
        android.util.Log.d("SysUICoverService", "onDestroy()");
        super.onDestroy();
        CoverScreenManager coverScreenManager = this.mCoverScreenManager;
        coverScreenManager.getClass();
        android.util.Log.d("CoverScreenManager", "onDestroy()");
        ScreenLifecycle screenLifecycle = coverScreenManager.mScreenLifecycle;
        if (screenLifecycle != null) {
            screenLifecycle.removeObserver(coverScreenManager);
        }
        WakefulnessLifecycle wakefulnessLifecycle = coverScreenManager.mWakefulnessLifecycle;
        if (wakefulnessLifecycle != null) {
            wakefulnessLifecycle.removeObserver(coverScreenManager);
        }
    }
}
