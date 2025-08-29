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
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.IntCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;

/* loaded from: classes.dex */
public abstract class AnimateAsStateKt {
    public static final SpringSpec defaultAnimation = AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7);
    public static final SpringSpec dpDefaultSpring;
    public static final SpringSpec offsetDefaultSpring = null;
    public static final SpringSpec rectDefaultSpring = null;

    static {
        Dp.Companion companion = Dp.Companion;
        Rect rect = VisibilityThresholdsKt.RectVisibilityThreshold;
        dpDefaultSpring = AnimationSpecKt.spring$default(0.0f, 0.0f, Dp.m837boximpl(0.1f), 3);
        Size.Companion companion2 = Size.Companion;
        AnimationSpecKt.spring$default(0.0f, 0.0f, Size.m415boximpl((Float.floatToRawIntBits(0.5f) << 32) | (Float.floatToRawIntBits(0.5f) & 4294967295L)), 3);
        Offset.Companion companion3 = Offset.Companion;
        AnimationSpecKt.spring$default(0.0f, 0.0f, Offset.m395boximpl((Float.floatToRawIntBits(0.5f) << 32) | (Float.floatToRawIntBits(0.5f) & 4294967295L)), 3);
        Rect.Companion companion4 = Rect.Companion;
        AnimationSpecKt.spring$default(0.0f, 0.0f, VisibilityThresholdsKt.RectVisibilityThreshold, 3);
        int i = IntCompanionObject.$r8$clinit;
        AnimationSpecKt.spring$default(0.0f, 0.0f, 1, 3);
        IntOffset.Companion companion5 = IntOffset.Companion;
        AnimationSpecKt.spring$default(0.0f, 0.0f, IntOffset.m849boximpl(VisibilityThresholdsKt.getVisibilityThreshold()), 3);
        IntSize.Companion companion6 = IntSize.Companion;
        AnimationSpecKt.spring$default(0.0f, 0.0f, IntSize.m861boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 3);
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
        State stateAnimateValueAsState = animateValueAsState(Dp.m837boximpl(f), VectorConvertersKt.DpToVector, finiteAnimationSpec2, null, str2, null, composer, ((i << 3) & 896) | ((i << 6) & 57344), 8);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return stateAnimateValueAsState;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final State animateFloatAsState(float f, FiniteAnimationSpec finiteAnimationSpec, String str, Function1 function1, Composer composer, int i, int i2) {
        FiniteAnimationSpec finiteAnimationSpec2;
        int i3 = i2 & 2;
        SpringSpec springSpec = defaultAnimation;
        if (i3 != 0) {
            finiteAnimationSpec = springSpec;
        }
        if ((i2 & 8) != 0) {
            str = "FloatAnimation";
        }
        String str2 = str;
        Function1 function12 = (i2 & 16) != 0 ? null : function1;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.animateFloatAsState (AnimateAsState.kt:67)");
        }
        if (finiteAnimationSpec == springSpec) {
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startReplaceGroup(1125558999);
            boolean z = (((i & 896) ^ 384) > 256 && composerImpl.changed(0.01f)) || (i & 384) == 256;
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = AnimationSpecKt.spring$default(0.0f, 0.0f, Float.valueOf(0.01f), 3);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                finiteAnimationSpec2 = (SpringSpec) objRememberedValue;
            }
        } else {
            ComposerImpl composerImpl2 = (ComposerImpl) composer;
            composerImpl2.startReplaceGroup(1125668925);
            composerImpl2.end(false);
            finiteAnimationSpec2 = finiteAnimationSpec;
        }
        Float fValueOf = Float.valueOf(f);
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        int i4 = i << 3;
        State stateAnimateValueAsState = animateValueAsState(fValueOf, VectorConvertersKt.FloatToVector, finiteAnimationSpec2, Float.valueOf(0.01f), str2, function12, composer, (i & 14) | (i4 & 7168) | (57344 & i4) | (i4 & 458752), 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return stateAnimateValueAsState;
    }

    public static final State animateValueAsState(final Object obj, TwoWayConverter twoWayConverter, AnimationSpec animationSpec, Object obj2, String str, Function1 function1, Composer composer, int i, int i2) {
        if ((i2 & 8) != 0) {
            obj2 = null;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.animateValueAsState (AnimateAsState.kt:395)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Object obj3 = Composer.Companion.Empty;
        if (objRememberedValue == obj3) {
            objRememberedValue = SnapshotStateKt.mutableStateOf$default(null);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        MutableState mutableState = (MutableState) objRememberedValue;
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (objRememberedValue2 == obj3) {
            objRememberedValue2 = new Animatable(obj, twoWayConverter, obj2, str);
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        Animatable animatable = (Animatable) objRememberedValue2;
        MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function1, composerImpl);
        if (obj2 != null && (animationSpec instanceof SpringSpec)) {
            SpringSpec springSpec = (SpringSpec) animationSpec;
            if (!Intrinsics.areEqual(springSpec.visibilityThreshold, obj2)) {
                animationSpec = new SpringSpec(springSpec.dampingRatio, springSpec.stiffness, obj2);
            }
        }
        MutableState mutableStateRememberUpdatedState2 = SnapshotStateKt.rememberUpdatedState(animationSpec, composerImpl);
        Object objRememberedValue3 = composerImpl.rememberedValue();
        if (objRememberedValue3 == obj3) {
            objRememberedValue3 = ChannelKt.Channel$default(-1, null, null, 6);
            composerImpl.updateRememberedValue(objRememberedValue3);
        }
        final Channel channel = (Channel) objRememberedValue3;
        boolean zChangedInstance = composerImpl.changedInstance(channel) | ((((i & 14) ^ 6) > 4 && composerImpl.changedInstance(obj)) || (i & 6) == 4);
        Object objRememberedValue4 = composerImpl.rememberedValue();
        if (zChangedInstance || objRememberedValue4 == obj3) {
            objRememberedValue4 = new Function0() { // from class: androidx.compose.animation.core.AnimateAsStateKt$animateValueAsState$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    channel.mo3475trySendJP2dKIU(obj);
                    return Unit.INSTANCE;
                }
            };
            composerImpl.updateRememberedValue(objRememberedValue4);
        }
        EffectsKt.SideEffect((Function0) objRememberedValue4, composerImpl);
        boolean zChangedInstance2 = composerImpl.changedInstance(channel) | composerImpl.changedInstance(animatable) | composerImpl.changed(mutableStateRememberUpdatedState2) | composerImpl.changed(mutableStateRememberUpdatedState);
        Object objRememberedValue5 = composerImpl.rememberedValue();
        if (zChangedInstance2 || objRememberedValue5 == obj3) {
            Object animateAsStateKt$animateValueAsState$3$1 = new AnimateAsStateKt$animateValueAsState$3$1(channel, animatable, mutableStateRememberUpdatedState2, mutableStateRememberUpdatedState, null);
            composerImpl.updateRememberedValue(animateAsStateKt$animateValueAsState$3$1);
            objRememberedValue5 = animateAsStateKt$animateValueAsState$3$1;
        }
        EffectsKt.LaunchedEffect(composerImpl, channel, (Function2) objRememberedValue5);
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
