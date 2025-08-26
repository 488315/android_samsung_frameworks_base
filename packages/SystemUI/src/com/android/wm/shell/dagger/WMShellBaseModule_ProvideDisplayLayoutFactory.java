package com.android.wm.shell.dagger;

import com.android.wm.shell.common.DisplayLayout;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideDisplayLayoutFactory implements Provider {
    public static DisplayLayout provideDisplayLayout() {
        return new DisplayLayout();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DisplayLayout();
    }
}
