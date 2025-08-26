package com.android.systemui.decor;

import java.util.Arrays;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public abstract class CornerDecorProvider extends DecorProvider {
    public final Lazy alignedBounds$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.decor.CornerDecorProvider$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            CornerDecorProvider cornerDecorProvider = this.f$0;
            return Arrays.asList(Integer.valueOf(cornerDecorProvider.getAlignedBound1()), Integer.valueOf(cornerDecorProvider.getAlignedBound2()));
        }
    });

    public abstract int getAlignedBound1();

    public abstract int getAlignedBound2();

    @Override // com.android.systemui.decor.DecorProvider
    public final List getAlignedBounds() {
        return (List) this.alignedBounds$delegate.getValue();
    }
}
