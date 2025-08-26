package com.android.systemui.qs.tiles;

import com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileDataInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class ModesTile$handleUpdateState$model$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ModesTile this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ModesTile$handleUpdateState$model$1(ModesTile modesTile, Continuation continuation) {
        super(2, continuation);
        this.this$0 = modesTile;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ModesTile$handleUpdateState$model$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ModesTile$handleUpdateState$model$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        ModesTileDataInteractor modesTileDataInteractor = this.this$0.dataInteractor;
        this.label = 1;
        Object currentTileModel = modesTileDataInteractor.getCurrentTileModel(this);
        return currentTileModel == coroutineSingletons ? coroutineSingletons : currentTileModel;
    }
}
