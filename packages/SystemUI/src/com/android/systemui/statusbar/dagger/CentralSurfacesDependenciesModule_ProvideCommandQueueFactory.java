package com.android.systemui.statusbar.dagger;

import android.content.Context;
import com.android.systemui.dump.DumpHandler;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CentralSurfacesDependenciesModule_ProvideCommandQueueFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayTrackerProvider;
    public final Provider dumpHandlerProvider;
    public final Provider powerInteractorProvider;
    public final Provider registryProvider;

    public CentralSurfacesDependenciesModule_ProvideCommandQueueFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.displayTrackerProvider = provider2;
        this.registryProvider = provider3;
        this.dumpHandlerProvider = provider4;
        this.powerInteractorProvider = provider5;
    }

    public static CommandQueue provideCommandQueue(Context context, DisplayTracker displayTracker, CommandRegistry commandRegistry, DumpHandler dumpHandler, Lazy lazy) {
        return new CommandQueue(context, displayTracker, commandRegistry, dumpHandler, lazy);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new CommandQueue((Context) this.contextProvider.get(), (DisplayTracker) this.displayTrackerProvider.get(), (CommandRegistry) this.registryProvider.get(), (DumpHandler) this.dumpHandlerProvider.get(), DoubleCheck.lazy(this.powerInteractorProvider));
    }
}
