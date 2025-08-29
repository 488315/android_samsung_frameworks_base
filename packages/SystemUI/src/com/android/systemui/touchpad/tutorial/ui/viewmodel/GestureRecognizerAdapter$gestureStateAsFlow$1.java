package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import com.android.systemui.touchpad.tutorial.ui.gesture.GestureRecognizer;
import com.android.systemui.touchpad.tutorial.ui.gesture.GestureState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class GestureRecognizerAdapter$gestureStateAsFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ GestureRecognizer $recognizer;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GestureRecognizerAdapter$gestureStateAsFlow$1(GestureRecognizer gestureRecognizer, Continuation continuation) {
        super(2, continuation);
        this.$recognizer = gestureRecognizer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        GestureRecognizerAdapter$gestureStateAsFlow$1 gestureRecognizerAdapter$gestureStateAsFlow$1 = new GestureRecognizerAdapter$gestureStateAsFlow$1(this.$recognizer, continuation);
        gestureRecognizerAdapter$gestureStateAsFlow$1.L$0 = obj;
        return gestureRecognizerAdapter$gestureStateAsFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GestureRecognizerAdapter$gestureStateAsFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.touchpad.tutorial.ui.viewmodel.GestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            this.$recognizer.addGestureStateCallback(new Function1() { // from class: com.android.systemui.touchpad.tutorial.ui.viewmodel.GestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU((GestureState) obj2);
                    return Unit.INSTANCE;
                }
            });
            final GestureRecognizer gestureRecognizer = this.$recognizer;
            Function0 function0 = new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.viewmodel.GestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    gestureRecognizer.clearGestureStateCallback();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
