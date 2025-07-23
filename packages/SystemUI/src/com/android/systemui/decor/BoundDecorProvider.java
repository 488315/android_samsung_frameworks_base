package com.android.systemui.decor;

import java.util.Collections;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class BoundDecorProvider extends DecorProvider {
    public final Lazy alignedBounds$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.decor.BoundDecorProvider$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Collections.singletonList(Integer.valueOf(BoundDecorProvider.this.getAlignedBound()));
        }
    });

    public abstract int getAlignedBound();

    @Override // com.android.systemui.decor.DecorProvider
    public final List getAlignedBounds() {
        return (List) this.alignedBounds$delegate.getValue();
    }
}
