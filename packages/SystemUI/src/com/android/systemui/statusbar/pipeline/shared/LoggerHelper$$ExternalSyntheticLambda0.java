package com.android.systemui.statusbar.pipeline.shared;

import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class LoggerHelper$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                LoggerHelper loggerHelper = LoggerHelper.INSTANCE;
                String str = logMessage.getBool1() ? SystemUIAnalytics.DID_NOTI_SELECT_DEFAULT : "";
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(int1, "on", str, "CapabilitiesChanged: net=", " capabilities=");
                sbM890m.append(str1);
                return sbM890m.toString();
            default:
                LoggerHelper loggerHelper2 = LoggerHelper.INSTANCE;
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "on", logMessage.getBool1() ? SystemUIAnalytics.DID_NOTI_SELECT_DEFAULT : "", "Lost: net=");
        }
    }
}
