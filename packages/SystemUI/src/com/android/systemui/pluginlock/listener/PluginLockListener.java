package com.android.systemui.pluginlock.listener;

import android.app.PendingIntent;
import android.app.SemWallpaperColors;
import android.content.ComponentName;
import android.os.Bundle;
import com.samsung.systemui.splugins.pluginlock.PluginLock;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface PluginLockListener {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Window {
        void onScreenOrientationChangeRequired(boolean z);

        void onScreenTimeoutChanged(long j);

        void onViewModeChanged(int i);

        void onViewModePageChanged(SemWallpaperColors semWallpaperColors);

        void updateBiometricRecognition(boolean z);

        void updateOverlayUserTimeout(boolean z);

        void updateWindowSecureState(boolean z);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface State {
        default int getMaxKeyguardNotifications(int i) {
            return 1;
        }

        default boolean isNoUnlockNeed(String str) {
            return false;
        }

        default boolean isSecure() {
            return false;
        }

        default Bundle onUiInfoRequested(boolean z) {
            return null;
        }

        default void onBarStateChanged(int i) {
        }

        default void onClockChanged(Bundle bundle) {
        }

        default void onLockStarEnabled(boolean z) {
        }

        default void onMusicChanged(Bundle bundle) {
        }

        default void onUnNeedLockAppStarted(ComponentName componentName) {
        }

        default void onViewModeChanged(int i) {
        }

        default void setDynamicLockData(String str) {
        }

        default void setPluginLock(PluginLock pluginLock) {
        }

        default void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent) {
        }

        default void updateDynamicLockData(String str) {
        }

        default void goToLockedShade() {
        }

        default void makeExpandedInvisible() {
        }

        default void onPluginLockReset() {
        }

        default void onUserActivity() {
        }

        default void resetDynamicLock() {
        }
    }
}
