package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

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
