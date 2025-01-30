package com.android.systemui.statusbar.notification.row;

import android.os.PowerManager;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RowContentBindStage_Factory implements Provider {
    public final Provider binderProvider;
    public final Provider errorManagerProvider;
    public final Provider loggerProvider;
    public final Provider pmProvider;

    public RowContentBindStage_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.binderProvider = provider;
        this.errorManagerProvider = provider2;
        this.loggerProvider = provider3;
        this.pmProvider = provider4;
    }

    public static RowContentBindStage newInstance(NotificationRowContentBinder notificationRowContentBinder, NotifInflationErrorManager notifInflationErrorManager, RowContentBindStageLogger rowContentBindStageLogger, PowerManager powerManager) {
        return new RowContentBindStage(notificationRowContentBinder, notifInflationErrorManager, rowContentBindStageLogger, powerManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new RowContentBindStage((NotificationRowContentBinder) this.binderProvider.get(), (NotifInflationErrorManager) this.errorManagerProvider.get(), (RowContentBindStageLogger) this.loggerProvider.get(), (PowerManager) this.pmProvider.get());
    }
}
