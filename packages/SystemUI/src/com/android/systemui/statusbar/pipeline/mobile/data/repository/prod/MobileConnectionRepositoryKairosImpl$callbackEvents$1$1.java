package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

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
