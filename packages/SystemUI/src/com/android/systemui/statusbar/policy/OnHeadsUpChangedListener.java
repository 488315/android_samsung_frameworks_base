package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

public interface OnHeadsUpChangedListener {
    default void onHeadsUpPinned(NotificationEntry notificationEntry) {
    }

    default void onHeadsUpPinnedModeChanged(boolean z) {
    }

    default void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
    }

    default void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
    }
}
