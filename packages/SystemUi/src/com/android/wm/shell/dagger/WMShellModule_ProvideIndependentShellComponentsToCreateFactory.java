package com.android.wm.shell.dagger;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvideIndependentShellComponentsToCreateFactory implements Provider {
    public final Provider defaultMixedHandlerProvider;
    public final Provider desktopModeControllerProvider;

    public WMShellModule_ProvideIndependentShellComponentsToCreateFactory(Provider provider, Provider provider2) {
        this.defaultMixedHandlerProvider = provider;
        this.desktopModeControllerProvider = provider2;
    }

    public static Object provideIndependentShellComponentsToCreate$1() {
        return new Object();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new Object();
    }
}
