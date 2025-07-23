package com.android.wm.shell.dagger;

import com.android.internal.logging.UiEventLogger;
import com.android.wm.shell.bubbles.BubbleLogger;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
