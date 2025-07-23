package com.android.systemui.statusbar.notification.headsup;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
