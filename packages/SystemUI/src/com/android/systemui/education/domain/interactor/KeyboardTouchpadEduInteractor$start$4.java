package com.android.systemui.education.domain.interactor;

import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.education.data.repository.UserContextualEducationRepository;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyboardTouchpadEduInteractor$start$4 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyboardTouchpadEduInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyboardTouchpadEduInteractor$start$4(KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyboardTouchpadEduInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyboardTouchpadEduInteractor$start$4(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyboardTouchpadEduInteractor$start$4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor = this.this$0;
            Flow flow = keyboardTouchpadEduInteractor.contextualEducationInteractor.keyboardShortcutTriggered;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$4.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    ContextualEducationInteractor contextualEducationInteractor = KeyboardTouchpadEduInteractor.this.contextualEducationInteractor;
                    contextualEducationInteractor.getClass();
                    Object updateGestureEduModel = ((UserContextualEducationRepository) contextualEducationInteractor.repository).updateGestureEduModel((GestureType) obj2, new ContextualEducationInteractor$$ExternalSyntheticLambda1(contextualEducationInteractor, 3), continuation);
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (updateGestureEduModel != coroutineSingletons2) {
                        updateGestureEduModel = Unit.INSTANCE;
                    }
                    return updateGestureEduModel == coroutineSingletons2 ? updateGestureEduModel : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
