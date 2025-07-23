package com.android.systemui.communal.ui.compose.section;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
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
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalPopupSection {
    public final CommunalViewModel viewModel;

    public CommunalPopupSection(CommunalViewModel communalViewModel) {
        this.viewModel = communalViewModel;
    }

    public final void ButtonToEditWidgets(final AnimatedVisibilityScope animatedVisibilityScope, final Function0 function0, final Function0 function02, Composer composer, final int i) {
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
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1114910448);
            if (m == composer$Companion$Empty$1) {
                m = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl);
            }
            final FocusRequester focusRequester = (FocusRequester) m;
            composerImpl.end(false);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(1114912503);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new CommunalPopupSection$ButtonToEditWidgets$1$1(focusRequester, null);
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) rememberedValue2);
            Alignment.Companion.getClass();
            IntOffset.Companion companion = IntOffset.Companion;
            AndroidPopup_androidKt.m885PopupK5zGePQ(Alignment.Companion.TopCenter, (0 << 32) | (40 & 4294967295L), function02, null, ComposableLambdaKt.rememberComposableLambda(-1145349246, new Function2() { // from class: com.android.systemui.communal.ui.compose.section.CommunalPopupSection$ButtonToEditWidgets$2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.CommunalPopupSection.ButtonToEditWidgets.<anonymous> (CommunalPopupSection.kt:113)");
                    }
                    MaterialTheme.INSTANCE.getClass();
                    final ColorScheme colorScheme = MaterialTheme.getColorScheme(composer2);
                    Dp.Companion companion2 = Dp.Companion;
                    Modifier focusable$default = FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(SizeKt.m130height3ABfNKs(Modifier.Companion, 56), focusRequester), false, mutableInteractionSource, 1);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    composerImpl3.startReplaceGroup(-1843743873);
                    Object rememberedValue3 = composerImpl3.rememberedValue();
                    Composer.Companion.getClass();
                    if (rememberedValue3 == Composer.Companion.Empty) {
                        rememberedValue3 = new CommunalPopupSection$ButtonToEditWidgets$2$$ExternalSyntheticLambda0();
                        composerImpl3.updateRememberedValue(rememberedValue3);
                    }
                    composerImpl3.end(false);
                    Modifier graphicsLayer = GraphicsLayerModifierKt.graphicsLayer(focusable$default, (Function1) rememberedValue3);
                    EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda0 = EasingKt.LinearEasing;
                    EnterTransition fadeIn = EnterExitTransitionKt.fadeIn(AnimationSpecKt.tween$default(83, 0, easingKt$$ExternalSyntheticLambda0, 2));
                    ExitTransition fadeOut$default = EnterExitTransitionKt.fadeOut$default(new TweenSpec(83, 167, easingKt$$ExternalSyntheticLambda0), 2);
                    final AnimatedVisibilityScope animatedVisibilityScope2 = AnimatedVisibilityScope.this;
                    ButtonKt.Button(function0, BackgroundKt.m26backgroundbw27NRU(animatedVisibilityScope2.animateEnterExit(graphicsLayer, fadeIn, fadeOut$default), colorScheme.secondary, RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(50)), false, null, null, null, null, null, null, ComposableLambdaKt.rememberComposableLambda(1804176786, new Function3() { // from class: com.android.systemui.communal.ui.compose.section.CommunalPopupSection$ButtonToEditWidgets$2.2
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj3, Object obj4, Object obj5) {
                            Composer composer3 = (Composer) obj4;
                            if ((((Number) obj5).intValue() & 17) == 16) {
                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                if (composerImpl4.getSkipping()) {
                                    composerImpl4.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.CommunalPopupSection.ButtonToEditWidgets.<anonymous>.<anonymous> (CommunalPopupSection.kt:140)");
                            }
                            Modifier.Companion companion3 = Modifier.Companion;
                            EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda02 = EasingKt.LinearEasing;
                            Modifier animateEnterExit = AnimatedVisibilityScope.this.animateEnterExit(companion3, EnterExitTransitionKt.fadeIn$default(new TweenSpec(167, 83, easingKt$$ExternalSyntheticLambda02), 2), EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(167, 0, easingKt$$ExternalSyntheticLambda02, 2), 2));
                            Arrangement.INSTANCE.getClass();
                            Arrangement$Start$1 arrangement$Start$1 = Arrangement.Start;
                            Alignment.Companion.getClass();
                            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Start$1, Alignment.Companion.Top, composer3, 0);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                            ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl5.currentCompositionLocalScope();
                            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer3, animateEnterExit);
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
                            Updater.m336setimpl(composer3, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m336setimpl(composer3, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl5, currentCompositeKeyHash, function2);
                            }
                            Updater.m336setimpl(composer3, materializeModifier, ComposeUiNode.Companion.SetModifier);
                            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                            Icons.Outlined outlined = Icons.Outlined.INSTANCE;
                            ImageVector widgets = WidgetsKt.getWidgets();
                            ColorScheme colorScheme2 = colorScheme;
                            long j = colorScheme2.onSecondary;
                            Dp.Companion companion4 = Dp.Companion;
                            IconKt.m270Iconww6aTOc(widgets, (String) null, SizeKt.m139size3ABfNKs(companion3, 20), j, composer3, 432, 0);
                            SpacerKt.Spacer(composer3, SizeKt.m139size3ABfNKs(companion3, 8));
                            String stringResource = StringResources_androidKt.stringResource(R.string.button_to_configure_widgets_text, composer3);
                            MaterialTheme.INSTANCE.getClass();
                            TextKt.m316Text4IGK_g(stringResource, null, colorScheme2.onSecondary, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer3).titleSmall, composer3, 0, 0, 65530);
                            composerImpl5.end(true);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl3), composerImpl3, 805306368, 508);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, (i2 & 896) | 24630, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.section.CommunalPopupSection$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    CommunalPopupSection.this.ButtonToEditWidgets(animatedVisibilityScope, function0, function02, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0066, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void Popup(final int r10, androidx.compose.runtime.Composer r11) {
        /*
            r9 = this;
            r6 = r11
            androidx.compose.runtime.ComposerImpl r6 = (androidx.compose.runtime.ComposerImpl) r6
            r11 = 2073420952(0x7b95e498, float:1.5565773E36)
            r6.startRestartGroup(r11)
            boolean r11 = r6.changedInstance(r9)
            r0 = 2
            if (r11 == 0) goto L12
            r11 = 4
            goto L13
        L12:
            r11 = r0
        L13:
            r11 = r11 | r10
            r1 = r11 & 3
            if (r1 != r0) goto L24
            boolean r0 = r6.getSkipping()
            if (r0 != 0) goto L1f
            goto L24
        L1f:
            r6.skipToGroupEnd()
            goto Lb5
        L24:
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L2f
            java.lang.String r0 = "com.android.systemui.communal.ui.compose.section.CommunalPopupSection.Popup (CommunalPopupSection.kt:70)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L2f:
            com.android.systemui.communal.ui.viewmodel.CommunalViewModel r0 = r9.viewModel
            kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r0.currentPopup
            r2 = 48
            r3 = 0
            androidx.compose.runtime.MutableState r1 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r1, r3, r6, r2)
            r2 = -134306061(0xfffffffff7fea6f3, float:-1.0329918E34)
            r6.startReplaceGroup(r2)
            java.lang.Object r2 = r1.getValue()
            com.android.systemui.communal.ui.viewmodel.PopupType r2 = (com.android.systemui.communal.ui.viewmodel.PopupType) r2
            com.android.systemui.communal.ui.viewmodel.PopupType$CtaTile r3 = com.android.systemui.communal.ui.viewmodel.PopupType.CtaTile.INSTANCE
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
            r3 = 0
            if (r2 == 0) goto L7e
            r2 = -134303747(0xfffffffff7feaffd, float:-1.03313505E34)
            r6.startReplaceGroup(r2)
            boolean r2 = r6.changedInstance(r0)
            java.lang.Object r4 = r6.rememberedValue()
            if (r2 != 0) goto L68
            androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r4 != r2) goto L70
        L68:
            com.android.systemui.communal.ui.compose.section.CommunalPopupSection$Popup$1$1 r4 = new com.android.systemui.communal.ui.compose.section.CommunalPopupSection$Popup$1$1
            r4.<init>(r0)
            r6.updateRememberedValue(r4)
        L70:
            kotlin.reflect.KFunction r4 = (kotlin.reflect.KFunction) r4
            r6.end(r3)
            kotlin.jvm.functions.Function0 r4 = (kotlin.jvm.functions.Function0) r4
            int r11 = r11 << 3
            r11 = r11 & 112(0x70, float:1.57E-43)
            r9.PopupOnDismissCtaTile(r4, r6, r11)
        L7e:
            r6.end(r3)
            java.lang.Object r11 = r1.getValue()
            com.android.systemui.communal.ui.viewmodel.PopupType r11 = (com.android.systemui.communal.ui.viewmodel.PopupType) r11
            com.android.systemui.communal.ui.viewmodel.PopupType$CustomizeWidgetButton r0 = com.android.systemui.communal.ui.viewmodel.PopupType.CustomizeWidgetButton.INSTANCE
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r11, r0)
            androidx.compose.ui.Modifier$Companion r11 = androidx.compose.ui.Modifier.Companion
            r1 = 1065353216(0x3f800000, float:1.0)
            androidx.compose.ui.Modifier r1 = androidx.compose.foundation.layout.SizeKt.fillMaxSize(r11, r1)
            com.android.systemui.communal.ui.compose.section.CommunalPopupSection$Popup$2 r11 = new com.android.systemui.communal.ui.compose.section.CommunalPopupSection$Popup$2
            r11.<init>()
            r2 = -1233446720(0xffffffffb67b18c0, float:-3.741632E-6)
            androidx.compose.runtime.internal.ComposableLambdaImpl r5 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r2, r11, r6)
            r3 = 0
            r4 = 0
            r2 = 0
            r7 = 196656(0x30030, float:2.75574E-40)
            r8 = 28
            androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibility(r0, r1, r2, r3, r4, r5, r6, r7, r8)
            boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r11 == 0) goto Lb5
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lb5:
            androidx.compose.runtime.RecomposeScopeImpl r11 = r6.endRestartGroup()
            if (r11 == 0) goto Lc2
            com.android.systemui.communal.ui.compose.section.CommunalPopupSection$$ExternalSyntheticLambda1 r0 = new com.android.systemui.communal.ui.compose.section.CommunalPopupSection$$ExternalSyntheticLambda1
            r0.<init>(r10)
            r11.block = r0
        Lc2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.section.CommunalPopupSection.Popup(int, androidx.compose.runtime.Composer):void");
    }

    public final void PopupOnDismissCtaTile(Function0 function0, Composer composer, final int i) {
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
            AndroidPopup_androidKt.m885PopupK5zGePQ(Alignment.Companion.TopCenter, (0 << 32) | (40 & 4294967295L), function02, null, ComposableSingletons$CommunalPopupSectionKt.f38lambda1, composerImpl, ((i2 << 6) & 896) | 24630, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.section.CommunalPopupSection$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    CommunalPopupSection.this.PopupOnDismissCtaTile(function02, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
