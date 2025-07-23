package com.android.systemui.qs.pipeline.data.domain.interactor;

import com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepository;
import com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class RemovedTilesInteractorImpl$setChangedTiles$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<TileSpec> $result;
    int label;
    final /* synthetic */ RemovedTilesInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RemovedTilesInteractorImpl$setChangedTiles$1(RemovedTilesInteractorImpl removedTilesInteractorImpl, List<TileSpec> list, Continuation continuation) {
        super(2, continuation);
        this.this$0 = removedTilesInteractorImpl;
        this.$result = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RemovedTilesInteractorImpl$setChangedTiles$1(this.this$0, this.$result, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RemovedTilesInteractorImpl$setChangedTiles$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            RemovedTilesInteractorImpl removedTilesInteractorImpl = this.this$0;
            RemovedTileSpecRepository removedTileSpecRepository = removedTilesInteractorImpl.removedTileSpecRepository;
            int i2 = removedTilesInteractorImpl.userId;
            List<TileSpec> list = this.$result;
            this.label = 1;
            if (((RemovedTileSpecRepositoryImpl) removedTileSpecRepository).storeTiles(i2, list, this) == coroutineSingletons) {
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
