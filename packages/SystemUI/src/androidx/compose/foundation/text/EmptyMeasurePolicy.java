package androidx.compose.foundation.text;

import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.unit.Constraints;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class EmptyMeasurePolicy implements MeasurePolicy {
    public static final EmptyMeasurePolicy INSTANCE = new EmptyMeasurePolicy();
    public static final Function1 placementBlock = new Function1() { // from class: androidx.compose.foundation.text.EmptyMeasurePolicy$placementBlock$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
            return Unit.INSTANCE;
        }
    };

    private EmptyMeasurePolicy() {
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
        MeasureResult layout$1;
        layout$1 = measureScope.layout$1(Constraints.m821getMaxWidthimpl(j), Constraints.m820getMaxHeightimpl(j), MapsKt__MapsKt.emptyMap(), placementBlock);
        return layout$1;
    }
}
