package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.compatui.impl.DefaultCompatUIComponentFactory;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideCompatUIComponentFactoryFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider syncQueueProvider;

    public WMShellBaseModule_ProvideCompatUIComponentFactoryFactory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.syncQueueProvider = provider2;
        this.displayControllerProvider = provider3;
    }

    public static DefaultCompatUIComponentFactory provideCompatUIComponentFactory(Context context, SyncTransactionQueue syncTransactionQueue, DisplayController displayController) {
        return new DefaultCompatUIComponentFactory(context, syncTransactionQueue, displayController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DefaultCompatUIComponentFactory((Context) this.contextProvider.get(), (SyncTransactionQueue) this.syncQueueProvider.get(), (DisplayController) this.displayControllerProvider.get());
    }
}
