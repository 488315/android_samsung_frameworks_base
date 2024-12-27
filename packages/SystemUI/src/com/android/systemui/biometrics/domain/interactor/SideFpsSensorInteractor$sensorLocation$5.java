package com.android.systemui.biometrics.domain.interactor;

import com.android.systemui.biometrics.domain.model.SideFpsSensorLocation;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class SideFpsSensorInteractor$sensorLocation$5 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SideFpsSensorInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SideFpsSensorInteractor$sensorLocation$5(SideFpsSensorInteractor sideFpsSensorInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sideFpsSensorInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SideFpsSensorInteractor$sensorLocation$5 sideFpsSensorInteractor$sensorLocation$5 = new SideFpsSensorInteractor$sensorLocation$5(this.this$0, continuation);
        sideFpsSensorInteractor$sensorLocation$5.L$0 = obj;
        return sideFpsSensorInteractor$sensorLocation$5;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SideFpsSensorInteractor$sensorLocation$5) create((SideFpsSensorLocation) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SideFpsSensorLocation sideFpsSensorLocation = (SideFpsSensorLocation) this.L$0;
        this.this$0.logger.sensorLocationStateChanged(sideFpsSensorLocation.left, sideFpsSensorLocation.top, sideFpsSensorLocation.length, sideFpsSensorLocation.isSensorVerticalInDefaultOrientation);
        return Unit.INSTANCE;
    }
}
