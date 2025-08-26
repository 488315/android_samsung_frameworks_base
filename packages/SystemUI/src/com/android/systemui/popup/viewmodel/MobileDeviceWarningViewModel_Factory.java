package com.android.systemui.popup.viewmodel;

import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.util.PopupUIToastWrapper;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes2.dex */
public final class MobileDeviceWarningViewModel_Factory implements Provider {
    private final Provider intentWrapperProvider;
    private final Provider logWrapperProvider;
    private final Provider toastWrapperProvider;

    public MobileDeviceWarningViewModel_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.toastWrapperProvider = provider;
        this.logWrapperProvider = provider2;
        this.intentWrapperProvider = provider3;
    }

    public static MobileDeviceWarningViewModel_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new MobileDeviceWarningViewModel_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static MobileDeviceWarningViewModel newInstance(PopupUIToastWrapper popupUIToastWrapper, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper) {
        return new MobileDeviceWarningViewModel(popupUIToastWrapper, logWrapper, popupUIIntentWrapper);
    }

    public static MobileDeviceWarningViewModel_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new MobileDeviceWarningViewModel_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public MobileDeviceWarningViewModel get() {
        return newInstance((PopupUIToastWrapper) this.toastWrapperProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (PopupUIIntentWrapper) this.intentWrapperProvider.get());
    }
}
