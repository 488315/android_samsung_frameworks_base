package com.android.systemui.pluginlock;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import com.android.systemui.pluginlock.component.PluginHomeWallpaper;
import com.android.systemui.pluginlock.component.PluginLockStatusBarCallback;
import com.android.systemui.pluginlock.component.PluginLockSwipe;
import com.android.systemui.pluginlock.component.PluginLockWallpaper;
import com.android.systemui.pluginlock.listener.KeyguardListener;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.util.function.Consumer;

public interface PluginLockMediator {
    void addDump(String str);

    int getCurrentScreenType();

    String getDynamicLockData();

    String getLockStarItemLocationInfo(String str);

    PluginHomeWallpaper getPluginHomeWallpaper();

    PluginLockSwipe getPluginLockSwipe();

    PluginLockWallpaper getPluginLockWallpaper();

    int getSecureMode();

    void goToLockedShade();

    boolean isDynamicLockEnabled();

    boolean isHomeWallpaperRequired(int i);

    boolean isRotateMenuHide();

    boolean isSecure();

    boolean isTouchAndHoldToEditEnabled();

    boolean isTouchConsumablePosition(float f, float f2);

    boolean isWindowSecured();

    void makeExpandedInvisible();

    void onAodTransitionEnd();

    void onBarStateChanged();

    void onBarStateChanged(int i);

    void onDataCleared();

    void onDensityOrFontScaleChanged();

    void onEventReceived(Bundle bundle);

    void onFolderStateChanged(boolean z, boolean z2);

    void onReady();

    void onRootViewAttached(ViewGroup viewGroup);

    void onRootViewDetached();

    void onUserActivity();

    void onViewModeChanged(int i);

    void onWallpaperChanged(int i);

    void recoverItem(int i);

    void registerStateCallback(PluginLockListener.State state);

    void registerStatusBarCallback(PluginLockStatusBarCallback pluginLockStatusBarCallback);

    void registerUpdateMonitor();

    void registerWindowListener(PluginLockListener.Window window);

    void removeStateCallback(PluginLockListener.State state);

    void requestDismissKeyguard(Intent intent);

    void resetConfigs();

    void resetDynamicLock();

    void resetDynamicLockData(boolean z);

    void resetItem(int i, boolean z);

    void setDynamicLockData(String str);

    void setEnabled(boolean z);

    void setInstanceState(int i, PluginLockInstanceState pluginLockInstanceState);

    void setKeyguardBasicListener(KeyguardListener.Basic basic);

    void setKeyguardSPluginListener(KeyguardListener.SPlugin sPlugin);

    void setKeyguardUserSwitchListener(KeyguardListener.UserSwitch userSwitch);

    void setLockscreenEnabled(boolean z);

    void setLockscreenTimer(long j);

    void setNoSensorConsumer(Consumer<Boolean> consumer);

    void setPluginLock(PluginLock pluginLock);

    void setPluginLockItem(PluginLockInstanceState pluginLockInstanceState);

    void setPluginWallpaper(int i, int i2, int i3, String str);

    void setPluginWallpaper(int i, int i2, int i3, String str, String str2);

    void setPluginWallpaper(int i, int i2, String str);

    void setPluginWallpaperHint(int i, String str);

    void setPluginWallpaperHint(String str);

    void setPluginWallpaperState(int i, int i2);

    void setQsExpansion(float f);

    void setScreenOrientation(boolean z, boolean z2);

    void setScreenTypeChanged(int i);

    void unregisterUpdateMonitor();

    void updateBiometricRecognition(boolean z);

    void updateDynamicLockData(String str);

    void updateOverlayUserTimeout(boolean z);

    void updateWindowSecureState(boolean z);
}
