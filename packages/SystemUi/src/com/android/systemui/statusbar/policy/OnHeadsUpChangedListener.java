package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
