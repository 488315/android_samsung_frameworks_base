package com.android.p038wm.shell.dagger;

import android.view.Choreographer;
import com.android.p038wm.shell.common.ShellExecutor;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellConcurrencyModule_ProvideShellMainChoreographerFactory implements Provider {
    public final Provider executorProvider;

    public WMShellConcurrencyModule_ProvideShellMainChoreographerFactory(Provider provider) {
        this.executorProvider = provider;
    }

    public static Choreographer provideShellMainChoreographer(ShellExecutor shellExecutor) {
        try {
            final Choreographer[] choreographerArr = new Choreographer[1];
            shellExecutor.executeBlocking(new Runnable() { // from class: com.android.wm.shell.dagger.WMShellConcurrencyModule$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    choreographerArr[0] = Choreographer.getInstance();
                }
            });
            Choreographer choreographer = choreographerArr[0];
            Preconditions.checkNotNullFromProvides(choreographer);
            return choreographer;
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to obtain main Choreographer.", e);
        }
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShellMainChoreographer((ShellExecutor) this.executorProvider.get());
    }
}
