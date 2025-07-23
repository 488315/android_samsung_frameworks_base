package androidx.compose.animation.core;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.IntCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AnimateAsStateKt {
    public static final SpringSpec defaultAnimation = AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7);
    public static final SpringSpec dpDefaultSpring;
    public static final SpringSpec offsetDefaultSpring = null;
    public static final SpringSpec rectDefaultSpring = null;

    static {
        Dp.Companion companion = Dp.Companion;
        Rect rect = VisibilityThresholdsKt.RectVisibilityThreshold;
        dpDefaultSpring = AnimationSpecKt.spring$default(0.0f, 0.0f, Dp.m835boximpl(0.1f), 3);
        Size.Companion companion2 = Size.Companion;
        AnimationSpecKt.spring$default(0.0f, 0.0f, Size.m413boximpl((Float.floatToRawIntBits(0.5f) << 32) | (Float.floatToRawIntBits(0.5f) & 4294967295L)), 3);
        Offset.Companion companion3 = Offset.Companion;
        AnimationSpecKt.spring$default(0.0f, 0.0f, Offset.m393boximpl((Float.floatToRawIntBits(0.5f) << 32) | (Float.floatToRawIntBits(0.5f) & 4294967295L)), 3);
        Rect.Companion companion4 = Rect.Companion;
        AnimationSpecKt.spring$default(0.0f, 0.0f, VisibilityThresholdsKt.RectVisibilityThreshold, 3);
        int i = IntCompanionObject.$r8$clinit;
        AnimationSpecKt.spring$default(0.0f, 0.0f, 1, 3);
        IntOffset.Companion companion5 = IntOffset.Companion;
        AnimationSpecKt.spring$default(0.0f, 0.0f, IntOffset.m847boximpl(VisibilityThresholdsKt.getVisibilityThreshold()), 3);
        IntSize.Companion companion6 = IntSize.Companion;
        AnimationSpecKt.spring$default(0.0f, 0.0f, IntSize.m859boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 3);
    }

    /* renamed from: animateDpAsState-AjpBEmI, reason: not valid java name */
    public static final State m8animateDpAsStateAjpBEmI(float f, FiniteAnimationSpec finiteAnimationSpec, String str, Composer composer, int i, int i2) {
        if ((i2 & 2) != 0) {
            finiteAnimationSpec = dpDefaultSpring;
        }
        FiniteAnimationSpec finiteAnimationSpec2 = finiteAnimationSpec;
        if ((i2 & 4) != 0) {
            str = "DpAnimation";
        }
        String str2 = str;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.animateDpAsState (AnimateAsState.kt:111)");
        }
        State animateValueAsState = animateValueAsState(Dp.m835boximpl(f), VectorConvertersKt.DpToVector, finiteAnimationSpec2, null, str2, null, composer, ((i << 3) & 896) | ((i << 6) & 57344), 8);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return animateValueAsState;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0056, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.runtime.State animateFloatAsState(float r12, androidx.compose.animation.core.FiniteAnimationSpec r13, java.lang.String r14, kotlin.jvm.functions.Function1 r15, androidx.compose.runtime.Composer r16, int r17, int r18) {
        /*
            r0 = r17
            r1 = r18 & 2
            androidx.compose.animation.core.SpringSpec r2 = androidx.compose.animation.core.AnimateAsStateKt.defaultAnimation
            if (r1 == 0) goto L9
            r13 = r2
        L9:
            r1 = r18 & 8
            if (r1 == 0) goto Lf
            java.lang.String r14 = "FloatAnimation"
        Lf:
            r7 = r14
            r14 = r18 & 16
            if (r14 == 0) goto L17
            r14 = 0
            r8 = r14
            goto L18
        L17:
            r8 = r15
        L18:
            boolean r14 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r14 == 0) goto L23
            java.lang.String r14 = "androidx.compose.animation.core.animateFloatAsState (AnimateAsState.kt:67)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r14)
        L23:
            r14 = 1008981770(0x3c23d70a, float:0.01)
            r1 = 3
            r3 = 0
            if (r13 != r2) goto L6c
            r13 = r16
            androidx.compose.runtime.ComposerImpl r13 = (androidx.compose.runtime.ComposerImpl) r13
            r2 = 1125558999(0x4316aad7, float:150.66734)
            r13.startReplaceGroup(r2)
            r2 = r0 & 896(0x380, float:1.256E-42)
            r2 = r2 ^ 384(0x180, float:5.38E-43)
            r4 = 256(0x100, float:3.59E-43)
            if (r2 <= r4) goto L42
            boolean r2 = r13.changed(r14)
            if (r2 != 0) goto L46
        L42:
            r2 = r0 & 384(0x180, float:5.38E-43)
            if (r2 != r4) goto L48
        L46:
            r2 = 1
            goto L49
        L48:
            r2 = r3
        L49:
            java.lang.Object r4 = r13.rememberedValue()
            if (r2 != 0) goto L58
            androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r4 != r2) goto L64
        L58:
            java.lang.Float r2 = java.lang.Float.valueOf(r14)
            r4 = 0
            androidx.compose.animation.core.SpringSpec r4 = androidx.compose.animation.core.AnimationSpecKt.spring$default(r4, r4, r2, r1)
            r13.updateRememberedValue(r4)
        L64:
            r2 = r4
            androidx.compose.animation.core.SpringSpec r2 = (androidx.compose.animation.core.SpringSpec) r2
            r13.end(r3)
            r5 = r2
            goto L7a
        L6c:
            r2 = r16
            androidx.compose.runtime.ComposerImpl r2 = (androidx.compose.runtime.ComposerImpl) r2
            r4 = 1125668925(0x4318583d, float:152.34468)
            r2.startReplaceGroup(r4)
            r2.end(r3)
            r5 = r13
        L7a:
            java.lang.Float r3 = java.lang.Float.valueOf(r12)
            kotlin.jvm.internal.FloatCompanionObject r12 = kotlin.jvm.internal.FloatCompanionObject.INSTANCE
            androidx.compose.animation.core.TwoWayConverter r4 = androidx.compose.animation.core.VectorConvertersKt.FloatToVector
            java.lang.Float r6 = java.lang.Float.valueOf(r14)
            r12 = r0 & 14
            int r13 = r0 << 3
            r14 = r13 & 7168(0x1c00, float:1.0045E-41)
            r12 = r12 | r14
            r14 = 57344(0xe000, float:8.0356E-41)
            r14 = r14 & r13
            r12 = r12 | r14
            r14 = 458752(0x70000, float:6.42848E-40)
            r13 = r13 & r14
            r10 = r12 | r13
            r11 = 0
            r9 = r16
            androidx.compose.runtime.State r12 = animateValueAsState(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            boolean r13 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r13 == 0) goto La7
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        La7:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.AnimateAsStateKt.animateFloatAsState(float, androidx.compose.animation.core.FiniteAnimationSpec, java.lang.String, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int, int):androidx.compose.runtime.State");
    }

    public static final State animateValueAsState(final Object obj, TwoWayConverter twoWayConverter, AnimationSpec animationSpec, Object obj2, String str, Function1 function1, Composer composer, int i, int i2) {
        if ((i2 & 8) != 0) {
            obj2 = null;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.animateValueAsState (AnimateAsState.kt:395)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Object obj3 = Composer.Companion.Empty;
        if (rememberedValue == obj3) {
            rememberedValue = SnapshotStateKt.mutableStateOf$default(null);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        MutableState mutableState = (MutableState) rememberedValue;
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == obj3) {
            rememberedValue2 = new Animatable(obj, twoWayConverter, obj2, str);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        Animatable animatable = (Animatable) rememberedValue2;
        MutableState rememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function1, composerImpl);
        if (obj2 != null && (animationSpec instanceof SpringSpec)) {
            SpringSpec springSpec = (SpringSpec) animationSpec;
            if (!Intrinsics.areEqual(springSpec.visibilityThreshold, obj2)) {
                animationSpec = new SpringSpec(springSpec.dampingRatio, springSpec.stiffness, obj2);
            }
        }
        MutableState rememberUpdatedState2 = SnapshotStateKt.rememberUpdatedState(animationSpec, composerImpl);
        Object rememberedValue3 = composerImpl.rememberedValue();
        if (rememberedValue3 == obj3) {
            rememberedValue3 = ChannelKt.Channel$default(-1, null, null, 6);
            composerImpl.updateRememberedValue(rememberedValue3);
        }
        final Channel channel = (Channel) rememberedValue3;
        boolean changedInstance = composerImpl.changedInstance(channel) | ((((i & 14) ^ 6) > 4 && composerImpl.changedInstance(obj)) || (i & 6) == 4);
        Object rememberedValue4 = composerImpl.rememberedValue();
        if (changedInstance || rememberedValue4 == obj3) {
            rememberedValue4 = new Function0() { // from class: androidx.compose.animation.core.AnimateAsStateKt$animateValueAsState$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Channel.this.mo3456trySendJP2dKIU(obj);
                    return Unit.INSTANCE;
                }
            };
            composerImpl.updateRememberedValue(rememberedValue4);
        }
        EffectsKt.SideEffect((Function0) rememberedValue4, composerImpl);
        boolean changedInstance2 = composerImpl.changedInstance(channel) | composerImpl.changedInstance(animatable) | composerImpl.changed(rememberUpdatedState2) | composerImpl.changed(rememberUpdatedState);
        Object rememberedValue5 = composerImpl.rememberedValue();
        if (changedInstance2 || rememberedValue5 == obj3) {
            Object animateAsStateKt$animateValueAsState$3$1 = new AnimateAsStateKt$animateValueAsState$3$1(channel, animatable, rememberUpdatedState2, rememberUpdatedState, null);
            composerImpl.updateRememberedValue(animateAsStateKt$animateValueAsState$3$1);
            rememberedValue5 = animateAsStateKt$animateValueAsState$3$1;
        }
        EffectsKt.LaunchedEffect(composerImpl, channel, (Function2) rememberedValue5);
        State state = (State) mutableState.getValue();
        if (state == null) {
            state = animatable.internalState;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return state;
    }
}
