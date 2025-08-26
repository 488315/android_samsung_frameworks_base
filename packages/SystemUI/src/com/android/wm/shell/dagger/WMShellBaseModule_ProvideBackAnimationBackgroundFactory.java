package com.android.wm.shell.dagger;

import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.back.BackAnimationBackground;
import dagger.internal.Provider;

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
