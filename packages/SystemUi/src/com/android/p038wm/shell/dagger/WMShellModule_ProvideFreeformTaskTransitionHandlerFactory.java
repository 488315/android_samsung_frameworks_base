package com.android.p038wm.shell.dagger;

import com.android.p038wm.shell.freeform.FreeformTaskTransitionHandler;
import com.android.p038wm.shell.shortcut.ShortcutController;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.p038wm.shell.transition.Transitions;
import com.android.p038wm.shell.windowdecor.WindowDecorViewModel;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvideFreeformTaskTransitionHandlerFactory implements Provider {
    public final Provider shellInitProvider;
    public final Provider shortcutControllerProvider;
    public final Provider transitionsProvider;
    public final Provider windowDecorViewModelProvider;

    public WMShellModule_ProvideFreeformTaskTransitionHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.shellInitProvider = provider;
        this.transitionsProvider = provider2;
        this.windowDecorViewModelProvider = provider3;
        this.shortcutControllerProvider = provider4;
    }

    public static FreeformTaskTransitionHandler provideFreeformTaskTransitionHandler(ShellInit shellInit, Transitions transitions, WindowDecorViewModel windowDecorViewModel, ShortcutController shortcutController) {
        return new FreeformTaskTransitionHandler(shellInit, transitions, windowDecorViewModel, shortcutController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new FreeformTaskTransitionHandler((ShellInit) this.shellInitProvider.get(), (Transitions) this.transitionsProvider.get(), (WindowDecorViewModel) this.windowDecorViewModelProvider.get(), (ShortcutController) this.shortcutControllerProvider.get());
    }
}
