package com.android.p038wm.shell.dagger;

import com.android.internal.logging.UiEventLogger;
import com.android.p038wm.shell.bubbles.BubbleLogger;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
