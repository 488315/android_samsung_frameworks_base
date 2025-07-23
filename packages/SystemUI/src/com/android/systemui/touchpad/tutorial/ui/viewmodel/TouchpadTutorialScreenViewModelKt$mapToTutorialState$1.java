package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState;
import com.android.systemui.touchpad.tutorial.ui.gesture.GestureState;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class TouchpadTutorialScreenViewModelKt$mapToTutorialState$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_mapToTutorialState;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TouchpadTutorialScreenViewModelKt$mapToTutorialState$1(Flow flow, Continuation continuation) {
        super(2, continuation);
        this.$this_mapToTutorialState = flow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TouchpadTutorialScreenViewModelKt$mapToTutorialState$1 touchpadTutorialScreenViewModelKt$mapToTutorialState$1 = new TouchpadTutorialScreenViewModelKt$mapToTutorialState$1(this.$this_mapToTutorialState, continuation);
        touchpadTutorialScreenViewModelKt$mapToTutorialState$1.L$0 = obj;
        return touchpadTutorialScreenViewModelKt$mapToTutorialState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TouchpadTutorialScreenViewModelKt$mapToTutorialState$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [T, com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState$NotStarted] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final FlowCollector flowCollector = (FlowCollector) this.L$0;
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = TutorialActionState.NotStarted.INSTANCE;
            Flow flow = this.$this_mapToTutorialState;
            FlowCollector flowCollector2 = new FlowCollector() { // from class: com.android.systemui.touchpad.tutorial.ui.viewmodel.TouchpadTutorialScreenViewModelKt$mapToTutorialState$1.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    T finished;
                    Pair pair = (Pair) obj2;
                    GestureState gestureState = (GestureState) pair.component1();
                    TutorialAnimationProperties tutorialAnimationProperties = (TutorialAnimationProperties) pair.component2();
                    Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                    TutorialActionState tutorialActionState = (TutorialActionState) ref$ObjectRef2.element;
                    if (Intrinsics.areEqual(gestureState, GestureState.NotStarted.INSTANCE)) {
                        finished = TutorialActionState.NotStarted.INSTANCE;
                    } else if (gestureState instanceof GestureState.InProgress) {
                        TutorialActionState.InProgress inProgress = new TutorialActionState.InProgress(((GestureState.InProgress) gestureState).progress, tutorialAnimationProperties.progressStartMarker, tutorialAnimationProperties.progressEndMarker);
                        finished = ((tutorialActionState instanceof TutorialActionState.InProgressAfterError) || (tutorialActionState instanceof TutorialActionState.Error)) ? new TutorialActionState.InProgressAfterError(inProgress) : inProgress;
                    } else if (gestureState instanceof GestureState.Finished) {
                        finished = new TutorialActionState.Finished(tutorialAnimationProperties.successAnimation);
                    } else {
                        if (!Intrinsics.areEqual(gestureState, GestureState.Error.INSTANCE)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        finished = TutorialActionState.Error.INSTANCE;
                    }
                    ref$ObjectRef2.element = finished;
                    Object emit = flowCollector.emit(finished, continuation);
                    return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flow.collect(flowCollector2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
