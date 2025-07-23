package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.notification.row.icon.AppIconProvider;
import com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProvider;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PreparationCoordinator_Factory implements Provider {
    private final Provider adjustmentProvider;
    private final Provider appIconProvider;
    private final Provider bindEventManagerProvider;
    private final Provider errorManagerProvider;
    private final Provider loggerProvider;
    private final Provider notifInflaterProvider;
    private final Provider notificationIconStyleProvider;
    private final Provider ongoingActivityControllerProvider;
    private final Provider serviceProvider;
    private final Provider viewBarnProvider;

    public PreparationCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        this.loggerProvider = provider;
        this.notifInflaterProvider = provider2;
        this.errorManagerProvider = provider3;
        this.viewBarnProvider = provider4;
        this.adjustmentProvider = provider5;
        this.serviceProvider = provider6;
        this.bindEventManagerProvider = provider7;
        this.appIconProvider = provider8;
        this.notificationIconStyleProvider = provider9;
        this.ongoingActivityControllerProvider = provider10;
    }

    public static PreparationCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10) {
        return new PreparationCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8), Providers.asDaggerProvider(provider9), Providers.asDaggerProvider(provider10));
    }

    public static PreparationCoordinator newInstance(PreparationCoordinatorLogger preparationCoordinatorLogger, NotifInflater notifInflater, NotifInflationErrorManager notifInflationErrorManager, NotifViewBarn notifViewBarn, NotifUiAdjustmentProvider notifUiAdjustmentProvider, IStatusBarService iStatusBarService, BindEventManagerImpl bindEventManagerImpl, AppIconProvider appIconProvider, NotificationIconStyleProvider notificationIconStyleProvider, OngoingActivityController ongoingActivityController) {
        return new PreparationCoordinator(preparationCoordinatorLogger, notifInflater, notifInflationErrorManager, notifViewBarn, notifUiAdjustmentProvider, iStatusBarService, bindEventManagerImpl, appIconProvider, notificationIconStyleProvider, ongoingActivityController);
    }

    public static PreparationCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        return new PreparationCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    @Override // javax.inject.Provider
    public PreparationCoordinator get() {
        return newInstance((PreparationCoordinatorLogger) this.loggerProvider.get(), (NotifInflater) this.notifInflaterProvider.get(), (NotifInflationErrorManager) this.errorManagerProvider.get(), (NotifViewBarn) this.viewBarnProvider.get(), (NotifUiAdjustmentProvider) this.adjustmentProvider.get(), (IStatusBarService) this.serviceProvider.get(), (BindEventManagerImpl) this.bindEventManagerProvider.get(), (AppIconProvider) this.appIconProvider.get(), (NotificationIconStyleProvider) this.notificationIconStyleProvider.get(), (OngoingActivityController) this.ongoingActivityControllerProvider.get());
    }
}
