package com.android.systemui.volume.panel.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
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
import com.android.systemui.volume.panel.ui.viewmodel.ComponentState;
import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelState;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class VolumePanelRootKt {
    public static final float padding;

    static {
        Dp.Companion companion = Dp.Companion;
        padding = 24;
    }

    public static final void BottomBar(final VolumePanelComposeScope volumePanelComposeScope, final ComponentsLayout componentsLayout, final Modifier.Companion companion, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(54378751);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(volumePanelComposeScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(componentsLayout) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(companion) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.ui.composable.BottomBar (VolumePanelRoot.kt:93)");
            }
            ComponentState componentState = componentsLayout.bottomBarComponent;
            if (componentState.isVisible) {
                Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion, 1.0f);
                Alignment.Companion.getClass();
                MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, fillMaxWidth);
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
                Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                }
                Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                composerImpl.startReplaceGroup(-1961198403);
                ((ComposeVolumePanelUiComponent) componentState.component).Content(volumePanelComposeScope, Modifier.Companion, composerImpl, (i2 & 14) | 48);
                composerImpl.end(false);
                composerImpl.end(true);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    VolumePanelRootKt.BottomBar(VolumePanelComposeScope.this, componentsLayout, companion, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void Components(final VolumePanelComposeScope volumePanelComposeScope, final ComponentsLayout componentsLayout, final Modifier modifier, Composer composer, final int i) {
        float f;
        Arrangement.SpacedAligned m91spacedBy0680j_4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(359173169);
        int i2 = (composerImpl.changed(volumePanelComposeScope) ? 4 : 2) | i | (composerImpl.changedInstance(componentsLayout) ? 32 : 16) | (composerImpl.changed(modifier) ? 256 : 128);
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.ui.composable.Components (VolumePanelRoot.kt:66)");
            }
            VolumePanelState volumePanelState = volumePanelComposeScope.state;
            if (volumePanelState.isLargeScreen) {
                Arrangement arrangement = Arrangement.INSTANCE;
                Dp.Companion companion = Dp.Companion;
                arrangement.getClass();
                m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(20);
            } else {
                if (volumePanelState.orientation == 1) {
                    Arrangement.INSTANCE.getClass();
                    f = padding;
                } else {
                    Arrangement arrangement2 = Arrangement.INSTANCE;
                    f = 4;
                    Dp.Companion companion2 = Dp.Companion;
                    arrangement2.getClass();
                }
                m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(f);
            }
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m91spacedBy0680j_4, Alignment.Companion.Start, composerImpl, 0);
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
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            if (volumePanelState.orientation == 1 || volumePanelState.isLargeScreen) {
                composerImpl.startReplaceGroup(1211361470);
                VerticalVolumePanelContentKt.VerticalVolumePanelContent(volumePanelComposeScope, componentsLayout, columnScopeInstance.weight(Modifier.Companion, 1.0f, false), composerImpl, i2 & 126);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(1211535845);
                Modifier weight = columnScopeInstance.weight(Modifier.Companion, 1.0f, false);
                float f2 = IKnoxCustomManager.Stub.TRANSACTION_getWifiState;
                Dp.Companion companion3 = Dp.Companion;
                HorizontalVolumePanelContentKt.HorizontalVolumePanelContent(volumePanelComposeScope, componentsLayout, SizeKt.m132heightInVpY3zN4$default(weight, 0.0f, f2, 1), composerImpl, i2 & 126);
                composerImpl.end(false);
            }
            BottomBar(volumePanelComposeScope, componentsLayout, Modifier.Companion, composerImpl, (i2 & 112) | (i2 & 14) | 384);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(componentsLayout, modifier, i) { // from class: com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$$ExternalSyntheticLambda2
                public final /* synthetic */ ComponentsLayout f$1;
                public final /* synthetic */ Modifier f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    VolumePanelRootKt.Components(VolumePanelComposeScope.this, this.f$1, this.f$2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x007e, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void VolumePanelRoot(final com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel r7, final androidx.compose.ui.Modifier.Companion r8, androidx.compose.runtime.Composer r9, final int r10) {
        /*
            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
            r0 = -1113483181(0xffffffffbda19853, float:-0.07890382)
            r9.startRestartGroup(r0)
            boolean r0 = r9.changedInstance(r7)
            if (r0 == 0) goto L10
            r0 = 4
            goto L11
        L10:
            r0 = 2
        L11:
            r0 = r0 | r10
            r0 = r0 | 48
            r0 = r0 & 19
            r1 = 18
            if (r0 != r1) goto L26
            boolean r0 = r9.getSkipping()
            if (r0 != 0) goto L21
            goto L26
        L21:
            r9.skipToGroupEnd()
            goto Lad
        L26:
            androidx.compose.ui.Modifier$Companion r8 = androidx.compose.ui.Modifier.Companion
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L33
            java.lang.String r0 = "com.android.systemui.volume.panel.ui.composable.VolumePanelRoot (VolumePanelRoot.kt:44)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L33:
            r0 = 2131951977(0x7f130169, float:1.9540384E38)
            java.lang.String r0 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r0, r9)
            kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r7.volumePanelState
            androidx.compose.runtime.MutableState r1 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r1, r9)
            kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r7.componentsLayout
            androidx.compose.runtime.MutableState r2 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r2, r9)
            com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope r3 = new com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope
            java.lang.Object r1 = r1.getValue()
            com.android.systemui.volume.panel.ui.viewmodel.VolumePanelState r1 = (com.android.systemui.volume.panel.ui.viewmodel.VolumePanelState) r1
            r3.<init>(r1)
            java.lang.Object r1 = r2.getValue()
            com.android.systemui.volume.panel.ui.layout.ComponentsLayout r1 = (com.android.systemui.volume.panel.ui.layout.ComponentsLayout) r1
            r2 = 202756092(0xc15cffc, float:1.154113E-31)
            r9.startReplaceGroup(r2)
            r2 = 0
            if (r1 != 0) goto L61
            goto La1
        L61:
            java.lang.String r4 = "VolumePanel"
            androidx.compose.ui.Modifier r4 = com.android.systemui.compose.modifiers.SysuiTestTagKt.sysuiResTag(r8, r4)
            r5 = 583332390(0x22c4f226, float:5.338229E-18)
            r9.startReplaceGroup(r5)
            boolean r5 = r9.changed(r0)
            java.lang.Object r6 = r9.rememberedValue()
            if (r5 != 0) goto L80
            androidx.compose.runtime.Composer$Companion r5 = androidx.compose.runtime.Composer.Companion
            r5.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r5 = androidx.compose.runtime.Composer.Companion.Empty
            if (r6 != r5) goto L88
        L80:
            com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$$ExternalSyntheticLambda0 r6 = new com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$$ExternalSyntheticLambda0
            r6.<init>()
            r9.updateRememberedValue(r6)
        L88:
            kotlin.jvm.functions.Function1 r6 = (kotlin.jvm.functions.Function1) r6
            r9.end(r2)
            androidx.compose.ui.Modifier r0 = androidx.compose.ui.semantics.SemanticsModifierKt.semantics(r4, r2, r6)
            r4 = 20
            float r4 = (float) r4
            androidx.compose.ui.unit.Dp$Companion r5 = androidx.compose.ui.unit.Dp.Companion
            float r5 = com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt.padding
            androidx.compose.ui.Modifier r0 = androidx.compose.foundation.layout.PaddingKt.m127paddingqDBjuR0(r0, r5, r5, r5, r4)
            Components(r3, r1, r0, r9, r2)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
        La1:
            r9.end(r2)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lad
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lad:
            androidx.compose.runtime.RecomposeScopeImpl r9 = r9.endRestartGroup()
            if (r9 == 0) goto Lba
            com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$$ExternalSyntheticLambda1 r0 = new com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$$ExternalSyntheticLambda1
            r0.<init>(r8, r10)
            r9.block = r0
        Lba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt.VolumePanelRoot(com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel, androidx.compose.ui.Modifier$Companion, androidx.compose.runtime.Composer, int):void");
    }
}
