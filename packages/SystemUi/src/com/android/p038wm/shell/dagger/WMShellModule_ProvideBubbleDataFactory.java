package com.android.p038wm.shell.dagger;

import android.content.Context;
import com.android.p038wm.shell.bubbles.BubbleData;
import com.android.p038wm.shell.bubbles.BubbleLogger;
import com.android.p038wm.shell.bubbles.BubblePositioner;
import com.android.p038wm.shell.common.ShellExecutor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvideBubbleDataFactory implements Provider {
    public final Provider contextProvider;
    public final Provider loggerProvider;
    public final Provider mainExecutorProvider;
    public final Provider positionerProvider;

    public WMShellModule_ProvideBubbleDataFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.loggerProvider = provider2;
        this.positionerProvider = provider3;
        this.mainExecutorProvider = provider4;
    }

    public static BubbleData provideBubbleData(Context context, BubbleLogger bubbleLogger, BubblePositioner bubblePositioner, ShellExecutor shellExecutor) {
        return new BubbleData(context, bubbleLogger, bubblePositioner, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BubbleData((Context) this.contextProvider.get(), (BubbleLogger) this.loggerProvider.get(), (BubblePositioner) this.positionerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
