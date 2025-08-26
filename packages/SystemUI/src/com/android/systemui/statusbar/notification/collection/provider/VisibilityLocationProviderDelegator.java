package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda12;

/* loaded from: classes3.dex */
public final class VisibilityLocationProviderDelegator implements VisibilityLocationProvider {
    public NotificationStackScrollLayoutController$$ExternalSyntheticLambda12 delegate;

    @Override // com.android.systemui.statusbar.notification.VisibilityLocationProvider
    public final boolean isInVisibleLocation(NotificationEntry notificationEntry) {
        NotificationStackScrollLayoutController$$ExternalSyntheticLambda12 notificationStackScrollLayoutController$$ExternalSyntheticLambda12 = this.delegate;
        if (notificationStackScrollLayoutController$$ExternalSyntheticLambda12 != null) {
            return notificationStackScrollLayoutController$$ExternalSyntheticLambda12.isInVisibleLocation(notificationEntry);
        }
        throw new IllegalArgumentException("delegate not initialized");
    }
}
