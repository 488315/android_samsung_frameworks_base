package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PreparationCoordinator_Factory implements Provider {
    private final javax.inject.Provider adjustmentProvider;
    private final javax.inject.Provider bindEventManagerProvider;
    private final javax.inject.Provider errorManagerProvider;
    private final javax.inject.Provider loggerProvider;
    private final javax.inject.Provider notifInflaterProvider;
    private final javax.inject.Provider serviceProvider;
    private final javax.inject.Provider viewBarnProvider;

    public PreparationCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7) {
        this.loggerProvider = provider;
        this.notifInflaterProvider = provider2;
        this.errorManagerProvider = provider3;
        this.viewBarnProvider = provider4;
        this.adjustmentProvider = provider5;
        this.serviceProvider = provider6;
        this.bindEventManagerProvider = provider7;
    }

    public static PreparationCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7) {
        return new PreparationCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static PreparationCoordinator newInstance(PreparationCoordinatorLogger preparationCoordinatorLogger, NotifInflater notifInflater, NotifInflationErrorManager notifInflationErrorManager, NotifViewBarn notifViewBarn, NotifUiAdjustmentProvider notifUiAdjustmentProvider, IStatusBarService iStatusBarService, BindEventManagerImpl bindEventManagerImpl) {
        return new PreparationCoordinator(preparationCoordinatorLogger, notifInflater, notifInflationErrorManager, notifViewBarn, notifUiAdjustmentProvider, iStatusBarService, bindEventManagerImpl);
    }

    @Override // javax.inject.Provider
    public PreparationCoordinator get() {
        return newInstance((PreparationCoordinatorLogger) this.loggerProvider.get(), (NotifInflater) this.notifInflaterProvider.get(), (NotifInflationErrorManager) this.errorManagerProvider.get(), (NotifViewBarn) this.viewBarnProvider.get(), (NotifUiAdjustmentProvider) this.adjustmentProvider.get(), (IStatusBarService) this.serviceProvider.get(), (BindEventManagerImpl) this.bindEventManagerProvider.get());
    }
}
