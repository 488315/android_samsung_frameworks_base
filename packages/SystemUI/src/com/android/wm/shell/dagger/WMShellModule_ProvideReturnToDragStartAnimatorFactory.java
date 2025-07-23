package com.android.wm.shell.dagger;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.desktopmode.ReturnToDragStartAnimator;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideReturnToDragStartAnimatorFactory implements Provider {
    public final Provider interactionJankMonitorProvider;

    public WMShellModule_ProvideReturnToDragStartAnimatorFactory(Provider provider) {
        this.interactionJankMonitorProvider = provider;
    }

    public static ReturnToDragStartAnimator provideReturnToDragStartAnimator(InteractionJankMonitor interactionJankMonitor) {
        return new ReturnToDragStartAnimator(interactionJankMonitor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ReturnToDragStartAnimator((InteractionJankMonitor) this.interactionJankMonitorProvider.get());
    }
}
