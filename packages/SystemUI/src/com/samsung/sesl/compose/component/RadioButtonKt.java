package com.samsung.sesl.compose.component;

import android.content.Context;
import androidx.compose.animation.CrossfadeKt;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.Indication;
import androidx.compose.foundation.IndicationKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.material3.RippleKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.unit.Dp;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.foundation.BasicRadioButtonKt;
import com.samsung.sesl.compose.foundation.SeslBasicRadioButtonColors;
import com.samsung.sesl.compose.phone.resources.DrawableResourcesKt;
import com.samsung.sesl.compose.theme.SeslTheme;
import com.samsung.sesl.compose.theme.ThemeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class RadioButtonKt {
    public static final void SeslOpenThemeRadioButton(final boolean z, final Function0 function0, final Modifier modifier, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2127561461);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(true) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(mutableInteractionSource) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i2 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslOpenThemeRadioButton (RadioButton.kt:90)");
            }
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(15173364);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = DrawableResourcesKt.seslDrawablePainter(R.drawable.sesl_btn_radio_to_on_024, context);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final Painter painter = (Painter) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 15177780);
            if (m == composer$Companion$Empty$1) {
                m = DrawableResourcesKt.seslDrawablePainter(R.drawable.sesl_btn_radio_to_on_000, context);
                composerImpl.updateRememberedValue(m);
            }
            final Painter painter2 = (Painter) m;
            Object m2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 15182196);
            if (m2 == composer$Companion$Empty$1) {
                m2 = DrawableResourcesKt.seslDrawablePainter(R.drawable.sesl_btn_radio_to_on_025, context);
                composerImpl.updateRememberedValue(m2);
            }
            Object m3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 15186644);
            if (m3 == composer$Companion$Empty$1) {
                m3 = DrawableResourcesKt.seslDrawablePainter(R.drawable.sesl_btn_radio_to_on_001, context);
                composerImpl.updateRememberedValue(m3);
            }
            composerImpl.end(false);
            composerImpl.startReplaceGroup(470930333);
            boolean z2 = true;
            Boolean valueOf = Boolean.valueOf(z);
            Indication indication = (Indication) composerImpl.consume(IndicationKt.LocalIndication);
            composerImpl.startReplaceGroup(15195681);
            if ((i2 & 112) != 32) {
                z2 = false;
            }
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (z2 || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new Function0() { // from class: com.samsung.sesl.compose.component.RadioButtonKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Function0 function02 = Function0.this;
                        if (function02 != null) {
                            function02.invoke();
                        }
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            CrossfadeKt.Crossfade(valueOf, ClickableKt.m34clickableO2vRcR0$default(modifier, mutableInteractionSource, indication, true, null, null, (Function0) rememberedValue2, 24), (FiniteAnimationSpec) null, "", ComposableLambdaKt.rememberComposableLambda(-1957665189, new Function3() { // from class: com.samsung.sesl.compose.component.RadioButtonKt$SeslOpenThemeRadioButton$3
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 6) == 0) {
                        intValue |= ((ComposerImpl) composer2).changed(booleanValue) ? 4 : 2;
                    }
                    if ((intValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslOpenThemeRadioButton.<anonymous> (RadioButton.kt:117)");
                    }
                    if (booleanValue) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        composerImpl3.startReplaceGroup(-418319809);
                        ImageKt.Image(Painter.this, "selected", null, null, null, 0.0f, null, composerImpl3, 48, 124);
                        composerImpl3.end(false);
                    } else {
                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                        composerImpl4.startReplaceGroup(-418158981);
                        ImageKt.Image(painter2, "unselected", null, null, null, 0.0f, null, composerImpl4, 48, 124);
                        composerImpl4.end(false);
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, (i2 & 14) | 27648, 4);
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.RadioButtonKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Modifier modifier2 = modifier;
                    MutableInteractionSource mutableInteractionSource2 = mutableInteractionSource;
                    RadioButtonKt.SeslOpenThemeRadioButton(z, function0, modifier2, mutableInteractionSource2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SeslRadioButton(final boolean z, final Function0 function0, Modifier.Companion companion, boolean z2, MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        boolean z3;
        int i2;
        final boolean z4;
        final Modifier.Companion companion2;
        final MutableInteractionSource mutableInteractionSource2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(407538050);
        if ((i & 6) == 0) {
            z3 = z;
            i2 = i | (composerImpl.changed(z3) ? 4 : 2);
        } else {
            z3 = z;
            i2 = i;
        }
        int i3 = i2 | (composerImpl.changedInstance(function0) ? 32 : 16) | 28032;
        if ((i3 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
            z4 = z2;
            mutableInteractionSource2 = mutableInteractionSource;
        } else {
            Modifier.Companion companion3 = Modifier.Companion;
            composerImpl.startReplaceGroup(1249200442);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MutableInteractionSource mutableInteractionSource3 = (MutableInteractionSource) rememberedValue;
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslRadioButton (RadioButton.kt:53)");
            }
            if (((Boolean) composerImpl.consume(ThemeKt.LocalOneUiOpenTheme)).booleanValue()) {
                composerImpl.startReplaceGroup(70594282);
                SeslOpenThemeRadioButton(z3, function0, companion3, mutableInteractionSource3, composerImpl, 65534 & i3);
                companion3 = companion3;
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(70838686);
                SeslTheme.INSTANCE.getClass();
                SeslBasicRadioButtonColors seslBasicRadioButtonColors = new SeslBasicRadioButtonColors(SeslTheme.getColorScheme(composerImpl).primary, SeslTheme.getColorScheme(composerImpl).controlNormal, null);
                SeslRadioButtonDefaults.INSTANCE.getClass();
                float f = SeslRadioButtonDefaults.radioButtonSize;
                Dp.Companion companion4 = Dp.Companion;
                int i4 = i3 << 3;
                BasicRadioButtonKt.m3331SeslBasicRadioButtonaA_HZ9I(seslBasicRadioButtonColors, z, function0, f, RippleKt.m280rippleH2RKhps$default(f / 2, false, 4), companion3, mutableInteractionSource3, composerImpl, (i4 & 896) | (i4 & 112) | 27648 | 14352384);
                composerImpl = composerImpl;
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            z4 = true;
            companion2 = companion3;
            mutableInteractionSource2 = mutableInteractionSource3;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.RadioButtonKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    boolean z5 = z4;
                    MutableInteractionSource mutableInteractionSource4 = mutableInteractionSource2;
                    RadioButtonKt.SeslRadioButton(z, function0, companion2, z5, mutableInteractionSource4, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
