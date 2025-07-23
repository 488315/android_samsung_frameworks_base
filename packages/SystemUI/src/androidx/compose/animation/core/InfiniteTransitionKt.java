package androidx.compose.animation.core;

import androidx.compose.animation.core.InfiniteTransition;
import androidx.compose.animation.core.InfiniteTransition.TransitionAnimationState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class InfiniteTransitionKt {
    public static final InfiniteTransition.TransitionAnimationState animateFloat(InfiniteTransition infiniteTransition, float f, float f2, InfiniteRepeatableSpec infiniteRepeatableSpec, String str, Composer composer, int i, int i2) {
        final InfiniteTransition infiniteTransition2;
        final InfiniteRepeatableSpec infiniteRepeatableSpec2;
        if ((i2 & 8) != 0) {
            str = "FloatAnimation";
        }
        String str2 = str;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.animateFloat (InfiniteTransition.kt:296)");
        }
        final Float valueOf = Float.valueOf(f);
        final Float valueOf2 = Float.valueOf(f2);
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        TwoWayConverter twoWayConverter = VectorConvertersKt.FloatToVector;
        int i3 = (i & 1022) | NetworkAnalyticsConstants.DataPoints.FLAG_UID | ((i << 3) & 458752);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.animateValue (InfiniteTransition.kt:245)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            infiniteTransition2 = infiniteTransition;
            infiniteRepeatableSpec2 = infiniteRepeatableSpec;
            InfiniteTransition.TransitionAnimationState transitionAnimationState = infiniteTransition2.new TransitionAnimationState(valueOf, valueOf2, twoWayConverter, infiniteRepeatableSpec2, str2);
            composerImpl.updateRememberedValue(transitionAnimationState);
            rememberedValue = transitionAnimationState;
        } else {
            infiniteTransition2 = infiniteTransition;
            infiniteRepeatableSpec2 = infiniteRepeatableSpec;
        }
        final InfiniteTransition.TransitionAnimationState transitionAnimationState2 = (InfiniteTransition.TransitionAnimationState) rememberedValue;
        boolean z = true;
        boolean z2 = (((i3 & 112) ^ 48) > 32 && composerImpl.changedInstance(valueOf)) || (i3 & 48) == 32;
        if ((((i3 & 896) ^ 384) <= 256 || !composerImpl.changedInstance(valueOf2)) && (i3 & 384) != 256) {
            z = false;
        }
        boolean changedInstance = z2 | z | composerImpl.changedInstance(infiniteRepeatableSpec2);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new Function0() { // from class: androidx.compose.animation.core.InfiniteTransitionKt$animateValue$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    if (!Intrinsics.areEqual(valueOf, transitionAnimationState2.initialValue) || !Intrinsics.areEqual(valueOf2, transitionAnimationState2.targetValue)) {
                        InfiniteTransition.TransitionAnimationState<Object, AnimationVector> transitionAnimationState3 = transitionAnimationState2;
                        Object obj = valueOf;
                        Object obj2 = valueOf2;
                        InfiniteRepeatableSpec<Object> infiniteRepeatableSpec3 = infiniteRepeatableSpec2;
                        transitionAnimationState3.initialValue = obj;
                        transitionAnimationState3.targetValue = obj2;
                        transitionAnimationState3.animation = new TargetBasedAnimation(infiniteRepeatableSpec3, transitionAnimationState3.typeConverter, obj, obj2, (AnimationVector) null, 16, (DefaultConstructorMarker) null);
                        ((SnapshotMutableStateImpl) InfiniteTransition.this.refreshChildNeeded$delegate).setValue(Boolean.TRUE);
                        transitionAnimationState3.isFinished = false;
                        transitionAnimationState3.startOnTheNextFrame = true;
                    }
                    return Unit.INSTANCE;
                }
            };
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        EffectsKt.SideEffect((Function0) rememberedValue2, composerImpl);
        boolean changedInstance2 = composerImpl.changedInstance(infiniteTransition2);
        Object rememberedValue3 = composerImpl.rememberedValue();
        if (changedInstance2 || rememberedValue3 == composer$Companion$Empty$1) {
            rememberedValue3 = new Function1() { // from class: androidx.compose.animation.core.InfiniteTransitionKt$animateValue$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    InfiniteTransition infiniteTransition3 = InfiniteTransition.this;
                    infiniteTransition3._animations.add(transitionAnimationState2);
                    ((SnapshotMutableStateImpl) infiniteTransition3.refreshChildNeeded$delegate).setValue(Boolean.TRUE);
                    final InfiniteTransition infiniteTransition4 = InfiniteTransition.this;
                    final InfiniteTransition.TransitionAnimationState<Object, AnimationVector> transitionAnimationState3 = transitionAnimationState2;
                    return new DisposableEffectResult() { // from class: androidx.compose.animation.core.InfiniteTransitionKt$animateValue$2$1$invoke$$inlined$onDispose$1
                        @Override // androidx.compose.runtime.DisposableEffectResult
                        public final void dispose() {
                            InfiniteTransition.this._animations.remove(transitionAnimationState3);
                        }
                    };
                }
            };
            composerImpl.updateRememberedValue(rememberedValue3);
        }
        EffectsKt.DisposableEffect(transitionAnimationState2, (Function1) rememberedValue3, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return transitionAnimationState2;
    }

    public static final InfiniteTransition rememberInfiniteTransition(String str, Composer composer, int i) {
        if ((i & 1) != 0) {
            str = "InfiniteTransition";
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.rememberInfiniteTransition (InfiniteTransition.kt:44)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue = new InfiniteTransition(str);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        InfiniteTransition infiniteTransition = (InfiniteTransition) rememberedValue;
        infiniteTransition.run$animation_core(0, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return infiniteTransition;
    }
}
