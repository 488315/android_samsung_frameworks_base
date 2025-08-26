package androidx.compose.material3;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.InfiniteTransition;
import androidx.compose.animation.core.InfiniteTransitionKt;
import androidx.compose.animation.core.KeyframesSpec;
import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.ProgressSemanticsKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.tokens.CircularProgressIndicatorTokens;
import androidx.compose.material3.tokens.LinearProgressIndicatorTokens;
import androidx.compose.material3.tokens.MotionTokens;
import androidx.compose.material3.tokens.ProgressIndicatorTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class ProgressIndicatorKt {
    public static final float CircularIndicatorDiameter;
    public static final CubicBezierEasing CircularProgressEasing;

    static {
        Dp.Companion companion = Dp.Companion;
        LinearProgressIndicatorTokens.INSTANCE.getClass();
        CircularProgressIndicatorTokens.INSTANCE.getClass();
        CircularIndicatorDiameter = CircularProgressIndicatorTokens.Size;
        MotionTokens motionTokens = MotionTokens.INSTANCE;
        motionTokens.getClass();
        motionTokens.getClass();
        CircularProgressEasing = MotionTokens.EasingStandardCubicBezier;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x024c  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0256  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0282 A[PHI: r14 r24
      0x0282: PHI (r14v13 long) = (r14v11 long), (r14v14 long) binds: [B:135:0x0280, B:132:0x0275] A[DONT_GENERATE, DONT_INLINE]
      0x0282: PHI (r24v2 androidx.compose.animation.core.InfiniteTransition$TransitionAnimationState) = 
      (r24v0 androidx.compose.animation.core.InfiniteTransition$TransitionAnimationState)
      (r24v3 androidx.compose.animation.core.InfiniteTransition$TransitionAnimationState)
     binds: [B:135:0x0280, B:132:0x0275] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x02bb  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:161:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0124  */
    /* renamed from: CircularProgressIndicator-4lLiAd8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m279CircularProgressIndicator4lLiAd8(Modifier modifier, long j, float f, long j2, int i, float f2, Composer composer, final int i2, final int i3) {
        Modifier modifier2;
        int i4;
        long j3;
        float f3;
        long j4;
        int i5;
        int i6;
        int i7;
        float f4;
        final Modifier modifier3;
        final float f5;
        long j5;
        final InfiniteTransition.TransitionAnimationState transitionAnimationState;
        boolean z;
        boolean zChangedInstance;
        final long j6;
        final float f6;
        final long j7;
        final int i8;
        final float f7;
        final float f8;
        final long j8;
        final long j9;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(333154241);
        int i9 = i3 & 1;
        if (i9 != 0) {
            i4 = i2 | 6;
            modifier2 = modifier;
        } else if ((i2 & 6) == 0) {
            modifier2 = modifier;
            i4 = (composerImpl.changed(modifier2) ? 4 : 2) | i2;
        } else {
            modifier2 = modifier;
            i4 = i2;
        }
        if ((i2 & 48) == 0) {
            j3 = j;
            i4 |= ((i3 & 2) == 0 && composerImpl.changed(j3)) ? 32 : 16;
        } else {
            j3 = j;
        }
        int i10 = i3 & 4;
        if (i10 != 0) {
            i4 |= 384;
        } else {
            if ((i2 & 384) == 0) {
                f3 = f;
                i4 |= composerImpl.changed(f3) ? 256 : 128;
            }
            if ((i2 & 3072) != 0) {
                j4 = j2;
                i4 |= ((i3 & 8) == 0 && composerImpl.changed(j4)) ? 2048 : 1024;
            } else {
                j4 = j2;
            }
            i5 = i3 & 16;
            if (i5 != 0) {
                if ((i2 & 24576) == 0) {
                    i6 = i;
                    i4 |= composerImpl.changed(i6) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                i7 = i3 & 32;
                if (i7 != 0) {
                    i4 |= 196608;
                    f4 = f2;
                } else {
                    f4 = f2;
                    if ((i2 & 196608) == 0) {
                        i4 |= composerImpl.changed(f4) ? 131072 : 65536;
                    }
                }
                if ((i4 & 74899) == 74898 && composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    modifier3 = modifier2;
                    j9 = j3;
                    f7 = f4;
                    f8 = f3;
                    i8 = i6;
                    j8 = j4;
                } else {
                    composerImpl.startDefaults();
                    if ((i2 & 1) != 0 || composerImpl.getDefaultsInvalid()) {
                        modifier3 = i9 == 0 ? Modifier.Companion : modifier2;
                        if ((i3 & 2) != 0) {
                            ProgressIndicatorDefaults.INSTANCE.getClass();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.ProgressIndicatorDefaults.<get-circularColor> (ProgressIndicator.kt:814)");
                            }
                            ProgressIndicatorTokens.INSTANCE.getClass();
                            long value = ColorSchemeKt.getValue(ProgressIndicatorTokens.ActiveIndicatorColor, composerImpl);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            i4 &= -113;
                            j3 = value;
                        }
                        if (i10 != 0) {
                            ProgressIndicatorDefaults.INSTANCE.getClass();
                            f3 = ProgressIndicatorDefaults.CircularStrokeWidth;
                        }
                        if ((i3 & 8) != 0) {
                            ProgressIndicatorDefaults.INSTANCE.getClass();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.ProgressIndicatorDefaults.<get-circularIndeterminateTrackColor> (ProgressIndicator.kt:835)");
                            }
                            Color.Companion.getClass();
                            long j10 = Color.Transparent;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            i4 &= -7169;
                            j4 = j10;
                        }
                        if (i5 != 0) {
                            ProgressIndicatorDefaults.INSTANCE.getClass();
                            i6 = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap;
                        }
                        if (i7 == 0) {
                            ProgressIndicatorDefaults.INSTANCE.getClass();
                            f5 = ProgressIndicatorDefaults.CircularIndicatorTrackGapSize;
                        }
                        final int i11 = i6;
                        composerImpl.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.CircularProgressIndicator (ProgressIndicator.kt:627)");
                        }
                        final Stroke stroke = new Stroke(((Density) composerImpl.consume(CompositionLocalsKt.LocalDensity)).mo58toPx0680j_4(f3), 0.0f, i11, 0, null, 26, null);
                        InfiniteTransition infiniteTransitionRememberInfiniteTransition = InfiniteTransitionKt.rememberInfiniteTransition(null, composerImpl, 1);
                        long j11 = j4;
                        InfiniteTransition.TransitionAnimationState transitionAnimationStateAnimateFloat = InfiniteTransitionKt.animateFloat(infiniteTransitionRememberInfiniteTransition, 0.0f, 1080.0f, AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(6, AnimationSpecKt.tween$default(PluginEdgeLightingPlus.VERSION, 0, EasingKt.LinearEasing, 2), null, 0L), null, composerImpl, 4536, 8);
                        final InfiniteTransition.TransitionAnimationState transitionAnimationStateAnimateFloat2 = InfiniteTransitionKt.animateFloat(infiniteTransitionRememberInfiniteTransition, 0.0f, 360.0f, AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(6, AnimationSpecKt.keyframes(new Function1() { // from class: androidx.compose.material3.ProgressIndicatorKt$circularIndeterminateRotationAnimationSpec$1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                KeyframesSpec.KeyframesSpecConfig keyframesSpecConfig = (KeyframesSpec.KeyframesSpecConfig) obj;
                                keyframesSpecConfig.durationMillis = PluginEdgeLightingPlus.VERSION;
                                KeyframesSpec.KeyframeEntity keyframeEntityAt = keyframesSpecConfig.at(300, Float.valueOf(90.0f));
                                MotionTokens.INSTANCE.getClass();
                                keyframeEntityAt.easing = MotionTokens.EasingEmphasizedDecelerateCubicBezier;
                                keyframesSpecConfig.at(1500, Float.valueOf(90.0f));
                                keyframesSpecConfig.at(1800, Float.valueOf(180.0f));
                                keyframesSpecConfig.at(3000, Float.valueOf(180.0f));
                                keyframesSpecConfig.at(3300, Float.valueOf(270.0f));
                                keyframesSpecConfig.at(4500, Float.valueOf(270.0f));
                                keyframesSpecConfig.at(4800, Float.valueOf(360.0f));
                                keyframesSpecConfig.at(PluginEdgeLightingPlus.VERSION, Float.valueOf(360.0f));
                                return Unit.INSTANCE;
                            }
                        }), null, 0L), null, composerImpl, 4536, 8);
                        final InfiniteTransition.TransitionAnimationState transitionAnimationStateAnimateFloat3 = InfiniteTransitionKt.animateFloat(infiniteTransitionRememberInfiniteTransition, 0.1f, 0.87f, AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(6, AnimationSpecKt.keyframes(new Function1() { // from class: androidx.compose.material3.ProgressIndicatorKt$circularIndeterminateProgressAnimationSpec$1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                KeyframesSpec.KeyframesSpecConfig keyframesSpecConfig = (KeyframesSpec.KeyframesSpecConfig) obj;
                                keyframesSpecConfig.durationMillis = PluginEdgeLightingPlus.VERSION;
                                keyframesSpecConfig.at(3000, Float.valueOf(0.87f)).easing = ProgressIndicatorKt.CircularProgressEasing;
                                keyframesSpecConfig.at(PluginEdgeLightingPlus.VERSION, Float.valueOf(0.1f));
                                return Unit.INSTANCE;
                            }
                        }), null, 0L), null, composerImpl, 4536, 8);
                        Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(ProgressSemanticsKt.progressSemantics(modifier3), CircularIndicatorDiameter);
                        boolean zChanged = composerImpl.changed(transitionAnimationStateAnimateFloat3) | ((57344 & i4) == 16384) | ((458752 & i4) == 131072) | ((i4 & 896) == 256) | composerImpl.changed(transitionAnimationStateAnimateFloat) | composerImpl.changed(transitionAnimationStateAnimateFloat2);
                        if (((i4 & 7168) ^ 3072) > 2048) {
                            j5 = j11;
                            if (composerImpl.changed(j5)) {
                                transitionAnimationState = transitionAnimationStateAnimateFloat;
                            }
                            zChangedInstance = z | zChanged | composerImpl.changedInstance(stroke) | ((((i4 & 112) ^ 48) <= 32 && composerImpl.changed(j3)) || (i4 & 48) == 32);
                            Object objRememberedValue = composerImpl.rememberedValue();
                            if (!zChangedInstance) {
                                Composer.Companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    j6 = j3;
                                    f6 = f3;
                                    j7 = j5;
                                    objRememberedValue = new Function1() { // from class: androidx.compose.material3.ProgressIndicatorKt$CircularProgressIndicator$6$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj) {
                                            float f9;
                                            DrawScope drawScope = (DrawScope) obj;
                                            float fFloatValue = ((Number) transitionAnimationStateAnimateFloat3.getValue()).floatValue() * 360.0f;
                                            int i12 = i11;
                                            StrokeCap.Companion.getClass();
                                            if (i12 != 0 && Size.m417getHeightimpl(drawScope.mo547getSizeNHjbRc()) <= Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc())) {
                                                f9 = f5 + f6;
                                                Dp.Companion companion = Dp.Companion;
                                            } else {
                                                f9 = f5;
                                            }
                                            float fMo54toDpu2uoSUM = (f9 / ((float) (drawScope.mo54toDpu2uoSUM(Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc())) * 3.141592653589793d))) * 360.0f;
                                            float fFloatValue2 = ((Number) transitionAnimationStateAnimateFloat2.getValue()).floatValue() + ((Number) transitionAnimationState.getValue()).floatValue();
                                            long j12 = j7;
                                            Stroke stroke2 = stroke;
                                            long j13 = j6;
                                            long jMo546getCenterF1C5BW0 = drawScope.mo546getCenterF1C5BW0();
                                            CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
                                            long jM528getSizeNHjbRc = drawContext.m528getSizeNHjbRc();
                                            drawContext.getCanvas().save();
                                            try {
                                                drawContext.transform.m531rotateUv8p0NA(fFloatValue2, jMo546getCenterF1C5BW0);
                                                ProgressIndicatorKt.m280drawCircularIndicator42QJj7c(drawScope, Math.min(fFloatValue, fMo54toDpu2uoSUM) + fFloatValue, (360.0f - fFloatValue) - (Math.min(fFloatValue, fMo54toDpu2uoSUM) * 2), j12, stroke2);
                                                ProgressIndicatorKt.m280drawCircularIndicator42QJj7c(drawScope, 0.0f, fFloatValue, j13, stroke2);
                                                BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, jM528getSizeNHjbRc);
                                                return Unit.INSTANCE;
                                            } catch (Throwable th) {
                                                BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, jM528getSizeNHjbRc);
                                                throw th;
                                            }
                                        }
                                    };
                                    composerImpl.updateRememberedValue(objRememberedValue);
                                } else {
                                    j6 = j3;
                                    f6 = f3;
                                    j7 = j5;
                                }
                                CanvasKt.Canvas(modifierM140size3ABfNKs, (Function1) objRememberedValue, composerImpl, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                composerImpl = composerImpl;
                                i8 = i11;
                                f7 = f5;
                                f8 = f6;
                                j8 = j7;
                                j9 = j6;
                            }
                        } else {
                            j5 = j11;
                        }
                        transitionAnimationState = transitionAnimationStateAnimateFloat;
                        z = (i4 & 3072) == 2048;
                        if (((i4 & 112) ^ 48) <= 32) {
                            zChangedInstance = z | zChanged | composerImpl.changedInstance(stroke) | ((((i4 & 112) ^ 48) <= 32 && composerImpl.changed(j3)) || (i4 & 48) == 32);
                            Object objRememberedValue2 = composerImpl.rememberedValue();
                            if (!zChangedInstance) {
                            }
                        } else {
                            zChangedInstance = z | zChanged | composerImpl.changedInstance(stroke) | ((((i4 & 112) ^ 48) <= 32 && composerImpl.changed(j3)) || (i4 & 48) == 32);
                            Object objRememberedValue22 = composerImpl.rememberedValue();
                            if (!zChangedInstance) {
                            }
                        }
                    } else {
                        composerImpl.skipToGroupEnd();
                        if ((i3 & 2) != 0) {
                            i4 &= -113;
                        }
                        if ((i3 & 8) != 0) {
                            i4 &= -7169;
                        }
                        modifier3 = modifier2;
                    }
                    f5 = f4;
                    final int i112 = i6;
                    composerImpl.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    final Stroke stroke2 = new Stroke(((Density) composerImpl.consume(CompositionLocalsKt.LocalDensity)).mo58toPx0680j_4(f3), 0.0f, i112, 0, null, 26, null);
                    InfiniteTransition infiniteTransitionRememberInfiniteTransition2 = InfiniteTransitionKt.rememberInfiniteTransition(null, composerImpl, 1);
                    long j112 = j4;
                    InfiniteTransition.TransitionAnimationState transitionAnimationStateAnimateFloat4 = InfiniteTransitionKt.animateFloat(infiniteTransitionRememberInfiniteTransition2, 0.0f, 1080.0f, AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(6, AnimationSpecKt.tween$default(PluginEdgeLightingPlus.VERSION, 0, EasingKt.LinearEasing, 2), null, 0L), null, composerImpl, 4536, 8);
                    final State<Float> transitionAnimationStateAnimateFloat22 = InfiniteTransitionKt.animateFloat(infiniteTransitionRememberInfiniteTransition2, 0.0f, 360.0f, AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(6, AnimationSpecKt.keyframes(new Function1() { // from class: androidx.compose.material3.ProgressIndicatorKt$circularIndeterminateRotationAnimationSpec$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            KeyframesSpec.KeyframesSpecConfig keyframesSpecConfig = (KeyframesSpec.KeyframesSpecConfig) obj;
                            keyframesSpecConfig.durationMillis = PluginEdgeLightingPlus.VERSION;
                            KeyframesSpec.KeyframeEntity keyframeEntityAt = keyframesSpecConfig.at(300, Float.valueOf(90.0f));
                            MotionTokens.INSTANCE.getClass();
                            keyframeEntityAt.easing = MotionTokens.EasingEmphasizedDecelerateCubicBezier;
                            keyframesSpecConfig.at(1500, Float.valueOf(90.0f));
                            keyframesSpecConfig.at(1800, Float.valueOf(180.0f));
                            keyframesSpecConfig.at(3000, Float.valueOf(180.0f));
                            keyframesSpecConfig.at(3300, Float.valueOf(270.0f));
                            keyframesSpecConfig.at(4500, Float.valueOf(270.0f));
                            keyframesSpecConfig.at(4800, Float.valueOf(360.0f));
                            keyframesSpecConfig.at(PluginEdgeLightingPlus.VERSION, Float.valueOf(360.0f));
                            return Unit.INSTANCE;
                        }
                    }), null, 0L), null, composerImpl, 4536, 8);
                    final State<Float> transitionAnimationStateAnimateFloat32 = InfiniteTransitionKt.animateFloat(infiniteTransitionRememberInfiniteTransition2, 0.1f, 0.87f, AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(6, AnimationSpecKt.keyframes(new Function1() { // from class: androidx.compose.material3.ProgressIndicatorKt$circularIndeterminateProgressAnimationSpec$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            KeyframesSpec.KeyframesSpecConfig keyframesSpecConfig = (KeyframesSpec.KeyframesSpecConfig) obj;
                            keyframesSpecConfig.durationMillis = PluginEdgeLightingPlus.VERSION;
                            keyframesSpecConfig.at(3000, Float.valueOf(0.87f)).easing = ProgressIndicatorKt.CircularProgressEasing;
                            keyframesSpecConfig.at(PluginEdgeLightingPlus.VERSION, Float.valueOf(0.1f));
                            return Unit.INSTANCE;
                        }
                    }), null, 0L), null, composerImpl, 4536, 8);
                    Modifier modifierM140size3ABfNKs2 = SizeKt.m140size3ABfNKs(ProgressSemanticsKt.progressSemantics(modifier3), CircularIndicatorDiameter);
                    boolean zChanged2 = composerImpl.changed(transitionAnimationStateAnimateFloat32) | ((57344 & i4) == 16384) | ((458752 & i4) == 131072) | ((i4 & 896) == 256) | composerImpl.changed(transitionAnimationStateAnimateFloat4) | composerImpl.changed(transitionAnimationStateAnimateFloat22);
                    if (((i4 & 7168) ^ 3072) > 2048) {
                    }
                    transitionAnimationState = transitionAnimationStateAnimateFloat4;
                    if ((i4 & 3072) == 2048) {
                    }
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.ProgressIndicatorKt$CircularProgressIndicator$7
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ProgressIndicatorKt.m279CircularProgressIndicator4lLiAd8(modifier3, j9, f8, j8, i8, f7, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i2 | 1), i3);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i4 |= 24576;
            i6 = i;
            i7 = i3 & 32;
            if (i7 != 0) {
            }
            if ((i4 & 74899) == 74898) {
                composerImpl.startDefaults();
                if ((i2 & 1) != 0) {
                    if (i9 == 0) {
                    }
                    if ((i3 & 2) != 0) {
                    }
                    if (i10 != 0) {
                    }
                    if ((i3 & 8) != 0) {
                    }
                    if (i5 != 0) {
                    }
                    if (i7 == 0) {
                        f5 = f4;
                    }
                    final int i1122 = i6;
                    composerImpl.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    final Stroke stroke22 = new Stroke(((Density) composerImpl.consume(CompositionLocalsKt.LocalDensity)).mo58toPx0680j_4(f3), 0.0f, i1122, 0, null, 26, null);
                    InfiniteTransition infiniteTransitionRememberInfiniteTransition22 = InfiniteTransitionKt.rememberInfiniteTransition(null, composerImpl, 1);
                    long j1122 = j4;
                    InfiniteTransition.TransitionAnimationState transitionAnimationStateAnimateFloat42 = InfiniteTransitionKt.animateFloat(infiniteTransitionRememberInfiniteTransition22, 0.0f, 1080.0f, AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(6, AnimationSpecKt.tween$default(PluginEdgeLightingPlus.VERSION, 0, EasingKt.LinearEasing, 2), null, 0L), null, composerImpl, 4536, 8);
                    final State<Float> transitionAnimationStateAnimateFloat222 = InfiniteTransitionKt.animateFloat(infiniteTransitionRememberInfiniteTransition22, 0.0f, 360.0f, AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(6, AnimationSpecKt.keyframes(new Function1() { // from class: androidx.compose.material3.ProgressIndicatorKt$circularIndeterminateRotationAnimationSpec$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            KeyframesSpec.KeyframesSpecConfig keyframesSpecConfig = (KeyframesSpec.KeyframesSpecConfig) obj;
                            keyframesSpecConfig.durationMillis = PluginEdgeLightingPlus.VERSION;
                            KeyframesSpec.KeyframeEntity keyframeEntityAt = keyframesSpecConfig.at(300, Float.valueOf(90.0f));
                            MotionTokens.INSTANCE.getClass();
                            keyframeEntityAt.easing = MotionTokens.EasingEmphasizedDecelerateCubicBezier;
                            keyframesSpecConfig.at(1500, Float.valueOf(90.0f));
                            keyframesSpecConfig.at(1800, Float.valueOf(180.0f));
                            keyframesSpecConfig.at(3000, Float.valueOf(180.0f));
                            keyframesSpecConfig.at(3300, Float.valueOf(270.0f));
                            keyframesSpecConfig.at(4500, Float.valueOf(270.0f));
                            keyframesSpecConfig.at(4800, Float.valueOf(360.0f));
                            keyframesSpecConfig.at(PluginEdgeLightingPlus.VERSION, Float.valueOf(360.0f));
                            return Unit.INSTANCE;
                        }
                    }), null, 0L), null, composerImpl, 4536, 8);
                    final State<Float> transitionAnimationStateAnimateFloat322 = InfiniteTransitionKt.animateFloat(infiniteTransitionRememberInfiniteTransition22, 0.1f, 0.87f, AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(6, AnimationSpecKt.keyframes(new Function1() { // from class: androidx.compose.material3.ProgressIndicatorKt$circularIndeterminateProgressAnimationSpec$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            KeyframesSpec.KeyframesSpecConfig keyframesSpecConfig = (KeyframesSpec.KeyframesSpecConfig) obj;
                            keyframesSpecConfig.durationMillis = PluginEdgeLightingPlus.VERSION;
                            keyframesSpecConfig.at(3000, Float.valueOf(0.87f)).easing = ProgressIndicatorKt.CircularProgressEasing;
                            keyframesSpecConfig.at(PluginEdgeLightingPlus.VERSION, Float.valueOf(0.1f));
                            return Unit.INSTANCE;
                        }
                    }), null, 0L), null, composerImpl, 4536, 8);
                    Modifier modifierM140size3ABfNKs22 = SizeKt.m140size3ABfNKs(ProgressSemanticsKt.progressSemantics(modifier3), CircularIndicatorDiameter);
                    boolean zChanged22 = composerImpl.changed(transitionAnimationStateAnimateFloat322) | ((57344 & i4) == 16384) | ((458752 & i4) == 131072) | ((i4 & 896) == 256) | composerImpl.changed(transitionAnimationStateAnimateFloat42) | composerImpl.changed(transitionAnimationStateAnimateFloat222);
                    if (((i4 & 7168) ^ 3072) > 2048) {
                    }
                    transitionAnimationState = transitionAnimationStateAnimateFloat42;
                    if ((i4 & 3072) == 2048) {
                    }
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        f3 = f;
        if ((i2 & 3072) != 0) {
        }
        i5 = i3 & 16;
        if (i5 != 0) {
        }
        i6 = i;
        i7 = i3 & 32;
        if (i7 != 0) {
        }
        if ((i4 & 74899) == 74898) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* renamed from: drawCircularIndicator-42QJj7c, reason: not valid java name */
    public static final void m280drawCircularIndicator42QJj7c(DrawScope drawScope, float f, float f2, long j, Stroke stroke) {
        float f3 = 2;
        float f4 = stroke.width / f3;
        float fM419getWidthimpl = Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc()) - (f3 * f4);
        long jOffset = OffsetKt.Offset(f4, f4);
        long jSize = androidx.compose.ui.geometry.SizeKt.Size(fM419getWidthimpl, fM419getWidthimpl);
        DrawScope.Companion.getClass();
        drawScope.mo518drawArcyD3GUKo(j, f, f2, jOffset, jSize, stroke, DrawScope.Companion.DefaultBlendMode);
    }
}
