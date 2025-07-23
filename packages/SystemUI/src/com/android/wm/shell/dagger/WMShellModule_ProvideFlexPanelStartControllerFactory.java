package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.flexpanel.FlexPanelStartController;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
