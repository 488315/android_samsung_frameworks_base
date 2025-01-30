package com.android.systemui.statusbar.notification.interruption;

import com.android.internal.util.NotificationMessagingUtil;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HeadsUpViewBinder_Factory implements Provider {
    public final Provider bindStageProvider;
    public final Provider loggerProvider;
    public final Provider notificationMessagingUtilProvider;

    public HeadsUpViewBinder_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.notificationMessagingUtilProvider = provider;
        this.bindStageProvider = provider2;
        this.loggerProvider = provider3;
    }

    public static HeadsUpViewBinder newInstance(NotificationMessagingUtil notificationMessagingUtil, RowContentBindStage rowContentBindStage, HeadsUpViewBinderLogger headsUpViewBinderLogger) {
        return new HeadsUpViewBinder(notificationMessagingUtil, rowContentBindStage, headsUpViewBinderLogger);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new HeadsUpViewBinder((NotificationMessagingUtil) this.notificationMessagingUtilProvider.get(), (RowContentBindStage) this.bindStageProvider.get(), (HeadsUpViewBinderLogger) this.loggerProvider.get());
    }
}
