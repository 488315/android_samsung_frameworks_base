package com.android.systemui.popup.util;

import android.content.Context;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DisplayManagerWrapper_Factory implements Provider {
    private final javax.inject.Provider contextProvider;

    public DisplayManagerWrapper_Factory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static DisplayManagerWrapper_Factory create(javax.inject.Provider provider) {
        return new DisplayManagerWrapper_Factory(provider);
    }

    public static DisplayManagerWrapper newInstance(Context context) {
        return new DisplayManagerWrapper(context);
    }

    @Override // javax.inject.Provider
    public DisplayManagerWrapper get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
