package com.android.systemui.brightness.ui.compose;

import android.content.Context;
import android.view.MotionEvent;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.CornerSizeKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SliderColors;
import androidx.compose.material3.SliderDefaults;
import androidx.compose.material3.SliderKt;
import androidx.compose.material3.SliderState;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.CacheDrawScope;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.painter.ColorPainter;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.input.pointer.PointerInteropFilter_androidKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.compose.modifiers.PaddingKt;
import com.android.compose.theme.AndroidColorScheme;
import com.android.compose.theme.AndroidColorSchemeKt;
import com.android.compose.ui.graphics.ContainerState;
import com.android.compose.ui.graphics.DrawInContainerKt;
import com.android.compose.ui.graphics.DrawInContainerNode$$ExternalSyntheticLambda1;
import com.android.compose.ui.graphics.DrawInOverlayKt;
import com.android.systemui.brightness.shared.model.GammaBrightness;
import com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.haptics.slider.SeekableSliderTrackerConfig;
import com.android.systemui.haptics.slider.SliderHapticFeedbackConfig;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.android.systemui.qs.ui.compose.BorderOnFocusKt;
import com.android.systemui.utils.PolicyRestriction;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.IntRange;
import kotlin.reflect.KFunction;
import kotlinx.coroutines.CoroutineScope;
import platform.test.motion.compose.values.MotionTestValueKt;
import platform.test.motion.compose.values.MotionTestValuesNode;

