package com.android.systemui.statusbar.notification.headsup;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* loaded from: classes3.dex */
public interface OnHeadsUpChangedListener {
    default void onHeadsUpAnimatingAwayEnded(NotificationEntry notificationEntry) {
    }

    default void onHeadsUpPinned(NotificationEntry notificationEntry) {
    }

    default void onHeadsUpPinnedModeChanged(boolean z) {
    }

    default void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
    }

    default void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
    }
}
