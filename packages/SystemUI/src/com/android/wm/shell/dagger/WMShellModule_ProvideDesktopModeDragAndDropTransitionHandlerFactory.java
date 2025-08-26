package com.android.wm.shell.dagger;

import com.android.wm.shell.desktopmode.DesktopModeDragAndDropTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;

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
