package com.android.systemui.popup;

import android.content.Context;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.viewmodel.PopupUIViewModel;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PopupUI_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider logWrapperProvider;
    private final Provider popupUIViewModelListProvider;

    public PopupUI_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.logWrapperProvider = provider2;
        this.popupUIViewModelListProvider = provider3;
    }

    public static PopupUI_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new PopupUI_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static PopupUI newInstance(Context context, LogWrapper logWrapper, List<PopupUIViewModel> list) {
        return new PopupUI(context, logWrapper, list);
    }

    public static PopupUI_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new PopupUI_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public PopupUI get() {
        return newInstance((Context) this.contextProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (List) this.popupUIViewModelListProvider.get());
    }
}
