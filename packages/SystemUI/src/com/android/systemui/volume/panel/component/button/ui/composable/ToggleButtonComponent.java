package com.android.systemui.volume.panel.component.button.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.FillElement;
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

public final class ToggleButtonComponent implements ComposeVolumePanelUiComponent {
    public final Function1 onCheckedChange;
    public final StateFlow viewModelFlow;

    public ToggleButtonComponent(StateFlow stateFlow, Function1 function1) {
        this.viewModelFlow = stateFlow;
        this.onCheckedChange = function1;
    }

    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    public final void Content(final VolumePanelComposeScope volumePanelComposeScope, final Modifier modifier, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1883072515);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final ButtonViewModel buttonViewModel = (ButtonViewModel) FlowExtKt.collectAsStateWithLifecycle(this.viewModelFlow, composerImpl).getValue();
        if (buttonViewModel == null) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent$Content$viewModel$1
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj2).intValue();
                        ToggleButtonComponent.this.Content(volumePanelComposeScope, modifier, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        final String obj = buttonViewModel.label.toString();
        Arrangement arrangement = Arrangement.INSTANCE;
        Dp.Companion companion = Dp.Companion;
        arrangement.getClass();
        Arrangement.SpacedAligned m75spacedBy0680j_4 = Arrangement.m75spacedBy0680j_4(12);
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m75spacedBy0680j_4, Alignment.Companion.CenterHorizontally, composerImpl, 54);
        int i2 = composerImpl.compoundKeyHash;
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
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        BottomComponentButtonSurfaceKt.BottomComponentButtonSurface(null, ComposableLambdaKt.rememberComposableLambda(14766006, composerImpl, new Function2() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent$Content$1$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj2, Object obj3) {
                ButtonColors m209buttonColorsro_MJ88;
                Composer composer2 = (Composer) obj2;
                if ((((Number) obj3).intValue() & 11) == 2) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                if (ButtonViewModel.this.isActive) {
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    composerImpl3.startReplaceGroup(162108855);
                    ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
                    MaterialTheme.INSTANCE.getClass();
                    long j = MaterialTheme.getColorScheme(composerImpl3).tertiaryContainer;
                    long j2 = MaterialTheme.getColorScheme(composerImpl3).onTertiaryContainer;
                    buttonDefaults.getClass();
                    m209buttonColorsro_MJ88 = ButtonDefaults.m209buttonColorsro_MJ88(j, j2, composerImpl3, 12);
                    composerImpl3.end(false);
                } else {
                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                    composerImpl4.startReplaceGroup(162109143);
                    ButtonDefaults buttonDefaults2 = ButtonDefaults.INSTANCE;
                    Color.Companion.getClass();
                    long j3 = Color.Transparent;
                    MaterialTheme.INSTANCE.getClass();
                    long j4 = MaterialTheme.getColorScheme(composerImpl4).onSurfaceVariant;
                    buttonDefaults2.getClass();
                    m209buttonColorsro_MJ88 = ButtonDefaults.m209buttonColorsro_MJ88(j3, j4, composerImpl4, 12);
                    composerImpl4.end(false);
                }
                ButtonColors buttonColors = m209buttonColorsro_MJ88;
                Modifier.Companion companion2 = Modifier.Companion;
                FillElement fillElement = SizeKt.FillWholeMaxSize;
                companion2.then(fillElement);
                Dp.Companion companion3 = Dp.Companion;
                Modifier m102padding3ABfNKs = PaddingKt.m102padding3ABfNKs(fillElement, 8);
                final ButtonViewModel buttonViewModel2 = ButtonViewModel.this;
                final String str = obj;
                Modifier semantics = SemanticsModifierKt.semantics(m102padding3ABfNKs, false, new Function1() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent$Content$1$1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj4) {
                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj4;
                        Role.Companion.getClass();
                        SemanticsPropertiesKt.m623setRolekuIjeqM(semanticsPropertyReceiver, Role.Switch);
                        SemanticsPropertiesKt.setToggleableState(semanticsPropertyReceiver, ButtonViewModel.this.isActive ? ToggleableState.On : ToggleableState.Off);
                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, str);
                        return Unit.INSTANCE;
                    }
                });
                RoundedCornerShape m151RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(20);
                PaddingValuesImpl m99PaddingValues0680j_4 = PaddingKt.m99PaddingValues0680j_4(0);
                final ToggleButtonComponent toggleButtonComponent = this;
                final ButtonViewModel buttonViewModel3 = ButtonViewModel.this;
                Function0 function02 = new Function0() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent$Content$1$1.2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ToggleButtonComponent.this.onCheckedChange.invoke(Boolean.valueOf(!buttonViewModel3.isActive));
                        return Unit.INSTANCE;
                    }
                };
                final ButtonViewModel buttonViewModel4 = ButtonViewModel.this;
                ButtonKt.Button(function02, semantics, false, m151RoundedCornerShape0680j_4, buttonColors, null, null, m99PaddingValues0680j_4, null, ComposableLambdaKt.rememberComposableLambda(1777153478, composer2, new Function3() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent$Content$1$1.3
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj4, Object obj5, Object obj6) {
                        Composer composer3 = (Composer) obj5;
                        if ((((Number) obj6).intValue() & 81) == 16) {
                            ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                            if (composerImpl5.getSkipping()) {
                                composerImpl5.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        OpaqueKey opaqueKey3 = ComposerKt.invocation;
                        Dp.Companion companion4 = Dp.Companion;
                        IconKt.m938IconFNF3uiM(ButtonViewModel.this.icon, SizeKt.m115size3ABfNKs(Modifier.Companion, 24), 0L, composer3, 48, 4);
                        return Unit.INSTANCE;
                    }
                }), composer2, 817889280, 356);
                return Unit.INSTANCE;
            }
        }), composerImpl, 48, 1);
        Modifier m25basicMarquee1Mj1MLw$default = BasicMarqueeKt.m25basicMarquee1Mj1MLw$default(SemanticsModifierKt.clearAndSetSemantics(Modifier.Companion, new Function1() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent$Content$1$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                return Unit.INSTANCE;
            }
        }));
        MaterialTheme.INSTANCE.getClass();
        TextKt.m257Text4IGK_g(obj, m25basicMarquee1Mj1MLw$default, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 2, 0, null, MaterialTheme.getTypography(composerImpl).labelMedium, composerImpl, 0, 3072, 57340);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block = new Function2() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent$Content$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    ToggleButtonComponent.this.Content(volumePanelComposeScope, modifier, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
