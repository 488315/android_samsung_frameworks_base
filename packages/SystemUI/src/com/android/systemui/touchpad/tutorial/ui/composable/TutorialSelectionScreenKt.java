package com.android.systemui.touchpad.tutorial.ui.composable;

import android.content.res.Configuration;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.Arrangement$Start$1;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.layout.WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.CoreTextFieldKt$$ExternalSyntheticOutline0;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.automirrored.outlined.ArrowBackKt;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
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
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.res.VectorResources_androidKt;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.unit.Dp;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialComponentsKt;
import com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperUtilsKt;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.Screen;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class TutorialSelectionScreenKt {
    public static final void FourTutorialButtons(Function0 function0, Function0 function02, Function0 function03, Function0 function04, Screen screen, Modifier modifier, Composer composer, int i) {
        int i2;
        Function0 function05;
        Function0 function06;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-566679518);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changedInstance(function02) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            function05 = function03;
            i2 |= composerImpl2.changedInstance(function05) ? 256 : 128;
        } else {
            function05 = function03;
        }
        if ((i & 3072) == 0) {
            function06 = function04;
            i2 |= composerImpl2.changedInstance(function06) ? 2048 : 1024;
        } else {
            function06 = function04;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(screen) ? 16384 : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.FourTutorialButtons (TutorialSelectionScreen.kt:274)");
            }
            composerImpl2.startReplaceGroup(-1749529378);
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl2);
            }
            FocusRequester focusRequester = (FocusRequester) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -1749527490);
            if (m == composer$Companion$Empty$1) {
                m = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl2);
            }
            FocusRequester focusRequester2 = (FocusRequester) m;
            Object m2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -1749525410);
            if (m2 == composer$Companion$Empty$1) {
                m2 = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl2);
            }
            FocusRequester focusRequester3 = (FocusRequester) m2;
            Object m3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -1749523330);
            if (m3 == composer$Companion$Empty$1) {
                m3 = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl2);
            }
            FocusRequester focusRequester4 = (FocusRequester) m3;
            composerImpl2.end(false);
            Unit unit = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(-1749521211);
            boolean z = (57344 & i2) == 16384;
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (z || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new TutorialSelectionScreenKt$FourTutorialButtons$1$1(screen, focusRequester, focusRequester2, focusRequester3, focusRequester4, null);
                composerImpl2.updateRememberedValue(rememberedValue2);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, unit, (Function2) rememberedValue2);
            String stringResource = StringResources_androidKt.stringResource(R.string.touchpad_tutorial_home_gesture_button, composerImpl2);
            ImageVector.Companion companion = ImageVector.Companion;
            ImageVector vectorResource = VectorResources_androidKt.vectorResource(R.drawable.touchpad_tutorial_home_icon, composerImpl2);
            MaterialTheme.INSTANCE.getClass();
            m3108TutorialButtonD_bmR2Q(stringResource, vectorResource, MaterialTheme.getColorScheme(composerImpl2).onPrimary, function02, MaterialTheme.getColorScheme(composerImpl2).primary, FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(modifier, focusRequester), false, null, 3), composerImpl2, (i2 << 6) & 7168);
            String stringResource2 = StringResources_androidKt.stringResource(R.string.touchpad_tutorial_back_gesture_button, composerImpl2);
            Icons.AutoMirrored.Outlined outlined = Icons.AutoMirrored.Outlined.INSTANCE;
            m3108TutorialButtonD_bmR2Q(stringResource2, ArrowBackKt.getArrowBack(), MaterialTheme.getColorScheme(composerImpl2).onTertiary, function0, MaterialTheme.getColorScheme(composerImpl2).tertiary, FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(modifier, focusRequester2), false, null, 3), composerImpl2, (i2 << 9) & 7168);
            m3108TutorialButtonD_bmR2Q(StringResources_androidKt.stringResource(R.string.touchpad_tutorial_recent_apps_gesture_button, composerImpl2), VectorResources_androidKt.vectorResource(R.drawable.touchpad_tutorial_recents_icon, composerImpl2), MaterialTheme.getColorScheme(composerImpl2).onSecondary, function05, MaterialTheme.getColorScheme(composerImpl2).secondary, FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(modifier, focusRequester3), false, null, 3), composerImpl2, (i2 << 3) & 7168);
            composerImpl = composerImpl2;
            m3108TutorialButtonD_bmR2Q(StringResources_androidKt.stringResource(R.string.touchpad_tutorial_switch_apps_gesture_button, composerImpl2), VectorResources_androidKt.vectorResource(R.drawable.touchpad_tutorial_apps_icon, composerImpl2), MaterialTheme.getColorScheme(composerImpl2).primary, function06, MaterialTheme.getColorScheme(composerImpl2).onPrimary, FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(modifier, focusRequester4), false, null, 3), composerImpl, i2 & 7168);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new TutorialSelectionScreenKt$$ExternalSyntheticLambda1(function0, function02, function03, function04, screen, modifier, i, 3);
        }
    }

    public static final void HorizontalCompactSelectionButtons(Function0 function0, Function0 function02, Function0 function03, Function0 function04, Screen screen, Modifier modifier, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1003266553);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function03) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function04) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(screen) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(modifier) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.HorizontalCompactSelectionButtons (TutorialSelectionScreen.kt:224)");
            }
            Arrangement arrangement = Arrangement.INSTANCE;
            Dp.Companion companion = Dp.Companion;
            arrangement.getClass();
            Arrangement.SpacedAligned m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(16);
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(m91spacedBy0680j_4, Alignment.Companion.CenterVertically, composerImpl, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
            ComposeUiNode.Companion.getClass();
            Function0 function05 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function05);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            FourTutorialButtons(function0, function02, function03, function04, screen, SizeKt.fillMaxSize(RowScopeInstance.INSTANCE.weight(Modifier.Companion, 1.0f, true), 1.0f), composerImpl, i2 & 65534);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new TutorialSelectionScreenKt$$ExternalSyntheticLambda1(function0, function02, function03, function04, screen, modifier, i, 0);
        }
    }

    public static final void HorizontalSelectionButtons(Function0 function0, Function0 function02, Function0 function03, Function0 function04, Screen screen, Modifier modifier, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-253169382);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function03) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function04) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(screen) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(modifier) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.HorizontalSelectionButtons (TutorialSelectionScreen.kt:136)");
            }
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
            ComposeUiNode.Companion.getClass();
            Function0 function05 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function05);
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
            TwoByTwoTutorialButtons(function0, function02, function03, function04, screen, SizeKt.fillMaxSize(ColumnScopeInstance.INSTANCE.weight(Modifier.Companion, 1.0f, true), 1.0f), composerImpl, i2 & 65534);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new TutorialSelectionScreenKt$$ExternalSyntheticLambda1(function0, function02, function03, function04, screen, modifier, i, 2);
        }
    }

    /* renamed from: TutorialButton-D_bmR2Q, reason: not valid java name */
    public static final void m3108TutorialButtonD_bmR2Q(final String str, final ImageVector imageVector, final long j, final Function0 function0, final long j2, final Modifier modifier, Composer composer, final int i) {
        int i2;
        Function0 function02;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(7579216);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(imageVector) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(j) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            function02 = function0;
            i2 |= composerImpl.changedInstance(function02) ? 2048 : 1024;
        } else {
            function02 = function0;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(j2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(modifier) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.TutorialButton (TutorialSelectionScreen.kt:330)");
            }
            Dp.Companion companion = Dp.Companion;
            RoundedCornerShape m186RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(16);
            ButtonDefaults.INSTANCE.getClass();
            ButtonKt.Button(function02, modifier, false, m186RoundedCornerShape0680j_4, ButtonDefaults.m251buttonColorsro_MJ88(j2, 0L, composerImpl, 14), null, null, null, null, ComposableLambdaKt.rememberComposableLambda(1643154528, new Function3() { // from class: com.android.systemui.touchpad.tutorial.ui.composable.TutorialSelectionScreenKt$TutorialButton$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.TutorialButton.<anonymous> (TutorialSelectionScreen.kt:337)");
                    }
                    Arrangement.INSTANCE.getClass();
                    Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
                    Alignment.Companion.getClass();
                    BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                    Modifier.Companion companion2 = Modifier.Companion;
                    ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, horizontal, composer2, 54);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, companion2);
                    ComposeUiNode.Companion.getClass();
                    Function0 function03 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl3.applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl3.startReusableNode();
                    if (composerImpl3.inserting) {
                        composerImpl3.createNode(function03);
                    } else {
                        composerImpl3.useNode();
                    }
                    Updater.m336setimpl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m336setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                    }
                    Updater.m336setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                    float f = 30;
                    Dp.Companion companion3 = Dp.Companion;
                    Modifier m130height3ABfNKs = SizeKt.m130height3ABfNKs(SizeKt.m143width3ABfNKs(companion2, f), f);
                    ImageVector imageVector2 = ImageVector.this;
                    long j3 = j;
                    IconKt.m270Iconww6aTOc(imageVector2, (String) null, m130height3ABfNKs, j3, composer2, 432, 0);
                    composerImpl3.startReplaceGroup(-1272083664);
                    if (!ShortcutHelperUtilsKt.hasCompactWindowSize(composer2)) {
                        SpacerKt.Spacer(composer2, SizeKt.m130height3ABfNKs(companion2, 16));
                    }
                    composerImpl3.end(false);
                    TextAlign.Companion.getClass();
                    int i3 = TextAlign.Center;
                    MaterialTheme.INSTANCE.getClass();
                    TextKt.m316Text4IGK_g(str, null, j3, 0L, null, null, null, 0L, null, TextAlign.m805boximpl(i3), 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer2).headlineLarge, composer2, 0, 0, 65018);
                    composerImpl3.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, ((i2 >> 9) & 14) | 805306368 | ((i2 >> 12) & 112), VolteConstants.ErrorCode.ADDRESS_INCOMPLETE);
            composerImpl = composerImpl;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.composable.TutorialSelectionScreenKt$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    long j3 = j2;
                    Modifier modifier2 = modifier;
                    TutorialSelectionScreenKt.m3108TutorialButtonD_bmR2Q(str, imageVector, j, function0, j3, modifier2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void TutorialSelectionScreen(Function0 function0, Function0 function02, Function0 function03, Function0 function04, Function0 function05, Screen screen, Composer composer, int i) {
        int i2;
        Modifier.Companion companion;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(102176832);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function03) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function04) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changedInstance(function05) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(screen) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.TutorialSelectionScreen (TutorialSelectionScreen.kt:69)");
            }
            Arrangement.INSTANCE.getClass();
            Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
            Modifier.Companion companion2 = Modifier.Companion;
            MaterialTheme.INSTANCE.getClass();
            Modifier composed = ComposedModifierKt.composed(SizeKt.fillMaxSize(BackgroundKt.m26backgroundbw27NRU(companion2, MaterialTheme.getColorScheme(composerImpl).surfaceContainer, RectangleShapeKt.RectangleShape), 1.0f), InspectableValueKt.NoInspectorInfo, new WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1());
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, composed);
            ComposeUiNode.Companion.getClass();
            Function0 function06 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function06);
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
            boolean hasCompactWindowSize = ShortcutHelperUtilsKt.hasCompactWindowSize(composerImpl);
            float f = hasCompactWindowSize ? 24 : 48;
            Dp.Companion companion3 = Dp.Companion;
            if (((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).orientation == 2) {
                composerImpl.startReplaceGroup(837875447);
                if (hasCompactWindowSize) {
                    composerImpl.startReplaceGroup(-1219896450);
                    companion = companion2;
                    HorizontalCompactSelectionButtons(function0, function02, function03, function04, screen, PaddingKt.m124padding3ABfNKs(columnScopeInstance.weight(companion2, 1.0f, true), f), composerImpl, (i2 & 8190) | ((i2 >> 3) & 57344));
                    composerImpl.end(false);
                } else {
                    companion = companion2;
                    composerImpl.startReplaceGroup(-1219879721);
                    HorizontalSelectionButtons(function0, function02, function03, function04, screen, PaddingKt.m124padding3ABfNKs(columnScopeInstance.weight(companion, 1.0f, true), f), composerImpl, (i2 & 8190) | ((i2 >> 3) & 57344));
                    composerImpl.end(false);
                }
                composerImpl.end(false);
            } else {
                companion = companion2;
                composerImpl.startReplaceGroup(838939305);
                VerticalSelectionButtons(function0, function02, function03, function04, screen, PaddingKt.m124padding3ABfNKs(columnScopeInstance.weight(companion, 1.0f, true), f), composerImpl, (i2 & 8190) | ((i2 >> 3) & 57344));
                composerImpl.end(false);
            }
            TutorialComponentsKt.DoneButton(function05, PaddingKt.m127paddingqDBjuR0(companion, f, 0, f, 32), false, false, composerImpl, (i2 >> 12) & 14, 12);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new TutorialSelectionScreenKt$$ExternalSyntheticLambda1(function0, function02, function03, function04, function05, screen, i);
        }
    }

    public static final void TwoByTwoTutorialButtons(Function0 function0, Function0 function02, Function0 function03, Function0 function04, Screen screen, Modifier modifier, Composer composer, int i) {
        int i2;
        Unit unit;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(778581663);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changedInstance(function02) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changedInstance(function03) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changedInstance(function04) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(screen) ? 16384 : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.TwoByTwoTutorialButtons (TutorialSelectionScreen.kt:157)");
            }
            composerImpl2.startReplaceGroup(-816706373);
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl2);
            }
            FocusRequester focusRequester = (FocusRequester) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -816704485);
            if (m == composer$Companion$Empty$1) {
                m = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl2);
            }
            FocusRequester focusRequester2 = (FocusRequester) m;
            Object m2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -816702405);
            if (m2 == composer$Companion$Empty$1) {
                m2 = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl2);
            }
            FocusRequester focusRequester3 = (FocusRequester) m2;
            Object m3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -816700325);
            if (m3 == composer$Companion$Empty$1) {
                m3 = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl2);
            }
            FocusRequester focusRequester4 = (FocusRequester) m3;
            composerImpl2.end(false);
            Unit unit2 = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(-816698206);
            boolean z = (57344 & i2) == 16384;
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (z || rememberedValue2 == composer$Companion$Empty$1) {
                unit = unit2;
                TutorialSelectionScreenKt$TwoByTwoTutorialButtons$1$1 tutorialSelectionScreenKt$TwoByTwoTutorialButtons$1$1 = new TutorialSelectionScreenKt$TwoByTwoTutorialButtons$1$1(screen, focusRequester, focusRequester2, focusRequester3, focusRequester4, null);
                composerImpl2.updateRememberedValue(tutorialSelectionScreenKt$TwoByTwoTutorialButtons$1$1);
                rememberedValue2 = tutorialSelectionScreenKt$TwoByTwoTutorialButtons$1$1;
            } else {
                unit = unit2;
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, unit, (Function2) rememberedValue2);
            Modifier.Companion companion = Modifier.Companion;
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl2, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, companion);
            ComposeUiNode.Companion.getClass();
            Function0 function05 = ComposeUiNode.Companion.Constructor;
            if (composerImpl2.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function05);
            } else {
                composerImpl2.useNode();
            }
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m336setimpl(composerImpl2, columnMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl2, currentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl2, materializeModifier, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            Modifier weight = columnScopeInstance.weight(companion, 1.0f, true);
            Arrangement$Start$1 arrangement$Start$1 = Arrangement.Start;
            BiasAlignment.Vertical vertical = Alignment.Companion.Top;
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Start$1, vertical, composerImpl2, 0);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl2, weight);
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function05);
            } else {
                composerImpl2.useNode();
            }
            Updater.m336setimpl(composerImpl2, rowMeasurePolicy, function2);
            Updater.m336setimpl(composerImpl2, currentCompositionLocalScope2, function22);
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function23);
            }
            Updater.m336setimpl(composerImpl2, materializeModifier2, function24);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            String stringResource = StringResources_androidKt.stringResource(R.string.touchpad_tutorial_home_gesture_button, composerImpl2);
            ImageVector.Companion companion2 = ImageVector.Companion;
            ImageVector vectorResource = VectorResources_androidKt.vectorResource(R.drawable.touchpad_tutorial_home_icon, composerImpl2);
            MaterialTheme.INSTANCE.getClass();
            m3108TutorialButtonD_bmR2Q(stringResource, vectorResource, MaterialTheme.getColorScheme(composerImpl2).onPrimary, function02, MaterialTheme.getColorScheme(composerImpl2).primary, SizeKt.fillMaxSize(FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(modifier, focusRequester), false, null, 3), 1.0f), composerImpl2, (i2 << 6) & 7168);
            float f = 16;
            Dp.Companion companion3 = Dp.Companion;
            SpacerKt.Spacer(composerImpl2, SizeKt.m139size3ABfNKs(companion, f));
            String stringResource2 = StringResources_androidKt.stringResource(R.string.touchpad_tutorial_back_gesture_button, composerImpl2);
            Icons.AutoMirrored.Outlined outlined = Icons.AutoMirrored.Outlined.INSTANCE;
            m3108TutorialButtonD_bmR2Q(stringResource2, ArrowBackKt.getArrowBack(), MaterialTheme.getColorScheme(composerImpl2).onTertiary, function0, MaterialTheme.getColorScheme(composerImpl2).tertiary, SizeKt.fillMaxSize(FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(modifier, focusRequester2), false, null, 3), 1.0f), composerImpl2, (i2 << 9) & 7168);
            composerImpl2.end(true);
            SpacerKt.Spacer(composerImpl2, SizeKt.m139size3ABfNKs(companion, f));
            Modifier weight2 = columnScopeInstance.weight(companion, 1.0f, true);
            RowMeasurePolicy rowMeasurePolicy2 = RowKt.rowMeasurePolicy(arrangement$Start$1, vertical, composerImpl2, 0);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl2, weight2);
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function05);
            } else {
                composerImpl2.useNode();
            }
            Updater.m336setimpl(composerImpl2, rowMeasurePolicy2, function2);
            Updater.m336setimpl(composerImpl2, currentCompositionLocalScope3, function22);
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl2, currentCompositeKeyHash3, function23);
            }
            Updater.m336setimpl(composerImpl2, materializeModifier3, function24);
            m3108TutorialButtonD_bmR2Q(StringResources_androidKt.stringResource(R.string.touchpad_tutorial_recent_apps_gesture_button, composerImpl2), VectorResources_androidKt.vectorResource(R.drawable.touchpad_tutorial_recents_icon, composerImpl2), MaterialTheme.getColorScheme(composerImpl2).onSecondary, function03, MaterialTheme.getColorScheme(composerImpl2).secondary, SizeKt.fillMaxSize(FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(modifier, focusRequester3), false, null, 3), 1.0f), composerImpl2, (i2 << 3) & 7168);
            SpacerKt.Spacer(composerImpl2, SizeKt.m139size3ABfNKs(companion, f));
            m3108TutorialButtonD_bmR2Q(StringResources_androidKt.stringResource(R.string.touchpad_tutorial_switch_apps_gesture_button, composerImpl2), VectorResources_androidKt.vectorResource(R.drawable.touchpad_tutorial_apps_icon, composerImpl2), MaterialTheme.getColorScheme(composerImpl2).primary, function04, MaterialTheme.getColorScheme(composerImpl2).onPrimary, SizeKt.fillMaxSize(FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(modifier, focusRequester4), false, null, 3), 1.0f), composerImpl2, i2 & 7168);
            composerImpl = composerImpl2;
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, true, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new TutorialSelectionScreenKt$$ExternalSyntheticLambda1(function0, function02, function03, function04, screen, modifier, i, 4);
        }
    }

    public static final void VerticalSelectionButtons(Function0 function0, Function0 function02, Function0 function03, Function0 function04, Screen screen, Modifier modifier, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(97284716);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function03) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function04) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(screen) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(modifier) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.VerticalSelectionButtons (TutorialSelectionScreen.kt:249)");
            }
            Arrangement arrangement = Arrangement.INSTANCE;
            Dp.Companion companion = Dp.Companion;
            arrangement.getClass();
            Arrangement.SpacedAligned m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(16);
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m91spacedBy0680j_4, Alignment.Companion.CenterHorizontally, composerImpl, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
            ComposeUiNode.Companion.getClass();
            Function0 function05 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function05);
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
            FourTutorialButtons(function0, function02, function03, function04, screen, SizeKt.fillMaxSize(ColumnScopeInstance.INSTANCE.weight(Modifier.Companion, 1.0f, true), 1.0f), composerImpl, i2 & 65534);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new TutorialSelectionScreenKt$$ExternalSyntheticLambda1(function0, function02, function03, function04, screen, modifier, i, 1);
        }
    }
}
