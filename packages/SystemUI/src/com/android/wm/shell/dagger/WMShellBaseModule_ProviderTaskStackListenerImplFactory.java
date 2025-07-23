package com.android.wm.shell.dagger;

import android.os.Handler;
import com.android.wm.shell.common.TaskStackListenerImpl;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProviderTaskStackListenerImplFactory implements Provider {
    public final Provider mainHandlerProvider;

    public WMShellBaseModule_ProviderTaskStackListenerImplFactory(Provider provider) {
        this.mainHandlerProvider = provider;
    }

    public static TaskStackListenerImpl providerTaskStackListenerImpl(Handler handler) {
        return new TaskStackListenerImpl(handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new TaskStackListenerImpl((Handler) this.mainHandlerProvider.get());
    }
}
