package com.android.systemui.grid.ui.compose;

import androidx.collection.IntIntPair;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.semantics.CollectionInfo;
import androidx.compose.ui.semantics.CollectionItemInfo;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.KProperty;

/* loaded from: classes2.dex */
public abstract class SpannedGridsKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0145  */
    /* JADX WARN: Type inference failed for: r13v14 */
    /* JADX WARN: Type inference failed for: r13v15, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v16 */
    /* renamed from: SpannedGrid-eWuZFaY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m2583SpannedGrideWuZFaY(final int i, final float f, final float f2, final List list, final Modifier modifier, final Function1 function1, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i2) {
        boolean z;
        Object obj;
        int i3;
        int i4;
        ?? r13;
        char c;
        ComposerImpl composerImpl;
        int i5 = i;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1800887913);
        int i6 = (i2 & 6) == 0 ? (composerImpl2.changed(i5) ? 4 : 2) | i2 : i2;
        if ((i2 & 48) == 0) {
            i6 |= composerImpl2.changed(f) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i6 |= composerImpl2.changed(f2) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i6 |= composerImpl2.changedInstance(list) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            i6 |= composerImpl2.changed(true) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i2) == 0) {
            i6 |= composerImpl2.changed(modifier) ? 131072 : 65536;
        }
        if ((1572864 & i2) == 0) {
            i6 |= composerImpl2.changedInstance(function1) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((12582912 & i2) == 0) {
            i6 |= composerImpl2.changedInstance(composableLambdaImpl) ? 8388608 : 4194304;
        }
        if ((i6 & 4793491) == 4793490 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.grid.ui.compose.SpannedGrid (SpannedGrids.kt:142)");
            }
            Arrangement.INSTANCE.getClass();
            final Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(f);
            int i7 = 0;
            for (Object obj2 : list) {
                int i8 = i7 + 1;
                if (i7 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                int iIntValue = ((Number) obj2).intValue();
                if (1 > iIntValue || iIntValue > i5) {
                    throw new IllegalStateException(ReorderTile$$ExternalSyntheticOutline0.m(i5, "]", MutableObjectList$$ExternalSyntheticOutline0.m(i7, iIntValue, "Span out of bounds. Span at index ", " has value of ", " which is outside of the expected rance of [1, ")).toString());
                }
                i7 = i8;
            }
            float f3 = 0;
            Dp.Companion companion = Dp.Companion;
            if (Float.compare(f, f3) < 0) {
                throw new IllegalStateException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Negative columnSpacing ", Dp.m839toStringimpl(f)).toString());
            }
            if (Float.compare(f2, f3) < 0) {
                throw new IllegalStateException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Negative rowSpacing ", Dp.m839toStringimpl(f2)).toString());
            }
            int size = list.size();
            composerImpl2.startReplaceGroup(829574491);
            boolean zChanged = composerImpl2.changed(size);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion companion2 = Composer.Companion;
            if (!zChanged) {
                companion2.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    int size2 = list.size();
                    IntIntPair[] intIntPairArr = new IntIntPair[size2];
                    z = true;
                    for (int i9 = 0; i9 < size2; i9++) {
                        intIntPairArr[i9] = IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(0, 0));
                    }
                    composerImpl2.updateRememberedValue(intIntPairArr);
                    obj = intIntPairArr;
                } else {
                    z = true;
                    obj = objRememberedValue;
                }
                final IntIntPair[] intIntPairArr2 = (IntIntPair[]) obj;
                composerImpl2.end(false);
                composerImpl2.startReplaceGroup(829578159);
                int i10 = i6 & 14;
                boolean zChanged2 = (i10 == 4 ? z : false) | composerImpl2.changed(list);
                Object objRememberedValue2 = composerImpl2.rememberedValue();
                if (!zChanged2) {
                    companion2.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        int size3 = list.size();
                        int i11 = 0;
                        int i12 = 0;
                        int i13 = 0;
                        while (i11 < size3) {
                            int iIntValue2 = ((Number) list.get(i11)).intValue();
                            if (i13 + iIntValue2 > i5) {
                                i12++;
                                i3 = 0;
                            } else {
                                i3 = i13;
                            }
                            int i14 = i12;
                            intIntPairArr2[i11] = IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(i14, i3));
                            i13 = i3 + iIntValue2;
                            i11++;
                            i12 = i14;
                            i5 = i;
                        }
                        objRememberedValue2 = Integer.valueOf(i12 + 1);
                        composerImpl2.updateRememberedValue(objRememberedValue2);
                    }
                    final int iIntValue3 = ((Number) objRememberedValue2).intValue();
                    composerImpl2.end(false);
                    composerImpl2.startReplaceGroup(829593971);
                    Object objRememberedValue3 = composerImpl2.rememberedValue();
                    companion2.getClass();
                    Object obj3 = Composer.Companion.Empty;
                    if (objRememberedValue3 == obj3) {
                        objRememberedValue3 = new SpannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$1();
                        composerImpl2.updateRememberedValue(objRememberedValue3);
                    }
                    final SpannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$1 spannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$1 = (SpannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$1) objRememberedValue3;
                    composerImpl2.end(false);
                    composerImpl2.startReplaceGroup(829629582);
                    boolean zChangedInstance = composerImpl2.changedInstance(list);
                    Object objRememberedValue4 = composerImpl2.rememberedValue();
                    if (zChangedInstance || objRememberedValue4 == obj3) {
                        objRememberedValue4 = new Function1() { // from class: com.android.systemui.grid.ui.compose.SpannedGridsKt$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj4) {
                                CollectionInfo collectionInfo = new CollectionInfo(list.size(), 1);
                                KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                                SemanticsProperties.INSTANCE.getClass();
                                SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.CollectionInfo;
                                KProperty kProperty = SemanticsPropertiesKt.$$delegatedProperties[20];
                                semanticsPropertyKey.setValue((SemanticsPropertyReceiver) obj4, collectionInfo);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl2.updateRememberedValue(objRememberedValue4);
                    }
                    composerImpl2.end(false);
                    Modifier modifierSemantics = SemanticsModifierKt.semantics(modifier, false, (Function1) objRememberedValue4);
                    composerImpl2.startReplaceGroup(829635739);
                    int i15 = i6 & 57344;
                    boolean zChangedInstance2 = (i10 == 4 ? z : false) | (i15 == 16384 ? z : false) | composerImpl2.changedInstance(list) | composerImpl2.changedInstance(spannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$1) | ((i6 & 112) == 32 ? z : false) | composerImpl2.changed(spacedAlignedM92spacedBy0680j_4) | ((i6 & 896) == 256 ? z : false) | composerImpl2.changed(iIntValue3) | composerImpl2.changedInstance(intIntPairArr2);
                    Object objRememberedValue5 = composerImpl2.rememberedValue();
                    if (zChangedInstance2 || objRememberedValue5 == obj3) {
                        i4 = i6;
                        r13 = 0;
                        c = ' ';
                        final boolean z2 = true;
                        MeasurePolicy measurePolicy = new MeasurePolicy() { // from class: com.android.systemui.grid.ui.compose.SpannedGridsKt$SpannedGrid$9$1
                            @Override // androidx.compose.ui.layout.MeasurePolicy
                            /* renamed from: measure-3p2s80s */
                            public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list2, long j) {
                                int i16;
                                if (list2.size() != list.size()) {
                                    throw new IllegalStateException("Check failed.");
                                }
                                boolean z3 = z2;
                                int iM823getMaxWidthimpl = z3 ? Constraints.m823getMaxWidthimpl(j) : Constraints.m822getMaxHeightimpl(j);
                                if (iM823getMaxWidthimpl == Integer.MAX_VALUE) {
                                    throw new IllegalStateException("Width must be constrained");
                                }
                                SpannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$1 spannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$12 = spannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$1;
                                int length = spannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$12.sizes.length;
                                int i17 = i;
                                if (length != i17) {
                                    spannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$12.sizes = new int[i17];
                                    spannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$12.positions = new int[i17];
                                }
                                int iMo52roundToPx0680j_4 = measureScope.mo52roundToPx0680j_4(f);
                                int[] iArr = spannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$12.sizes;
                                if (iArr.length != i17) {
                                    throw new IllegalStateException("Check failed.");
                                }
                                int i18 = iM823getMaxWidthimpl - ((i17 - 1) * iMo52roundToPx0680j_4);
                                int i19 = i18 / i17;
                                int i20 = i18 % i17;
                                IntProgressionIterator it = ArraysKt___ArraysKt.getIndices(iArr).iterator();
                                while (true) {
                                    int i21 = 0;
                                    if (!it.hasNext) {
                                        break;
                                    }
                                    int iNextInt = it.nextInt();
                                    if (iNextInt < i20) {
                                        i21 = 1;
                                    }
                                    iArr[iNextInt] = i21 + i19;
                                }
                                int[] iArr2 = spannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$12.sizes;
                                spacedAlignedM92spacedBy0680j_4.arrange(measureScope, iM823getMaxWidthimpl, iArr2, LayoutDirection.Ltr, spannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$12.positions);
                                int[] iArr3 = spannedGridsKt$SpannedGrid$slotPositionsAndSizesCache$1$12.positions;
                                final int iMo52roundToPx0680j_42 = measureScope.mo52roundToPx0680j_4(f2);
                                int i22 = iIntValue3;
                                int i23 = (i22 - 1) * iMo52roundToPx0680j_42;
                                int iM822getMaxHeightimpl = z3 ? Constraints.m822getMaxHeightimpl(j) : Constraints.m823getMaxWidthimpl(j);
                                int iMax = iM822getMaxHeightimpl != Integer.MAX_VALUE ? Math.max(0, (iM822getMaxHeightimpl - i23) / i22) : Integer.MAX_VALUE;
                                List list3 = list;
                                final ArrayList arrayList = new ArrayList(list2.size());
                                int size4 = list2.size();
                                int i24 = 0;
                                int i25 = 0;
                                int i26 = 0;
                                while (i24 < size4) {
                                    Measurable measurable = (Measurable) list2.get(i24);
                                    int iIntValue4 = ((Number) list3.get(i24)).intValue();
                                    int[] iArr4 = iArr3;
                                    int i27 = iM823getMaxWidthimpl;
                                    int[] iArr5 = iArr2;
                                    long j2 = intIntPairArr2[i24].packedValue;
                                    int i28 = (int) (j2 & 4294967295L);
                                    if (iIntValue4 == 1) {
                                        i16 = iArr5[i28];
                                    } else {
                                        int i29 = (iIntValue4 + i28) - 1;
                                        i16 = (iArr4[i29] + iArr5[i29]) - iArr4[i28];
                                    }
                                    if (i16 < 0) {
                                        i16 = 0;
                                    }
                                    Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(z3 ? ConstraintsKt.Constraints$default(i16, i16, 0, iMax, 4) : ConstraintsKt.Constraints$default(0, iMax, i16, i16, 1));
                                    int iMax2 = z3 ? placeableMo610measureBRTryo0.height : placeableMo610measureBRTryo0.width;
                                    int i30 = (int) (j2 >> 32);
                                    int i31 = i26;
                                    if (i30 != i31) {
                                        i23 += i25;
                                        i26 = i30;
                                    } else {
                                        iMax2 = Math.max(i25, iMax2);
                                        i26 = i31;
                                    }
                                    i25 = iMax2;
                                    arrayList.add(placeableMo610measureBRTryo0);
                                    i24++;
                                    iArr3 = iArr4;
                                    iM823getMaxWidthimpl = i27;
                                    iArr2 = iArr5;
                                }
                                final int[] iArr6 = iArr3;
                                int i32 = iM823getMaxWidthimpl;
                                int i33 = i23 + i25;
                                final boolean z4 = z2;
                                int i34 = z4 ? i33 : i32;
                                if (z4) {
                                    i33 = i32;
                                }
                                final IntIntPair[] intIntPairArr3 = intIntPairArr2;
                                return measureScope.layout$1(i33, i34, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.grid.ui.compose.SpannedGridsKt$SpannedGrid$9$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj4) {
                                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj4;
                                        int i35 = 0;
                                        int i36 = 0;
                                        int iMax3 = 0;
                                        int i37 = 0;
                                        for (Object obj5 : arrayList) {
                                            int i38 = i35 + 1;
                                            if (i35 < 0) {
                                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                                throw null;
                                            }
                                            Placeable placeable = (Placeable) obj5;
                                            long j3 = intIntPairArr3[i35].packedValue;
                                            int i39 = (int) (4294967295L & j3);
                                            boolean z5 = z4;
                                            int i40 = z5 ? placeable.height : placeable.width;
                                            int i41 = (int) (j3 >> 32);
                                            if (i41 != i36) {
                                                i37 = iMax3 + iMo52roundToPx0680j_42 + i37;
                                                i36 = i41;
                                                iMax3 = i40;
                                            } else {
                                                iMax3 = Math.max(iMax3, i40);
                                            }
                                            int[] iArr7 = iArr6;
                                            placementScope.placeRelative(placeable, z5 ? iArr7[i39] : i37, z5 ? i37 : iArr7[i39], 0.0f);
                                            i35 = i38;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                });
                            }
                        };
                        composerImpl2.updateRememberedValue(measurePolicy);
                        objRememberedValue5 = measurePolicy;
                    } else {
                        i4 = i6;
                        r13 = 0;
                        c = ' ';
                    }
                    MeasurePolicy measurePolicy2 = (MeasurePolicy) objRememberedValue5;
                    composerImpl2.end(r13);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierSemantics);
                    ComposeUiNode.Companion.getClass();
                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                    Applier applier = composerImpl2.applier;
                    if (applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl2.startReusableNode();
                    if (composerImpl2.inserting) {
                        composerImpl2.createNode(function0);
                    } else {
                        composerImpl2.useNode();
                    }
                    Updater.m337setimpl(composerImpl2, measurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
                    }
                    Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                    composerImpl2.startReplaceGroup(-1400325536);
                    IntRange intRangeUntil = RangesKt___RangesKt.until(r13, list.size());
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(intRangeUntil, 10));
                    IntProgressionIterator it = intRangeUntil.iterator();
                    ComposerImpl composerImpl3 = composerImpl2;
                    while (it.hasNext) {
                        final int iNextInt = it.nextInt();
                        composerImpl3.startMovableGroup(1615839246, function1.mo781invoke(Integer.valueOf(iNextInt)));
                        Modifier.Companion companion3 = Modifier.Companion;
                        composerImpl3.startReplaceGroup(1615841657);
                        boolean zChanged3 = composerImpl3.changed(iNextInt) | (i15 == 16384 ? z : r13);
                        Object objRememberedValue6 = composerImpl3.rememberedValue();
                        if (zChanged3 || objRememberedValue6 == Composer.Companion.Empty) {
                            objRememberedValue6 = new Function1() { // from class: com.android.systemui.grid.ui.compose.SpannedGridsKt$$ExternalSyntheticLambda2
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj4) {
                                    CollectionItemInfo collectionItemInfo = new CollectionItemInfo(iNextInt, 1, 0, 1);
                                    KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                                    SemanticsProperties.INSTANCE.getClass();
                                    SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.CollectionItemInfo;
                                    KProperty kProperty = SemanticsPropertiesKt.$$delegatedProperties[21];
                                    semanticsPropertyKey.setValue((SemanticsPropertyReceiver) obj4, collectionItemInfo);
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl3.updateRememberedValue(objRememberedValue6);
                        }
                        composerImpl3.end(r13);
                        Modifier modifierSemantics2 = SemanticsModifierKt.semantics(companion3, r13, (Function1) objRememberedValue6);
                        Alignment.Companion.getClass();
                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, r13);
                        int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl3, modifierSemantics2);
                        ComposeUiNode.Companion.getClass();
                        Function0 function02 = ComposeUiNode.Companion.Constructor;
                        if (applier == null) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl3.startReusableNode();
                        if (composerImpl3.inserting) {
                            composerImpl3.createNode(function02);
                        } else {
                            composerImpl3.useNode();
                        }
                        Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope2, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function22);
                        }
                        Updater.m337setimpl(composerImpl3, modifierMaterializeModifier2, ComposeUiNode.Companion.SetModifier);
                        Object obj4 = BoxScopeInstance.INSTANCE;
                        long j = intIntPairArr2[iNextInt].packedValue;
                        Object objValueOf = Integer.valueOf(iNextInt);
                        int i16 = (int) (4294967295L & j);
                        Object objValueOf2 = Integer.valueOf(i16);
                        Object objValueOf3 = Boolean.valueOf(i16 == 0 ? z : r13);
                        IntIntPair intIntPair = (IntIntPair) ArraysKt___ArraysKt.getOrNull(iNextInt + 1, intIntPairArr2);
                        ComposerImpl composerImpl4 = composerImpl3;
                        IntProgressionIterator intProgressionIterator = it;
                        composableLambdaImpl.invoke(obj4, objValueOf, objValueOf2, objValueOf3, Boolean.valueOf(!((intIntPair == null || ((int) (intIntPair.packedValue >> c)) != ((int) (j >> c))) ? r13 : z)), composerImpl4, Integer.valueOf(6 | (458752 & (i4 >> 6))));
                        boolean z3 = z;
                        composerImpl4.end(z3);
                        composerImpl4.end(r13);
                        arrayList.add(Unit.INSTANCE);
                        z = z3;
                        it = intProgressionIterator;
                        composerImpl3 = composerImpl4;
                    }
                    composerImpl = composerImpl3;
                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, r13, z)) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.grid.ui.compose.SpannedGridsKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj5, Object obj6) {
                    ((Integer) obj6).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i2 | 1);
                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                    SpannedGridsKt.m2583SpannedGrideWuZFaY(i, f, f2, list, modifier, function1, composableLambdaImpl2, (Composer) obj5, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    /* renamed from: VerticalSpannedGrid-KhTvWYU, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m2584VerticalSpannedGridKhTvWYU(final int i, final float f, final float f2, final List list, Modifier modifier, final Function1 function1, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i2, final int i3) {
        Modifier modifier2;
        int i4;
        ComposerImpl composerImpl;
        final Modifier modifier3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1342815023);
        int i5 = (composerImpl2.changed(i) ? 4 : 2) | i2 | (composerImpl2.changed(f) ? 32 : 16) | (composerImpl2.changed(f2) ? 256 : 128) | (composerImpl2.changedInstance(list) ? 2048 : 1024);
        int i6 = i3 & 16;
        if (i6 == 0) {
            if ((i2 & 24576) == 0) {
                modifier2 = modifier;
                i5 |= composerImpl2.changed(modifier2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
            }
            i4 = i5 | (!composerImpl2.changedInstance(function1) ? 131072 : 65536);
            if ((599187 & i4) == 599186 || !composerImpl2.getSkipping()) {
                Modifier modifier4 = i6 == 0 ? Modifier.Companion : modifier2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.grid.ui.compose.VerticalSpannedGrid (SpannedGrids.kt:117)");
                }
                int i7 = (i4 & 14) | 24576 | (i4 & 112) | (i4 & 896) | (i4 & 7168);
                int i8 = i4 << 3;
                composerImpl = composerImpl2;
                m2583SpannedGrideWuZFaY(i, f, f2, list, modifier4, function1, composableLambdaImpl, composerImpl, (i8 & 3670016) | i7 | (458752 & i8) | 12582912);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                modifier3 = modifier4;
            } else {
                composerImpl2.skipToGroupEnd();
                composerImpl = composerImpl2;
                modifier3 = modifier2;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.grid.ui.compose.SpannedGridsKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i2 | 1);
                        ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                        SpannedGridsKt.m2584VerticalSpannedGridKhTvWYU(i, f, f2, list, modifier3, function1, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags, i3);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i5 |= 24576;
        modifier2 = modifier;
        i4 = i5 | (!composerImpl2.changedInstance(function1) ? 131072 : 65536);
        if ((599187 & i4) == 599186) {
            if (i6 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            int i72 = (i4 & 14) | 24576 | (i4 & 112) | (i4 & 896) | (i4 & 7168);
            int i82 = i4 << 3;
            composerImpl = composerImpl2;
            m2583SpannedGrideWuZFaY(i, f, f2, list, modifier4, function1, composableLambdaImpl, composerImpl, (i82 & 3670016) | i72 | (458752 & i82) | 12582912);
            if (ComposerKt.isTraceInProgress()) {
            }
            modifier3 = modifier4;
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
