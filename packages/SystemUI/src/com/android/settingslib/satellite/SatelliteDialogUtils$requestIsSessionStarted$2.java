package com.android.settingslib.satellite;

import android.content.Context;
import android.telephony.satellite.SatelliteManager;
import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SatelliteDialogUtils$requestIsSessionStarted$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SatelliteDialogUtils$requestIsSessionStarted$2(Context context, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SatelliteDialogUtils$requestIsSessionStarted$2(this.$context, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SatelliteDialogUtils$requestIsSessionStarted$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowOn;
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
        if (((SatelliteManager) this.$context.getSystemService(SatelliteManager.class)) == null) {
            Log.w("SatelliteDialogUtils", "SatelliteManager is null");
            return Boolean.FALSE;
        }
        SatelliteDialogUtils satelliteDialogUtils = SatelliteDialogUtils.INSTANCE;
        Context context = this.$context;
        satelliteDialogUtils.getClass();
        SatelliteManager satelliteManager = (SatelliteManager) context.getSystemService(SatelliteManager.class);
        if (satelliteManager == null) {
            Log.w("SatelliteDialogUtils", "SatelliteManager is null");
            flowOn = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        } else {
            flowOn = FlowKt.flowOn(FlowKt.callbackFlow(new SatelliteDialogUtils$getIsSessionStartedFlow$1(satelliteManager, null)), Dispatchers.Default);
        }
        Flow buffer$default = FlowKt.buffer$default(flowOn, -1, 2);
        this.label = 1;
        Object first = FlowKt.first(buffer$default, this);
        return first == coroutineSingletons ? coroutineSingletons : first;
    }
}
