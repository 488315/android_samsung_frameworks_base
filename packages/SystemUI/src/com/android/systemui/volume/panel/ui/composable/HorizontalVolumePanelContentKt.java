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
import androidx.compose.ui.BiasAlignment;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public abstract class HorizontalVolumePanelContentKt {
    /* JADX WARN: Type inference failed for: r4v12, types: [com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContentKt$HorizontalVolumePanelContent$1$2$2, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r5v12, types: [com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContentKt$HorizontalVolumePanelContent$1$2$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r5v14, types: [com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContentKt$HorizontalVolumePanelContent$1$1$1, kotlin.jvm.internal.Lambda] */
    public static final void HorizontalVolumePanelContent(final VolumePanelComposeScope volumePanelComposeScope, final ComponentsLayout componentsLayout, Modifier modifier, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1334321093);
        Modifier modifier2 = (i2 & 2) != 0 ? Modifier.Companion : modifier;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final float f = 20;
        Dp.Companion companion = Dp.Companion;
        Arrangement.INSTANCE.getClass();
        Arrangement.SpacedAligned m75spacedBy0680j_4 = Arrangement.m75spacedBy0680j_4(f);
        Alignment.Companion.getClass();
        RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(m75spacedBy0680j_4, Alignment.Companion.Top, composerImpl, 6);
        int i3 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier2);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        boolean z = composerImpl.applier instanceof Applier;
        if (!z) {
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
        Updater.m276setimpl(composerImpl, rowMeasurePolicy, function2);
        Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, function22);
        Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function23);
        }
        Function2 function24 = ComposeUiNode.Companion.SetModifier;
        Updater.m276setimpl(composerImpl, materializeModifier, function24);
        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
        Modifier verticalScroll$default = ScrollKt.verticalScroll$default(rowScopeInstance.weight(Modifier.Companion, 1.0f, true), ScrollKt.rememberScrollState(composerImpl), false, 14);
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.m75spacedBy0680j_4(f), Alignment.Companion.Start, composerImpl, 6);
        int i4 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, verticalScroll$default);
        if (!z) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, columnMeasurePolicy, function2);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope2, function22);
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i4))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i4, composerImpl, i4, function23);
        }
        Updater.m276setimpl(composerImpl, materializeModifier2, function24);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        composerImpl.startReplaceGroup(625650753);
        for (final ComponentState componentState : componentsLayout.contentComponents) {
            AnimatedVisibilityKt.AnimatedVisibility(columnScopeInstance, componentState.isVisible, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-872588377, composerImpl, new Function3() { // from class: com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContentKt$HorizontalVolumePanelContent$1$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
            z = z;
        }
        boolean z2 = z;
        boolean z3 = false;
        composerImpl.end(false);
        composerImpl.end(true);
        Modifier verticalScroll$default2 = ScrollKt.verticalScroll$default(rowScopeInstance.weight(Modifier.Companion, 1.0f, true), ScrollKt.rememberScrollState(composerImpl), false, 14);
        Arrangement arrangement = Arrangement.INSTANCE;
        Alignment.Companion.getClass();
        BiasAlignment.Vertical vertical = Alignment.Companion.Top;
        arrangement.getClass();
        ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(Arrangement.m77spacedByD5KLDUw(f, vertical), Alignment.Companion.Start, composerImpl, 6);
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        int i5 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, verticalScroll$default2);
        ComposeUiNode.Companion.getClass();
        Function0 function02 = ComposeUiNode.Companion.Constructor;
        if (!z2) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function02);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, columnMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope3, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function25 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i5))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i5, composerImpl, i5, function25);
        }
        Updater.m276setimpl(composerImpl, materializeModifier3, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
        composerImpl.startReplaceGroup(-1750281161);
        for (final ComponentState componentState2 : componentsLayout.headerComponents) {
            AnimatedVisibilityKt.AnimatedVisibility(columnScopeInstance2, componentState2.isVisible, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-1685962416, composerImpl, new Function3() { // from class: com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContentKt$HorizontalVolumePanelContent$1$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    OpaqueKey opaqueKey3 = ComposerKt.invocation;
                    ((ComposeVolumePanelUiComponent) ComponentState.this.component).Content(volumePanelComposeScope, Modifier.Companion, (Composer) obj2, 48);
                    return Unit.INSTANCE;
                }
            }), composerImpl, 1572870, 30);
            z3 = z3;
        }
        composerImpl.end(z3);
        AnimatedContentKt.AnimatedContent(componentsLayout.footerComponents, null, null, null, "FooterComponentAnimation", null, ComposableLambdaKt.rememberComposableLambda(-788015821, composerImpl, new Function4() { // from class: com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContentKt$HorizontalVolumePanelContent$1$2$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                List<ComponentState> list = (List) obj2;
                Composer composer2 = (Composer) obj3;
                ((Number) obj4).intValue();
                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
                Arrangement arrangement2 = Arrangement.INSTANCE;
                float f2 = f;
                arrangement2.getClass();
                Arrangement.SpacedAligned m75spacedBy0680j_42 = Arrangement.m75spacedBy0680j_4(f2);
                VolumePanelComposeScope volumePanelComposeScope2 = volumePanelComposeScope;
                Alignment.Companion.getClass();
                RowMeasurePolicy rowMeasurePolicy2 = RowKt.rowMeasurePolicy(m75spacedBy0680j_42, Alignment.Companion.Top, composer2, 6);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                PersistentCompositionLocalMap currentCompositionLocalScope4 = composerImpl2.currentCompositionLocalScope();
                Modifier materializeModifier4 = ComposedModifierKt.materializeModifier(composer2, fillMaxWidth);
                ComposeUiNode.Companion.getClass();
                Function0 function03 = ComposeUiNode.Companion.Constructor;
                if (!(composerImpl2.applier instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl2.startReusableNode();
                if (composerImpl2.inserting) {
                    composerImpl2.createNode(function03);
                } else {
                    composerImpl2.useNode();
                }
                Updater.m276setimpl(composer2, rowMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m276setimpl(composer2, currentCompositionLocalScope4, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function26 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function26);
                }
                Updater.m276setimpl(composer2, materializeModifier4, ComposeUiNode.Companion.SetModifier);
                RowScopeInstance rowScopeInstance2 = RowScopeInstance.INSTANCE;
                composerImpl2.startReplaceGroup(-1192635590);
                for (ComponentState componentState3 : list) {
                    composerImpl2.startReplaceGroup(-621549927);
                    if (componentState3.isVisible) {
                        ((ComposeVolumePanelUiComponent) componentState3.component).Content(volumePanelComposeScope2, rowScopeInstance2.weight(Modifier.Companion, 1.0f, true), composer2, 0);
                    }
                    composerImpl2.end(false);
                }
                composerImpl2.end(false);
                composerImpl2.end(true);
                OpaqueKey opaqueKey4 = ComposerKt.invocation;
                return Unit.INSTANCE;
            }
        }), composerImpl, 1597448, 46);
        composerImpl.end(true);
        composerImpl.end(true);
        OpaqueKey opaqueKey3 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Modifier modifier3 = modifier2;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.ui.composable.HorizontalVolumePanelContentKt$HorizontalVolumePanelContent$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    HorizontalVolumePanelContentKt.HorizontalVolumePanelContent(VolumePanelComposeScope.this, componentsLayout, modifier3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
