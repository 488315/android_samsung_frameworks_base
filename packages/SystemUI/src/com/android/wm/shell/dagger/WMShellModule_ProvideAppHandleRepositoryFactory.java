package com.android.wm.shell.dagger;

import com.android.wm.shell.desktopmode.WindowDecorCaptionHandleRepository;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideAppHandleRepositoryFactory implements Provider {
    public static WindowDecorCaptionHandleRepository provideAppHandleRepository() {
        return new WindowDecorCaptionHandleRepository();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new WindowDecorCaptionHandleRepository();
    }
}
