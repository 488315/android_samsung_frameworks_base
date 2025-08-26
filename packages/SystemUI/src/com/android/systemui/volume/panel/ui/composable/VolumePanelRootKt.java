package com.android.systemui.volume.panel.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.R;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.volume.panel.ui.layout.ComponentsLayout;
import com.android.systemui.volume.panel.ui.viewmodel.ComponentState;
import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelState;
import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

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
                Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion, 1.0f);
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxWidth);
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
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    VolumePanelRootKt.BottomBar(volumePanelComposeScope, componentsLayout, companion, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void Components(final VolumePanelComposeScope volumePanelComposeScope, final ComponentsLayout componentsLayout, final Modifier modifier, Composer composer, final int i) throws Throwable {
        float f;
        Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4;
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
                spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(20);
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
                spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(f);
            }
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_4, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            if (volumePanelState.orientation == 1 || volumePanelState.isLargeScreen) {
                composerImpl.startReplaceGroup(1211361470);
                VerticalVolumePanelContentKt.VerticalVolumePanelContent(volumePanelComposeScope, componentsLayout, columnScopeInstance.weight(Modifier.Companion, 1.0f, false), composerImpl, i2 & 126);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(1211535845);
                Modifier modifierWeight = columnScopeInstance.weight(Modifier.Companion, 1.0f, false);
                float f2 = IKnoxCustomManager.Stub.TRANSACTION_getWifiState;
                Dp.Companion companion3 = Dp.Companion;
                HorizontalVolumePanelContentKt.HorizontalVolumePanelContent(volumePanelComposeScope, componentsLayout, SizeKt.m133heightInVpY3zN4$default(modifierWeight, 0.0f, f2, 1), composerImpl, i2 & 126);
                composerImpl.end(false);
            }
            BottomBar(volumePanelComposeScope, componentsLayout, Modifier.Companion, composerImpl, (i2 & 112) | (i2 & 14) | 384);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(componentsLayout, modifier, i) { // from class: com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$$ExternalSyntheticLambda2
                public final /* synthetic */ ComponentsLayout f$1;
                public final /* synthetic */ Modifier f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Throwable {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    VolumePanelRootKt.Components(this.f$0, this.f$1, this.f$2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void VolumePanelRoot(final VolumePanelViewModel volumePanelViewModel, final Modifier.Companion companion, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1113483181);
        if ((((composerImpl.changedInstance(volumePanelViewModel) ? 4 : 2) | i | 48) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.ui.composable.VolumePanelRoot (VolumePanelRoot.kt:44)");
            }
            final String strStringResource = StringResources_androidKt.stringResource(R.string.accessibility_volume_settings, composerImpl);
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(volumePanelViewModel.volumePanelState, composerImpl);
            MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(volumePanelViewModel.componentsLayout, composerImpl);
            VolumePanelComposeScope volumePanelComposeScope = new VolumePanelComposeScope((VolumePanelState) mutableStateCollectAsStateWithLifecycle.getValue());
            ComponentsLayout componentsLayout = (ComponentsLayout) mutableStateCollectAsStateWithLifecycle2.getValue();
            composerImpl.startReplaceGroup(202756092);
            if (componentsLayout != null) {
                Modifier modifierSysuiResTag = SysuiTestTagKt.sysuiResTag(companion, "VolumePanel");
                composerImpl.startReplaceGroup(583332390);
                boolean zChanged = composerImpl.changed(strStringResource);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChanged) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function1() { // from class: com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                SemanticsPropertiesKt.setPaneTitle((SemanticsPropertyReceiver) obj, strStringResource);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    composerImpl.end(false);
                    Dp.Companion companion2 = Dp.Companion;
                    float f = padding;
                    Components(volumePanelComposeScope, componentsLayout, PaddingKt.m128paddingqDBjuR0(SemanticsModifierKt.semantics(modifierSysuiResTag, false, (Function1) objRememberedValue), f, f, f, 20), composerImpl, 0);
                    Unit unit = Unit.INSTANCE;
                }
            }
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(companion, i) { // from class: com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt$$ExternalSyntheticLambda1
                public final /* synthetic */ Modifier.Companion f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    VolumePanelRootKt.VolumePanelRoot(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
