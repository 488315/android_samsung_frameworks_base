package com.android.systemui.communal;

import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.shared.model.CommunalScenes;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

final class CommunalSceneStartable$startHubTimeout$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CommunalSceneStartable this$0;

    public CommunalSceneStartable$startHubTimeout$1(CommunalSceneStartable communalSceneStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalSceneStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalSceneStartable$startHubTimeout$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalSceneStartable$startHubTimeout$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Duration.Companion companion = Duration.Companion;
            long duration = DurationKt.toDuration(this.this$0.screenTimeout, DurationUnit.MILLISECONDS);
            this.label = 1;
            if (DelayKt.m2546delayVtjQ1oo(duration, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        CommunalSceneStartable communalSceneStartable = this.this$0;
        if (communalSceneStartable.isDreaming) {
            ((CommunalSceneRepositoryImpl) communalSceneStartable.communalSceneInteractor.communalSceneRepository).changeScene(CommunalScenes.Blank, null);
        }
        this.this$0.timeoutJob = null;
        return Unit.INSTANCE;
    }
}
