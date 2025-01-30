package com.android.p038wm.shell.dagger;

import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideRecentTasksFactory implements Provider {
    public final Provider recentTasksControllerProvider;

    public WMShellBaseModule_ProvideRecentTasksFactory(Provider provider) {
        this.recentTasksControllerProvider = provider;
    }

    public static Optional provideRecentTasks(Optional optional) {
        Optional map = optional.map(new WMShellBaseModule$$ExternalSyntheticLambda0(2));
        Preconditions.checkNotNullFromProvides(map);
        return map;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideRecentTasks((Optional) this.recentTasksControllerProvider.get());
    }
}
