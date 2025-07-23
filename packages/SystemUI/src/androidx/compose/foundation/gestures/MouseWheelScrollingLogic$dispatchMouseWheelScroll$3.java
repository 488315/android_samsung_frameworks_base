package androidx.compose.foundation.gestures;

import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.foundation.gestures.MouseWheelScrollingLogic;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:26:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x018a  */
    /* JADX WARN: Type inference failed for: r2v9, types: [T, androidx.compose.animation.core.AnimationState] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x017e -> B:7:0x0180). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x018a -> B:8:0x0064). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r19) {
        /*
            Method dump skipped, instructions count: 449
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.MouseWheelScrollingLogic$dispatchMouseWheelScroll$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
