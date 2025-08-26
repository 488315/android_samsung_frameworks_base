package com.android.wm.shell.dagger;

import com.android.wm.shell.compatui.impl.DefaultComponentIdGenerator;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideCompatUIComponentIdGeneratorFactory implements Provider {
    public static DefaultComponentIdGenerator provideCompatUIComponentIdGenerator() {
        return new DefaultComponentIdGenerator();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DefaultComponentIdGenerator();
    }
}
