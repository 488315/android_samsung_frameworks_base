package com.android.wm.shell.dagger;

import com.android.wm.shell.desktopmode.WindowDecorCaptionHandleRepository;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
