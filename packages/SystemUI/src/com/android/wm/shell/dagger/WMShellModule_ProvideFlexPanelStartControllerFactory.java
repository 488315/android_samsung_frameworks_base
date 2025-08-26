package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.flexpanel.FlexPanelStartController;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideFlexPanelStartControllerFactory implements Provider {
    public final Provider contextProvider;

    public WMShellModule_ProvideFlexPanelStartControllerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FlexPanelStartController provideFlexPanelStartController(Context context) {
        return new FlexPanelStartController(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new FlexPanelStartController((Context) this.contextProvider.get());
    }
}
