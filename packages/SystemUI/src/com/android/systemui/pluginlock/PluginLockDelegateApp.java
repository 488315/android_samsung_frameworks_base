package com.android.systemui.pluginlock;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.listener.KeyguardListener;
import com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PluginLockDelegateApp implements KeyguardListener.Basic {
    private static final String DEF_PACKAGE = "com.android.systemui";
    private static final String TAG = "PluginLockDelegateApp";
    private PluginLockBasicManager mBasicManager;
    private ViewGroup mRootView;
    private final PluginLockUtils mUtils;

    public PluginLockDelegateApp(PluginLockUtils pluginLockUtils) {
        Log.d(TAG, "PluginLockDelegateApp, " + this);
        this.mUtils = pluginLockUtils;
    }

    private ViewGroup findPanelView() {
        View findViewById = this.mRootView.findViewById(this.mRootView.getResources().getIdentifier("notification_panel", "id", DEF_PACKAGE));
        int childCount = this.mRootView.getChildCount();
        int i = 0;
        while (i < childCount && findViewById != this.mRootView.getChildAt(i)) {
            i++;
        }
        return (ViewGroup) this.mRootView.getChildAt(i);
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public boolean hasBackupWallpaper(int i) {
        Log.d(TAG, "hasBackupWallpaper :" + this.mBasicManager + ", screen = " + i);
        PluginLockBasicManager pluginLockBasicManager = this.mBasicManager;
        if (pluginLockBasicManager != null) {
            return pluginLockBasicManager.hasBackupWallpaper(i);
        }
        return false;
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public boolean isTouchConsumablePosition(float f, float f2) {
        PluginLockBasicManager pluginLockBasicManager = this.mBasicManager;
        if (pluginLockBasicManager != null) {
            return pluginLockBasicManager.isTouchConsumablePosition(f, f2);
        }
        return false;
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public void onAodTransitionEnd() {
        PluginLockBasicManager pluginLockBasicManager = this.mBasicManager;
        if (pluginLockBasicManager != null) {
            pluginLockBasicManager.onAodTransitionEnd();
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public void onBarStateChanged(int i) {
        Log.d(TAG, "onBarStateChanged ");
        PluginLockBasicManager pluginLockBasicManager = this.mBasicManager;
        if (pluginLockBasicManager != null) {
            pluginLockBasicManager.setBarState(i);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public void onDensityOrFontScaleChanged() {
        PluginLockBasicManager pluginLockBasicManager = this.mBasicManager;
        if (pluginLockBasicManager != null) {
            pluginLockBasicManager.onDensityOrFontScaleChanged();
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public void onEventReceived(Bundle bundle) {
        PluginLockBasicManager pluginLockBasicManager = this.mBasicManager;
        if (pluginLockBasicManager != null) {
            pluginLockBasicManager.onEventReceived(bundle);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public void onFolderStateChanged(boolean z, boolean z2) {
        PluginLockBasicManager pluginLockBasicManager = this.mBasicManager;
        if (pluginLockBasicManager != null) {
            try {
                pluginLockBasicManager.onFolderStateChanged(z, z2);
            } catch (AbstractMethodError e) {
                Log.w(TAG, "onFolderStateChanged, " + e.getMessage());
                this.mBasicManager.onFolderStateChanged(z);
            }
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public void onLocaleChanged() {
        PluginLockBasicManager pluginLockBasicManager = this.mBasicManager;
        if (pluginLockBasicManager != null) {
            pluginLockBasicManager.onLocaleChanged();
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public void onRootViewAttached(ViewGroup viewGroup) {
        Log.d(TAG, "onRootViewAttached");
        this.mRootView = viewGroup;
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public void onStartedGoingToSleep(int i, boolean z) {
        Log.d(TAG, "onStartedGoingToSleep enabled: " + this.mUtils.isLockScreenEnabled() + " aodClockTransition : " + z);
        PluginLockBasicManager pluginLockBasicManager = this.mBasicManager;
        if (pluginLockBasicManager != null) {
            pluginLockBasicManager.onStartedGoingToSleep(z);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public void onStartedWakingUp() {
        PluginLockBasicManager pluginLockBasicManager = this.mBasicManager;
        if (pluginLockBasicManager != null) {
            pluginLockBasicManager.onStartedWakingUp();
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public void onWallpaperChanged(int i) {
        Log.d(TAG, "onWallpaperChanged :" + this.mBasicManager);
        PluginLockBasicManager pluginLockBasicManager = this.mBasicManager;
        if (pluginLockBasicManager != null) {
            pluginLockBasicManager.onLockWallpaperChanged(i);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public void onWallpaperConsumed(int i, boolean z) {
        StringBuilder sb = new StringBuilder("onWallpaperConsumed :");
        sb.append(this.mBasicManager);
        sb.append(", screen = ");
        sb.append(i);
        sb.append(", updateColor = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, TAG);
        PluginLockBasicManager pluginLockBasicManager = this.mBasicManager;
        if (pluginLockBasicManager != null) {
            pluginLockBasicManager.onWallpaperConsumed(i, z);
        }
    }

    public void setBasicManager(PluginLockBasicManager pluginLockBasicManager) {
        Log.d(TAG, "setBasicManager, " + pluginLockBasicManager);
        this.mBasicManager = pluginLockBasicManager;
        if (pluginLockBasicManager != null) {
            setPanelView(pluginLockBasicManager);
        }
    }

    public void setPanelView(PluginLockBasicManager pluginLockBasicManager) {
        ViewGroup viewGroup = this.mRootView;
        if (viewGroup == null) {
            Log.e(TAG, "setPanelView failed. mRootView is null.");
            return;
        }
        View findViewById = this.mRootView.findViewById(viewGroup.getResources().getIdentifier("notification_panel", "id", DEF_PACKAGE));
        int childCount = this.mRootView.getChildCount();
        int i = 0;
        while (i < childCount && findViewById != this.mRootView.getChildAt(i)) {
            i++;
        }
        ViewGroup viewGroup2 = (ViewGroup) this.mRootView.getChildAt(i);
        Log.d(TAG, "setPanelView :" + viewGroup2);
        if (viewGroup2 != null) {
            pluginLockBasicManager.setPanelView(viewGroup2);
        } else {
            Log.e(TAG, "setPanelView failed. panelView is null.");
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.Basic
    public void setQsExpansion(float f) {
        PluginLockBasicManager pluginLockBasicManager = this.mBasicManager;
        if (pluginLockBasicManager != null) {
            pluginLockBasicManager.setQsExpansion(f);
        }
    }
}
