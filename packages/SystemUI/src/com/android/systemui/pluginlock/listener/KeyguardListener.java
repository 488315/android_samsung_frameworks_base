package com.android.systemui.pluginlock.listener;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import com.samsung.android.cover.CoverState;
import com.samsung.systemui.splugins.pluginlock.PluginLock;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface KeyguardListener {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Basic {
        boolean hasBackupWallpaper(int i);

        boolean isTouchConsumablePosition(float f, float f2);

        void onAodTransitionEnd();

        void onBarStateChanged(int i);

        void onDensityOrFontScaleChanged();

        void onEventReceived(Bundle bundle);

        void onFolderStateChanged(boolean z, boolean z2);

        void onLocaleChanged();

        void onRootViewAttached(ViewGroup viewGroup);

        void onStartedGoingToSleep(int i, boolean z);

        void onStartedWakingUp();

        void onWallpaperChanged(int i);

        void onWallpaperConsumed(int i, boolean z);

        void setQsExpansion(float f);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface UserSwitch {
        void onUserSwitchComplete(int i);

        void onUserSwitching(int i);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SPlugin {
        void onCoverStateChanged(CoverState coverState);

        void onPluginConnected(PluginLock pluginLock, Context context);

        void onRootViewAttached(ViewGroup viewGroup);

        default void onPluginDisconnected(PluginLock pluginLock, int i) {
        }
    }
}
