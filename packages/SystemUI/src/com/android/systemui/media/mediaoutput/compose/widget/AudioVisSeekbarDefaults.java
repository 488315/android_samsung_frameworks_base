package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.InfiniteRepeatableSpec;
import androidx.compose.animation.core.InfiniteTransition;
import androidx.compose.animation.core.InfiniteTransitionKt;
import androidx.compose.animation.core.KeyframesSpec;
import androidx.compose.animation.core.RepeatMode;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.SliderState;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.Unit;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AudioVisSeekbarDefaults {
    public static final AudioVisSeekbarDefaults INSTANCE = new AudioVisSeekbarDefaults();

    private AudioVisSeekbarDefaults() {
    }

    /* renamed from: drawTrack-Imwu3XQ, reason: not valid java name */
    public static void m2615drawTrackImwu3XQ(DrawScope drawScope, int i, float f, float f2, float f3, float f4, double d, float f5, float f6, float f7, long j, boolean z) {
        IntProgression intRange;
        float f8 = (i + 1) * f3;
        float f9 = f5 / 2;
        AndroidPath Path = AndroidPath_androidKt.Path();
        float f10 = -f9;
        Path.internalPath.moveTo(f, f10);
        if (z) {
            IntProgression.Companion.getClass();
            intRange = new IntProgression((int) f, (int) f2, -1);
        } else {
            intRange = new IntRange((int) f, (int) f2);
        }
        int i2 = intRange.first;
        int i3 = intRange.last;
        int i4 = intRange.step <= 0 ? -1 : 1;
        int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(i2, i3, i4);
        if ((i4 > 0 && i2 <= progressionLastElement) || (i4 < 0 && progressionLastElement <= i2)) {
            while (true) {
                Path.internalPath.lineTo(i2, (((((float) Math.sin((f4 * r9) + f8)) * f6) - f7) * ((float) Math.sin(Math.abs(i2 - intRange.first) * d))) - f9);
                if (i2 == progressionLastElement) {
                    break;
                } else {
                    i2 += i4;
                }
            }
        }
        Path.internalPath.lineTo(f2, f10);
        Path.internalPath.close();
        DrawScope.m537drawPathLG529CI$default(drawScope, Path, j, 60);
        Offset.Companion companion = Offset.Companion;
        StrokeCap.Companion.getClass();
        DrawScope.m535drawLineNGM6Ib0$default(drawScope, j, (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L), (Float.floatToRawIntBits(f2) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L), f5, StrokeCap.Round, 0.0f, VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE);
    }

    /* renamed from: toPx-8Feqmps, reason: not valid java name */
    public static float m2616toPx8Feqmps(float f, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(194299629);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults.toPx (AudioVisSeekbar.kt:371)");
        }
        float mo57toPx0680j_4 = ((Density) composerImpl.consume(CompositionLocalsKt.LocalDensity)).mo57toPx0680j_4(f);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mo57toPx0680j_4;
    }

    /* renamed from: Thumb-FJfuzF0, reason: not valid java name */
    public final void m2617ThumbFJfuzF0(State state, Modifier modifier, final AudioVisSeekbarColors audioVisSeekbarColors, final boolean z, float f, Composer composer, final int i) {
        Modifier modifier2;
        float f2;
        State state2;
        final Modifier modifier3;
        final float f3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(821471169);
        if (((i | 48 | (composerImpl.changed(audioVisSeekbarColors) ? 256 : 128) | (composerImpl.changed(z) ? 2048 : 1024) | 24576) & 74899) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            state2 = state;
            modifier3 = modifier;
            f3 = f;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                modifier2 = Modifier.Companion;
                f2 = 16;
                Dp.Companion companion = Dp.Companion;
            } else {
                composerImpl.skipToGroupEnd();
                modifier2 = modifier;
                f2 = f;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults.Thumb (AudioVisSeekbar.kt:175)");
            }
            composerImpl.startReplaceGroup(-351367963);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                state2 = state;
                composerImpl.updateRememberedValue(state2);
                rememberedValue = state2;
            } else {
                state2 = state;
            }
            State state3 = (State) rememberedValue;
            composerImpl.end(false);
            Modifier m139size3ABfNKs = SizeKt.m139size3ABfNKs(modifier2, f2);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m139size3ABfNKs);
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
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            AnimatedVisibilityKt.AnimatedVisibility(!((Boolean) state3.getValue()).booleanValue(), null, EnterExitTransitionKt.fadeIn$default(null, 3).plus(EnterExitTransitionKt.m5scaleInL8ZKhE$default(null, 0.0f, 7)), EnterExitTransitionKt.m6scaleOutL8ZKhE$default(null, 0.0f, 7).plus(EnterExitTransitionKt.fadeOut$default(null, 3)), null, ComposableLambdaKt.rememberComposableLambda(-558027101, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults$Thumb$1$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults.Thumb.<anonymous>.<anonymous> (AudioVisSeekbar.kt:184)");
                    }
                    Modifier clip = ClipKt.clip(SizeKt.fillMaxSize(Modifier.Companion, 1.0f), RoundedCornerShapeKt.CircleShape);
                    boolean z2 = z;
                    AudioVisSeekbarColors audioVisSeekbarColors2 = AudioVisSeekbarColors.this;
                    SpacerKt.Spacer(composer2, BackgroundKt.m26backgroundbw27NRU(clip, z2 ? audioVisSeekbarColors2.thumbColor : audioVisSeekbarColors2.disabledThumbColor, RectangleShapeKt.RectangleShape));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 200064, 18);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier3 = modifier2;
            f3 = f2;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final State state4 = state2;
            endRestartGroup.block = new Function2(state4, modifier3, audioVisSeekbarColors, z, f3, i) { // from class: com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults$$ExternalSyntheticLambda5
                public final /* synthetic */ State f$1;
                public final /* synthetic */ Modifier f$2;
                public final /* synthetic */ AudioVisSeekbarColors f$3;
                public final /* synthetic */ boolean f$4;
                public final /* synthetic */ float f$5;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    AudioVisSeekbarDefaults audioVisSeekbarDefaults = AudioVisSeekbarDefaults.INSTANCE;
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(196615);
                    AudioVisSeekbarDefaults.this.m2617ThumbFJfuzF0(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, composer2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8, types: [boolean, int] */
    public final void Track(final SliderState sliderState, final State state, Modifier.Companion companion, final boolean z, final AudioVisSeekbarColors audioVisSeekbarColors, final WaveAnimationOptions waveAnimationOptions, final WaveOptions waveOptions, Composer composer, final int i) {
        int i2;
        final boolean z2;
        Modifier.Companion companion2;
        Modifier.Companion companion3;
        float f;
        Modifier modifier;
        ComposerImpl composerImpl;
        final Modifier.Companion companion4;
        ?? r7;
        ComposerImpl composerImpl2;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(-1140396952);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl3.changed(sliderState) : composerImpl3.changedInstance(sliderState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl3.changed(state) ? 32 : 16;
        }
        int i3 = i2 | 384;
        if ((i & 3072) == 0) {
            z2 = z;
            i3 |= composerImpl3.changed(z2) ? 2048 : 1024;
        } else {
            z2 = z;
        }
        if ((i & 24576) == 0) {
            i3 |= composerImpl3.changed(audioVisSeekbarColors) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i3 |= composerImpl3.changed(waveAnimationOptions) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i3 |= composerImpl3.changed(waveOptions) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((12582912 & i) == 0) {
            i3 |= composerImpl3.changed(this) ? 8388608 : 4194304;
        }
        if ((i3 & 4793491) == 4793490 && composerImpl3.getSkipping()) {
            composerImpl3.skipToGroupEnd();
            companion4 = companion;
            composerImpl2 = composerImpl3;
        } else {
            composerImpl3.startDefaults();
            if ((i & 1) == 0 || composerImpl3.getDefaultsInvalid()) {
                companion2 = Modifier.Companion;
            } else {
                composerImpl3.skipToGroupEnd();
                companion2 = companion;
            }
            composerImpl3.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults.Track (AudioVisSeekbar.kt:204)");
            }
            boolean z3 = composerImpl3.consume(CompositionLocalsKt.LocalLayoutDirection) == LayoutDirection.Rtl;
            composerImpl3.startReplaceGroup(-83803782);
            Object rememberedValue = composerImpl3.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                composerImpl3.updateRememberedValue(state);
                rememberedValue = state;
            }
            State state2 = (State) rememberedValue;
            composerImpl3.end(false);
            final float f2 = waveOptions.amplitude / 2;
            if (!waveAnimationOptions.flatLineOnDrag || (!((Boolean) state2.getValue()).booleanValue() && waveAnimationOptions.animateWave)) {
                companion3 = companion2;
                f = f2;
            } else {
                companion3 = companion2;
                f = 0.0f;
            }
            final Modifier.Companion companion5 = companion3;
            final boolean z4 = z3;
            final State animateFloatAsState = AnimateAsStateKt.animateFloatAsState(f, AnimationSpecKt.tween$default(200, 0, null, 6), "Wave amplitude", null, composerImpl3, 3120, 20);
            composerImpl3.startReplaceGroup(-83786502);
            if (((Number) animateFloatAsState.getValue()).floatValue() <= 0.0f) {
                Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion5, 1.0f);
                composerImpl3.startReplaceGroup(-83783865);
                boolean changed = composerImpl3.changed(z4) | ((i3 & 14) == 4 || ((i3 & 8) != 0 && composerImpl3.changedInstance(sliderState))) | ((((i3 & 57344) ^ 24576) > 16384 && composerImpl3.changed(audioVisSeekbarColors)) || (i3 & 24576) == 16384) | ((i3 & 7168) == 2048) | ((i3 & 3670016) == 1048576);
                Object rememberedValue2 = composerImpl3.rememberedValue();
                if (changed || rememberedValue2 == composer$Companion$Empty$1) {
                    r7 = 0;
                    Function1 function1 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            char c;
                            float f3;
                            DrawScope drawScope;
                            boolean z5;
                            AudioVisSeekbarColors audioVisSeekbarColors2;
                            float f4;
                            WaveOptions waveOptions2;
                            DrawScope drawScope2 = (DrawScope) obj;
                            AudioVisSeekbarDefaults audioVisSeekbarDefaults = AudioVisSeekbarDefaults.INSTANCE;
                            float intBitsToFloat = Float.intBitsToFloat((int) (drawScope2.mo545getSizeNHjbRc() >> 32));
                            boolean z6 = z4;
                            float f5 = z6 ? intBitsToFloat : 0.0f;
                            float value = sliderState.getValue() * intBitsToFloat;
                            if (z6) {
                                value = intBitsToFloat - value;
                            }
                            float f6 = value;
                            AudioVisSeekbarColors audioVisSeekbarColors3 = audioVisSeekbarColors;
                            boolean z7 = z2;
                            WaveOptions waveOptions3 = waveOptions;
                            if (f5 == f6) {
                                c = ' ';
                                f3 = intBitsToFloat;
                                f4 = 0.0f;
                                waveOptions2 = waveOptions3;
                                drawScope = drawScope2;
                                audioVisSeekbarColors2 = audioVisSeekbarColors3;
                                z5 = z7;
                            } else {
                                Offset.Companion companion6 = Offset.Companion;
                                c = ' ';
                                f3 = intBitsToFloat;
                                float f7 = waveOptions3.trackWidth;
                                StrokeCap.Companion.getClass();
                                int i4 = StrokeCap.Round;
                                drawScope = drawScope2;
                                z5 = z7;
                                audioVisSeekbarColors2 = audioVisSeekbarColors3;
                                f4 = 0.0f;
                                waveOptions2 = waveOptions3;
                                DrawScope.m535drawLineNGM6Ib0$default(drawScope, audioVisSeekbarColors3.m2614x119e4b41(z7, true), (Float.floatToRawIntBits(f5) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L), (Float.floatToRawIntBits(f6) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L), f7, i4, 0.0f, VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE);
                                DrawScope.m535drawLineNGM6Ib0$default(drawScope, z5 ? audioVisSeekbarColors2.activeTrackSecondaryColor : audioVisSeekbarColors2.disabledActiveTrackSecondaryColor, (Float.floatToRawIntBits(f5) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L), (Float.floatToRawIntBits(f6) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L), waveOptions2.trackWidth, i4, 0.0f, VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE);
                            }
                            long m2614x119e4b41 = audioVisSeekbarColors2.m2614x119e4b41(z5, false);
                            long floatToRawIntBits = (Float.floatToRawIntBits(f6) << c) | (Float.floatToRawIntBits(f4) & 4294967295L);
                            Offset.Companion companion7 = Offset.Companion;
                            float f8 = waveOptions2.trackWidth;
                            StrokeCap.Companion.getClass();
                            DrawScope.m535drawLineNGM6Ib0$default(drawScope, m2614x119e4b41, floatToRawIntBits, (Float.floatToRawIntBits(z6 ? f4 : f3) << c) | (Float.floatToRawIntBits(f4) & 4294967295L), f8, StrokeCap.Round, 0.0f, VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE);
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl3.updateRememberedValue(function1);
                    rememberedValue2 = function1;
                } else {
                    r7 = 0;
                }
                composerImpl3.end(r7);
                CanvasKt.Canvas(fillMaxWidth, (Function1) rememberedValue2, composerImpl3, r7);
                composerImpl3.end(r7);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl endRestartGroup = composerImpl3.endRestartGroup();
                if (endRestartGroup != null) {
                    final int i4 = 0;
                    endRestartGroup.block = new Function2(this) { // from class: com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults$$ExternalSyntheticLambda1
                        public final /* synthetic */ AudioVisSeekbarDefaults f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i5 = i;
                            switch (i4) {
                                case 0:
                                    Composer composer2 = (Composer) obj;
                                    ((Integer) obj2).getClass();
                                    AudioVisSeekbarDefaults audioVisSeekbarDefaults = AudioVisSeekbarDefaults.INSTANCE;
                                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i5 | 1);
                                    this.f$0.Track(sliderState, state, companion5, z, audioVisSeekbarColors, waveAnimationOptions, waveOptions, composer2, updateChangedFlags);
                                    break;
                                default:
                                    Composer composer3 = (Composer) obj;
                                    ((Integer) obj2).getClass();
                                    AudioVisSeekbarDefaults audioVisSeekbarDefaults2 = AudioVisSeekbarDefaults.INSTANCE;
                                    int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i5 | 1);
                                    this.f$0.Track(sliderState, state, companion5, z, audioVisSeekbarColors, waveAnimationOptions, waveOptions, composer3, updateChangedFlags2);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            composerImpl3.end(false);
            InfiniteTransition rememberInfiniteTransition = InfiniteTransitionKt.rememberInfiniteTransition("Wave infinite transition", composerImpl3, 0);
            composerImpl3.startReplaceGroup(-83733990);
            int i5 = 458752 & i3;
            boolean z5 = i5 == 131072;
            Object rememberedValue3 = composerImpl3.rememberedValue();
            if (z5 || rememberedValue3 == composer$Companion$Empty$1) {
                rememberedValue3 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        AudioVisSeekbarDefaults audioVisSeekbarDefaults = AudioVisSeekbarDefaults.INSTANCE;
                        ((KeyframesSpec.KeyframesSpecConfig) obj).durationMillis = WaveAnimationOptions.this.animationSpeedMs;
                        return Unit.INSTANCE;
                    }
                };
                composerImpl3.updateRememberedValue(rememberedValue3);
            }
            composerImpl3.end(false);
            InfiniteRepeatableSpec m9infiniteRepeatable9IiC70o$default = AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(4, AnimationSpecKt.keyframes((Function1) rememberedValue3), RepeatMode.Restart, 0L);
            final float f3 = waveOptions.frequency;
            int i6 = i3;
            final InfiniteTransition.TransitionAnimationState animateFloat = InfiniteTransitionKt.animateFloat(rememberInfiniteTransition, 0.0f, f3, m9infiniteRepeatable9IiC70o$default, "Wave phase shift", composerImpl3, 28728, 0);
            Modifier fillMaxWidth2 = SizeKt.fillMaxWidth(companion5, 1.0f);
            composerImpl3.startReplaceGroup(-83725374);
            boolean changed2 = composerImpl3.changed(z4) | ((i6 & 14) == 4 || ((i6 & 8) != 0 && composerImpl3.changedInstance(sliderState))) | ((i6 & 7168) == 2048) | (i5 == 131072) | composerImpl3.changed(animateFloat) | composerImpl3.changed(animateFloatAsState) | composerImpl3.changed(f3) | ((i6 & 3670016) == 1048576);
            if ((((i6 & 57344) ^ 24576) <= 16384 || !composerImpl3.changed(audioVisSeekbarColors)) && (i6 & 24576) != 16384) {
                r18 = false;
            }
            boolean changed3 = changed2 | r18 | composerImpl3.changed(f2);
            Object rememberedValue4 = composerImpl3.rememberedValue();
            if (changed3 || rememberedValue4 == composer$Companion$Empty$1) {
                ComposerImpl composerImpl4 = composerImpl3;
                modifier = fillMaxWidth2;
                Function1 function12 = new Function1(z4, sliderState, z, waveAnimationOptions, f3, waveOptions, audioVisSeekbarColors, f2, animateFloat, animateFloatAsState) { // from class: com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults$$ExternalSyntheticLambda3
                    public final /* synthetic */ boolean f$0;
                    public final /* synthetic */ SliderState f$1;
                    public final /* synthetic */ boolean f$2;
                    public final /* synthetic */ WaveAnimationOptions f$3;
                    public final /* synthetic */ float f$4;
                    public final /* synthetic */ WaveOptions f$5;
                    public final /* synthetic */ AudioVisSeekbarColors f$6;
                    public final /* synthetic */ InfiniteTransition.TransitionAnimationState f$8;
                    public final /* synthetic */ State f$9;

                    {
                        this.f$8 = animateFloat;
                        this.f$9 = animateFloatAsState;
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        float f4;
                        float f5;
                        DrawScope drawScope;
                        float f6;
                        long j;
                        DrawScope drawScope2 = (DrawScope) obj;
                        AudioVisSeekbarDefaults audioVisSeekbarDefaults = AudioVisSeekbarDefaults.INSTANCE;
                        float intBitsToFloat = Float.intBitsToFloat((int) (drawScope2.mo545getSizeNHjbRc() >> 32));
                        boolean z6 = this.f$0;
                        float f7 = z6 ? intBitsToFloat : 0.0f;
                        float value = this.f$1.getValue() * intBitsToFloat;
                        if (z6) {
                            value = intBitsToFloat - value;
                        }
                        State state3 = this.f$9;
                        boolean z7 = this.f$2;
                        if (z7) {
                            boolean z8 = this.f$3.reverse;
                            InfiniteTransition.TransitionAnimationState transitionAnimationState = this.f$8;
                            f4 = z8 ? ((Number) transitionAnimationState.getValue()).floatValue() : -((Number) transitionAnimationState.getValue()).floatValue();
                            f5 = ((Number) state3.getValue()).floatValue();
                        } else {
                            f4 = 0.0f;
                            f5 = 0.0f;
                        }
                        float f8 = (this.f$4 / intBitsToFloat) * 3;
                        double abs = 3.141592653589793d / Math.abs(value - f7);
                        WaveOptions waveOptions2 = this.f$5;
                        float f9 = waveOptions2.trackWidth;
                        AudioVisSeekbarColors audioVisSeekbarColors2 = this.f$6;
                        long m2614x119e4b41 = audioVisSeekbarColors2.m2614x119e4b41(z7, true);
                        if (z7) {
                            drawScope = drawScope2;
                            f6 = f7;
                            j = audioVisSeekbarColors2.activeTrackSecondaryColor;
                        } else {
                            drawScope = drawScope2;
                            f6 = f7;
                            j = audioVisSeekbarColors2.disabledActiveTrackSecondaryColor;
                        }
                        long j2 = j;
                        float floatValue = ((Number) state3.getValue()).floatValue();
                        AudioVisSeekbarDefaults.INSTANCE.getClass();
                        float f10 = f4;
                        DrawScope drawScope3 = drawScope;
                        float f11 = f6;
                        AudioVisSeekbarDefaults.m2615drawTrackImwu3XQ(drawScope3, 0, f11, value, f10, f8, abs, f9, floatValue, f5, m2614x119e4b41, z6);
                        AudioVisSeekbarDefaults.m2615drawTrackImwu3XQ(drawScope3, 1, f11, value, f10, f8, abs, f9, ((Number) state3.getValue()).floatValue(), f5, j2, z6);
                        long m2614x119e4b412 = audioVisSeekbarColors2.m2614x119e4b41(z7, false);
                        long floatToRawIntBits = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (drawScope3.mo544getCenterF1C5BW0() & 4294967295L))) & 4294967295L) | (Float.floatToRawIntBits(value) << 32);
                        Offset.Companion companion6 = Offset.Companion;
                        float f12 = z6 ? 0.0f : intBitsToFloat;
                        StrokeCap.Companion.getClass();
                        DrawScope.m535drawLineNGM6Ib0$default(drawScope3, m2614x119e4b412, floatToRawIntBits, (Float.floatToRawIntBits(Float.intBitsToFloat((int) (drawScope3.mo544getCenterF1C5BW0() & 4294967295L))) & 4294967295L) | (Float.floatToRawIntBits(f12) << 32), waveOptions2.trackWidth, StrokeCap.Round, 0.0f, VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl4.updateRememberedValue(function12);
                rememberedValue4 = function12;
                composerImpl = composerImpl4;
            } else {
                composerImpl = composerImpl3;
                modifier = fillMaxWidth2;
            }
            composerImpl.end(false);
            CanvasKt.Canvas(modifier, (Function1) rememberedValue4, composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion4 = companion5;
            composerImpl2 = composerImpl;
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl2.endRestartGroup();
        if (endRestartGroup2 != null) {
            final int i7 = 1;
            endRestartGroup2.block = new Function2(this) { // from class: com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults$$ExternalSyntheticLambda1
                public final /* synthetic */ AudioVisSeekbarDefaults f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int i52 = i;
                    switch (i7) {
                        case 0:
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).getClass();
                            AudioVisSeekbarDefaults audioVisSeekbarDefaults = AudioVisSeekbarDefaults.INSTANCE;
                            int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i52 | 1);
                            this.f$0.Track(sliderState, state, companion4, z, audioVisSeekbarColors, waveAnimationOptions, waveOptions, composer2, updateChangedFlags);
                            break;
                        default:
                            Composer composer3 = (Composer) obj;
                            ((Integer) obj2).getClass();
                            AudioVisSeekbarDefaults audioVisSeekbarDefaults2 = AudioVisSeekbarDefaults.INSTANCE;
                            int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i52 | 1);
                            this.f$0.Track(sliderState, state, companion4, z, audioVisSeekbarColors, waveAnimationOptions, waveOptions, composer3, updateChangedFlags2);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
