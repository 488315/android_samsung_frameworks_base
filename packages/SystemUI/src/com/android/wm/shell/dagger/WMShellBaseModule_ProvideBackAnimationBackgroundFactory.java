package com.android.wm.shell.dagger;

import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.back.BackAnimationBackground;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideBackAnimationBackgroundFactory implements Provider {
    public final Provider rootTaskDisplayAreaOrganizerProvider;

    public WMShellBaseModule_ProvideBackAnimationBackgroundFactory(Provider provider) {
        this.rootTaskDisplayAreaOrganizerProvider = provider;
    }

    public static BackAnimationBackground provideBackAnimationBackground(RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer) {
        return new BackAnimationBackground(rootTaskDisplayAreaOrganizer);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BackAnimationBackground((RootTaskDisplayAreaOrganizer) this.rootTaskDisplayAreaOrganizerProvider.get());
    }
}
