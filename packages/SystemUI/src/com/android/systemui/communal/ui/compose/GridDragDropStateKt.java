package com.android.systemui.communal.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.lazy.grid.LazyGridItemScope;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class GridDragDropStateKt {
    /* JADX WARN: Removed duplicated region for block: B:85:0x0159  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void DraggableItem(final LazyGridItemScope lazyGridItemScope, final GridDragDropState gridDragDropState, final Object obj, final boolean z, final boolean z2, final Modifier modifier, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        int i2;
        final GridDragDropState gridDragDropState2;
        Modifier modifier2;
        ComposableLambdaImpl composableLambdaImpl2;
        ComposerImpl composerImpl;
        Modifier modifierAnimateItem$default;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1695700757);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(lazyGridItemScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(gridDragDropState) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changedInstance(obj) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changed(z) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(z2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl2.changedInstance(composableLambdaImpl) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        int i3 = i2;
        if ((599187 & i3) == 599186 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
            gridDragDropState2 = gridDragDropState;
            modifier2 = modifier;
            composableLambdaImpl2 = composableLambdaImpl;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.DraggableItem (GridDragDropState.kt:672)");
            }
            composerImpl2.startReplaceGroup(-921209284);
            if (!z) {
                composableLambdaImpl.invoke(Boolean.FALSE, composerImpl2, Integer.valueOf(((i3 >> 15) & 112) | 6));
                composerImpl2.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final int i4 = 0;
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.GridDragDropStateKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            switch (i4) {
                                case 0:
                                    ((Integer) obj3).getClass();
                                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                    Object obj4 = obj;
                                    ComposableLambdaImpl composableLambdaImpl3 = composableLambdaImpl;
                                    GridDragDropStateKt.DraggableItem(lazyGridItemScope, gridDragDropState, obj4, z, z2, modifier, composableLambdaImpl3, (Composer) obj2, iUpdateChangedFlags);
                                    break;
                                default:
                                    ((Integer) obj3).getClass();
                                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                    Object obj5 = obj;
                                    ComposableLambdaImpl composableLambdaImpl4 = composableLambdaImpl;
                                    GridDragDropStateKt.DraggableItem(lazyGridItemScope, gridDragDropState, obj5, z, z2, modifier, composableLambdaImpl4, (Composer) obj2, iUpdateChangedFlags2);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            gridDragDropState2 = gridDragDropState;
            modifier2 = modifier;
            composableLambdaImpl2 = composableLambdaImpl;
            composerImpl2.end(false);
            boolean zAreEqual = Intrinsics.areEqual(obj, gridDragDropState2.dragDropState.getDraggingItemKey());
            GridDragDropStateV1 gridDragDropStateV1 = gridDragDropState2.dragDropState;
            composerImpl = composerImpl2;
            final State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(gridDragDropStateV1.isDraggingToRemove() ? 0.5f : 1.0f, null, "DraggableItemAlpha", null, composerImpl, 3072, 22);
            final LayoutDirection layoutDirection = (LayoutDirection) composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection);
            composerImpl.startReplaceGroup(-921196817);
            if (zAreEqual) {
                Modifier.Companion companion = Modifier.Companion;
                composerImpl.startReplaceGroup(-921195298);
                boolean zChanged = ((i3 & 112) == 32) | composerImpl.changed(layoutDirection) | composerImpl.changed(stateAnimateFloatAsState);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChanged) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function1() { // from class: com.android.systemui.communal.ui.compose.GridDragDropStateKt$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                GraphicsLayerScope graphicsLayerScope = (GraphicsLayerScope) obj2;
                                GridDragDropState gridDragDropState3 = gridDragDropState2;
                                float fIntBitsToFloat = Float.intBitsToFloat((int) (gridDragDropState3.dragDropState.m1086getDraggingItemOffsetF1C5BW0() >> 32));
                                if (LayoutDirection.Ltr != layoutDirection) {
                                    fIntBitsToFloat = -fIntBitsToFloat;
                                }
                                ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) graphicsLayerScope;
                                reusableGraphicsLayerScope.setTranslationX(fIntBitsToFloat);
                                reusableGraphicsLayerScope.setTranslationY(Float.intBitsToFloat((int) (gridDragDropState3.dragDropState.m1086getDraggingItemOffsetF1C5BW0() & 4294967295L)));
                                reusableGraphicsLayerScope.setAlpha(((Number) stateAnimateFloatAsState.getValue()).floatValue());
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    composerImpl.end(false);
                    modifierAnimateItem$default = GraphicsLayerModifierKt.graphicsLayer(companion, (Function1) objRememberedValue);
                }
            } else {
                modifierAnimateItem$default = LazyGridItemScope.animateItem$default(lazyGridItemScope, Modifier.Companion, null, 7);
            }
            Modifier modifier3 = modifierAnimateItem$default;
            composerImpl.end(false);
            State stateAnimateFloatAsState2 = AnimateAsStateKt.animateFloatAsState((!zAreEqual || gridDragDropStateV1.isDraggingToRemove()) ? 0.0f : 1.0f, AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5), "Widget outline alpha", null, composerImpl, 3120, 20);
            int i5 = i3 >> 15;
            Alignment.Companion.getClass();
            BiasAlignment biasAlignment = Alignment.Companion.TopStart;
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier2);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
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
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
            CommunalHubKt.HighlightedItem(BoxScopeInstance.INSTANCE.matchParentSize(Modifier.Companion), ((Number) stateAnimateFloatAsState2.getValue()).floatValue(), composerImpl, 0, 0);
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifier3);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function2);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
            composableLambdaImpl2.invoke(Boolean.valueOf(zAreEqual), composerImpl, Integer.valueOf(i5 & 112));
            composerImpl.end(true);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            final int i6 = 1;
            final GridDragDropState gridDragDropState3 = gridDragDropState2;
            final Modifier modifier4 = modifier2;
            final ComposableLambdaImpl composableLambdaImpl3 = composableLambdaImpl2;
            recomposeScopeImplEndRestartGroup2.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.GridDragDropStateKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    switch (i6) {
                        case 0:
                            ((Integer) obj3).getClass();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            Object obj4 = obj;
                            ComposableLambdaImpl composableLambdaImpl32 = composableLambdaImpl3;
                            GridDragDropStateKt.DraggableItem(lazyGridItemScope, gridDragDropState3, obj4, z, z2, modifier4, composableLambdaImpl32, (Composer) obj2, iUpdateChangedFlags);
                            break;
                        default:
                            ((Integer) obj3).getClass();
                            int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            Object obj5 = obj;
                            ComposableLambdaImpl composableLambdaImpl4 = composableLambdaImpl3;
                            GridDragDropStateKt.DraggableItem(lazyGridItemScope, gridDragDropState3, obj5, z, z2, modifier4, composableLambdaImpl4, (Composer) obj2, iUpdateChangedFlags2);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
