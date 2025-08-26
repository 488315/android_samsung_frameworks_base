package com.android.wm.shell.dagger;

import android.content.Context;
import android.window.DesktopModeFlags;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.compatui.SystemModalsTransitionHandler;
import com.android.wm.shell.shared.desktopmode.DesktopModeCompatPolicy;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideSystemModalsTransitionHandlerFactory implements Provider {
    public final Provider animExecutorProvider;
    public final Provider contextProvider;
    public final Provider desktopModeCompatPolicyProvider;
    public final Provider desktopStateProvider;
    public final Provider desktopUserRepositoriesProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellInitProvider;
    public final Provider transitionsProvider;

    public WMShellModule_ProvideSystemModalsTransitionHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.mainExecutorProvider = provider2;
        this.animExecutorProvider = provider3;
        this.shellInitProvider = provider4;
        this.transitionsProvider = provider5;
        this.desktopUserRepositoriesProvider = provider6;
        this.desktopModeCompatPolicyProvider = provider7;
        this.desktopStateProvider = provider8;
    }

    public static Optional provideSystemModalsTransitionHandler(Context context, ShellExecutor shellExecutor, ShellExecutor shellExecutor2, ShellInit shellInit, Transitions transitions, DesktopUserRepositories desktopUserRepositories, DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopState desktopState) {
        Optional optionalOf = (((DesktopStateImpl) desktopState).canEnterDesktopMode && DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_MODALS_POLICY.isTrue() && DesktopModeFlags.ENABLE_DESKTOP_SYSTEM_DIALOGS_TRANSITIONS.isTrue()) ? Optional.of(new SystemModalsTransitionHandler(context, shellExecutor, shellExecutor2, shellInit, transitions, desktopUserRepositories, desktopModeCompatPolicy)) : Optional.empty();
        optionalOf.getClass();
        return optionalOf;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSystemModalsTransitionHandler((Context) this.contextProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (ShellExecutor) this.animExecutorProvider.get(), (ShellInit) this.shellInitProvider.get(), (Transitions) this.transitionsProvider.get(), (DesktopUserRepositories) this.desktopUserRepositoriesProvider.get(), (DesktopModeCompatPolicy) this.desktopModeCompatPolicyProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}
