package com.android.systemui.decor;

import java.util.Collections;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class BoundDecorProvider extends DecorProvider {
    public final Lazy alignedBounds$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.decor.BoundDecorProvider$alignedBounds$2
        {
            super(0);
        }

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
