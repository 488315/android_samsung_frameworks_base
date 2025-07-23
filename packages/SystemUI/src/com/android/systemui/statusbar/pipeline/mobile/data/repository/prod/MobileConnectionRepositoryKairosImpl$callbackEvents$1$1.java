package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class MobileConnectionRepositoryKairosImpl$callbackEvents$1$1 extends FunctionReferenceImpl implements Function2 {
    public static final MobileConnectionRepositoryKairosImpl$callbackEvents$1$1 INSTANCE = new MobileConnectionRepositoryKairosImpl$callbackEvents$1$1();

    public MobileConnectionRepositoryKairosImpl$callbackEvents$1$1() {
        super(2, TelephonyCallbackState.class, "applyEvent", "applyEvent(Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/prod/CallbackEvent;)Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/prod/TelephonyCallbackState;", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TelephonyCallbackState) obj).applyEvent((CallbackEvent) obj2);
    }
}
