package com.android.systemui.statusbar.notification.collection.notifcollection;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class NotifEvent {
    public final String traceName;

    public /* synthetic */ NotifEvent(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    public abstract void dispatchToListener(NotifCollectionListener notifCollectionListener);

    private NotifEvent(String str) {
        this.traceName = str;
    }
}
