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

/* loaded from: classes.dex */
public abstract class GridsKt {
    /* renamed from: Grid-nSlTg7c, reason: not valid java name */
    public static final void m938GridnSlTg7c(final int i, Modifier modifier, final float f, float f2, ComposableLambdaImpl composableLambdaImpl, Composer composer, int i2) {
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
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = new GridsKt$Grid$sizeCache$1$1();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            final GridsKt$Grid$sizeCache$1$1 gridsKt$Grid$sizeCache$1$1 = (GridsKt$Grid$sizeCache$1$1) objRememberedValue;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1236942994);
            boolean zChangedInstance = ((i4 & 14) == 4) | ((i4 & 112) == 32) | composerImpl.changedInstance(gridsKt$Grid$sizeCache$1$1) | ((57344 & i4) == 16384) | ((i4 & 7168) == 2048);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                final float f4 = f3;
                MeasurePolicy measurePolicy = new MeasurePolicy() { // from class: com.android.compose.grid.GridsKt$Grid$2$1
                    @Override // androidx.compose.ui.layout.MeasurePolicy
                    /* renamed from: measure-3p2s80s */
                    public final MeasureResult mo3measure3p2s80s(final MeasureScope measureScope, List list, long j) {
                        int iCeil;
                        int i5;
                        int i6;
                        List list2 = list;
                        final int size = list2.size();
                        int iCeil2 = i;
                        boolean z2 = z;
                        if (z2) {
                            iCeil = iCeil2;
                            iCeil2 = (int) Math.ceil(size / iCeil2);
                        } else {
                            iCeil = (int) Math.ceil(size / iCeil2);
                        }
                        GridsKt$Grid$sizeCache$1$1 gridsKt$Grid$sizeCache$1$12 = gridsKt$Grid$sizeCache$1$1;
                        if (gridsKt$Grid$sizeCache$1$12.rowHeights.length != iCeil2) {
                            int[] iArr = new int[iCeil2];
                            for (int i7 = 0; i7 < iCeil2; i7++) {
                                iArr[i7] = 0;
                            }
                            gridsKt$Grid$sizeCache$1$12.rowHeights = iArr;
                        } else {
                            for (int i8 = 0; i8 < iCeil2; i8++) {
                                gridsKt$Grid$sizeCache$1$12.rowHeights[i8] = 0;
                            }
                        }
                        if (gridsKt$Grid$sizeCache$1$12.columnWidths.length != iCeil) {
                            int[] iArr2 = new int[iCeil];
                            for (int i9 = 0; i9 < iCeil; i9++) {
                                iArr2[i9] = 0;
                            }
                            gridsKt$Grid$sizeCache$1$12.columnWidths = iArr2;
                        } else {
                            for (int i10 = 0; i10 < iCeil; i10++) {
                                gridsKt$Grid$sizeCache$1$12.columnWidths[i10] = 0;
                            }
                        }
                        int iRoundToInt = MathKt__MathJVMKt.roundToInt(measureScope.mo58toPx0680j_4(f4) * (iCeil - 1));
                        int iRoundToInt2 = MathKt__MathJVMKt.roundToInt(measureScope.mo58toPx0680j_4(f) * (iCeil2 - 1));
                        long jConstraints$default = ConstraintsKt.Constraints$default(0, Constraints.m823getMaxWidthimpl(j) != Integer.MAX_VALUE ? (Constraints.m823getMaxWidthimpl(j) - iRoundToInt) / iCeil : Integer.MAX_VALUE, 0, Constraints.m822getMaxHeightimpl(j) != Integer.MAX_VALUE ? (Constraints.m822getMaxHeightimpl(j) - iRoundToInt2) / iCeil2 : Integer.MAX_VALUE, 5);
                        ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                        int size2 = list2.size();
                        int i11 = 0;
                        while (i11 < size2) {
                            if (z2) {
                                i5 = i11 % iCeil;
                                i6 = i11 / iCeil;
                            } else {
                                i5 = i11 / iCeil2;
                                i6 = i11 % iCeil2;
                            }
                            Placeable placeableMo610measureBRTryo0 = ((Measurable) list2.get(i11)).mo610measureBRTryo0(jConstraints$default);
                            int[] iArr3 = gridsKt$Grid$sizeCache$1$12.rowHeights;
                            iArr3[i6] = Math.max(iArr3[i6], placeableMo610measureBRTryo0.height);
                            int[] iArr4 = gridsKt$Grid$sizeCache$1$12.columnWidths;
                            iArr4[i5] = Math.max(iArr4[i5], placeableMo610measureBRTryo0.width);
                            listBuilderCreateListBuilder.add(placeableMo610measureBRTryo0);
                            i11++;
                            list2 = list;
                            iCeil2 = iCeil2;
                        }
                        final int i12 = iCeil2;
                        final ListBuilder listBuilderBuild = listBuilderCreateListBuilder.build();
                        int length = gridsKt$Grid$sizeCache$1$12.columnWidths.length;
                        for (int i13 = 0; i13 < length; i13++) {
                            iRoundToInt += gridsKt$Grid$sizeCache$1$12.columnWidths[i13];
                        }
                        int length2 = gridsKt$Grid$sizeCache$1$12.rowHeights.length;
                        for (int i14 = 0; i14 < length2; i14++) {
                            iRoundToInt2 += gridsKt$Grid$sizeCache$1$12.rowHeights[i14];
                        }
                        final float f5 = f;
                        final float f6 = f4;
                        final int i15 = iCeil;
                        return measureScope.layout$1(iRoundToInt, iRoundToInt2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.compose.grid.GridsKt$Grid$2$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                MeasureScope measureScope2;
                                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                                int iMo52roundToPx0680j_4 = 0;
                                for (int i16 = 0; i16 < i12; i16++) {
                                    int i17 = 0;
                                    int iMax = 0;
                                    int iMo52roundToPx0680j_42 = 0;
                                    while (true) {
                                        int i18 = i15;
                                        measureScope2 = measureScope;
                                        if (i17 < i18) {
                                            int i19 = (i18 * i16) + i17;
                                            if (i19 < size) {
                                                Placeable placeable = (Placeable) listBuilderBuild.get(i19);
                                                placementScope.placeRelative(placeable, iMo52roundToPx0680j_42, iMo52roundToPx0680j_4, 0.0f);
                                                iMo52roundToPx0680j_42 += measureScope2.mo52roundToPx0680j_4(f6) + placeable.width;
                                                iMax = Math.max(iMax, placeable.height);
                                            }
                                            i17++;
                                        }
                                    }
                                    iMo52roundToPx0680j_4 += measureScope2.mo52roundToPx0680j_4(f5) + iMax;
                                }
                                return Unit.INSTANCE;
                            }
                        });
                    }
                };
                composerImpl.updateRememberedValue(measurePolicy);
                objRememberedValue2 = measurePolicy;
            }
            MeasurePolicy measurePolicy2 = (MeasurePolicy) objRememberedValue2;
            composerImpl.end(false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            Updater.m337setimpl(composerImpl, measurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            composableLambdaImpl.invoke(composerImpl, Integer.valueOf((i5 >> 6) & 14));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new GridsKt$$ExternalSyntheticLambda0(i, modifier, f, f2, composableLambdaImpl, i2, 1);
        }
    }

    /* renamed from: VerticalGrid-vz2T9sI, reason: not valid java name */
    public static final void m939VerticalGridvz2T9sI(int i, Modifier modifier, float f, float f2, ComposableLambdaImpl composableLambdaImpl, Composer composer, int i2) {
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
            m938GridnSlTg7c(i, modifier, f, f2, composableLambdaImpl, composerImpl, i4 | (i5 & 896) | (i5 & 7168) | (57344 & i5) | (i5 & 458752));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new GridsKt$$ExternalSyntheticLambda0(i, modifier, f, f2, composableLambdaImpl, i2, 0);
        }
    }
}
