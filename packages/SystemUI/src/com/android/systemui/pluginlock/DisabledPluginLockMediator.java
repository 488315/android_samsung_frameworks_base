package com.android.systemui.pluginlock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import com.android.systemui.pluginlock.component.PluginHomeWallpaper;
import com.android.systemui.pluginlock.component.PluginLockStatusBarCallback;
import com.android.systemui.pluginlock.component.PluginLockSwipe;
import com.android.systemui.pluginlock.component.PluginLockWallpaper;
import com.android.systemui.pluginlock.listener.KeyguardListener;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.util.function.Consumer;

public class DisabledPluginLockMediator implements PluginLockMediator, SPluginListener<PluginLock> {
    public DisabledPluginLockMediator() {
        Log.i("DisabledPluginLockMediator", "## DisabledPluginLockMediator ##, " + this);
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public int getCurrentScreenType() {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public String getDynamicLockData() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public String getLockStarItemLocationInfo(String str) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public PluginHomeWallpaper getPluginHomeWallpaper() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public PluginLockSwipe getPluginLockSwipe() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public PluginLockWallpaper getPluginLockWallpaper() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public int getSecureMode() {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isDynamicLockEnabled() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isHomeWallpaperRequired(int i) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isRotateMenuHide() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isSecure() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isTouchAndHoldToEditEnabled() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isTouchConsumablePosition(float f, float f2) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isWindowSecured() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onBarStateChanged() {
    }

    @Override // com.samsung.systemui.splugins.SPluginListener
    public void onPluginConnected(PluginLock pluginLock, Context context) {
    }

    @Override // com.samsung.systemui.splugins.SPluginListener
    public void onPluginDisconnected(PluginLock pluginLock, int i) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginWallpaper(int i, int i2, int i3, String str) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginWallpaperHint(int i, String str) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onBarStateChanged(int i) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginWallpaper(int i, int i2, int i3, String str, String str2) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginWallpaperHint(String str) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginWallpaper(int i, int i2, String str) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void goToLockedShade() {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void makeExpandedInvisible() {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onAodTransitionEnd() {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onDataCleared() {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onDensityOrFontScaleChanged() {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onReady() {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onRootViewDetached() {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onUserActivity() {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void registerUpdateMonitor() {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void resetConfigs() {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void resetDynamicLock() {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void unregisterUpdateMonitor() {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void addDump(String str) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onEventReceived(Bundle bundle) {
    }

    @Override // com.samsung.systemui.splugins.SPluginListener
    public void onPluginLoadFailed(int i) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onRootViewAttached(ViewGroup viewGroup) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onViewModeChanged(int i) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onWallpaperChanged(int i) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void recoverItem(int i) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void registerStateCallback(PluginLockListener.State state) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void registerStatusBarCallback(PluginLockStatusBarCallback pluginLockStatusBarCallback) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void registerWindowListener(PluginLockListener.Window window) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void removeStateCallback(PluginLockListener.State state) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void requestDismissKeyguard(Intent intent) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void resetDynamicLockData(boolean z) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setDynamicLockData(String str) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setEnabled(boolean z) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setKeyguardBasicListener(KeyguardListener.Basic basic) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setKeyguardSPluginListener(KeyguardListener.SPlugin sPlugin) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setKeyguardUserSwitchListener(KeyguardListener.UserSwitch userSwitch) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setLockscreenEnabled(boolean z) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setLockscreenTimer(long j) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setNoSensorConsumer(Consumer<Boolean> consumer) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginLock(PluginLock pluginLock) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginLockItem(PluginLockInstanceState pluginLockInstanceState) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setQsExpansion(float f) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setScreenTypeChanged(int i) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void updateBiometricRecognition(boolean z) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void updateDynamicLockData(String str) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void updateOverlayUserTimeout(boolean z) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void updateWindowSecureState(boolean z) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onFolderStateChanged(boolean z, boolean z2) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void resetItem(int i, boolean z) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setInstanceState(int i, PluginLockInstanceState pluginLockInstanceState) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginWallpaperState(int i, int i2) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setScreenOrientation(boolean z, boolean z2) {
    }
}
