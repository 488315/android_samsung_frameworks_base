package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SliderColors;
import androidx.compose.material3.SliderKt;
import androidx.compose.material3.SliderState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.res.ColorResources_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Dp;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.wrapper.SecHapticFeedbackType;
import com.android.systemui.media.mediaoutput.wrapper.SecPlatformHapticFeedback;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.component.CheckboxKt;
import com.samsung.sesl.compose.component.tokens.SeslSliderColorSchemeKeyTokens;
import com.samsung.sesl.compose.foundation.theme.BasicColorSchemeKt;
import com.samsung.sesl.compose.theme.SeslColorScheme;
import com.samsung.sesl.compose.theme.SeslTheme;
import com.samsung.sesl.compose.theme.ThemeKt;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.ClosedFloatingPointRange;

/* loaded from: classes2.dex */
public abstract class ControllersKt {
    public static final void MoCheckbox(final boolean z, final Function1 function1, final Modifier modifier, final boolean z2, MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        final MutableInteractionSource mutableInteractionSource2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1618790467);
        if (((i | (composerImpl.changed(z) ? 4 : 2) | (composerImpl.changedInstance(function1) ? 32 : 16) | (composerImpl.changed(z2) ? 2048 : 1024) | 24576) & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            mutableInteractionSource2 = mutableInteractionSource;
        } else {
            composerImpl.startReplaceGroup(1738266152);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            mutableInteractionSource2 = (MutableInteractionSource) objRememberedValue;
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.MoCheckbox (Controllers.kt:81)");
            }
            SeslTheme.INSTANCE.getClass();
            ThemeKt.SeslTheme(false, SeslColorScheme.m3358copyFD3wquc$default(SeslTheme.getColorScheme(composerImpl), ColorKt.Color(4294769919L), ColorKt.Color(4294769919L)), ComposableLambdaKt.rememberComposableLambda(1403220974, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt.MoCheckbox.2
                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
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
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.MoCheckbox.<anonymous> (Controllers.kt:88)");
                            }
                            CheckboxKt.SeslCheckbox(z, function1, modifier, z2, mutableInteractionSource2, composer2, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 384, 1);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(z, function1, modifier, z2, mutableInteractionSource2, i) { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt$$ExternalSyntheticLambda0
                public final /* synthetic */ boolean f$0;
                public final /* synthetic */ Function1 f$1;
                public final /* synthetic */ Modifier f$2;
                public final /* synthetic */ boolean f$3;
                public final /* synthetic */ MutableInteractionSource f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(385);
                    boolean z3 = this.f$3;
                    MutableInteractionSource mutableInteractionSource3 = this.f$4;
                    ControllersKt.MoCheckbox(this.f$0, this.f$1, this.f$2, z3, mutableInteractionSource3, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x02ad  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x02f1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void MoSlider(final float f, final Function1 function1, final Modifier modifier, boolean z, final Function0 function0, SliderColors sliderColors, MutableInteractionSource mutableInteractionSource, final ClosedFloatRange closedFloatRange, final float f2, final String str, Composer composer, final int i) {
        int i2;
        final boolean z2;
        MutableInteractionSource mutableInteractionSource2;
        SliderColors sliderColors2;
        int i3;
        MutableState mutableState;
        boolean z3;
        float fFloatValue;
        MutableState mutableState2;
        final ClosedFloatRange closedFloatRange2;
        boolean zChangedInstance;
        Object objRememberedValue;
        final MutableInteractionSource mutableInteractionSource3;
        final boolean z4;
        final SliderColors sliderColors3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(270038144);
        int i4 = i | (composerImpl.changed(f) ? 4 : 2) | (composerImpl.changedInstance(function1) ? 32 : 16) | 3072 | (composerImpl.changedInstance(function0) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | 1638400 | (composerImpl.changed(closedFloatRange) ? 8388608 : 4194304) | (composerImpl.changed(f2) ? 67108864 : 33554432) | (composerImpl.changed(str) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456);
        if ((306783379 & i4) == 306783378 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            z4 = z;
            sliderColors3 = sliderColors;
            mutableInteractionSource3 = mutableInteractionSource;
        } else {
            composerImpl.startDefaults();
            int i5 = i & 1;
            Composer.Companion companion = Composer.Companion;
            boolean z5 = true;
            if (i5 == 0 || composerImpl.getDefaultsInvalid()) {
                SeekbarDefaults.INSTANCE.getClass();
                composerImpl.startReplaceGroup(116419295);
                long color = BasicColorSchemeKt.toColor(SeslSliderColorSchemeKeyTokens.ThumbFillColor, composerImpl);
                long color2 = BasicColorSchemeKt.toColor(SeslSliderColorSchemeKeyTokens.ActivateTrackColor, composerImpl);
                long color3 = BasicColorSchemeKt.toColor(SeslSliderColorSchemeKeyTokens.ActivateTickColor, composerImpl);
                long color4 = BasicColorSchemeKt.toColor(SeslSliderColorSchemeKeyTokens.InactivateTrackColor, composerImpl);
                long color5 = BasicColorSchemeKt.toColor(SeslSliderColorSchemeKeyTokens.InactiveTickColor, composerImpl);
                MaterialTheme materialTheme = MaterialTheme.INSTANCE;
                materialTheme.getClass();
                long jM466compositeOverOWjLjI = ColorKt.m466compositeOverOWjLjI(color, MaterialTheme.getColorScheme(composerImpl).surface);
                materialTheme.getClass();
                long jM466compositeOverOWjLjI2 = ColorKt.m466compositeOverOWjLjI(color3, MaterialTheme.getColorScheme(composerImpl).surface);
                long jColor = ColorKt.Color(Color.m463getRedimpl(color2), Color.m462getGreenimpl(color2), Color.m460getBlueimpl(color2), Color.m459getAlphaimpl(color2) * 0.4f, Color.m461getColorSpaceimpl(color2));
                materialTheme.getClass();
                long jM466compositeOverOWjLjI3 = ColorKt.m466compositeOverOWjLjI(color5, MaterialTheme.getColorScheme(composerImpl).surface);
                long jColor2 = ColorKt.Color(Color.m463getRedimpl(color4), Color.m462getGreenimpl(color4), Color.m460getBlueimpl(color4), Color.m459getAlphaimpl(color4) * 0.4f, Color.m461getColorSpaceimpl(color4));
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults.colors (Controllers.kt:201)");
                }
                SliderColors sliderColors4 = new SliderColors(color, color2, color3, color4, color5, jM466compositeOverOWjLjI, jColor, jM466compositeOverOWjLjI2, jColor2, jM466compositeOverOWjLjI3, null);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                int i6 = i4 & (-458753);
                Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 1196938982, companion);
                if (objM == Composer.Companion.Empty) {
                    objM = InteractionSourceKt.MutableInteractionSource();
                    composerImpl.updateRememberedValue(objM);
                }
                composerImpl.end(false);
                i2 = i6;
                z2 = true;
                mutableInteractionSource2 = (MutableInteractionSource) objM;
                sliderColors2 = sliderColors4;
            } else {
                composerImpl.skipToGroupEnd();
                z2 = z;
                sliderColors2 = sliderColors;
                i2 = i4 & (-458753);
                mutableInteractionSource2 = mutableInteractionSource;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.MoSlider (Controllers.kt:112)");
            }
            final SecPlatformHapticFeedback secPlatformHapticFeedback = (SecPlatformHapticFeedback) composerImpl.consume(CompositionExtKt.LocalSecHapticFeedback);
            Object objM2 = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 1196946574, companion);
            Object obj = Composer.Companion.Empty;
            if (objM2 == obj) {
                objM2 = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(objM2);
            }
            final MutableState mutableState3 = (MutableState) objM2;
            Object objM3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1196948769);
            if (objM3 == obj) {
                objM3 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl.updateRememberedValue(objM3);
            }
            MutableState mutableState4 = (MutableState) objM3;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1196950802);
            if (((Boolean) mutableState4.getValue()).booleanValue()) {
                fFloatValue = f;
                i3 = i2;
                mutableState = mutableState4;
                z3 = false;
            } else {
                int i7 = i2 & 14;
                i3 = i2;
                mutableState = mutableState4;
                z3 = false;
                fFloatValue = ((Number) AnimateAsStateKt.animateFloatAsState(f, AnimationSpecKt.tween$default(300, 0, EasingKt.LinearEasing, 2), null, null, composerImpl, i7, 28).getValue()).floatValue();
            }
            composerImpl.end(z3);
            SeekbarDefaults seekbarDefaults = SeekbarDefaults.INSTANCE;
            boolean z6 = (!closedFloatRange.contains(Float.valueOf(f2)) || fFloatValue <= f2) ? z3 : true;
            seekbarDefaults.getClass();
            composerImpl.startReplaceGroup(1372188714);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults.getOverlapColor (Controllers.kt:215)");
            }
            long jColorResource = z6 ? ColorResources_androidKt.colorResource(R.color.sesl_seekbar_overlap_color_activated_light, composerImpl) : sliderColors2.activeTrackColor;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(z3);
            final State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(jColorResource, AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5), "animateColorAsState", composerImpl, 432, 8);
            composerImpl.startReplaceGroup(1196966999);
            boolean z7 = (i3 & 57344) == 16384;
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (z7 || objRememberedValue2 == obj) {
                mutableState2 = mutableState;
                objRememberedValue2 = new ControllersKt$MoSlider$2$1(mutableInteractionSource2, mutableState2, function0, null);
                composerImpl.updateRememberedValue(objRememberedValue2);
            } else {
                mutableState2 = mutableState;
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, mutableInteractionSource2, (Function2) objRememberedValue2);
            composerImpl.startReplaceGroup(1196985757);
            boolean z8 = (i3 & 112) == 32;
            if (((i3 & 29360128) ^ 12582912) > 8388608) {
                closedFloatRange2 = closedFloatRange;
                if (!composerImpl.changed(closedFloatRange2)) {
                }
                zChangedInstance = z8 | z5 | composerImpl.changedInstance(secPlatformHapticFeedback);
                objRememberedValue = composerImpl.rememberedValue();
                if (!zChangedInstance || objRememberedValue == obj) {
                    objRememberedValue = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            SecPlatformHapticFeedback secPlatformHapticFeedback2;
                            Float f3 = (Float) obj2;
                            float fFloatValue2 = f3.floatValue();
                            function1.mo781invoke(f3);
                            ClosedFloatRange closedFloatRange3 = closedFloatRange2;
                            if ((fFloatValue2 == closedFloatRange3._start || fFloatValue2 == closedFloatRange3._endInclusive) && (secPlatformHapticFeedback2 = secPlatformHapticFeedback) != null) {
                                secPlatformHapticFeedback2.performHapticFeedback(SecHapticFeedbackType.Seekbar);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                Function1 function12 = (Function1) objRememberedValue;
                composerImpl.end(false);
                final MutableInteractionSource mutableInteractionSource4 = mutableInteractionSource2;
                final MutableState mutableState5 = mutableState2;
                final float f3 = fFloatValue;
                final ClosedFloatRange closedFloatRange3 = closedFloatRange2;
                final SliderColors sliderColors5 = sliderColors2;
                boolean z9 = z2;
                seekbarDefaults.SliderContainer(mutableState3, modifier, mutableState5, function12, null, ComposableLambdaKt.rememberComposableLambda(-1394662355, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt.MoSlider.4
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                    /* JADX WARN: Removed duplicated region for block: B:22:0x0061  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        Function1 function13 = (Function1) obj2;
                        Composer composer2 = (Composer) obj3;
                        int iIntValue = ((Number) obj4).intValue();
                        if ((iIntValue & 6) == 0) {
                            iIntValue |= ((ComposerImpl) composer2).changedInstance(function13) ? 4 : 2;
                        }
                        if ((iIntValue & 19) == 18) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.MoSlider.<anonymous> (Controllers.kt:155)");
                                }
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                composerImpl3.startReplaceGroup(-352004894);
                                final String str2 = str;
                                boolean zChanged = composerImpl3.changed(str2);
                                Object objRememberedValue3 = composerImpl3.rememberedValue();
                                if (!zChanged) {
                                    Composer.Companion.getClass();
                                    if (objRememberedValue3 == Composer.Companion.Empty) {
                                        objRememberedValue3 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt$MoSlider$4$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj5) {
                                                SemanticsPropertiesKt.setContentDescription((SemanticsPropertyReceiver) obj5, str2);
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl3.updateRememberedValue(objRememberedValue3);
                                    }
                                    composerImpl3.end(false);
                                    Modifier modifierSemantics = SemanticsModifierKt.semantics(modifier, false, (Function1) objRememberedValue3);
                                    final State state = stateM7animateColorAsStateeuL9pac;
                                    long j = ((Color) state.getValue()).value;
                                    SliderColors sliderColors6 = sliderColors5;
                                    SliderColors sliderColorsM291copyK518z4 = sliderColors6.m291copyK518z4(sliderColors6.thumbColor, (1021 & 2) != 0 ? sliderColors6.activeTrackColor : j, (1021 & 4) != 0 ? sliderColors6.activeTickColor : 0L, (1021 & 8) != 0 ? sliderColors6.inactiveTrackColor : 0L, (1021 & 16) != 0 ? sliderColors6.inactiveTickColor : 0L, sliderColors6.disabledThumbColor, sliderColors6.disabledActiveTrackColor, sliderColors6.disabledActiveTickColor, sliderColors6.disabledInactiveTrackColor, sliderColors6.disabledInactiveTickColor);
                                    final MutableState mutableState6 = mutableState3;
                                    final MutableState mutableState7 = mutableState5;
                                    final SliderColors sliderColors7 = sliderColors5;
                                    SliderKt.Slider(f3, function13, modifierSemantics, z2, function0, sliderColorsM291copyK518z4, mutableInteractionSource4, 0, ComposableLambdaKt.rememberComposableLambda(392021361, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt.MoSlider.4.2
                                        /* JADX WARN: Removed duplicated region for block: B:18:0x0044  */
                                        @Override // kotlin.jvm.functions.Function3
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                            SliderState sliderState = (SliderState) obj5;
                                            Composer composer3 = (Composer) obj6;
                                            int iIntValue2 = ((Number) obj7).intValue();
                                            if ((iIntValue2 & 6) == 0) {
                                                iIntValue2 |= (iIntValue2 & 8) == 0 ? ((ComposerImpl) composer3).changed(sliderState) : ((ComposerImpl) composer3).changedInstance(sliderState) ? 4 : 2;
                                            }
                                            if ((iIntValue2 & 19) == 18) {
                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                if (composerImpl4.getSkipping()) {
                                                    composerImpl4.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.MoSlider.<anonymous>.<anonymous> (Controllers.kt:164)");
                                                    }
                                                    mutableState6.setValue(sliderState);
                                                    SeekbarDefaults seekbarDefaults2 = SeekbarDefaults.INSTANCE;
                                                    Modifier.Companion companion2 = Modifier.Companion;
                                                    Dp.Companion companion3 = Dp.Companion;
                                                    seekbarDefaults2.getClass();
                                                    float f4 = SeekbarDefaults.thumbSize;
                                                    Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(companion2, 0.0f, (22 - f4) / 2, 1);
                                                    long j2 = ((Color) state.getValue()).value;
                                                    SliderColors sliderColors8 = sliderColors7;
                                                    seekbarDefaults2.m2636ThumbFJfuzF0(mutableState7, modifierM127paddingVpY3zN4$default, sliderColors8.m291copyK518z4(sliderColors8.thumbColor, (1021 & 2) != 0 ? sliderColors8.activeTrackColor : j2, (1021 & 4) != 0 ? sliderColors8.activeTickColor : 0L, (1021 & 8) != 0 ? sliderColors8.inactiveTrackColor : 0L, (1021 & 16) != 0 ? sliderColors8.inactiveTickColor : 0L, sliderColors8.disabledThumbColor, sliderColors8.disabledActiveTrackColor, sliderColors8.disabledActiveTickColor, sliderColors8.disabledInactiveTrackColor, sliderColors8.disabledInactiveTickColor), false, f4, composer3, 221238);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl3), ComposableLambdaKt.rememberComposableLambda(316182800, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt.MoSlider.4.3
                                        /* JADX WARN: Removed duplicated region for block: B:18:0x0043  */
                                        @Override // kotlin.jvm.functions.Function3
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                            SliderState sliderState = (SliderState) obj5;
                                            Composer composer3 = (Composer) obj6;
                                            int iIntValue2 = ((Number) obj7).intValue();
                                            if ((iIntValue2 & 6) == 0) {
                                                iIntValue2 |= (iIntValue2 & 8) == 0 ? ((ComposerImpl) composer3).changed(sliderState) : ((ComposerImpl) composer3).changedInstance(sliderState) ? 4 : 2;
                                            }
                                            if ((iIntValue2 & 19) == 18) {
                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                if (composerImpl4.getSkipping()) {
                                                    composerImpl4.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.MoSlider.<anonymous>.<anonymous> (Controllers.kt:173)");
                                                    }
                                                    mutableState6.setValue(sliderState);
                                                    SeekbarDefaults seekbarDefaults2 = SeekbarDefaults.INSTANCE;
                                                    long j2 = ((Color) state.getValue()).value;
                                                    SliderColors sliderColors8 = sliderColors7;
                                                    seekbarDefaults2.Track(sliderState, mutableState7, null, sliderColors8.m291copyK518z4(sliderColors8.thumbColor, (1021 & 2) != 0 ? sliderColors8.activeTrackColor : j2, (1021 & 4) != 0 ? sliderColors8.activeTickColor : 0L, (1021 & 8) != 0 ? sliderColors8.inactiveTrackColor : 0L, (1021 & 16) != 0 ? sliderColors8.inactiveTickColor : 0L, sliderColors8.disabledThumbColor, sliderColors8.disabledActiveTrackColor, sliderColors8.disabledActiveTickColor, sliderColors8.disabledInactiveTrackColor, sliderColors8.disabledInactiveTickColor), composer3, 24632 | (iIntValue2 & 14));
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl3), closedFloatRange3, composerImpl3, ((iIntValue << 3) & 112) | 905969664, 0, 128);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 1769910);
                composerImpl = composerImpl;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                mutableInteractionSource3 = mutableInteractionSource4;
                z4 = z9;
                sliderColors3 = sliderColors5;
            } else {
                closedFloatRange2 = closedFloatRange;
            }
            if ((i3 & 12582912) != 8388608) {
                z5 = false;
            }
            zChangedInstance = z8 | z5 | composerImpl.changedInstance(secPlatformHapticFeedback);
            objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                objRememberedValue = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        SecPlatformHapticFeedback secPlatformHapticFeedback2;
                        Float f32 = (Float) obj2;
                        float fFloatValue2 = f32.floatValue();
                        function1.mo781invoke(f32);
                        ClosedFloatRange closedFloatRange32 = closedFloatRange2;
                        if ((fFloatValue2 == closedFloatRange32._start || fFloatValue2 == closedFloatRange32._endInclusive) && (secPlatformHapticFeedback2 = secPlatformHapticFeedback) != null) {
                            secPlatformHapticFeedback2.performHapticFeedback(SecHapticFeedbackType.Seekbar);
                        }
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue);
                Function1 function122 = (Function1) objRememberedValue;
                composerImpl.end(false);
                final MutableInteractionSource mutableInteractionSource42 = mutableInteractionSource2;
                final MutableState<Boolean> mutableState52 = mutableState2;
                final float f32 = fFloatValue;
                final ClosedFloatingPointRange closedFloatRange32 = closedFloatRange2;
                final SliderColors sliderColors52 = sliderColors2;
                boolean z92 = z2;
                seekbarDefaults.SliderContainer(mutableState3, modifier, mutableState52, function122, null, ComposableLambdaKt.rememberComposableLambda(-1394662355, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt.MoSlider.4
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                    /* JADX WARN: Removed duplicated region for block: B:22:0x0061  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        Function1 function13 = (Function1) obj2;
                        Composer composer2 = (Composer) obj3;
                        int iIntValue = ((Number) obj4).intValue();
                        if ((iIntValue & 6) == 0) {
                            iIntValue |= ((ComposerImpl) composer2).changedInstance(function13) ? 4 : 2;
                        }
                        if ((iIntValue & 19) == 18) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.MoSlider.<anonymous> (Controllers.kt:155)");
                                }
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                composerImpl3.startReplaceGroup(-352004894);
                                final String str2 = str;
                                boolean zChanged = composerImpl3.changed(str2);
                                Object objRememberedValue3 = composerImpl3.rememberedValue();
                                if (!zChanged) {
                                    Composer.Companion.getClass();
                                    if (objRememberedValue3 == Composer.Companion.Empty) {
                                        objRememberedValue3 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt$MoSlider$4$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj5) {
                                                SemanticsPropertiesKt.setContentDescription((SemanticsPropertyReceiver) obj5, str2);
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl3.updateRememberedValue(objRememberedValue3);
                                    }
                                    composerImpl3.end(false);
                                    Modifier modifierSemantics = SemanticsModifierKt.semantics(modifier, false, (Function1) objRememberedValue3);
                                    final State<Color> state = stateM7animateColorAsStateeuL9pac;
                                    long j = ((Color) state.getValue()).value;
                                    SliderColors sliderColors6 = sliderColors52;
                                    SliderColors sliderColorsM291copyK518z4 = sliderColors6.m291copyK518z4(sliderColors6.thumbColor, (1021 & 2) != 0 ? sliderColors6.activeTrackColor : j, (1021 & 4) != 0 ? sliderColors6.activeTickColor : 0L, (1021 & 8) != 0 ? sliderColors6.inactiveTrackColor : 0L, (1021 & 16) != 0 ? sliderColors6.inactiveTickColor : 0L, sliderColors6.disabledThumbColor, sliderColors6.disabledActiveTrackColor, sliderColors6.disabledActiveTickColor, sliderColors6.disabledInactiveTrackColor, sliderColors6.disabledInactiveTickColor);
                                    final MutableState<SliderState> mutableState6 = mutableState3;
                                    final MutableState<Boolean> mutableState7 = mutableState52;
                                    final SliderColors sliderColors7 = sliderColors52;
                                    SliderKt.Slider(f32, function13, modifierSemantics, z2, function0, sliderColorsM291copyK518z4, mutableInteractionSource42, 0, ComposableLambdaKt.rememberComposableLambda(392021361, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt.MoSlider.4.2
                                        /* JADX WARN: Removed duplicated region for block: B:18:0x0044  */
                                        @Override // kotlin.jvm.functions.Function3
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                            SliderState sliderState = (SliderState) obj5;
                                            Composer composer3 = (Composer) obj6;
                                            int iIntValue2 = ((Number) obj7).intValue();
                                            if ((iIntValue2 & 6) == 0) {
                                                iIntValue2 |= (iIntValue2 & 8) == 0 ? ((ComposerImpl) composer3).changed(sliderState) : ((ComposerImpl) composer3).changedInstance(sliderState) ? 4 : 2;
                                            }
                                            if ((iIntValue2 & 19) == 18) {
                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                if (composerImpl4.getSkipping()) {
                                                    composerImpl4.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.MoSlider.<anonymous>.<anonymous> (Controllers.kt:164)");
                                                    }
                                                    mutableState6.setValue(sliderState);
                                                    SeekbarDefaults seekbarDefaults2 = SeekbarDefaults.INSTANCE;
                                                    Modifier.Companion companion2 = Modifier.Companion;
                                                    Dp.Companion companion3 = Dp.Companion;
                                                    seekbarDefaults2.getClass();
                                                    float f4 = SeekbarDefaults.thumbSize;
                                                    Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(companion2, 0.0f, (22 - f4) / 2, 1);
                                                    long j2 = ((Color) state.getValue()).value;
                                                    SliderColors sliderColors8 = sliderColors7;
                                                    seekbarDefaults2.m2636ThumbFJfuzF0(mutableState7, modifierM127paddingVpY3zN4$default, sliderColors8.m291copyK518z4(sliderColors8.thumbColor, (1021 & 2) != 0 ? sliderColors8.activeTrackColor : j2, (1021 & 4) != 0 ? sliderColors8.activeTickColor : 0L, (1021 & 8) != 0 ? sliderColors8.inactiveTrackColor : 0L, (1021 & 16) != 0 ? sliderColors8.inactiveTickColor : 0L, sliderColors8.disabledThumbColor, sliderColors8.disabledActiveTrackColor, sliderColors8.disabledActiveTickColor, sliderColors8.disabledInactiveTrackColor, sliderColors8.disabledInactiveTickColor), false, f4, composer3, 221238);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl3), ComposableLambdaKt.rememberComposableLambda(316182800, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt.MoSlider.4.3
                                        /* JADX WARN: Removed duplicated region for block: B:18:0x0043  */
                                        @Override // kotlin.jvm.functions.Function3
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                            SliderState sliderState = (SliderState) obj5;
                                            Composer composer3 = (Composer) obj6;
                                            int iIntValue2 = ((Number) obj7).intValue();
                                            if ((iIntValue2 & 6) == 0) {
                                                iIntValue2 |= (iIntValue2 & 8) == 0 ? ((ComposerImpl) composer3).changed(sliderState) : ((ComposerImpl) composer3).changedInstance(sliderState) ? 4 : 2;
                                            }
                                            if ((iIntValue2 & 19) == 18) {
                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                if (composerImpl4.getSkipping()) {
                                                    composerImpl4.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.MoSlider.<anonymous>.<anonymous> (Controllers.kt:173)");
                                                    }
                                                    mutableState6.setValue(sliderState);
                                                    SeekbarDefaults seekbarDefaults2 = SeekbarDefaults.INSTANCE;
                                                    long j2 = ((Color) state.getValue()).value;
                                                    SliderColors sliderColors8 = sliderColors7;
                                                    seekbarDefaults2.Track(sliderState, mutableState7, null, sliderColors8.m291copyK518z4(sliderColors8.thumbColor, (1021 & 2) != 0 ? sliderColors8.activeTrackColor : j2, (1021 & 4) != 0 ? sliderColors8.activeTickColor : 0L, (1021 & 8) != 0 ? sliderColors8.inactiveTrackColor : 0L, (1021 & 16) != 0 ? sliderColors8.inactiveTickColor : 0L, sliderColors8.disabledThumbColor, sliderColors8.disabledActiveTrackColor, sliderColors8.disabledActiveTickColor, sliderColors8.disabledInactiveTrackColor, sliderColors8.disabledInactiveTickColor), composer3, 24632 | (iIntValue2 & 14));
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl3), closedFloatRange32, composerImpl3, ((iIntValue << 3) & 112) | 905969664, 0, 128);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 1769910);
                composerImpl = composerImpl;
                if (ComposerKt.isTraceInProgress()) {
                }
                mutableInteractionSource3 = mutableInteractionSource42;
                z4 = z92;
                sliderColors3 = sliderColors52;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(f, function1, modifier, z4, function0, sliderColors3, mutableInteractionSource3, closedFloatRange, f2, str, i) { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt$$ExternalSyntheticLambda2
                public final /* synthetic */ float f$0;
                public final /* synthetic */ Function1 f$1;
                public final /* synthetic */ Modifier f$2;
                public final /* synthetic */ boolean f$3;
                public final /* synthetic */ Function0 f$4;
                public final /* synthetic */ SliderColors f$5;
                public final /* synthetic */ MutableInteractionSource f$6;
                public final /* synthetic */ ClosedFloatRange f$7;
                public final /* synthetic */ float f$8;
                public final /* synthetic */ String f$9;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(385);
                    ClosedFloatRange closedFloatRange4 = this.f$7;
                    float f4 = this.f$8;
                    String str2 = this.f$9;
                    ControllersKt.MoSlider(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, closedFloatRange4, f4, str2, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
