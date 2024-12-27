package com.android.systemui.popup;

import android.content.Context;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.viewmodel.PopupUIViewModel;
import dagger.internal.Provider;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PopupUI_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider logWrapperProvider;
    private final javax.inject.Provider popupUIViewModelListProvider;

    public PopupUI_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.contextProvider = provider;
        this.logWrapperProvider = provider2;
        this.popupUIViewModelListProvider = provider3;
    }

    public static PopupUI_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new PopupUI_Factory(provider, provider2, provider3);
    }

    public static PopupUI newInstance(Context context, LogWrapper logWrapper, List<PopupUIViewModel> list) {
        return new PopupUI(context, logWrapper, list);
    }

    @Override // javax.inject.Provider
    public PopupUI get() {
        return newInstance((Context) this.contextProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (List) this.popupUIViewModelListProvider.get());
    }
}
