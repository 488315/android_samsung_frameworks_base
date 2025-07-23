package com.android.wm.shell.dagger;

import android.view.Choreographer;
import com.android.wm.shell.common.ShellExecutor;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
            choreographer.getClass();
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
