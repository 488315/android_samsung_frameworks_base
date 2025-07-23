package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface EntryAdapter {
    void endLifetimeExtension(NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback, NotifLifetimeExtender notifLifetimeExtender);

    String getKey();
}
