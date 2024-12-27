package com.android.systemui.volume.panel.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
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
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.volume.panel.ui.layout.ComponentsLayout;
import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelState;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

public abstract class VolumePanelRootKt {
    public static final float padding;

    static {
        Dp.Companion companion = Dp.Companion;
        padding = 24;
    }

    public static final void BottomBar(final VolumePanelComposeScope volumePanelComposeScope, final ComponentsLayout componentsLayout, Modifier modifier, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(54378751);
        if ((i2 & 2) != 0) {
            modifier = Modifier.Companion;
        }
        final Modifier modifier2 = modifier;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (componentsLayout.bottomBarComponent.isVisible) {
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(modifier2, 1.0f);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int i3 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, fillMaxWidth);
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
            Updater.m276setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function2);
            }
            Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(1466769242);
            ((ComposeVolumePanelUiComponent) componentsLayout.bottomBarComponent.component).Content(volumePanelComposeScope, Modifier.Companion, composerImpl, (i & 14) | 48);
            composerImpl.end(false);
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$BottomBar$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    VolumePanelRootKt.BottomBar(VolumePanelComposeScope.this, componentsLayout, modifier2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void Components(final VolumePanelComposeScope volumePanelComposeScope, final ComponentsLayout componentsLayout, Modifier modifier, Composer composer, final int i, final int i2) {
        Arrangement.SpacedAligned m75spacedBy0680j_4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(359173169);
        if ((i2 & 2) != 0) {
            modifier = Modifier.Companion;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        VolumePanelState volumePanelState = volumePanelComposeScope.state;
        if (volumePanelState.isLargeScreen) {
            Arrangement arrangement = Arrangement.INSTANCE;
            Dp.Companion companion = Dp.Companion;
            arrangement.getClass();
            m75spacedBy0680j_4 = Arrangement.m75spacedBy0680j_4(20);
        } else if (volumePanelState.orientation == 1) {
            Arrangement.INSTANCE.getClass();
            m75spacedBy0680j_4 = Arrangement.m75spacedBy0680j_4(padding);
        } else {
            Arrangement arrangement2 = Arrangement.INSTANCE;
            Dp.Companion companion2 = Dp.Companion;
            arrangement2.getClass();
            m75spacedBy0680j_4 = Arrangement.m75spacedBy0680j_4(4);
        }
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m75spacedBy0680j_4, Alignment.Companion.Start, composerImpl, 0);
        int i3 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
        VolumePanelState volumePanelState2 = volumePanelComposeScope.state;
        if ((volumePanelState2.orientation == 1) || volumePanelState2.isLargeScreen) {
            composerImpl.startReplaceGroup(-1674715450);
            VerticalVolumePanelContentKt.VerticalVolumePanelContent(volumePanelComposeScope, componentsLayout, columnScopeInstance.weight(Modifier.Companion, 1.0f, false), composerImpl, (i & 14) | 64, 0);
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceGroup(-1674715276);
            Modifier weight = columnScopeInstance.weight(Modifier.Companion, 1.0f, false);
            float f = IKnoxCustomManager.Stub.TRANSACTION_getWifiState;
            Dp.Companion companion3 = Dp.Companion;
            HorizontalVolumePanelContentKt.HorizontalVolumePanelContent(volumePanelComposeScope, componentsLayout, SizeKt.m110heightInVpY3zN4$default(weight, 0.0f, f, 1), composerImpl, (i & 14) | 64, 0);
            composerImpl.end(false);
        }
        BottomBar(volumePanelComposeScope, componentsLayout, Modifier.Companion, composerImpl, (i & 14) | 448, 0);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Modifier modifier2 = modifier;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$Components$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    VolumePanelRootKt.Components(VolumePanelComposeScope.this, componentsLayout, modifier2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0058, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void VolumePanelRoot(final com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel r7, final androidx.compose.ui.Modifier r8, androidx.compose.runtime.Composer r9, final int r10, final int r11) {
        /*
            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
            r0 = -1113483181(0xffffffffbda19853, float:-0.07890382)
            r9.startRestartGroup(r0)
            r0 = r11 & 2
            if (r0 == 0) goto Le
            androidx.compose.ui.Modifier$Companion r8 = androidx.compose.ui.Modifier.Companion
        Le:
            androidx.compose.runtime.OpaqueKey r0 = androidx.compose.runtime.ComposerKt.invocation
            r0 = 2131951946(0x7f13014a, float:1.954032E38)
            java.lang.String r0 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r0, r9)
            kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r7.volumePanelState
            androidx.compose.runtime.MutableState r1 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r1, r9)
            r2 = 56
            r3 = 0
            kotlinx.coroutines.flow.ReadonlySharedFlow r4 = r7.componentsLayout
            androidx.compose.runtime.MutableState r2 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r4, r3, r9, r2)
            com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope r3 = new com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope
            java.lang.Object r1 = r1.getValue()
            com.android.systemui.volume.panel.ui.viewmodel.VolumePanelState r1 = (com.android.systemui.volume.panel.ui.viewmodel.VolumePanelState) r1
            r3.<init>(r1)
            java.lang.Object r1 = r2.getValue()
            com.android.systemui.volume.panel.ui.layout.ComponentsLayout r1 = (com.android.systemui.volume.panel.ui.layout.ComponentsLayout) r1
            r2 = 2084750241(0x7c42c3a1, float:4.0450937E36)
            r9.startReplaceGroup(r2)
            r6 = 0
            if (r1 != 0) goto L41
            goto L80
        L41:
            r2 = 434458861(0x19e550ed, float:2.371073E-23)
            r9.startReplaceGroup(r2)
            boolean r2 = r9.changed(r0)
            java.lang.Object r4 = r9.rememberedValue()
            if (r2 != 0) goto L5a
            androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r4 != r2) goto L62
        L5a:
            com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$VolumePanelRoot$1$1$1$1 r4 = new com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$VolumePanelRoot$1$1$1$1
            r4.<init>()
            r9.updateRememberedValue(r4)
        L62:
            kotlin.jvm.functions.Function1 r4 = (kotlin.jvm.functions.Function1) r4
            r9.end(r6)
            androidx.compose.ui.Modifier r0 = androidx.compose.ui.semantics.SemanticsModifierKt.semantics(r8, r6, r4)
            r2 = 20
            float r2 = (float) r2
            androidx.compose.ui.unit.Dp$Companion r4 = androidx.compose.ui.unit.Dp.Companion
            float r4 = com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt.padding
            androidx.compose.ui.Modifier r2 = androidx.compose.foundation.layout.PaddingKt.m105paddingqDBjuR0(r0, r4, r4, r4, r2)
            r4 = 64
            r5 = 0
            r0 = r3
            r3 = r9
            Components(r0, r1, r2, r3, r4, r5)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
        L80:
            r9.end(r6)
            androidx.compose.runtime.RecomposeScopeImpl r9 = r9.endRestartGroup()
            if (r9 == 0) goto L90
            com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$VolumePanelRoot$2 r0 = new com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$VolumePanelRoot$2
            r0.<init>()
            r9.block = r0
        L90:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt.VolumePanelRoot(com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int):void");
    }
}
