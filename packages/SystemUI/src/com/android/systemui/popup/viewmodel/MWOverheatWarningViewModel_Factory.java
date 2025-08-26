package com.android.systemui.popup.viewmodel;

import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.view.PopupUIAlertDialogFactory;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes2.dex */
public final class MWOverheatWarningViewModel_Factory implements Provider {
    private final Provider alertDialogFactoryProvider;
    private final Provider intentWrapperProvider;
    private final Provider logWrapperProvider;

    public MWOverheatWarningViewModel_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.alertDialogFactoryProvider = provider;
        this.logWrapperProvider = provider2;
        this.intentWrapperProvider = provider3;
    }

    public static MWOverheatWarningViewModel_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new MWOverheatWarningViewModel_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static MWOverheatWarningViewModel newInstance(PopupUIAlertDialogFactory popupUIAlertDialogFactory, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper) {
        return new MWOverheatWarningViewModel(popupUIAlertDialogFactory, logWrapper, popupUIIntentWrapper);
    }

    public static MWOverheatWarningViewModel_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new MWOverheatWarningViewModel_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public MWOverheatWarningViewModel get() {
        return newInstance((PopupUIAlertDialogFactory) this.alertDialogFactoryProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (PopupUIIntentWrapper) this.intentWrapperProvider.get());
    }
}
