package androidx.compose.foundation.gestures;

import androidx.compose.animation.core.AnimationScope;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.AnimationStateKt;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.SuspendAnimationKt;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.gestures.MouseWheelScrollingLogic;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes.dex */
final class MouseWheelScrollingLogic$dispatchMouseWheelScroll$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$ObjectRef<AnimationState<Float, AnimationVector1D>> $animationState;
    final /* synthetic */ float $speed;
    final /* synthetic */ Ref$ObjectRef<MouseWheelScrollingLogic.MouseWheelScrollDelta> $targetScrollDelta;
    final /* synthetic */ Ref$FloatRef $targetValue;
    final /* synthetic */ ScrollingLogic $this_dispatchMouseWheelScroll;
    final /* synthetic */ float $threshold;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ MouseWheelScrollingLogic this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MouseWheelScrollingLogic$dispatchMouseWheelScroll$3(Ref$FloatRef ref$FloatRef, Ref$ObjectRef<AnimationState<Float, AnimationVector1D>> ref$ObjectRef, Ref$ObjectRef<MouseWheelScrollingLogic.MouseWheelScrollDelta> ref$ObjectRef2, float f, MouseWheelScrollingLogic mouseWheelScrollingLogic, float f2, ScrollingLogic scrollingLogic, Continuation continuation) {
        super(2, continuation);
        this.$targetValue = ref$FloatRef;
        this.$animationState = ref$ObjectRef;
        this.$targetScrollDelta = ref$ObjectRef2;
        this.$threshold = f;
        this.this$0 = mouseWheelScrollingLogic;
        this.$speed = f2;
        this.$this_dispatchMouseWheelScroll = scrollingLogic;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MouseWheelScrollingLogic$dispatchMouseWheelScroll$3 mouseWheelScrollingLogic$dispatchMouseWheelScroll$3 = new MouseWheelScrollingLogic$dispatchMouseWheelScroll$3(this.$targetValue, this.$animationState, this.$targetScrollDelta, this.$threshold, this.this$0, this.$speed, this.$this_dispatchMouseWheelScroll, continuation);
        mouseWheelScrollingLogic$dispatchMouseWheelScroll$3.L$0 = obj;
        return mouseWheelScrollingLogic$dispatchMouseWheelScroll$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MouseWheelScrollingLogic$dispatchMouseWheelScroll$3) create((NestedScrollScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x018a  */
    /* JADX WARN: Type inference failed for: r2v9, types: [T, androidx.compose.animation.core.AnimationState] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x017e -> B:36:0x0180). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x018a -> B:13:0x0064). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        Ref$BooleanRef ref$BooleanRef;
        final NestedScrollScope nestedScrollScope;
        NestedScrollScope nestedScrollScope2;
        Ref$BooleanRef ref$BooleanRef2;
        MouseWheelScrollingLogic$dispatchMouseWheelScroll$3 mouseWheelScrollingLogic$dispatchMouseWheelScroll$3;
        int i;
        Object objAccess$dispatchMouseWheelScroll$waitNextScrollDelta;
        MouseWheelScrollingLogic$dispatchMouseWheelScroll$3 mouseWheelScrollingLogic$dispatchMouseWheelScroll$32 = this;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            NestedScrollScope nestedScrollScope3 = (NestedScrollScope) mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$0;
            ref$BooleanRef = new Ref$BooleanRef();
            ref$BooleanRef.element = true;
            nestedScrollScope = nestedScrollScope3;
        } else if (i2 == 1) {
            Ref$BooleanRef ref$BooleanRef3 = (Ref$BooleanRef) mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$2;
            ref$BooleanRef = (Ref$BooleanRef) mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$1;
            NestedScrollScope nestedScrollScope4 = (NestedScrollScope) mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$0;
            ResultKt.throwOnFailure(obj);
            nestedScrollScope = nestedScrollScope4;
            ref$BooleanRef3.element = ((Boolean) obj).booleanValue();
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$32 = this;
        } else if (i2 == 2) {
            i = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.I$0;
            Ref$BooleanRef ref$BooleanRef4 = (Ref$BooleanRef) mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$1;
            NestedScrollScope nestedScrollScope5 = (NestedScrollScope) mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$0;
            ResultKt.throwOnFailure(obj);
            ref$BooleanRef2 = ref$BooleanRef4;
            nestedScrollScope2 = nestedScrollScope5;
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$3 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32;
            if (ref$BooleanRef2.element) {
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$3.L$0 = nestedScrollScope2;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$3.L$1 = ref$BooleanRef2;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$3.L$2 = ref$BooleanRef2;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$3.label = 3;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$32 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$3;
                objAccess$dispatchMouseWheelScroll$waitNextScrollDelta = MouseWheelScrollingLogic.access$dispatchMouseWheelScroll$waitNextScrollDelta(mouseWheelScrollingLogic$dispatchMouseWheelScroll$3.this$0, mouseWheelScrollingLogic$dispatchMouseWheelScroll$3.$targetScrollDelta, mouseWheelScrollingLogic$dispatchMouseWheelScroll$3.$targetValue, mouseWheelScrollingLogic$dispatchMouseWheelScroll$3.$this_dispatchMouseWheelScroll, mouseWheelScrollingLogic$dispatchMouseWheelScroll$3.$animationState, 50 - i, mouseWheelScrollingLogic$dispatchMouseWheelScroll$32);
                if (objAccess$dispatchMouseWheelScroll$waitNextScrollDelta != coroutineSingletons) {
                    ref$BooleanRef = ref$BooleanRef2;
                    nestedScrollScope = nestedScrollScope2;
                    ref$BooleanRef2.element = ((Boolean) objAccess$dispatchMouseWheelScroll$waitNextScrollDelta).booleanValue();
                }
                return coroutineSingletons;
            }
            mouseWheelScrollingLogic$dispatchMouseWheelScroll$32 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$3;
            ref$BooleanRef = ref$BooleanRef2;
            nestedScrollScope = nestedScrollScope2;
        } else {
            if (i2 != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Ref$BooleanRef ref$BooleanRef5 = (Ref$BooleanRef) mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$2;
            ref$BooleanRef = (Ref$BooleanRef) mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$1;
            NestedScrollScope nestedScrollScope6 = (NestedScrollScope) mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$0;
            ResultKt.throwOnFailure(obj);
            ref$BooleanRef2 = ref$BooleanRef5;
            nestedScrollScope = nestedScrollScope6;
            objAccess$dispatchMouseWheelScroll$waitNextScrollDelta = obj;
            ref$BooleanRef2.element = ((Boolean) objAccess$dispatchMouseWheelScroll$waitNextScrollDelta).booleanValue();
        }
        while (ref$BooleanRef.element) {
            ref$BooleanRef.element = false;
            float fFloatValue = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$targetValue.element - ((Number) ((SnapshotMutableStateImpl) mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$animationState.element.value$delegate).getValue()).floatValue();
            if (mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$targetScrollDelta.element.shouldApplyImmediately || Math.abs(fFloatValue) < mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$threshold) {
                Ref$BooleanRef ref$BooleanRef6 = ref$BooleanRef;
                MouseWheelScrollingLogic.access$dispatchMouseWheelScroll(mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.this$0, nestedScrollScope, fFloatValue);
                MouseWheelScrollingLogic mouseWheelScrollingLogic = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.this$0;
                Ref$ObjectRef<MouseWheelScrollingLogic.MouseWheelScrollDelta> ref$ObjectRef = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$targetScrollDelta;
                Ref$FloatRef ref$FloatRef = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$targetValue;
                ScrollingLogic scrollingLogic = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$this_dispatchMouseWheelScroll;
                Ref$ObjectRef<AnimationState<Float, AnimationVector1D>> ref$ObjectRef2 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$animationState;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$0 = nestedScrollScope;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$1 = ref$BooleanRef6;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$2 = ref$BooleanRef6;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.label = 1;
                NestedScrollScope nestedScrollScope7 = nestedScrollScope;
                Object objAccess$dispatchMouseWheelScroll$waitNextScrollDelta2 = MouseWheelScrollingLogic.access$dispatchMouseWheelScroll$waitNextScrollDelta(mouseWheelScrollingLogic, ref$ObjectRef, ref$FloatRef, scrollingLogic, ref$ObjectRef2, 50L, mouseWheelScrollingLogic$dispatchMouseWheelScroll$32);
                if (objAccess$dispatchMouseWheelScroll$waitNextScrollDelta2 != coroutineSingletons) {
                    ref$BooleanRef = ref$BooleanRef6;
                    nestedScrollScope = nestedScrollScope7;
                    ref$BooleanRef6.element = ((Boolean) objAccess$dispatchMouseWheelScroll$waitNextScrollDelta2).booleanValue();
                    mouseWheelScrollingLogic$dispatchMouseWheelScroll$32 = this;
                }
            } else {
                float fSignum = Math.signum(fFloatValue) * mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$threshold;
                MouseWheelScrollingLogic.access$dispatchMouseWheelScroll(mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.this$0, nestedScrollScope, fSignum);
                Ref$ObjectRef<AnimationState<Float, AnimationVector1D>> ref$ObjectRef3 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$animationState;
                AnimationState<Float, AnimationVector1D> animationState = ref$ObjectRef3.element;
                ref$ObjectRef3.element = AnimationStateKt.copy$default(animationState, ((Number) ((SnapshotMutableStateImpl) animationState.value$delegate).getValue()).floatValue() + fSignum, 0.0f, 30);
                int iRoundToInt = MathKt__MathJVMKt.roundToInt(Math.abs(mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$targetValue.element - ((Number) ((SnapshotMutableStateImpl) mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$animationState.element.value$delegate).getValue()).floatValue()) / mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$speed);
                if (iRoundToInt > 100) {
                    iRoundToInt = 100;
                }
                final MouseWheelScrollingLogic mouseWheelScrollingLogic2 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.this$0;
                AnimationState<Float, AnimationVector1D> animationState2 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$animationState.element;
                final Ref$FloatRef ref$FloatRef2 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$targetValue;
                float f = ref$FloatRef2.element;
                final Ref$ObjectRef<MouseWheelScrollingLogic.MouseWheelScrollDelta> ref$ObjectRef4 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$targetScrollDelta;
                final ScrollingLogic scrollingLogic2 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.$this_dispatchMouseWheelScroll;
                final Ref$BooleanRef ref$BooleanRef7 = ref$BooleanRef;
                final Function1 function1 = new Function1() { // from class: androidx.compose.foundation.gestures.MouseWheelScrollingLogic$dispatchMouseWheelScroll$3.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    /* JADX WARN: Type inference failed for: r3v2, types: [T, androidx.compose.foundation.gestures.MouseWheelScrollingLogic$MouseWheelScrollDelta] */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        float fFloatValue2 = ((Number) obj2).floatValue();
                        MouseWheelScrollingLogic.MouseWheelScrollDelta mouseWheelScrollDeltaSumOrNull = MouseWheelScrollingLogic.sumOrNull(mouseWheelScrollingLogic2.channel);
                        if (mouseWheelScrollDeltaSumOrNull != null) {
                            mouseWheelScrollingLogic2.trackVelocity(mouseWheelScrollDeltaSumOrNull);
                            Ref$ObjectRef<MouseWheelScrollingLogic.MouseWheelScrollDelta> ref$ObjectRef5 = ref$ObjectRef4;
                            ref$ObjectRef5.element = ref$ObjectRef5.element.plus(mouseWheelScrollDeltaSumOrNull);
                            Ref$FloatRef ref$FloatRef3 = ref$FloatRef2;
                            ScrollingLogic scrollingLogic3 = scrollingLogic2;
                            ref$FloatRef3.element = scrollingLogic3.m85toFloatk4lQ0M(scrollingLogic3.m84reverseIfNeededMKHz9U(ref$ObjectRef4.element.value));
                            ref$BooleanRef7.element = !MouseWheelScrollableKt.access$isLowScrollingDelta(ref$FloatRef2.element - fFloatValue2);
                        }
                        return Boolean.valueOf(mouseWheelScrollDeltaSumOrNull != null);
                    }
                };
                ref$BooleanRef2 = ref$BooleanRef7;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$0 = nestedScrollScope;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$1 = ref$BooleanRef2;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.L$2 = null;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.I$0 = iRoundToInt;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$32.label = 2;
                mouseWheelScrollingLogic2.getClass();
                final Ref$FloatRef ref$FloatRef3 = new Ref$FloatRef();
                ref$FloatRef3.element = ((Number) ((SnapshotMutableStateImpl) animationState2.value$delegate).getValue()).floatValue();
                Float f2 = new Float(f);
                TweenSpec tweenSpecTween$default = AnimationSpecKt.tween$default(iRoundToInt, 0, EasingKt.LinearEasing, 2);
                Function1 function12 = new Function1() { // from class: androidx.compose.foundation.gestures.MouseWheelScrollingLogic$animateMouseWheelScroll$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:10:0x004c  */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object mo781invoke(Object obj2) {
                        AnimationScope animationScope = (AnimationScope) obj2;
                        float fFloatValue2 = ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue() - ref$FloatRef3.element;
                        if (MouseWheelScrollableKt.access$isLowScrollingDelta(fFloatValue2)) {
                            if (((Boolean) function1.mo781invoke(Float.valueOf(ref$FloatRef3.element))).booleanValue()) {
                                animationScope.cancelAnimation();
                            }
                        } else if (MouseWheelScrollableKt.access$isLowScrollingDelta(fFloatValue2 - MouseWheelScrollingLogic.access$dispatchMouseWheelScroll(mouseWheelScrollingLogic2, nestedScrollScope, fFloatValue2))) {
                            ref$FloatRef3.element += fFloatValue2;
                            if (((Boolean) function1.mo781invoke(Float.valueOf(ref$FloatRef3.element))).booleanValue()) {
                            }
                        } else {
                            animationScope.cancelAnimation();
                        }
                        return Unit.INSTANCE;
                    }
                };
                int i3 = iRoundToInt;
                mouseWheelScrollingLogic$dispatchMouseWheelScroll$3 = mouseWheelScrollingLogic$dispatchMouseWheelScroll$32;
                Object objAnimateTo = SuspendAnimationKt.animateTo(animationState2, f2, tweenSpecTween$default, true, function12, mouseWheelScrollingLogic$dispatchMouseWheelScroll$3);
                if (objAnimateTo != CoroutineSingletons.COROUTINE_SUSPENDED) {
                    objAnimateTo = Unit.INSTANCE;
                }
                if (objAnimateTo != coroutineSingletons) {
                    nestedScrollScope2 = nestedScrollScope;
                    i = i3;
                    if (ref$BooleanRef2.element) {
                    }
                }
            }
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
