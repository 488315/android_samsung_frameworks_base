package com.android.wm.shell.dagger;

import com.android.wm.shell.desktopmode.DesktopModeDragAndDropTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopModeDragAndDropTransitionHandlerFactory implements Provider {
    public final Provider transitionsProvider;

    public WMShellModule_ProvideDesktopModeDragAndDropTransitionHandlerFactory(Provider provider) {
        this.transitionsProvider = provider;
    }

    public static DesktopModeDragAndDropTransitionHandler provideDesktopModeDragAndDropTransitionHandler(Transitions transitions) {
        return new DesktopModeDragAndDropTransitionHandler(transitions);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopModeDragAndDropTransitionHandler((Transitions) this.transitionsProvider.get());
    }
}
