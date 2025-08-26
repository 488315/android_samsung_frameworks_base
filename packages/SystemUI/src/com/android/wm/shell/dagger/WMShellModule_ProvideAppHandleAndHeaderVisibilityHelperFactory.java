package com.android.wm.shell.dagger;

import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.shared.desktopmode.DesktopModeCompatPolicy;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.windowdecor.common.AppHandleAndHeaderVisibilityHelper;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideAppHandleAndHeaderVisibilityHelperFactory implements Provider {
    public final Provider contextProvider;
    public final Provider desktopModeCompatPolicyProvider;
    public final Provider desktopStateProvider;
    public final Provider displayControllerProvider;

    public WMShellModule_ProvideAppHandleAndHeaderVisibilityHelperFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.displayControllerProvider = provider2;
        this.desktopModeCompatPolicyProvider = provider3;
        this.desktopStateProvider = provider4;
    }

    public static AppHandleAndHeaderVisibilityHelper provideAppHandleAndHeaderVisibilityHelper(DisplayController displayController, DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopState desktopState) {
        return new AppHandleAndHeaderVisibilityHelper(displayController, desktopModeCompatPolicy, desktopState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new AppHandleAndHeaderVisibilityHelper((DisplayController) this.displayControllerProvider.get(), (DesktopModeCompatPolicy) this.desktopModeCompatPolicyProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}
