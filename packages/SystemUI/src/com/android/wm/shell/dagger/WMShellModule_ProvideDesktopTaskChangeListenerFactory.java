package com.android.wm.shell.dagger;

import android.window.DesktopModeFlags;
import com.android.wm.shell.desktopmode.DesktopTaskChangeListener;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.shared.desktopmode.DesktopModeCompatPolicy;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopTaskChangeListenerFactory implements Provider {
    public final Provider desktopModeCompatPolicyProvider;
    public final Provider desktopStateProvider;
    public final Provider desktopUserRepositoriesProvider;

    public WMShellModule_ProvideDesktopTaskChangeListenerFactory(Provider provider, Provider provider2, Provider provider3) {
        this.desktopModeCompatPolicyProvider = provider;
        this.desktopUserRepositoriesProvider = provider2;
        this.desktopStateProvider = provider3;
    }

    public static Optional provideDesktopTaskChangeListener(DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopUserRepositories desktopUserRepositories, DesktopState desktopState) {
        Optional of = (DesktopModeFlags.ENABLE_WINDOWING_TRANSITION_HANDLERS_OBSERVERS.isTrue() && ((DesktopStateImpl) desktopState).canEnterDesktopMode) ? Optional.of(new DesktopTaskChangeListener(desktopModeCompatPolicy, desktopUserRepositories)) : Optional.empty();
        of.getClass();
        return of;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDesktopTaskChangeListener((DesktopModeCompatPolicy) this.desktopModeCompatPolicyProvider.get(), (DesktopUserRepositories) this.desktopUserRepositoriesProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}
