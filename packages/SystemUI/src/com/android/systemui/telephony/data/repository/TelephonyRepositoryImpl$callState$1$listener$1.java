package com.android.systemui.telephony.data.repository;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class TelephonyRepositoryImpl$callState$1$listener$1 extends AdaptedFunctionReference implements Function1 {
    public TelephonyRepositoryImpl$callState$1$listener$1(Object obj) {
        super(1, obj, ProducerScope.class, "trySend", "trySend-JP2dKIU(Ljava/lang/Object;)Ljava/lang/Object;", 8);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        int intValue = ((Number) obj).intValue();
        ((ChannelCoroutine) ((ProducerScope) this.receiver)).mo2552trySendJP2dKIU(Integer.valueOf(intValue));
        return Unit.INSTANCE;
    }
}
