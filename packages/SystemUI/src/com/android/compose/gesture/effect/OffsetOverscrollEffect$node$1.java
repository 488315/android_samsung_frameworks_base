package com.android.compose.gesture.effect;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import com.android.compose.gesture.effect.OffsetOverscrollEffect;
import com.android.compose.gesture.effect.ProgressConverter;
import com.android.compose.ui.util.SpaceVectorConverter;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class OffsetOverscrollEffect$node$1 extends Modifier.Node implements LayoutModifierNode {
    public final /* synthetic */ OffsetOverscrollEffect this$0;

    public OffsetOverscrollEffect$node$1(OffsetOverscrollEffect offsetOverscrollEffect) {
        this.this$0 = offsetOverscrollEffect;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(final MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
        int i = mo608measureBRTryo0.width;
        int i2 = mo608measureBRTryo0.height;
        final OffsetOverscrollEffect offsetOverscrollEffect = this.this$0;
        layout$1 = measureScope.layout$1(i, i2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.compose.gesture.effect.OffsetOverscrollEffect$node$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                OffsetOverscrollEffect.Companion companion = OffsetOverscrollEffect.Companion;
                OffsetOverscrollEffect offsetOverscrollEffect2 = offsetOverscrollEffect;
                float floatValue = ((Number) offsetOverscrollEffect2.animatable.internalState.getValue()).floatValue();
                companion.getClass();
                float mo57toPx0680j_4 = MeasureScope.this.mo57toPx0680j_4(OffsetOverscrollEffect.MaxDistance);
                ProgressConverter.Companion.getClass();
                ProgressConverter$Companion$tanh$1 progressConverter$Companion$tanh$1 = ProgressConverter.Companion.Default;
                float f = progressConverter$Companion$tanh$1.$tilt;
                int roundToInt = MathKt__MathJVMKt.roundToInt(progressConverter$Companion$tanh$1.$maxProgress * ((float) Math.tanh((floatValue / mo57toPx0680j_4) / (f * r3))) * mo57toPx0680j_4);
                Placeable placeable = mo608measureBRTryo0;
                if (roundToInt != 0) {
                    SpaceVectorConverter spaceVectorConverter = offsetOverscrollEffect2.lastConverter;
                    if (spaceVectorConverter == null) {
                        throw new IllegalStateException("lastConverter is null, make sure to call requireConverter() only when overscrollDistance != 0f");
                    }
                    Placeable.PlacementScope.m629placeWithLayeraW9wM$default(placementScope, placeable, spaceVectorConverter.mo916toIntOffsetBjo55l4(roundToInt), null, 6);
                } else {
                    placementScope.place(placeable, 0, 0, 0.0f);
                }
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }
}
