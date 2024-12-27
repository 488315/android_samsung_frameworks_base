package com.android.systemui.pluginlock.listener;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import com.samsung.android.cover.CoverState;
import com.samsung.systemui.splugins.pluginlock.PluginLock;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface KeyguardListener {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface UserSwitch {
        void onUserSwitchComplete(int i);

        void onUserSwitching(int i);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface SPlugin {
        void onCoverStateChanged(CoverState coverState);

        void onPluginConnected(PluginLock pluginLock, Context context);

        void onRootViewAttached(ViewGroup viewGroup);

        default void onPluginDisconnected(PluginLock pluginLock, int i) {
        }
    }
}
