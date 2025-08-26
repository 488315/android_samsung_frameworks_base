package com.android.systemui.volume.panel.component.button.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.state.ToggleableState;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel;
import com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent;
import com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class ToggleButtonComponent implements ComposeVolumePanelUiComponent {
    public final Function1 onCheckedChange;
    public final StateFlow viewModelFlow;

    public ToggleButtonComponent(StateFlow stateFlow, Function1 function1) {
        this.viewModelFlow = stateFlow;
        this.onCheckedChange = function1;
    }

    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    public final void Content(VolumePanelComposeScope volumePanelComposeScope, Modifier modifier, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1883072515);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent.Content (ToggleButtonComponent.kt:58)");
        }
        final ButtonViewModel buttonViewModel = (ButtonViewModel) FlowExtKt.collectAsStateWithLifecycle(this.viewModelFlow, composerImpl).getValue();
        if (buttonViewModel == null) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            return;
        }
        final String string = buttonViewModel.label.toString();
        Arrangement arrangement = Arrangement.INSTANCE;
        Dp.Companion companion = Dp.Companion;
        arrangement.getClass();
        Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(12);
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_4, Alignment.Companion.CenterHorizontally, composerImpl, 54);
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
        BottomComponentButtonSurfaceKt.BottomComponentButtonSurface(48, composerImpl, ComposableLambdaKt.rememberComposableLambda(14766006, new Function2() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent$Content$1$1
            /* JADX WARN: Removed duplicated region for block: B:20:0x00ce  */
            /* JADX WARN: Removed duplicated region for block: B:25:0x0109  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj, Object obj2) {
                ButtonColors buttonColorsM252buttonColorsro_MJ88;
                Composer composer2 = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                    } else {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent.Content.<anonymous>.<anonymous> (ToggleButtonComponent.kt:69)");
                        }
                        final ButtonViewModel buttonViewModel2 = buttonViewModel;
                        if (buttonViewModel2.isActive) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(1167900027);
                            composerImpl3.startReplaceGroup(1168228007);
                            ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
                            MaterialTheme.INSTANCE.getClass();
                            long j = MaterialTheme.getColorScheme(composerImpl3).tertiaryContainer;
                            long j2 = MaterialTheme.getColorScheme(composerImpl3).onTertiaryContainer;
                            buttonDefaults.getClass();
                            buttonColorsM252buttonColorsro_MJ88 = ButtonDefaults.m252buttonColorsro_MJ88(j, j2, composerImpl3, 12);
                            composerImpl3.end(false);
                            composerImpl3.end(false);
                        } else {
                            ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                            composerImpl4.startReplaceGroup(1168566155);
                            composerImpl4.startReplaceGroup(1168906628);
                            ButtonDefaults buttonDefaults2 = ButtonDefaults.INSTANCE;
                            Color.Companion.getClass();
                            long j3 = Color.Transparent;
                            MaterialTheme.INSTANCE.getClass();
                            long j4 = MaterialTheme.getColorScheme(composerImpl4).onSurfaceVariant;
                            buttonDefaults2.getClass();
                            buttonColorsM252buttonColorsro_MJ88 = ButtonDefaults.m252buttonColorsro_MJ88(j3, j4, composerImpl4, 12);
                            composerImpl4.end(false);
                            composerImpl4.end(false);
                        }
                        ButtonColors buttonColors = buttonColorsM252buttonColorsro_MJ88;
                        Dp.Companion companion2 = Dp.Companion;
                        Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(SizeKt.fillMaxSize(Modifier.Companion, 1.0f), 8);
                        ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                        composerImpl5.startReplaceGroup(730456976);
                        boolean zChangedInstance = composerImpl5.changedInstance(buttonViewModel2);
                        final String str = string;
                        boolean zChanged = zChangedInstance | composerImpl5.changed(str);
                        Object objRememberedValue = composerImpl5.rememberedValue();
                        Composer.Companion companion3 = Composer.Companion;
                        if (!zChanged) {
                            companion3.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new Function1() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent$Content$1$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj3) {
                                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj3;
                                        Role.Companion.getClass();
                                        SemanticsPropertiesKt.m719setRolekuIjeqM(semanticsPropertyReceiver, Role.Switch);
                                        SemanticsPropertiesKt.setToggleableState(semanticsPropertyReceiver, buttonViewModel2.isActive ? ToggleableState.On : ToggleableState.Off);
                                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, str);
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl5.updateRememberedValue(objRememberedValue);
                            }
                            composerImpl5.end(false);
                            Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM125padding3ABfNKs, false, (Function1) objRememberedValue);
                            RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(20);
                            PaddingValuesImpl paddingValuesImplM120PaddingValues0680j_4 = PaddingKt.m120PaddingValues0680j_4(0);
                            composerImpl5.startReplaceGroup(730471029);
                            final ToggleButtonComponent toggleButtonComponent = this;
                            boolean zChangedInstance2 = composerImpl5.changedInstance(toggleButtonComponent) | composerImpl5.changedInstance(buttonViewModel2);
                            Object objRememberedValue2 = composerImpl5.rememberedValue();
                            if (!zChangedInstance2) {
                                companion3.getClass();
                                if (objRememberedValue2 == Composer.Companion.Empty) {
                                    objRememberedValue2 = new Function0() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent$Content$1$1$$ExternalSyntheticLambda1
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            toggleButtonComponent.onCheckedChange.mo781invoke(Boolean.valueOf(!buttonViewModel2.isActive));
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl5.updateRememberedValue(objRememberedValue2);
                                }
                                composerImpl5.end(false);
                                ButtonKt.Button((Function0) objRememberedValue2, modifierSemantics, false, roundedCornerShapeM187RoundedCornerShape0680j_4, buttonColors, null, null, paddingValuesImplM120PaddingValues0680j_4, null, ComposableLambdaKt.rememberComposableLambda(1777153478, new Function3() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent$Content$1$1.3
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                                    @Override // kotlin.jvm.functions.Function3
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                        Composer composer3 = (Composer) obj4;
                                        if ((((Number) obj5).intValue() & 17) == 16) {
                                            ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                            if (composerImpl6.getSkipping()) {
                                                composerImpl6.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent.Content.<anonymous>.<anonymous>.<anonymous> (ToggleButtonComponent.kt:112)");
                                                }
                                                Dp.Companion companion4 = Dp.Companion;
                                                IconKt.m1074IconFNF3uiM(buttonViewModel2.icon, SizeKt.m140size3ABfNKs(Modifier.Companion, 24), 0L, composer3, 48, 4);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl5), composerImpl5, 817889280, 356);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        }, composerImpl), null);
        Modifier.Companion companion2 = Modifier.Companion;
        composerImpl.startReplaceGroup(-446910608);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (objRememberedValue == Composer.Companion.Empty) {
            objRememberedValue = new ToggleButtonComponent$$ExternalSyntheticLambda0();
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        composerImpl.end(false);
        Modifier modifierM27basicMarquee1Mj1MLw$default = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(SemanticsModifierKt.clearAndSetSemantics(companion2, (Function1) objRememberedValue), 0, 63);
        MaterialTheme.INSTANCE.getClass();
        TextKt.m317Text4IGK_g(string, modifierM27basicMarquee1Mj1MLw$default, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 2, 0, null, MaterialTheme.getTypography(composerImpl).labelMedium, composerImpl, 0, 3072, 57340);
        composerImpl.end(true);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }
}
