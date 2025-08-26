package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class HeadsUpViewBinder_Factory implements Provider {
    public final Provider bindStageProvider;
    public final Provider loggerProvider;

    public HeadsUpViewBinder_Factory(Provider provider, Provider provider2) {
        this.bindStageProvider = provider;
        this.loggerProvider = provider2;
    }

    public static HeadsUpViewBinder newInstance(RowContentBindStage rowContentBindStage, HeadsUpViewBinderLogger headsUpViewBinderLogger) {
        return new HeadsUpViewBinder(rowContentBindStage, headsUpViewBinderLogger);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new HeadsUpViewBinder((RowContentBindStage) this.bindStageProvider.get(), (HeadsUpViewBinderLogger) this.loggerProvider.get());
    }
}
