package com.android.systemui.popup.viewmodel;

import android.content.Context;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.util.PopupUIToastWrapper;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.popup.view.PopupUIAlertDialogFactory;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DataConnectionViewModel_Factory implements Provider {
    private final javax.inject.Provider alertDialogFactoryProvider;
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider intentWrapperProvider;
    private final javax.inject.Provider logWrapperProvider;
    private final javax.inject.Provider toastWrapperProvider;
    private final javax.inject.Provider utilProvider;

    public DataConnectionViewModel_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        this.contextProvider = provider;
        this.toastWrapperProvider = provider2;
        this.logWrapperProvider = provider3;
        this.intentWrapperProvider = provider4;
        this.utilProvider = provider5;
        this.alertDialogFactoryProvider = provider6;
    }

    public static DataConnectionViewModel_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        return new DataConnectionViewModel_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static DataConnectionViewModel newInstance(Context context, PopupUIToastWrapper popupUIToastWrapper, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper, PopupUIUtil popupUIUtil, PopupUIAlertDialogFactory popupUIAlertDialogFactory) {
        return new DataConnectionViewModel(context, popupUIToastWrapper, logWrapper, popupUIIntentWrapper, popupUIUtil, popupUIAlertDialogFactory);
    }

    @Override // javax.inject.Provider
    public DataConnectionViewModel get() {
        return newInstance((Context) this.contextProvider.get(), (PopupUIToastWrapper) this.toastWrapperProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (PopupUIIntentWrapper) this.intentWrapperProvider.get(), (PopupUIUtil) this.utilProvider.get(), (PopupUIAlertDialogFactory) this.alertDialogFactoryProvider.get());
    }
}
