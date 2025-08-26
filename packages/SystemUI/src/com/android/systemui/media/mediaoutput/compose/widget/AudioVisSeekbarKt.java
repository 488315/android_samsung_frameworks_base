package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
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
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.wrapper.SecHapticFeedbackType;
import com.android.systemui.media.mediaoutput.wrapper.SecPlatformHapticFeedback;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.ranges.ClosedFloatRange;

/* loaded from: classes2.dex */
public abstract class AudioVisSeekbarKt {
    public static final void AudioVisSeekbar(final float f, final Function1 function1, final Modifier modifier, final boolean z, final Function0 function0, final AudioVisSeekbarColors audioVisSeekbarColors, MutableInteractionSource mutableInteractionSource, ClosedFloatRange closedFloatRange, final WaveAnimationOptions waveAnimationOptions, WaveOptions waveOptions, boolean z2, Composer composer, final int i) {
        boolean z3;
        MutableInteractionSource mutableInteractionSource2;
        int i2;
        WaveOptions waveOptions2;
        final ClosedFloatRange closedFloatRange2;
        WaveOptions waveOptions3;
        Object obj;
        MutableState mutableState;
        final SecPlatformHapticFeedback secPlatformHapticFeedback;
        boolean z4;
        Continuation continuation;
        final MutableInteractionSource mutableInteractionSource3;
        ComposerImpl composerImpl;
        final ClosedFloatRange closedFloatRange3;
        final boolean z5;
        final WaveOptions waveOptions4;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1707449716);
        float fFloatValue = f;
        int i3 = i | (composerImpl2.changed(fFloatValue) ? 4 : 2) | (composerImpl2.changedInstance(function1) ? 32 : 16) | (composerImpl2.changed(z) ? 2048 : 1024) | (composerImpl2.changedInstance(function0) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | (composerImpl2.changed(audioVisSeekbarColors) ? 131072 : 65536) | 47710208 | (composerImpl2.changed(waveAnimationOptions) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456);
        if ((306783379 & i3) == 306783378 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            mutableInteractionSource3 = mutableInteractionSource;
            closedFloatRange3 = closedFloatRange;
            waveOptions4 = waveOptions;
            composerImpl = composerImpl2;
            z5 = z2;
        } else {
            composerImpl2.startDefaults();
            int i4 = i & 1;
            Composer.Companion companion = Composer.Companion;
            if (i4 == 0 || composerImpl2.getDefaultsInvalid()) {
                composerImpl2.startReplaceGroup(-1847107364);
                Object objRememberedValue = composerImpl2.rememberedValue();
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                    composerImpl2.updateRememberedValue(objRememberedValue);
                }
                MutableInteractionSource mutableInteractionSource4 = (MutableInteractionSource) objRememberedValue;
                composerImpl2.end(false);
                ClosedFloatRange closedFloatRange4 = new ClosedFloatRange(0.0f, 1.0f);
                int i5 = i3 & (-234881025);
                AudioVisSeekbarDefaults.INSTANCE.getClass();
                composerImpl2.startReplaceGroup(1290994960);
                float f2 = 8;
                Dp.Companion companion2 = Dp.Companion;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults.waveOptions (AudioVisSeekbar.kt:364)");
                }
                WaveOptions waveOptions5 = new WaveOptions(AudioVisSeekbarDefaults.m2633toPx8Feqmps(f2, composerImpl2), 6.2831855f, AudioVisSeekbarDefaults.m2633toPx8Feqmps(f2, composerImpl2));
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
                z3 = z;
                mutableInteractionSource2 = mutableInteractionSource4;
                i2 = i5;
                waveOptions2 = waveOptions5;
                closedFloatRange2 = closedFloatRange4;
            } else {
                composerImpl2.skipToGroupEnd();
                mutableInteractionSource2 = mutableInteractionSource;
                closedFloatRange2 = closedFloatRange;
                z3 = z2;
                i2 = i3 & (-234881025);
                waveOptions2 = waveOptions;
            }
            composerImpl2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbar (AudioVisSeekbar.kt:75)");
            }
            SecPlatformHapticFeedback secPlatformHapticFeedback2 = (SecPlatformHapticFeedback) composerImpl2.consume(CompositionExtKt.LocalSecHapticFeedback);
            composerImpl2.startReplaceGroup(-1847093865);
            Object objRememberedValue2 = composerImpl2.rememberedValue();
            companion.getClass();
            Object obj2 = Composer.Companion.Empty;
            if (objRememberedValue2 == obj2) {
                objRememberedValue2 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl2.updateRememberedValue(objRememberedValue2);
            }
            MutableState mutableState2 = (MutableState) objRememberedValue2;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(-1847091811);
            int i6 = i2;
            if (((Boolean) mutableState2.getValue()).booleanValue()) {
                waveOptions3 = waveOptions2;
                obj = obj2;
                mutableState = mutableState2;
                secPlatformHapticFeedback = secPlatformHapticFeedback2;
                z4 = z3;
                continuation = null;
                fFloatValue = ((Number) AnimateAsStateKt.animateFloatAsState(f, AnimationSpecKt.spring$default(1.0f, 450.0f, null, 4), null, null, composerImpl2, (i6 & 14) | 48, 28).getValue()).floatValue();
            } else {
                waveOptions3 = waveOptions2;
                z4 = z3;
                continuation = null;
                secPlatformHapticFeedback = secPlatformHapticFeedback2;
                obj = obj2;
                mutableState = mutableState2;
            }
            float f3 = fFloatValue;
            composerImpl2.end(false);
            Unit unit = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(-1847085717);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            if (objRememberedValue3 == obj) {
                objRememberedValue3 = new AudioVisSeekbarKt$AudioVisSeekbar$2$1(mutableState, continuation);
                composerImpl2.updateRememberedValue(objRememberedValue3);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, unit, (Function2) objRememberedValue3);
            composerImpl2.startReplaceGroup(-1847084265);
            Object objRememberedValue4 = composerImpl2.rememberedValue();
            if (objRememberedValue4 == obj) {
                objRememberedValue4 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl2.updateRememberedValue(objRememberedValue4);
            }
            final MutableState mutableState3 = (MutableState) objRememberedValue4;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(-1847081523);
            int i7 = i6 & 57344;
            boolean z6 = i7 == 16384;
            Object objRememberedValue5 = composerImpl2.rememberedValue();
            if (z6 || objRememberedValue5 == obj) {
                objRememberedValue5 = new AudioVisSeekbarKt$AudioVisSeekbar$3$1(mutableInteractionSource2, mutableState3, function0, continuation);
                composerImpl2.updateRememberedValue(objRememberedValue5);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, mutableInteractionSource2, (Function2) objRememberedValue5);
            composerImpl2.startReplaceGroup(-1847065453);
            boolean zChanged = ((i6 & 112) == 32) | composerImpl2.changed(closedFloatRange2) | composerImpl2.changedInstance(secPlatformHapticFeedback);
            Object objRememberedValue6 = composerImpl2.rememberedValue();
            if (zChanged || objRememberedValue6 == obj) {
                objRememberedValue6 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj3) {
                        SecPlatformHapticFeedback secPlatformHapticFeedback3;
                        Float f4 = (Float) obj3;
                        float fFloatValue2 = f4.floatValue();
                        function1.mo781invoke(f4);
                        ClosedFloatRange closedFloatRange5 = closedFloatRange2;
                        if ((fFloatValue2 == closedFloatRange5._start || fFloatValue2 == closedFloatRange5._endInclusive) && (secPlatformHapticFeedback3 = secPlatformHapticFeedback) != null) {
                            secPlatformHapticFeedback3.performHapticFeedback(SecHapticFeedbackType.Seekbar);
                        }
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(objRememberedValue6);
            }
            Function1 function12 = (Function1) objRememberedValue6;
            composerImpl2.end(false);
            final boolean z7 = z4;
            final WaveOptions waveOptions6 = waveOptions3;
            MutableInteractionSource mutableInteractionSource5 = mutableInteractionSource2;
            ClosedFloatRange closedFloatRange5 = closedFloatRange2;
            SliderKt.Slider(f3, function12, modifier, z, function0, null, mutableInteractionSource5, 0, ComposableLambdaKt.rememberComposableLambda(-1223196232, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarKt.AudioVisSeekbar.5
                /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj3, Object obj4, Object obj5) {
                    Composer composer2 = (Composer) obj4;
                    if ((((Number) obj5).intValue() & 17) == 16) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        if (composerImpl3.getSkipping()) {
                            composerImpl3.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbar.<anonymous> (AudioVisSeekbar.kt:119)");
                            }
                            if (z7) {
                                AudioVisSeekbarDefaults.INSTANCE.m2634ThumbFJfuzF0(mutableState3, null, audioVisSeekbarColors, z, 0.0f, composer2, 196614);
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2), ComposableLambdaKt.rememberComposableLambda(-352374633, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarKt.AudioVisSeekbar.6
                /* JADX WARN: Removed duplicated region for block: B:18:0x003d  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj3, Object obj4, Object obj5) {
                    SliderState sliderState = (SliderState) obj3;
                    Composer composer2 = (Composer) obj4;
                    int iIntValue = ((Number) obj5).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= (iIntValue & 8) == 0 ? ((ComposerImpl) composer2).changed(sliderState) : ((ComposerImpl) composer2).changedInstance(sliderState) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        if (composerImpl3.getSkipping()) {
                            composerImpl3.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbar.<anonymous> (AudioVisSeekbar.kt:128)");
                            }
                            AudioVisSeekbarDefaults.INSTANCE.Track(sliderState, mutableState3, null, z, audioVisSeekbarColors, waveAnimationOptions, waveOptions6, composer2, 12582968 | (iIntValue & 14));
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2), closedFloatRange5, composerImpl2, (i6 & 7168) | 905970048 | i7 | 14155776, 0, 32);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            mutableInteractionSource3 = mutableInteractionSource5;
            composerImpl = composerImpl2;
            closedFloatRange3 = closedFloatRange5;
            z5 = z4;
            waveOptions4 = waveOptions6;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(f, function1, modifier, z, function0, audioVisSeekbarColors, mutableInteractionSource3, closedFloatRange3, waveAnimationOptions, waveOptions4, z5, i) { // from class: com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarKt$$ExternalSyntheticLambda1
                public final /* synthetic */ float f$0;
                public final /* synthetic */ Function1 f$1;
                public final /* synthetic */ WaveOptions f$10;
                public final /* synthetic */ boolean f$11;
                public final /* synthetic */ Modifier f$2;
                public final /* synthetic */ boolean f$3;
                public final /* synthetic */ Function0 f$4;
                public final /* synthetic */ AudioVisSeekbarColors f$5;
                public final /* synthetic */ MutableInteractionSource f$6;
                public final /* synthetic */ ClosedFloatRange f$8;
                public final /* synthetic */ WaveAnimationOptions f$9;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj3, Object obj4) {
                    ((Integer) obj4).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(385);
                    AudioVisSeekbarColors audioVisSeekbarColors2 = this.f$5;
                    WaveAnimationOptions waveAnimationOptions2 = this.f$9;
                    WaveOptions waveOptions7 = this.f$10;
                    boolean z8 = this.f$11;
                    AudioVisSeekbarKt.AudioVisSeekbar(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, audioVisSeekbarColors2, this.f$6, this.f$8, waveAnimationOptions2, waveOptions7, z8, (Composer) obj3, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
