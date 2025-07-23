package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
