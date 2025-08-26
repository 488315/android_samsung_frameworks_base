package com.android.systemui.statusbar.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class CentralSurfacesDependenciesModule_ProvideNotificationMediaManagerFactory implements Provider {
    public final Provider backgroundExecutorProvider;
    public final Provider contextProvider;
    public final Provider dumpManagerProvider;
    public final Provider handlerProvider;
    public final Provider mediaDataManagerProvider;
    public final Provider notifCollectionProvider;
    public final Provider notifPipelineProvider;
    public final Provider visibilityProvider;

    public CentralSurfacesDependenciesModule_ProvideNotificationMediaManagerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.visibilityProvider = provider2;
        this.notifPipelineProvider = provider3;
        this.notifCollectionProvider = provider4;
        this.mediaDataManagerProvider = provider5;
        this.dumpManagerProvider = provider6;
        this.backgroundExecutorProvider = provider7;
        this.handlerProvider = provider8;
    }

    public static NotificationMediaManager provideNotificationMediaManager(Context context, NotificationVisibilityProvider notificationVisibilityProvider, NotifPipeline notifPipeline, NotifCollection notifCollection, MediaDataManager mediaDataManager, DumpManager dumpManager, Executor executor, Handler handler) {
        return new NotificationMediaManager(context, notificationVisibilityProvider, notifPipeline, notifCollection, mediaDataManager, dumpManager, executor, handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new NotificationMediaManager((Context) this.contextProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (NotifCollection) this.notifCollectionProvider.get(), (MediaDataManager) this.mediaDataManagerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (Handler) this.handlerProvider.get());
    }
}
