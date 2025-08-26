package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
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
