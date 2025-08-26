package com.android.systemui.facewidget.plugin;

import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class FaceWidgetNotificationControllerWrapper_Factory implements Provider {
    public final Provider activityStarterProvider;
    public final Provider mPanelViewControllerLazyProvider;
    public final Provider notifiCollectionProvider;

    public FaceWidgetNotificationControllerWrapper_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.notifiCollectionProvider = provider;
        this.activityStarterProvider = provider2;
        this.mPanelViewControllerLazyProvider = provider3;
    }

    public static FaceWidgetNotificationControllerWrapper newInstance(NotifCollection notifCollection, ActivityStarter activityStarter) {
        return new FaceWidgetNotificationControllerWrapper(notifCollection, activityStarter);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = new FaceWidgetNotificationControllerWrapper((NotifCollection) this.notifiCollectionProvider.get(), (ActivityStarter) this.activityStarterProvider.get());
        faceWidgetNotificationControllerWrapper.mPanelViewControllerLazy = DoubleCheck.lazy(this.mPanelViewControllerLazyProvider);
        return faceWidgetNotificationControllerWrapper;
    }
}
