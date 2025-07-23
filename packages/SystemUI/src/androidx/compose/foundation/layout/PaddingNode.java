package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.ConstraintsKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PaddingNode extends Modifier.Node implements LayoutModifierNode {
    public float bottom;
    public float end;
    public boolean rtlAware;
    public float start;
    public float top;

    public /* synthetic */ PaddingNode(float f, float f2, float f3, float f4, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, z);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(final MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        int mo51roundToPx0680j_4 = measureScope.mo51roundToPx0680j_4(this.end) + measureScope.mo51roundToPx0680j_4(this.start);
        int mo51roundToPx0680j_42 = measureScope.mo51roundToPx0680j_4(this.bottom) + measureScope.mo51roundToPx0680j_4(this.top);
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(ConstraintsKt.m833offsetNN6EwU(-mo51roundToPx0680j_4, -mo51roundToPx0680j_42, j));
        layout$1 = measureScope.layout$1(ConstraintsKt.m832constrainWidthK40F9xA(mo608measureBRTryo0.width + mo51roundToPx0680j_4, j), ConstraintsKt.m831constrainHeightK40F9xA(mo608measureBRTryo0.height + mo51roundToPx0680j_42, j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.PaddingNode$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                PaddingNode paddingNode = PaddingNode.this;
                if (paddingNode.rtlAware) {
                    placementScope.placeRelative(mo608measureBRTryo0, measureScope.mo51roundToPx0680j_4(paddingNode.start), measureScope.mo51roundToPx0680j_4(PaddingNode.this.top), 0.0f);
                } else {
                    placementScope.place(mo608measureBRTryo0, measureScope.mo51roundToPx0680j_4(paddingNode.start), measureScope.mo51roundToPx0680j_4(PaddingNode.this.top), 0.0f);
                }
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    private PaddingNode(float f, float f2, float f3, float f4, boolean z) {
        this.start = f;
        this.top = f2;
        this.end = f3;
        this.bottom = f4;
        this.rtlAware = z;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PaddingNode(float r9, float r10, float r11, float r12, boolean r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r8 = this;
            r15 = r14 & 1
            r0 = 0
            if (r15 == 0) goto L8
            float r9 = (float) r0
            androidx.compose.ui.unit.Dp$Companion r15 = androidx.compose.ui.unit.Dp.Companion
        L8:
            r2 = r9
            r9 = r14 & 2
            if (r9 == 0) goto L10
            float r10 = (float) r0
            androidx.compose.ui.unit.Dp$Companion r9 = androidx.compose.ui.unit.Dp.Companion
        L10:
            r3 = r10
            r9 = r14 & 4
            if (r9 == 0) goto L18
            float r11 = (float) r0
            androidx.compose.ui.unit.Dp$Companion r9 = androidx.compose.ui.unit.Dp.Companion
        L18:
            r4 = r11
            r9 = r14 & 8
            if (r9 == 0) goto L20
            float r12 = (float) r0
            androidx.compose.ui.unit.Dp$Companion r9 = androidx.compose.ui.unit.Dp.Companion
        L20:
            r5 = r12
            r7 = 0
            r1 = r8
            r6 = r13
            r1.<init>(r2, r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.PaddingNode.<init>(float, float, float, float, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
