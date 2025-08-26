package com.android.wm.shell.dagger;

import com.android.wm.shell.common.FloatingContentCoordinator;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideFloatingContentCoordinatorFactory implements Provider {
    public static FloatingContentCoordinator provideFloatingContentCoordinator() {
        return new FloatingContentCoordinator();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new FloatingContentCoordinator();
    }
}
