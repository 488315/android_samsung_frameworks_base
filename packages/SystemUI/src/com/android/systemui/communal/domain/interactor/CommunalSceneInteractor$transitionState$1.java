package com.android.systemui.communal.domain.interactor;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.communal.shared.log.CommunalSceneLogger;
import com.android.systemui.communal.shared.log.CommunalSceneLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalSceneInteractor$transitionState$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalSceneInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSceneInteractor$transitionState$1(CommunalSceneInteractor communalSceneInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalSceneInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalSceneInteractor$transitionState$1 communalSceneInteractor$transitionState$1 = new CommunalSceneInteractor$transitionState$1(this.this$0, continuation);
        communalSceneInteractor$transitionState$1.L$0 = obj;
        return communalSceneInteractor$transitionState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalSceneInteractor$transitionState$1) create((ObservableTransitionState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ObservableTransitionState observableTransitionState = (ObservableTransitionState) this.L$0;
        CommunalSceneLogger communalSceneLogger = this.this$0.logger;
        communalSceneLogger.getClass();
        boolean z = observableTransitionState instanceof ObservableTransitionState.Transition;
        LogBuffer logBuffer = communalSceneLogger.logBuffer;
        if (z) {
            LogMessage obtain = logBuffer.obtain("CommunalSceneLogger", LogLevel.INFO, new CommunalSceneLogger$$ExternalSyntheticLambda0(1), null);
            ObservableTransitionState.Transition transition = (ObservableTransitionState.Transition) observableTransitionState;
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = transition.fromContent.toString();
            logMessageImpl.str2 = transition.toContent.toString();
            logBuffer.commit(obtain);
        } else {
            if (!(observableTransitionState instanceof ObservableTransitionState.Idle)) {
                throw new NoWhenBranchMatchedException();
            }
            LogMessage obtain2 = logBuffer.obtain("CommunalSceneLogger", LogLevel.INFO, new CommunalSceneLogger$$ExternalSyntheticLambda0(2), null);
            ((LogMessageImpl) obtain2).str1 = ((ObservableTransitionState.Idle) observableTransitionState).currentScene.toString();
            logBuffer.commit(obtain2);
        }
        return Unit.INSTANCE;
    }
}
