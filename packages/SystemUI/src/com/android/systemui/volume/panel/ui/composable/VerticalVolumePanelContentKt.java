package com.android.systemui.volume.panel.ui.composable;

import androidx.compose.animation.AnimatedContentKt;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.ScrollingContainerKt;
import androidx.compose.foundation.ScrollingLayoutElement;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.volume.panel.ui.layout.ComponentsLayout;
import com.android.systemui.volume.panel.ui.viewmodel.ComponentState;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class VerticalVolumePanelContentKt {
    public static final void VerticalVolumePanelContent(final VolumePanelComposeScope volumePanelComposeScope, final ComponentsLayout componentsLayout, final Modifier modifier, Composer composer, final int i) {
        Modifier then;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2034105147);
        int i2 = (i & 6) == 0 ? (composerImpl.changed(volumePanelComposeScope) ? 4 : 2) | i : i;
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(componentsLayout) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.ui.composable.VerticalVolumePanelContent (VerticalVolumePanelContent.kt:38)");
            }
            then = ScrollingContainerKt.scrollingContainer(modifier, r5, Orientation.Vertical, (r14 & 2) != 0, false, null, r5.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(ScrollKt.rememberScrollState(composerImpl), false, true));
            Arrangement arrangement = Arrangement.INSTANCE;
            Dp.Companion companion = Dp.Companion;
            arrangement.getClass();
            Arrangement.SpacedAligned m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(20);
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m91spacedBy0680j_4, Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, then);
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
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(-1877304411);
            for (final ComponentState componentState : componentsLayout.headerComponents) {
                AnimatedVisibilityKt.AnimatedVisibility(columnScopeInstance, componentState.isVisible, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-1606794357, new Function3() { // from class: com.android.systemui.volume.panel.ui.composable.VerticalVolumePanelContentKt$VerticalVolumePanelContent$1$1
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        Composer composer2 = (Composer) obj2;
                        ((Number) obj3).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.volume.panel.ui.composable.VerticalVolumePanelContent.<anonymous>.<anonymous> (VerticalVolumePanelContent.kt:45)");
                        }
                        ((ComposeVolumePanelUiComponent) ComponentState.this.component).Content(volumePanelComposeScope, Modifier.Companion, composer2, 48);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 1572870, 30);
            }
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-1877297114);
            for (final ComponentState componentState2 : componentsLayout.contentComponents) {
                AnimatedVisibilityKt.AnimatedVisibility(columnScopeInstance, componentState2.isVisible, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-904598156, new Function3() { // from class: com.android.systemui.volume.panel.ui.composable.VerticalVolumePanelContentKt$VerticalVolumePanelContent$1$2
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        Composer composer2 = (Composer) obj2;
                        ((Number) obj3).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.volume.panel.ui.composable.VerticalVolumePanelContent.<anonymous>.<anonymous> (VerticalVolumePanelContent.kt:50)");
                        }
                        ((ComposeVolumePanelUiComponent) ComponentState.this.component).Content(volumePanelComposeScope, Modifier.Companion, composer2, 48);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 1572870, 30);
            }
            composerImpl.end(false);
            AnimatedContentKt.AnimatedContent(componentsLayout.footerComponents, null, null, null, "FooterComponentAnimation", null, ComposableLambdaKt.rememberComposableLambda(991343342, new Function4() { // from class: com.android.systemui.volume.panel.ui.composable.VerticalVolumePanelContentKt$VerticalVolumePanelContent$1$3
                @Override // kotlin.jvm.functions.Function4
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                    List<ComponentState> list = (List) obj2;
                    Composer composer2 = (Composer) obj3;
                    ((Number) obj4).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.volume.panel.ui.composable.VerticalVolumePanelContent.<anonymous>.<anonymous> (VerticalVolumePanelContent.kt:58)");
                    }
                    Modifier wrapContentHeight$default = SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), 3);
                    Arrangement arrangement2 = Arrangement.INSTANCE;
                    VolumePanelComposeScope volumePanelComposeScope2 = VolumePanelComposeScope.this;
                    float f = volumePanelComposeScope2.state.isLargeScreen ? 28 : 20;
                    Dp.Companion companion2 = Dp.Companion;
                    arrangement2.getClass();
                    Arrangement.SpacedAligned m91spacedBy0680j_42 = Arrangement.m91spacedBy0680j_4(f);
                    Alignment.Companion.getClass();
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(m91spacedBy0680j_42, Alignment.Companion.Top, composer2, 0);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
                    Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composer2, wrapContentHeight$default);
                    ComposeUiNode.Companion.getClass();
                    Function0 function02 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl2.applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl2.startReusableNode();
                    if (composerImpl2.inserting) {
                        composerImpl2.createNode(function02);
                    } else {
                        composerImpl2.useNode();
                    }
                    Updater.m336setimpl(composer2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m336setimpl(composer2, currentCompositionLocalScope2, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function22);
                    }
                    Updater.m336setimpl(composer2, materializeModifier2, ComposeUiNode.Companion.SetModifier);
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    int size = list.size();
                    int i3 = 0;
                    for (int i4 = 0; i4 < size; i4++) {
                        i3 += ((ComponentState) list.get(i4)).isVisible ? 1 : 0;
                    }
                    composerImpl2.startReplaceGroup(-611616135);
                    if (i3 == 1) {
                        SpacerKt.Spacer(composer2, rowScopeInstance.weight(Modifier.Companion, 0.5f, true));
                    }
                    composerImpl2.end(false);
                    composerImpl2.startReplaceGroup(-611611764);
                    for (ComponentState componentState3 : list) {
                        composerImpl2.startReplaceGroup(-611609984);
                        if (componentState3.isVisible) {
                            ((ComposeVolumePanelUiComponent) componentState3.component).Content(volumePanelComposeScope2, rowScopeInstance.weight(Modifier.Companion, 1.0f, true), composer2, 0);
                        }
                        composerImpl2.end(false);
                    }
                    composerImpl2.end(false);
                    composerImpl2.startReplaceGroup(-611602023);
                    if (i3 == 1) {
                        SpacerKt.Spacer(composer2, rowScopeInstance.weight(Modifier.Companion, 0.5f, true));
                    }
                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, false, true)) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 1597440, 46);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.ui.composable.VerticalVolumePanelContentKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    VerticalVolumePanelContentKt.VerticalVolumePanelContent(VolumePanelComposeScope.this, componentsLayout, modifier, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
