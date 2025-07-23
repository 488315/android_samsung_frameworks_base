package com.samsung.sesl.compose.component;

import androidx.compose.animation.core.Animatable;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import com.samsung.sesl.compose.foundation.scroll.SeslScrollableState;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class ScrollbarKt$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ScrollbarKt$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf((int) ((Number) ((SnapshotMutableStateImpl) ((Animatable) this.f$0).internalState.value$delegate).getValue()).floatValue());
            default:
                ScrollAdapter scrollAdapter = (ScrollAdapter) this.f$0;
                int i = scrollAdapter.scrollBarSize;
                SeslScrollableState seslScrollableState = scrollAdapter.scrollableState;
                int handleSizeFraction = (int) (seslScrollableState.getHandleSizeFraction() * i);
                int i2 = scrollAdapter.handleMinSize;
                if (handleSizeFraction < i2) {
                    handleSizeFraction = i2;
                }
                return Float.valueOf(seslScrollableState.getPositionFraction() * (i - handleSizeFraction));
        }
    }
}
