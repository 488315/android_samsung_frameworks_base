package com.android.systemui.p032tv;

import android.content.Context;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.RecentsImplementation;
import com.android.systemui.statusbar.CommandQueue;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvSystemUIModule_ProvideRecentsFactory implements Provider {
    public final Provider commandQueueProvider;
    public final Provider contextProvider;
    public final Provider recentsImplementationProvider;

    public TvSystemUIModule_ProvideRecentsFactory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.recentsImplementationProvider = provider2;
        this.commandQueueProvider = provider3;
    }

    public static Recents provideRecents(Context context, RecentsImplementation recentsImplementation, CommandQueue commandQueue) {
        return new Recents(context, recentsImplementation, commandQueue);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new Recents((Context) this.contextProvider.get(), (RecentsImplementation) this.recentsImplementationProvider.get(), (CommandQueue) this.commandQueueProvider.get());
    }
}
