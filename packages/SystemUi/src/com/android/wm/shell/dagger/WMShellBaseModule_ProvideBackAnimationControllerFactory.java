package com.android.wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.wm.shell.back.BackAnimationBackground;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideBackAnimationControllerFactory implements Provider {
    public final Provider backAnimationBackgroundProvider;
    public final Provider backgroundHandlerProvider;
    public final Provider contextProvider;
    public final Provider shellControllerProvider;
    public final Provider shellExecutorProvider;
    public final Provider shellInitProvider;

    public WMShellBaseModule_ProvideBackAnimationControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellControllerProvider = provider3;
        this.shellExecutorProvider = provider4;
        this.backgroundHandlerProvider = provider5;
        this.backAnimationBackgroundProvider = provider6;
    }

    public static Optional provideBackAnimationController(ShellInit shellInit, ShellController shellController, ShellExecutor shellExecutor, Handler handler, Context context, BackAnimationBackground backAnimationBackground) {
        Optional of = BackAnimationController.IS_ENABLED ? Optional.of(new BackAnimationController(shellInit, shellController, shellExecutor, handler, context, backAnimationBackground)) : Optional.empty();
        Preconditions.checkNotNullFromProvides(of);
        return of;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideBackAnimationController((ShellInit) this.shellInitProvider.get(), (ShellController) this.shellControllerProvider.get(), (ShellExecutor) this.shellExecutorProvider.get(), (Handler) this.backgroundHandlerProvider.get(), (Context) this.contextProvider.get(), (BackAnimationBackground) this.backAnimationBackgroundProvider.get());
    }
}
