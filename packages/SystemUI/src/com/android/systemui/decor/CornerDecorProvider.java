package com.android.systemui.decor;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;

public abstract class CornerDecorProvider extends DecorProvider {
    public final Lazy alignedBounds$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.decor.CornerDecorProvider$alignedBounds$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return CollectionsKt__CollectionsKt.listOf(Integer.valueOf(CornerDecorProvider.this.getAlignedBound1()), Integer.valueOf(CornerDecorProvider.this.getAlignedBound2()));
        }
    });

    public abstract int getAlignedBound1();

    public abstract int getAlignedBound2();

    @Override // com.android.systemui.decor.DecorProvider
    public final List getAlignedBounds() {
        return (List) this.alignedBounds$delegate.getValue();
    }
}
