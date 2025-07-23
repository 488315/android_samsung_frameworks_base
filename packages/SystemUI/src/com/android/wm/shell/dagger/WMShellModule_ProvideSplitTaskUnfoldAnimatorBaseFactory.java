package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.unfold.UnfoldBackgroundController;
import com.android.wm.shell.unfold.animation.SplitTaskUnfoldAnimator;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideSplitTaskUnfoldAnimatorBaseFactory implements Provider {
    public final Provider backgroundControllerProvider;
    public final Provider contextProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider executorProvider;
    public final Provider shellControllerProvider;
    public final Provider splitScreenOptionalProvider;

    public WMShellModule_ProvideSplitTaskUnfoldAnimatorBaseFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.backgroundControllerProvider = provider2;
        this.shellControllerProvider = provider3;
        this.executorProvider = provider4;
        this.splitScreenOptionalProvider = provider5;
        this.displayInsetsControllerProvider = provider6;
    }

    public static SplitTaskUnfoldAnimator provideSplitTaskUnfoldAnimatorBase(Context context, UnfoldBackgroundController unfoldBackgroundController, ShellController shellController, ShellExecutor shellExecutor, Lazy lazy, DisplayInsetsController displayInsetsController) {
        return new SplitTaskUnfoldAnimator(context, shellExecutor, lazy, shellController, unfoldBackgroundController, displayInsetsController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        Context context = (Context) this.contextProvider.get();
        UnfoldBackgroundController unfoldBackgroundController = (UnfoldBackgroundController) this.backgroundControllerProvider.get();
        return new SplitTaskUnfoldAnimator(context, (ShellExecutor) this.executorProvider.get(), DoubleCheck.lazy(this.splitScreenOptionalProvider), (ShellController) this.shellControllerProvider.get(), unfoldBackgroundController, (DisplayInsetsController) this.displayInsetsControllerProvider.get());
    }
}
