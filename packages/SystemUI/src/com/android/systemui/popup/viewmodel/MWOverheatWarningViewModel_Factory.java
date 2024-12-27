package com.android.systemui.popup.viewmodel;

import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.view.PopupUIAlertDialogFactory;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MWOverheatWarningViewModel_Factory implements Provider {
    private final javax.inject.Provider alertDialogFactoryProvider;
    private final javax.inject.Provider intentWrapperProvider;
    private final javax.inject.Provider logWrapperProvider;

    public MWOverheatWarningViewModel_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.alertDialogFactoryProvider = provider;
        this.logWrapperProvider = provider2;
        this.intentWrapperProvider = provider3;
    }

    public static MWOverheatWarningViewModel_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new MWOverheatWarningViewModel_Factory(provider, provider2, provider3);
    }

    public static MWOverheatWarningViewModel newInstance(PopupUIAlertDialogFactory popupUIAlertDialogFactory, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper) {
        return new MWOverheatWarningViewModel(popupUIAlertDialogFactory, logWrapper, popupUIIntentWrapper);
    }

    @Override // javax.inject.Provider
    public MWOverheatWarningViewModel get() {
        return newInstance((PopupUIAlertDialogFactory) this.alertDialogFactoryProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (PopupUIIntentWrapper) this.intentWrapperProvider.get());
    }
}
