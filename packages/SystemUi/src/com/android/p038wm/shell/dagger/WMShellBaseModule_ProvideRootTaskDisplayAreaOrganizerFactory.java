package com.android.p038wm.shell.dagger;

import android.content.Context;
import com.android.p038wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.p038wm.shell.common.ShellExecutor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideRootTaskDisplayAreaOrganizerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider mainExecutorProvider;

    public WMShellBaseModule_ProvideRootTaskDisplayAreaOrganizerFactory(Provider provider, Provider provider2) {
        this.mainExecutorProvider = provider;
        this.contextProvider = provider2;
    }

    public static RootTaskDisplayAreaOrganizer provideRootTaskDisplayAreaOrganizer(Context context, ShellExecutor shellExecutor) {
        return new RootTaskDisplayAreaOrganizer(shellExecutor, context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new RootTaskDisplayAreaOrganizer((ShellExecutor) this.mainExecutorProvider.get(), (Context) this.contextProvider.get());
    }
}
