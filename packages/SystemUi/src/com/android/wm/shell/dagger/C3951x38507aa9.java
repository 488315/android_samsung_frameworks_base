package com.android.wm.shell.dagger;

import com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvideEnterDesktopModeTaskTransitionHandlerFactory */
/* loaded from: classes2.dex */
public final class C3951x38507aa9 implements Provider {
    public final Provider transitionsProvider;

    public C3951x38507aa9(Provider provider) {
        this.transitionsProvider = provider;
    }

    public static EnterDesktopTaskTransitionHandler provideEnterDesktopModeTaskTransitionHandler(Transitions transitions) {
        return new EnterDesktopTaskTransitionHandler(transitions);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new EnterDesktopTaskTransitionHandler((Transitions) this.transitionsProvider.get());
    }
}
