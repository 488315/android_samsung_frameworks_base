package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class NotifBindPipeline_Factory implements Provider {
    public final Provider collectionProvider;
    public final Provider loggerProvider;
    public final Provider processorFactoryProvider;

    public NotifBindPipeline_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.collectionProvider = provider;
        this.loggerProvider = provider2;
        this.processorFactoryProvider = provider3;
    }

    public static NotifBindPipeline newInstance(CommonNotifCollection commonNotifCollection, NotifBindPipelineLogger notifBindPipelineLogger, NotificationEntryProcessorFactoryLooperImpl notificationEntryProcessorFactoryLooperImpl) {
        return new NotifBindPipeline(commonNotifCollection, notifBindPipelineLogger, notificationEntryProcessorFactoryLooperImpl);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new NotifBindPipeline((CommonNotifCollection) this.collectionProvider.get(), (NotifBindPipelineLogger) this.loggerProvider.get(), (NotificationEntryProcessorFactory) this.processorFactoryProvider.get());
    }
}
