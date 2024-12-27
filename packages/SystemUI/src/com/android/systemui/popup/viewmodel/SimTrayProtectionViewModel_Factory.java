package com.android.systemui.popup.viewmodel;

import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.view.PopupUIAlertDialogFactory;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SimTrayProtectionViewModel_Factory implements Provider {
    private final javax.inject.Provider dialogFactoryProvider;
    private final javax.inject.Provider intentWrapperProvider;
    private final javax.inject.Provider logWrapperProvider;
    private final javax.inject.Provider wakefulnessLifecycleProvider;

    public SimTrayProtectionViewModel_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        this.dialogFactoryProvider = provider;
        this.logWrapperProvider = provider2;
        this.intentWrapperProvider = provider3;
        this.wakefulnessLifecycleProvider = provider4;
    }

    public static SimTrayProtectionViewModel_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new SimTrayProtectionViewModel_Factory(provider, provider2, provider3, provider4);
    }

    public static SimTrayProtectionViewModel newInstance(PopupUIAlertDialogFactory popupUIAlertDialogFactory, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper, WakefulnessLifecycle wakefulnessLifecycle) {
        return new SimTrayProtectionViewModel(popupUIAlertDialogFactory, logWrapper, popupUIIntentWrapper, wakefulnessLifecycle);
    }

    @Override // javax.inject.Provider
    public SimTrayProtectionViewModel get() {
        return newInstance((PopupUIAlertDialogFactory) this.dialogFactoryProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (PopupUIIntentWrapper) this.intentWrapperProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get());
    }
}
