package com.android.systemui.communal.ui.compose.section;

import android.content.res.Resources;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.AnimatedVisibilityScope;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.EasingKt$$ExternalSyntheticLambda0;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Start$1;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.CoreTextFieldKt$$ExternalSyntheticOutline0;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.WidgetsKt;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
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
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.window.AndroidPopup_androidKt;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.communal.ui.viewmodel.PopupType;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

/* loaded from: classes2.dex */
public final class CommunalPopupSection {
    public final CommunalViewModel viewModel;

    public CommunalPopupSection(CommunalViewModel communalViewModel) {
        this.viewModel = communalViewModel;
    }

    public final void ButtonToEditWidgets(final AnimatedVisibilityScope animatedVisibilityScope, final Function0 function0, final Function0 function02, Composer composer, final int i) throws Throwable {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(824270661);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(animatedVisibilityScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.CommunalPopupSection.ButtonToEditWidgets (CommunalPopupSection.kt:98)");
            }
            composerImpl.startReplaceGroup(1114908250);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            final MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) objRememberedValue;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1114910448);
            if (objM == composer$Companion$Empty$1) {
                objM = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl);
            }
            final FocusRequester focusRequester = (FocusRequester) objM;
            composerImpl.end(false);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(1114912503);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new CommunalPopupSection$ButtonToEditWidgets$1$1(focusRequester, null);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) objRememberedValue2);
            Alignment.Companion.getClass();
            IntOffset.Companion companion = IntOffset.Companion;
            AndroidPopup_androidKt.m887PopupK5zGePQ(Alignment.Companion.TopCenter, (0 << 32) | (40 & 4294967295L), function02, null, ComposableLambdaKt.rememberComposableLambda(-1145349246, new Function2() { // from class: com.android.systemui.communal.ui.compose.section.CommunalPopupSection.ButtonToEditWidgets.2
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.CommunalPopupSection.ButtonToEditWidgets.<anonymous> (CommunalPopupSection.kt:113)");
                            }
                            MaterialTheme.INSTANCE.getClass();
                            final ColorScheme colorScheme = MaterialTheme.getColorScheme(composer2);
                            Dp.Companion companion2 = Dp.Companion;
                            Modifier modifierFocusable$default = FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(SizeKt.m131height3ABfNKs(Modifier.Companion, 56), focusRequester), false, mutableInteractionSource, 1);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(-1843743873);
                            Object objRememberedValue3 = composerImpl3.rememberedValue();
                            Composer.Companion.getClass();
                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                objRememberedValue3 = new CommunalPopupSection$ButtonToEditWidgets$2$$ExternalSyntheticLambda0();
                                composerImpl3.updateRememberedValue(objRememberedValue3);
                            }
                            composerImpl3.end(false);
                            Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifierFocusable$default, (Function1) objRememberedValue3);
                            EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda0 = EasingKt.LinearEasing;
                            EnterTransition enterTransitionFadeIn = EnterExitTransitionKt.fadeIn(AnimationSpecKt.tween$default(83, 0, easingKt$$ExternalSyntheticLambda0, 2));
                            ExitTransition exitTransitionFadeOut$default = EnterExitTransitionKt.fadeOut$default(new TweenSpec(83, 167, easingKt$$ExternalSyntheticLambda0), 2);
                            final AnimatedVisibilityScope animatedVisibilityScope2 = animatedVisibilityScope;
                            ButtonKt.Button(function0, BackgroundKt.m26backgroundbw27NRU(animatedVisibilityScope2.animateEnterExit(modifierGraphicsLayer, enterTransitionFadeIn, exitTransitionFadeOut$default), colorScheme.secondary, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(50)), false, null, null, null, null, null, null, ComposableLambdaKt.rememberComposableLambda(1804176786, new Function3() { // from class: com.android.systemui.communal.ui.compose.section.CommunalPopupSection.ButtonToEditWidgets.2.2
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
                                @Override // kotlin.jvm.functions.Function3
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                    Composer composer3 = (Composer) obj4;
                                    if ((((Number) obj5).intValue() & 17) == 16) {
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                        if (composerImpl4.getSkipping()) {
                                            composerImpl4.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.CommunalPopupSection.ButtonToEditWidgets.<anonymous>.<anonymous> (CommunalPopupSection.kt:140)");
                                            }
                                            Modifier.Companion companion3 = Modifier.Companion;
                                            EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda02 = EasingKt.LinearEasing;
                                            Modifier modifierAnimateEnterExit = animatedVisibilityScope2.animateEnterExit(companion3, EnterExitTransitionKt.fadeIn$default(new TweenSpec(167, 83, easingKt$$ExternalSyntheticLambda02), 2), EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(167, 0, easingKt$$ExternalSyntheticLambda02, 2), 2));
                                            Arrangement.INSTANCE.getClass();
                                            Arrangement$Start$1 arrangement$Start$1 = Arrangement.Start;
                                            Alignment.Companion.getClass();
                                            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Start$1, Alignment.Companion.Top, composer3, 0);
                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                            ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl5.currentCompositionLocalScope();
                                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, modifierAnimateEnterExit);
                                            ComposeUiNode.Companion.getClass();
                                            Function0 function03 = ComposeUiNode.Companion.Constructor;
                                            if (composerImpl5.applier == null) {
                                                ComposablesKt.invalidApplier();
                                                throw null;
                                            }
                                            composerImpl5.startReusableNode();
                                            if (composerImpl5.inserting) {
                                                composerImpl5.createNode(function03);
                                            } else {
                                                composerImpl5.useNode();
                                            }
                                            Updater.m337setimpl(composer3, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                            Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                            if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl5, currentCompositeKeyHash, function2);
                                            }
                                            Updater.m337setimpl(composer3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                                            Icons.Outlined outlined = Icons.Outlined.INSTANCE;
                                            ImageVector widgets = WidgetsKt.getWidgets();
                                            ColorScheme colorScheme2 = colorScheme;
                                            long j = colorScheme2.onSecondary;
                                            Dp.Companion companion4 = Dp.Companion;
                                            IconKt.m271Iconww6aTOc(widgets, (String) null, SizeKt.m140size3ABfNKs(companion3, 20), j, composer3, 432, 0);
                                            SpacerKt.Spacer(composer3, SizeKt.m140size3ABfNKs(companion3, 8));
                                            String strStringResource = StringResources_androidKt.stringResource(R.string.button_to_configure_widgets_text, composer3);
                                            MaterialTheme.INSTANCE.getClass();
                                            TextKt.m317Text4IGK_g(strStringResource, null, colorScheme2.onSecondary, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer3).titleSmall, composer3, 0, 0, 65530);
                                            composerImpl5.end(true);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl3), composerImpl3, 805306368, 508);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, (i2 & 896) | 24630, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.section.CommunalPopupSection$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Throwable {
                    ((Integer) obj2).intValue();
                    this.f$0.ButtonToEditWidgets(animatedVisibilityScope, function0, function02, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void Popup(final int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2073420952);
        int i2 = (composerImpl.changedInstance(this) ? 4 : 2) | i;
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.CommunalPopupSection.Popup (CommunalPopupSection.kt:70)");
            }
            CommunalViewModel communalViewModel = this.viewModel;
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.currentPopup, null, composerImpl, 48);
            composerImpl.startReplaceGroup(-134306061);
            if (Intrinsics.areEqual((PopupType) mutableStateCollectAsStateWithLifecycle.getValue(), PopupType.CtaTile.INSTANCE)) {
                composerImpl.startReplaceGroup(-134303747);
                boolean zChangedInstance = composerImpl.changedInstance(communalViewModel);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new CommunalPopupSection$Popup$1$1(communalViewModel);
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    composerImpl.end(false);
                    PopupOnDismissCtaTile((Function0) ((KFunction) objRememberedValue), composerImpl, (i2 << 3) & 112);
                }
            }
            composerImpl.end(false);
            AnimatedVisibilityKt.AnimatedVisibility(Intrinsics.areEqual((PopupType) mutableStateCollectAsStateWithLifecycle.getValue(), PopupType.CustomizeWidgetButton.INSTANCE), SizeKt.fillMaxSize(Modifier.Companion, 1.0f), null, null, null, ComposableLambdaKt.rememberComposableLambda(-1233446720, new Function3() { // from class: com.android.systemui.communal.ui.compose.section.CommunalPopupSection.Popup.2
                /* JADX WARN: Removed duplicated region for block: B:14:0x005a  */
                /* JADX WARN: Removed duplicated region for block: B:9:0x0034  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) throws Throwable {
                    AnimatedVisibilityScope animatedVisibilityScope = (AnimatedVisibilityScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.CommunalPopupSection.Popup.<anonymous> (CommunalPopupSection.kt:81)");
                    }
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    composerImpl2.startReplaceGroup(-267202760);
                    final CommunalPopupSection communalPopupSection = CommunalPopupSection.this;
                    boolean zChangedInstance2 = composerImpl2.changedInstance(communalPopupSection);
                    Object objRememberedValue2 = composerImpl2.rememberedValue();
                    Composer.Companion companion = Composer.Companion;
                    if (!zChangedInstance2) {
                        companion.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            final int i3 = 0;
                            objRememberedValue2 = new Function0() { // from class: com.android.systemui.communal.ui.compose.section.CommunalPopupSection$Popup$2$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() throws Resources.NotFoundException {
                                    switch (i3) {
                                        case 0:
                                            CommunalPopupSection communalPopupSection2 = communalPopupSection;
                                            communalPopupSection2.viewModel.setCurrentPopupType(null);
                                            communalPopupSection2.viewModel.onOpenWidgetEditor(false);
                                            break;
                                        default:
                                            CommunalPopupSection communalPopupSection3 = communalPopupSection;
                                            communalPopupSection3.viewModel.setCurrentPopupType(null);
                                            communalPopupSection3.viewModel.setSelectedKey(null);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue2);
                        }
                    }
                    Function0 function0 = (Function0) objRememberedValue2;
                    composerImpl2.end(false);
                    composerImpl2.startReplaceGroup(-267197928);
                    boolean zChangedInstance3 = composerImpl2.changedInstance(communalPopupSection);
                    Object objRememberedValue3 = composerImpl2.rememberedValue();
                    if (!zChangedInstance3) {
                        companion.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            final int i4 = 1;
                            objRememberedValue3 = new Function0() { // from class: com.android.systemui.communal.ui.compose.section.CommunalPopupSection$Popup$2$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() throws Resources.NotFoundException {
                                    switch (i4) {
                                        case 0:
                                            CommunalPopupSection communalPopupSection2 = communalPopupSection;
                                            communalPopupSection2.viewModel.setCurrentPopupType(null);
                                            communalPopupSection2.viewModel.onOpenWidgetEditor(false);
                                            break;
                                        default:
                                            CommunalPopupSection communalPopupSection3 = communalPopupSection;
                                            communalPopupSection3.viewModel.setCurrentPopupType(null);
                                            communalPopupSection3.viewModel.setSelectedKey(null);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue3);
                        }
                    }
                    composerImpl2.end(false);
                    CommunalPopupSection.this.ButtonToEditWidgets(animatedVisibilityScope, function0, (Function0) objRememberedValue3, composerImpl2, iIntValue & 14);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 196656, 28);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(i) { // from class: com.android.systemui.communal.ui.compose.section.CommunalPopupSection$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    this.f$0.Popup(iUpdateChangedFlags, (Composer) obj);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public final void PopupOnDismissCtaTile(Function0 function0, Composer composer, final int i) throws Throwable {
        int i2;
        final Function0 function02;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1734221839);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function02 = function0;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.CommunalPopupSection.PopupOnDismissCtaTile (CommunalPopupSection.kt:177)");
            }
            Alignment.Companion.getClass();
            IntOffset.Companion companion = IntOffset.Companion;
            ComposableSingletons$CommunalPopupSectionKt.INSTANCE.getClass();
            function02 = function0;
            AndroidPopup_androidKt.m887PopupK5zGePQ(Alignment.Companion.TopCenter, (0 << 32) | (40 & 4294967295L), function02, null, ComposableSingletons$CommunalPopupSectionKt.f38lambda1, composerImpl, ((i2 << 6) & 896) | 24630, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.section.CommunalPopupSection$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Throwable {
                    ((Integer) obj2).intValue();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    this.f$0.PopupOnDismissCtaTile(function02, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
