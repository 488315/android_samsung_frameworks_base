package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VisibilityLocationProviderDelegator implements VisibilityLocationProvider {
    public VisibilityLocationProvider delegate;

    @Override // com.android.systemui.statusbar.notification.VisibilityLocationProvider
    public final boolean isInVisibleLocation(NotificationEntry notificationEntry) {
        VisibilityLocationProvider visibilityLocationProvider = this.delegate;
        if (visibilityLocationProvider != null) {
            return visibilityLocationProvider.isInVisibleLocation(notificationEntry);
        }
        throw new IllegalArgumentException("delegate not initialized".toString());
    }
}
