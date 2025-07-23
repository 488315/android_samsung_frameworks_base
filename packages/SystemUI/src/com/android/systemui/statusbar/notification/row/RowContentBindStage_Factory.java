package com.android.systemui.statusbar.notification.row;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RowContentBindStage_Factory implements Provider {
    public final Provider binderProvider;
    public final Provider errorManagerProvider;
    public final Provider loggerProvider;

    public RowContentBindStage_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.binderProvider = provider;
        this.errorManagerProvider = provider2;
        this.loggerProvider = provider3;
    }

    public static RowContentBindStage newInstance(NotificationRowContentBinder notificationRowContentBinder, NotifInflationErrorManager notifInflationErrorManager, RowContentBindStageLogger rowContentBindStageLogger) {
        return new RowContentBindStage(notificationRowContentBinder, notifInflationErrorManager, rowContentBindStageLogger);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new RowContentBindStage((NotificationRowContentBinder) this.binderProvider.get(), (NotifInflationErrorManager) this.errorManagerProvider.get(), (RowContentBindStageLogger) this.loggerProvider.get());
    }
}
