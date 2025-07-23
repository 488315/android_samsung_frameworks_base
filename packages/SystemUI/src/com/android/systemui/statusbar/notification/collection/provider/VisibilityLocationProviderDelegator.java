package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda12;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
