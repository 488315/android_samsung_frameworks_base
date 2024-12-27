package com.android.systemui.statusbar.notification.collection.notifcollection;

import kotlin.jvm.internal.DefaultConstructorMarker;

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
