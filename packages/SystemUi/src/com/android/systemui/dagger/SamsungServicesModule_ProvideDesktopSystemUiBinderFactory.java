package com.android.systemui.dagger;

import android.content.Context;
import com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungServicesModule_ProvideDesktopSystemUiBinderFactory implements Provider {
    public final Provider contextProvider;

    public SamsungServicesModule_ProvideDesktopSystemUiBinderFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static DesktopSystemUiBinder provideDesktopSystemUiBinder(Context context) {
        return new DesktopSystemUiBinder(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopSystemUiBinder((Context) this.contextProvider.get());
    }
}
