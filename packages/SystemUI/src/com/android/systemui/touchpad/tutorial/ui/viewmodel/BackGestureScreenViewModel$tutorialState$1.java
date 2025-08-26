package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.touchpad.tutorial.ui.gesture.GestureDirection;
import com.android.systemui.touchpad.tutorial.ui.gesture.GestureState;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class BackGestureScreenViewModel$tutorialState$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BackGestureScreenViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BackGestureScreenViewModel$tutorialState$1(BackGestureScreenViewModel backGestureScreenViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = backGestureScreenViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BackGestureScreenViewModel$tutorialState$1 backGestureScreenViewModel$tutorialState$1 = new BackGestureScreenViewModel$tutorialState$1(this.this$0, (Continuation) obj3);
        backGestureScreenViewModel$tutorialState$1.L$0 = (GestureState) obj;
        backGestureScreenViewModel$tutorialState$1.L$1 = (GestureState) obj2;
        return backGestureScreenViewModel$tutorialState$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        GestureState gestureState = (GestureState) this.L$0;
        GestureState gestureState2 = (GestureState) this.L$1;
        this.this$0.getClass();
        Pair pair = ((gestureState2 instanceof GestureState.InProgress) && ((GestureState.InProgress) gestureState2).direction == GestureDirection.LEFT) ? new Pair("gesture to L", "end progress L") : new Pair("gesture to R", "end progress R");
        return new Pair(gestureState2, new TutorialAnimationProperties((String) pair.component1(), (String) pair.component2(), ((gestureState instanceof GestureState.InProgress) && ((GestureState.InProgress) gestureState).direction == GestureDirection.LEFT) ? R.raw.trackpad_back_success_left : R.raw.trackpad_back_success_right));
    }
}
