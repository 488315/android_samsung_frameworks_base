package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.bubbles.BubbleData;
import com.android.wm.shell.bubbles.BubbleEducationController;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.common.ShellExecutor;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideBubbleDataFactory implements Provider {
    public final Provider bgExecutorProvider;
    public final Provider contextProvider;
    public final Provider educationControllerProvider;
    public final Provider loggerProvider;
    public final Provider mainExecutorProvider;
    public final Provider positionerProvider;

    public WMShellModule_ProvideBubbleDataFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.loggerProvider = provider2;
        this.positionerProvider = provider3;
        this.educationControllerProvider = provider4;
        this.mainExecutorProvider = provider5;
        this.bgExecutorProvider = provider6;
    }

    public static BubbleData provideBubbleData(Context context, BubbleLogger bubbleLogger, BubblePositioner bubblePositioner, BubbleEducationController bubbleEducationController, ShellExecutor shellExecutor, ShellExecutor shellExecutor2) {
        return new BubbleData(context, bubbleLogger, bubblePositioner, bubbleEducationController, shellExecutor, shellExecutor2);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BubbleData((Context) this.contextProvider.get(), (BubbleLogger) this.loggerProvider.get(), (BubblePositioner) this.positionerProvider.get(), (BubbleEducationController) this.educationControllerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (ShellExecutor) this.bgExecutorProvider.get());
    }
}
