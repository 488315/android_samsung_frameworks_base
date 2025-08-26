package com.android.wm.shell.dagger;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.desktopmode.ReturnToDragStartAnimator;
import dagger.internal.Provider;

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
