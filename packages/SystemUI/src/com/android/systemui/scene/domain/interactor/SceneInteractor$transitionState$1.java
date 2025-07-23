package com.android.systemui.scene.domain.interactor;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.scene.shared.logger.SceneLogger;
import com.android.systemui.scene.shared.logger.SceneLogger$$ExternalSyntheticLambda0;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SceneInteractor$transitionState$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SceneInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SceneInteractor$transitionState$1(SceneInteractor sceneInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sceneInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SceneInteractor$transitionState$1 sceneInteractor$transitionState$1 = new SceneInteractor$transitionState$1(this.this$0, continuation);
        sceneInteractor$transitionState$1.L$0 = obj;
        return sceneInteractor$transitionState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SceneInteractor$transitionState$1) create((ObservableTransitionState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ObservableTransitionState observableTransitionState = (ObservableTransitionState) this.L$0;
        SceneLogger sceneLogger = this.this$0.logger;
        sceneLogger.getClass();
        boolean z = observableTransitionState instanceof ObservableTransitionState.Transition;
        LogBuffer logBuffer = sceneLogger.logBuffer;
        if (z) {
            LogMessage obtain = logBuffer.obtain("SceneFramework", LogLevel.INFO, new SceneLogger$$ExternalSyntheticLambda0(0), null);
            ObservableTransitionState.Transition transition = (ObservableTransitionState.Transition) observableTransitionState;
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = transition.fromContent.toString();
            logMessageImpl.str2 = transition.toContent.toString();
            logBuffer.commit(obtain);
        } else {
            if (!(observableTransitionState instanceof ObservableTransitionState.Idle)) {
                throw new NoWhenBranchMatchedException();
            }
            LogMessage obtain2 = logBuffer.obtain("SceneFramework", LogLevel.INFO, new SceneLogger$$ExternalSyntheticLambda0(1), null);
            ObservableTransitionState.Idle idle = (ObservableTransitionState.Idle) observableTransitionState;
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
            logMessageImpl2.str1 = idle.currentScene.toString();
            logMessageImpl2.str2 = CollectionsKt___CollectionsKt.joinToString$default(idle.currentOverlays, null, null, null, null, 63);
            logBuffer.commit(obtain2);
        }
        return Unit.INSTANCE;
    }
}
