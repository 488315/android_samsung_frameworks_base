package com.android.systemui.popup.view;

import android.content.Context;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.KeyguardUpdateMonitorWrapper;
import com.android.systemui.popup.util.PopupUIUtil;
import dagger.internal.Provider;

public final class PopupUIAlertDialogFactory_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider keyguardUpdateMonitorWrapperProvider;
    private final javax.inject.Provider logWrapperProvider;
    private final javax.inject.Provider popupUIUtilProvider;

    public PopupUIAlertDialogFactory_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        this.contextProvider = provider;
        this.popupUIUtilProvider = provider2;
        this.logWrapperProvider = provider3;
        this.keyguardUpdateMonitorWrapperProvider = provider4;
    }

    public static PopupUIAlertDialogFactory_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new PopupUIAlertDialogFactory_Factory(provider, provider2, provider3, provider4);
    }

    public static PopupUIAlertDialogFactory newInstance(Context context, PopupUIUtil popupUIUtil, LogWrapper logWrapper, KeyguardUpdateMonitorWrapper keyguardUpdateMonitorWrapper) {
        return new PopupUIAlertDialogFactory(context, popupUIUtil, logWrapper, keyguardUpdateMonitorWrapper);
    }

    @Override // javax.inject.Provider
    public PopupUIAlertDialogFactory get() {
        return newInstance((Context) this.contextProvider.get(), (PopupUIUtil) this.popupUIUtilProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (KeyguardUpdateMonitorWrapper) this.keyguardUpdateMonitorWrapperProvider.get());
    }
}
