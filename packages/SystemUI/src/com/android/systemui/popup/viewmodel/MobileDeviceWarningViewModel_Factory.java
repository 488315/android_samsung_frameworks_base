package com.android.systemui.popup.viewmodel;

import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.util.PopupUIToastWrapper;
import dagger.internal.Provider;

public final class MobileDeviceWarningViewModel_Factory implements Provider {
    private final javax.inject.Provider intentWrapperProvider;
    private final javax.inject.Provider logWrapperProvider;
    private final javax.inject.Provider toastWrapperProvider;

    public MobileDeviceWarningViewModel_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.toastWrapperProvider = provider;
        this.logWrapperProvider = provider2;
        this.intentWrapperProvider = provider3;
    }

    public static MobileDeviceWarningViewModel_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new MobileDeviceWarningViewModel_Factory(provider, provider2, provider3);
    }

    public static MobileDeviceWarningViewModel newInstance(PopupUIToastWrapper popupUIToastWrapper, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper) {
        return new MobileDeviceWarningViewModel(popupUIToastWrapper, logWrapper, popupUIIntentWrapper);
    }

    @Override // javax.inject.Provider
    public MobileDeviceWarningViewModel get() {
        return newInstance((PopupUIToastWrapper) this.toastWrapperProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (PopupUIIntentWrapper) this.intentWrapperProvider.get());
    }
}