/* loaded from: classes.dex */
public abstract class BrightnessSliderKt {
    /* JADX WARN: Removed duplicated region for block: B:100:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0238  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0259  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x02a7  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x02e6  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x02f6  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x02f9  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x035b  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0385  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x03c2  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x03cf  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x03d9  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0404  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0407  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0421  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x0424  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x042e  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0467  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x046a  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x047c  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0490  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x04f6  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0506  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x051e  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x0528  */
    /* JADX WARN: Removed duplicated region for block: B:263:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00ff  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void BrightnessSlider(final int i, final IntRange intRange, final Function1 function1, final Function3 function3, final PolicyRestriction policyRestriction, final Function1 function12, final Function1 function13, final Function1 function14, final boolean z, Modifier modifier, Function0 function0, final SliderHapticsViewModel.Factory factory, Composer composer, final int i2, final int i3, final int i4) {
        int i5;
        int i6;
        int i7;
        Modifier modifier2;
        int i8;
        int i9;
        int i10;
        int i11;
        Function0 function02;
        boolean z2;
        Function0 function03;
        final MutableIntState mutableIntState;
        final ClosedFloatRange closedFloatRange;
        boolean z3;
        Object objM;
        Object obj;
        final MutableInteractionSource mutableInteractionSource;
        boolean zChanged;
        Object objRememberedValue;
        final SliderHapticsViewModel sliderHapticsViewModel;
        boolean zChanged2;
        Object objRememberedValue2;
        State state;
        Context context;
        boolean zChangedInstance;
        Object objRememberedValue3;
        Object objRememberedValue4;
        boolean z4;
        Object objRememberedValue5;
        boolean zChanged3;
        Object obj2;
        Function0 function04;
        Modifier modifier3;
        MutableInteractionSource mutableInteractionSource2;
        final MutableIntState mutableIntState2;
        final boolean z5;
        boolean z6;
        int i12;
        boolean zChanged4;
        Object obj3;
        final MutableInteractionSource mutableInteractionSource3;
        ComposerImpl composerImpl;
        MutableState mutableStateRememberUpdatedState;
        boolean zChanged5;
        Object objRememberedValue6;
        final Function0 function05;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        final boolean z7 = z;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1977181288);
        if ((i4 & 1) != 0) {
            i6 = i2 | 6;
            i5 = i;
        } else {
            i5 = i;
            if ((i2 & 6) == 0) {
                i6 = (composerImpl2.changed(i5) ? 4 : 2) | i2;
            } else {
                i6 = i2;
            }
        }
        if ((i4 & 2) != 0) {
            i6 |= 48;
        } else if ((i2 & 48) == 0) {
            i6 |= composerImpl2.changedInstance(intRange) ? 32 : 16;
        }
        if ((i4 & 4) != 0) {
            i6 |= 384;
        } else if ((i2 & 384) == 0) {
            i6 |= composerImpl2.changedInstance(function1) ? 256 : 128;
        }
        if ((i4 & 8) != 0) {
            i6 |= 3072;
        } else if ((i2 & 3072) == 0) {
            i6 |= composerImpl2.changedInstance(function3) ? 2048 : 1024;
        }
        if ((i4 & 16) != 0) {
            i6 |= 24576;
        } else if ((i2 & 24576) == 0) {
            i6 |= (i2 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) == 0 ? composerImpl2.changed(policyRestriction) : composerImpl2.changedInstance(policyRestriction) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i4 & 32) != 0) {
            i6 |= 196608;
        } else if ((i2 & 196608) == 0) {
            i6 |= composerImpl2.changedInstance(function12) ? 131072 : 65536;
        }
        if ((i4 & 64) != 0) {
            i6 |= 1572864;
        } else {
            if ((i2 & 1572864) == 0) {
                i6 |= composerImpl2.changedInstance(function13) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
            }
            if ((i4 & 128) != 0) {
                if ((i2 & 12582912) == 0) {
                    i6 |= composerImpl2.changedInstance(function14) ? 8388608 : 4194304;
                }
                if ((i4 & 256) != 0) {
                    i6 |= 100663296;
                } else if ((i2 & 100663296) == 0) {
                    i6 |= composerImpl2.changed(z7) ? 67108864 : 33554432;
                }
                i7 = i4 & 512;
                if (i7 != 0) {
                    i6 |= 805306368;
                    modifier2 = modifier;
                } else {
                    modifier2 = modifier;
                    if ((i2 & 805306368) == 0) {
                        i6 |= composerImpl2.changed(modifier2) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                    }
                }
                i8 = i6;
                i9 = i4 & 1024;
                if (i9 != 0) {
                    i11 = i3 | 6;
                    i10 = i9;
                } else if ((i3 & 6) == 0) {
                    i10 = i9;
                    i11 = i3 | (composerImpl2.changedInstance(function0) ? 4 : 2);
                } else {
                    i10 = i9;
                    i11 = i3;
                }
                if ((i4 & 2048) != 0) {
                    i11 |= 48;
                } else if ((i3 & 48) == 0) {
                    i11 |= (i3 & 64) == 0 ? composerImpl2.changed(factory) : composerImpl2.changedInstance(factory) ? 32 : 16;
                }
                int i13 = i11;
                if ((i8 & 306783379) == 306783378 && (i13 & 19) == 18 && composerImpl2.getSkipping()) {
                    composerImpl2.skipToGroupEnd();
                    function05 = function0;
                    modifier3 = modifier2;
                    composerImpl = composerImpl2;
                } else {
                    Modifier modifier4 = i7 == 0 ? Modifier.Companion : modifier2;
                    Composer.Companion companion = Composer.Companion;
                    if (i10 == 0) {
                        Object objM2 = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl2, -276992244, companion);
                        if (objM2 == Composer.Companion.Empty) {
                            objM2 = new BrightnessSliderKt$$ExternalSyntheticLambda0();
                            composerImpl2.updateRememberedValue(objM2);
                        }
                        function02 = (Function0) objM2;
                        composerImpl2.end(false);
                    } else {
                        function02 = function0;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.brightness.ui.compose.BrightnessSlider (BrightnessSlider.kt:119)");
                    }
                    composerImpl2.startReplaceGroup(-276989440);
                    int i14 = i8 & 14;
                    z2 = i14 != 4;
                    Object objRememberedValue7 = composerImpl2.rememberedValue();
                    if (z2) {
                        companion.getClass();
                        function03 = function02;
                        if (objRememberedValue7 == Composer.Companion.Empty) {
                        }
                        mutableIntState = (MutableIntState) objRememberedValue7;
                        composerImpl2.end(false);
                        State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(((SnapshotMutableIntStateImpl) mutableIntState).getIntValue(), null, "BrightnessSliderAnimatedValue", null, composerImpl2, 3072, 22);
                        closedFloatRange = new ClosedFloatRange(intRange.first, intRange.last);
                        boolean z8 = policyRestriction instanceof PolicyRestriction.Restricted;
                        z3 = !z8;
                        objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl2, -276977071, companion);
                        obj = Composer.Companion.Empty;
                        if (objM == obj) {
                            objM = InteractionSourceKt.MutableInteractionSource();
                            composerImpl2.updateRememberedValue(objM);
                        }
                        mutableInteractionSource = (MutableInteractionSource) objM;
                        composerImpl2.end(false);
                        composerImpl2.startReplaceGroup(-276973344);
                        composerImpl2.startReplaceGroup(-276970040);
                        zChanged = ((i13 & 112) == 32 || ((i13 & 64) != 0 && composerImpl2.changedInstance(factory))) | composerImpl2.changed(closedFloatRange);
                        objRememberedValue = composerImpl2.rememberedValue();
                        if (!zChanged || objRememberedValue == obj) {
                            final int i15 = 0;
                            objRememberedValue = new Function0() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda1
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    switch (i15) {
                                        case 0:
                                            return ((SliderHapticsViewModel.Factory) factory).create((MutableInteractionSource) mutableInteractionSource, (ClosedFloatRange) closedFloatRange, Orientation.Horizontal, new SliderHapticFeedbackConfig(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 1.0f, 0, 0.0f, 0.0f, 0.0f, 0.0f, null, 32511, null), new SeekableSliderTrackerConfig(0L, 0.0f, 0.0f, 0.0f, 15, null));
                                        default:
                                            int intValue = ((SnapshotMutableIntStateImpl) ((MutableIntState) closedFloatRange)).getIntValue();
                                            int i16 = ((IntRange) factory).first;
                                            return Integer.valueOf(((Number) ((Function1) mutableInteractionSource).mo781invoke(Float.valueOf(((intValue - i16) * 100.0f) / (r2.last - i16)))).intValue());
                                    }
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue);
                        }
                        composerImpl2.end(false);
                        sliderHapticsViewModel = (SliderHapticsViewModel) SysUiViewModelKt.rememberViewModel("SliderHapticsViewModel", null, (Function0) objRememberedValue, composerImpl2, 6, 2);
                        composerImpl2.end(false);
                        composerImpl2.startReplaceGroup(-1949910451);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.brightness.ui.compose.colors (BrightnessSlider.kt:449)");
                        }
                        SliderDefaults.INSTANCE.getClass();
                        SliderColors sliderColorsColors = SliderDefaults.colors(composerImpl2);
                        long j = ((AndroidColorScheme) composerImpl2.consume(AndroidColorSchemeKt.LocalAndroidColorScheme)).surfaceEffect2;
                        MaterialTheme.INSTANCE.getClass();
                        final SliderColors sliderColorsM291copyK518z4 = sliderColorsColors.m291copyK518z4(sliderColorsColors.thumbColor, (1021 & 2) != 0 ? sliderColorsColors.activeTrackColor : 0L, (1021 & 4) != 0 ? sliderColorsColors.activeTickColor : MaterialTheme.getColorScheme(composerImpl2).onPrimary, (1021 & 8) != 0 ? sliderColorsColors.inactiveTrackColor : j, (1021 & 16) != 0 ? sliderColorsColors.inactiveTickColor : MaterialTheme.getColorScheme(composerImpl2).onSurface, sliderColorsColors.disabledThumbColor, sliderColorsColors.disabledActiveTrackColor, sliderColorsColors.disabledActiveTickColor, sliderColorsColors.disabledInactiveTrackColor, sliderColorsColors.disabledInactiveTickColor);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        composerImpl2.end(false);
                        composerImpl2.startReplaceGroup(-276945077);
                        zChanged2 = (i14 != 4) | composerImpl2.changed(intRange);
                        objRememberedValue2 = composerImpl2.rememberedValue();
                        if (!zChanged2 || objRememberedValue2 == obj) {
                            final int i16 = 1;
                            objRememberedValue2 = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda1
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    switch (i16) {
                                        case 0:
                                            return ((SliderHapticsViewModel.Factory) intRange).create((MutableInteractionSource) function1, (ClosedFloatRange) mutableIntState, Orientation.Horizontal, new SliderHapticFeedbackConfig(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 1.0f, 0, 0.0f, 0.0f, 0.0f, 0.0f, null, 32511, null), new SeekableSliderTrackerConfig(0L, 0.0f, 0.0f, 0.0f, 15, null));
                                        default:
                                            int intValue = ((SnapshotMutableIntStateImpl) ((MutableIntState) mutableIntState)).getIntValue();
                                            int i162 = ((IntRange) intRange).first;
                                            return Integer.valueOf(((Number) ((Function1) function1).mo781invoke(Float.valueOf(((intValue - i162) * 100.0f) / (r2.last - i162)))).intValue());
                                    }
                                }
                            });
                            composerImpl2.updateRememberedValue(objRememberedValue2);
                        }
                        state = (State) objRememberedValue2;
                        composerImpl2.end(false);
                        context = (Context) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalContext);
                        Color.Companion.getClass();
                        ColorPainter colorPainter = new ColorPainter(Color.Transparent, null);
                        Integer numValueOf = Integer.valueOf(((Number) state.getValue()).intValue());
                        composerImpl2.startReplaceGroup(-276929638);
                        zChangedInstance = composerImpl2.changedInstance(function3) | composerImpl2.changed(state) | composerImpl2.changedInstance(context);
                        objRememberedValue3 = composerImpl2.rememberedValue();
                        if (!zChangedInstance || objRememberedValue3 == obj) {
                            objRememberedValue3 = new BrightnessSliderKt$BrightnessSlider$painter$2$1(function3, context, state, null);
                            composerImpl2.updateRememberedValue(objRememberedValue3);
                        }
                        composerImpl2.end(false);
                        final MutableState mutableStateProduceState = SnapshotStateKt.produceState(colorPainter, numValueOf, context, (Function2) objRememberedValue3, composerImpl2, 0);
                        composerImpl2.startReplaceGroup(-276915607);
                        objRememberedValue4 = composerImpl2.rememberedValue();
                        if (objRememberedValue4 == obj) {
                            objRememberedValue4 = new Function4() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSlider$trackIcon$1$1
                                @Override // kotlin.jvm.functions.Function4
                                public final Object invoke(Object obj4, Object obj5, Object obj6, Object obj7) {
                                    DrawScope drawScope = (DrawScope) obj4;
                                    long j2 = ((Offset) obj5).packedValue;
                                    long j3 = ((Color) obj6).value;
                                    float fFloatValue = ((Number) obj7).floatValue();
                                    float fIntBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32));
                                    Dimensions.INSTANCE.getClass();
                                    float fMo58toPx0680j_4 = drawScope.mo58toPx0680j_4(Dimensions.IconPadding) + fIntBitsToFloat;
                                    float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j2 & 4294967295L));
                                    State state2 = mutableStateProduceState;
                                    drawScope.getDrawContext().transform.translate(fMo58toPx0680j_4, fIntBitsToFloat2);
                                    try {
                                        ((Painter) state2.getValue()).m564drawx_KDEd0(drawScope, drawScope.mo59toSizeXkaWNTQ(Dimensions.IconSize), fFloatValue, ColorFilter.Companion.m465tintxETnrds$default(ColorFilter.Companion, j3));
                                        drawScope.getDrawContext().transform.translate(-fMo58toPx0680j_4, -fIntBitsToFloat2);
                                        return Unit.INSTANCE;
                                    } catch (Throwable th) {
                                        drawScope.getDrawContext().transform.translate(-fMo58toPx0680j_4, -fIntBitsToFloat2);
                                        throw th;
                                    }
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue4);
                        }
                        final Function4 function4 = (Function4) objRememberedValue4;
                        composerImpl2.end(false);
                        float fFloatValue = ((Number) stateAnimateFloatAsState.getValue()).floatValue();
                        Modifier modifierSysuiResTag = SysuiTestTagKt.sysuiResTag(modifier4, "slider");
                        composerImpl2.startReplaceGroup(-276882783);
                        z4 = ((i8 & 458752) != 131072) | ((i8 & 57344) != 16384 || ((i8 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 && composerImpl2.changedInstance(policyRestriction)));
                        objRememberedValue5 = composerImpl2.rememberedValue();
                        if (!z4 || objRememberedValue5 == obj) {
                            objRememberedValue5 = new BrightnessSliderKt$$ExternalSyntheticLambda3(0, policyRestriction, function12);
                            composerImpl2.updateRememberedValue(objRememberedValue5);
                        }
                        composerImpl2.end(false);
                        Modifier modifierM35clickableXHw0xAI$default = ClickableKt.m35clickableXHw0xAI$default(modifierSysuiResTag, z8, null, (Function0) objRememberedValue5, 6);
                        composerImpl2.startReplaceGroup(-276901378);
                        int i17 = i8 & 234881024;
                        zChanged3 = ((i8 & 3670016) != 1048576) | composerImpl2.changed(z3) | (i17 != 67108864) | composerImpl2.changedInstance(sliderHapticsViewModel) | composerImpl2.changed(mutableIntState);
                        Object objRememberedValue8 = composerImpl2.rememberedValue();
                        if (!zChanged3 || objRememberedValue8 == obj) {
                            function04 = function03;
                            modifier3 = modifier4;
                            mutableInteractionSource2 = mutableInteractionSource;
                            mutableIntState2 = mutableIntState;
                            z5 = z3;
                            z6 = false;
                            i12 = 67108864;
                            obj2 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda4
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj4) {
                                    float fFloatValue2 = ((Float) obj4).floatValue();
                                    if (z5 && !z) {
                                        SliderHapticsViewModel sliderHapticsViewModel2 = sliderHapticsViewModel;
                                        if (sliderHapticsViewModel2 != null) {
                                            sliderHapticsViewModel2.onValueChange(fFloatValue2);
                                        }
                                        SnapshotMutableIntStateImpl snapshotMutableIntStateImpl = (SnapshotMutableIntStateImpl) mutableIntState2;
                                        snapshotMutableIntStateImpl.setIntValue((int) fFloatValue2);
                                        function13.mo781invoke(Integer.valueOf(snapshotMutableIntStateImpl.getIntValue()));
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(obj2);
                        } else {
                            function04 = function03;
                            modifier3 = modifier4;
                            mutableInteractionSource2 = mutableInteractionSource;
                            z5 = z3;
                            obj2 = objRememberedValue8;
                            z6 = false;
                            i12 = 67108864;
                            mutableIntState2 = mutableIntState;
                        }
                        Function1 function15 = (Function1) obj2;
                        composerImpl2.end(z6);
                        composerImpl2.startReplaceGroup(-276892518);
                        zChanged4 = composerImpl2.changed(z5) | (i17 != i12 ? true : z6) | composerImpl2.changedInstance(sliderHapticsViewModel) | ((i8 & 29360128) != 8388608 ? true : z6) | composerImpl2.changed(mutableIntState2);
                        Object objRememberedValue9 = composerImpl2.rememberedValue();
                        if (!zChanged4 || objRememberedValue9 == obj) {
                            z7 = z;
                            obj3 = new Function0() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda5
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    if (z5 && !z7) {
                                        SliderHapticsViewModel sliderHapticsViewModel2 = sliderHapticsViewModel;
                                        if (sliderHapticsViewModel2 != null) {
                                            sliderHapticsViewModel2.onValueChangeEnded();
                                        }
                                        function14.mo781invoke(Integer.valueOf(((SnapshotMutableIntStateImpl) mutableIntState2).getIntValue()));
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(obj3);
                        } else {
                            obj3 = objRememberedValue9;
                            z7 = z;
                        }
                        composerImpl2.end(z6);
                        mutableInteractionSource3 = mutableInteractionSource2;
                        ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(2013632220, new Function3() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt.BrightnessSlider.5
                            /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                            @Override // kotlin.jvm.functions.Function3
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj4, Object obj5, Object obj6) {
                                Composer composer2 = (Composer) obj5;
                                if ((((Number) obj6).intValue() & 17) == 16) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.brightness.ui.compose.BrightnessSlider.<anonymous> (BrightnessSlider.kt:211)");
                                        }
                                        SliderDefaults sliderDefaults = SliderDefaults.INSTANCE;
                                        Dp.Companion companion2 = Dp.Companion;
                                        sliderDefaults.m296Thumb9LiSoMs(mutableInteractionSource3, null, sliderColorsM291copyK518z4, z5, DpKt.m840DpSizeYgX7TsA(4, 52), composer2, 221190, 2);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composerImpl2);
                        int i18 = i12;
                        composerImpl = composerImpl2;
                        final long j2 = sliderColorsM291copyK518z4.activeTickColor;
                        final long j3 = sliderColorsM291copyK518z4.inactiveTickColor;
                        SliderKt.Slider(fFloatValue, function15, modifierM35clickableXHw0xAI$default, z5, (Function0) obj3, sliderColorsM291copyK518z4, mutableInteractionSource3, 0, composableLambdaImplRememberComposableLambda, ComposableLambdaKt.rememberComposableLambda(-1329503237, new Function3() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt.BrightnessSlider.6
                            /* JADX WARN: Removed duplicated region for block: B:18:0x0046  */
                            @Override // kotlin.jvm.functions.Function3
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj4, Object obj5, Object obj6) {
                                final SliderState sliderState;
                                int i19;
                                SliderState sliderState2 = (SliderState) obj4;
                                Composer composer2 = (Composer) obj5;
                                int iIntValue = ((Number) obj6).intValue();
                                if ((iIntValue & 6) == 0) {
                                    iIntValue |= (iIntValue & 8) == 0 ? ((ComposerImpl) composer2).changed(sliderState2) : ((ComposerImpl) composer2).changedInstance(sliderState2) ? 4 : 2;
                                }
                                if ((iIntValue & 19) == 18) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.brightness.ui.compose.BrightnessSlider.<anonymous> (BrightnessSlider.kt:219)");
                                        }
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                        composerImpl4.startReplaceGroup(195415466);
                                        Object objRememberedValue10 = composerImpl4.rememberedValue();
                                        Composer.Companion.getClass();
                                        Object obj7 = Composer.Companion.Empty;
                                        if (objRememberedValue10 == obj7) {
                                            objRememberedValue10 = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
                                            composerImpl4.updateRememberedValue(objRememberedValue10);
                                        }
                                        final MutableState mutableState = (MutableState) objRememberedValue10;
                                        Object objM3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl4, false, 195418144);
                                        if (objM3 == obj7) {
                                            Float fValueOf = Float.valueOf(1.0f);
                                            FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
                                            Object animatable = new Animatable(fValueOf, VectorConvertersKt.FloatToVector, null, "iconActiveAlpha", 4, null);
                                            composerImpl4.updateRememberedValue(animatable);
                                            objM3 = animatable;
                                        }
                                        final Animatable animatable2 = (Animatable) objM3;
                                        Object objM4 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl4, false, 195426562);
                                        if (objM4 == obj7) {
                                            Float fValueOf2 = Float.valueOf(0.0f);
                                            FloatCompanionObject floatCompanionObject2 = FloatCompanionObject.INSTANCE;
                                            Object animatable3 = new Animatable(fValueOf2, VectorConvertersKt.FloatToVector, null, "iconInactiveAlpha", 4, null);
                                            composerImpl4.updateRememberedValue(animatable3);
                                            objM4 = animatable3;
                                        }
                                        final Animatable animatable4 = (Animatable) objM4;
                                        composerImpl4.end(false);
                                        Boolean bool = (Boolean) mutableState.getValue();
                                        bool.getClass();
                                        composerImpl4.startReplaceGroup(195436891);
                                        boolean zChangedInstance2 = composerImpl4.changedInstance(animatable2) | composerImpl4.changedInstance(animatable4);
                                        Object objRememberedValue11 = composerImpl4.rememberedValue();
                                        if (zChangedInstance2 || objRememberedValue11 == obj7) {
                                            objRememberedValue11 = new BrightnessSliderKt$BrightnessSlider$6$1$1(mutableState, animatable2, animatable4, null);
                                            composerImpl4.updateRememberedValue(objRememberedValue11);
                                        }
                                        composerImpl4.end(false);
                                        EffectsKt.LaunchedEffect(animatable2, animatable4, bool, (Function2) objRememberedValue11, composerImpl4);
                                        SliderDefaults sliderDefaults = SliderDefaults.INSTANCE;
                                        Modifier.Companion companion2 = Modifier.Companion;
                                        composerImpl4.startReplaceGroup(195453778);
                                        boolean zChangedInstance3 = composerImpl4.changedInstance(animatable2) | composerImpl4.changedInstance(animatable4);
                                        Object objRememberedValue12 = composerImpl4.rememberedValue();
                                        if (zChangedInstance3 || objRememberedValue12 == obj7) {
                                            objRememberedValue12 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSlider$6$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj8) {
                                                    MotionTestValuesNode.AnonymousClass1 anonymousClass1 = (MotionTestValuesNode.AnonymousClass1) obj8;
                                                    Animatable animatable5 = animatable2;
                                                    boolean zIsRunning = animatable5.isRunning();
                                                    Animatable animatable6 = animatable4;
                                                    Boolean boolValueOf = Boolean.valueOf(zIsRunning || animatable6.isRunning());
                                                    BrightnessSliderMotionTestKeys.INSTANCE.getClass();
                                                    anonymousClass1.exportAs(boolValueOf, BrightnessSliderMotionTestKeys.AnimatingIcon);
                                                    anonymousClass1.exportAs(animatable5.internalState.getValue(), BrightnessSliderMotionTestKeys.ActiveIconAlpha);
                                                    anonymousClass1.exportAs(animatable6.internalState.getValue(), BrightnessSliderMotionTestKeys.InactiveIconAlpha);
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl4.updateRememberedValue(objRememberedValue12);
                                        }
                                        composerImpl4.end(false);
                                        Dp.Companion companion3 = Dp.Companion;
                                        Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(MotionTestValueKt.motionTestValues(companion2, (Function1) objRememberedValue12), 40);
                                        composerImpl4.startReplaceGroup(195475316);
                                        int i20 = iIntValue & 14;
                                        boolean zChanged6 = (i20 == 4 || ((iIntValue & 8) != 0 && composerImpl4.changedInstance(sliderState2))) | composerImpl4.changed(j2) | composerImpl4.changedInstance(animatable2) | composerImpl4.changed(j3) | composerImpl4.changedInstance(animatable4);
                                        Object objRememberedValue13 = composerImpl4.rememberedValue();
                                        if (zChanged6 || objRememberedValue13 == obj7) {
                                            sliderState = sliderState2;
                                            final long j4 = j3;
                                            final Function4 function42 = function4;
                                            i19 = i20;
                                            final long j5 = j2;
                                            Object obj8 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSlider$6$$ExternalSyntheticLambda1
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj9) {
                                                    LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj9);
                                                    layoutNodeDrawScope.drawContent();
                                                    CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                                                    float f = 2;
                                                    float fIntBitsToFloat = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() & 4294967295L)) / f;
                                                    Dimensions.INSTANCE.getClass();
                                                    long j6 = Dimensions.IconSize;
                                                    float fIntBitsToFloat2 = fIntBitsToFloat - (Float.intBitsToFloat((int) (canvasDrawScope.mo59toSizeXkaWNTQ(j6) & 4294967295L)) / f);
                                                    float coercedValueAsFraction = sliderState.getCoercedValueAsFraction() * Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32));
                                                    float f2 = Dimensions.ThumbTrackGapSize;
                                                    float fMo58toPx0680j_4 = coercedValueAsFraction - layoutNodeDrawScope.mo58toPx0680j_4(f2);
                                                    float fMo58toPx0680j_42 = (layoutNodeDrawScope.mo58toPx0680j_4(f2) * f) + fMo58toPx0680j_4;
                                                    float fIntBitsToFloat3 = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32)) - fMo58toPx0680j_42;
                                                    float fIntBitsToFloat4 = Float.intBitsToFloat((int) (canvasDrawScope.mo59toSizeXkaWNTQ(j6) >> 32));
                                                    float f3 = Dimensions.IconPadding;
                                                    float fMo58toPx0680j_43 = (fMo58toPx0680j_4 - 0.0f) - (layoutNodeDrawScope.mo58toPx0680j_4(f3) * f);
                                                    MutableState mutableState2 = mutableState;
                                                    Function4 function43 = function42;
                                                    if (fIntBitsToFloat4 < fMo58toPx0680j_43) {
                                                        mutableState2.setValue(Boolean.TRUE);
                                                        function43.invoke(layoutNodeDrawScope, Offset.m395boximpl((Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(0.0f) << 32)), Color.m456boximpl(j5), animatable2.internalState.getValue());
                                                    } else if (Float.intBitsToFloat((int) (canvasDrawScope.mo59toSizeXkaWNTQ(j6) >> 32)) < fIntBitsToFloat3 - (layoutNodeDrawScope.mo58toPx0680j_4(f3) * f)) {
                                                        mutableState2.setValue(Boolean.FALSE);
                                                        function43.invoke(layoutNodeDrawScope, Offset.m395boximpl((Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(fMo58toPx0680j_42) << 32)), Color.m456boximpl(j4), animatable4.internalState.getValue());
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl4.updateRememberedValue(obj8);
                                            objRememberedValue13 = obj8;
                                        } else {
                                            i19 = i20;
                                            sliderState = sliderState2;
                                        }
                                        composerImpl4.end(false);
                                        Modifier modifierDrawWithContent = DrawModifierKt.drawWithContent(modifierM131height3ABfNKs, (Function1) objRememberedValue13);
                                        Dimensions.INSTANCE.getClass();
                                        sliderDefaults.m299TrackmnvyFg4(sliderState, Dimensions.SliderTrackRoundedCorner, modifierDrawWithContent, false, sliderColorsM291copyK518z4, null, null, Dimensions.ThumbTrackGapSize, 2, composerImpl4, 918749240 | i19, 72);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composerImpl), closedFloatRange, composerImpl, 907542528, 0, 128);
                        Function0 function06 = function04;
                        mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function06, composerImpl);
                        composerImpl.startReplaceGroup(-276733260);
                        zChanged5 = (i17 == i18) | composerImpl.changed(mutableStateRememberUpdatedState);
                        objRememberedValue6 = composerImpl.rememberedValue();
                        if (!zChanged5 || objRememberedValue6 == obj) {
                            objRememberedValue6 = new BrightnessSliderKt$BrightnessSlider$7$1(mutableInteractionSource3, z7, mutableStateRememberUpdatedState, null);
                            composerImpl.updateRememberedValue(objRememberedValue6);
                        }
                        composerImpl.end(false);
                        EffectsKt.LaunchedEffect(composerImpl, mutableInteractionSource3, (Function2) objRememberedValue6);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        function05 = function06;
                    } else {
                        function03 = function02;
                    }
                    objRememberedValue7 = SnapshotIntStateKt.mutableIntStateOf(i5);
                    composerImpl2.updateRememberedValue(objRememberedValue7);
                    mutableIntState = (MutableIntState) objRememberedValue7;
                    composerImpl2.end(false);
                    State stateAnimateFloatAsState2 = AnimateAsStateKt.animateFloatAsState(((SnapshotMutableIntStateImpl) mutableIntState).getIntValue(), null, "BrightnessSliderAnimatedValue", null, composerImpl2, 3072, 22);
                    closedFloatRange = new ClosedFloatRange(intRange.first, intRange.last);
                    boolean z82 = policyRestriction instanceof PolicyRestriction.Restricted;
                    z3 = !z82;
                    objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl2, -276977071, companion);
                    obj = Composer.Companion.Empty;
                    if (objM == obj) {
                    }
                    mutableInteractionSource = (MutableInteractionSource) objM;
                    composerImpl2.end(false);
                    composerImpl2.startReplaceGroup(-276973344);
                    composerImpl2.startReplaceGroup(-276970040);
                    if ((i13 & 112) == 32) {
                        zChanged = ((i13 & 112) == 32 || ((i13 & 64) != 0 && composerImpl2.changedInstance(factory))) | composerImpl2.changed(closedFloatRange);
                        objRememberedValue = composerImpl2.rememberedValue();
                        if (!zChanged) {
                            final int i152 = 0;
                            objRememberedValue = new Function0() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda1
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    switch (i152) {
                                        case 0:
                                            return ((SliderHapticsViewModel.Factory) factory).create((MutableInteractionSource) mutableInteractionSource, (ClosedFloatRange) closedFloatRange, Orientation.Horizontal, new SliderHapticFeedbackConfig(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 1.0f, 0, 0.0f, 0.0f, 0.0f, 0.0f, null, 32511, null), new SeekableSliderTrackerConfig(0L, 0.0f, 0.0f, 0.0f, 15, null));
                                        default:
                                            int intValue = ((SnapshotMutableIntStateImpl) ((MutableIntState) closedFloatRange)).getIntValue();
                                            int i162 = ((IntRange) factory).first;
                                            return Integer.valueOf(((Number) ((Function1) mutableInteractionSource).mo781invoke(Float.valueOf(((intValue - i162) * 100.0f) / (r2.last - i162)))).intValue());
                                    }
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue);
                            composerImpl2.end(false);
                            sliderHapticsViewModel = (SliderHapticsViewModel) SysUiViewModelKt.rememberViewModel("SliderHapticsViewModel", null, (Function0) objRememberedValue, composerImpl2, 6, 2);
                            composerImpl2.end(false);
                            composerImpl2.startReplaceGroup(-1949910451);
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            SliderDefaults.INSTANCE.getClass();
                            SliderColors sliderColorsColors2 = SliderDefaults.colors(composerImpl2);
                            long j4 = ((AndroidColorScheme) composerImpl2.consume(AndroidColorSchemeKt.LocalAndroidColorScheme)).surfaceEffect2;
                            MaterialTheme.INSTANCE.getClass();
                            final SliderColors sliderColorsM291copyK518z42 = sliderColorsColors2.m291copyK518z4(sliderColorsColors2.thumbColor, (1021 & 2) != 0 ? sliderColorsColors2.activeTrackColor : 0L, (1021 & 4) != 0 ? sliderColorsColors2.activeTickColor : MaterialTheme.getColorScheme(composerImpl2).onPrimary, (1021 & 8) != 0 ? sliderColorsColors2.inactiveTrackColor : j4, (1021 & 16) != 0 ? sliderColorsColors2.inactiveTickColor : MaterialTheme.getColorScheme(composerImpl2).onSurface, sliderColorsColors2.disabledThumbColor, sliderColorsColors2.disabledActiveTrackColor, sliderColorsColors2.disabledActiveTickColor, sliderColorsColors2.disabledInactiveTrackColor, sliderColorsColors2.disabledInactiveTickColor);
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            composerImpl2.end(false);
                            composerImpl2.startReplaceGroup(-276945077);
                            zChanged2 = (i14 != 4) | composerImpl2.changed(intRange);
                            objRememberedValue2 = composerImpl2.rememberedValue();
                            if (!zChanged2) {
                                final int i162 = 1;
                                objRememberedValue2 = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda1
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        switch (i162) {
                                            case 0:
                                                return ((SliderHapticsViewModel.Factory) intRange).create((MutableInteractionSource) function1, (ClosedFloatRange) mutableIntState, Orientation.Horizontal, new SliderHapticFeedbackConfig(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 1.0f, 0, 0.0f, 0.0f, 0.0f, 0.0f, null, 32511, null), new SeekableSliderTrackerConfig(0L, 0.0f, 0.0f, 0.0f, 15, null));
                                            default:
                                                int intValue = ((SnapshotMutableIntStateImpl) ((MutableIntState) mutableIntState)).getIntValue();
                                                int i1622 = ((IntRange) intRange).first;
                                                return Integer.valueOf(((Number) ((Function1) function1).mo781invoke(Float.valueOf(((intValue - i1622) * 100.0f) / (r2.last - i1622)))).intValue());
                                        }
                                    }
                                });
                                composerImpl2.updateRememberedValue(objRememberedValue2);
                                state = (State) objRememberedValue2;
                                composerImpl2.end(false);
                                context = (Context) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalContext);
                                Color.Companion.getClass();
                                ColorPainter colorPainter2 = new ColorPainter(Color.Transparent, null);
                                Integer numValueOf2 = Integer.valueOf(((Number) state.getValue()).intValue());
                                composerImpl2.startReplaceGroup(-276929638);
                                zChangedInstance = composerImpl2.changedInstance(function3) | composerImpl2.changed(state) | composerImpl2.changedInstance(context);
                                objRememberedValue3 = composerImpl2.rememberedValue();
                                if (!zChangedInstance) {
                                    objRememberedValue3 = new BrightnessSliderKt$BrightnessSlider$painter$2$1(function3, context, state, null);
                                    composerImpl2.updateRememberedValue(objRememberedValue3);
                                    composerImpl2.end(false);
                                    final State<? extends Painter> mutableStateProduceState2 = SnapshotStateKt.produceState(colorPainter2, numValueOf2, context, (Function2) objRememberedValue3, composerImpl2, 0);
                                    composerImpl2.startReplaceGroup(-276915607);
                                    objRememberedValue4 = composerImpl2.rememberedValue();
                                    if (objRememberedValue4 == obj) {
                                    }
                                    final Function4 function42 = (Function4) objRememberedValue4;
                                    composerImpl2.end(false);
                                    float fFloatValue2 = ((Number) stateAnimateFloatAsState2.getValue()).floatValue();
                                    Modifier modifierSysuiResTag2 = SysuiTestTagKt.sysuiResTag(modifier4, "slider");
                                    composerImpl2.startReplaceGroup(-276882783);
                                    if ((i8 & 57344) != 16384) {
                                        z4 = ((i8 & 458752) != 131072) | ((i8 & 57344) != 16384 || ((i8 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 && composerImpl2.changedInstance(policyRestriction)));
                                        objRememberedValue5 = composerImpl2.rememberedValue();
                                        if (!z4) {
                                            objRememberedValue5 = new BrightnessSliderKt$$ExternalSyntheticLambda3(0, policyRestriction, function12);
                                            composerImpl2.updateRememberedValue(objRememberedValue5);
                                            composerImpl2.end(false);
                                            Modifier modifierM35clickableXHw0xAI$default2 = ClickableKt.m35clickableXHw0xAI$default(modifierSysuiResTag2, z82, null, (Function0) objRememberedValue5, 6);
                                            composerImpl2.startReplaceGroup(-276901378);
                                            int i172 = i8 & 234881024;
                                            zChanged3 = ((i8 & 3670016) != 1048576) | composerImpl2.changed(z3) | (i172 != 67108864) | composerImpl2.changedInstance(sliderHapticsViewModel) | composerImpl2.changed(mutableIntState);
                                            Object objRememberedValue82 = composerImpl2.rememberedValue();
                                            if (zChanged3) {
                                                function04 = function03;
                                                modifier3 = modifier4;
                                                mutableInteractionSource2 = mutableInteractionSource;
                                                mutableIntState2 = mutableIntState;
                                                z5 = z3;
                                                z6 = false;
                                                i12 = 67108864;
                                                obj2 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda4
                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj4) {
                                                        float fFloatValue22 = ((Float) obj4).floatValue();
                                                        if (z5 && !z) {
                                                            SliderHapticsViewModel sliderHapticsViewModel2 = sliderHapticsViewModel;
                                                            if (sliderHapticsViewModel2 != null) {
                                                                sliderHapticsViewModel2.onValueChange(fFloatValue22);
                                                            }
                                                            SnapshotMutableIntStateImpl snapshotMutableIntStateImpl = (SnapshotMutableIntStateImpl) mutableIntState2;
                                                            snapshotMutableIntStateImpl.setIntValue((int) fFloatValue22);
                                                            function13.mo781invoke(Integer.valueOf(snapshotMutableIntStateImpl.getIntValue()));
                                                        }
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl2.updateRememberedValue(obj2);
                                                Function1 function152 = (Function1) obj2;
                                                composerImpl2.end(z6);
                                                composerImpl2.startReplaceGroup(-276892518);
                                                zChanged4 = composerImpl2.changed(z5) | (i172 != i12 ? true : z6) | composerImpl2.changedInstance(sliderHapticsViewModel) | ((i8 & 29360128) != 8388608 ? true : z6) | composerImpl2.changed(mutableIntState2);
                                                Object objRememberedValue92 = composerImpl2.rememberedValue();
                                                if (zChanged4) {
                                                    z7 = z;
                                                    obj3 = new Function0() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda5
                                                        @Override // kotlin.jvm.functions.Function0
                                                        public final Object invoke() {
                                                            if (z5 && !z7) {
                                                                SliderHapticsViewModel sliderHapticsViewModel2 = sliderHapticsViewModel;
                                                                if (sliderHapticsViewModel2 != null) {
                                                                    sliderHapticsViewModel2.onValueChangeEnded();
                                                                }
                                                                function14.mo781invoke(Integer.valueOf(((SnapshotMutableIntStateImpl) mutableIntState2).getIntValue()));
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    };
                                                    composerImpl2.updateRememberedValue(obj3);
                                                    composerImpl2.end(z6);
                                                    mutableInteractionSource3 = mutableInteractionSource2;
                                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(2013632220, new Function3() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt.BrightnessSlider.5
                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                                                        @Override // kotlin.jvm.functions.Function3
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj4, Object obj5, Object obj6) {
                                                            Composer composer2 = (Composer) obj5;
                                                            if ((((Number) obj6).intValue() & 17) == 16) {
                                                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                                if (composerImpl3.getSkipping()) {
                                                                    composerImpl3.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("com.android.systemui.brightness.ui.compose.BrightnessSlider.<anonymous> (BrightnessSlider.kt:211)");
                                                                    }
                                                                    SliderDefaults sliderDefaults = SliderDefaults.INSTANCE;
                                                                    Dp.Companion companion2 = Dp.Companion;
                                                                    sliderDefaults.m296Thumb9LiSoMs(mutableInteractionSource3, null, sliderColorsM291copyK518z42, z5, DpKt.m840DpSizeYgX7TsA(4, 52), composer2, 221190, 2);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl2);
                                                    int i182 = i12;
                                                    composerImpl = composerImpl2;
                                                    final long j22 = sliderColorsM291copyK518z42.activeTickColor;
                                                    final long j32 = sliderColorsM291copyK518z42.inactiveTickColor;
                                                    SliderKt.Slider(fFloatValue2, function152, modifierM35clickableXHw0xAI$default2, z5, (Function0) obj3, sliderColorsM291copyK518z42, mutableInteractionSource3, 0, composableLambdaImplRememberComposableLambda2, ComposableLambdaKt.rememberComposableLambda(-1329503237, new Function3() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt.BrightnessSlider.6
                                                        /* JADX WARN: Removed duplicated region for block: B:18:0x0046  */
                                                        @Override // kotlin.jvm.functions.Function3
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj4, Object obj5, Object obj6) {
                                                            final SliderState sliderState;
                                                            int i19;
                                                            SliderState sliderState2 = (SliderState) obj4;
                                                            Composer composer2 = (Composer) obj5;
                                                            int iIntValue = ((Number) obj6).intValue();
                                                            if ((iIntValue & 6) == 0) {
                                                                iIntValue |= (iIntValue & 8) == 0 ? ((ComposerImpl) composer2).changed(sliderState2) : ((ComposerImpl) composer2).changedInstance(sliderState2) ? 4 : 2;
                                                            }
                                                            if ((iIntValue & 19) == 18) {
                                                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                                if (composerImpl3.getSkipping()) {
                                                                    composerImpl3.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("com.android.systemui.brightness.ui.compose.BrightnessSlider.<anonymous> (BrightnessSlider.kt:219)");
                                                                    }
                                                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                                    composerImpl4.startReplaceGroup(195415466);
                                                                    Object objRememberedValue10 = composerImpl4.rememberedValue();
                                                                    Composer.Companion.getClass();
                                                                    Object obj7 = Composer.Companion.Empty;
                                                                    if (objRememberedValue10 == obj7) {
                                                                        objRememberedValue10 = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
                                                                        composerImpl4.updateRememberedValue(objRememberedValue10);
                                                                    }
                                                                    final MutableState mutableState = (MutableState) objRememberedValue10;
                                                                    Object objM3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl4, false, 195418144);
                                                                    if (objM3 == obj7) {
                                                                        Float fValueOf = Float.valueOf(1.0f);
                                                                        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
                                                                        Object animatable = new Animatable(fValueOf, VectorConvertersKt.FloatToVector, null, "iconActiveAlpha", 4, null);
                                                                        composerImpl4.updateRememberedValue(animatable);
                                                                        objM3 = animatable;
                                                                    }
                                                                    final Animatable animatable2 = (Animatable) objM3;
                                                                    Object objM4 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl4, false, 195426562);
                                                                    if (objM4 == obj7) {
                                                                        Float fValueOf2 = Float.valueOf(0.0f);
                                                                        FloatCompanionObject floatCompanionObject2 = FloatCompanionObject.INSTANCE;
                                                                        Object animatable3 = new Animatable(fValueOf2, VectorConvertersKt.FloatToVector, null, "iconInactiveAlpha", 4, null);
                                                                        composerImpl4.updateRememberedValue(animatable3);
                                                                        objM4 = animatable3;
                                                                    }
                                                                    final Animatable animatable4 = (Animatable) objM4;
                                                                    composerImpl4.end(false);
                                                                    Boolean bool = (Boolean) mutableState.getValue();
                                                                    bool.getClass();
                                                                    composerImpl4.startReplaceGroup(195436891);
                                                                    boolean zChangedInstance2 = composerImpl4.changedInstance(animatable2) | composerImpl4.changedInstance(animatable4);
                                                                    Object objRememberedValue11 = composerImpl4.rememberedValue();
                                                                    if (zChangedInstance2 || objRememberedValue11 == obj7) {
                                                                        objRememberedValue11 = new BrightnessSliderKt$BrightnessSlider$6$1$1(mutableState, animatable2, animatable4, null);
                                                                        composerImpl4.updateRememberedValue(objRememberedValue11);
                                                                    }
                                                                    composerImpl4.end(false);
                                                                    EffectsKt.LaunchedEffect(animatable2, animatable4, bool, (Function2) objRememberedValue11, composerImpl4);
                                                                    SliderDefaults sliderDefaults = SliderDefaults.INSTANCE;
                                                                    Modifier.Companion companion2 = Modifier.Companion;
                                                                    composerImpl4.startReplaceGroup(195453778);
                                                                    boolean zChangedInstance3 = composerImpl4.changedInstance(animatable2) | composerImpl4.changedInstance(animatable4);
                                                                    Object objRememberedValue12 = composerImpl4.rememberedValue();
                                                                    if (zChangedInstance3 || objRememberedValue12 == obj7) {
                                                                        objRememberedValue12 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSlider$6$$ExternalSyntheticLambda0
                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            /* renamed from: invoke */
                                                                            public final Object mo781invoke(Object obj8) {
                                                                                MotionTestValuesNode.AnonymousClass1 anonymousClass1 = (MotionTestValuesNode.AnonymousClass1) obj8;
                                                                                Animatable animatable5 = animatable2;
                                                                                boolean zIsRunning = animatable5.isRunning();
                                                                                Animatable animatable6 = animatable4;
                                                                                Boolean boolValueOf = Boolean.valueOf(zIsRunning || animatable6.isRunning());
                                                                                BrightnessSliderMotionTestKeys.INSTANCE.getClass();
                                                                                anonymousClass1.exportAs(boolValueOf, BrightnessSliderMotionTestKeys.AnimatingIcon);
                                                                                anonymousClass1.exportAs(animatable5.internalState.getValue(), BrightnessSliderMotionTestKeys.ActiveIconAlpha);
                                                                                anonymousClass1.exportAs(animatable6.internalState.getValue(), BrightnessSliderMotionTestKeys.InactiveIconAlpha);
                                                                                return Unit.INSTANCE;
                                                                            }
                                                                        };
                                                                        composerImpl4.updateRememberedValue(objRememberedValue12);
                                                                    }
                                                                    composerImpl4.end(false);
                                                                    Dp.Companion companion3 = Dp.Companion;
                                                                    Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(MotionTestValueKt.motionTestValues(companion2, (Function1) objRememberedValue12), 40);
                                                                    composerImpl4.startReplaceGroup(195475316);
                                                                    int i20 = iIntValue & 14;
                                                                    boolean zChanged6 = (i20 == 4 || ((iIntValue & 8) != 0 && composerImpl4.changedInstance(sliderState2))) | composerImpl4.changed(j22) | composerImpl4.changedInstance(animatable2) | composerImpl4.changed(j32) | composerImpl4.changedInstance(animatable4);
                                                                    Object objRememberedValue13 = composerImpl4.rememberedValue();
                                                                    if (zChanged6 || objRememberedValue13 == obj7) {
                                                                        sliderState = sliderState2;
                                                                        final long j42 = j32;
                                                                        final Function4 function422 = function42;
                                                                        i19 = i20;
                                                                        final long j5 = j22;
                                                                        Object obj8 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSlider$6$$ExternalSyntheticLambda1
                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            /* renamed from: invoke */
                                                                            public final Object mo781invoke(Object obj9) {
                                                                                LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj9);
                                                                                layoutNodeDrawScope.drawContent();
                                                                                CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                                                                                float f = 2;
                                                                                float fIntBitsToFloat = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() & 4294967295L)) / f;
                                                                                Dimensions.INSTANCE.getClass();
                                                                                long j6 = Dimensions.IconSize;
                                                                                float fIntBitsToFloat2 = fIntBitsToFloat - (Float.intBitsToFloat((int) (canvasDrawScope.mo59toSizeXkaWNTQ(j6) & 4294967295L)) / f);
                                                                                float coercedValueAsFraction = sliderState.getCoercedValueAsFraction() * Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32));
                                                                                float f2 = Dimensions.ThumbTrackGapSize;
                                                                                float fMo58toPx0680j_4 = coercedValueAsFraction - layoutNodeDrawScope.mo58toPx0680j_4(f2);
                                                                                float fMo58toPx0680j_42 = (layoutNodeDrawScope.mo58toPx0680j_4(f2) * f) + fMo58toPx0680j_4;
                                                                                float fIntBitsToFloat3 = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32)) - fMo58toPx0680j_42;
                                                                                float fIntBitsToFloat4 = Float.intBitsToFloat((int) (canvasDrawScope.mo59toSizeXkaWNTQ(j6) >> 32));
                                                                                float f3 = Dimensions.IconPadding;
                                                                                float fMo58toPx0680j_43 = (fMo58toPx0680j_4 - 0.0f) - (layoutNodeDrawScope.mo58toPx0680j_4(f3) * f);
                                                                                MutableState mutableState2 = mutableState;
                                                                                Function4 function43 = function422;
                                                                                if (fIntBitsToFloat4 < fMo58toPx0680j_43) {
                                                                                    mutableState2.setValue(Boolean.TRUE);
                                                                                    function43.invoke(layoutNodeDrawScope, Offset.m395boximpl((Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(0.0f) << 32)), Color.m456boximpl(j5), animatable2.internalState.getValue());
                                                                                } else if (Float.intBitsToFloat((int) (canvasDrawScope.mo59toSizeXkaWNTQ(j6) >> 32)) < fIntBitsToFloat3 - (layoutNodeDrawScope.mo58toPx0680j_4(f3) * f)) {
                                                                                    mutableState2.setValue(Boolean.FALSE);
                                                                                    function43.invoke(layoutNodeDrawScope, Offset.m395boximpl((Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(fMo58toPx0680j_42) << 32)), Color.m456boximpl(j42), animatable4.internalState.getValue());
                                                                                }
                                                                                return Unit.INSTANCE;
                                                                            }
                                                                        };
                                                                        composerImpl4.updateRememberedValue(obj8);
                                                                        objRememberedValue13 = obj8;
                                                                    } else {
                                                                        i19 = i20;
                                                                        sliderState = sliderState2;
                                                                    }
                                                                    composerImpl4.end(false);
                                                                    Modifier modifierDrawWithContent = DrawModifierKt.drawWithContent(modifierM131height3ABfNKs, (Function1) objRememberedValue13);
                                                                    Dimensions.INSTANCE.getClass();
                                                                    sliderDefaults.m299TrackmnvyFg4(sliderState, Dimensions.SliderTrackRoundedCorner, modifierDrawWithContent, false, sliderColorsM291copyK518z42, null, null, Dimensions.ThumbTrackGapSize, 2, composerImpl4, 918749240 | i19, 72);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl), closedFloatRange, composerImpl, 907542528, 0, 128);
                                                    Function0 function062 = function04;
                                                    mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function062, composerImpl);
                                                    composerImpl.startReplaceGroup(-276733260);
                                                    zChanged5 = (i172 == i182) | composerImpl.changed(mutableStateRememberUpdatedState);
                                                    objRememberedValue6 = composerImpl.rememberedValue();
                                                    if (!zChanged5) {
                                                        objRememberedValue6 = new BrightnessSliderKt$BrightnessSlider$7$1(mutableInteractionSource3, z7, mutableStateRememberUpdatedState, null);
                                                        composerImpl.updateRememberedValue(objRememberedValue6);
                                                        composerImpl.end(false);
                                                        EffectsKt.LaunchedEffect(composerImpl, mutableInteractionSource3, (Function2) objRememberedValue6);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                        }
                                                        function05 = function062;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final Modifier modifier5 = modifier3;
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda6
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj4, Object obj5) {
                            ((Integer) obj5).intValue();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i2 | 1);
                            int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i3);
                            BrightnessSliderKt.BrightnessSlider(i, intRange, function1, function3, policyRestriction, function12, function13, function14, z7, modifier5, function05, factory, (Composer) obj4, iUpdateChangedFlags, iUpdateChangedFlags2, i4);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i6 |= 12582912;
            if ((i4 & 256) != 0) {
            }
            i7 = i4 & 512;
            if (i7 != 0) {
            }
            i8 = i6;
            i9 = i4 & 1024;
            if (i9 != 0) {
            }
            if ((i4 & 2048) != 0) {
            }
            int i132 = i11;
            if ((i8 & 306783379) == 306783378) {
                if (i7 == 0) {
                }
                Composer.Companion companion2 = Composer.Companion;
                if (i10 == 0) {
                }
                if (ComposerKt.isTraceInProgress()) {
                }
                composerImpl2.startReplaceGroup(-276989440);
                int i142 = i8 & 14;
                if (i142 != 4) {
                }
                Object objRememberedValue72 = composerImpl2.rememberedValue();
                if (z2) {
                }
                objRememberedValue72 = SnapshotIntStateKt.mutableIntStateOf(i5);
                composerImpl2.updateRememberedValue(objRememberedValue72);
                mutableIntState = (MutableIntState) objRememberedValue72;
                composerImpl2.end(false);
                State stateAnimateFloatAsState22 = AnimateAsStateKt.animateFloatAsState(((SnapshotMutableIntStateImpl) mutableIntState).getIntValue(), null, "BrightnessSliderAnimatedValue", null, composerImpl2, 3072, 22);
                closedFloatRange = new ClosedFloatRange(intRange.first, intRange.last);
                boolean z822 = policyRestriction instanceof PolicyRestriction.Restricted;
                z3 = !z822;
                objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl2, -276977071, companion2);
                obj = Composer.Companion.Empty;
                if (objM == obj) {
                }
                mutableInteractionSource = (MutableInteractionSource) objM;
                composerImpl2.end(false);
                composerImpl2.startReplaceGroup(-276973344);
                composerImpl2.startReplaceGroup(-276970040);
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        if ((i4 & 128) != 0) {
        }
        if ((i4 & 256) != 0) {
        }
        i7 = i4 & 512;
        if (i7 != 0) {
        }
        i8 = i6;
        i9 = i4 & 1024;
        if (i9 != 0) {
        }
        if ((i4 & 2048) != 0) {
        }
        int i1322 = i11;
        if ((i8 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v11, types: [androidx.compose.ui.Modifier] */
    public static final void BrightnessSliderContainer(final BrightnessSliderViewModel brightnessSliderViewModel, final Modifier modifier, final ContainerColors containerColors, Composer composer, final int i) {
        KFunction kFunction;
        IntRange intRange;
        KFunction kFunction2;
        boolean z;
        Modifier.Companion companion;
        final MutableState mutableState;
        boolean z2;
        Object obj;
        ComposerImpl composerImpl;
        final int i2 = 0;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-2106303987);
        if (((i | (composerImpl2.changedInstance(brightnessSliderViewModel) ? 4 : 2) | (composerImpl2.changed(containerColors) ? 256 : 128)) & 147) == 146 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.brightness.ui.compose.BrightnessSliderContainer (BrightnessSlider.kt:332)");
            }
            int i3 = ((GammaBrightness) ((SnapshotMutableStateImpl) brightnessSliderViewModel.currentBrightness$delegate).getValue()).value;
            BrightnessSliderViewModel.Companion companion2 = BrightnessSliderViewModel.Companion;
            companion2.getClass();
            if (i3 == BrightnessSliderViewModel.initialValue) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final int i4 = 0;
                    recomposeScopeImplEndRestartGroup.block = new Function2(brightnessSliderViewModel, modifier, containerColors, i, i4) { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda7
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ BrightnessSliderViewModel f$0;
                        public final /* synthetic */ Modifier f$1;
                        public final /* synthetic */ ContainerColors f$2;

                        {
                            this.$r8$classId = i4;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            int i5 = this.$r8$classId;
                            Composer composer2 = (Composer) obj2;
                            ((Integer) obj3).getClass();
                            switch (i5) {
                                case 0:
                                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                                    BrightnessSliderKt.BrightnessSliderContainer(this.f$0, this.f$1, this.f$2, composer2, iUpdateChangedFlags);
                                    break;
                                default:
                                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(49);
                                    BrightnessSliderKt.BrightnessSliderContainer(this.f$0, this.f$1, this.f$2, composer2, iUpdateChangedFlags2);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            Context context = (Context) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalContext);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            Object obj2 = objRememberedValue;
            if (objRememberedValue == composer$Companion$Empty$1) {
                CoroutineScope coroutineScopeCreateCompositionCoroutineScope = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                composerImpl2.updateRememberedValue(coroutineScopeCreateCompositionCoroutineScope);
                obj2 = coroutineScopeCreateCompositionCoroutineScope;
            }
            final CoroutineScope coroutineScope = (CoroutineScope) obj2;
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(brightnessSliderViewModel.policyRestriction, PolicyRestriction.NoRestriction.INSTANCE, composerImpl2, 48);
            composerImpl2.startReplaceGroup(-777739249);
            MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(brightnessSliderViewModel.brightnessOverriddenByWindow, composerImpl2);
            composerImpl2.end(false);
            Unit unit = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(2053127495);
            boolean zChangedInstance = composerImpl2.changedInstance(brightnessSliderViewModel);
            Object objRememberedValue2 = composerImpl2.rememberedValue();
            Object obj3 = objRememberedValue2;
            if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                Function1 function1 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda8
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj4) {
                        switch (i2) {
                            case 0:
                                final BrightnessSliderViewModel brightnessSliderViewModel2 = brightnessSliderViewModel;
                                return new DisposableEffectResult() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSliderContainer$lambda$32$lambda$31$$inlined$onDispose$1
                                    @Override // androidx.compose.runtime.DisposableEffectResult
                                    public final void dispose() {
                                        brightnessSliderViewModel2.setIsDragging(false);
                                    }
                                };
                            default:
                                MotionEvent motionEvent = (MotionEvent) obj4;
                                if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
                                    brightnessSliderViewModel.falsingInteractor.manager.isFalseTouch(10);
                                }
                                return Boolean.FALSE;
                        }
                    }
                };
                composerImpl2.updateRememberedValue(function1);
                obj3 = function1;
            }
            composerImpl2.end(false);
            EffectsKt.DisposableEffect(unit, (Function1) obj3, composerImpl2);
            composerImpl2.startReplaceGroup(2053129721);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            Object obj4 = objRememberedValue3;
            if (objRememberedValue3 == composer$Companion$Empty$1) {
                MutableState mutableStateMutableStateOf$default = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl2.updateRememberedValue(mutableStateMutableStateOf$default);
                obj4 = mutableStateMutableStateOf$default;
            }
            MutableState mutableState2 = (MutableState) obj4;
            composerImpl2.end(false);
            State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(((Boolean) mutableState2.getValue()).booleanValue() ? containerColors.mirrorColor : containerColors.idleColor, null, null, composerImpl2, 0, 14);
            composerImpl2.startReplaceGroup(2053145543);
            Object objRememberedValue4 = composerImpl2.rememberedValue();
            Object obj5 = objRememberedValue4;
            if (objRememberedValue4 == composer$Companion$Empty$1) {
                BrightnessSliderKt$$ExternalSyntheticLambda9 brightnessSliderKt$$ExternalSyntheticLambda9 = new BrightnessSliderKt$$ExternalSyntheticLambda9();
                composerImpl2.updateRememberedValue(brightnessSliderKt$$ExternalSyntheticLambda9);
                obj5 = brightnessSliderKt$$ExternalSyntheticLambda9;
            }
            composerImpl2.end(false);
            Modifier modifierSysuiResTag = SysuiTestTagKt.sysuiResTag(SizeKt.fillMaxWidth(PaddingKt.padding$default(modifier, null, (Function1) obj5, 1), 1.0f), "brightness_slider");
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierSysuiResTag);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl2.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function0);
            } else {
                composerImpl2.useNode();
            }
            Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            IntRange intRange2 = new IntRange(0, brightnessSliderViewModel.maxBrightness);
            composerImpl2.startReplaceGroup(-734809435);
            Object objRememberedValue5 = composerImpl2.rememberedValue();
            Object obj6 = objRememberedValue5;
            if (objRememberedValue5 == composer$Companion$Empty$1) {
                BrightnessSliderKt$BrightnessSliderContainer$4$1$1 brightnessSliderKt$BrightnessSliderContainer$4$1$1 = new BrightnessSliderKt$BrightnessSliderContainer$4$1$1(companion2);
                composerImpl2.updateRememberedValue(brightnessSliderKt$BrightnessSliderContainer$4$1$1);
                obj6 = brightnessSliderKt$BrightnessSliderContainer$4$1$1;
            }
            KFunction kFunction3 = (KFunction) obj6;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(-734807062);
            boolean zChangedInstance2 = composerImpl2.changedInstance(brightnessSliderViewModel);
            Object objRememberedValue6 = composerImpl2.rememberedValue();
            Object obj7 = objRememberedValue6;
            if (zChangedInstance2 || objRememberedValue6 == composer$Companion$Empty$1) {
                BrightnessSliderKt$BrightnessSliderContainer$4$2$1 brightnessSliderKt$BrightnessSliderContainer$4$2$1 = new BrightnessSliderKt$BrightnessSliderContainer$4$2$1(brightnessSliderViewModel);
                composerImpl2.updateRememberedValue(brightnessSliderKt$BrightnessSliderContainer$4$2$1);
                obj7 = brightnessSliderKt$BrightnessSliderContainer$4$2$1;
            }
            KFunction kFunction4 = (KFunction) obj7;
            composerImpl2.end(false);
            PolicyRestriction policyRestriction = (PolicyRestriction) mutableStateCollectAsStateWithLifecycle.getValue();
            composerImpl2.startReplaceGroup(-734804068);
            boolean zChangedInstance3 = composerImpl2.changedInstance(brightnessSliderViewModel);
            Object objRememberedValue7 = composerImpl2.rememberedValue();
            Object obj8 = objRememberedValue7;
            if (zChangedInstance3 || objRememberedValue7 == composer$Companion$Empty$1) {
                BrightnessSliderKt$BrightnessSliderContainer$4$3$1 brightnessSliderKt$BrightnessSliderContainer$4$3$1 = new BrightnessSliderKt$BrightnessSliderContainer$4$3$1(brightnessSliderViewModel);
                composerImpl2.updateRememberedValue(brightnessSliderKt$BrightnessSliderContainer$4$3$1);
                obj8 = brightnessSliderKt$BrightnessSliderContainer$4$3$1;
            }
            KFunction kFunction5 = (KFunction) obj8;
            composerImpl2.end(false);
            Modifier.Companion companion3 = Modifier.Companion;
            MaterialTheme.INSTANCE.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl2).secondary;
            Dimensions.INSTANCE.getClass();
            Modifier modifierM2937borderOnFocusPOIbLQ4$default = BorderOnFocusKt.m2937borderOnFocusPOIbLQ4$default(companion3, j, CornerSizeKt.m186CornerSize0680j_4(Dimensions.SliderTrackRoundedCorner));
            composerImpl2.startReplaceGroup(-734781066);
            if (((Boolean) ((SnapshotMutableStateImpl) brightnessSliderViewModel.showMirror$delegate).getValue()).booleanValue()) {
                composerImpl2.startReplaceGroup(-294285675);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.compose.ui.graphics.drawInOverlay (DrawInOverlay.kt:42)");
                }
                composerImpl2.startReplaceGroup(-1255809229);
                Object objRememberedValue8 = composerImpl2.rememberedValue();
                Object obj9 = objRememberedValue8;
                if (objRememberedValue8 == composer$Companion$Empty$1) {
                    ContainerState containerState = new ContainerState();
                    composerImpl2.updateRememberedValue(containerState);
                    obj9 = containerState;
                }
                final ContainerState containerState2 = (ContainerState) obj9;
                composerImpl2.end(false);
                composerImpl2.startReplaceGroup(-1255807140);
                boolean zChangedInstance4 = composerImpl2.changedInstance(containerState2);
                kFunction = kFunction3;
                Object objRememberedValue9 = composerImpl2.rememberedValue();
                Object obj10 = objRememberedValue9;
                if (zChangedInstance4 || objRememberedValue9 == composer$Companion$Empty$1) {
                    Function1 function12 = new Function1() { // from class: com.android.compose.ui.graphics.DrawInOverlayKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj11) {
                            Modifier.Companion companion4 = Modifier.Companion;
                            ContainerElement containerElement = new ContainerElement(containerState2);
                            companion4.then(containerElement);
                            return containerElement;
                        }
                    };
                    composerImpl2.updateRememberedValue(function12);
                    obj10 = function12;
                }
                composerImpl2.end(false);
                intRange = intRange2;
                kFunction2 = kFunction4;
                DrawInOverlayKt.FullScreenComposeViewInOverlay(null, (Function1) obj10, composerImpl2, 0, 1);
                composerImpl2.startReplaceGroup(-1255804066);
                Object objRememberedValue10 = composerImpl2.rememberedValue();
                Object obj11 = objRememberedValue10;
                if (objRememberedValue10 == composer$Companion$Empty$1) {
                    DrawInContainerNode$$ExternalSyntheticLambda1 drawInContainerNode$$ExternalSyntheticLambda1 = new DrawInContainerNode$$ExternalSyntheticLambda1();
                    composerImpl2.updateRememberedValue(drawInContainerNode$$ExternalSyntheticLambda1);
                    obj11 = drawInContainerNode$$ExternalSyntheticLambda1;
                }
                z = false;
                composerImpl2.end(false);
                ?? DrawInContainer$default = DrawInContainerKt.drawInContainer$default(companion3, containerState2, (Function0) obj11);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
                companion = DrawInContainer$default;
            } else {
                kFunction = kFunction3;
                intRange = intRange2;
                kFunction2 = kFunction4;
                z = false;
                companion = companion3;
            }
            composerImpl2.end(z);
            Modifier modifierThen = modifierM2937borderOnFocusPOIbLQ4$default.then(companion);
            final long j2 = ((Color) stateM7animateColorAsStateeuL9pac.getValue()).value;
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(DrawModifierKt.drawWithCache(modifierThen, new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda15
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj12) {
                    CacheDrawScope cacheDrawScope = (CacheDrawScope) obj12;
                    Dimensions.INSTANCE.getClass();
                    long jMo59toSizeXkaWNTQ = cacheDrawScope.mo59toSizeXkaWNTQ(Dimensions.SliderBackgroundFrameSize);
                    float f = 2;
                    int i5 = (int) (jMo59toSizeXkaWNTQ >> 32);
                    float fIntBitsToFloat = (Float.intBitsToFloat(i5) * f) + Float.intBitsToFloat((int) (cacheDrawScope.cacheParams.mo361getSizeNHjbRc() >> 32));
                    int i6 = (int) (jMo59toSizeXkaWNTQ & 4294967295L);
                    float fIntBitsToFloat2 = (Float.intBitsToFloat(i6) * f) + Float.intBitsToFloat((int) (cacheDrawScope.cacheParams.mo361getSizeNHjbRc() & 4294967295L));
                    final long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L);
                    Size.Companion companion4 = Size.Companion;
                    float f2 = -Float.intBitsToFloat(i5);
                    float f3 = -Float.intBitsToFloat(i6);
                    final long jFloatToRawIntBits2 = (Float.floatToRawIntBits(f2) << 32) | (Float.floatToRawIntBits(f3) & 4294967295L);
                    Offset.Companion companion5 = Offset.Companion;
                    float density = cacheDrawScope.getDensity() * Dimensions.SliderBackgroundRoundedCorner;
                    final long jFloatToRawIntBits3 = (Float.floatToRawIntBits(density) << 32) | (Float.floatToRawIntBits(density) & 4294967295L);
                    CornerRadius.Companion companion6 = CornerRadius.Companion;
                    final long j3 = j2;
                    return cacheDrawScope.onDrawBehind(new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda16
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj13) {
                            DrawScope.m543drawRoundRectuAw5IA$default((DrawScope) obj13, j3, jFloatToRawIntBits2, jFloatToRawIntBits, jFloatToRawIntBits3, null, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
                            return Unit.INSTANCE;
                        }
                    });
                }
            }), 1.0f);
            composerImpl2.startReplaceGroup(-734774439);
            boolean zChangedInstance5 = composerImpl2.changedInstance(brightnessSliderViewModel);
            Object objRememberedValue11 = composerImpl2.rememberedValue();
            Object obj12 = objRememberedValue11;
            if (zChangedInstance5 || objRememberedValue11 == composer$Companion$Empty$1) {
                final int i5 = 1;
                Function1 function13 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda8
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj42) {
                        switch (i5) {
                            case 0:
                                final BrightnessSliderViewModel brightnessSliderViewModel2 = brightnessSliderViewModel;
                                return new DisposableEffectResult() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSliderContainer$lambda$32$lambda$31$$inlined$onDispose$1
                                    @Override // androidx.compose.runtime.DisposableEffectResult
                                    public final void dispose() {
                                        brightnessSliderViewModel2.setIsDragging(false);
                                    }
                                };
                            default:
                                MotionEvent motionEvent = (MotionEvent) obj42;
                                if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
                                    brightnessSliderViewModel.falsingInteractor.manager.isFalseTouch(10);
                                }
                                return Boolean.FALSE;
                        }
                    }
                };
                composerImpl2.updateRememberedValue(function13);
                obj12 = function13;
            }
            composerImpl2.end(false);
            Modifier modifierPointerInteropFilter$default = PointerInteropFilter_androidKt.pointerInteropFilter$default(modifierFillMaxWidth, (Function1) obj12);
            boolean zBooleanValue = ((Boolean) mutableStateCollectAsStateWithLifecycle2.getValue()).booleanValue();
            Function1 function14 = (Function1) kFunction;
            Function3 function3 = (Function3) kFunction2;
            Function1 function15 = (Function1) kFunction5;
            composerImpl2.startReplaceGroup(-734801966);
            boolean zChangedInstance6 = composerImpl2.changedInstance(brightnessSliderViewModel) | composerImpl2.changedInstance(coroutineScope);
            Object objRememberedValue12 = composerImpl2.rememberedValue();
            if (zChangedInstance6 || objRememberedValue12 == composer$Companion$Empty$1) {
                mutableState = mutableState2;
                z2 = false;
                final boolean z3 = false ? 1 : 0;
                Function1 function16 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda11
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj13) {
                        int i6 = z3;
                        int iIntValue = ((Integer) obj13).intValue();
                        switch (i6) {
                            case 0:
                                BrightnessSliderViewModel brightnessSliderViewModel2 = brightnessSliderViewModel;
                                brightnessSliderViewModel2.setIsDragging(true);
                                mutableState.setValue(Boolean.TRUE);
                                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new BrightnessSliderKt$BrightnessSliderContainer$4$5$1$1(brightnessSliderViewModel2, iIntValue, null), 7);
                                break;
                            default:
                                BrightnessSliderViewModel brightnessSliderViewModel3 = brightnessSliderViewModel;
                                brightnessSliderViewModel3.setIsDragging(false);
                                mutableState.setValue(Boolean.FALSE);
                                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new BrightnessSliderKt$BrightnessSliderContainer$4$6$1$1(brightnessSliderViewModel3, iIntValue, null), 7);
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(function16);
                obj = function16;
            } else {
                mutableState = mutableState2;
                z2 = false;
                obj = objRememberedValue12;
            }
            Function1 function17 = (Function1) obj;
            composerImpl2.end(z2);
            composerImpl2.startReplaceGroup(-734795213);
            boolean zChangedInstance7 = composerImpl2.changedInstance(brightnessSliderViewModel) | composerImpl2.changedInstance(coroutineScope);
            Object objRememberedValue13 = composerImpl2.rememberedValue();
            Object obj13 = objRememberedValue13;
            if (zChangedInstance7 || objRememberedValue13 == composer$Companion$Empty$1) {
                final int i6 = 1;
                Function1 function18 = new Function1() { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda11
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj132) {
                        int i62 = i6;
                        int iIntValue = ((Integer) obj132).intValue();
                        switch (i62) {
                            case 0:
                                BrightnessSliderViewModel brightnessSliderViewModel2 = brightnessSliderViewModel;
                                brightnessSliderViewModel2.setIsDragging(true);
                                mutableState.setValue(Boolean.TRUE);
                                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new BrightnessSliderKt$BrightnessSliderContainer$4$5$1$1(brightnessSliderViewModel2, iIntValue, null), 7);
                                break;
                            default:
                                BrightnessSliderViewModel brightnessSliderViewModel3 = brightnessSliderViewModel;
                                brightnessSliderViewModel3.setIsDragging(false);
                                mutableState.setValue(Boolean.FALSE);
                                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new BrightnessSliderKt$BrightnessSliderContainer$4$6$1$1(brightnessSliderViewModel3, iIntValue, null), 7);
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(function18);
                obj13 = function18;
            }
            Function1 function19 = (Function1) obj13;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(-734758328);
            boolean zChangedInstance8 = composerImpl2.changedInstance(brightnessSliderViewModel) | composerImpl2.changedInstance(context);
            Object objRememberedValue14 = composerImpl2.rememberedValue();
            Object obj14 = objRememberedValue14;
            if (zChangedInstance8 || objRememberedValue14 == composer$Companion$Empty$1) {
                BrightnessSliderKt$$ExternalSyntheticLambda3 brightnessSliderKt$$ExternalSyntheticLambda3 = new BrightnessSliderKt$$ExternalSyntheticLambda3(1, brightnessSliderViewModel, context);
                composerImpl2.updateRememberedValue(brightnessSliderKt$$ExternalSyntheticLambda3);
                obj14 = brightnessSliderKt$$ExternalSyntheticLambda3;
            }
            composerImpl2.end(false);
            BrightnessSlider(i3, intRange, function14, function3, policyRestriction, function15, function17, function19, zBooleanValue, modifierPointerInteropFilter$default, (Function0) obj14, brightnessSliderViewModel.hapticsViewModelFactory, composerImpl2, 0, 0, 0);
            composerImpl = composerImpl2;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            final int i7 = 1;
            recomposeScopeImplEndRestartGroup2.block = new Function2(brightnessSliderViewModel, modifier, containerColors, i, i7) { // from class: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$$ExternalSyntheticLambda7
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ BrightnessSliderViewModel f$0;
                public final /* synthetic */ Modifier f$1;
                public final /* synthetic */ ContainerColors f$2;

                {
                    this.$r8$classId = i7;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj22, Object obj32) {
                    int i52 = this.$r8$classId;
                    Composer composer2 = (Composer) obj22;
                    ((Integer) obj32).getClass();
                    switch (i52) {
                        case 0:
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                            BrightnessSliderKt.BrightnessSliderContainer(this.f$0, this.f$1, this.f$2, composer2, iUpdateChangedFlags);
                            break;
                        default:
                            int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(49);
                            BrightnessSliderKt.BrightnessSliderContainer(this.f$0, this.f$1, this.f$2, composer2, iUpdateChangedFlags2);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
