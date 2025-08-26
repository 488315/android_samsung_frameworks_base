package com.android.keyguard.logging;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger;
import com.android.systemui.log.core.LogMessage;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class CarrierTextManagerLogger$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                CarrierTextManagerLogger.Companion companion = CarrierTextManagerLogger.Companion;
                return MoveResult$$ExternalSyntheticOutline0.m(CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("┗ updateCarrierText: result=(carrierText=", logMessage.getStr1(), ", anySimReady=", ", airplaneMode=", logMessage.getBool1()), logMessage.getBool2(), ")");
            case 1:
                CarrierTextManagerLogger.Companion companion2 = CarrierTextManagerLogger.Companion;
                String str1 = logMessage.getStr1();
                return "Start listening for satellite carrier text. Location=".concat(str1 != null ? str1 : "(unknown)");
            case 2:
                CarrierTextManagerLogger.Companion companion3 = CarrierTextManagerLogger.Companion;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("New satellite text = ", logMessage.getStr1());
            case 3:
                CarrierTextManagerLogger.Companion companion4 = CarrierTextManagerLogger.Companion;
                return "┣ updateCarrierText: found WFC state";
            case 4:
                CarrierTextManagerLogger.Companion companion5 = CarrierTextManagerLogger.Companion;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("┣ updateCarrierText: getting PLMN/SPN sticky brdcst. plmn=", logMessage.getStr1(), ", spn=", logMessage.getStr1());
            case 5:
                CarrierTextManagerLogger.Companion companion6 = CarrierTextManagerLogger.Companion;
                String str12 = logMessage.getStr1();
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "updateCarrierText: location=", str12 != null ? str12 : "(unknown)", " numSubs=");
            case 6:
                CarrierTextManagerLogger.Companion companion7 = CarrierTextManagerLogger.Companion;
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str13 = logMessage.getStr1();
                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(int1, int2, "┣ updateCarrierText: updating sub=", " simState=", " carrierName=");
                sbM.append(str13);
                return sbM.toString();
            case 7:
                CarrierTextManagerLogger.Companion companion8 = CarrierTextManagerLogger.Companion;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("┣ updateCarrierText: using satellite text. text=", logMessage.getStr1());
            default:
                CarrierTextManagerLogger.Companion companion9 = CarrierTextManagerLogger.Companion;
                String str14 = logMessage.getStr1();
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Stop listening for satellite carrier text. Location=", str14 != null ? str14 : "(unknown)", " Reason=", logMessage.getStr2());
        }
    }
}
