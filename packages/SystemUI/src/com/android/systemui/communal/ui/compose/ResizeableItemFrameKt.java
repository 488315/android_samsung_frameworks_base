package com.android.systemui.communal.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.gestures.AnchoredDraggableKt;
import androidx.compose.foundation.gestures.AnchoredDraggableState;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.grid.GridItemSpan;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.ZIndexModifierKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.communal.ui.viewmodel.DragHandle;
import com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class ResizeableItemFrameKt {
    /* JADX WARN: Removed duplicated region for block: B:112:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00fb  */
    /* renamed from: DragHandle--b7W0Lw, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m1087DragHandleb7W0Lw(final BoxScopeInstance boxScopeInstance, final DragHandle dragHandle, final AnchoredDraggableState anchoredDraggableState, final float f, final SolidColor solidColor, final Function0 function0, final Modifier modifier, Composer composer, final int i) {
        int i2;
        BiasAlignment biasAlignment;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1177231531);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(boxScopeInstance) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(dragHandle) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(anchoredDraggableState) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(f) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(solidColor) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl.changed(modifier) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((599187 & i2) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.DragHandle (ResizeableItemFrame.kt:126)");
            }
            DragHandle dragHandle2 = DragHandle.TOP;
            final int i3 = dragHandle == dragHandle2 ? -1 : 1;
            if (dragHandle == dragHandle2) {
                Alignment.Companion.getClass();
                biasAlignment = Alignment.Companion.TopCenter;
            } else {
                Alignment.Companion.getClass();
                biasAlignment = Alignment.Companion.BottomCenter;
            }
            Modifier modifierAlign = boxScopeInstance.align(modifier, biasAlignment);
            composerImpl.startReplaceGroup(964526213);
            int i4 = i2 & 7168;
            int i5 = i2 & 896;
            boolean zChanged = (i5 == 256) | composerImpl.changed(i3) | (i4 == 2048);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChanged) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function1() { // from class: com.android.systemui.communal.ui.compose.ResizeableItemFrameKt$$ExternalSyntheticLambda4
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj);
                            float density = ((reusableGraphicsLayerScope.graphicsDensity.getDensity() * f) + (Float.intBitsToFloat((int) (reusableGraphicsLayerScope.size & 4294967295L)) / 2)) * i3;
                            float floatValue = ((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue();
                            Float fValueOf = Float.valueOf(floatValue);
                            if ((Float.floatToRawIntBits(floatValue) & Integer.MAX_VALUE) >= 2139095040) {
                                fValueOf = null;
                            }
                            reusableGraphicsLayerScope.setTranslationY(density + (fValueOf != null ? fValueOf.floatValue() : 0.0f));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierAnchoredDraggable$default = AnchoredDraggableKt.anchoredDraggable$default(GraphicsLayerModifierKt.graphicsLayer(modifierAlign, (Function1) objRememberedValue), anchoredDraggableState, Orientation.Vertical, false, 60);
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierAnchoredDraggable$default);
                ComposeUiNode.Companion.getClass();
                Function0 function02 = ComposeUiNode.Companion.Constructor;
                if (composerImpl.applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function02);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                Modifier modifierFillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
                composerImpl.startReplaceGroup(-723171319);
                boolean z = ((57344 & i2) == 16384) | (i5 == 256) | (i4 == 2048) | ((458752 & i2) == 131072);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!z) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new Function1() { // from class: com.android.systemui.communal.ui.compose.ResizeableItemFrameKt$$ExternalSyntheticLambda5
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                DrawScope drawScope = (DrawScope) obj;
                                if (anchoredDraggableState.getAnchors().getSize() > 1) {
                                    float fMo58toPx0680j_4 = drawScope.mo58toPx0680j_4(f);
                                    float fIntBitsToFloat = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32));
                                    float f2 = 2;
                                    float fIntBitsToFloat2 = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) / f2;
                                    long jFloatToRawIntBits = Float.floatToRawIntBits(fIntBitsToFloat / f2);
                                    Offset.Companion companion2 = Offset.Companion;
                                    float fFloatValue = ((Number) function0.invoke()).floatValue();
                                    DrawScope.m533drawCircleV9BoPsw$default(drawScope, solidColor, fMo58toPx0680j_4, (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L) | (jFloatToRawIntBits << 32), fFloatValue, 0, 112);
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(false);
                    CanvasKt.Canvas(modifierFillMaxSize, (Function1) objRememberedValue2, composerImpl, 6);
                    composerImpl.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.ResizeableItemFrameKt$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SolidColor solidColor2 = solidColor;
                    Function0 function03 = function0;
                    Modifier modifier2 = modifier;
                    ResizeableItemFrameKt.m1087DragHandleb7W0Lw(boxScopeInstance, dragHandle, anchoredDraggableState, f, solidColor2, function03, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0202  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0337  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x03ac  */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r12v6, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r12v9 */
    /* renamed from: ResizableItemFrame-gFm42b4, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m1088ResizableItemFramegFm42b4(final String str, final long j, final LazyGridState lazyGridState, final PaddingValues paddingValues, final Arrangement.SpacedAligned spacedAligned, final Modifier modifier, final boolean z, float f, long j2, float f2, float f3, final int i, final int i2, final int i3, final Function0 function0, final ResizeableItemFrameViewModel resizeableItemFrameViewModel, final Function1 function1, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i4, final int i5) {
        int i6;
        float f4;
        int i7;
        final float f5;
        long j3;
        float f6;
        boolean z2;
        Object objDerivedStateOf;
        float f7;
        float f8;
        boolean z3;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2;
        final float f9;
        final float f10;
        final float f11;
        final long j4;
        Continuation continuation;
        ?? r12;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(-1859026123);
        if ((i4 & 6) == 0) {
            i6 = i4 | (composerImpl3.changed(str) ? 4 : 2);
        } else {
            i6 = i4;
        }
        if ((i4 & 48) == 0) {
            i6 |= composerImpl3.changed(j) ? 32 : 16;
        }
        if ((i4 & 384) == 0) {
            i6 |= composerImpl3.changed(lazyGridState) ? 256 : 128;
        }
        if ((i4 & 3072) == 0) {
            i6 |= composerImpl3.changed(paddingValues) ? 2048 : 1024;
        }
        if ((i4 & 24576) == 0) {
            i6 |= composerImpl3.changed(spacedAligned) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i4 & 196608) == 0) {
            i6 |= composerImpl3.changed(modifier) ? 131072 : 65536;
        }
        int i8 = i4 & 1572864;
        int i9 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        if (i8 == 0) {
            i6 |= composerImpl3.changed(z) ? 1048576 : 524288;
        }
        int i10 = i6 | 12582912;
        if ((i4 & 100663296) == 0) {
            i10 = i6 | 46137344;
        }
        int i11 = i10 | 805306368;
        int i12 = i5 | 6;
        if ((i5 & 48) == 0) {
            i12 |= composerImpl3.changed(i) ? 32 : 16;
        }
        if ((i5 & 384) == 0) {
            i12 |= composerImpl3.changed(i2) ? 256 : 128;
        }
        if ((i5 & 3072) == 0) {
            i12 |= composerImpl3.changed(i3) ? 2048 : 1024;
        }
        if ((i5 & 24576) == 0) {
            i12 |= composerImpl3.changedInstance(function0) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i5 & 196608) == 0) {
            i12 |= composerImpl3.changedInstance(resizeableItemFrameViewModel) ? 131072 : 65536;
        }
        if ((i5 & 1572864) == 0) {
            if (composerImpl3.changedInstance(function1)) {
                i9 = 1048576;
            }
            i12 |= i9;
        }
        if ((i5 & 12582912) == 0) {
            i12 |= composerImpl3.changedInstance(composableLambdaImpl) ? 8388608 : 4194304;
        }
        if ((i11 & 306783379) == 306783378 && (i12 & 4793491) == 4793490 && composerImpl3.getSkipping()) {
            composerImpl3.skipToGroupEnd();
            f11 = f;
            f10 = f2;
            f9 = f3;
            composerImpl2 = composerImpl3;
            j4 = j2;
        } else {
            composerImpl3.startDefaults();
            if ((i4 & 1) == 0 || composerImpl3.getDefaultsInvalid()) {
                Dp.Companion companion = Dp.Companion;
                MaterialTheme.INSTANCE.getClass();
                f4 = 3;
                i7 = i11 & (-234881025);
                f5 = 37;
                j3 = MaterialTheme.getColorScheme(composerImpl3).primary;
                f6 = 8;
            } else {
                composerImpl3.skipToGroupEnd();
                f6 = f;
                f5 = f2;
                f4 = f3;
                i7 = i11 & (-234881025);
                j3 = j2;
            }
            composerImpl3.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ResizableItemFrame (ResizeableItemFrame.kt:196)");
            }
            final SolidColor solidColor = new SolidColor(j3, null);
            MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function1, composerImpl3);
            Dp.Companion companion2 = Dp.Companion;
            long j5 = j3;
            float f12 = spacedAligned.spacing - (2 * f6);
            composerImpl3.startReplaceGroup(-2027046946);
            boolean zChanged = composerImpl3.changed(resizeableItemFrameViewModel);
            Object objRememberedValue = composerImpl3.rememberedValue();
            Composer.Companion companion3 = Composer.Companion;
            if (!zChanged) {
                companion3.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    z2 = false;
                    objDerivedStateOf = SnapshotStateKt.derivedStateOf(new ResizeableItemFrameKt$$ExternalSyntheticLambda0(resizeableItemFrameViewModel, 0));
                    composerImpl3.updateRememberedValue(objDerivedStateOf);
                } else {
                    objDerivedStateOf = objRememberedValue;
                    z2 = false;
                }
                composerImpl3.end(z2);
                Modifier modifierThen = ((Boolean) ((State) objDerivedStateOf).getValue()).booleanValue() ? modifier.then(ZIndexModifierKt.zIndex(Modifier.Companion, 1.0f)) : modifier;
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierThen);
                ComposeUiNode.Companion.getClass();
                Function0 function02 = ComposeUiNode.Companion.Constructor;
                if (composerImpl3.applier == null) {
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
                Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                composableLambdaImpl.invoke(composerImpl3, Integer.valueOf((i12 >> 21) & 14));
                composerImpl3.startReplaceGroup(-1414591315);
                if (z) {
                    DragHandle dragHandle = DragHandle.TOP;
                    AnchoredDraggableState anchoredDraggableState = resizeableItemFrameViewModel.topDragState;
                    Modifier.Companion companion4 = Modifier.Companion;
                    int i13 = ((i7 >> 12) & 7168) | 54 | ((i12 << 3) & 458752);
                    m1087DragHandleb7W0Lw(boxScopeInstance, dragHandle, anchoredDraggableState, f6, solidColor, function0, SizeKt.m131height3ABfNKs(SizeKt.fillMaxWidth(companion4, 1.0f), f12), composerImpl3, i13);
                    m1087DragHandleb7W0Lw(boxScopeInstance, DragHandle.BOTTOM, resizeableItemFrameViewModel.bottomDragState, f6, solidColor, function0, SizeKt.m131height3ABfNKs(SizeKt.fillMaxWidth(companion4, 1.0f), f12), composerImpl3, i13);
                    Modifier modifierMatchParentSize = boxScopeInstance.matchParentSize(companion4);
                    composerImpl3.startReplaceGroup(-1414568135);
                    boolean zChangedInstance = composerImpl3.changedInstance(resizeableItemFrameViewModel) | ((i7 & 29360128) == 8388608) | ((i12 & 57344) == 16384) | ((1879048192 & i7) == 536870912) | ((i12 & 14) == 4) | composerImpl3.changed(solidColor);
                    Object objRememberedValue2 = composerImpl3.rememberedValue();
                    if (!zChangedInstance) {
                        companion3.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            final float f13 = f6;
                            final float f14 = f4;
                            continuation = null;
                            r12 = 0;
                            Function1 function12 = new Function1() { // from class: com.android.systemui.communal.ui.compose.ResizeableItemFrameKt$$ExternalSyntheticLambda1
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    DrawScope drawScope = (DrawScope) obj;
                                    float fMo58toPx0680j_4 = drawScope.mo58toPx0680j_4(f13);
                                    ResizeableItemFrameViewModel resizeableItemFrameViewModel2 = resizeableItemFrameViewModel;
                                    float floatValue = ((SnapshotMutableFloatStateImpl) resizeableItemFrameViewModel2.topDragState.offset$delegate).getFloatValue();
                                    Float fValueOf = Float.valueOf(floatValue);
                                    if ((Float.floatToRawIntBits(floatValue) & Integer.MAX_VALUE) >= 2139095040) {
                                        fValueOf = null;
                                    }
                                    float fFloatValue = fValueOf != null ? fValueOf.floatValue() : 0.0f;
                                    float floatValue2 = ((SnapshotMutableFloatStateImpl) resizeableItemFrameViewModel2.bottomDragState.offset$delegate).getFloatValue();
                                    Float fValueOf2 = (Float.floatToRawIntBits(floatValue2) & Integer.MAX_VALUE) < 2139095040 ? Float.valueOf(floatValue2) : null;
                                    float fFloatValue2 = fValueOf2 != null ? fValueOf2.floatValue() : 0.0f;
                                    float fFloatValue3 = ((Number) function0.invoke()).floatValue();
                                    float f15 = -fMo58toPx0680j_4;
                                    Offset.Companion companion5 = Offset.Companion;
                                    float f16 = fMo58toPx0680j_4 * 2;
                                    long jFloatToRawIntBits = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) + (-fFloatValue) + fFloatValue2 + f16) & 4294967295L) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32)) + f16) << 32);
                                    Size.Companion companion6 = Size.Companion;
                                    float fMo58toPx0680j_42 = drawScope.mo58toPx0680j_4(f5);
                                    CornerRadius.Companion companion7 = CornerRadius.Companion;
                                    DrawScope.m542drawRoundRectZuiqVtQ$default(drawScope, solidColor, (Float.floatToRawIntBits(fFloatValue + f15) & 4294967295L) | (Float.floatToRawIntBits(f15) << 32), jFloatToRawIntBits, (Float.floatToRawIntBits(fMo58toPx0680j_42) << 32) | (Float.floatToRawIntBits(fMo58toPx0680j_42) & 4294967295L), fFloatValue3, new Stroke(drawScope.mo58toPx0680j_4(f14), 0.0f, 0, 0, null, 30, null), 192);
                                    return Unit.INSTANCE;
                                }
                            };
                            f7 = f5;
                            f8 = f14;
                            composerImpl3.updateRememberedValue(function12);
                            objRememberedValue2 = function12;
                        } else {
                            f7 = f5;
                            f8 = f4;
                            continuation = null;
                            r12 = 0;
                        }
                        composerImpl3.end(r12);
                        CanvasKt.Canvas(modifierMatchParentSize, (Function1) objRememberedValue2, composerImpl3, r12);
                        int i14 = i12 << 12;
                        Continuation continuation2 = continuation;
                        ComposerImpl composerImpl4 = composerImpl3;
                        m1089UpdateGridLayoutInfoPiiTyQc(resizeableItemFrameViewModel, str, lazyGridState, paddingValues, spacedAligned, i, i2, i3, j, composerImpl4, ((i12 >> 15) & 14) | ((i7 << 3) & 112) | (i7 & 896) | (i7 & 7168) | (i7 & 57344) | (i14 & 458752) | (3670016 & i14) | (29360128 & i14) | ((i7 << 21) & 234881024));
                        composerImpl4.startReplaceGroup(-1414526039);
                        boolean zChangedInstance2 = composerImpl4.changedInstance(resizeableItemFrameViewModel) | composerImpl4.changed(mutableStateRememberUpdatedState);
                        Object objRememberedValue3 = composerImpl4.rememberedValue();
                        if (!zChangedInstance2) {
                            companion3.getClass();
                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                objRememberedValue3 = new ResizeableItemFrameKt$ResizableItemFrame$4$2$1(resizeableItemFrameViewModel, mutableStateRememberUpdatedState, continuation2);
                                composerImpl4.updateRememberedValue(objRememberedValue3);
                            }
                            z3 = false;
                            composerImpl4.end(false);
                            EffectsKt.LaunchedEffect(composerImpl4, resizeableItemFrameViewModel, (Function2) objRememberedValue3);
                            composerImpl = composerImpl4;
                        }
                    }
                } else {
                    f7 = f5;
                    f8 = f4;
                    z3 = false;
                    composerImpl = composerImpl3;
                }
                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, z3, true)) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2 = composerImpl;
                f9 = f8;
                f10 = f7;
                f11 = f6;
                j4 = j5;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.ResizeableItemFrameKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i4 | 1);
                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i5);
                    Arrangement.SpacedAligned spacedAligned2 = spacedAligned;
                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                    ResizeableItemFrameKt.m1088ResizableItemFramegFm42b4(str, j, lazyGridState, paddingValues, spacedAligned2, modifier, z, f11, j4, f10, f9, i, i2, i3, function0, resizeableItemFrameViewModel, function1, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags, iUpdateChangedFlags2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x0181  */
    /* renamed from: UpdateGridLayoutInfo-PiiTyQc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m1089UpdateGridLayoutInfoPiiTyQc(final ResizeableItemFrameViewModel resizeableItemFrameViewModel, final String str, final LazyGridState lazyGridState, final PaddingValues paddingValues, final Arrangement.SpacedAligned spacedAligned, final int i, final int i2, final int i3, final long j, Composer composer, final int i4) {
        int i5;
        String str2;
        LazyGridState lazyGridState2;
        PaddingValues paddingValues2;
        Arrangement.SpacedAligned spacedAligned2;
        int i6;
        boolean z;
        Object[] objArr;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1899037231);
        if ((i4 & 6) == 0) {
            i5 = (composerImpl.changedInstance(resizeableItemFrameViewModel) ? 4 : 2) | i4;
        } else {
            i5 = i4;
        }
        if ((i4 & 48) == 0) {
            str2 = str;
            i5 |= composerImpl.changed(str2) ? 32 : 16;
        } else {
            str2 = str;
        }
        if ((i4 & 384) == 0) {
            lazyGridState2 = lazyGridState;
            i5 |= composerImpl.changed(lazyGridState2) ? 256 : 128;
        } else {
            lazyGridState2 = lazyGridState;
        }
        if ((i4 & 3072) == 0) {
            paddingValues2 = paddingValues;
            i5 |= composerImpl.changed(paddingValues2) ? 2048 : 1024;
        } else {
            paddingValues2 = paddingValues;
        }
        if ((i4 & 24576) == 0) {
            spacedAligned2 = spacedAligned;
            i5 |= composerImpl.changed(spacedAligned2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        } else {
            spacedAligned2 = spacedAligned;
        }
        if ((196608 & i4) == 0) {
            i5 |= composerImpl.changed(i) ? 131072 : 65536;
        }
        if ((1572864 & i4) == 0) {
            i6 = i2;
            i5 |= composerImpl.changed(i6) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        } else {
            i6 = i2;
        }
        if ((i4 & 12582912) == 0) {
            i5 |= composerImpl.changed(i3) ? 8388608 : 4194304;
        }
        if ((i4 & 100663296) == 0) {
            i5 |= composerImpl.changed(j) ? 67108864 : 33554432;
        }
        if ((i5 & 38347923) == 38347922 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.UpdateGridLayoutInfo (ResizeableItemFrame.kt:72)");
            }
            Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
            int i7 = i5;
            Object[] objArr2 = {density, resizeableItemFrameViewModel, str2, lazyGridState2, paddingValues2, spacedAligned2, Integer.valueOf(i), Integer.valueOf(i6), Integer.valueOf(i3), GridItemSpan.m158boximpl(j)};
            composerImpl.startReplaceGroup(-1140112186);
            boolean zChanged = composerImpl.changed(density) | ((57344 & i7) == 16384) | ((i7 & 7168) == 2048) | ((i7 & 896) == 256) | ((i7 & 112) == 32) | composerImpl.changedInstance(resizeableItemFrameViewModel) | ((3670016 & i7) == 1048576) | ((458752 & i7) == 131072) | ((234881024 & i7) == 67108864) | ((29360128 & i7) == 8388608);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChanged) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    z = false;
                    objArr = objArr2;
                    ResizeableItemFrameKt$UpdateGridLayoutInfo$1$1 resizeableItemFrameKt$UpdateGridLayoutInfo$1$1 = new ResizeableItemFrameKt$UpdateGridLayoutInfo$1$1(density, spacedAligned, paddingValues, lazyGridState, str, resizeableItemFrameViewModel, i2, i, j, i3, null);
                    composerImpl.updateRememberedValue(resizeableItemFrameKt$UpdateGridLayoutInfo$1$1);
                    objRememberedValue = resizeableItemFrameKt$UpdateGridLayoutInfo$1$1;
                } else {
                    z = false;
                    objArr = objArr2;
                }
                composerImpl.end(z);
                EffectsKt.LaunchedEffect(objArr, (Function2) objRememberedValue, composerImpl);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.ResizeableItemFrameKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i4 | 1);
                    Arrangement.SpacedAligned spacedAligned3 = spacedAligned;
                    int i8 = i3;
                    long j2 = j;
                    ResizeableItemFrameKt.m1089UpdateGridLayoutInfoPiiTyQc(resizeableItemFrameViewModel, str, lazyGridState, paddingValues, spacedAligned3, i, i2, i8, j2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
