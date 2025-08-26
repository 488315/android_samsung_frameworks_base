package androidx.compose.material3.internal;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
public abstract class AccessibilityUtilKt {
    public static final float HorizontalSemanticsBoundsPadding;
    public static final Modifier IncreaseHorizontalSemanticsBounds;
    public static final float VerticalSemanticsBoundsPadding;

    static {
        float f = 10;
        Dp.Companion companion = Dp.Companion;
        HorizontalSemanticsBoundsPadding = f;
        VerticalSemanticsBoundsPadding = f;
        Modifier.Companion companion2 = Modifier.Companion;
        IncreaseHorizontalSemanticsBounds = PaddingKt.m127paddingVpY3zN4$default(SemanticsModifierKt.semantics(LayoutModifierKt.layout(companion2, new Function3() { // from class: androidx.compose.material3.internal.AccessibilityUtilKt$IncreaseHorizontalSemanticsBounds$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                MeasureScope measureScope = (MeasureScope) obj;
                long j = ((Constraints) obj3).value;
                final int iMo52roundToPx0680j_4 = measureScope.mo52roundToPx0680j_4(AccessibilityUtilKt.HorizontalSemanticsBoundsPadding);
                int i = iMo52roundToPx0680j_4 * 2;
                final Placeable placeableMo610measureBRTryo0 = ((Measurable) obj2).mo610measureBRTryo0(ConstraintsKt.m835offsetNN6EwU(i, 0, j));
                return measureScope.layout$1(placeableMo610measureBRTryo0.width - i, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.internal.AccessibilityUtilKt$IncreaseHorizontalSemanticsBounds$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj4) {
                        ((Placeable.PlacementScope) obj4).place(placeableMo610measureBRTryo0, -iMo52roundToPx0680j_4, 0, 0.0f);
                        return Unit.INSTANCE;
                    }
                });
            }
        }), true, new Function1() { // from class: androidx.compose.material3.internal.AccessibilityUtilKt$IncreaseHorizontalSemanticsBounds$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                return Unit.INSTANCE;
            }
        }), f, 0.0f, 2);
        PaddingKt.m127paddingVpY3zN4$default(SemanticsModifierKt.semantics(LayoutModifierKt.layout(companion2, new Function3() { // from class: androidx.compose.material3.internal.AccessibilityUtilKt$IncreaseVerticalSemanticsBounds$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                MeasureScope measureScope = (MeasureScope) obj;
                long j = ((Constraints) obj3).value;
                final int iMo52roundToPx0680j_4 = measureScope.mo52roundToPx0680j_4(AccessibilityUtilKt.VerticalSemanticsBoundsPadding);
                int i = iMo52roundToPx0680j_4 * 2;
                final Placeable placeableMo610measureBRTryo0 = ((Measurable) obj2).mo610measureBRTryo0(ConstraintsKt.m835offsetNN6EwU(0, i, j));
                return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height - i, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.internal.AccessibilityUtilKt$IncreaseVerticalSemanticsBounds$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj4) {
                        ((Placeable.PlacementScope) obj4).place(placeableMo610measureBRTryo0, 0, -iMo52roundToPx0680j_4, 0.0f);
                        return Unit.INSTANCE;
                    }
                });
            }
        }), true, new Function1() { // from class: androidx.compose.material3.internal.AccessibilityUtilKt$IncreaseVerticalSemanticsBounds$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                return Unit.INSTANCE;
            }
        }), 0.0f, f, 1);
    }
}
