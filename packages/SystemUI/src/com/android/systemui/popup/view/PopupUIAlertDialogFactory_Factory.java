package com.android.systemui.popup.view;

import android.content.Context;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.DisplayManagerWrapper;
import com.android.systemui.popup.util.KeyguardUpdateMonitorWrapper;
import com.android.systemui.popup.util.PopupUIUtil;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes2.dex */
public final class PopupUIAlertDialogFactory_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider displayManagerWrapperProvider;
    private final Provider keyguardUpdateMonitorWrapperProvider;
    private final Provider logWrapperProvider;
    private final Provider popupUIUtilProvider;

    public PopupUIAlertDialogFactory_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.popupUIUtilProvider = provider2;
        this.logWrapperProvider = provider3;
        this.keyguardUpdateMonitorWrapperProvider = provider4;
        this.displayManagerWrapperProvider = provider5;
    }

    public static PopupUIAlertDialogFactory_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new PopupUIAlertDialogFactory_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5));
    }

    public static PopupUIAlertDialogFactory newInstance(Context context, PopupUIUtil popupUIUtil, LogWrapper logWrapper, KeyguardUpdateMonitorWrapper keyguardUpdateMonitorWrapper, DisplayManagerWrapper displayManagerWrapper) {
        return new PopupUIAlertDialogFactory(context, popupUIUtil, logWrapper, keyguardUpdateMonitorWrapper, displayManagerWrapper);
    }

    public static PopupUIAlertDialogFactory_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new PopupUIAlertDialogFactory_Factory(provider, provider2, provider3, provider4, provider5);
    }

    @Override // javax.inject.Provider
    public PopupUIAlertDialogFactory get() {
        return newInstance((Context) this.contextProvider.get(), (PopupUIUtil) this.popupUIUtilProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (KeyguardUpdateMonitorWrapper) this.keyguardUpdateMonitorWrapperProvider.get(), (DisplayManagerWrapper) this.displayManagerWrapperProvider.get());
    }
}
