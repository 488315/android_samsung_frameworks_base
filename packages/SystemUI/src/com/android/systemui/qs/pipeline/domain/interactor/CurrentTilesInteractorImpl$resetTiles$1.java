package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.qs.pipeline.data.repository.TileSpecRepository;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CurrentTilesInteractorImpl$resetTiles$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CurrentTilesInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CurrentTilesInteractorImpl$resetTiles$1(CurrentTilesInteractorImpl currentTilesInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = currentTilesInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CurrentTilesInteractorImpl$resetTiles$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CurrentTilesInteractorImpl$resetTiles$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
            TileSpecRepository tileSpecRepository = currentTilesInteractorImpl.tileSpecRepository;
            int intValue = ((Number) currentTilesInteractorImpl.currentUser.getValue()).intValue();
            this.label = 1;
            if (tileSpecRepository.resetToDefault(intValue, this) == coroutineSingletons) {
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
