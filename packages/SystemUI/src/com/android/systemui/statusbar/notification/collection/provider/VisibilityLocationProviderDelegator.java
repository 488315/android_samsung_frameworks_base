package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
