package com.android.systemui.qs.tiles.impl.battery.domain.interactor;

import com.android.systemui.qs.tiles.impl.battery.domain.model.BatterySaverTileModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

final class BatterySaverTileDataInteractor$tileData$1 extends SuspendLambda implements Function4 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public BatterySaverTileDataInteractor$tileData$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        ((Number) obj3).intValue();
        BatterySaverTileDataInteractor$tileData$1 batterySaverTileDataInteractor$tileData$1 = new BatterySaverTileDataInteractor$tileData$1((Continuation) obj4);
        batterySaverTileDataInteractor$tileData$1.Z$0 = booleanValue;
        batterySaverTileDataInteractor$tileData$1.Z$1 = booleanValue2;
        return batterySaverTileDataInteractor$tileData$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new BatterySaverTileModel.Standard(this.Z$0, this.Z$1);
    }
}
