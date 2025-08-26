package com.android.wm.shell.dagger;

import com.android.internal.logging.UiEventLogger;
import com.android.wm.shell.bubbles.BubbleLogger;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideBubbleLoggerFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public WMShellModule_ProvideBubbleLoggerFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static BubbleLogger provideBubbleLogger(UiEventLogger uiEventLogger) {
        return new BubbleLogger(uiEventLogger);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BubbleLogger((UiEventLogger) this.uiEventLoggerProvider.get());
    }
}
