package com.samsung.sesl.compose.component;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.gestures.DraggableKt;
import androidx.compose.foundation.gestures.DraggableState;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.interaction.DragInteractionKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.interaction.PressInteractionKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.LazyListState;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.component.SeslDragAxis;
import com.samsung.sesl.compose.component.tokens.SeslListColorSchemeKeyTokens;
import com.samsung.sesl.compose.foundation.scroll.SeslLazyListState;
import com.samsung.sesl.compose.foundation.scroll.SeslScrollableState;
import com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Default;
import com.samsung.sesl.compose.foundation.theme.BasicColorSchemeKt;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes4.dex */
public abstract class ScrollbarKt {
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SeslScrollbar(final LazyListState lazyListState, Modifier modifier, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-131542391);
        int i2 = (composerImpl.changed(lazyListState) ? 4 : 2) | i | 384;
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            composerImpl.startReplaceGroup(-587995213);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MutableInteractionSource mutableInteractionSource2 = (MutableInteractionSource) objRememberedValue;
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScrollbar (Scrollbar.kt:127)");
            }
            int i3 = i2 & 14;
            composerImpl.startReplaceGroup(-879979364);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.scroll.rememberSeslScrollbarState (SeslScrollState.kt:66)");
            }
            SeslScrollingStrategy$Default seslScrollingStrategy$Default = new Object() { // from class: com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Default
                public final boolean equals(Object obj) {
                    return this == obj || (obj instanceof SeslScrollingStrategy$Default);
                }

                public final int hashCode() {
                    return -908521908;
                }

                public final String toString() {
                    return SystemUIAnalytics.DID_NOTI_SELECT_DEFAULT;
                }
            };
            int i4 = i3 | 48;
            composerImpl.startReplaceGroup(-342579925);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.scroll.rememberSeslScrollbarState (SeslScrollState.kt:82)");
            }
            composerImpl.startReplaceGroup(1443903201);
            boolean z = (((i4 & 14) ^ 6) > 4 && composerImpl.changed(lazyListState)) || (i4 & 6) == 4;
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (!z) {
                companion.getClass();
                if (objRememberedValue2 == composer$Companion$Empty$1) {
                    if (!Intrinsics.areEqual(seslScrollingStrategy$Default, seslScrollingStrategy$Default) && !Intrinsics.areEqual(seslScrollingStrategy$Default, new Object() { // from class: com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index
                        public final boolean equals(Object obj) {
                            return this == obj || (obj instanceof SeslScrollingStrategy$Index);
                        }

                        public final int hashCode() {
                            return -617289827;
                        }

                        public final String toString() {
                            return "Index";
                        }
                    })) {
                        throw new NoWhenBranchMatchedException();
                    }
                    objRememberedValue2 = new SeslLazyListState(lazyListState);
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                SeslScrollableState seslScrollableState = (SeslScrollableState) objRememberedValue2;
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                modifier2 = modifier;
                SeslScrollbar(seslScrollableState, modifier2, mutableInteractionSource2, false, (Composer) composerImpl, 440);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                mutableInteractionSource = mutableInteractionSource2;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(modifier2, mutableInteractionSource, i) { // from class: com.samsung.sesl.compose.component.ScrollbarKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Modifier f$1;
                public final /* synthetic */ MutableInteractionSource f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                    Modifier modifier3 = this.f$1;
                    MutableInteractionSource mutableInteractionSource3 = this.f$2;
                    ScrollbarKt.SeslScrollbar(this.f$0, modifier3, mutableInteractionSource3, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SeslScrollbar(SeslScrollableState seslScrollableState, Modifier modifier, final MutableInteractionSource mutableInteractionSource, boolean z, Composer composer, final int i) {
        int i2;
        final SeslScrollableState seslScrollableState2;
        final Modifier modifier2;
        final MutableInteractionSource mutableInteractionSource2;
        final boolean z2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1594911195);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl.changed(seslScrollableState) : composerImpl.changedInstance(seslScrollableState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(mutableInteractionSource) ? 256 : 128;
        }
        int i3 = i2 | 3072;
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            z2 = z;
            mutableInteractionSource2 = mutableInteractionSource;
            modifier2 = modifier;
            seslScrollableState2 = seslScrollableState;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScrollbar (Scrollbar.kt:222)");
            }
            int i4 = (i3 & 14) | 56;
            int i5 = i3 << 3;
            SeslScrollbar(seslScrollableState, ComposableLambdaKt.rememberComposableLambda(1433112073, new Function3() { // from class: com.samsung.sesl.compose.component.ScrollbarKt.SeslScrollbar.17
                /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    SeslScrollbarThumbScope seslScrollbarThumbScope = (SeslScrollbarThumbScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= (iIntValue & 8) == 0 ? ((ComposerImpl) composer2).changed(seslScrollbarThumbScope) : ((ComposerImpl) composer2).changedInstance(seslScrollbarThumbScope) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScrollbar.<anonymous> (Scrollbar.kt:229)");
                            }
                            MutableInteractionSource mutableInteractionSource3 = mutableInteractionSource;
                            boolean z3 = ((Boolean) DragInteractionKt.collectIsDraggedAsState(mutableInteractionSource3, composer2).getValue()).booleanValue() || ((Boolean) PressInteractionKt.collectIsPressedAsState(mutableInteractionSource3, composer2, 0).getValue()).booleanValue();
                            Modifier.Companion companion = Modifier.Companion;
                            ((SeslScrollbarThumbScopeImpl) seslScrollbarThumbScope).getClass();
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(-1453257121);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScrollbarThumbScopeImpl.FastScrollbar (Scrollbar.kt:461)");
                            }
                            float f = z3 ? 5 : 3;
                            Dp.Companion companion2 = Dp.Companion;
                            State stateM8animateDpAsStateAjpBEmI = AnimateAsStateKt.m8animateDpAsStateAjpBEmI(f, null, "", composerImpl3, 384, 10);
                            SeslScrollbarDefaults.INSTANCE.getClass();
                            composerImpl3.startReplaceGroup(-1294381766);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScrollbarDefaults.scrollbarThumbColor (Scrollbar.kt:494)");
                            }
                            long color = BasicColorSchemeKt.toColor(SeslListColorSchemeKeyTokens.ScrollbarThumbActivate, composerImpl3);
                            long color2 = BasicColorSchemeKt.toColor(SeslListColorSchemeKeyTokens.ScrollbarThumbInActivate, composerImpl3);
                            if (!z3) {
                                color = color2;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl3.end(false);
                            BoxKt.Box(BackgroundKt.m26backgroundbw27NRU(SizeKt.m139requiredWidth3ABfNKs(companion, ((Dp) stateM8animateDpAsStateAjpBEmI.getValue()).value).then(SizeKt.FillWholeMaxHeight), ((Color) SingleValueAnimationKt.m7animateColorAsStateeuL9pac(color, null, "", composerImpl3, 384, 10).getValue()).value, RoundedCornerShapeKt.CircleShape), composerImpl3, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl3.end(false);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), modifier, mutableInteractionSource, composerImpl, i4 | (i5 & 896) | (i5 & 7168) | (i5 & 57344));
            seslScrollableState2 = seslScrollableState;
            modifier2 = modifier;
            mutableInteractionSource2 = mutableInteractionSource;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            z2 = true;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.ScrollbarKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    MutableInteractionSource mutableInteractionSource3 = mutableInteractionSource2;
                    boolean z3 = z2;
                    ScrollbarKt.SeslScrollbar(seslScrollableState2, modifier2, mutableInteractionSource3, z3, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x02b0  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02eb  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0303  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01fe  */
    /* JADX WARN: Type inference failed for: r2v2, types: [androidx.compose.runtime.internal.ComposableLambdaImpl] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SeslScrollbar(final SeslScrollableState seslScrollableState, ComposableLambdaImpl composableLambdaImpl, final Modifier modifier, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        int i2;
        int i3;
        int handleSizeFraction;
        int i4;
        boolean zChangedInstance;
        Object objRememberedValue;
        boolean zChangedInstance2;
        Object objRememberedValue2;
        boolean zChangedInstance3;
        Object objRememberedValue3;
        int currentCompositeKeyHash;
        boolean zChangedInstance4;
        Object objRememberedValue4;
        final ComposableLambdaImpl composableLambdaImpl2;
        ComposableLambdaImpl composableLambdaImpl3 = composableLambdaImpl;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1262085851);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl.changed(seslScrollableState) : composerImpl.changedInstance(seslScrollableState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(composableLambdaImpl3) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(mutableInteractionSource) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(true) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i2 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            composableLambdaImpl2 = composableLambdaImpl3;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScrollbar (Scrollbar.kt:287)");
            }
            final SeslDragAxis.Vertical vertical = SeslDragAxis.Vertical.INSTANCE;
            Object objRememberedValue5 = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue5 == composer$Companion$Empty$1) {
                CompositionScopedCoroutineScopeCanceller compositionScopedCoroutineScopeCanceller = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl));
                composerImpl.updateRememberedValue(compositionScopedCoroutineScopeCanceller);
                objRememberedValue5 = compositionScopedCoroutineScopeCanceller;
            }
            CoroutineScope coroutineScope = ((CompositionScopedCoroutineScopeCanceller) objRememberedValue5).coroutineScope;
            composerImpl.startReplaceGroup(-587792051);
            Object objRememberedValue6 = composerImpl.rememberedValue();
            if (objRememberedValue6 == composer$Companion$Empty$1) {
                objRememberedValue6 = SnapshotIntStateKt.mutableIntStateOf(0);
                composerImpl.updateRememberedValue(objRememberedValue6);
            }
            final MutableIntState mutableIntState = (MutableIntState) objRememberedValue6;
            composerImpl.end(false);
            Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
            SnapshotMutableIntStateImpl snapshotMutableIntStateImpl = (SnapshotMutableIntStateImpl) mutableIntState;
            int intValue = snapshotMutableIntStateImpl.getIntValue();
            composerImpl.startReplaceGroup(-587788327);
            boolean zChanged = ((i2 & 14) == 4 || ((i2 & 8) != 0 && composerImpl.changed(seslScrollableState))) | composerImpl.changed(intValue) | composerImpl.changed(coroutineScope) | composerImpl.changed(density);
            Object objRememberedValue7 = composerImpl.rememberedValue();
            if (zChanged || objRememberedValue7 == composer$Companion$Empty$1) {
                int intValue2 = snapshotMutableIntStateImpl.getIntValue();
                Dp.Companion companion = Dp.Companion;
                ScrollAdapter scrollAdapter = new ScrollAdapter(seslScrollableState, intValue2, density.mo52roundToPx0680j_4(36), coroutineScope);
                composerImpl.updateRememberedValue(scrollAdapter);
                objRememberedValue7 = scrollAdapter;
            }
            final ScrollAdapter scrollAdapter2 = (ScrollAdapter) objRememberedValue7;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, -587777113);
            if (objM == composer$Companion$Empty$1) {
                objM = AnimatableKt.Animatable(0.0f, 0.01f);
                composerImpl.updateRememberedValue(objM);
            }
            Animatable animatable = (Animatable) objM;
            Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, -587775566);
            if (objM2 == composer$Companion$Empty$1) {
                objM2 = SnapshotStateKt.derivedStateOf(new ScrollbarKt$$ExternalSyntheticLambda2(animatable, 0));
                composerImpl.updateRememberedValue(objM2);
            }
            final State state = (State) objM2;
            composerImpl.end(false);
            float f = 24;
            Dp.Companion companion2 = Dp.Companion;
            Modifier modifierM139requiredWidth3ABfNKs = SizeKt.m139requiredWidth3ABfNKs(modifier, f);
            composerImpl.startReplaceGroup(-587733361);
            boolean zChangedInstance5 = composerImpl.changedInstance(vertical);
            Object objRememberedValue8 = composerImpl.rememberedValue();
            if (zChangedInstance5 || objRememberedValue8 == composer$Companion$Empty$1) {
                objRememberedValue8 = new MeasurePolicy() { // from class: com.samsung.sesl.compose.component.ScrollbarKt$SeslScrollbar$22$1
                    @Override // androidx.compose.ui.layout.MeasurePolicy
                    /* renamed from: measure-3p2s80s */
                    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                        final Placeable placeableMo610measureBRTryo0 = ((Measurable) CollectionsKt___CollectionsKt.first(list)).mo610measureBRTryo0(j);
                        long jIntSize = IntSizeKt.IntSize(Constraints.m823getMaxWidthimpl(j), Constraints.m822getMaxHeightimpl(j));
                        IntSize.Companion companion3 = IntSize.Companion;
                        long jIntSize2 = IntSizeKt.IntSize(Math.max((int) (jIntSize >> 32), placeableMo610measureBRTryo0.width), Math.max((int) (jIntSize & 4294967295L), placeableMo610measureBRTryo0.width));
                        int i5 = (int) (jIntSize2 & 4294967295L);
                        ((SnapshotMutableIntStateImpl) mutableIntState).setIntValue(i5);
                        ((SeslDragAxis.Vertical) vertical).getClass();
                        int i6 = (int) (jIntSize2 >> 32);
                        final int i7 = (i6 - placeableMo610measureBRTryo0.width) / 2;
                        final State state2 = state;
                        return measureScope.layout$1(i6, i5, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.samsung.sesl.compose.component.ScrollbarKt$SeslScrollbar$22$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                ((Placeable.PlacementScope) obj).place(placeableMo610measureBRTryo0, i7, ((Number) state2.getValue()).intValue(), 0.0f);
                                return Unit.INSTANCE;
                            }
                        });
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue8);
            }
            MeasurePolicy measurePolicy = (MeasurePolicy) objRememberedValue8;
            composerImpl.end(false);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM139requiredWidth3ABfNKs);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier != null) {
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                Updater.m337setimpl(composerImpl, measurePolicy, function2);
                Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting) {
                    i3 = i2;
                } else {
                    i3 = i2;
                    if (!Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    }
                    Function2 function24 = ComposeUiNode.Companion.SetModifier;
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
                    Modifier modifierFocusable = FocusableKt.focusable(mutableInteractionSource, Modifier.Companion, true);
                    handleSizeFraction = (int) (scrollAdapter2.scrollableState.getHandleSizeFraction() * scrollAdapter2.scrollBarSize);
                    i4 = scrollAdapter2.handleMinSize;
                    if (handleSizeFraction < i4) {
                        handleSizeFraction = i4;
                    }
                    Modifier modifierM137requiredSizeVpY3zN4 = SizeKt.m137requiredSizeVpY3zN4(modifierFocusable, f, density.mo55toDpu2uoSUM(handleSizeFraction));
                    composerImpl.startReplaceGroup(-1764268690);
                    zChangedInstance = composerImpl.changedInstance(scrollAdapter2);
                    objRememberedValue = composerImpl.rememberedValue();
                    if (!zChangedInstance || objRememberedValue == composer$Companion$Empty$1) {
                        objRememberedValue = new Function1() { // from class: com.samsung.sesl.compose.component.ScrollbarKt$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                float fFloatValue = ((Float) obj).floatValue();
                                ScrollAdapter scrollAdapter3 = scrollAdapter2;
                                scrollAdapter3.setRawPosition(scrollAdapter3.rawPosition + fFloatValue);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    composerImpl.end(false);
                    DraggableState draggableStateRememberDraggableState = DraggableKt.rememberDraggableState(composerImpl, (Function1) objRememberedValue);
                    Orientation orientation = Orientation.Vertical;
                    composerImpl.startReplaceGroup(-1764264171);
                    zChangedInstance2 = composerImpl.changedInstance(scrollAdapter2);
                    objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChangedInstance2 || objRememberedValue2 == composer$Companion$Empty$1) {
                        objRememberedValue2 = new ScrollbarKt$SeslScrollbar$23$3$1(scrollAdapter2, null);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    Function3 function3 = (Function3) objRememberedValue2;
                    composerImpl.end(false);
                    composerImpl.startReplaceGroup(-1764259435);
                    zChangedInstance3 = composerImpl.changedInstance(scrollAdapter2);
                    objRememberedValue3 = composerImpl.rememberedValue();
                    if (!zChangedInstance3 || objRememberedValue3 == composer$Companion$Empty$1) {
                        objRememberedValue3 = new ScrollbarKt$SeslScrollbar$23$4$1(scrollAdapter2, null);
                        composerImpl.updateRememberedValue(objRememberedValue3);
                    }
                    composerImpl.end(false);
                    Modifier modifierDraggable$default = DraggableKt.draggable$default(modifierM137requiredSizeVpY3zN4, draggableStateRememberDraggableState, orientation, true, mutableInteractionSource, false, function3, (Function3) objRememberedValue3, false, 144);
                    Alignment.Companion.getClass();
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                    currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierDraggable$default);
                    composerImpl.startReusableNode();
                    if (!composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                    if (!composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                    ?? r2 = composableLambdaImpl;
                    r2.invoke(SeslScrollbarThumbScopeImpl.INSTANCE, composerImpl, Integer.valueOf((i3 & 112) | 6));
                    composerImpl.end(true);
                    composerImpl.end(true);
                    composerImpl.startReplaceGroup(-587711150);
                    zChangedInstance4 = composerImpl.changedInstance(scrollAdapter2) | composerImpl.changedInstance(animatable);
                    objRememberedValue4 = composerImpl.rememberedValue();
                    if (!zChangedInstance4 || objRememberedValue4 == composer$Companion$Empty$1) {
                        objRememberedValue4 = new ScrollbarKt$SeslScrollbar$24$1(scrollAdapter2, animatable, null);
                        composerImpl.updateRememberedValue(objRememberedValue4);
                    }
                    composerImpl.end(false);
                    EffectsKt.LaunchedEffect(composerImpl, scrollAdapter2, (Function2) objRememberedValue4);
                    composableLambdaImpl2 = r2;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        composableLambdaImpl2 = r2;
                    }
                }
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
                Function2 function242 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function242);
                Modifier modifierFocusable2 = FocusableKt.focusable(mutableInteractionSource, Modifier.Companion, true);
                handleSizeFraction = (int) (scrollAdapter2.scrollableState.getHandleSizeFraction() * scrollAdapter2.scrollBarSize);
                i4 = scrollAdapter2.handleMinSize;
                if (handleSizeFraction < i4) {
                }
                Modifier modifierM137requiredSizeVpY3zN42 = SizeKt.m137requiredSizeVpY3zN4(modifierFocusable2, f, density.mo55toDpu2uoSUM(handleSizeFraction));
                composerImpl.startReplaceGroup(-1764268690);
                zChangedInstance = composerImpl.changedInstance(scrollAdapter2);
                objRememberedValue = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    objRememberedValue = new Function1() { // from class: com.samsung.sesl.compose.component.ScrollbarKt$$ExternalSyntheticLambda3
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            float fFloatValue = ((Float) obj).floatValue();
                            ScrollAdapter scrollAdapter3 = scrollAdapter2;
                            scrollAdapter3.setRawPosition(scrollAdapter3.rawPosition + fFloatValue);
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                    composerImpl.end(false);
                    DraggableState draggableStateRememberDraggableState2 = DraggableKt.rememberDraggableState(composerImpl, (Function1) objRememberedValue);
                    Orientation orientation2 = Orientation.Vertical;
                    composerImpl.startReplaceGroup(-1764264171);
                    zChangedInstance2 = composerImpl.changedInstance(scrollAdapter2);
                    objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChangedInstance2) {
                        objRememberedValue2 = new ScrollbarKt$SeslScrollbar$23$3$1(scrollAdapter2, null);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                        Function3 function32 = (Function3) objRememberedValue2;
                        composerImpl.end(false);
                        composerImpl.startReplaceGroup(-1764259435);
                        zChangedInstance3 = composerImpl.changedInstance(scrollAdapter2);
                        objRememberedValue3 = composerImpl.rememberedValue();
                        if (!zChangedInstance3) {
                            objRememberedValue3 = new ScrollbarKt$SeslScrollbar$23$4$1(scrollAdapter2, null);
                            composerImpl.updateRememberedValue(objRememberedValue3);
                            composerImpl.end(false);
                            Modifier modifierDraggable$default2 = DraggableKt.draggable$default(modifierM137requiredSizeVpY3zN42, draggableStateRememberDraggableState2, orientation2, true, mutableInteractionSource, false, function32, (Function3) objRememberedValue3, false, 144);
                            Alignment.Companion.getClass();
                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                            currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope22 = composerImpl.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier22 = ComposedModifierKt.materializeModifier(composerImpl, modifierDraggable$default2);
                            composerImpl.startReusableNode();
                            if (!composerImpl.inserting) {
                            }
                            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function2);
                            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope22, function22);
                            if (!composerImpl.inserting) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
                                Updater.m337setimpl(composerImpl, modifierMaterializeModifier22, function242);
                                BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                                ?? r22 = composableLambdaImpl;
                                r22.invoke(SeslScrollbarThumbScopeImpl.INSTANCE, composerImpl, Integer.valueOf((i3 & 112) | 6));
                                composerImpl.end(true);
                                composerImpl.end(true);
                                composerImpl.startReplaceGroup(-587711150);
                                zChangedInstance4 = composerImpl.changedInstance(scrollAdapter2) | composerImpl.changedInstance(animatable);
                                objRememberedValue4 = composerImpl.rememberedValue();
                                if (!zChangedInstance4) {
                                    objRememberedValue4 = new ScrollbarKt$SeslScrollbar$24$1(scrollAdapter2, animatable, null);
                                    composerImpl.updateRememberedValue(objRememberedValue4);
                                    composerImpl.end(false);
                                    EffectsKt.LaunchedEffect(composerImpl, scrollAdapter2, (Function2) objRememberedValue4);
                                    composableLambdaImpl2 = r22;
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                ComposablesKt.invalidApplier();
                throw null;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.ScrollbarKt$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ComposableLambdaImpl composableLambdaImpl4 = composableLambdaImpl2;
                    Modifier modifier2 = modifier;
                    MutableInteractionSource mutableInteractionSource2 = mutableInteractionSource;
                    ScrollbarKt.SeslScrollbar(seslScrollableState, composableLambdaImpl4, modifier2, mutableInteractionSource2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
