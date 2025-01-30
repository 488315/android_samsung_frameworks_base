package com.android.wm.shell.dagger;

import android.os.Handler;
import com.android.wm.shell.common.TaskStackListenerImpl;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
