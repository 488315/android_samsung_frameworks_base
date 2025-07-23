package com.android.systemui.popup.viewmodel;

import android.content.Context;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.util.PopupUIToastWrapper;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.popup.view.PopupUIAlertDialogFactory;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DataConnectionViewModel_Factory implements Provider {
    private final Provider alertDialogFactoryProvider;
    private final Provider contextProvider;
    private final Provider intentWrapperProvider;
    private final Provider logWrapperProvider;
    private final Provider toastWrapperProvider;
    private final Provider utilProvider;

    public DataConnectionViewModel_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.toastWrapperProvider = provider2;
        this.logWrapperProvider = provider3;
        this.intentWrapperProvider = provider4;
        this.utilProvider = provider5;
        this.alertDialogFactoryProvider = provider6;
    }

    public static DataConnectionViewModel_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        return new DataConnectionViewModel_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6));
    }

    public static DataConnectionViewModel newInstance(Context context, PopupUIToastWrapper popupUIToastWrapper, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper, PopupUIUtil popupUIUtil, PopupUIAlertDialogFactory popupUIAlertDialogFactory) {
        return new DataConnectionViewModel(context, popupUIToastWrapper, logWrapper, popupUIIntentWrapper, popupUIUtil, popupUIAlertDialogFactory);
    }

    public static DataConnectionViewModel_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new DataConnectionViewModel_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    @Override // javax.inject.Provider
    public DataConnectionViewModel get() {
        return newInstance((Context) this.contextProvider.get(), (PopupUIToastWrapper) this.toastWrapperProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (PopupUIIntentWrapper) this.intentWrapperProvider.get(), (PopupUIUtil) this.utilProvider.get(), (PopupUIAlertDialogFactory) this.alertDialogFactoryProvider.get());
    }
}
