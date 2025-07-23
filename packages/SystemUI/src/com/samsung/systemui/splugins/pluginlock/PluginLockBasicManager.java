package com.samsung.systemui.splugins.pluginlock;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface PluginLockBasicManager {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        void dispatchEvent(Bundle bundle);

        String getDynamicLockData();

        void goToLockedShade();

        boolean isSecure();

        void makeExpandedInvisible();

        void onLaunchTransitionFadingEnded();

        void requestDismissKeyguard(Intent intent);

        void setBiometricRecognition(boolean z);

        void setDynamicLockData(String str);

        void setLockscreenTimer(long j);

        void setPluginLockWallpaper(int i, int i2, String str);

        void setPluginWallpaper(int i, int i2, int i3, String str);

        void setPluginWallpaper(int i, int i2, int i3, String str, String str2);

        void setPluginWallpaperHints(int i, String str);

        @Deprecated
        void setRotationAllowed(boolean z);

        void setScreenOrientation(boolean z, boolean z2);

        void setTimeOut(boolean z);

        void setViewMode(int i);

        void setWallpaperHints(String str);

        void updateDynamicLockData(String str);

        void updateWindowSecureState(boolean z);

        void userActivity();
    }

    int getMode();

    int getServiceType();

    boolean hasBackupWallpaper(int i);

    boolean isTouchConsumablePosition(float f, float f2);

    void onAodTransitionEnd();

    void onDensityOrFontScaleChanged();

    void onEventReceived(Bundle bundle);

    void onFolderStateChanged(boolean z);

    void onFolderStateChanged(boolean z, boolean z2);

    void onLocaleChanged();

    void onLockWallpaperChanged(int i);

    void onPluginLockModeChanged(int i, boolean z);

    void onPluginLockModeChanged(boolean z);

    void onStartedGoingToSleep(boolean z);

    void onStartedWakingUp();

    void onWallpaperConsumed(int i, boolean z);

    void reset();

    void setAllowedNumber(int i);

    void setBarState(int i);

    void setCallback(Callback callback);

    void setPanelView(ViewGroup viewGroup);

    void setQsExpansion(float f);

    void updateWhiteWallpaperState(boolean z);

    @Deprecated
    default void onSemBackupStatusChanged(Bundle bundle) {
    }
}
