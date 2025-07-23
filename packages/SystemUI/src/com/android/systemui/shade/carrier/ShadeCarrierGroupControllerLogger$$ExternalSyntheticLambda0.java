package com.android.systemui.shade.carrier;

import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ShadeCarrierGroupControllerLogger$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        int i = ShadeCarrierGroupControllerLogger.$r8$clinit;
        String str1 = logMessage.getStr1();
        boolean bool1 = logMessage.getBool1();
        return MoveResult$$ExternalSyntheticOutline0.m(CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("handleUpdateCarrierInfo: result=(carrierText=", str1, ", anySimReady=", ", airplaneMode=", bool1), logMessage.getBool2(), ")");
    }
}
