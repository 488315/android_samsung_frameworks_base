package com.android.wm.shell.dagger;

import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.taskview.TaskViewTransitions;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideMixedTransitionHandlerFactory implements Provider {
    public final Provider activityEmbeddingControllerProvider;
    public final Provider desktopTasksControllerProvider;
    public final Provider keyguardTransitionHandlerProvider;
    public final Provider pipTransitionControllerProvider;
    public final Provider recentsTransitionHandlerProvider;
    public final Provider shellInitProvider;
    public final Provider splitScreenOptionalProvider;
    public final Provider taskViewTransitionsProvider;
    public final Provider transitionsProvider;
    public final Provider unfoldHandlerProvider;

    public WMShellModule_ProvideMixedTransitionHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        this.shellInitProvider = provider;
        this.splitScreenOptionalProvider = provider2;
        this.pipTransitionControllerProvider = provider3;
        this.recentsTransitionHandlerProvider = provider4;
        this.keyguardTransitionHandlerProvider = provider5;
        this.desktopTasksControllerProvider = provider6;
        this.unfoldHandlerProvider = provider7;
        this.activityEmbeddingControllerProvider = provider8;
        this.taskViewTransitionsProvider = provider9;
        this.transitionsProvider = provider10;
    }

    public static DefaultMixedHandler provideMixedTransitionHandler(ShellInit shellInit, Transitions transitions, Optional optional, PipTransitionController pipTransitionController, Optional optional2, KeyguardTransitionHandler keyguardTransitionHandler, Optional optional3, Optional optional4, Optional optional5, TaskViewTransitions taskViewTransitions) {
        return new DefaultMixedHandler(shellInit, transitions, optional, pipTransitionController, optional2, keyguardTransitionHandler, optional3, optional4, optional5, taskViewTransitions);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DefaultMixedHandler((ShellInit) this.shellInitProvider.get(), (Transitions) this.transitionsProvider.get(), (Optional) this.splitScreenOptionalProvider.get(), (PipTransitionController) this.pipTransitionControllerProvider.get(), (Optional) this.recentsTransitionHandlerProvider.get(), (KeyguardTransitionHandler) this.keyguardTransitionHandlerProvider.get(), (Optional) this.desktopTasksControllerProvider.get(), (Optional) this.unfoldHandlerProvider.get(), (Optional) this.activityEmbeddingControllerProvider.get(), (TaskViewTransitions) this.taskViewTransitionsProvider.get());
    }
}
