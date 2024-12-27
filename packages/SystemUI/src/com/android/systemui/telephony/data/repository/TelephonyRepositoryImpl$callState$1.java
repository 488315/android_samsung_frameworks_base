package com.android.systemui.telephony.data.repository;

import android.telephony.TelephonyCallback;
import com.android.systemui.telephony.TelephonyListenerManager;
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class TelephonyRepositoryImpl$callState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TelephonyRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TelephonyRepositoryImpl$callState$1(TelephonyRepositoryImpl telephonyRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = telephonyRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TelephonyRepositoryImpl$callState$1 telephonyRepositoryImpl$callState$1 = new TelephonyRepositoryImpl$callState$1(this.this$0, continuation);
        telephonyRepositoryImpl$callState$1.L$0 = obj;
        return telephonyRepositoryImpl$callState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TelephonyRepositoryImpl$callState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final TelephonyRepositoryImpl$callState$1$listener$1 telephonyRepositoryImpl$callState$1$listener$1 = new TelephonyRepositoryImpl$callState$1$listener$1(producerScope);
            final TelephonyCallback.CallStateListener callStateListener = new TelephonyCallback.CallStateListener() { // from class: com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$sam$android_telephony_TelephonyCallback_CallStateListener$0
                @Override // android.telephony.TelephonyCallback.CallStateListener
                public final /* synthetic */ void onCallStateChanged(int i2) {
                    Function1.this.invoke(Integer.valueOf(i2));
                }
            };
            TelephonyListenerManager telephonyListenerManager = this.this$0.manager;
            ((ArrayList) telephonyListenerManager.mTelephonyCallback.mCallStateListeners).add(callStateListener);
            telephonyListenerManager.updateListening();
            final TelephonyRepositoryImpl telephonyRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$callState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    TelephonyListenerManager telephonyListenerManager2 = TelephonyRepositoryImpl.this.manager;
                    ((ArrayList) telephonyListenerManager2.mTelephonyCallback.mCallStateListeners).remove(callStateListener);
                    telephonyListenerManager2.updateListening();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
