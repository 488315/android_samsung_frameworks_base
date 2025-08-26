package com.android.wm.shell.dagger;

import com.android.wm.shell.compatui.api.CompatUIState;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideCompatUIStateFactory implements Provider {
    public static CompatUIState provideCompatUIState() {
        return new CompatUIState();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new CompatUIState();
    }
}
