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
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.volume.panel.ui.layout.ComponentsLayout;
import com.android.systemui.volume.panel.ui.viewmodel.ComponentState;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class HorizontalVolumePanelContentKt {
    public static final void HorizontalVolumePanelContent(final VolumePanelComposeScope volumePanelComposeScope, final ComponentsLayout componentsLayout, final Modifier modifier, Composer composer, final int i) {
        Modifier then;
        Modifier then2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1334321093);
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
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContent (HorizontalVolumePanelContent.kt:36)");
            }
            final float f = 20;
            Dp.Companion companion = Dp.Companion;
            Arrangement.INSTANCE.getClass();
            Arrangement.SpacedAligned m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(f);
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(m91spacedBy0680j_4, Alignment.Companion.Top, composerImpl, 6);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            Updater.m336setimpl(composerImpl, rowMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl, materializeModifier, function24);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            then = ScrollingContainerKt.scrollingContainer(rowScopeInstance.weight(Modifier.Companion, 1.0f, true), r13, Orientation.Vertical, (r14 & 2) != 0, false, null, r13.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(ScrollKt.rememberScrollState(composerImpl), false, true));
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.m91spacedBy0680j_4(f), Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, then);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, function2);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m336setimpl(composerImpl, materializeModifier2, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(1575846307);
            for (Iterator it = componentsLayout.contentComponents.iterator(); it.hasNext(); it = it) {
                final ComponentState componentState = (ComponentState) it.next();
                AnimatedVisibilityKt.AnimatedVisibility(columnScopeInstance, componentState.isVisible, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-872588377, new Function3() { // from class: com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContentKt$HorizontalVolumePanelContent$1$1$1
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        Composer composer2 = (Composer) obj2;
                        ((Number) obj3).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContent.<anonymous>.<anonymous>.<anonymous> (HorizontalVolumePanelContent.kt:45)");
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
            composerImpl.end(true);
            then2 = ScrollingContainerKt.scrollingContainer(rowScopeInstance.weight(Modifier.Companion, 1.0f, true), r5, Orientation.Vertical, (r14 & 2) != 0, false, null, r5.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(ScrollKt.rememberScrollState(composerImpl), false, true));
            Arrangement arrangement = Arrangement.INSTANCE;
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.Top;
            arrangement.getClass();
            ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(Arrangement.m93spacedByD5KLDUw(f, vertical), Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, then2);
            ComposeUiNode.Companion.getClass();
            Function0 function02 = ComposeUiNode.Companion.Constructor;
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function02);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, columnMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope3, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function25 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function25);
            }
            Updater.m336setimpl(composerImpl, materializeModifier3, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(1575861324);
            for (final ComponentState componentState2 : componentsLayout.headerComponents) {
                AnimatedVisibilityKt.AnimatedVisibility(columnScopeInstance2, componentState2.isVisible, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-1685962416, new Function3() { // from class: com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContentKt$HorizontalVolumePanelContent$1$2$1
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        Composer composer2 = (Composer) obj2;
                        ((Number) obj3).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContent.<anonymous>.<anonymous>.<anonymous> (HorizontalVolumePanelContent.kt:56)");
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
            AnimatedContentKt.AnimatedContent(componentsLayout.footerComponents, null, null, null, "FooterComponentAnimation", null, ComposableLambdaKt.rememberComposableLambda(-788015821, new Function4() { // from class: com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContentKt$HorizontalVolumePanelContent$1$2$2
                @Override // kotlin.jvm.functions.Function4
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                    List<ComponentState> list = (List) obj2;
                    Composer composer2 = (Composer) obj3;
                    ((Number) obj4).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContent.<anonymous>.<anonymous>.<anonymous> (HorizontalVolumePanelContent.kt:63)");
                    }
                    Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
                    Arrangement.INSTANCE.getClass();
                    Arrangement.SpacedAligned m91spacedBy0680j_42 = Arrangement.m91spacedBy0680j_4(f);
                    Alignment.Companion.getClass();
                    RowMeasurePolicy rowMeasurePolicy2 = RowKt.rowMeasurePolicy(m91spacedBy0680j_42, Alignment.Companion.Top, composer2, 6);
                    int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope4 = composerImpl2.currentCompositionLocalScope();
                    Modifier materializeModifier4 = ComposedModifierKt.materializeModifier(composer2, fillMaxWidth);
                    ComposeUiNode.Companion.getClass();
                    Function0 function03 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl2.applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl2.startReusableNode();
                    if (composerImpl2.inserting) {
                        composerImpl2.createNode(function03);
                    } else {
                        composerImpl2.useNode();
                    }
                    Updater.m336setimpl(composer2, rowMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m336setimpl(composer2, currentCompositionLocalScope4, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function26 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl2, currentCompositeKeyHash4, function26);
                    }
                    Updater.m336setimpl(composer2, materializeModifier4, ComposeUiNode.Companion.SetModifier);
                    RowScopeInstance rowScopeInstance2 = RowScopeInstance.INSTANCE;
                    composerImpl2.startReplaceGroup(-2088177327);
                    for (ComponentState componentState3 : list) {
                        composerImpl2.startReplaceGroup(-2088175427);
                        if (componentState3.isVisible) {
                            ((ComposeVolumePanelUiComponent) componentState3.component).Content(volumePanelComposeScope, rowScopeInstance2.weight(Modifier.Companion, 1.0f, true), composer2, 0);
                        }
                        composerImpl2.end(false);
                    }
                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, false, true)) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 1597440, 46);
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, true, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContentKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    HorizontalVolumePanelContentKt.HorizontalVolumePanelContent(VolumePanelComposeScope.this, componentsLayout, modifier, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
