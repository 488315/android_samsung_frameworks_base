package com.android.systemui.dialog.ui.composable;

import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Dp;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes2.dex */
public final class AlertDialogContentKt$AlertDialogButtons$2$1 implements MeasurePolicy {
    public static final AlertDialogContentKt$AlertDialogButtons$2$1 INSTANCE = new AlertDialogContentKt$AlertDialogButtons$2$1();

    public static final void measure_3p2s80s$lambda$4$place(Placeable placeable, Placeable.PlacementScope placementScope, int i, Ref$IntRef ref$IntRef) {
        placementScope.placeRelative(placeable, i - placeable.width, ref$IntRef.element, 0.0f);
        ref$IntRef.element += placeable.height;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v7, types: [T, androidx.compose.ui.layout.Placeable] */
    /* JADX WARN: Type inference failed for: r6v8, types: [T, androidx.compose.ui.layout.Placeable] */
    /* JADX WARN: Type inference failed for: r6v9, types: [T, androidx.compose.ui.layout.Placeable] */
    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
        if (!Constraints.m819getHasBoundedWidthimpl(j)) {
            throw new IllegalStateException("AlertDialogButtons should not be composed in an horizontally scrollable layout");
        }
        final int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
        final Ref$ObjectRef ref$ObjectRef3 = new Ref$ObjectRef();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Measurable measurable = (Measurable) list.get(i);
            Object layoutId = LayoutIdKt.getLayoutId(measurable);
            if (Intrinsics.areEqual(layoutId, "positive")) {
                ref$ObjectRef.element = measurable.mo610measureBRTryo0(j);
            } else if (Intrinsics.areEqual(layoutId, "negative")) {
                ref$ObjectRef2.element = measurable.mo610measureBRTryo0(j);
            } else {
                if (!Intrinsics.areEqual(layoutId, "neutral")) {
                    throw new IllegalStateException(("Unexpected layoutId=" + layoutId).toString());
                }
                ref$ObjectRef3.element = measurable.mo610measureBRTryo0(j);
            }
        }
        Dp.Companion companion = Dp.Companion;
        final float fMo58toPx0680j_4 = measureScope.mo58toPx0680j_4(8);
        float size2 = (list.size() - 1) * fMo58toPx0680j_4;
        Placeable placeable = (Placeable) ref$ObjectRef.element;
        int i2 = placeable != null ? placeable.width : 0;
        Placeable placeable2 = (Placeable) ref$ObjectRef2.element;
        int i3 = i2 + (placeable2 != null ? placeable2.width : 0);
        Placeable placeable3 = (Placeable) ref$ObjectRef3.element;
        if (i3 + (placeable3 != null ? placeable3.width : 0) + size2 <= iM823getMaxWidthimpl) {
            return measureScope.layout$1(iM823getMaxWidthimpl, Math.max(placeable != null ? placeable.height : 0, Math.max(placeable2 != null ? placeable2.height : 0, placeable3 != null ? placeable3.height : 0)), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.dialog.ui.composable.AlertDialogContentKt$AlertDialogButtons$2$1$$ExternalSyntheticLambda0
                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                    AlertDialogContentKt$AlertDialogButtons$2$1 alertDialogContentKt$AlertDialogButtons$2$1 = AlertDialogContentKt$AlertDialogButtons$2$1.INSTANCE;
                    Ref$ObjectRef ref$ObjectRef4 = ref$ObjectRef;
                    Placeable placeable4 = (Placeable) ref$ObjectRef4.element;
                    int i4 = iM823getMaxWidthimpl;
                    if (placeable4 != null) {
                        placementScope.placeRelative(placeable4, i4 - placeable4.width, 0, 0.0f);
                    }
                    Placeable placeable5 = (Placeable) ref$ObjectRef2.element;
                    if (placeable5 != null) {
                        T t = ref$ObjectRef4.element;
                        if (t == 0) {
                            placementScope.placeRelative(placeable5, i4 - placeable5.width, 0, 0.0f);
                        } else {
                            placementScope.placeRelative(placeable5, ((i4 - placeable5.width) - ((Placeable) t).width) - MathKt__MathJVMKt.roundToInt(fMo58toPx0680j_4), 0, 0.0f);
                        }
                    }
                    Placeable placeable6 = (Placeable) ref$ObjectRef3.element;
                    if (placeable6 != null) {
                        placementScope.placeRelative(placeable6, 0, 0, 0.0f);
                    }
                    return Unit.INSTANCE;
                }
            });
        }
        return measureScope.layout$1(iM823getMaxWidthimpl, (placeable != null ? placeable.height : 0) + (placeable2 != null ? placeable2.height : 0) + (placeable3 != null ? placeable3.height : 0), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.dialog.ui.composable.AlertDialogContentKt$AlertDialogButtons$2$1$$ExternalSyntheticLambda1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                AlertDialogContentKt$AlertDialogButtons$2$1 alertDialogContentKt$AlertDialogButtons$2$1 = AlertDialogContentKt$AlertDialogButtons$2$1.INSTANCE;
                Ref$IntRef ref$IntRef = new Ref$IntRef();
                Placeable placeable4 = (Placeable) ref$ObjectRef.element;
                int i4 = iM823getMaxWidthimpl;
                if (placeable4 != null) {
                    AlertDialogContentKt$AlertDialogButtons$2$1.measure_3p2s80s$lambda$4$place(placeable4, placementScope, i4, ref$IntRef);
                }
                Placeable placeable5 = (Placeable) ref$ObjectRef2.element;
                if (placeable5 != null) {
                    AlertDialogContentKt$AlertDialogButtons$2$1.measure_3p2s80s$lambda$4$place(placeable5, placementScope, i4, ref$IntRef);
                }
                Placeable placeable6 = (Placeable) ref$ObjectRef3.element;
                if (placeable6 != null) {
                    AlertDialogContentKt$AlertDialogButtons$2$1.measure_3p2s80s$lambda$4$place(placeable6, placementScope, i4, ref$IntRef);
                }
                return Unit.INSTANCE;
            }
        });
    }
}
