package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.desktopmode.ExitDesktopTaskTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvideExitDesktopTaskTransitionHandlerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider transitionsProvider;

    public WMShellModule_ProvideExitDesktopTaskTransitionHandlerFactory(Provider provider, Provider provider2) {
        this.transitionsProvider = provider;
        this.contextProvider = provider2;
    }

    public static ExitDesktopTaskTransitionHandler provideExitDesktopTaskTransitionHandler(Transitions transitions, Context context) {
        return new ExitDesktopTaskTransitionHandler(transitions, context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ExitDesktopTaskTransitionHandler((Transitions) this.transitionsProvider.get(), (Context) this.contextProvider.get());
    }
}
