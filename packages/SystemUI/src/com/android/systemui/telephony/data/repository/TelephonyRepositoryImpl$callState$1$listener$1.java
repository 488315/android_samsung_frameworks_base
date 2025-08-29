package com.android.systemui.telephony.data.repository;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final /* synthetic */ class TelephonyRepositoryImpl$callState$1$listener$1 extends AdaptedFunctionReference implements Function1 {
    public TelephonyRepositoryImpl$callState$1$listener$1(Object obj) {
        super(1, obj, ProducerScope.class, "trySend", "trySend-JP2dKIU(Ljava/lang/Object;)Ljava/lang/Object;", 8);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        int iIntValue = ((Number) obj).intValue();
        ((ChannelCoroutine) ((ProducerScope) this.receiver)).mo3475trySendJP2dKIU(Integer.valueOf(iIntValue));
        return Unit.INSTANCE;
    }
}
