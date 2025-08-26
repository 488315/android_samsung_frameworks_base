package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
        int iMo52roundToPx0680j_4 = measureScope.mo52roundToPx0680j_4(this.end) + measureScope.mo52roundToPx0680j_4(this.start);
        int iMo52roundToPx0680j_42 = measureScope.mo52roundToPx0680j_4(this.bottom) + measureScope.mo52roundToPx0680j_4(this.top);
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(ConstraintsKt.m835offsetNN6EwU(-iMo52roundToPx0680j_4, -iMo52roundToPx0680j_42, j));
        return measureScope.layout$1(ConstraintsKt.m834constrainWidthK40F9xA(placeableMo610measureBRTryo0.width + iMo52roundToPx0680j_4, j), ConstraintsKt.m833constrainHeightK40F9xA(placeableMo610measureBRTryo0.height + iMo52roundToPx0680j_42, j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.layout.PaddingNode$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                PaddingNode paddingNode = this.this$0;
                if (paddingNode.rtlAware) {
                    placementScope.placeRelative(placeableMo610measureBRTryo0, measureScope.mo52roundToPx0680j_4(paddingNode.start), measureScope.mo52roundToPx0680j_4(this.this$0.top), 0.0f);
                } else {
                    placementScope.place(placeableMo610measureBRTryo0, measureScope.mo52roundToPx0680j_4(paddingNode.start), measureScope.mo52roundToPx0680j_4(this.this$0.top), 0.0f);
                }
                return Unit.INSTANCE;
            }
        });
    }

    private PaddingNode(float f, float f2, float f3, float f4, boolean z) {
        this.start = f;
        this.top = f2;
        this.end = f3;
        this.bottom = f4;
        this.rtlAware = z;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public PaddingNode(float f, float f2, float f3, float f4, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            f = 0;
            Dp.Companion companion = Dp.Companion;
        }
        float f5 = f;
        if ((i & 2) != 0) {
            f2 = 0;
            Dp.Companion companion2 = Dp.Companion;
        }
        float f6 = f2;
        if ((i & 4) != 0) {
            f3 = 0;
            Dp.Companion companion3 = Dp.Companion;
        }
        float f7 = f3;
        if ((i & 8) != 0) {
            f4 = 0;
            Dp.Companion companion4 = Dp.Companion;
        }
        this(f5, f6, f7, f4, z, null);
    }
}
