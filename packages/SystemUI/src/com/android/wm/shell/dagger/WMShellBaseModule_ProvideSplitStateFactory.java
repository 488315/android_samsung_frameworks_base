package com.android.wm.shell.dagger;

import com.android.wm.shell.common.split.SplitState;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideSplitStateFactory implements Provider {
    public static SplitState provideSplitState() {
        return new SplitState();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new SplitState();
    }
}
