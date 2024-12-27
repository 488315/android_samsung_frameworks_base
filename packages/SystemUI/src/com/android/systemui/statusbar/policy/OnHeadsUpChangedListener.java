package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
