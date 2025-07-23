package com.android.app.displaylib;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DefaultDisplayOnlyInstanceRepositoryImpl implements PerDisplayRepository {
    public final PerDisplayInstanceProvider instanceProvider;
    public final Lazy lazyDefaultDisplayInstance$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.app.displaylib.DefaultDisplayOnlyInstanceRepositoryImpl$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return DefaultDisplayOnlyInstanceRepositoryImpl.this.instanceProvider.createInstance(0);
        }
    });

    public DefaultDisplayOnlyInstanceRepositoryImpl(String str, PerDisplayInstanceProvider perDisplayInstanceProvider) {
        this.instanceProvider = perDisplayInstanceProvider;
    }

    @Override // com.android.app.displaylib.PerDisplayRepository
    public final Object get(int i) {
        return this.lazyDefaultDisplayInstance$delegate.getValue();
    }
}
