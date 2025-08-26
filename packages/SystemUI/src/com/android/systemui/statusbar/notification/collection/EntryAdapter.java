package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;

/* loaded from: classes3.dex */
public interface EntryAdapter {
    void endLifetimeExtension(NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback, NotifLifetimeExtender notifLifetimeExtender);

    String getKey();
}
