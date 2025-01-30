package com.android.systemui.pluginlock.listener;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.os.Bundle;
import com.samsung.systemui.splugins.pluginlock.PluginLock;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PluginLockListener$State {
    default boolean isNoUnlockNeed(String str) {
        return false;
    }

    default boolean isSecure() {
        return false;
    }

    default Bundle onUiInfoRequested(boolean z) {
        return null;
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
