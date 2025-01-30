package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifRemoteViewCacheImpl_Factory implements Provider {
    public final Provider collectionProvider;

    public NotifRemoteViewCacheImpl_Factory(Provider provider) {
        this.collectionProvider = provider;
    }

    public static NotifRemoteViewCacheImpl newInstance(CommonNotifCollection commonNotifCollection) {
        return new NotifRemoteViewCacheImpl(commonNotifCollection);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new NotifRemoteViewCacheImpl((CommonNotifCollection) this.collectionProvider.get());
    }
}
