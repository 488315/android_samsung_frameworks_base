package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.CrossfadeKt;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.material3.IconKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.media.mediaoutput.icons.EqualizerPlayingKt;
import com.android.systemui.media.mediaoutput.icons.Icons;
import java.util.Arrays;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class AnimatedPlayingIconKt {
    public static final void AnimatedPlayingIcon(List list, final Modifier modifier, final long j, Composer composer, final int i) {
        int i2;
        MutableState mutableState;
        final List list2 = list;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(960395791);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(list2) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(j) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.AnimatedPlayingIcon (AnimatedPlayingIcon.kt:26)");
            }
            composerImpl.startReplaceGroup(-717121946);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(0);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MutableState mutableState2 = (MutableState) rememberedValue;
            composerImpl.end(false);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(-717120019);
            boolean changedInstance = ((i2 & 896) == 256) | composerImpl.changedInstance(list2);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                mutableState = mutableState2;
                AnimatedPlayingIconKt$AnimatedPlayingIcon$1$1 animatedPlayingIconKt$AnimatedPlayingIcon$1$1 = new AnimatedPlayingIconKt$AnimatedPlayingIcon$1$1(j, list2, mutableState, null);
                list2 = list2;
                composerImpl.updateRememberedValue(animatedPlayingIconKt$AnimatedPlayingIcon$1$1);
                rememberedValue2 = animatedPlayingIconKt$AnimatedPlayingIcon$1$1;
            } else {
                mutableState = mutableState2;
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) rememberedValue2);
            CrossfadeKt.Crossfade(list2.get(((Number) mutableState.getValue()).intValue()), (Modifier) null, (FiniteAnimationSpec) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-1146096476, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.AnimatedPlayingIconKt$AnimatedPlayingIcon$2
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ImageVector imageVector = (ImageVector) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 6) == 0) {
                        intValue |= ((ComposerImpl) composer2).changed(imageVector) ? 4 : 2;
                    }
                    if ((intValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.AnimatedPlayingIcon.<anonymous> (AnimatedPlayingIcon.kt:37)");
                    }
                    IconKt.m270Iconww6aTOc(imageVector, "", Modifier.this, 0L, composer2, (intValue & 14) | 48, 8);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 24576, 14);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.AnimatedPlayingIconKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    AnimatedPlayingIconKt.AnimatedPlayingIcon(list2, modifier, j, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void EqualizerPlayingIcon(final Modifier.Companion companion, final long j, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-937922536);
        if (((i | 54) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            Modifier.Companion companion2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.EqualizerPlayingIcon (AnimatedPlayingIcon.kt:49)");
            }
            int i2 = Icons.$r8$clinit;
            AnimatedPlayingIcon(Arrays.asList((ImageVector) EqualizerPlayingKt.EqualizerPlaying1$delegate.getValue(), (ImageVector) EqualizerPlayingKt.EqualizerPlaying2$delegate.getValue(), (ImageVector) EqualizerPlayingKt.EqualizerPlaying3$delegate.getValue()), companion2, 500L, composerImpl, 432);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion = companion2;
            j = 500;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(j, i) { // from class: com.android.systemui.media.mediaoutput.compose.widget.AnimatedPlayingIconKt$$ExternalSyntheticLambda0
                public final /* synthetic */ long f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    AnimatedPlayingIconKt.EqualizerPlayingIcon(Modifier.Companion.this, this.f$1, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
