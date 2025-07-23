package com.android.systemui.keyguard.dagger;

import com.android.systemui.util.ThreadAssert;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
