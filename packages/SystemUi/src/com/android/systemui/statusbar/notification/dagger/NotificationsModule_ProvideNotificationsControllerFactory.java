package com.android.systemui.statusbar.notification.dagger;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationsModule_ProvideNotificationsControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider realControllerProvider;
    public final Provider stubControllerProvider;

    public NotificationsModule_ProvideNotificationsControllerFactory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.realControllerProvider = provider2;
        this.stubControllerProvider = provider3;
    }

    public static NotificationsController provideNotificationsController(Context context, Provider provider, Provider provider2) {
        NotificationsController notificationsController = context.getResources().getBoolean(R.bool.config_renderNotifications) ? (NotificationsController) provider.get() : (NotificationsController) provider2.get();
        Preconditions.checkNotNullFromProvides(notificationsController);
        return notificationsController;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideNotificationsController((Context) this.contextProvider.get(), this.realControllerProvider, this.stubControllerProvider);
    }
}
