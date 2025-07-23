package com.android.systemui.communal.log;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.communal.shared.log.CommunalUiEvent;
import com.android.systemui.util.kotlin.WithPrev;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalLoggerStartable$start$3 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public CommunalLoggerStartable$start$3(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        CommunalLoggerStartable$start$3 communalLoggerStartable$start$3 = new CommunalLoggerStartable$start$3((Continuation) obj3);
        communalLoggerStartable$start$3.L$0 = (WithPrev) obj;
        communalLoggerStartable$start$3.Z$0 = booleanValue;
        return communalLoggerStartable$start$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        WithPrev withPrev = (WithPrev) this.L$0;
        boolean z = this.Z$0;
        ObservableTransitionState observableTransitionState = (ObservableTransitionState) withPrev.component1();
        ObservableTransitionState observableTransitionState2 = (ObservableTransitionState) withPrev.component2();
        if (CommunalLoggerStartableKt.access$isOnCommunal(observableTransitionState2) && CommunalLoggerStartableKt.access$isSwipingToCommunal(observableTransitionState)) {
            return z ? CommunalUiEvent.DREAM_TO_COMMUNAL_HUB_SWIPE_FINISH : CommunalUiEvent.COMMUNAL_HUB_SWIPE_TO_ENTER_FINISH;
        }
        if (CommunalLoggerStartableKt.access$isOnCommunal(observableTransitionState2) && CommunalLoggerStartableKt.access$isSwipingFromCommunal(observableTransitionState)) {
            return z ? CommunalUiEvent.COMMUNAL_HUB_TO_DREAM_SWIPE_CANCEL : CommunalUiEvent.COMMUNAL_HUB_SWIPE_TO_EXIT_CANCEL;
        }
        if (CommunalLoggerStartableKt.access$isNotOnCommunal(observableTransitionState2) && CommunalLoggerStartableKt.access$isSwipingFromCommunal(observableTransitionState)) {
            return z ? CommunalUiEvent.COMMUNAL_HUB_TO_DREAM_SWIPE_FINISH : CommunalUiEvent.COMMUNAL_HUB_SWIPE_TO_EXIT_FINISH;
        }
        if (CommunalLoggerStartableKt.access$isNotOnCommunal(observableTransitionState2) && CommunalLoggerStartableKt.access$isSwipingToCommunal(observableTransitionState)) {
            return z ? CommunalUiEvent.DREAM_TO_COMMUNAL_HUB_SWIPE_CANCEL : CommunalUiEvent.COMMUNAL_HUB_SWIPE_TO_ENTER_CANCEL;
        }
        if (CommunalLoggerStartableKt.access$isSwipingToCommunal(observableTransitionState2) && CommunalLoggerStartableKt.access$isNotOnCommunal(observableTransitionState)) {
            return z ? CommunalUiEvent.DREAM_TO_COMMUNAL_HUB_SWIPE_START : CommunalUiEvent.COMMUNAL_HUB_SWIPE_TO_ENTER_START;
        }
        if (CommunalLoggerStartableKt.access$isSwipingFromCommunal(observableTransitionState2) && CommunalLoggerStartableKt.access$isOnCommunal(observableTransitionState)) {
            return z ? CommunalUiEvent.COMMUNAL_HUB_TO_DREAM_SWIPE_START : CommunalUiEvent.COMMUNAL_HUB_SWIPE_TO_EXIT_START;
        }
        return null;
    }
}
