package com.android.systemui.dagger;

import android.content.Context;
import com.android.systemui.searcle.SearcleManager;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungServicesModule_ProvideSearcleManagerFactory implements Provider {
    public final Provider contextProvider;

    public SamsungServicesModule_ProvideSearcleManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static SearcleManager provideSearcleManager(Context context) {
        return new SearcleManager(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new SearcleManager((Context) this.contextProvider.get());
    }
}
