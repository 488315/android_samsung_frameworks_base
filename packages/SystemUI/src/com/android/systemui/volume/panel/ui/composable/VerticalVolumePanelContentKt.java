package com.android.systemui.volume.panel.ui.composable;

import androidx.compose.animation.AnimatedContentKt;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
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

public abstract class VerticalVolumePanelContentKt {
    public static final void VerticalVolumePanelContent(final VolumePanelComposeScope volumePanelComposeScope, final ComponentsLayout componentsLayout, Modifier modifier, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2034105147);
        Modifier modifier2 = (i2 & 2) != 0 ? Modifier.Companion : modifier;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier verticalScroll$default = ScrollKt.verticalScroll$default(modifier2, ScrollKt.rememberScrollState(composerImpl), false, 14);
        Arrangement arrangement = Arrangement.INSTANCE;
        Dp.Companion companion = Dp.Companion;
        arrangement.getClass();
        Arrangement.SpacedAligned m75spacedBy0680j_4 = Arrangement.m75spacedBy0680j_4(20);
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m75spacedBy0680j_4, Alignment.Companion.Start, composerImpl, 6);
        int i3 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, verticalScroll$default);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        composerImpl.startReplaceGroup(-2000220919);
        for (final ComponentState componentState : componentsLayout.headerComponents) {
            AnimatedVisibilityKt.AnimatedVisibility(columnScopeInstance, componentState.isVisible, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-1606794357, composerImpl, new Function3() { // from class: com.android.systemui.volume.panel.ui.composable.VerticalVolumePanelContentKt$VerticalVolumePanelContent$1$1
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    ((ComposeVolumePanelUiComponent) ComponentState.this.component).Content(volumePanelComposeScope, Modifier.Companion, (Composer) obj2, 48);
                    return Unit.INSTANCE;
                }
            }), composerImpl, 1572870, 30);
        }
        composerImpl.end(false);
        composerImpl.startReplaceGroup(-2000220691);
        for (final ComponentState componentState2 : componentsLayout.contentComponents) {
            AnimatedVisibilityKt.AnimatedVisibility(columnScopeInstance, componentState2.isVisible, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-904598156, composerImpl, new Function3() { // from class: com.android.systemui.volume.panel.ui.composable.VerticalVolumePanelContentKt$VerticalVolumePanelContent$1$2
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    ((ComposeVolumePanelUiComponent) ComponentState.this.component).Content(volumePanelComposeScope, Modifier.Companion, (Composer) obj2, 48);
                    return Unit.INSTANCE;
                }
            }), composerImpl, 1572870, 30);
        }
        composerImpl.end(false);
        AnimatedContentKt.AnimatedContent(componentsLayout.footerComponents, null, null, null, "FooterComponentAnimation", null, ComposableLambdaKt.rememberComposableLambda(991343342, composerImpl, new Function4() { // from class: com.android.systemui.volume.panel.ui.composable.VerticalVolumePanelContentKt$VerticalVolumePanelContent$1$3
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                List<ComponentState> list = (List) obj2;
                Composer composer2 = (Composer) obj3;
                ((Number) obj4).intValue();
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                Modifier wrapContentHeight$default = SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), false, 3);
                Arrangement arrangement2 = Arrangement.INSTANCE;
                float f = VolumePanelComposeScope.this.state.isLargeScreen ? 28 : 20;
                Dp.Companion companion2 = Dp.Companion;
                arrangement2.getClass();
                Arrangement.SpacedAligned m75spacedBy0680j_42 = Arrangement.m75spacedBy0680j_4(f);
                VolumePanelComposeScope volumePanelComposeScope2 = VolumePanelComposeScope.this;
                Alignment.Companion.getClass();
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(m75spacedBy0680j_42, Alignment.Companion.Top, composer2, 0);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
                Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composer2, wrapContentHeight$default);
                ComposeUiNode.Companion.getClass();
                Function0 function02 = ComposeUiNode.Companion.Constructor;
                if (!(composerImpl2.applier instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl2.startReusableNode();
                if (composerImpl2.inserting) {
                    composerImpl2.createNode(function02);
                } else {
                    composerImpl2.useNode();
                }
                Updater.m276setimpl(composer2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m276setimpl(composer2, currentCompositionLocalScope2, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function22);
                }
                Updater.m276setimpl(composer2, materializeModifier2, ComposeUiNode.Companion.SetModifier);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                int size = list.size();
                int i4 = 0;
                for (int i5 = 0; i5 < size; i5++) {
                    i4 += ((ComponentState) list.get(i5)).isVisible ? 1 : 0;
                }
                composerImpl2.startReplaceGroup(1781385673);
                if (i4 == 1) {
                    SpacerKt.Spacer(composer2, rowScopeInstance.weight(Modifier.Companion, 0.5f, true));
                }
                composerImpl2.end(false);
                composerImpl2.startReplaceGroup(1781385804);
                for (ComponentState componentState3 : list) {
                    composerImpl2.startReplaceGroup(1781385862);
                    if (componentState3.isVisible) {
                        ((ComposeVolumePanelUiComponent) componentState3.component).Content(volumePanelComposeScope2, rowScopeInstance.weight(Modifier.Companion, 1.0f, true), composer2, 0);
                    }
                    composerImpl2.end(false);
                }
                composerImpl2.end(false);
                composerImpl2.startReplaceGroup(-2123807775);
                if (i4 == 1) {
                    SpacerKt.Spacer(composer2, rowScopeInstance.weight(Modifier.Companion, 0.5f, true));
                }
                composerImpl2.end(false);
                composerImpl2.end(true);
                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                return Unit.INSTANCE;
            }
        }), composerImpl, 1597448, 46);
        composerImpl.end(true);
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Modifier modifier3 = modifier2;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.ui.composable.VerticalVolumePanelContentKt$VerticalVolumePanelContent$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    VerticalVolumePanelContentKt.VerticalVolumePanelContent(VolumePanelComposeScope.this, componentsLayout, modifier3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
