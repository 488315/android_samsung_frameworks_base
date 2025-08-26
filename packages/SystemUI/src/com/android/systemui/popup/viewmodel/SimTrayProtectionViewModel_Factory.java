package com.android.systemui.popup.viewmodel;

import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.view.PopupUIAlertDialogFactory;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes2.dex */
public final class SimTrayProtectionViewModel_Factory implements Provider {
    private final Provider dialogFactoryProvider;
    private final Provider intentWrapperProvider;
    private final Provider logWrapperProvider;
    private final Provider wakefulnessLifecycleProvider;

    public SimTrayProtectionViewModel_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.dialogFactoryProvider = provider;
        this.logWrapperProvider = provider2;
        this.intentWrapperProvider = provider3;
        this.wakefulnessLifecycleProvider = provider4;
    }

    public static SimTrayProtectionViewModel_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new SimTrayProtectionViewModel_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4));
    }

    public static SimTrayProtectionViewModel newInstance(PopupUIAlertDialogFactory popupUIAlertDialogFactory, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper, WakefulnessLifecycle wakefulnessLifecycle) {
        return new SimTrayProtectionViewModel(popupUIAlertDialogFactory, logWrapper, popupUIIntentWrapper, wakefulnessLifecycle);
    }

    public static SimTrayProtectionViewModel_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new SimTrayProtectionViewModel_Factory(provider, provider2, provider3, provider4);
    }

    @Override // javax.inject.Provider
    public SimTrayProtectionViewModel get() {
        return newInstance((PopupUIAlertDialogFactory) this.dialogFactoryProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (PopupUIIntentWrapper) this.intentWrapperProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get());
    }
}
