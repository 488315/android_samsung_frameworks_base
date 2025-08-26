package com.android.systemui.keyguard.dagger;

import com.android.systemui.util.ThreadAssert;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class KeyguardModule_ProvidesThreadAssertFactory implements Provider {
    public static ThreadAssert providesThreadAssert() {
        return new ThreadAssert();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ThreadAssert();
    }
}
