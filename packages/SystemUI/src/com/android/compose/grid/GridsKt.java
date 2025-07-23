package com.android.compose.grid;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class GridsKt {
    /* renamed from: Grid-nSlTg7c, reason: not valid java name */
    public static final void m936GridnSlTg7c(final int i, Modifier modifier, final float f, float f2, ComposableLambdaImpl composableLambdaImpl, Composer composer, int i2) {
        int i3;
        float f3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1116050376);
        if ((i2 & 6) == 0) {
            i3 = (composerImpl.changed(i) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        final boolean z = true;
        if ((i2 & 48) == 0) {
            i3 |= composerImpl.changed(true) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i3 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i3 |= composerImpl.changed(f) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            f3 = f2;
            i3 |= composerImpl.changed(f3) ? 16384 : 8192;
        } else {
            f3 = f2;
        }
        if ((196608 & i2) == 0) {
            i3 |= composerImpl.changedInstance(composableLambdaImpl) ? 131072 : 65536;
        }
        int i4 = i3;
        if ((74899 & i4) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.grid.Grid (Grids.kt:89)");
            }
            if (i <= 0) {
                throw new IllegalStateException("Must provide a positive number of ".concat("columns").toString());
            }
            composerImpl.startReplaceGroup(1236933799);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = new GridsKt$Grid$sizeCache$1$1();
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final GridsKt$Grid$sizeCache$1$1 gridsKt$Grid$sizeCache$1$1 = (GridsKt$Grid$sizeCache$1$1) rememberedValue;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1236942994);
            boolean changedInstance = ((i4 & 14) == 4) | ((i4 & 112) == 32) | composerImpl.changedInstance(gridsKt$Grid$sizeCache$1$1) | ((57344 & i4) == 16384) | ((i4 & 7168) == 2048);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                final float f4 = f3;
                MeasurePolicy measurePolicy = new MeasurePolicy() { // from class: com.android.compose.grid.GridsKt$Grid$2$1
                    @Override // androidx.compose.ui.layout.MeasurePolicy
                    /* renamed from: measure-3p2s80s */
                    public final MeasureResult mo3measure3p2s80s(final MeasureScope measureScope, List list, long j) {
                        int ceil;
                        MeasureResult layout$1;
                        int i5;
                        int i6;
                        List list2 = list;
                        final int size = list2.size();
                        int i7 = i;
                        boolean z2 = z;
                        if (z2) {
                            ceil = i7;
                            i7 = (int) Math.ceil(size / i7);
                        } else {
                            ceil = (int) Math.ceil(size / i7);
                        }
                        GridsKt$Grid$sizeCache$1$1 gridsKt$Grid$sizeCache$1$12 = gridsKt$Grid$sizeCache$1$1;
                        if (gridsKt$Grid$sizeCache$1$12.rowHeights.length != i7) {
                            int[] iArr = new int[i7];
                            for (int i8 = 0; i8 < i7; i8++) {
                                iArr[i8] = 0;
                            }
                            gridsKt$Grid$sizeCache$1$12.rowHeights = iArr;
                        } else {
                            for (int i9 = 0; i9 < i7; i9++) {
                                gridsKt$Grid$sizeCache$1$12.rowHeights[i9] = 0;
                            }
                        }
                        if (gridsKt$Grid$sizeCache$1$12.columnWidths.length != ceil) {
                            int[] iArr2 = new int[ceil];
                            for (int i10 = 0; i10 < ceil; i10++) {
                                iArr2[i10] = 0;
                            }
                            gridsKt$Grid$sizeCache$1$12.columnWidths = iArr2;
                        } else {
                            for (int i11 = 0; i11 < ceil; i11++) {
                                gridsKt$Grid$sizeCache$1$12.columnWidths[i11] = 0;
                            }
                        }
                        int roundToInt = MathKt__MathJVMKt.roundToInt(measureScope.mo57toPx0680j_4(f4) * (ceil - 1));
                        int roundToInt2 = MathKt__MathJVMKt.roundToInt(measureScope.mo57toPx0680j_4(f) * (i7 - 1));
                        long Constraints$default = ConstraintsKt.Constraints$default(0, Constraints.m821getMaxWidthimpl(j) != Integer.MAX_VALUE ? (Constraints.m821getMaxWidthimpl(j) - roundToInt) / ceil : Integer.MAX_VALUE, 0, Constraints.m820getMaxHeightimpl(j) != Integer.MAX_VALUE ? (Constraints.m820getMaxHeightimpl(j) - roundToInt2) / i7 : Integer.MAX_VALUE, 5);
                        ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                        int size2 = list2.size();
                        int i12 = 0;
                        while (i12 < size2) {
                            if (z2) {
                                i5 = i12 % ceil;
                                i6 = i12 / ceil;
                            } else {
                                i5 = i12 / i7;
                                i6 = i12 % i7;
                            }
                            Placeable mo608measureBRTryo0 = ((Measurable) list2.get(i12)).mo608measureBRTryo0(Constraints$default);
                            int[] iArr3 = gridsKt$Grid$sizeCache$1$12.rowHeights;
                            iArr3[i6] = Math.max(iArr3[i6], mo608measureBRTryo0.height);
                            int[] iArr4 = gridsKt$Grid$sizeCache$1$12.columnWidths;
                            iArr4[i5] = Math.max(iArr4[i5], mo608measureBRTryo0.width);
                            createListBuilder.add(mo608measureBRTryo0);
                            i12++;
                            list2 = list;
                            i7 = i7;
                        }
                        final int i13 = i7;
                        final ListBuilder build = createListBuilder.build();
                        int length = gridsKt$Grid$sizeCache$1$12.columnWidths.length;
                        for (int i14 = 0; i14 < length; i14++) {
                            roundToInt += gridsKt$Grid$sizeCache$1$12.columnWidths[i14];
                        }
                        int length2 = gridsKt$Grid$sizeCache$1$12.rowHeights.length;
                        for (int i15 = 0; i15 < length2; i15++) {
                            roundToInt2 += gridsKt$Grid$sizeCache$1$12.rowHeights[i15];
                        }
                        final float f5 = f;
                        final float f6 = f4;
                        final int i16 = ceil;
                        layout$1 = measureScope.layout$1(roundToInt, roundToInt2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.compose.grid.GridsKt$Grid$2$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj) {
                                MeasureScope measureScope2;
                                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                                int i17 = 0;
                                for (int i18 = 0; i18 < i13; i18++) {
                                    int i19 = 0;
                                    int i20 = 0;
                                    int i21 = 0;
                                    while (true) {
                                        int i22 = i16;
                                        measureScope2 = measureScope;
                                        if (i19 < i22) {
                                            int i23 = (i22 * i18) + i19;
                                            if (i23 < size) {
                                                Placeable placeable = (Placeable) build.get(i23);
                                                placementScope.placeRelative(placeable, i21, i17, 0.0f);
                                                i21 += measureScope2.mo51roundToPx0680j_4(f6) + placeable.width;
                                                i20 = Math.max(i20, placeable.height);
                                            }
                                            i19++;
                                        }
                                    }
                                    i17 += measureScope2.mo51roundToPx0680j_4(f5) + i20;
                                }
                                return Unit.INSTANCE;
                            }
                        });
                        return layout$1;
                    }
                };
                composerImpl.updateRememberedValue(measurePolicy);
                rememberedValue2 = measurePolicy;
            }
            MeasurePolicy measurePolicy2 = (MeasurePolicy) rememberedValue2;
            composerImpl.end(false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            int i5 = (((((i4 >> 15) & 14) | ((i4 >> 3) & 112)) << 6) & 896) | 6;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, measurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            composableLambdaImpl.invoke(composerImpl, Integer.valueOf((i5 >> 6) & 14));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new GridsKt$$ExternalSyntheticLambda0(i, modifier, f, f2, composableLambdaImpl, i2, 1);
        }
    }

    /* renamed from: VerticalGrid-vz2T9sI, reason: not valid java name */
    public static final void m937VerticalGridvz2T9sI(int i, Modifier modifier, float f, float f2, ComposableLambdaImpl composableLambdaImpl, Composer composer, int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(420445518);
        if ((i2 & 6) == 0) {
            i3 = (composerImpl.changed(i) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            i3 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i3 |= composerImpl.changed(f) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i3 |= composerImpl.changed(f2) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            i3 |= composerImpl.changedInstance(composableLambdaImpl) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i3 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.grid.VerticalGrid (Grids.kt:44)");
            }
            int i4 = (i3 & 14) | 48;
            int i5 = i3 << 3;
            m936GridnSlTg7c(i, modifier, f, f2, composableLambdaImpl, composerImpl, i4 | (i5 & 896) | (i5 & 7168) | (57344 & i5) | (i5 & 458752));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new GridsKt$$ExternalSyntheticLambda0(i, modifier, f, f2, composableLambdaImpl, i2, 0);
        }
    }
}
