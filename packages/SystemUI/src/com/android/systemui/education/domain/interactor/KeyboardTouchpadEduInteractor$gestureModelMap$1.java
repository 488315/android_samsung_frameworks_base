package com.android.systemui.education.domain.interactor;

import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.education.data.model.GestureEduModel;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyboardTouchpadEduInteractor$gestureModelMap$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    int label;

    public KeyboardTouchpadEduInteractor$gestureModelMap$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        KeyboardTouchpadEduInteractor$gestureModelMap$1 keyboardTouchpadEduInteractor$gestureModelMap$1 = new KeyboardTouchpadEduInteractor$gestureModelMap$1((Continuation) obj5);
        keyboardTouchpadEduInteractor$gestureModelMap$1.L$0 = (GestureEduModel) obj;
        keyboardTouchpadEduInteractor$gestureModelMap$1.L$1 = (GestureEduModel) obj2;
        keyboardTouchpadEduInteractor$gestureModelMap$1.L$2 = (GestureEduModel) obj3;
        keyboardTouchpadEduInteractor$gestureModelMap$1.L$3 = (GestureEduModel) obj4;
        return keyboardTouchpadEduInteractor$gestureModelMap$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return MapsKt__MapsKt.mapOf(new Pair(GestureType.BACK, (GestureEduModel) this.L$0), new Pair(GestureType.HOME, (GestureEduModel) this.L$1), new Pair(GestureType.OVERVIEW, (GestureEduModel) this.L$2), new Pair(GestureType.ALL_APPS, (GestureEduModel) this.L$3));
    }
}
