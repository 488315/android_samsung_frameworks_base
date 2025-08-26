package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.foundation.gestures.DefaultFlingBehavior;
import androidx.compose.foundation.gestures.ScrollableDefaults;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyListItemInfo;
import androidx.compose.foundation.lazy.LazyListMeasureResult;
import androidx.compose.foundation.lazy.LazyListState;
import androidx.compose.foundation.lazy.LazyListStateKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Dp;
import com.samsung.sesl.compose.component.ScrollbarKt;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class ScrollbarLazyColumnKt {
    public static final void ScrollbarLazyColumn(final Modifier modifier, LazyListState lazyListState, final PaddingValues paddingValues, Arrangement.Vertical vertical, BiasAlignment.Horizontal horizontal, DefaultFlingBehavior defaultFlingBehavior, boolean z, final Function1 function1, Composer composer, final int i) {
        int i2;
        int i3;
        final LazyListState lazyListState2;
        Arrangement.Vertical vertical2;
        BiasAlignment.Horizontal horizontal2;
        DefaultFlingBehavior defaultFlingBehaviorFlingBehavior;
        boolean z2;
        ComposerImpl composerImpl;
        final LazyListState lazyListState3;
        final Arrangement.Vertical vertical3;
        final BiasAlignment.Horizontal horizontal3;
        final DefaultFlingBehavior defaultFlingBehavior2;
        final boolean z3;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-94050740);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(paddingValues) ? 256 : 128;
        }
        int i4 = i2 | 3072;
        if ((i & 24576) == 0) {
            i4 = i2 | 11264;
        }
        int i5 = 196608 | i4;
        if ((1572864 & i) == 0) {
            i5 = 720896 | i4;
        }
        int i6 = i5 | 113246208;
        if ((805306368 & i) == 0) {
            i6 |= composerImpl2.changedInstance(function1) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        }
        if ((306783379 & i6) == 306783378 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            lazyListState3 = lazyListState;
            vertical3 = vertical;
            horizontal3 = horizontal;
            z3 = z;
            composerImpl = composerImpl2;
            defaultFlingBehavior2 = defaultFlingBehavior;
        } else {
            composerImpl2.startDefaults();
            if ((i & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                LazyListState lazyListStateRememberLazyListState = LazyListStateKt.rememberLazyListState(composerImpl2);
                Arrangement.INSTANCE.getClass();
                Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                Alignment.Companion.getClass();
                BiasAlignment.Horizontal horizontal4 = Alignment.Companion.Start;
                ScrollableDefaults.INSTANCE.getClass();
                i3 = i6 & (-3727473);
                lazyListState2 = lazyListStateRememberLazyListState;
                vertical2 = arrangement$Top$1;
                horizontal2 = horizontal4;
                defaultFlingBehaviorFlingBehavior = ScrollableDefaults.flingBehavior(composerImpl2);
                z2 = true;
            } else {
                composerImpl2.skipToGroupEnd();
                i3 = i6 & (-3727473);
                lazyListState2 = lazyListState;
                vertical2 = vertical;
                horizontal2 = horizontal;
                defaultFlingBehaviorFlingBehavior = defaultFlingBehavior;
                z2 = z;
            }
            composerImpl2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.ScrollbarLazyColumn (ScrollbarLazyColumn.kt:44)");
            }
            composerImpl2.startReplaceGroup(-1600485195);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
                composerImpl2.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState = (MutableState) objRememberedValue;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(-1600482569);
            boolean zChanged = composerImpl2.changed(lazyListState2);
            Object objRememberedValue2 = composerImpl2.rememberedValue();
            if (zChanged || objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ScrollbarLazyColumnKt$$ExternalSyntheticLambda0
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        LazyListMeasureResult lazyListMeasureResult = (LazyListMeasureResult) lazyListState2.getLayoutInfo();
                        int i7 = lazyListMeasureResult.viewportEndOffset;
                        List list = lazyListMeasureResult.visibleItemsInfo;
                        boolean z4 = false;
                        List<LazyListItemInfo> listFilterNotNull = ArraysKt___ArraysKt.filterNotNull(new LazyListItemInfo[]{CollectionsKt___CollectionsKt.firstOrNull(list), CollectionsKt___CollectionsKt.lastOrNull(list)});
                        if (!listFilterNotNull.isEmpty()) {
                            for (LazyListItemInfo lazyListItemInfo : listFilterNotNull) {
                                if (lazyListItemInfo != null) {
                                    if (lazyListItemInfo.getOffset() < 0) {
                                        lazyListItemInfo = null;
                                    }
                                    if (lazyListItemInfo == null || lazyListItemInfo.getSize() + lazyListItemInfo.getOffset() > i7) {
                                    }
                                }
                                z4 = true;
                            }
                        }
                        return Boolean.valueOf(z4);
                    }
                });
                composerImpl2.updateRememberedValue(objRememberedValue2);
            }
            State state = (State) objRememberedValue2;
            composerImpl2.end(false);
            Boolean boolValueOf = Boolean.valueOf(lazyListState2.scrollableState.isScrollInProgress());
            composerImpl2.startReplaceGroup(-1600461963);
            boolean zChanged2 = composerImpl2.changed(lazyListState2);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            if (zChanged2 || objRememberedValue3 == composer$Companion$Empty$1) {
                objRememberedValue3 = new ScrollbarLazyColumnKt$ScrollbarLazyColumn$1$1(lazyListState2, mutableState, null);
                composerImpl2.updateRememberedValue(objRememberedValue3);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, boolValueOf, (Function2) objRememberedValue3);
            Modifier.Companion companion = Modifier.Companion;
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, companion);
            ComposeUiNode.Companion.getClass();
            int i7 = i3;
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl2.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function0);
            } else {
                composerImpl2.useNode();
            }
            Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            LazyDslKt.LazyColumn(modifier, lazyListState2, paddingValues, false, vertical2, horizontal2, defaultFlingBehaviorFlingBehavior, z2, null, function1, composerImpl2, i7 & 2147483646, 0);
            AnimatedVisibilityKt.AnimatedVisibility(((Boolean) mutableState.getValue()).booleanValue() && ((Boolean) state.getValue()).booleanValue(), boxScopeInstance.align(companion, Alignment.Companion.CenterEnd), EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), null, ComposableLambdaKt.rememberComposableLambda(2025729966, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ScrollbarLazyColumnKt$ScrollbarLazyColumn$2$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.ScrollbarLazyColumn.<anonymous>.<anonymous> (ScrollbarLazyColumn.kt:92)");
                    }
                    Dp.Companion companion2 = Dp.Companion;
                    ScrollbarKt.SeslScrollbar(lazyListState2, SizeKt.m144width3ABfNKs(PaddingKt.m129paddingqDBjuR0$default(Modifier.Companion, 0.0f, 0.0f, 0.0f, 20, 7), 8), null, composer2, 48);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2), composerImpl2, 200064, 16);
            composerImpl2.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl = composerImpl2;
            lazyListState3 = lazyListState2;
            vertical3 = vertical2;
            horizontal3 = horizontal2;
            defaultFlingBehavior2 = defaultFlingBehaviorFlingBehavior;
            z3 = z2;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ScrollbarLazyColumnKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    boolean z4 = z3;
                    Function1 function12 = function1;
                    ScrollbarLazyColumnKt.ScrollbarLazyColumn(modifier, lazyListState3, paddingValues, vertical3, horizontal3, defaultFlingBehavior2, z4, function12, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
