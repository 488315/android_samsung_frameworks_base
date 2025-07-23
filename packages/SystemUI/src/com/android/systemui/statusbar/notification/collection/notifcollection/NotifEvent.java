package com.android.systemui.statusbar.notification.collection.notifcollection;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
