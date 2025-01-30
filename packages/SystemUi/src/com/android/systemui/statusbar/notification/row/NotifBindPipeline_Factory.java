package com.android.systemui.statusbar.notification.row;

import android.os.Looper;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifBindPipeline_Factory implements Provider {
    public final Provider collectionProvider;
    public final Provider loggerProvider;
    public final Provider mainLooperProvider;

    public NotifBindPipeline_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.collectionProvider = provider;
        this.loggerProvider = provider2;
        this.mainLooperProvider = provider3;
    }

    public static NotifBindPipeline newInstance(CommonNotifCollection commonNotifCollection, NotifBindPipelineLogger notifBindPipelineLogger, Looper looper) {
        return new NotifBindPipeline(commonNotifCollection, notifBindPipelineLogger, looper);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new NotifBindPipeline((CommonNotifCollection) this.collectionProvider.get(), (NotifBindPipelineLogger) this.loggerProvider.get(), (Looper) this.mainLooperProvider.get());
    }
}
